import React from "react";
import Col from "react-bootstrap/Col";
import Row from "react-bootstrap/Row";
import Mentor from "./Mentor";
import LastMeeting from "./LastMeeting";
import "./Craftsperson.css";

export default function Craftsperson({ craftsperson, craftspeople, rerender }) {
  return (
    <Row>
      <Col lg className="craftsperson-name-container">
        <h2 className="craftsperson-name" data-testid="craftspersonName">
          {craftsperson.firstName} {craftsperson.lastName}
        </h2>
      </Col>
      <Mentor
        craftsperson={craftsperson}
        craftspeople={craftspeople}
        rerender={rerender}
      />
      <LastMeeting
        craftsperson={craftsperson}
        craftspeople={craftspeople}
        rerender={rerender}
      />
      <Col lg className="mentees-count-container">
        <h5
          className="mentees-count-label"
          data-testid="craftspersonMenteeLabel"
        >
          Mentees
        </h5>
        <h2 className="mentee-count" data-testid="craftspersonMenteeValue">
          {craftsperson.mentees ? craftsperson.mentees.length : "0"}
        </h2>
      </Col>
    </Row>
  );
}
