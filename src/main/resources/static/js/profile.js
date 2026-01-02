// Check authentication
checkAuth();

// Get userId from URL if admin is viewing another user's profile
const urlParams = new URLSearchParams(window.location.search);
const viewingUserId = urlParams.get('userId');
const isViewingOtherUser = viewingUserId && isAdmin();

let user = getUser();
let viewedUser = null;

// Check if user data exists
if (!user) {
    console.error('User data not found in localStorage');
    logout();
}

// If admin is viewing another user, load that user's data
if (isViewingOtherUser) {
    loadOtherUserProfile(viewingUserId);
}

// Theme icons mapping
const themeIcons = {
    'Histoire': '🏛️',
    'Géographie': '🌍',
    'Science': '🔬',
    'Culture Générale': '🎭',
    'Sport': '⚽',
    'Politique': '🏛️',
    'Art': '🎨',
    'Musique': '🎵',
    'Cinéma': '🎬',
    'Littérature': '📚'
};

// Logout handler
document.getElementById('logoutBtn').addEventListener('click', (e) => {
    e.preventDefault();
    logout();
});

// Load other user's profile (for admin viewing)
async function loadOtherUserProfile(userId) {
    try {
        const userData = await apiCall(`/admin/users/${userId}`);
        viewedUser = userData.user;
        displayUserInfo(viewedUser);

        // Set avatar initials
        const initials = viewedUser.firstName && viewedUser.lastName
            ? (viewedUser.firstName[0] + viewedUser.lastName[0]).toUpperCase()
            : viewedUser.username.substring(0, 2).toUpperCase();
        document.getElementById('avatarInitials').textContent = initials;

        // Hide edit button when viewing other user
        document.getElementById('editProfileBtn').style.display = 'none';

        // Add back to admin button
        const profileCard = document.querySelector('.profile-card');
        const backBtn = document.createElement('button');
        backBtn.className = 'btn btn-secondary';
        backBtn.textContent = 'Retour à l\'admin';
        backBtn.style.marginTop = '10px';
        backBtn.onclick = () => window.location.href = '/admin.html';
        profileCard.appendChild(backBtn);

        // Load stats for the viewed user
        loadStatsForUser(userId);
        loadRecentQuizzesForUser(userId);
    } catch (error) {
        console.error('Error loading user profile:', error);
        alert('Erreur lors du chargement du profil utilisateur');
        window.location.href = '/admin.html';
    }
}

// Display user info
function displayUserInfo(userData) {
    document.getElementById('displayName').textContent = `${userData.firstName || ''} ${userData.lastName || ''}`.trim() || userData.username;
    document.getElementById('username').textContent = '@' + userData.username;
    document.getElementById('email').textContent = userData.email || '';
    document.getElementById('country').textContent = userData.country || '';
}

// Only display current user info if not viewing another user
if (!isViewingOtherUser) {
    displayUserInfo(user);

    // Set avatar initials
    const initials = user.firstName && user.lastName
        ? (user.firstName[0] + user.lastName[0]).toUpperCase()
        : user.username.substring(0, 2).toUpperCase();
    document.getElementById('avatarInitials').textContent = initials;
}

// Load statistics for a specific user (admin viewing)
async function loadStatsForUser(userId) {
    try {
        const userData = await apiCall(`/admin/users/${userId}`);
        const globalScore = userData.globalScore;
        const totalQuizzes = userData.totalQuizzes || 0;

        document.getElementById('totalQuizzes').textContent = totalQuizzes;
        document.getElementById('totalScore').textContent = globalScore?.totalScore || 0;

        const average = totalQuizzes > 0 && globalScore
            ? Math.round((globalScore.totalScore / (totalQuizzes * 200)) * 100)
            : 0;
        document.getElementById('averageScore').textContent = average + '%';

        document.getElementById('globalRank').textContent = '-';
        document.getElementById('totalTime').textContent = '0s';

        // Hide theme stats for viewed users
        document.querySelector('.theme-stats').style.display = 'none';
    } catch (error) {
        console.error('Error loading stats:', error);
    }
}

