// Propositions - Gestion des propositions utilisateur

document.addEventListener('DOMContentLoaded', function() {
    loadThemes();
    loadPropositions();
    setupEventListeners();
});

function setupEventListeners() {
    // Type selector
    document.querySelectorAll('.type-btn').forEach(btn => {
        btn.addEventListener('click', function() {
            const type = this.dataset.type;
            switchFormType(type);
        });
    });

    // Forms
    document.getElementById('createThemeForm').addEventListener('submit', handleCreateTheme);
    document.getElementById('createQuestionForm').addEventListener('submit', handleCreateQuestion);
}

function switchFormType(type) {
    // Update buttons
    document.querySelectorAll('.type-btn').forEach(btn => {
        btn.classList.remove('active');
    });
    document.querySelector(`[data-type="${type}"]`).classList.add('active');

    // Update forms
    document.querySelectorAll('.proposition-form').forEach(form => {
        form.classList.remove('active');
    });

    if (type === 'theme') {
        document.getElementById('themeForm').classList.add('active');
    } else {
        document.getElementById('questionForm').classList.add('active');
    }
}

async function loadThemes() {
    try {
        const response = await fetch(`${API_URL}/solo/themes`);
        if (response.ok) {
            const themes = await response.json();
            const select = document.getElementById('questionTheme');

            themes.forEach(theme => {
                const option = document.createElement('option');
                option.value = theme.id;
                option.textContent = theme.name;
                select.appendChild(option);
            });
        }
    } catch (error) {
        console.error('Error loading themes:', error);
    }
}

async function handleCreateTheme(e) {
    e.preventDefault();

    const title = document.getElementById('themeTitle').value.trim();
    const description = document.getElementById('themeDescription').value.trim();
    const icon = document.getElementById('themeIcon').value.trim();

    if (!title || !description) {
        showAlert('Veuillez remplir tous les champs obligatoires', 'error');
        return;
    }

    const requestData = {
        themeTitle: title,
        themeDescription: description,
        themeIcon: icon || ''
    };

    try {
        const response = await fetch(`${API_URL}/user/propositions/theme`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${getToken()}`
            },
            body: JSON.stringify(requestData)
        });

        if (response.ok) {
            showAlert('Proposition de thème créée avec succès !', 'success');
            document.getElementById('createThemeForm').reset();
            loadPropositions();
        } else {
            const error = await response.json();
            showAlert(error.error || 'Erreur lors de la création de la proposition', 'error');
        }
    } catch (error) {
        console.error('Error:', error);
        showAlert('Erreur de connexion au serveur', 'error');
    }
}

async function handleCreateQuestion(e) {
    e.preventDefault();

    const themeId = document.getElementById('questionTheme').value;
    const questionText = document.getElementById('questionText').value.trim();
    const difficulty = document.getElementById('questionDifficulty').value;
    const correctAnswer = document.getElementById('correctAnswer').value.trim();
    const wrongAnswer1 = document.getElementById('wrongAnswer1').value.trim();
    const wrongAnswer2 = document.getElementById('wrongAnswer2').value.trim();
    const wrongAnswer3 = document.getElementById('wrongAnswer3').value.trim();

    if (!themeId || !questionText || !difficulty || !correctAnswer || !wrongAnswer1 || !wrongAnswer2 || !wrongAnswer3) {
        showAlert('Veuillez remplir tous les champs obligatoires', 'error');
        return;
    }

    const requestData = {
        themeId: parseInt(themeId),
        questionText: questionText,
        difficulty: difficulty,
        correctAnswer: correctAnswer,
        wrongAnswer1: wrongAnswer1,
        wrongAnswer2: wrongAnswer2,
        wrongAnswer3: wrongAnswer3
    };

    try {
        const response = await fetch(`${API_URL}/user/propositions/question`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${getToken()}`
            },
            body: JSON.stringify(requestData)
        });

        if (response.ok) {
            showAlert('Proposition de question créée avec succès !', 'success');
            document.getElementById('createQuestionForm').reset();
            loadPropositions();
        } else {
            const error = await response.json();
            showAlert(error.error || 'Erreur lors de la création de la proposition', 'error');
        }
    } catch (error) {
        console.error('Error:', error);
        showAlert('Erreur de connexion au serveur', 'error');
    }
}

