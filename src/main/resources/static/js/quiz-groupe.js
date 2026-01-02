// Check authentication
checkAuth();

// Logout handler
document.getElementById('logoutBtn').addEventListener('click', (e) => {
    e.preventDefault();
    logout();
});

// Show history button
document.getElementById('showHistoryBtn').addEventListener('click', () => {
    const historySection = document.getElementById('historySection');
    const rankingsSection = document.getElementById('rankingsSection');

    if (historySection.style.display === 'none') {
        historySection.style.display = 'block';
        rankingsSection.style.display = 'none';
        loadHistory();
    } else {
        historySection.style.display = 'none';
    }
});

// Show rankings button
document.getElementById('showRankingsBtn').addEventListener('click', () => {
    const historySection = document.getElementById('historySection');
    const rankingsSection = document.getElementById('rankingsSection');

    if (rankingsSection.style.display === 'none') {
        rankingsSection.style.display = 'block';
        historySection.style.display = 'none';
        loadRankings();
    } else {
        rankingsSection.style.display = 'none';
    }
});

// Load group session history
async function loadHistory() {
    const container = document.getElementById('historyContainer');
    container.innerHTML = '<div class="loading-spinner"></div>';

    try {
        const history = await apiCall('/group/history');

        if (!history || history.length === 0) {
            container.innerHTML = '<p class="text-center text-muted">Aucune session précédente</p>';
            return;
        }

        container.innerHTML = history.map(session => {
            const statusBadge = session.status === 'COMPLETED' ?
                '<span class="badge badge-success">Terminée</span>' :
                session.status === 'IN_PROGRESS' ?
                '<span class="badge badge-warning">En cours</span>' :
                '<span class="badge badge-info">En attente</span>';

            const winner = session.winnerGroupName ?
                `<span>🏆 Vainqueur: <strong>${session.winnerGroupName}</strong></span>` :
                '<span class="text-muted">Aucun vainqueur</span>';

            const viewResultsBtn = session.status === 'COMPLETED' ?
                `<button class="btn btn-primary btn-sm" onclick="viewSessionResults(${session.id})">📊 Voir résultats</button>` :
                session.status === 'WAITING' ?
                `<button class="btn btn-success btn-sm" onclick="startSession(${session.id})">▶️ Commencer</button>` :
                `<button class="btn btn-warning btn-sm" onclick="resetSession(${session.id})">🔄 Abandonner et recommencer</button>`;

            return `
                <div class="history-item glass-card">
                    <div class="history-header">
                        <div>
                            <h3>${session.sessionName}</h3>
                            <span class="session-code">Code: ${session.sessionCode}</span>
                        </div>
                        <div class="history-meta">
                            ${statusBadge}
                            <span class="history-date">${new Date(session.createdAt).toLocaleDateString('fr-FR', {
                                day: '2-digit',
                                month: 'short',
                                year: 'numeric',
                                hour: '2-digit',
                                minute: '2-digit'
                            })}</span>
                        </div>
                    </div>
                    <div class="history-details">
                        ${winner}
                        <span>📚 Thème: ${session.themeName}</span>
                        <span>👥 ${session.numberOfGroups} groupes</span>
                        <span>❓ ${session.totalQuestions} questions</span>
                    </div>
                    <div class="history-actions" style="margin-top: 10px; text-align: right;">
                        ${viewResultsBtn}
                    </div>
                </div>
            `;
        }).join('');
    } catch (error) {
        console.error('Error loading history:', error);
        container.innerHTML = '<p class="text-center text-muted">Erreur de chargement</p>';
    }
}

// Load group rankings
async function loadRankings() {
    const container = document.getElementById('rankingsContainer');
    container.innerHTML = '<div class="loading-spinner"></div>';

    try {
        const rankings = await apiCall('/group/rankings');

        if (!rankings || rankings.length === 0) {
            container.innerHTML = '<p class="text-center text-muted">Aucun classement disponible</p>';
            return;
        }

        container.innerHTML = `
            <table class="rankings-table">
                <thead>
                    <tr>
                        <th>Rang</th>
                        <th>Groupe</th>
                        <th>Session</th>
                        <th>Score</th>
                        <th>Précision</th>
                    </tr>
                </thead>
                <tbody>
                    ${rankings.map((entry, index) => {
                        const rank = entry.rank || (index + 1);
                        const medal = rank === 1 ? '🥇' : rank === 2 ? '🥈' : rank === 3 ? '🥉' : rank;
                        const rankClass = rank <= 3 ? 'top-rank' : '';

                        // Validation stricte des valeurs
                        const groupName = entry.groupName || 'Groupe inconnu';
                        const sessionName = entry.sessionName || 'Session inconnue';
                        const score = typeof entry.score === 'number' ? entry.score : 0;
                        const percentage = typeof entry.percentage === 'number' ? entry.percentage : 0;

                        return `
                            <tr class="${rankClass}">
                                <td><strong>${medal}</strong></td>
                                <td>${groupName}</td>
                                <td>${sessionName}</td>
                                <td><strong>${score}</strong> pts</td>
                                <td>
                                    <div class="percentage-bar">
                                        <div class="percentage-fill" style="width: ${Math.min(100, Math.max(0, percentage))}%"></div>
                                        <span class="percentage-text">${percentage.toFixed(1)}%</span>
                                    </div>
                                </td>
                            </tr>
                        `;
                    }).join('')}
                </tbody>
            </table>
        `;
    } catch (error) {
        console.error('Error loading rankings:', error);
        container.innerHTML = '<p class="text-center text-muted">Erreur de chargement</p>';
    }
}

// View results of a completed session
async function viewSessionResults(sessionId) {
    try {
        const result = await apiCall(`/group/${sessionId}/results`);

        // Store result in sessionStorage and redirect to results page
        sessionStorage.setItem('groupResult', JSON.stringify(result));
        window.location.href = '/result-groupe.html';
    } catch (error) {
        console.error('Error loading session results:', error);
        alert('Erreur lors du chargement des résultats');
    }
}

// Start a waiting session
function startSession(sessionId) {
    window.location.href = `/play-groupe.html?sessionId=${sessionId}`;
}

// Reset an in-progress session
async function resetSession(sessionId) {
    if (!confirm('Êtes-vous sûr de vouloir abandonner cette session et recommencer ? Toutes les données seront perdues.')) {
        return;
    }

    try {
        await apiCall(`/group/${sessionId}/reset`, {
            method: 'POST'
        });

        alert('Session réinitialisée avec succès !');
        loadHistory(); // Reload history to show updated status
    } catch (error) {
        console.error('Error resetting session:', error);
        alert('Erreur lors de la réinitialisation de la session');
    }
}
