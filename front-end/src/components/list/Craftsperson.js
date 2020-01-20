import React from "react";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";

export default function Craftsperson({ craftsperson }) {
  return (
    <div className="row">
      <div className="col-lg-3">
        <h2 className="craftspersonName" data-testid="craftspersonName">
          {craftsperson.firstName} {craftsperson.lastName}
        </h2>
      </div>
      <div className="col-lg-3">
        <h5>
          <span className="mentorLabel" data-testid="craftspersonMentorLabel">
            Mentored by:
          </span>
        </h5>
      </div>
      <div className="col-lg-3">
        <h5>
          <span className="meetingLabel">Last Meeting:</span>
          <br />
          {craftsperson.mentor ?
            (<DatePicker data-testid="lastMeetingValue"
            selected={craftsperson.lastMeeting ? new Date(craftsperson.lastMeeting * 1000) : null}
            placeholderText="Select date..."
            dateFormat="dd MMMM yyyy"
            customInput={<input data-testid="lastMeetingValue" type="text" />}
            onChange={date =>
              alert(
                date.toLocaleDateString(undefined, {
                  day: "numeric",
                  month: "long",
                  year: "numeric"
                })
              )
            }
          />)
          : '-'}
        </h5>
      </div>
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
