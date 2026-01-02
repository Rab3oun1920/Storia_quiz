package org.storia.controllers;

import org.storia.entities.GroupQuizSession;
import org.storia.dto.GroupSessionRequest;
import org.storia.security.services.UserDetailsImpl;
import org.storia.services.GroupQuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/group")
@PreAuthorize("hasRole('USER')")
public class GroupQuizController {
    @Autowired
    private GroupQuizService groupQuizService;

    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> createSession(@Valid @RequestBody GroupSessionRequest request) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext()
            .getAuthentication().getPrincipal();
        Long userId = userDetails.getId();

        Map<String, Object> result = groupQuizService.createGroupSession(request, userId);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{sessionId}")
    public ResponseEntity<Map<String, Object>> getSessionDetails(@PathVariable Long sessionId) {
        return ResponseEntity.ok(groupQuizService.getSessionDetails(sessionId));
    }

    @GetMapping("/{sessionId}/start")
    public ResponseEntity<Map<String, Object>> startQuiz(@PathVariable Long sessionId) {
        return ResponseEntity.ok(groupQuizService.startGroupQuiz(sessionId));
    }

    @PostMapping("/check-answer")
    public ResponseEntity<Map<String, Object>> checkAnswer(@RequestBody Map<String, Object> request) {
        Long sessionId = Long.valueOf(request.get("sessionId").toString());
        Long groupId = Long.valueOf(request.get("groupId").toString());
        Long answerId = Long.valueOf(request.get("answerId").toString());
        int timeSpent = Integer.parseInt(request.get("timeSpent").toString());

        return ResponseEntity.ok(groupQuizService.checkGroupAnswer(sessionId, groupId, answerId, timeSpent));
    }

    @PostMapping("/complete-turn")
    public ResponseEntity<Map<String, Object>> completeGroupTurn(@RequestBody Map<String, Object> request) {
        Long sessionId = Long.valueOf(request.get("sessionId").toString());
        Long groupId = Long.valueOf(request.get("groupId").toString());

        return ResponseEntity.ok(groupQuizService.completeGroupTurn(sessionId, groupId));
    }

    @PostMapping("/next-group")
    public ResponseEntity<Map<String, Object>> moveToNextGroup(@RequestBody Map<String, Object> request) {
        Long sessionId = Long.valueOf(request.get("sessionId").toString());
        int currentGroupOrder = Integer.parseInt(request.get("currentGroupOrder").toString());

        return ResponseEntity.ok(groupQuizService.moveToNextGroup(sessionId, currentGroupOrder));
    }

    @PostMapping("/complete")
    public ResponseEntity<Map<String, Object>> completeSession(@RequestBody Map<String, Object> request) {
        Long sessionId = Long.valueOf(request.get("sessionId").toString());

        return ResponseEntity.ok(groupQuizService.completeSession(sessionId));
    }

    @GetMapping("/history")
    public ResponseEntity<List<Map<String, Object>>> getHistory() {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext()
            .getAuthentication().getPrincipal();
        Long userId = userDetails.getId();

        return ResponseEntity.ok(groupQuizService.getSessionHistory(userId));
    }

    @GetMapping("/rankings")
    public ResponseEntity<List<Map<String, Object>>> getRankings() {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext()
            .getAuthentication().getPrincipal();
        Long userId = userDetails.getId();

        return ResponseEntity.ok(groupQuizService.getGroupRankings(userId));
    }

    @GetMapping("/{sessionId}/results")
    public ResponseEntity<Map<String, Object>> getSessionResults(@PathVariable Long sessionId) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext()
            .getAuthentication().getPrincipal();
        Long userId = userDetails.getId();

        // Verify that the session belongs to this user
        Map<String, Object> sessionDetails = groupQuizService.getSessionDetails(sessionId);
        Map<String, Object> session = (Map<String, Object>) sessionDetails.get("session");

        Map<String, Object> result = new HashMap<>();
        result.put("sessionId", sessionId);
        result.put("sessionName", session.get("sessionName"));
        result.put("rankings", groupQuizService.getSessionRankings(sessionId));

        // Get winner (first in rankings)
        List<Map<String, Object>> rankings = (List<Map<String, Object>>) result.get("rankings");
        if (!rankings.isEmpty()) {
            result.put("winner", rankings.get(0));
        } else {
            result.put("winner", null);
        }

        return ResponseEntity.ok(result);
    }

    @PostMapping("/{sessionId}/reset")
    public ResponseEntity<Map<String, Object>> resetSession(@PathVariable Long sessionId) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext()
            .getAuthentication().getPrincipal();
        Long userId = userDetails.getId();

        groupQuizService.resetSession(sessionId, userId);

        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("message", "Session réinitialisée avec succès");

        return ResponseEntity.ok(result);
    }
}
