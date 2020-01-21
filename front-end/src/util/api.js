import { toast } from "react-toastify";

export function api({
  endpoint,
  type = "GET",
  headers = { "Content-Type": "application/json" },
  body,
} = {}) {
  let payload = { method: type, headers: headers };
  if (body) {
    payload.body = JSON.stringify(body);
  }
  return fetch(endpoint, payload)
    .then(response => {
      handdleResponse(response.status);
      return response.json();
    })
    .catch(error => console.log(error.message));
}

function handdleResponse(status) {
  if (status === 409) {
    toast.error("Already exists");
  } else if (status !== 200) {
    return;
  }
}
