// Check authentication
checkAuth();

// Redirect admins to admin panel
if (isAdmin()) {
    window.location.href = '/admin.html';
}

const user = getUser();
document.getElementById('username').textContent = user.username;

// Logout handler
document.getElementById('logoutBtn').addEventListener('click', (e) => {
    e.preventDefault();
    logout();
});

// Load user stats
async function loadStats() {
    try {
        const stats = await apiCall('/rankings/user/stats');
        const globalScore = stats.globalScore;

        // Check if globalScore exists
        if (globalScore) {
            document.getElementById('totalQuizzes').textContent = globalScore.totalQuizzes || 0;
            document.getElementById('totalScore').textContent = globalScore.totalScore || 0;

            // Convert average score to percentage (averageScore is out of 200)
            const averagePercentage = globalScore.averageScore ? (globalScore.averageScore / 200) * 100 : 0;
            document.getElementById('averageScore').textContent = Math.round(averagePercentage) + '%';
        } else {
            // No stats yet
            document.getElementById('totalQuizzes').textContent = '0';
            document.getElementById('totalScore').textContent = '0';
            document.getElementById('averageScore').textContent = '0%';
        }

        document.getElementById('globalRank').textContent = stats.globalRank || '-';
    } catch (error) {
        console.error('Error loading stats:', error);
        // Set default values on error
        document.getElementById('totalQuizzes').textContent = '0';
        document.getElementById('totalScore').textContent = '0';
        document.getElementById('averageScore').textContent = '0%';
        document.getElementById('globalRank').textContent = '-';
    }
}

// Load recent quizzes
async function loadRecentQuizzes() {
    try {
        const quizzes = await apiCall('/user/recent-quizzes?limit=5');
        const container = document.getElementById('recentQuizzes');

        if (!quizzes || quizzes.length === 0) {
            container.innerHTML = '<p class="text-center text-muted">Aucun quiz récent</p>';
            return;
        }

        container.innerHTML = quizzes.map(quiz => `
            <div class="activity-item">
                <div class="activity-details">
                    <div class="activity-title">${quiz.themeName || 'Quiz'}</div>
                </div>
                <div class="activity-score">${quiz.score || 0} / 200</div>
            </div>
        `).join('');
    } catch (error) {
        console.error('Error loading recent quizzes:', error);
        document.getElementById('recentQuizzes').innerHTML =
            '<p class="text-center text-muted">Erreur de chargement</p>';
    }
}

// Initialize
loadStats();
loadRecentQuizzes();
