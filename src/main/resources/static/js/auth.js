// Auth Service for Storia

// Login function
async function login(username, password) {
    try {
        const response = await fetch(`${API_URL}/auth/signin`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ username, password })
        });

        if (!response.ok) {
            const error = await response.json();
            throw new Error(error.message || 'Erreur de connexion');
        }

        const data = await response.json();
        saveAuthData(data);
        return data;
    } catch (error) {
        console.error('Login error:', error);
        throw error;
    }
}

// Register function
async function register(userData) {
    try {
        const response = await fetch(`${API_URL}/auth/signup`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(userData)
        });

        if (!response.ok) {
            const error = await response.json();
            throw new Error(error.message || 'Erreur lors de l\'inscription');
        }

        const data = await response.json();
        return data;
    } catch (error) {
        console.error('Register error:', error);
        throw error;
    }
}

// Logout function
async function logout() {
    try {
        const token = getToken();
        if (token) {
            await fetch(`${API_URL}/auth/signout`, {
                method: 'POST',
                headers: getAuthHeader()
            });
        }
    } catch (error) {
        console.error('Logout error:', error);
    } finally {
        // Clear local storage regardless of API call success
        localStorage.removeItem(TOKEN_KEY);
        localStorage.removeItem(REFRESH_TOKEN_KEY);
        localStorage.removeItem(USER_KEY);
        window.location.href = '/index.html';
    }
}

// Get token from localStorage
function getToken() {
    return localStorage.getItem(TOKEN_KEY);
}

// Get user from localStorage
function getUser() {
    const userStr = localStorage.getItem(USER_KEY);
    return userStr ? JSON.parse(userStr) : null;
}

// Check if user is authenticated
function isAuthenticated() {
    return getToken() !== null;
}

// Get authorization header
function getAuthHeader() {
    const token = getToken();
    return token ? { 'Authorization': `Bearer ${token}` } : {};
}

// Check authentication and redirect if not authenticated
function checkAuth() {
    if (!isAuthenticated()) {
        window.location.href = '/login.html';
        return false;
    }
    return true;
}

// Check if user has admin role
function isAdmin() {
    const user = getUser();
    return user && user.roles && user.roles.includes('ROLE_ADMIN');
}

// Check admin access and redirect if not admin
function checkAdminAuth() {
    if (!isAuthenticated()) {
        window.location.href = '/login.html';
        return false;
    }
    if (!isAdmin()) {
        window.location.href = '/dashboard.html';
        return false;
    }
    return true;
}

// Save authentication data to localStorage
function saveAuthData(response) {
    if (response.token) {
        localStorage.setItem(TOKEN_KEY, response.token);
    }
    if (response.refreshToken) {
        localStorage.setItem(REFRESH_TOKEN_KEY, response.refreshToken);
    }
    // Construct user object from response
    const user = {
        id: response.id,
        username: response.username,
        email: response.email,
        firstName: response.firstName,
        lastName: response.lastName,
        country: response.country,
        roles: response.roles
    };
    localStorage.setItem(USER_KEY, JSON.stringify(user));
}

// API call helper with auth
async function apiCall(endpoint, options = {}) {
    const defaultOptions = {
        headers: {
            'Content-Type': 'application/json',
            ...getAuthHeader()
        }
    };

    const finalOptions = {
        ...defaultOptions,
        ...options,
        headers: {
            ...defaultOptions.headers,
            ...options.headers
        }
    };

    try {
        const response = await fetch(`${API_URL}${endpoint}`, finalOptions);

        if (response.status === 401) {
            // Token expired or invalid
            console.warn('Token expiré ou invalide, déconnexion...');
            logout();
            return;
        }

        if (response.status === 403) {
            // Forbidden - User doesn't have permission
            console.error('Accès refusé - Permissions insuffisantes');
            const error = await response.json().catch(() => ({ message: 'Accès refusé' }));
            throw new Error(error.message || 'Vous n\'avez pas les permissions nécessaires');
        }

        if (!response.ok) {
            const error = await response.json().catch(() => ({ message: 'Une erreur est survenue' }));
            throw new Error(error.message || 'Une erreur est survenue');
        }

        return await response.json();
    } catch (error) {
        console.error('API call error:', error);
        throw error;
    }
}
