import { toast } from "react-toastify";

export function api({
  endpoint,
  token,
  type = "GET",
  headers = { "Content-Type": "application/json", Authorization: `${token}` },
  body
} = {}) {
  let payload = { method: type, headers: headers };
  if (body) {
    payload.body = JSON.stringify(body);
  }
  return fetch(endpoint, payload).catch(error => console.log(error.message));
}
