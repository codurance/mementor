export function api ({
    endpoint,
    type = 'GET',
    headers = {'Content-Type': 'application/json'},
    body
} = {}) {
    let payload = {method: type, headers: headers};
    if (body) {
        payload.body = JSON.stringify(body)
    }
    return fetch(endpoint, payload).then(response => response.json());
}
