// Check admin authentication
checkAdminAuth();

// Display admin username
const user = getUser();
if (user) {
    document.getElementById('adminUsername').textContent = user.username;
}

// Logout handler
document.getElementById('logoutBtn').addEventListener('click', (e) => {
    e.preventDefault();
    logout();
});

// Tab state
let currentTab = 'dashboard';

// Tab switching
document.querySelectorAll('.tab-btn').forEach(btn => {
    btn.addEventListener('click', () => {
        document.querySelectorAll('.tab-btn').forEach(b => b.classList.remove('active'));
        btn.classList.add('active');

        document.querySelectorAll('.tab-content').forEach(content => {
            content.style.display = 'none';
        });

        currentTab = btn.dataset.tab;
        const tabId = currentTab + 'Tab';
        document.getElementById(tabId).style.display = 'block';

        loadTabContent(currentTab);
    });
});

// Function to switch tabs programmatically
function switchTab(tabName) {
    const tabBtn = document.querySelector(`[data-tab="${tabName}"]`);
    if (tabBtn) {
        tabBtn.click();
    }
}

// Load tab content
function loadTabContent(tab) {
    switch (tab) {
        case 'dashboard':
            loadDashboard();
            break;
        case 'stats':
            loadStatistics();
            break;
        case 'themes':
            loadThemes();
            break;
        case 'questions':
            loadQuestions();
            break;
        case 'users':
            loadUsers();
            break;
        case 'reclamations':
            loadReclamations();
            break;
    }
}

// DASHBOARD TAB
async function loadDashboard() {
    try {
        const stats = await apiCall('/admin/statistics');

        // Update dashboard stats
        document.getElementById('dash-totalUsers').textContent = stats.totalUsers || 0;
        document.getElementById('dash-totalThemes').textContent = stats.activeThemes || 0;
        document.getElementById('dash-totalQuestions').textContent = stats.totalQuestions || 0;
        const totalQuizzes = (stats.totalQuizzesSolo || 0) + (stats.totalQuizzesGroup || 0);
        document.getElementById('dash-totalQuizzes').textContent = totalQuizzes;

        // Load recent activity and rankings
        await loadRecentActivity();
        await loadPlayerRankings();
    } catch (error) {
        console.error('Error loading dashboard:', error);
    }
}

// Load recent activity
async function loadRecentActivity() {
    try {
        const activityContainer = document.getElementById('recentActivity');

        // Get recent registrations and quizzes
        const users = await apiCall('/admin/users?page=0&size=5');
        const recentUsers = users.content || [];

        if (recentUsers.length === 0) {
            activityContainer.innerHTML = '<p class="text-center text-muted">Aucune activité récente</p>';
            return;
        }

        activityContainer.innerHTML = recentUsers.map(user => {
            const date = new Date(user.createdAt);
            return `
                <div class="activity-item">
                    <div class="activity-icon"></div>
                    <div class="activity-details">
                        <div class="activity-title">Nouvel utilisateur: ${user.username}</div>
                        <div class="activity-meta">${date.toLocaleDateString('fr-FR')} - ${user.email}</div>
                    </div>
                </div>
            `;
        }).join('');
    } catch (error) {
        console.error('Error loading recent activity:', error);
        document.getElementById('recentActivity').innerHTML = '<p class="text-center">Erreur de chargement</p>';
    }
}

