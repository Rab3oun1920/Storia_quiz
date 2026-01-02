package org.storia.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import org.storia.entities.*;
import org.storia.repositories.*;
import org.storia.services.ThemeService;
import org.storia.dto.MessageResponse;

import jakarta.validation.Valid;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ThemeRepository themeRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private SoloQuizSessionRepository soloQuizSessionRepository;

    @Autowired
    private GroupQuizSessionRepository groupQuizSessionRepository;

    @Autowired
    private UserGlobalScoreRepository userGlobalScoreRepository;

    @Autowired
    private ThemeService themeService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private org.springframework.security.crypto.password.PasswordEncoder passwordEncoder;

    // ================================================================================
    // STATISTIQUES GLOBALES
    // ================================================================================

    @GetMapping("/statistics")
    public ResponseEntity<Map<String, Object>> getGlobalStatistics() {
        Map<String, Object> stats = new HashMap<>();

        // Statistiques de base
        stats.put("totalUsers", userRepository.count());
        stats.put("totalQuizzesSolo", soloQuizSessionRepository.count());
        stats.put("totalQuizzesGroup", groupQuizSessionRepository.count());
        stats.put("totalQuestions", questionRepository.count());
        stats.put("activeThemes", themeRepository.findByIsActiveTrue().size());

        // Calcul moyenne score solo
        List<UserGlobalScore> allScores = userGlobalScoreRepository.findAll();
        double avgScore = allScores.stream()
            .mapToInt(UserGlobalScore::getTotalScore)
            .average()
            .orElse(0.0);
        stats.put("averageScoreSolo", avgScore);

        // Thème le plus joué
        Map<Long, Long> themePlayCount = new HashMap<>();
        List<SoloQuizSession> sessions = soloQuizSessionRepository.findAll();
        for (SoloQuizSession session : sessions) {
            Long themeId = session.getTheme().getId();
            themePlayCount.put(themeId, themePlayCount.getOrDefault(themeId, 0L) + 1);
        }

        Long mostPlayedThemeId = themePlayCount.entrySet().stream()
            .max(Map.Entry.comparingByValue())
            .map(Map.Entry::getKey)
            .orElse(null);

        if (mostPlayedThemeId != null) {
            Theme mostPlayedTheme = themeRepository.findById(mostPlayedThemeId).orElse(null);
            stats.put("mostPlayedTheme", mostPlayedTheme != null ? mostPlayedTheme.getName() : "N/A");
        } else {
            stats.put("mostPlayedTheme", "N/A");
        }

        // Inscriptions aujourd'hui
        LocalDate today = LocalDate.now();
        long registrationsToday = userRepository.findAll().stream()
            .filter(u -> u.getCreatedAt().toLocalDate().equals(today))
            .count();
        stats.put("registrationsToday", registrationsToday);

        // Quiz aujourd'hui
        long quizzesToday = soloQuizSessionRepository.findAll().stream()
            .filter(s -> s.getCompletedAt() != null && s.getCompletedAt().toLocalDate().equals(today))
            .count();
        stats.put("quizzesToday", quizzesToday);

        // Top 10 utilisateurs
        List<UserGlobalScore> topUsers = userGlobalScoreRepository.findTop50ByOrderByTotalScoreDesc()
            .stream().limit(10).collect(Collectors.toList());
        stats.put("topUsers", topUsers);

        return ResponseEntity.ok(stats);
    }

    // ================================================================================
    // GESTION DES THÈMES
    // ================================================================================

    @GetMapping("/themes")
    public ResponseEntity<List<Map<String, Object>>> getAllThemes() {
        List<Theme> themes = themeRepository.findAll();

        // Enrichir les données des thèmes avec le nombre de questions
        List<Map<String, Object>> enrichedThemes = themes.stream().map(theme -> {
            Map<String, Object> themeMap = new HashMap<>();
            themeMap.put("id", theme.getId());
            themeMap.put("name", theme.getName());
            themeMap.put("isActive", theme.getIsActive());
            themeMap.put("createdAt", theme.getCreatedAt());

            // Ajouter le nombre de questions
            long questionCount = questionRepository.findByThemeIdAndIsSoloQuestionTrue(theme.getId()).size();
            themeMap.put("questionCount", questionCount);

            return themeMap;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(enrichedThemes);
    }

    @PostMapping("/themes")
    public ResponseEntity<Theme> createTheme(@Valid @RequestBody Theme theme) {
        Theme savedTheme = themeService.createTheme(theme);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTheme);
    }

    @PutMapping("/themes/{id}")
    public ResponseEntity<Theme> updateTheme(@PathVariable Long id, @Valid @RequestBody Theme themeDetails) {
        Theme updatedTheme = themeService.updateTheme(id, themeDetails);
        return ResponseEntity.ok(updatedTheme);
    }

    @DeleteMapping("/themes/{id}")
    public ResponseEntity<?> deleteTheme(@PathVariable Long id) {
        themeService.deleteTheme(id);
        return ResponseEntity.ok(new MessageResponse("Theme deleted successfully"));
    }

    // ================================================================================
    // GESTION DES QUESTIONS
    // ================================================================================

    @GetMapping("/questions")
    public ResponseEntity<Page<Question>> getAllQuestions(
            @RequestParam(required = false) Long themeId,
            @RequestParam(required = false) String difficulty,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Question> questions;

        if (themeId != null && difficulty != null) {
            // Filtrer par thème ET difficulté
            Question.Difficulty diff = Question.Difficulty.valueOf(difficulty);
            Theme theme = themeRepository.findById(themeId).orElse(null);
            if (theme != null) {
                List<Question> filtered = questionRepository.findByThemeAndDifficulty(theme, diff);
                questions = new PageImpl<>(filtered, pageable, filtered.size());
            } else {
                questions = Page.empty(pageable);
            }
        } else if (themeId != null) {
            // Filtrer par thème uniquement
            List<Question> filtered = questionRepository.findByThemeIdAndIsSoloQuestionTrue(themeId);
            questions = new PageImpl<>(filtered, pageable, filtered.size());
        } else if (difficulty != null) {
            // Filtrer par difficulté uniquement
            Question.Difficulty diff = Question.Difficulty.valueOf(difficulty);
            List<Question> filtered = questionRepository.findByDifficulty(diff);
            questions = new PageImpl<>(filtered, pageable, filtered.size());
        } else {
            // Toutes les questions
            questions = questionRepository.findAll(pageable);
        }

        return ResponseEntity.ok(questions);
    }

    @GetMapping("/questions/{id}")
    public ResponseEntity<Map<String, Object>> getQuestionDetails(@PathVariable Long id) {
        Question question = questionRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Question not found"));

        List<Answer> answers = answerRepository.findByQuestionIdOrderByAnswerOrder(id);

        Map<String, Object> result = new HashMap<>();
        result.put("question", question);
        result.put("answers", answers);

        return ResponseEntity.ok(result);
    }

    @PostMapping("/questions")
    public ResponseEntity<Question> createQuestion(@Valid @RequestBody Map<String, Object> request) {
        Long themeId = Long.valueOf(request.get("themeId").toString());
        String questionText = request.get("questionText").toString();
        String difficulty = request.get("difficulty").toString();

        @SuppressWarnings("unchecked")
        List<Map<String, Object>> answersData = (List<Map<String, Object>>) request.get("answers");

        Theme theme = themeRepository.findById(themeId)
            .orElseThrow(() -> new RuntimeException("Theme not found"));

        Question question = new Question();
        question.setTheme(theme);
        question.setQuestionText(questionText);
        question.setDifficulty(Question.Difficulty.valueOf(difficulty));
        question.setIsSoloQuestion(true);
        question.setIsGroupQuestion(true);

        // Assigner points selon difficulté
        switch (question.getDifficulty()) {
            case EASY:
                question.setPoints(10);
                break;
            case MEDIUM:
                question.setPoints(20);
                break;
            case HARD:
                question.setPoints(30);
                break;
        }

        Question savedQuestion = questionRepository.save(question);

        // Créer les réponses
        List<Answer> answers = new ArrayList<>();
        for (int i = 0; i < answersData.size(); i++) {
            Map<String, Object> answerData = answersData.get(i);
            Answer answer = new Answer();
            answer.setQuestion(savedQuestion);
            answer.setAnswerText(answerData.get("answerText").toString());
            answer.setIsCorrect(Boolean.parseBoolean(answerData.get("isCorrect").toString()));
            answer.setAnswerOrder(i + 1);
            answers.add(answer);
        }
        answerRepository.saveAll(answers);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedQuestion);
    }

    @PutMapping("/questions/{id}")
    public ResponseEntity<Question> updateQuestion(@PathVariable Long id, @Valid @RequestBody Map<String, Object> request) {
        Question question = questionRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Question not found"));

        if (request.containsKey("questionText")) {
            question.setQuestionText(request.get("questionText").toString());
        }
        if (request.containsKey("difficulty")) {
            Question.Difficulty newDifficulty = Question.Difficulty.valueOf(request.get("difficulty").toString());
            question.setDifficulty(newDifficulty);

            // Mettre à jour les points selon la nouvelle difficulté
            switch (newDifficulty) {
                case EASY:
                    question.setPoints(10);
                    break;
                case MEDIUM:
                    question.setPoints(20);
                    break;
                case HARD:
                    question.setPoints(30);
                    break;
            }
        }
        if (request.containsKey("themeId")) {
            Long themeId = Long.valueOf(request.get("themeId").toString());
            Theme theme = themeRepository.findById(themeId)
                .orElseThrow(() -> new RuntimeException("Theme not found"));
            question.setTheme(theme);
        }

        Question updatedQuestion = questionRepository.save(question);

        // Mettre à jour les réponses si fournies
        if (request.containsKey("answers")) {
            @SuppressWarnings("unchecked")
            List<Map<String, Object>> answersData = (List<Map<String, Object>>) request.get("answers");

            // Supprimer les anciennes réponses
            answerRepository.deleteByQuestionId(id);

            // Créer les nouvelles réponses
            List<Answer> answers = new ArrayList<>();
            for (int i = 0; i < answersData.size(); i++) {
                Map<String, Object> answerData = answersData.get(i);
                Answer answer = new Answer();
                answer.setQuestion(updatedQuestion);
                answer.setAnswerText(answerData.get("answerText").toString());
                answer.setIsCorrect(Boolean.parseBoolean(answerData.get("isCorrect").toString()));
                answer.setAnswerOrder(i + 1);
                answers.add(answer);
            }
            answerRepository.saveAll(answers);
        }

        return ResponseEntity.ok(updatedQuestion);
    }

    @DeleteMapping("/questions/{id}")
    public ResponseEntity<?> deleteQuestion(@PathVariable Long id) {
        // Supprimer d'abord les réponses associées
        answerRepository.deleteByQuestionId(id);
        // Puis supprimer la question
        questionRepository.deleteById(id);
        return ResponseEntity.ok(new MessageResponse("Question deleted successfully"));
    }

    @PostMapping("/questions/import")
    public ResponseEntity<?> importQuestions(@RequestParam("file") MultipartFile file) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
            String line;
            int count = 0;
            int errors = 0;

            // Ignorer la première ligne (en-tête)
            reader.readLine();

            while ((line = reader.readLine()) != null) {
                try {
                    String[] parts = line.split(",");
                    if (parts.length < 8) {
                        errors++;
                        continue;
                    }

                    Long themeId = Long.parseLong(parts[0].trim());
                    String questionText = parts[1].trim();
                    String difficulty = parts[2].trim();
                    String answer1 = parts[3].trim();
                    String answer2 = parts[4].trim();
                    String answer3 = parts[5].trim();
                    String answer4 = parts[6].trim();
                    int correctIndex = Integer.parseInt(parts[7].trim());

                    Theme theme = themeRepository.findById(themeId).orElse(null);
                    if (theme == null) {
                        errors++;
                        continue;
                    }

                    Question question = new Question();
                    question.setTheme(theme);
                    question.setQuestionText(questionText);
                    question.setDifficulty(Question.Difficulty.valueOf(difficulty.toUpperCase()));
                    question.setIsSoloQuestion(true);
                    question.setIsGroupQuestion(true);

                    // Assigner points selon difficulté
                    switch (question.getDifficulty()) {
                        case EASY:
                            question.setPoints(10);
                            break;
                        case MEDIUM:
                            question.setPoints(20);
                            break;
                        case HARD:
                            question.setPoints(30);
                            break;
                    }

                    question = questionRepository.save(question);

                    String[] answerTexts = {answer1, answer2, answer3, answer4};
                    for (int i = 0; i < 4; i++) {
                        Answer answer = new Answer();
                        answer.setQuestion(question);
                        answer.setAnswerText(answerTexts[i]);
                        answer.setIsCorrect(i == (correctIndex - 1));
                        answer.setAnswerOrder(i + 1);
                        answerRepository.save(answer);
                    }

                    count++;
                } catch (Exception e) {
                    errors++;
                    continue;
                }
            }

            reader.close();

            Map<String, Object> result = new HashMap<>();
            result.put("imported", count);
            result.put("errors", errors);
            result.put("message", "Imported " + count + " questions successfully with " + errors + " errors");

            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new MessageResponse("Error importing questions: " + e.getMessage()));
        }
    }

    // ================================================================================
    // GESTION DES UTILISATEURS
    // ================================================================================

    @GetMapping("/users")
    public ResponseEntity<Map<String, Object>> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<User> usersPage = userRepository.findAll(pageable);

        // Enrichir les données utilisateur avec scores et quiz
        List<Map<String, Object>> enrichedUsers = usersPage.getContent().stream().map(user -> {
            Map<String, Object> userMap = new HashMap<>();
            userMap.put("id", user.getId());
            userMap.put("username", user.getUsername());
            userMap.put("email", user.getEmail());
            userMap.put("firstName", user.getFirstName());
            userMap.put("lastName", user.getLastName());
            userMap.put("country", user.getCountry());
            userMap.put("roles", user.getRoles());
            userMap.put("createdAt", user.getCreatedAt());

            // Ajouter le score global
            UserGlobalScore globalScore = userGlobalScoreRepository.findByUserId(user.getId()).orElse(null);
            userMap.put("totalScore", globalScore != null ? globalScore.getTotalScore() : 0);

            // Ajouter le nombre de quiz
            long quizCount = soloQuizSessionRepository.findByUserIdOrderByCompletedAtDesc(user.getId()).size();
            userMap.put("quizCount", quizCount);

            return userMap;
        }).collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("content", enrichedUsers);
        response.put("totalElements", usersPage.getTotalElements());
        response.put("totalPages", usersPage.getTotalPages());
        response.put("currentPage", usersPage.getNumber());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<Map<String, Object>> getUserDetails(@PathVariable Long id) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found"));

        UserGlobalScore globalScore = userGlobalScoreRepository.findByUserId(id).orElse(null);
        List<SoloQuizSession> sessions = soloQuizSessionRepository.findByUserIdOrderByCompletedAtDesc(id);

        Map<String, Object> result = new HashMap<>();
        result.put("user", user);
        result.put("globalScore", globalScore);
        result.put("recentSessions", sessions.stream().limit(10).collect(Collectors.toList()));
        result.put("totalQuizzes", sessions.size());

        // Calculer le taux de réussite moyen
        if (!sessions.isEmpty()) {
            double avgSuccessRate = sessions.stream()
                .mapToDouble(s -> (double) s.getCorrectAnswers() / s.getTotalQuestions() * 100)
                .average()
                .orElse(0.0);
            result.put("averageSuccessRate", avgSuccessRate);
        } else {
            result.put("averageSuccessRate", 0.0);
        }

        return ResponseEntity.ok(result);
    }

    @PutMapping("/users/{id}/role")
    public ResponseEntity<?> updateUserRole(@PathVariable Long id, @RequestBody Map<String, String> request) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found"));

        String roleName = request.get("role");

        try {
            ERole eRole = ERole.valueOf(roleName);
            Role role = roleRepository.findByName(eRole)
                .orElseThrow(() -> new RuntimeException("Role not found"));

            // Ajouter le rôle si l'utilisateur ne l'a pas déjà
            if (!user.getRoles().contains(role)) {
                user.getRoles().add(role);
                userRepository.save(user);
            }

            return ResponseEntity.ok(new MessageResponse("User role updated successfully"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new MessageResponse("Invalid role: " + roleName));
        }
    }

    @PostMapping("/users/{id}/add-admin")
    public ResponseEntity<?> addAdminRole(@PathVariable Long id) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found"));

        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
            .orElseThrow(() -> new RuntimeException("Admin role not found"));

        if (!user.getRoles().contains(adminRole)) {
            user.getRoles().add(adminRole);
            userRepository.save(user);
            return ResponseEntity.ok(new MessageResponse("Admin role added to user: " + user.getUsername()));
        }

        return ResponseEntity.ok(new MessageResponse("User already has admin role"));
    }

    @DeleteMapping("/users/{id}/remove-admin")
    public ResponseEntity<?> removeAdminRole(@PathVariable Long id) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found"));

        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
            .orElseThrow(() -> new RuntimeException("Admin role not found"));

        user.getRoles().remove(adminRole);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("Admin role removed from user: " + user.getUsername()));
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found"));

        // Vérifier que l'utilisateur n'est pas en train de se supprimer lui-même
        // Cette logique pourrait être améliorée avec le contexte de sécurité

        userRepository.deleteById(id);
        return ResponseEntity.ok(new MessageResponse("User deleted successfully"));
    }

    @PostMapping("/users")
    public ResponseEntity<?> createUser(@RequestBody Map<String, String> request) {
        try {
            String username = request.get("username");
            String email = request.get("email");
            String password = request.get("password");
            String firstName = request.get("firstName");
            String lastName = request.get("lastName");
            String country = request.get("country");
            String roleStr = request.get("role");

            // Vérifier si username existe
            if (userRepository.existsByUsername(username)) {
                return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
            }

            // Vérifier si email existe
            if (userRepository.existsByEmail(email)) {
                return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
            }

            // Créer nouveau User avec mot de passe encodé
            User user = new User(username, email, passwordEncoder.encode(password));
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setCountry(country);

            // Assigner le rôle (par défaut ROLE_USER si non spécifié)
            Set<Role> roles = new HashSet<>();
            ERole roleName = (roleStr != null && !roleStr.isEmpty())
                ? ERole.valueOf(roleStr)
                : ERole.ROLE_USER;
            Role userRole = roleRepository.findByName(roleName)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
            user.setRoles(roles);

            // Sauvegarder
            userRepository.save(user);

            return ResponseEntity.ok(new MessageResponse("User created successfully!"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new MessageResponse("Error creating user: " + e.getMessage()));
        }
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody Map<String, String> request) {
        try {
            User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

            String username = request.get("username");
            String email = request.get("email");
            String password = request.get("password");
            String firstName = request.get("firstName");
            String lastName = request.get("lastName");
            String country = request.get("country");
            String roleStr = request.get("role");

            // Vérifier si le nouveau username est déjà pris par un autre utilisateur
            if (username != null && !username.equals(user.getUsername())) {
                if (userRepository.existsByUsername(username)) {
                    return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
                }
                user.setUsername(username);
            }

            // Vérifier si le nouveau email est déjà pris par un autre utilisateur
            if (email != null && !email.equals(user.getEmail())) {
                if (userRepository.existsByEmail(email)) {
                    return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
                }
                user.setEmail(email);
            }

            // Mettre à jour le mot de passe uniquement s'il est fourni
            if (password != null && !password.isEmpty()) {
                user.setPassword(passwordEncoder.encode(password));
            }

            // Mettre à jour les autres champs
            if (firstName != null) user.setFirstName(firstName);
            if (lastName != null) user.setLastName(lastName);
            if (country != null) user.setCountry(country);

            // Mettre à jour le rôle
            if (roleStr != null && !roleStr.isEmpty()) {
                Set<Role> roles = new HashSet<>();
                ERole roleName = ERole.valueOf(roleStr);
                Role role = roleRepository.findByName(roleName)
                    .orElseThrow(() -> new RuntimeException("Error: Role not found."));
                roles.add(role);
                user.setRoles(roles);
            }

            // Sauvegarder
            userRepository.save(user);

            return ResponseEntity.ok(new MessageResponse("User updated successfully!"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new MessageResponse("Error updating user: " + e.getMessage()));
        }
    }

    @PutMapping("/users/{id}/status")
    public ResponseEntity<?> updateUserStatus(@PathVariable Long id, @RequestBody Map<String, Boolean> request) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found"));



        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User status updated successfully"));
    }


    @GetMapping("/reports/themes")
    public ResponseEntity<List<Map<String, Object>>> getThemeReports() {
        List<Theme> themes = themeRepository.findAll();
        List<Map<String, Object>> reports = new ArrayList<>();

        for (Theme theme : themes) {
            Map<String, Object> report = new HashMap<>();
            report.put("theme", theme);

            // Nombre de questions
            long questionCount = questionRepository.findByThemeIdAndIsSoloQuestionTrue(theme.getId()).size();
            report.put("questionCount", questionCount);

            // Nombre de sessions
            long sessionCount = soloQuizSessionRepository.findAll().stream()
                .filter(s -> s.getTheme().getId().equals(theme.getId()))
                .count();
            report.put("sessionCount", sessionCount);

            // Score moyen
            double avgScore = soloQuizSessionRepository.findAll().stream()
                .filter(s -> s.getTheme().getId().equals(theme.getId()))
                .mapToDouble(s -> (double) s.getScore())
                .average()
                .orElse(0.0);
            report.put("averageScore", avgScore);

            reports.add(report);
        }

        return ResponseEntity.ok(reports);
    }

    @GetMapping("/reports/activity")
    public ResponseEntity<Map<String, Object>> getActivityReport(
            @RequestParam(required = false) String period) {

        Map<String, Object> report = new HashMap<>();
        LocalDate startDate;
        LocalDate endDate = LocalDate.now();

        // Déterminer la période
        if ("week".equals(period)) {
            startDate = endDate.minusDays(7);
        } else if ("month".equals(period)) {
            startDate = endDate.minusDays(30);
        } else {
            startDate = endDate.minusDays(365); // Par défaut: année
        }

        // Nouvelles inscriptions
        long newUsers = userRepository.findAll().stream()
            .filter(u -> !u.getCreatedAt().toLocalDate().isBefore(startDate))
            .count();
        report.put("newUsers", newUsers);

        // Quiz complétés
        long completedQuizzes = soloQuizSessionRepository.findAll().stream()
            .filter(s -> s.getCompletedAt() != null &&
                        !s.getCompletedAt().toLocalDate().isBefore(startDate))
            .count();
        report.put("completedQuizzes", completedQuizzes);

        // Utilisateurs actifs (ayant joué au moins un quiz)
        long activeUsers = soloQuizSessionRepository.findAll().stream()
            .filter(s -> s.getCompletedAt() != null &&
                        !s.getCompletedAt().toLocalDate().isBefore(startDate))
            .map(s -> s.getUser().getId())
            .distinct()
            .count();
        report.put("activeUsers", activeUsers);

        report.put("period", period != null ? period : "year");
        report.put("startDate", startDate);
        report.put("endDate", endDate);

        return ResponseEntity.ok(report);
    }

    // ================================================================================
    // MAINTENANCE ET RÉPARATION
    // ================================================================================

    @PostMapping("/fix-user-roles")
    public ResponseEntity<?> fixUserRoles() {
        try {
            List<User> allUsers = userRepository.findAll();
            int fixedCount = 0;

            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Role ROLE_USER not found"));

            for (User user : allUsers) {
                // Vérifier si l'utilisateur a le rôle USER
                boolean hasUserRole = user.getRoles().stream()
                    .anyMatch(role -> role.getName().equals(ERole.ROLE_USER));

                if (!hasUserRole) {
                    user.getRoles().add(userRole);
                    userRepository.save(user);
                    fixedCount++;
                }
            }

            Map<String, Object> result = new HashMap<>();
            result.put("totalUsers", allUsers.size());
            result.put("fixedUsers", fixedCount);
            result.put("message", "Fixed " + fixedCount + " users without ROLE_USER");

            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new MessageResponse("Error fixing user roles: " + e.getMessage()));
        }
    }
}
