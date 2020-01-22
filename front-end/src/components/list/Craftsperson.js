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
      <Col lg>
        <span className="mentee-count">
          <h2 data-testid="craftspersonMenteeValue">
            {craftsperson.mentees ? craftsperson.mentees.length : "0"}
          </h2>
        </span>
        <span className="menteeLabel" data-testid="craftspersonMenteeLabel">
          <i>Mentees</i>
        </span>
      </Col>
    </Row>
  );
}