// Load player rankings
async function loadPlayerRankings() {
    try {
        const rankingsContainer = document.getElementById('playerRankings');

        // Get rankings from API
        const response = await apiCall('/rankings/global');
        const rankings = response.content || [];

        if (!rankings || rankings.length === 0) {
            rankingsContainer.innerHTML = '<p class="text-center text-muted">Aucun joueur classé</p>';
            return;
        }

        // Display top 10 players
        const topPlayers = rankings.slice(0, 10);

        rankingsContainer.innerHTML = `
            <table class="rankings-table">
                <thead>
                    <tr>
                        <th>Rang</th>
                        <th>Joueur</th>
                        <th>Score</th>
                        <th>Quiz</th>
                    </tr>
                </thead>
                <tbody>
                    ${topPlayers.map((player, index) => `
                        <tr class="ranking-item ${index < 3 ? 'top-three' : ''}">
                            <td class="rank">#${index + 1}</td>
                            <td class="player-name">${player.username}</td>
                            <td class="score">${player.totalScore}</td>
                            <td class="quizzes">${player.quizCount}</td>
                        </tr>
                    `).join('')}
                </tbody>
            </table>
        `;
    } catch (error) {
        console.error('Error loading player rankings:', error);
        document.getElementById('playerRankings').innerHTML = '<p class="text-center">Erreur de chargement</p>';
    }
}

// STATISTICS TAB
async function loadStatistics() {
    try {
        const stats = await apiCall('/admin/statistics');

        document.getElementById('totalUsers').textContent = stats.totalUsers || 0;
        document.getElementById('totalThemes').textContent = stats.activeThemes || 0;
        document.getElementById('totalQuestions').textContent = stats.totalQuestions || 0;
        const totalQuizzes = (stats.totalQuizzesSolo || 0) + (stats.totalQuizzesGroup || 0);
        document.getElementById('totalQuizzes').textContent = totalQuizzes;
    } catch (error) {
        console.error('Error loading statistics:', error);
    }
}

// THEMES TAB
async function loadThemes() {
    try {
        const themes = await apiCall('/admin/themes');
        const tbody = document.getElementById('themesTableBody');

        if (!themes || themes.length === 0) {
            tbody.innerHTML = '<tr><td colspan="4" class="text-center">Aucun thème</td></tr>';
            return;
        }

        tbody.innerHTML = themes.map(theme => `
            <tr>
                <td>${theme.id}</td>
                <td>${theme.name}</td>
                <td>${theme.questionCount || 0}</td>
                <td>
                    <button class="btn btn-sm btn-outline" onclick="editTheme(${theme.id})">Modifier</button>
                    <button class="btn btn-sm btn-danger" onclick="deleteTheme(${theme.id})">Supprimer</button>
                </td>
            </tr>
        `).join('');
    } catch (error) {
        console.error('Error loading themes:', error);
    }
}

// Add theme button
document.getElementById('addThemeBtn').addEventListener('click', () => {
    document.getElementById('themeId').value = '';
    document.getElementById('themeName').value = '';
    document.getElementById('themeModalTitle').textContent = 'Ajouter un thème';
    openModal('themeModal');
});

// Theme form submission
document.getElementById('themeForm').addEventListener('submit', async (e) => {
    e.preventDefault();

    const id = document.getElementById('themeId').value;
    const name = document.getElementById('themeName').value;

    try {
        if (id) {
            await apiCall(`/admin/themes/${id}`, {
                method: 'PUT',
                body: JSON.stringify({ name })
            });
        } else {
            await apiCall('/admin/themes', {
                method: 'POST',
                body: JSON.stringify({ name })
            });
        }

        closeModal('themeModal');
        loadThemes();
    } catch (error) {
        console.error('Error saving theme:', error);
        alert('Erreur: ' + error.message);
    }
});

// Edit theme
async function editTheme(id) {
    try {
        const themes = await apiCall('/admin/themes');
        const theme = themes.find(t => t.id === id);

        if (!theme) {
            alert('Thème non trouvé');
            return;
        }

        document.getElementById('themeId').value = theme.id;
        document.getElementById('themeName').value = theme.name;
        document.getElementById('themeModalTitle').textContent = 'Modifier le thème';
        openModal('themeModal');
    } catch (error) {
        console.error('Error loading theme:', error);
        alert('Erreur: ' + error.message);
    }
}

// Delete theme
async function deleteTheme(id) {
    if (!confirm('Êtes-vous sûr de vouloir supprimer ce thème ?')) {
        return;
    }

    try {
        await apiCall(`/admin/themes/${id}`, { method: 'DELETE' });
        loadThemes();
    } catch (error) {
        console.error('Error deleting theme:', error);
        alert('Erreur: ' + error.message);
    }
}

