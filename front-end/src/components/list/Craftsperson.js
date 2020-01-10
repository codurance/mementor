import React from "react";

export default function Craftsperson({ craftsperson }) {
  return (
    <div class="row">
      <div class="col-lg-4">
        <h2>
          {craftsperson.firstName} {craftsperson.lastName}
        </h2>
      </div>
      <div class="col-lg-4">
        <h5><span className="mentorLabel">Mentor by:</span><br />
        <span className="mentor">
          {craftsperson.mentor
            ? craftsperson.mentor.firstName + " " + craftsperson.mentor.lastName
            : "-"}{" "}
        </span></h5>
      </div>
      <div class="col-lg-4">
        <span className="menteeLabel">Mentees: </span>
        <span className="mentee-count">
          <h2>{craftsperson.mentees ? craftsperson.mentees.length : "0"}</h2>
        </span>
      </div>
    </div>
  );
}
