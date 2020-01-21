import { toast } from "react-toastify";

export function notifySuccess(message) {
  toast.success(message);
}

export function notifyBackendError(response) {
  response.json().then(body => toast.warn(body.message));
}

export function notifyUnexpectedBackendError(response) {
  console.log(response);
  toast.error("An unexpected error occured");
}