// QUESTIONS TAB
let questionsPage = 0;
const questionsPageSize = 20;

async function loadQuestions(page = 0) {
    try {
        questionsPage = page;
        const filterTheme = document.getElementById('filterTheme').value;

        let endpoint = `/admin/questions?page=${page}&size=${questionsPageSize}`;
        if (filterTheme) {
            endpoint += `&themeId=${filterTheme}`;
        }

        const response = await apiCall(endpoint);
        const tbody = document.getElementById('questionsTableBody');

        if (!response.content || response.content.length === 0) {
            tbody.innerHTML = '<tr><td colspan="5" class="text-center">Aucune question</td></tr>';
            return;
        }

        tbody.innerHTML = response.content.map(question => `
            <tr>
                <td>${question.id}</td>
                <td>${(question.questionText || question.text || '').substring(0, 50)}...</td>
                <td>${question.theme?.name || question.themeName || '-'}</td>
                <td>${question.difficulty || '-'}</td>
                <td>
                    <button class="btn btn-sm btn-outline" onclick="editQuestion(${question.id})">Modifier</button>
                    <button class="btn btn-sm btn-danger" onclick="deleteQuestion(${question.id})">Supprimer</button>
                </td>
            </tr>
        `).join('');

        renderQuestionsPagination(response.totalPages);
    } catch (error) {
        console.error('Error loading questions:', error);
        const tbody = document.getElementById('questionsTableBody');
        tbody.innerHTML = '<tr><td colspan="5" class="text-center" style="color: var(--danger-color);">Erreur de chargement des questions</td></tr>';
    }
}

// Render questions pagination
function renderQuestionsPagination(totalPages) {
    const pagination = document.getElementById('questionsPagination');

    let html = '<div class="pagination-buttons">';

    if (questionsPage > 0) {
        html += `<button class="btn btn-outline" onclick="loadQuestions(${questionsPage - 1})">Précédent</button>`;
    }

    html += `<span class="page-info">Page ${questionsPage + 1} / ${totalPages}</span>`;

    if (questionsPage < totalPages - 1) {
        html += `<button class="btn btn-outline" onclick="loadQuestions(${questionsPage + 1})">Suivant</button>`;
    }

    html += '</div>';
    pagination.innerHTML = html;
}

// Load themes for filter and form
async function loadThemesForQuestions() {
    try {
        const themes = await apiCall('/admin/themes');

        // Filter select
        const filterSelect = document.getElementById('filterTheme');
        filterSelect.innerHTML = '<option value="">Tous les thèmes</option>' +
            themes.map(theme => `<option value="${theme.id}">${theme.name}</option>`).join('');

        filterSelect.addEventListener('change', () => loadQuestions(0));

        // Form select
        const formSelect = document.getElementById('questionTheme');
        formSelect.innerHTML = '<option value="">Sélectionnez un thème</option>' +
            themes.map(theme => `<option value="${theme.id}">${theme.name}</option>`).join('');
    } catch (error) {
        console.error('Error loading themes:', error);
    }
}

// Add question button
document.getElementById('addQuestionBtn').addEventListener('click', () => {
    document.getElementById('questionForm').reset();
    document.getElementById('questionId').value = '';
    document.getElementById('questionModalTitle').textContent = 'Ajouter une question';
    openModal('questionModal');
});

// Question form submission
document.getElementById('questionForm').addEventListener('submit', async (e) => {
    e.preventDefault();

    const id = document.getElementById('questionId').value;
    const data = {
        text: document.getElementById('questionText').value,
        themeId: parseInt(document.getElementById('questionTheme').value),
        difficulty: document.getElementById('questionDifficulty').value,
        correctAnswer: document.getElementById('correctAnswer').value,
        wrongAnswer1: document.getElementById('wrongAnswer1').value,
        wrongAnswer2: document.getElementById('wrongAnswer2').value,
        wrongAnswer3: document.getElementById('wrongAnswer3').value
    };

    try {
        if (id) {
            await apiCall(`/admin/questions/${id}`, {
                method: 'PUT',
                body: JSON.stringify(data)
            });
        } else {
            await apiCall('/admin/questions', {
                method: 'POST',
                body: JSON.stringify(data)
            });
        }

        closeModal('questionModal');
        loadQuestions(questionsPage);
    } catch (error) {
        console.error('Error saving question:', error);
        alert('Erreur: ' + error.message);
    }
});