// Load statistics
async function loadStats() {
    try {
        const stats = await apiCall('/user/stats');

        document.getElementById('totalQuizzes').textContent = stats.totalQuizzes || 0;
        document.getElementById('totalScore').textContent = stats.totalScore || 0;

        const average = stats.totalQuizzes > 0
            ? Math.round((stats.totalScore / (stats.totalQuizzes * 200)) * 100)
            : 0;
        document.getElementById('averageScore').textContent = average + '%';

        document.getElementById('globalRank').textContent = stats.globalRank || '-';
        document.getElementById('totalTime').textContent = stats.totalTime || 0;

    } catch (error) {
        console.error('Error loading stats:', error);
    }
}

// Load theme stats
async function loadThemeStats() {
    try {
        const themes = await apiCall('/themes');
        const container = document.getElementById('themeStatsContainer');

        if (!themes || themes.length === 0) {
            container.innerHTML = '<p class="text-center">Aucune statistique disponible</p>';
            return;
        }

        const statsPromises = themes.map(theme =>
            apiCall(`/user/theme-stats/${theme.id}`).catch(() => null)
        );

        const themeStats = await Promise.all(statsPromises);

        container.innerHTML = themes.map((theme, index) => {
            const stats = themeStats[index];
            if (!stats || stats.quizCount === 0) {
                return `
                    <div class="theme-stat-card glass-card">
                        <h4>${theme.name}</h4>
                        <p class="text-muted">Aucun quiz joué</p>
                    </div>
                `;
            }

            const average = Math.round((stats.totalScore / (stats.quizCount * 200)) * 100);

            return `
                <div class="theme-stat-card glass-card">
                    <h4>${theme.name}</h4>
                    <div class="theme-stat-item">
                        <span>Quiz joués:</span>
                        <strong>${stats.quizCount}</strong>
                    </div>
                    <div class="theme-stat-item">
                        <span>Score total:</span>
                        <strong>${stats.totalScore}</strong>
                    </div>
                    <div class="theme-stat-item">
                        <span>Moyenne:</span>
                        <strong>${average}%</strong>
                    </div>
                    <div class="theme-stat-item">
                        <span>Rang:</span>
                        <strong>${stats.themeRank || '-'}</strong>
                    </div>
                </div>
            `;
        }).join('');

    } catch (error) {
        console.error('Error loading theme stats:', error);
        document.getElementById('themeStatsContainer').innerHTML =
            '<p class="text-center">Erreur de chargement</p>';
    }
}

// Load recent quizzes for a specific user (admin viewing)
async function loadRecentQuizzesForUser(userId) {
    try {
        const userData = await apiCall(`/admin/users/${userId}`);
        const quizzes = userData.recentSessions || [];
        const container = document.getElementById('recentQuizzesContainer');

        if (!quizzes || quizzes.length === 0) {
            container.innerHTML = '<p class="text-center text-muted">Aucun quiz récent</p>';
            return;
        }

        container.innerHTML = quizzes.map(quiz => {
            const date = new Date(quiz.completedAt);
            const percentage = Math.round((quiz.score / 200) * 100);
            const themeName = quiz.theme?.name || 'Inconnu';
            const icon = themeIcons[themeName] || '📚';

            return `
                <div class="recent-quiz-item">
                    <div class="quiz-icon">${icon}</div>
                    <div class="quiz-info">
                        <div class="quiz-theme">${themeName}</div>
                        <div class="quiz-date">${date.toLocaleDateString('fr-FR')} à ${date.toLocaleTimeString('fr-FR', { hour: '2-digit', minute: '2-digit' })}</div>
                    </div>
                    <div class="quiz-result">
                        <div class="quiz-score">${quiz.score} / 200</div>
                        <div class="quiz-percentage">${percentage}%</div>
                    </div>
                </div>
            `;
        }).join('');

    } catch (error) {
        console.error('Error loading recent quizzes:', error);
        document.getElementById('recentQuizzesContainer').innerHTML =
            '<p class="text-center">Erreur de chargement</p>';
    }
}

