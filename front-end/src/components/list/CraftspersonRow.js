import React from "react";
import Craftsperson from "./Craftsperson";
import Mentees from "./Mentees";
import Card from "react-bootstrap/Card";
import Accordion from "react-bootstrap/Accordion";
import Button from "react-bootstrap/Button";
import "./CraftspersonRow.css";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faChevronDown } from "@fortawesome/free-solid-svg-icons";

export default function CraftspersonRow({
  craftsperson,
  craftspeople,
  rerender,
}) {

  return (
    <Accordion>
      <div className="container">
        <Card>
          <Card.Header>
              <div className="justify-content-center">
                <Craftsperson craftsperson={craftsperson} craftspeople={craftspeople} rerender={rerender} />
              </div>
              <div className="row">
                <Accordion.Toggle
                  className="mentees-toggle"
                  as={Button}
                  variant="light"
                  eventKey="0"
                >
                  <FontAwesomeIcon icon={faChevronDown}/>
                </Accordion.Toggle>
              </div>
          </Card.Header>
          <Accordion.Collapse eventKey="0">
            <Card.Body>
              <Mentees
                rerender={rerender}
                craftsperson={craftsperson}
                mentees={craftsperson.mentees}
                craftspeople={craftspeople}
              />
            </Card.Body>
          </Accordion.Collapse>
        </Card>
      </div>
    </Accordion>
  );
}