// Edit question
async function editQuestion(id) {
    try {
        const question = await apiCall(`/admin/questions/${id}`);

        document.getElementById('questionId').value = question.id;
        document.getElementById('questionText').value = question.text;
        document.getElementById('questionTheme').value = question.themeId;
        document.getElementById('questionDifficulty').value = question.difficulty;
        document.getElementById('correctAnswer').value = question.correctAnswer;
        document.getElementById('wrongAnswer1').value = question.wrongAnswer1;
        document.getElementById('wrongAnswer2').value = question.wrongAnswer2;
        document.getElementById('wrongAnswer3').value = question.wrongAnswer3;

        document.getElementById('questionModalTitle').textContent = 'Modifier la question';
        openModal('questionModal');
    } catch (error) {
        console.error('Error loading question:', error);
        alert('Erreur: ' + error.message);
    }
}

// Delete question
async function deleteQuestion(id) {
    if (!confirm('Êtes-vous sûr de vouloir supprimer cette question ?')) {
        return;
    }

    try {
        await apiCall(`/admin/questions/${id}`, { method: 'DELETE' });
        loadQuestions(questionsPage);
    } catch (error) {
        console.error('Error deleting question:', error);
        alert('Erreur: ' + error.message);
    }
}

// CSV Import
document.getElementById('importCSVBtn').addEventListener('click', () => {
    document.getElementById('csvForm').reset();
    document.getElementById('csvError').style.display = 'none';
    openModal('csvModal');
});

document.getElementById('csvForm').addEventListener('submit', async (e) => {
    e.preventDefault();

    const fileInput = document.getElementById('csvFile');
    const file = fileInput.files[0];
    const errorDiv = document.getElementById('csvError');

    if (!file) {
        errorDiv.textContent = 'Veuillez sélectionner un fichier';
        errorDiv.style.display = 'block';
        return;
    }

    const formData = new FormData();
    formData.append('file', file);

    try {
        const response = await fetch(`${API_URL}/admin/questions/import-csv`, {
            method: 'POST',
            headers: getAuthHeader(),
            body: formData
        });

        if (!response.ok) {
            throw new Error('Erreur lors de l\'import');
        }

        const result = await response.json();
        alert(`${result.imported || 0} questions importées avec succès`);

        closeModal('csvModal');
        loadQuestions(questionsPage);
    } catch (error) {
        console.error('Error importing CSV:', error);
        errorDiv.textContent = error.message;
        errorDiv.style.display = 'block';
    }
});

// USERS TAB
async function loadUsers() {
    try {
        const response = await apiCall('/admin/users');
        const tbody = document.getElementById('usersTableBody');

        // L'API retourne un objet paginé avec une propriété 'content'
        const users = response.content || response;

        if (!users || users.length === 0) {
            tbody.innerHTML = '<tr><td colspan="7" class="text-center">Aucun utilisateur</td></tr>';
            return;
        }

        tbody.innerHTML = users.map(user => {
            // Gérer les rôles qui peuvent être un tableau d'objets ou de strings
            let rolesText = '';
            if (user.roles) {
                if (Array.isArray(user.roles)) {
                    rolesText = user.roles.map(r => r.name || r).join(', ');
                } else {
                    rolesText = String(user.roles);
                }
            }

            const hasAdminRole = rolesText.includes('ROLE_ADMIN') || rolesText.includes('ADMIN');

            return `
                <tr>
                    <td>${user.id}</td>
                    <td>${user.username}</td>
                    <td>${user.email}</td>
                    <td>${rolesText}</td>
                    <td>${user.quizCount || 0}</td>
                    <td>${user.totalScore || 0}</td>
                    <td>
                        <button class="btn btn-sm btn-outline" onclick="viewUser(${user.id})">Voir</button>
                        <button class="btn btn-sm btn-outline" onclick="editUser(${user.id})">Modifier</button>
                        ${!hasAdminRole ?
                            `<button class="btn btn-sm btn-danger" onclick="deleteUser(${user.id})">Supprimer</button>` : ''}
                    </td>
                </tr>
            `;
        }).join('');
    } catch (error) {
        console.error('Error loading users:', error);
        const tbody = document.getElementById('usersTableBody');
        tbody.innerHTML = '<tr><td colspan="7" class="text-center" style="color: var(--danger-color);">Erreur de chargement des utilisateurs</td></tr>';
    }
}

