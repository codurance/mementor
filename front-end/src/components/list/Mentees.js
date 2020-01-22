import React from "react";
import ListGroup from "react-bootstrap/ListGroup";
import Mentee from "./Mentee";
import ListGroupItem from "react-bootstrap/ListGroupItem";
import { Typeahead } from "react-bootstrap-typeahead";
import { sortAlphabetically } from "../../util/sorting";
import { api } from "../../util/api";
import "./Mentees.css";
import { filterCraftspeople } from "../../util/filtering";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import { handleResponse, mentorAddedMessage } from "../notification/notify";

export default function Mentees(props) {
  function addMentee(mentee, mentor) {
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
      <h3 className="mentees-title">Mentees</h3>
      <ListGroup data-testid="list">
        {props.mentees.sort(sortAlphabetically).map(mentee => (
          <Mentee key={mentee.id} rerender={props.rerender} mentee={mentee} />
        ))}
        <ListGroupItem
          className="mentees-list-item"
          data-testid="add-mentee-row"
        >
          <Row>
            <Col sm={4}>
              <h4 className="add-mentee">Add mentee</h4>
            </Col>
            <Col sm={8}>
              <Typeahead
                id={"add-mentee-" + props.craftsperson.id}
                labelKey={option => `${option.firstName} ${option.lastName}`}
                placeholder="Select a mentee"
                options={filterCraftspeople(
                  props.craftspeople,
                  props.craftsperson,
                )}
                onChange={selected =>
                  addMentee(selected[0], props.craftsperson)
                }
              />
            </Col>
          </Row>
        </ListGroupItem>
      </ListGroup>
    </div>
  );
}
