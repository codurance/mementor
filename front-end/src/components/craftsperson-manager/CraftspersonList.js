import React from "react";
import ListGroup from "react-bootstrap/ListGroup";
import "./CraftspersonList.css";
import { sortAlphabetically } from "../../util/sorting";

export default function CraftspersonList(props) {
  return (
    <ListGroup
      className="craftsperson-list"
      variant="flush"
      data-testid="craftspersonList"
    >
      {props.craftspeople
        .slice()
        .sort(sortAlphabetically)
        .map(craftsperson => (
          <ListGroup.Item
            action
            key={craftsperson.id}
            data-testid="craftspersonRowItem"
            onClick={() => props.click(craftsperson.id)}
            eventKey={craftsperson.id}
          >
            {craftsperson.firstName} {craftsperson.lastName}
          </ListGroup.Item>
        ))}
    </ListGroup>
  );
}