// Add user button
document.getElementById('addUserBtn').addEventListener('click', () => {
    document.getElementById('userForm').reset();
    document.getElementById('userId').value = '';
    document.getElementById('userError').style.display = 'none';
    document.getElementById('userModalTitle').textContent = 'Ajouter un utilisateur';
    document.getElementById('userSubmitBtn').textContent = 'Créer';
    document.getElementById('userPassword').required = true;
    document.getElementById('passwordHint').style.display = 'none';
    openModal('userModal');
});

// Edit user
async function editUser(id) {
    try {
        const userData = await apiCall(`/admin/users/${id}`);
        const user = userData.user;

        document.getElementById('userId').value = user.id;
        document.getElementById('userUsername').value = user.username;
        document.getElementById('userEmail').value = user.email;
        document.getElementById('userFirstName').value = user.firstName || '';
        document.getElementById('userLastName').value = user.lastName || '';
        document.getElementById('userCountry').value = user.country || '';
        document.getElementById('userPassword').value = '';
        document.getElementById('userPassword').required = false;
        document.getElementById('passwordHint').style.display = 'block';

        // Set user role
        const userRole = user.roles && user.roles.length > 0
            ? (user.roles[0].name || user.roles[0])
            : 'ROLE_USER';
        document.getElementById('userRole').value = userRole;

        document.getElementById('userModalTitle').textContent = 'Modifier l\'utilisateur';
        document.getElementById('userSubmitBtn').textContent = 'Valider';
        document.getElementById('userError').style.display = 'none';

        openModal('userModal');
    } catch (error) {
        console.error('Error loading user:', error);
        alert('Erreur lors du chargement de l\'utilisateur: ' + error.message);
    }
}

// User form submission
document.getElementById('userForm').addEventListener('submit', async (e) => {
    e.preventDefault();

    const errorDiv = document.getElementById('userError');
    errorDiv.style.display = 'none';

    const userId = document.getElementById('userId').value;
    const password = document.getElementById('userPassword').value;

    const data = {
        username: document.getElementById('userUsername').value,
        email: document.getElementById('userEmail').value,
        firstName: document.getElementById('userFirstName').value,
        lastName: document.getElementById('userLastName').value,
        country: document.getElementById('userCountry').value,
        role: document.getElementById('userRole').value
    };

    // Only include password if it's provided
    if (password) {
        data.password = password;
    }

    try {
        if (userId) {
            // Update existing user
            await apiCall(`/admin/users/${userId}`, {
                method: 'PUT',
                body: JSON.stringify(data)
            });
            alert('Utilisateur modifié avec succès!');
        } else {
            // Create new user
            if (!password) {
                errorDiv.textContent = 'Le mot de passe est requis pour créer un utilisateur';
                errorDiv.style.display = 'block';
                return;
            }
            await apiCall('/admin/users', {
                method: 'POST',
                body: JSON.stringify(data)
            });
            alert('Utilisateur créé avec succès!');
        }

        closeModal('userModal');
        loadUsers();
    } catch (error) {
        console.error('Error saving user:', error);
        errorDiv.textContent = error.message;
        errorDiv.style.display = 'block';
    }
});

