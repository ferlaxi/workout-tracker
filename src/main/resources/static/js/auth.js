function getJwtToken() {
    return localStorage.getItem('jwt');
}

function setJwtToken(token, username) {
    localStorage.setItem('jwt', token);
    if(username) localStorage.setItem('username', username);
}

function logout() {
    localStorage.removeItem('jwt');
    localStorage.removeItem('username');
    window.location.href = '/login';
}

function checkAuth(redirectIfNotAuth = true) {
    const token = getJwtToken();
    if (!token && redirectIfNotAuth) {
        window.location.href = '/login';
        return false;
    }
    
    // Update navbar username if element exists
    const usernameSpan = document.getElementById('nav-username');
    if (usernameSpan && localStorage.getItem('username')) {
        usernameSpan.textContent = localStorage.getItem('username');
    }
    
    return true;
}

function fetchWithAuth(url, options = {}) {
    const token = getJwtToken();
    
    const headers = {
        'Content-Type': 'application/json',
        ...options.headers
    };
    
    if (token) {
        headers['Authorization'] = `Bearer ${token}`;
    }
    
    return fetch(url, {
        ...options,
        headers
    }).then(response => {
        if (response.status === 401 || response.status === 403) {
            logout(); // Auto logout on unauthorized
            throw new Error('No autorizado');
        }
        return response;
    });
}
