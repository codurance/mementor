import React from "react";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import { api } from "../../util/api";
import { Typeahead } from "react-bootstrap-typeahead";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faTimes } from "@fortawesome/free-solid-svg-icons";
import { filterCraftspeople } from "../../util/filtering";
import Button from "react-bootstrap/Button";
import {notifySuccess, notifyMentorAdded, notifyBackendError, notifyUnexpectedBackendError, notifyMentorRemoved, handleResponse} from '../notification/notify';

export default function Craftsperson({ craftsperson, craftspeople, rerender }) {
  function setLastMeeting(date) {
    api({
      endpoint: "/craftspeople/lastmeeting",
      type: "PUT",
      body: {
        craftspersonId: craftsperson.id,
        lastMeeting: date.getTime() / 1000,
      },
    }).then(response => {
      handleResponse(response, 'Last meeting updated', rerender);
    });
  }

  let mentorSelect = React.createRef();

  function addMentorCallBack(selectedCraftspeople) {
    if (selectedCraftspeople.length === 0) {
      // nothing to do
      return; 
    }
    api({
      endpoint: "/craftspeople/mentor/add",
      type: "POST",
      body: {
        mentorId: selectedCraftspeople[0].id,
        menteeId: craftsperson.id,
      },
    }).then(response => {
        const mentorFirstname = selectedCraftspeople[0].firstName;
        const menteeFirstname = craftsperson.firstName;
        const successMessage = (
          <p>
            <strong>{mentorFirstname}</strong> is now mentoring <strong>{menteeFirstname}</strong>
          </p>
        );
      handleResponse(response, successMessage, rerender);
    });
  }

  function removeMentorCallback() {
    api({
      endpoint: "/craftspeople/mentor/remove",
      type: "POST",
      body: {
        menteeId: craftsperson.id,
      },
    }).then(response => {
      if (response.ok) {
        notifyMentorRemoved(craftsperson.firstName)
        mentorSelect.current.clear();
        rerender();
        return;
      }
      return notifyUnexpectedBackendError(response);
    });
  }

  function getCraftspersonMentorNameOrNull() {
    if (!craftsperson.mentor) {
      return "";
    }
    return craftsperson.mentor.firstName + " " + craftsperson.mentor.lastName;
  }

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
        <div className="row">
          <Typeahead
            id={"remove-mentor-" + craftsperson.id}
            defaultInputValue={getCraftspersonMentorNameOrNull()}
            ref={mentorSelect}
            inputProps={{ "data-testid": "add-mentor-select" }}
            labelKey={option => `${option.firstName} ${option.lastName}`}
            options={filterCraftspeople(craftspeople, craftsperson)}
            placeholder="Select a mentor"
            onChange={addMentorCallBack}
          />
          {craftsperson.mentor && (
            <Button
              className="remove-button"
              variant="danger"
              data-testid="removementeebutton"
              onClick={removeMentorCallback}
            >
              <FontAwesomeIcon className="times-icon" icon={faTimes} size="sm" />
            </Button>
          )}
        </div>
      </div>
      <div className="col-lg-3">
        <h5>
          <span className="meetingLabel">Last Meeting:</span>
          <br />
          {craftsperson.mentor ? (
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
          ) : (
            "-"
          )}
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
