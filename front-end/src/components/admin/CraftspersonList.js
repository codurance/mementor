import React from "react";
import ListGroup from "react-bootstrap/ListGroup";
import "./CraftspersonList.css";
import { sortAlphabetically } from "../../util/sorting"

export default function CraftspersonList(props) {
  return (
    <ListGroup variant="flush">
      {props.craftspeople.sort(sortAlphabetically).map(craftsperson => (
        <ListGroup.Item action href="#1">
          {craftsperson.firstName} {craftsperson.lastName}
        </ListGroup.Item>
      ))}

      {/* {craftspeople.craftspeople.map(craftsperson => (
        <ListGroup.Item action href="#1">
            {craftsperson}
        </ListGroup.Item>
      ))}; */}
    </ListGroup>
  );
}
