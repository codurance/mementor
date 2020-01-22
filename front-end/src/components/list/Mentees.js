import React, { useState } from "react";
import ListGroup from "react-bootstrap/ListGroup";
import Mentee from "./Mentee";
import ListGroupItem from "react-bootstrap/ListGroupItem";
import Button from "react-bootstrap/Button";
import { Typeahead } from "react-bootstrap-typeahead";
import { sortAlphabetically } from "../../util/sorting";
import { api } from "../../util/api";
import "./Mentees.css";
import { filterCraftspeople } from "../../util/filtering";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import { handleResponse, mentorAddedMessage, notifyFormValidationError } from "../notification/notify";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faPlus } from "@fortawesome/free-solid-svg-icons";

export default function Mentees(props) {

  const [menteeToAdd, setMenteeToAdd] = useState(null);

  function addMentee(mentee, mentor) {
    if (mentee == null) {
      notifyFormValidationError("You need to select a mentee");
    }
    if (mentee != null) {
      api({
        endpoint: "/craftspeople/mentee/add",
        type: "PUT",
        body: {
          mentorId: mentor.id,
          menteeId: mentee.id,
        },
      }).then(response => {
        handleResponse(
          response,
          mentorAddedMessage(mentor.firstName, mentee.firstName),
          props.rerender,
        );
      });
    }
  }

  return (
    <div>
      <ListGroup data-testid="list">
        {props.mentees.sort(sortAlphabetically).map(mentee => (
          <Mentee key={mentee.id} rerender={props.rerender} mentee={mentee} />
        ))}
        <ListGroupItem
          className="mentees-list-item"
          data-testid="add-mentee-row"
        >
          <Row>
            <Col sm={1}>
              <Button
                className="add-button"
                variant="success"
                data-testid="removementeebutton"
              >
                <FontAwesomeIcon
                  className="plus-icon"
                  icon={faPlus}
                  size="sm"
                  onClick={() => addMentee(menteeToAdd, props.craftsperson)}
                />
              </Button>
            </Col>
            <Col sm={11}>
              <Typeahead
                id={"add-mentee-" + props.craftsperson.id}
                labelKey={option => `${option.firstName} ${option.lastName}`}
                placeholder="Select a mentee"
                options={filterCraftspeople(
                  props.craftspeople,
                  props.craftsperson,
                )}
                onChange={selected =>
                  setMenteeToAdd(selected[0])
                }
              />
            </Col>
          </Row>
        </ListGroupItem>
      </ListGroup>
    </div>
  );
}
