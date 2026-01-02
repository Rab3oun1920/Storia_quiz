// Check authentication
checkAuth();

// Get result from sessionStorage
const resultStr = sessionStorage.getItem('quizResult');
if (!resultStr) {
    window.location.href = '/quiz-solo.html';
}

const result = JSON.parse(resultStr);

// Display results
document.getElementById('themeName').textContent = result.themeName;
document.getElementById('finalScore').textContent = result.score;

const percentage = Math.round((result.score / 200) * 100);
document.getElementById('percentage').textContent = percentage + '%';

document.getElementById('totalTime').textContent = result.totalTime + 's';

document.getElementById('globalRank').textContent = result.globalRank || '-';
document.getElementById('themeRank').textContent = result.themeRank || '-';

// Setup replay button
document.getElementById('replayBtn').href = `/play-solo.html?themeId=${result.themeId}`;

// Clear sessionStorage
sessionStorage.removeItem('quizResult');
