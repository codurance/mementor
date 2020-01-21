import React from "react";

export function MentorAddedMessage({ mentorFirstName, menteeFirstname }) {
  return (
    <p>
      <strong>{mentorFirstName}</strong> is now mentoring <strong>{menteeFirstname}</strong>
    </p>
  );
}