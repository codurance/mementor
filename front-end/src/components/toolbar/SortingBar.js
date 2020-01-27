import ToggleButtonGroup from "react-bootstrap/ToggleButtonGroup";
import ToggleButton from "react-bootstrap/ToggleButton";
import React from "react";
import Container from "react-bootstrap/Container";
import Col from "react-bootstrap/Col"
import Row from "react-bootstrap/Row"
import "./SortingBar.css"

export default function SortingBar(props) {
  return (
    <Container className="sorting-bar-container">
      <Row>
        <Col sm={2}>
          <h5 className="sort-by-label">Sort by: </h5>
        </Col>
        <Col sm={10}>
          <ToggleButtonGroup type="radio" name="options" defaultValue={1}>
            <ToggleButton variant="light" onClick={props.numberOfMenteesListener} prechecked value={1}>
              Number of mentees
            </ToggleButton>
            <ToggleButton variant="light" onClick={props.menteesWithoutMentorListener} value={2}>
              Unmentored craftsperson
            </ToggleButton>
            <ToggleButton variant="light" onClick={props.lastMeetingDateListener} value={3}>
              Last meeting
            </ToggleButton>
          </ToggleButtonGroup>
        </Col>
      </Row>
    </Container>
  );
}
