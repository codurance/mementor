import React from "react";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import { api } from "../../util/api";
import { handleResponse } from "../notification/notify";

export default function lastMeeting({ craftsperson, craftspeople, rerender }) {
  function setLastMeeting(date) {
    api({
      endpoint: "/craftspeople/lastmeeting",
      type: "PUT",
      body: {
        craftspersonId: craftsperson.id,
        lastMeeting: date.getTime() / 1000,
      },
    }).then(response => {
      handleResponse(response, "Last meeting updated", rerender);
    });
  }

  return (
    <div className="col-lg-3">
      <h5>
        <span className="meetingLabel">Last Meeting:</span>
        <br />
        <span data-testid="date-picker-container">
          {!craftsperson.mentor && '-'}
        {craftsperson.mentor && (
          <DatePicker
            selected={
              craftsperson.lastMeeting
                ? new Date(craftsperson.lastMeeting * 1000)
                : null
            }
            placeholderText="Select date..."
            dateFormat="dd MMMM yyyy"
            customInput={
              <input data-testid="lastMeetingDatePicker" type="text" />
            }
            onChange={setLastMeeting}
          />
        )}
        </span>
      </h5>
    </div>
  );
}
