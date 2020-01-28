import React, { useState } from "react";
import Button from "react-bootstrap/Button";
import Row from "react-bootstrap/Row";
import FormControl from "react-bootstrap/FormControl";
import InputGroup from "react-bootstrap/InputGroup";
import "./ManageCraftsperson.css";
import { api } from "../../util/api";
import { handleResponse, notifyFormValidationError } from "../../util/notify";
import Container from "react-bootstrap/Container";

export default function LastMeetingThreshold({
  rerender,
  setFetchConfig,
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
      handleResponse(response, "Last meeting threshold updated", () =>
        setFetchConfig()
      );
    });
  }

  return (
    <Container>
      <Row>
        <h5 className="admin-label">
          Amount of allowed weeks for last meeting
        </h5>
        <InputGroup className="mb-3">
          <FormControl
            required
            onChange={e => setLastMeetingThreshold(e.target.value)}
            value={lastMeetingThreshold}
            placeholder="Select a last meeting threshold"
          />
          <InputGroup.Append>
            <Button onClick={updateLastMeetingThresholdsInWeeks}>Update</Button>
          </InputGroup.Append>
        </InputGroup>
      </Row>
    </Container>
  );
}
