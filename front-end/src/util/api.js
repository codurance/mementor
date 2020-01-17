export const api = (path, options = {}) => {
    return fetch(`/${path}`, options).then(
        res => res.json()
    )
};
