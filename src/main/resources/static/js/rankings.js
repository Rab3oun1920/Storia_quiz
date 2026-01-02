// Check authentication
checkAuth();

const user = getUser();

// Logout handler
document.getElementById('logoutBtn').addEventListener('click', (e) => {
    e.preventDefault();
    logout();
});

// Tab state
let currentTab = 'global';
let currentThemeId = null;
let currentPage = 0;
const pageSize = 20;

// Tab switching
document.querySelectorAll('.tab-btn').forEach(btn => {
    btn.addEventListener('click', () => {
        document.querySelectorAll('.tab-btn').forEach(b => b.classList.remove('active'));
        btn.classList.add('active');

        currentTab = btn.dataset.tab;
        currentPage = 0;

        if (currentTab === 'theme') {
            document.getElementById('themeSelector').style.display = 'block';
            loadThemes();
        } else {
            document.getElementById('themeSelector').style.display = 'none';
            loadRankings();
        }
    });
});

// Load themes for selector
async function loadThemes() {
    try {
        const themes = await apiCall('/solo/themes');
        const select = document.getElementById('themeSelect');

        select.innerHTML = '<option value="">Sélectionnez un thème</option>' +
            themes.map(theme => `<option value="${theme.id}">${theme.name}</option>`).join('');

        select.addEventListener('change', (e) => {
            currentThemeId = e.target.value;
            currentPage = 0;
            if (currentThemeId) {
                loadRankings();
            }
        });
    } catch (error) {
        console.error('Error loading themes:', error);
    }
}

// Load rankings
async function loadRankings() {
    const tbody = document.getElementById('rankingsBody');
    tbody.innerHTML = '<tr><td colspan="5" class="text-center"><div class="loading-spinner"></div></td></tr>';

    try {
        let response;
        if (currentTab === 'global') {
            response = await apiCall(`/rankings/global?page=${currentPage}&size=${pageSize}`);
        } else {
            if (!currentThemeId) {
                tbody.innerHTML = '<tr><td colspan="5" class="text-center">Sélectionnez un thème</td></tr>';
                return;
            }
            response = await apiCall(`/rankings/theme/${currentThemeId}?page=${currentPage}&size=${pageSize}`);
        }

        // Handle both array and paginated object responses
        const rankings = Array.isArray(response) ? response : (response.content || []);

        if (!rankings || rankings.length === 0) {
            tbody.innerHTML = '<tr><td colspan="5" class="text-center">Aucun classement disponible</td></tr>';
            return;
        }

        tbody.innerHTML = rankings.map((ranking, index) => {
            const rank = (currentPage * pageSize) + index + 1;

            // Validation stricte de toutes les valeurs
            const username = ranking.username || ranking.user?.username || 'Utilisateur inconnu';
            const totalScore = typeof ranking.totalScore === 'number' ? ranking.totalScore : 0;
            const quizCount = typeof ranking.totalQuizzes === 'number' ? ranking.totalQuizzes :
                             (typeof ranking.quizzesPlayed === 'number' ? ranking.quizzesPlayed : 0);
            const avgScore = typeof ranking.averageScore === 'number' ? ranking.averageScore : 0;

            const isCurrentUser = username === user.username;
            const rowClass = isCurrentUser ? 'highlight-row' : '';

            const medal = rank === 1 ? '🥇' : rank === 2 ? '🥈' : rank === 3 ? '🥉' : rank;

            return `
                <tr class="${rowClass}">
                    <td><strong>${medal}</strong></td>
                    <td>${username}${isCurrentUser ? ' (Vous)' : ''}</td>
                    <td>${totalScore}</td>
                    <td>${quizCount}</td>
                    <td>${Math.round(avgScore)}%</td>
                </tr>
            `;
        }).join('');

        renderPagination(rankings.length === pageSize);
    } catch (error) {
        console.error('Error loading rankings:', error);
        tbody.innerHTML = '<tr><td colspan="5" class="text-center">Erreur de chargement</td></tr>';
    }
}

// Render pagination
function renderPagination(hasMore) {
    const pagination = document.getElementById('pagination');

    let html = '<div class="pagination-buttons">';

    if (currentPage > 0) {
        html += `<button class="btn btn-outline" onclick="changePage(${currentPage - 1})">Précédent</button>`;
    }

    html += `<span class="page-info">Page ${currentPage + 1}</span>`;

    if (hasMore) {
        html += `<button class="btn btn-outline" onclick="changePage(${currentPage + 1})">Suivant</button>`;
    }

    html += '</div>';
    pagination.innerHTML = html;
}

// Change page
function changePage(page) {
    currentPage = page;
    loadRankings();
}

// Initialize
loadRankings();
