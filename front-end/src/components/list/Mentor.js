import React from "react";
import { Typeahead } from "react-bootstrap-typeahead";
import Col from "react-bootstrap/Col";
import Row from "react-bootstrap/Row";
import {
  mentorAddedMessage,
  handleResponse,
  mentorRemovedMessage
} from "../notification/notify";
import { api } from "../../util/api";
import { filterCraftspeople } from "../../util/filtering";
import Button from "react-bootstrap/Button";
import { faTimes } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import "./Mentor.css";

export default function Mentor({
  craftsperson,
  craftspeople,
  rerender,
  idToken
}) {
  let mentorSelect = React.createRef();

  function addMentorCallBack(selectedCraftspeople) {
    if (selectedCraftspeople.length === 0) {
      // nothing to do
      return;
    }
    api({
      endpoint: "/craftspeople/mentor/add",
      token: idToken,
      type: "POST",
      body: {
        mentorId: selectedCraftspeople[0].id,
        menteeId: craftsperson.id
      }
    }).then(response => {
      const mentorFirstname = selectedCraftspeople[0].firstName;
      const menteeFirstname = craftsperson.firstName;
      handleResponse(
        response,
        mentorAddedMessage(mentorFirstname, menteeFirstname),
        rerender
      );
    });
  }

  function removeMentorCallback() {
    api({
      endpoint: "/craftspeople/mentor/remove",
      token: idToken,
      type: "POST",
      body: {
        menteeId: craftsperson.id
      }
    }).then(response => {
      handleResponse(
        response,
        mentorRemovedMessage(craftsperson.firstName),
        () => {
          mentorSelect.current.clear();
          rerender();
        }
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
    <Col lg className="mentor-container">
      <h5 className="mentor-label">Mentored by</h5>
      <Row className="mentor-dropdown-container">
        <Typeahead
          className="mentor-dropdown"
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
      </Row>
    </Col>
  );
}
