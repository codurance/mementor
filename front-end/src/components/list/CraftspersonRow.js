import React, { useState } from "react";
import Craftsperson from "./Craftsperson";
import Mentees from "./Mentees";
import Card from "react-bootstrap/Card";
import Row from "react-bootstrap/Row"
import Container from "react-bootstrap/Container"
import Accordion from "react-bootstrap/Accordion";
import Button from "react-bootstrap/Button";
import "./CraftspersonRow.css";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faChevronDown, faChevronUp } from "@fortawesome/free-solid-svg-icons";

export default function CraftspersonRow({
  craftsperson,
  craftspeople,
  rerender,
}) {
  const [isCollapsed, setIsCollapsed] = useState(false);

  return (
    <Accordion className="accordion-container">
      <Container>
        <Card>
          <Card.Header className="craftsperson-card">
            <div className="justify-content-center">
              <Craftsperson
                craftsperson={craftsperson}
                craftspeople={craftspeople}
                rerender={rerender}
              />
            </div>
            <Row>
              <Accordion.Toggle
                className="mentees-toggle"
                as={Button}
                variant="link"
                eventKey="0"
                onClick={() => setIsCollapsed(!isCollapsed)}
              >
                <FontAwesomeIcon
                  icon={isCollapsed ? faChevronUp : faChevronDown}
                />
              </Accordion.Toggle>
            </Row>
          </Card.Header>
          <Accordion.Collapse eventKey="0">
            <Card.Body class="mentees-container">
              <Mentees
                rerender={rerender}
                craftsperson={craftsperson}
                mentees={craftsperson.mentees}
                craftspeople={craftspeople}
              />
            </Card.Body>
          </Accordion.Collapse>
        </Card>
      </Container>
    </Accordion>
  );
}