// View user
function viewUser(id) {
    window.location.href = `/profile.html?userId=${id}`;
}

// Delete user
async function deleteUser(id) {
    if (!confirm('Êtes-vous sûr de vouloir supprimer cet utilisateur ?')) {
        return;
    }

    try {
        await apiCall(`/admin/users/${id}`, { method: 'DELETE' });
        loadUsers();
    } catch (error) {
        console.error('Error deleting user:', error);
        alert('Erreur: ' + error.message);
    }
}

// MODAL FUNCTIONS
function openModal(modalId) {
    document.getElementById(modalId).style.display = 'block';
}

function closeModal(modalId) {
    document.getElementById(modalId).style.display = 'none';
}

// Close modal on X click
document.querySelectorAll('.modal .close').forEach(closeBtn => {
    closeBtn.addEventListener('click', (e) => {
        const modal = e.target.closest('.modal');
        modal.style.display = 'none';
    });
});

// Close modal on close button click
document.querySelectorAll('.close-modal').forEach(btn => {
    btn.addEventListener('click', (e) => {
        const modal = e.target.closest('.modal');
        modal.style.display = 'none';
    });
});

// Close modal on outside click
window.addEventListener('click', (e) => {
    if (e.target.classList.contains('modal')) {
        e.target.style.display = 'none';
    }
});

// Fix user roles
async function fixUserRoles() {
    if (!confirm('Voulez-vous réparer les rôles de tous les utilisateurs qui n\'ont pas le rôle USER ?')) {
        return;
    }

    try {
        const result = await apiCall('/admin/fix-user-roles', { method: 'POST' });
        alert(`Réparation terminée!\nTotal: ${result.totalUsers} utilisateurs\nRéparés: ${result.fixedUsers} utilisateurs`);

        // Reload users if on users tab
        if (currentTab === 'users') {
            loadUsers();
        }
    } catch (error) {
        console.error('Error fixing user roles:', error);
        alert('Erreur lors de la réparation: ' + error.message);
    }
}

// ============================================
// RECLAMATIONS TAB
// ============================================

// Load reclamations
async function loadReclamations() {
    try {
        // Load statistics
        const stats = await apiCall('/admin/reclamations/stats');
        document.getElementById('totalReclamations').textContent = stats.total || 0;
        document.getElementById('enAttenteReclamations').textContent = stats.enAttente || 0;
        document.getElementById('enCoursReclamations').textContent = stats.enCours || 0;
        document.getElementById('resoluReclamations').textContent = stats.resolu || 0;

        // Load reclamations list
        await loadReclamationsList();
    } catch (error) {
        console.error('Error loading reclamations:', error);
    }
}

// Load reclamations list
async function loadReclamationsList(status = '') {
    try {
        const tableBody = document.getElementById('reclamationsTableBody');
        tableBody.innerHTML = '<tr><td colspan="6" class="text-center"><div class="loading-spinner"></div></td></tr>';

        let url = '/admin/reclamations';
        if (status) {
            url += `?status=${status}`;
        }

        const reclamations = await apiCall(url);

        if (reclamations.length === 0) {
            tableBody.innerHTML = '<tr><td colspan="6" class="text-center text-muted">Aucune réclamation trouvée</td></tr>';
            return;
        }

        tableBody.innerHTML = reclamations.map(rec => {
            const statusBadge = getReclamationStatusBadge(rec.status);
            const date = new Date(rec.createdAt).toLocaleDateString('fr-FR');

            return `
                <tr>
                    <td>${rec.id}</td>
                    <td>${escapeHtml(rec.username)}</td>
                    <td>${escapeHtml(rec.subject)}</td>
                    <td>${statusBadge}</td>
                    <td>${date}</td>
                    <td>
                        <button class="btn btn-sm btn-primary" onclick="openReclamationModal(${rec.id})">
                            ${rec.adminResponse ? 'Voir/Modifier' : 'Répondre'}
                        </button>
                    </td>
                </tr>
            `;
        }).join('');
    } catch (error) {
        console.error('Error loading reclamations list:', error);
        const tableBody = document.getElementById('reclamationsTableBody');
        tableBody.innerHTML = '<tr><td colspan="6" class="text-center text-danger">Erreur de chargement</td></tr>';
    }
}

