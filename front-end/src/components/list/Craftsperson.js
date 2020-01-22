import React from "react";
import Mentor from "./Mentor";
import LastMeeting from "./LastMeeting";

export default function Craftsperson({ craftsperson, craftspeople, rerender, idToken }) {
  return (
    <div className="row">
      <div className="col-lg-3">
        <h2 className="craftspersonName" data-testid="craftspersonName">
          {craftsperson.firstName} {craftsperson.lastName}
        </h2>
      </div>
      <Mentor
        craftsperson={craftsperson}
        craftspeople={craftspeople}
        rerender={rerender}
        idToken={idToken}
      />
      <LastMeeting
        craftsperson={craftsperson}
        craftspeople={craftspeople}
        rerender={rerender}
        idToken={idToken}
      />
      <div className="col-lg-3">
        <span className="mentee-count">
          <h2 data-testid="craftspersonMenteeValue">
            {craftsperson.mentees ? craftsperson.mentees.length : "0"}
          </h2>
        </span>
        <span className="menteeLabel" data-testid="craftspersonMenteeLabel">
          <i>Mentees</i>
        </span>
      </div>
    </div>
  );
}
