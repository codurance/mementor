import React, { useState } from "react";
import Button from "react-bootstrap/Button";
import Row from "react-bootstrap/Row";
import FormControl from "react-bootstrap/FormControl";
import InputGroup from "react-bootstrap/InputGroup";
import "./ManageCraftsperson.css";
import { api } from "../../util/api";
import {
  handleResponse,
  notifyFormValidationError
} from "../notification/notify";

export default function LastMeetingThreshold({
  rerender,
  lastMeetingThresholdDefaultValue,
  idToken
}) {
  const [lastMeetingThreshold, setLastMeetingThreshold] = useState(
    lastMeetingThresholdDefaultValue
  );

  function updateLastMeetingThresholdsInWeeks() {
    if (!lastMeetingThreshold || lastMeetingThreshold <= 0) {
      notifyFormValidationError("You need to select a value greater than zero");
      return;
    }

    api({
      endpoint: `/config`,
      token: idToken,
      type: "PUT",
      body: {
        lastMeetingThresholdsInWeeks: lastMeetingThreshold
      }
    }).then(response => {
      handleResponse(response, "Last meeting threshold updated", rerender);
    });
  }

  return (
    <Row>
      <InputGroup className="mb-3">
        <FormControl
          required
          onChange={e => setLastMeetingThreshold(e.target.value)}
          value={lastMeetingThreshold}
        />
        <InputGroup.Append>
          <Button onClick={updateLastMeetingThresholdsInWeeks}>Update</Button>
        </InputGroup.Append>
      </InputGroup>
    </Row>
  );
}
