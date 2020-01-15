import React from "react";
import ListGroup from "react-bootstrap/ListGroup";
import "./CraftspersonList.css";
import { sortAlphabetically } from "../../util/sorting"

export default function CraftspersonList(props) {
  return (
    <ListGroup variant="flush" data-testid="craftspersonList">
      {props.craftspeople.sort(sortAlphabetically).map(craftsperson => (
        <ListGroup.Item data-testid="craftspersonRowItem" action onClick={() => props.click(craftsperson.id)} eventKey={craftsperson.id}>
          {craftsperson.firstName} {craftsperson.lastName}
        </ListGroup.Item>
      ))}
    </ListGroup>
  );
}
