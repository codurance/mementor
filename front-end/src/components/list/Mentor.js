import React from "react";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import { api } from "../../util/api";
import { Typeahead } from "react-bootstrap-typeahead";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faTimes } from "@fortawesome/free-solid-svg-icons";
import { filterCraftspeople } from "../../util/filtering";
import Button from "react-bootstrap/Button";
import {
  mentorAddedMessage,
  handleResponse,
  mentorRemovedMessage,
} from "../notification/notify";

export default function Mentor({ craftsperson, craftspeople, rerender }) {
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
      handleResponse(
        response,
        mentorAddedMessage(mentorFirstname, menteeFirstname),
        rerender,
      );
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
      handleResponse(
        response,
        mentorRemovedMessage(craftsperson.firstName),
        () => {
          mentorSelect.current.clear();
          rerender();
        },
      );
    });
  }

  function getCraftspersonMentorNameOrNull() {
    if (!craftsperson.mentor) {
      return "";
    }
    return craftsperson.mentor.firstName + " " + craftsperson.mentor.lastName;
  }
  return (
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
  );
}
