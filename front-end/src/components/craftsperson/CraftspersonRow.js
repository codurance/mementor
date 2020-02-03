import React, { useState } from "react";
import Craftsperson from "./Craftsperson";
import Mentees from "../mentee/Mentees";
import Card from "react-bootstrap/Card";
import Row from "react-bootstrap/Row";
import Container from "react-bootstrap/Container";
import Accordion from "react-bootstrap/Accordion";
import Button from "react-bootstrap/Button";
import "./CraftspersonRow.css";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faChevronDown, faChevronUp } from "@fortawesome/free-solid-svg-icons";

export default function CraftspersonRow({
  craftsperson,
  craftspeople,
  refreshCraftspeople,
  lastMeetingThresholdsInWeeks,
  idToken
}) {
  const [isCollapsed, setIsCollapsed] = useState(false);

  function makeId() {
    return "craftsperson-row-container-" + craftsperson.id;
  }

  function refreshCraftspeopleWithRowId() {
    refreshCraftspeople(makeId());
  }

  return (
    <Accordion className="accordion-container craftsperson-row-container" id={makeId()}>
      <Container>
        <Card>
          <Card.Header className="craftsperson-card">
              <Craftsperson
                craftsperson={craftsperson}
                craftspeople={craftspeople}
                refreshCraftspeople={refreshCraftspeopleWithRowId}
                idToken={idToken}
                lastMeetingThresholdsInWeeks={lastMeetingThresholdsInWeeks}
              />
            <Row>
              <Accordion.Toggle
                className="mentees-toggle"
                as={Button}
                variant="link"
                eventKey="0"
                onClick={() => setIsCollapsed(!isCollapsed)}>
                <FontAwesomeIcon icon={isCollapsed ? faChevronUp : faChevronDown}/>
              </Accordion.Toggle>
            </Row>
          </Card.Header>
          <Accordion.Collapse eventKey="0">
            <Card.Body className="mentees-container">
              <Mentees
                refreshCraftspeople={refreshCraftspeopleWithRowId}
                craftsperson={craftsperson}
                mentees={craftsperson.mentees}
                craftspeople={craftspeople}
                idToken={idToken}
              />
            </Card.Body>
          </Accordion.Collapse>
        </Card>
      </Container>
    </Accordion>
  );
}
