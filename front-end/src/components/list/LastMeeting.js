import React from "react";
import { api } from "../../util/api";
import { handleResponse } from "../notification/notify";
import Col from "react-bootstrap/Col";
import Row from "react-bootstrap/Row";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import "./LastMeeting.css";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faExclamationTriangle } from "@fortawesome/free-solid-svg-icons";
import { validateLastMeetingThresold } from "../../util/date";

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
    <Col lg className="last-meeting-container">
      <h5 className="last-meeting-label">Last Meeting</h5>
      <Row className="last-meeting-picker-container">
        <span data-testid="date-picker-container">
          {!craftsperson.mentor && "-"}
          {craftsperson.mentor && (
            <DatePicker
              className="date-picker"
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
          {craftsperson.lastMeeting &&
            !validateLastMeetingThresold(craftsperson.lastMeeting) && (
              <FontAwesomeIcon
                data-testid="last-meeting-alert"
                icon={faExclamationTriangle}
              />
            )}
        </span>
      </Row>
    </Col>
  );
}
