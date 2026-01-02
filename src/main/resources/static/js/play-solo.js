// Check authentication
checkAuth();

// Get theme ID from URL
const urlParams = new URLSearchParams(window.location.search);
const themeId = urlParams.get('themeId');

if (!themeId) {
    window.location.href = '/quiz-solo.html';
}

// Quiz state
let quizSession = null;
let currentQuestionIndex = 0;
let currentScore = 0;
let correctAnswersCount = 0;
let timer = null;
let timeRemaining = 10;
let startTime = Date.now();

// Start quiz
async function startQuiz() {
    try {
        quizSession = await apiCall(`/solo/${themeId}/start`);

        document.getElementById('themeName').textContent = quizSession.themeName;
        document.getElementById('totalQuestions').textContent = quizSession.totalQuestions;

        loadQuestion();
    } catch (error) {
        console.error('Error starting quiz:', error);
        alert('Erreur lors du démarrage du quiz');
        window.location.href = '/quiz-solo.html';
    }
}

// Load current question
function loadQuestion() {
    if (currentQuestionIndex >= quizSession.questions.length) {
        completeQuiz();
        return;
    }

    const question = quizSession.questions[currentQuestionIndex];

    document.getElementById('currentQuestion').textContent = currentQuestionIndex + 1;
    document.getElementById('questionText').textContent = question.questionText;
    document.getElementById('currentScore').textContent = currentScore;

    // Update progress bar
    const progress = ((currentQuestionIndex + 1) / quizSession.totalQuestions) * 100;
    document.getElementById('progressBar').style.width = progress + '%';

    // Display answers (already shuffled by backend)
    const answersContainer = document.getElementById('answersContainer');
    answersContainer.innerHTML = question.answers.map((answer) => `
        <button class="answer-btn" onclick="selectAnswer(${answer.id})">
            ${answer.answerText}
        </button>
    `).join('');

    // Start timer
    startTimer();
}

// Start timer
function startTimer() {
    timeRemaining = 10;
    document.getElementById('timer').textContent = timeRemaining;
    document.getElementById('timerProgress').style.width = '100%';

    clearInterval(timer);
    timer = setInterval(() => {
        timeRemaining--;
        document.getElementById('timer').textContent = timeRemaining;

        const progress = (timeRemaining / 10) * 100;
        document.getElementById('timerProgress').style.width = progress + '%';

        if (timeRemaining <= 0) {
            clearInterval(timer);
            // Time's up - select first answer as a timeout penalty
            const question = quizSession.questions[currentQuestionIndex];
            if (question.answers && question.answers.length > 0) {
                selectAnswer(question.answers[0].id);
            }
        }
    }, 1000);
}

// Select answer
async function selectAnswer(answerId) {
    clearInterval(timer);

    // Disable all buttons
    const buttons = document.querySelectorAll('.answer-btn');
    buttons.forEach(btn => btn.disabled = true);

    try {
        const response = await apiCall('/solo/check-answer', {
            method: 'POST',
            body: JSON.stringify({
                answerId: answerId
            })
        });

        // Show feedback
        const feedback = document.getElementById('feedback');
        const isCorrect = response.correct;
        const points = response.points;

        if (isCorrect) {
            feedback.className = 'feedback feedback-correct';
            feedback.textContent = `✓ Correct ! +${points} points`;
            currentScore += points;
            correctAnswersCount++;

            // Highlight correct answer
            buttons.forEach(btn => {
                if (btn.onclick.toString().includes(answerId)) {
                    btn.classList.add('correct');
                }
            });
        } else {
            feedback.className = 'feedback feedback-wrong';
            feedback.textContent = '✗ Mauvaise réponse';

            // Highlight wrong answer and show correct one
            const question = quizSession.questions[currentQuestionIndex];
            buttons.forEach((btn) => {
                const btnAnswerId = question.answers.find(a =>
                    btn.textContent.trim() === a.answerText
                )?.id;

                if (btnAnswerId === answerId) {
                    btn.classList.add('wrong');
                }
            });
        }
        feedback.style.display = 'block';

        // Next question after delay
        setTimeout(() => {
            feedback.style.display = 'none';
            currentQuestionIndex++;
            loadQuestion();
        }, 2000);

    } catch (error) {
        console.error('Error checking answer:', error);
        alert('Erreur lors de la vérification de la réponse');
    }
}

// Complete quiz
async function completeQuiz() {
    try {
        const totalTime = Math.floor((Date.now() - startTime) / 1000);

        const result = await apiCall('/solo/complete', {
            method: 'POST',
            body: JSON.stringify({
                themeId: parseInt(themeId),
                score: currentScore,
                correctAnswers: correctAnswersCount,
                totalQuestions: quizSession.totalQuestions,
                totalTime: totalTime
            })
        });

        // Store result in sessionStorage for result page
        sessionStorage.setItem('quizResult', JSON.stringify({
            ...result,
            score: currentScore,
            correctAnswers: correctAnswersCount,
            totalQuestions: quizSession.totalQuestions,
            themeId: themeId,
            themeName: quizSession.themeName || 'Quiz'
        }));

        window.location.href = '/result-solo.html';
    } catch (error) {
        console.error('Error completing quiz:', error);
        alert('Erreur lors de la finalisation du quiz');
    }
}

// Initialize
startQuiz();
