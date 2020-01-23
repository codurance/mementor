import ButtonToolbar from "react-bootstrap/ButtonToolbar";
import ToggleButtonGroup from "react-bootstrap/ToggleButtonGroup";
import ToggleButton from "react-bootstrap/ToggleButton";
import React from "react";
import Container from "react-bootstrap/Container";
import Col from "react-bootstrap/Col"
import Row from "react-bootstrap/Row"
import "./SortingBar.css"

export function SortingBar(props) {
  return (
    <Container className="sorting-bar-container">
      <Row>
        <Col sm={2}>
          <h5 className="sort-by-label">Sort by: </h5>
        </Col>
        <Col sm={10}>
          <ButtonToolbar>
            <ToggleButtonGroup type="radio" name="options" defaultValue={1}>
              <ToggleButton
                variant="light"
                onClick={props.onClick}
                prechecked
                value={1}
              >
                number of mentees
              </ToggleButton>
              <ToggleButton variant="light" onClick={props.onClick1} value={2}>
                unmentored craftsperson
              </ToggleButton>
              <ToggleButton variant="light" onClick={props.onClick2} value={3}>
                oldest last meeting
              </ToggleButton>
            </ToggleButtonGroup>
          </ButtonToolbar>
        </Col>
      </Row>
    </Container>
  );
}
