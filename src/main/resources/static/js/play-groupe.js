// Check authentication
checkAuth();

// Get session ID from URL
const urlParams = new URLSearchParams(window.location.search);
const sessionId = urlParams.get('sessionId');

if (!sessionId) {
    window.location.href = '/quiz-groupe.html';
}

// Quiz state
let session = null;
let currentQuestionIndex = 0;
let currentGroupIndex = 0;
let timer = null;
let timeRemaining = 5;
let questionStartTime = 0;

// Start session
async function startSession() {
    try {
        session = await apiCall(`/group/${sessionId}/start`);

        // Validation des données de session
        if (!session || !session.name || !session.groups || !session.questions) {
            throw new Error('Invalid session data');
        }

        // Initialiser les scores à 0 si non définis
        session.groups.forEach(group => {
            if (typeof group.score !== 'number') {
                group.score = 0;
            }
        });

        document.getElementById('sessionName').textContent = session.name;

        displayGroupScores();
        loadQuestion();
    } catch (error) {
        console.error('Error starting session:', error);
        alert('Erreur lors du démarrage de la session');
        window.location.href = '/quiz-groupe.html';
    }
}

// Display group scores
function displayGroupScores() {
    const container = document.getElementById('groupScoresContainer');

    container.innerHTML = session.groups.map((group, index) => {
        const groupName = group.name || `Groupe ${index + 1}`;
        const groupScore = typeof group.score === 'number' ? group.score : 0;

        return `
            <div class="group-score ${index === currentGroupIndex ? 'active' : ''}">
                <div class="group-name">${groupName}</div>
                <div class="group-points">${groupScore} pts</div>
            </div>
        `;
    }).join('');
}

// Load current question
function loadQuestion() {
    if (currentQuestionIndex >= session.questions.length) {
        // Current group has finished all questions
        showNextGroupButton();
        return;
    }

    const question = session.questions[currentQuestionIndex];
    const currentGroup = session.groups[currentGroupIndex];

    // Update UI
    document.getElementById('currentGroupName').textContent = currentGroup.name || `Groupe ${currentGroupIndex + 1}`;
    document.getElementById('currentRound').textContent = currentQuestionIndex + 1;
    document.getElementById('questionText').textContent = question.questionText;

    // Hide next button and feedback
    document.getElementById('nextGroupBtn').style.display = 'none';
    document.getElementById('feedback').style.display = 'none';

    // Shuffle and display answers (already shuffled by backend)
    const answersContainer = document.getElementById('answersContainer');
    answersContainer.innerHTML = question.answers.map((answer) => `
        <button class="answer-btn" onclick="selectAnswer(${answer.id})">
            ${answer.answerText}
        </button>
    `).join('');

    // Update group scores display
    displayGroupScores();

    // Start timer
    startTimer();
}

// Show next group button
function showNextGroupButton() {
    const currentGroup = session.groups[currentGroupIndex];

    // Hide question interface
    document.getElementById('answersContainer').innerHTML = '';
    document.getElementById('questionText').textContent = `${currentGroup.name || 'Le groupe'} a terminé ses questions !`;

    // Show next button
    const nextBtn = document.getElementById('nextGroupBtn');
    const isLastGroup = currentGroupIndex >= session.groups.length - 1;

    nextBtn.querySelector('button').textContent = isLastGroup ? 'Voir les résultats' : 'Groupe suivant';
    nextBtn.querySelector('button').onclick = nextGroup;
    nextBtn.style.display = 'block';

    // Show feedback
    const feedback = document.getElementById('feedback');
    feedback.className = 'feedback feedback-correct';
    feedback.textContent = `Score du groupe: ${currentGroup.score || 0} points`;
    feedback.style.display = 'block';
}

// Start timer
function startTimer() {
    questionStartTime = Date.now();
    timeRemaining = session.timePerQuestion || 5;
    document.getElementById('timer').textContent = timeRemaining;
    document.getElementById('timerProgress').style.width = '100%';

    clearInterval(timer);
    timer = setInterval(() => {
        timeRemaining--;
        document.getElementById('timer').textContent = timeRemaining;

        const progress = (timeRemaining / (session.timePerQuestion || 5)) * 100;
        document.getElementById('timerProgress').style.width = progress + '%';

        if (timeRemaining <= 0) {
            clearInterval(timer);
            selectAnswer(null); // Time's up, no answer selected
        }
    }, 1000);
}

