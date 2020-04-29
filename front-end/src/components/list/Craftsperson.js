import React from "react";
import Col from "react-bootstrap/Col";
import Row from "react-bootstrap/Row";
import Mentor from "./Mentor";
import LastMeeting from "./LastMeeting";
import "./Craftsperson.css";

export default function Craftsperson({
  craftsperson,
  craftspeople,
  refreshCraftspeople,
  idToken,
  lastMeetingThresholdsInWeeks
}) {
  return (
    <Row>
      <Col lg className="craftsperson-name-container">
        <h4 name={craftsperson.firstName + craftsperson.lastName} className="craftsperson-name" data-testid="craftspersonName">
          {craftsperson.firstName} {craftsperson.lastName}
        </h4>
      </Col>
      <Mentor
        craftsperson={craftsperson}
        craftspeople={craftspeople}
        refreshCraftspeople={refreshCraftspeople}
        idToken={idToken}
      />
      <LastMeeting
        craftsperson={craftsperson}
        craftspeople={craftspeople}
        refreshCraftspeople={refreshCraftspeople}
        idToken={idToken}
        lastMeetingThresholdsInWeeks={lastMeetingThresholdsInWeeks}
      />
      <Col lg={2} md={2} className="mentees-count-container">
        <h5
          className="mentees-count-label"
          data-testid="craftspersonMenteeLabel"
        >
          Mentees
        </h5>
        <h4 className="mentee-count" data-testid="craftspersonMenteeValue">
          {craftsperson.mentees ? craftsperson.mentees.length : "0"}
        </h4>
      </Col>
    </Row>
  );
}
