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
      <Col xs={{ span: 10, offset: 1}} sm={{ span:10, offset: 1}} md={{ span:6, offset: 3}} lg={{ span:3, offset: 0}} className="craftsperson-name-container">
        <h4 className="craftsperson-name" data-testid="craftspersonName">
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
      <Col xs={{ span: 10, offset: 1}} sm={{ span:10, offset: 1}} md={{ span:6, offset: 3}} lg={{ span:3, offset: 0}} className="mentees-count-container">
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
