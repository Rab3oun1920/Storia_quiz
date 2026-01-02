// Réclamations - Gestion des réclamations utilisateur

document.addEventListener('DOMContentLoaded', function() {
    loadReclamations();
    setupEventListeners();
});

function setupEventListeners() {
    const btnNewReclamation = document.getElementById('btnNewReclamation');
    const btnCancelForm = document.getElementById('btnCancelForm');
    const createReclamationForm = document.getElementById('createReclamationForm');

    btnNewReclamation.addEventListener('click', toggleForm);
    btnCancelForm.addEventListener('click', toggleForm);
    createReclamationForm.addEventListener('submit', handleCreateReclamation);
}

function toggleForm() {
    const form = document.getElementById('reclamationForm');
    if (form.style.display === 'none' || form.style.display === '') {
        form.style.display = 'block';
        document.getElementById('createReclamationForm').reset();
        // Scroll vers le formulaire
        form.scrollIntoView({ behavior: 'smooth', block: 'start' });
    } else {
        form.style.display = 'none';
    }
}

async function handleCreateReclamation(e) {
    e.preventDefault();

    const subject = document.getElementById('subject').value.trim();
    const description = document.getElementById('description').value.trim();

    if (!subject || !description) {
        showAlert('Veuillez remplir tous les champs', 'error');
        return;
    }

    const requestData = {
        subject: subject,
        description: description
    };

    try {
        const response = await fetch(`${API_URL}/user/reclamations`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${getToken()}`
            },
            body: JSON.stringify(requestData)
        });

        if (response.ok) {
            showAlert('Réclamation créée avec succès !', 'success');
            toggleForm();
            loadReclamations();
        } else {
            const error = await response.json();
            showAlert(error.error || 'Erreur lors de la création de la réclamation', 'error');
        }
    } catch (error) {
        console.error('Error:', error);
        showAlert('Erreur de connexion au serveur', 'error');
    }
}

async function loadReclamations() {
    const reclamationsList = document.getElementById('reclamationsList');
    reclamationsList.innerHTML = '<p class="text-center">Chargement...</p>';

    try {
        const response = await fetch(`${API_URL}/user/reclamations`, {
            headers: {
                'Authorization': `Bearer ${getToken()}`
            }
        });

        if (response.ok) {
            const reclamations = await response.json();
            displayReclamations(reclamations);
        } else {
            reclamationsList.innerHTML = '<p class="text-center" style="color: red;">Erreur lors du chargement des réclamations</p>';
        }
    } catch (error) {
        console.error('Error:', error);
        reclamationsList.innerHTML = '<p class="text-center" style="color: red;">Erreur de connexion au serveur</p>';
    }
}

function displayReclamations(reclamations) {
    const reclamationsList = document.getElementById('reclamationsList');

    if (reclamations.length === 0) {
        reclamationsList.innerHTML = `
            <div class="empty-state">
                <h3>Aucune réclamation</h3>
                <p>Vous n'avez pas encore soumis de réclamation.</p>
            </div>
        `;
        return;
    }

    reclamationsList.innerHTML = reclamations.map(reclamation => {
        return `
            <div class="reclamation-card fade-in">
                <div class="reclamation-card-header">
                    <div>
                        <div class="reclamation-subject">${escapeHtml(reclamation.subject)}</div>
                        <div class="reclamation-meta">
                            <span>Créée ${formatDate(reclamation.createdAt)}</span>
                        </div>
                    </div>
                    <div class="reclamation-status status-${reclamation.status}">
                        ${getStatusLabel(reclamation.status)}
                    </div>
                </div>

                <div class="reclamation-description">
                    ${escapeHtml(reclamation.description)}
                </div>

                ${reclamation.adminResponse ? `
                    <div class="reclamation-response">
                        <div class="response-header">
                            Réponse de l'administrateur (${reclamation.respondedByUsername})
                        </div>
                        <div>${escapeHtml(reclamation.adminResponse)}</div>
                        <div class="reclamation-meta" style="margin-top: 10px;">
                            <span>Répondu ${formatDate(reclamation.respondedAt)}</span>
                        </div>
                    </div>
                ` : ''}
            </div>
        `;
    }).join('');
}

function getStatusLabel(status) {
    const labels = {
        'EN_ATTENTE': 'En Attente',
        'EN_COURS': 'En Cours',
        'RESOLU': 'Résolu',
        'FERME': 'Fermé'
    };
    return labels[status] || status;
}

function formatDate(dateString) {
    if (!dateString) return '-';

    const date = new Date(dateString);
    const now = new Date();
    const diff = now - date;
    const days = Math.floor(diff / (1000 * 60 * 60 * 24));

    if (days === 0) {
        const hours = Math.floor(diff / (1000 * 60 * 60));
        if (hours === 0) {
            const minutes = Math.floor(diff / (1000 * 60));
            return `Il y a ${minutes} minute${minutes > 1 ? 's' : ''}`;
        }
        return `Il y a ${hours} heure${hours > 1 ? 's' : ''}`;
    } else if (days === 1) {
        return 'Hier';
    } else if (days < 7) {
        return `Il y a ${days} jours`;
    } else {
        return date.toLocaleDateString('fr-FR', {
            day: '2-digit',
            month: 'long',
            year: 'numeric'
        });
    }
}

function showAlert(message, type) {
    const alertContainer = document.getElementById('alertContainer');
    const alertClass = type === 'success' ? 'alert-success' : 'alert-error';

    alertContainer.innerHTML = `
        <div class="alert ${alertClass}">
            ${message}
        </div>
    `;

    // Supprimer l'alerte après 5 secondes
    setTimeout(() => {
        alertContainer.innerHTML = '';
    }, 5000);

    // Scroll vers l'alerte
    alertContainer.scrollIntoView({ behavior: 'smooth', block: 'nearest' });
}

function escapeHtml(text) {
    const div = document.createElement('div');
    div.textContent = text;
    return div.innerHTML;
}
