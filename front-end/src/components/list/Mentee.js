import React from "react";
import ListGroupItem from "react-bootstrap/ListGroupItem";
import Button from "react-bootstrap/Button";
import Container from "react-bootstrap/Container";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import { api } from "./../../util/api";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faTimes } from "@fortawesome/free-solid-svg-icons";

export default function Mentee({ mentee, rerender }) {
  function removeMentee() {
    api({
      endpoint: `/craftspeople/mentee/remove/${mentee.id}`,
      type: "PUT",
    });
    rerender();
  }

  return (
    <ListGroupItem>
      <Container>
        <Row>
          <Col />
          <Col>
            <h4 className="menteeName">
              {mentee.firstName} {mentee.lastName}
            </h4>
          </Col>
          <Col>
            <Button
              className="remove-button"
              variant="danger"
              data-testid="removementeebutton"
              onClick={() => removeMentee()}
            >
              <FontAwesomeIcon icon={faTimes} size="lg" />
            </Button>
          </Col>
        </Row>
      </Container>
    </ListGroupItem>
  );
}
