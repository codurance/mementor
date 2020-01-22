import React from 'react';
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

export function notifyMentorAdded(mentorFirstname, menteeFirstname) {
  notifySuccess((
    <p>
      <strong>{mentorFirstname}</strong> is now mentoring <strong>{menteeFirstname}</strong>
    </p>
  ));
}

export function notifyMentorRemoved(menteeFirstname) {
  notifySuccess((
    <p>
      <strong>{menteeFirstname}</strong> is no longer mentored
    </p>
  ));
}