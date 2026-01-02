// Check authentication
checkAuth();

// Logout handler
document.getElementById('logoutBtn').addEventListener('click', (e) => {
    e.preventDefault();
    logout();
});

// Theme icons mapping
const themeIcons = {
    'Histoire': '🏛️',
    'Géographie': '🌍',
    'Science': '🔬',
    'Culture Générale': '🎭',
    'Sport': '⚽'
};

// Load themes
async function loadThemes() {
    try {
        const themes = await apiCall('/solo/themes');
        const container = document.getElementById('themesContainer');

        if (!themes || themes.length === 0) {
            container.innerHTML = '<p class="text-center">Aucun thème disponible</p>';
            return;
        }

        container.innerHTML = themes.map(theme => {
            const icon = themeIcons[theme.name] || '📚';
            return `
                <div class="theme-card glass-card fade-in" onclick="selectTheme(${theme.id})">
                    <div class="theme-icon">${icon}</div>
                    <h3>${theme.name}</h3>
                    <p>${theme.questionCount || 0} questions</p>
                    <div class="btn btn-primary">Commencer</div>
                </div>
            `;
        }).join('');
    } catch (error) {
        console.error('Error loading themes:', error);
        document.getElementById('errorMessage').textContent = 'Erreur lors du chargement des thèmes';
        document.getElementById('errorMessage').style.display = 'block';
        document.getElementById('themesContainer').innerHTML = '';
    }
}

// Select theme and start quiz
function selectTheme(themeId) {
    window.location.href = `/play-solo.html?themeId=${themeId}`;
}

// Initialize
loadThemes();
