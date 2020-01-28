import React from "react";
import { toast } from "react-toastify";

export function notifySuccess(message) {
  toast.success(message);
}

export function notifyBackendError(response) {
  if (response.status >= 500) {
    notifyUnexpectedBackendError(response);
  } else {
    response
      .json()
      .then(body => toast.warn(body.message))
      .catch(notifyUnexpectedBackendError);
  }
}

export function notifyUnexpectedBackendError(response) {
  console.log(response);
  toast.error("An unexpected error occured");
}

export function notifyFormValidationError(message) {
  toast.warn(message);
}

export function mentorAddedMessage(mentorFirstname, menteeFirstname) {
  return (
    <p>
      <strong>{mentorFirstname}</strong> is now mentoring{" "}
      <strong>{menteeFirstname}</strong>
    </p>
  );
}

export function mentorRemovedMessage(menteeFirstname) {
  return (
    <p>
      <strong>{menteeFirstname}</strong> is no longer mentored
    </p>
  );
}

export function handleResponse(
  response,
  successMessage,
  successCallback = () => {}
) {
  if (response.ok) {
    notifySuccess(successMessage);
    successCallback();
  } else {
    notifyBackendError(response);
  }
}
