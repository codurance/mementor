export const api = (path, options={}) => {
    return fetch(`http://localhost:8080/${path}`, options).then(
        res => res.json()
    )
};