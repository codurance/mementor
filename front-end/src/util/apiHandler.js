import { toast } from "react-toastify";

export function handleSuccess(message, rerenderCallback) {
  toast.success(message);
  rerenderCallback();
}

export function handleBackendError(response) {
  response.json().then(body => toast.warn(body.message));
}

export function handleUnexpectedBackendError(response) {
  console.log(response);
  toast.error("An unexpected error occured");
}
