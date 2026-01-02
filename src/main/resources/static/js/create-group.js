// Check authentication
checkAuth();

// Logout handler
document.getElementById('logoutBtn').addEventListener('click', (e) => {
    e.preventDefault();
    logout();
});

// Load themes
async function loadThemes() {
    try {
        const themes = await apiCall('/solo/themes');
        const select = document.getElementById('themeId');

        select.innerHTML = '<option value="">Sélectionnez un thème</option>' +
            themes.map(theme => `<option value="${theme.id}">${theme.name}</option>`).join('');
    } catch (error) {
        console.error('Error loading themes:', error);
    }
}

// Update group name inputs
function updateGroupInputs() {
    const numGroups = parseInt(document.querySelector('input[name="numGroups"]:checked').value);
    const container = document.getElementById('groupNamesContainer');

    let html = '<h3>Noms des groupes</h3>';
    for (let i = 1; i <= numGroups; i++) {
        html += `
            <div class="form-group">
                <label for="group${i}">Groupe ${i}</label>
                <input type="text" id="group${i}" name="group${i}"
                       value="Groupe ${i}" required>
            </div>
        `;
    }

    container.innerHTML = html;
}

// Radio button change listener
document.querySelectorAll('input[name="numGroups"]').forEach(radio => {
    radio.addEventListener('change', updateGroupInputs);
});

// Form submission
document.getElementById('createGroupForm').addEventListener('submit', async (e) => {
    e.preventDefault();

    const sessionName = document.getElementById('sessionName').value;
    const themeId = document.getElementById('themeId').value;
    const numGroups = parseInt(document.querySelector('input[name="numGroups"]:checked').value);

    const groups = [];
    for (let i = 1; i <= numGroups; i++) {
        groups.push({
            groupName: document.getElementById(`group${i}`).value,
            order: i
        });
    }

    const errorMessage = document.getElementById('errorMessage');
    errorMessage.style.display = 'none';

    const submitBtn = e.target.querySelector('button[type="submit"]');
    submitBtn.disabled = true;
    submitBtn.textContent = 'Création en cours...';

    try {
        const session = await apiCall('/group/create', {
            method: 'POST',
            body: JSON.stringify({
                sessionName: sessionName,
                themeId: parseInt(themeId),
                groups: groups
            })
        });

        window.location.href = `/play-groupe.html?sessionId=${session.sessionId}`;
    } catch (error) {
        console.error('Error creating session:', error);
        errorMessage.textContent = error.message || 'Erreur lors de la création de la session';
        errorMessage.style.display = 'block';
        submitBtn.disabled = false;
        submitBtn.textContent = 'Créer la session';
    }
});

// Initialize
loadThemes();
updateGroupInputs();
