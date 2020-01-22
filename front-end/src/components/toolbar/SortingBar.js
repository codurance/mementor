import ButtonToolbar from "react-bootstrap/ButtonToolbar";
import ToggleButtonGroup from "react-bootstrap/ToggleButtonGroup";
import ToggleButton from "react-bootstrap/ToggleButton";
import React from "react";

export function SortingBar(props) {
  return (
    <ButtonToolbar>
      <ToggleButtonGroup type="radio" name="options" defaultValue={1}>
        <ToggleButton variant="light" onClick={props.onClick} prechecked value={1}>
          Sort by number of mentees
        </ToggleButton>
        <ToggleButton variant="light" onClick={props.onClick1} value={2}>
          Sort by mentor
        </ToggleButton>
        <ToggleButton variant="light" onClick={props.onClick2} value={3}>
          Sort by last met
        </ToggleButton>
      </ToggleButtonGroup>
    </ButtonToolbar>
  );
}