async function loadPropositions() {
    const propositionsList = document.getElementById('propositionsList');
    propositionsList.innerHTML = '<p class="text-center">Chargement...</p>';

    try {
        const response = await fetch(`${API_URL}/user/propositions`, {
            headers: {
                'Authorization': `Bearer ${getToken()}`
            }
        });

        if (response.ok) {
            const propositions = await response.json();
            displayPropositions(propositions);
        } else {
            propositionsList.innerHTML = '<p class="text-center" style="color: red;">Erreur lors du chargement des propositions</p>';
        }
    } catch (error) {
        console.error('Error:', error);
        propositionsList.innerHTML = '<p class="text-center" style="color: red;">Erreur de connexion au serveur</p>';
    }
}

function displayPropositions(propositions) {
    const propositionsList = document.getElementById('propositionsList');

    if (propositions.length === 0) {
        propositionsList.innerHTML = `
            <div class="empty-state" style="text-align: center; padding: 60px 20px; color: #999;">
                <h3>Aucune proposition</h3>
                <p>Vous n'avez pas encore soumis de proposition.</p>
            </div>
        `;
        return;
    }

    propositionsList.innerHTML = propositions.map(prop => {
        const typeLabel = prop.type === 'THEME' ? 'Thème' : 'Question';
        const statusLabel = getStatusLabel(prop.status);

        let content = '';
        if (prop.type === 'THEME') {
            content = `
                <div class="proposition-title">${escapeHtml(prop.themeTitle)}</div>
                <div class="proposition-content">${escapeHtml(prop.themeDescription)}</div>
                ${prop.themeIcon ? `<div style="font-size: 2rem;">${prop.themeIcon}</div>` : ''}
            `;
        } else {
            content = `
                <div class="proposition-title">${escapeHtml(prop.questionText)}</div>
                <div class="proposition-content">
                    <strong>Thème:</strong> ${escapeHtml(prop.themeName || 'N/A')}<br>
                    <strong>Difficulté:</strong> ${getDifficultyLabel(prop.difficulty)}
                </div>
                <div class="question-answers">
                    <div class="answer-item answer-correct">Correcte: ${escapeHtml(prop.correctAnswer)}</div>
                    <div class="answer-item answer-wrong">Incorrecte: ${escapeHtml(prop.wrongAnswer1)}</div>
                    <div class="answer-item answer-wrong">Incorrecte: ${escapeHtml(prop.wrongAnswer2)}</div>
                    <div class="answer-item answer-wrong">Incorrecte: ${escapeHtml(prop.wrongAnswer3)}</div>
                </div>
            `;
        }

        return `
            <div class="proposition-card fade-in">
                <div class="proposition-card-header">
                    <div>
                        <span class="proposition-type type-${prop.type}">${typeLabel}</span>
                    </div>
                    <span class="proposition-status status-${prop.status}">${statusLabel}</span>
                </div>

                ${content}

                ${prop.adminComment ? `
                    <div class="admin-feedback">
                        <div class="feedback-header">
                            Commentaire de l'administrateur
                        </div>
                        <div>${escapeHtml(prop.adminComment)}</div>
                    </div>
                ` : ''}

                <div class="proposition-meta">
                    <span>Créée le ${formatDate(prop.createdAt)}</span>
                    ${prop.reviewedAt ? `<span>Examinée le ${formatDate(prop.reviewedAt)}</span>` : ''}
                </div>
            </div>
        `;
    }).join('');
}

function getStatusLabel(status) {
    const labels = {
        'EN_ATTENTE': 'En Attente',
        'APPROUVE': 'Approuvée',
        'REJETE': 'Rejetée'
    };
    return labels[status] || status;
}

function getDifficultyLabel(difficulty) {
    const labels = {
        'EASY': 'Facile (10 pts)',
        'MEDIUM': 'Moyenne (20 pts)',
        'HARD': 'Difficile (30 pts)'
    };
    return labels[difficulty] || difficulty;
}

function formatDate(dateString) {
    if (!dateString) return '-';

    const date = new Date(dateString);
    return date.toLocaleDateString('fr-FR', {
        day: '2-digit',
        month: 'long',
        year: 'numeric'
    });
}

function showAlert(message, type) {
    const alertContainer = document.getElementById('alertContainer');
    const alertClass = type === 'success' ? 'alert-success' : 'alert-error';

    alertContainer.innerHTML = `
        <div class="alert ${alertClass}">
            ${message}
        </div>
    `;

    setTimeout(() => {
        alertContainer.innerHTML = '';
    }, 5000);

    alertContainer.scrollIntoView({ behavior: 'smooth', block: 'nearest' });
}

function escapeHtml(text) {
    if (!text) return '';
    const div = document.createElement('div');
    div.textContent = text;
    return div.innerHTML;
}
