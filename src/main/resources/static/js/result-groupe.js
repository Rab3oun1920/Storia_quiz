// Check authentication
checkAuth();

// Get result from sessionStorage
const resultStr = sessionStorage.getItem('groupResult');
if (!resultStr) {
    window.location.href = '/quiz-groupe.html';
}

const result = JSON.parse(resultStr);

// Validation des données reçues
if (!result || !result.sessionName || !result.rankings) {
    alert('Données de résultats invalides');
    window.location.href = '/quiz-groupe.html';
    throw new Error('Invalid result data');
}

// Display results
document.getElementById('sessionName').textContent = result.sessionName || 'Session inconnue';
document.getElementById('winnerName').textContent = (result.winner && result.winner.name) ? result.winner.name : 'Aucun';

// Rankings are already sorted by score from backend
const sortedGroups = result.rankings || [];

// Display podium (top 3)
if (sortedGroups[0]) {
    document.getElementById('place1').querySelector('.podium-group').textContent = sortedGroups[0].name || 'Groupe 1';
    document.getElementById('place1').querySelector('.podium-score').textContent = (sortedGroups[0].score || 0) + ' pts';
}

if (sortedGroups[1]) {
    document.getElementById('place2').querySelector('.podium-group').textContent = sortedGroups[1].name || 'Groupe 2';
    document.getElementById('place2').querySelector('.podium-score').textContent = (sortedGroups[1].score || 0) + ' pts';
    document.getElementById('place2').style.display = 'flex';
} else {
    document.getElementById('place2').style.display = 'none';
}

if (sortedGroups[2]) {
    document.getElementById('place3').querySelector('.podium-group').textContent = sortedGroups[2].name || 'Groupe 3';
    document.getElementById('place3').querySelector('.podium-score').textContent = (sortedGroups[2].score || 0) + ' pts';
    document.getElementById('place3').style.display = 'flex';
} else {
    document.getElementById('place3').style.display = 'none';
}

// Display detailed results
const tbody = document.getElementById('resultsBody');
tbody.innerHTML = sortedGroups.map((group, index) => {
    const rank = group.rank || (index + 1);
    const medal = rank === 1 ? '🥇' : rank === 2 ? '🥈' : rank === 3 ? '🥉' : rank;

    // Validation de toutes les valeurs
    const groupName = group.name || 'Groupe inconnu';
    const groupScore = typeof group.score === 'number' ? group.score : 0;
    const groupCorrectAnswers = typeof group.correctAnswers === 'number' ? group.correctAnswers : 0;
    const groupPercentage = typeof group.percentage === 'number' ? group.percentage.toFixed(1) : '0.0';

    return `
        <tr>
            <td><strong>${medal}</strong></td>
            <td>${groupName}</td>
            <td>${groupScore} pts</td>
            <td>${groupCorrectAnswers}/${group.totalQuestions || 10}</td>
            <td>${groupPercentage}%</td>
        </tr>
    `;
}).join('');

// Clear sessionStorage
sessionStorage.removeItem('groupResult');