// Select answer
async function selectAnswer(answerId) {
    clearInterval(timer);

    // Calculate time spent in seconds
    const timeSpent = Math.floor((Date.now() - questionStartTime) / 1000);

    // Disable all buttons
    const buttons = document.querySelectorAll('.answer-btn');
    buttons.forEach(btn => btn.disabled = true);

    try {
        const question = session.questions[currentQuestionIndex];
        const currentGroup = session.groups[currentGroupIndex];

        // If no answer selected (time's up), use the first answer ID as dummy
        const selectedAnswerId = answerId || question.answers[0].id;

        const response = await apiCall('/group/check-answer', {
            method: 'POST',
            body: JSON.stringify({
                sessionId: session.sessionId,
                groupId: currentGroup.id,
                answerId: selectedAnswerId,
                timeSpent: timeSpent
            })
        });

        // Update group score
        const pointsEarned = response.points || 0;
        currentGroup.score += pointsEarned;

        // Show feedback
        const feedback = document.getElementById('feedback');
        if (response.correct) {
            feedback.className = 'feedback feedback-correct';
            feedback.textContent = `Correct ! +${pointsEarned} points`;

            // Highlight correct answer
            buttons.forEach(btn => {
                if (btn.onclick && btn.onclick.toString().includes(answerId)) {
                    btn.classList.add('correct');
                }
            });
        } else {
            feedback.className = 'feedback feedback-wrong';
            feedback.textContent = answerId ? 'Mauvaise réponse' : 'Temps écoulé !';

            // Highlight wrong answer if one was selected
            if (answerId) {
                buttons.forEach(btn => {
                    if (btn.onclick && btn.onclick.toString().includes(answerId)) {
                        btn.classList.add('wrong');
                    }
                });
            }
        }
        feedback.style.display = 'block';

        // Update scores display
        displayGroupScores();

        // Show next button
        const nextBtn = document.getElementById('nextGroupBtn');
        nextBtn.style.display = 'block';
        nextBtn.querySelector('button').textContent = 'Question suivante';
        nextBtn.querySelector('button').onclick = () => {
            currentQuestionIndex++;
            loadQuestion();
        };

    } catch (error) {
        console.error('Error checking answer:', error);
        alert('Erreur lors de la vérification de la réponse');
    }
}

// Move to next group
async function nextGroup() {
    try {
        const currentGroup = session.groups[currentGroupIndex];

        // Complete current group's turn and save scores
        await apiCall('/group/complete-turn', {
            method: 'POST',
            body: JSON.stringify({
                sessionId: session.sessionId,
                groupId: currentGroup.id
            })
        });

        // Move to next group
        currentGroupIndex++;
        currentQuestionIndex = 0; // Reset question index for new group

        // Check if all groups have finished
        if (currentGroupIndex >= session.groups.length) {
            completeSession();
            return;
        }

        // Load questions for next group
        const nextGroupData = await apiCall('/group/next-group', {
            method: 'POST',
            body: JSON.stringify({
                sessionId: session.sessionId,
                currentGroupOrder: currentGroup.order
            })
        });

        // Update session with new questions
        session.questions = nextGroupData.questions;

        // Load first question for next group
        loadQuestion();

    } catch (error) {
        console.error('Error moving to next group:', error);
        alert('Erreur lors du passage au groupe suivant');
    }
}

// Complete session
async function completeSession() {
    try {
        const result = await apiCall('/group/complete', {
            method: 'POST',
            body: JSON.stringify({
                sessionId: session.sessionId
            })
        });

        // Store result for result page
        sessionStorage.setItem('groupResult', JSON.stringify({
            ...result,
            sessionName: session.name
        }));

        window.location.href = '/result-groupe.html';
    } catch (error) {
        console.error('Error completing session:', error);
        alert('Erreur lors de la finalisation de la session');
    }
}

// Initialize
startSession();
