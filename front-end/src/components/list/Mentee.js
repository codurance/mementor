import React from "react";
import ListGroupItem from "react-bootstrap/ListGroupItem";
import Button from "react-bootstrap/Button";
import Container from "react-bootstrap/Container";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import { api } from "./../../util/api";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faTimes } from "@fortawesome/free-solid-svg-icons";
import "./Mentee.css";
import { handleResponse, mentorRemovedMessage } from "../notification/notify";

export default function Mentee({ mentee, rerender }) {
  function removeMentee() {
    api({
      endpoint: `/craftspeople/mentee/remove/${mentee.id}`,
      type: "PUT",
    }).then(response => {
      handleResponse(
        response,
        mentorRemovedMessage(mentee.firstName),
        rerender,
      );
    });
  }

  return (
    <ListGroupItem data-testid={`mentee-${mentee.id}`}>
      <Container>
        <Row>
          <Col sm={1}>
            <Button
              className="remove-button"
              variant="danger"
              data-testid="removementeebutton"
              onClick={() => removeMentee()}
            >
              <FontAwesomeIcon
                className="times-icon"
                icon={faTimes}
                size="sm"
              />
            </Button>
          </Col>
          <Col sm={11}>
            <h5 className="mentee-name menteeName">
              {mentee.firstName} {mentee.lastName}
            </h5>
          </Col>
        </Row>
      </Container>
    </ListGroupItem>
  );
}