// Load recent quizzes
async function loadRecentQuizzes() {
    try {
        const quizzes = await apiCall('/user/recent-quizzes?limit=10');
        const container = document.getElementById('recentQuizzesContainer');

        if (!quizzes || quizzes.length === 0) {
            container.innerHTML = '<p class="text-center text-muted">Aucun quiz récent</p>';
            return;
        }

        container.innerHTML = quizzes.map(quiz => {
            const date = new Date(quiz.completedAt);
            const percentage = Math.round((quiz.score / 200) * 100);
            const icon = themeIcons[quiz.themeName] || '📚';

            return `
                <div class="recent-quiz-item">
                    <div class="quiz-icon">${icon}</div>
                    <div class="quiz-info">
                        <div class="quiz-theme">${quiz.themeName}</div>
                        <div class="quiz-date">${date.toLocaleDateString('fr-FR')} à ${date.toLocaleTimeString('fr-FR', { hour: '2-digit', minute: '2-digit' })}</div>
                    </div>
                    <div class="quiz-result">
                        <div class="quiz-score">${quiz.score} / 200</div>
                        <div class="quiz-percentage">${percentage}%</div>
                    </div>
                </div>
            `;
        }).join('');

    } catch (error) {
        console.error('Error loading recent quizzes:', error);
        document.getElementById('recentQuizzesContainer').innerHTML =
            '<p class="text-center">Erreur de chargement</p>';
    }
}

// Modal management
const modal = document.getElementById('editProfileModal');
const editBtn = document.getElementById('editProfileBtn');
const closeBtn = document.getElementById('closeModal');
const cancelBtn = document.getElementById('cancelEditBtn');
const editForm = document.getElementById('editProfileForm');

// Open modal
editBtn.addEventListener('click', () => {
    // Pre-fill form with current user data
    document.getElementById('editFirstName').value = user.firstName || '';
    document.getElementById('editLastName').value = user.lastName || '';
    document.getElementById('editEmail').value = user.email || '';
    document.getElementById('editCountry').value = user.country || '';

    modal.style.display = 'block';
    document.getElementById('editMessage').textContent = '';
    document.getElementById('editMessage').className = 'message';
});

// Close modal
function closeModal() {
    modal.style.display = 'none';
}

closeBtn.addEventListener('click', closeModal);
cancelBtn.addEventListener('click', closeModal);

// Close modal when clicking outside
window.addEventListener('click', (e) => {
    if (e.target === modal) {
        closeModal();
    }
});

// Handle form submission
editForm.addEventListener('submit', async (e) => {
    e.preventDefault();

    const messageDiv = document.getElementById('editMessage');
    messageDiv.textContent = '';
    messageDiv.className = 'message';

    const formData = {
        firstName: document.getElementById('editFirstName').value,
        lastName: document.getElementById('editLastName').value,
        email: document.getElementById('editEmail').value,
        country: document.getElementById('editCountry').value
    };

    try {
        const updatedUser = await apiCall('/user/profile', {
            method: 'PUT',
            body: JSON.stringify(formData)
        });

        // Update user in localStorage
        const currentUser = getUser();
        const newUserData = {
            ...currentUser,
            firstName: updatedUser.firstName,
            lastName: updatedUser.lastName,
            email: updatedUser.email,
            country: updatedUser.country
        };
        localStorage.setItem('user', JSON.stringify(newUserData));

        // Update global user variable
        Object.assign(user, newUserData);

        // Update display
        displayUserInfo(newUserData);

        // Update avatar initials
        const initials = newUserData.firstName && newUserData.lastName
            ? (newUserData.firstName[0] + newUserData.lastName[0]).toUpperCase()
            : newUserData.username.substring(0, 2).toUpperCase();
        document.getElementById('avatarInitials').textContent = initials;

        // Show success message
        messageDiv.textContent = 'Profil mis à jour avec succès !';
        messageDiv.className = 'message success';

        // Close modal after 2 seconds
        setTimeout(() => {
            closeModal();
        }, 2000);

    } catch (error) {
        console.error('Error updating profile:', error);
        messageDiv.textContent = error.message || 'Erreur lors de la mise à jour du profil';
        messageDiv.className = 'message error';
    }
});

// Initialize - only load if not viewing another user
if (!isViewingOtherUser) {
    loadStats();
    loadThemeStats();
    loadRecentQuizzes();
}