// Get reclamation status badge
function getReclamationStatusBadge(status) {
    const badges = {
        'EN_ATTENTE': '<span class="badge badge-warning">En Attente</span>',
        'EN_COURS': '<span class="badge badge-info">En Cours</span>',
        'RESOLU': '<span class="badge badge-success">Résolu</span>',
        'FERME': '<span class="badge badge-secondary">Fermé</span>'
    };
    return badges[status] || status;
}

// Open reclamation modal
async function openReclamationModal(reclamationId) {
    try {
        const reclamation = await apiCall(`/admin/reclamations/${reclamationId}`);

        // Fill modal with reclamation details
        document.getElementById('reclamationId').value = reclamation.id;
        document.getElementById('reclamationUsername').textContent = reclamation.username;
        document.getElementById('reclamationSubject').textContent = reclamation.subject;
        document.getElementById('reclamationDescription').textContent = reclamation.description;
        document.getElementById('reclamationDate').textContent = new Date(reclamation.createdAt).toLocaleDateString('fr-FR', {
            day: '2-digit',
            month: 'long',
            year: 'numeric',
            hour: '2-digit',
            minute: '2-digit'
        });

        // Pre-fill response if exists
        if (reclamation.adminResponse) {
            document.getElementById('adminResponse').value = reclamation.adminResponse;
            document.getElementById('reclamationStatus').value = reclamation.status;
        } else {
            document.getElementById('adminResponse').value = '';
            document.getElementById('reclamationStatus').value = 'EN_COURS';
        }

        // Show modal
        document.getElementById('reclamationModal').style.display = 'block';
    } catch (error) {
        console.error('Error loading reclamation:', error);
        alert('Erreur lors du chargement de la réclamation');
    }
}

// Handle reclamation response form submission
document.getElementById('reclamationResponseForm').addEventListener('submit', async (e) => {
    e.preventDefault();

    const reclamationId = document.getElementById('reclamationId').value;
    const response = document.getElementById('adminResponse').value.trim();
    const status = document.getElementById('reclamationStatus').value;

    if (!response) {
        showError('reclamationError', 'Veuillez entrer une réponse');
        return;
    }

    try {
        await apiCall(`/admin/reclamations/${reclamationId}/respond`, {
            method: 'PUT',
            body: JSON.stringify({
                response: response,
                status: status
            })
        });

        // Close modal and reload
        document.getElementById('reclamationModal').style.display = 'none';
        showSuccess('Réponse envoyée avec succès');
        loadReclamations();
    } catch (error) {
        console.error('Error responding to reclamation:', error);
        showError('reclamationError', error.message || 'Erreur lors de l\'envoi de la réponse');
    }
});

// Filter reclamations by status
document.getElementById('filterStatus').addEventListener('change', (e) => {
    const status = e.target.value;
    loadReclamationsList(status);
});

// Modal close handlers for reclamation modal
const reclamationModal = document.getElementById('reclamationModal');
reclamationModal.querySelector('.close').addEventListener('click', () => {
    reclamationModal.style.display = 'none';
});

reclamationModal.querySelectorAll('.close-modal').forEach(btn => {
    btn.addEventListener('click', () => {
        reclamationModal.style.display = 'none';
    });
});

// Utility function to escape HTML
function escapeHtml(text) {
    const div = document.createElement('div');
    div.textContent = text;
    return div.innerHTML;
}

// Utility functions for showing success/error messages
function showSuccess(message) {
    alert(message);
}

function showError(errorDivId, message) {
    const errorDiv = document.getElementById(errorDivId);
    if (errorDiv) {
        errorDiv.textContent = message;
        errorDiv.style.display = 'block';
    } else {
        alert('Erreur: ' + message);
    }
}

// Initialize - Load dashboard by default
loadDashboard();
loadThemesForQuestions();
