import React, { useState, useEffect } from "react";
import Modal from "react-bootstrap/Modal";
import Button from "react-bootstrap/Button";
import Container from "react-bootstrap/Container";
import Row from "react-bootstrap/Row";
import FormControl from "react-bootstrap/FormControl";
import InputGroup from "react-bootstrap/InputGroup";
import CraftspersonList from "./CraftspersonList";
import "./ManageCraftsperson.css";
import { api } from "../../util/api";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faCog } from "@fortawesome/free-solid-svg-icons";
import { validateName } from "../../util/validate";
import {
  handleResponse,
  notifyFormValidationError
} from "../notification/notify";

export default function LastMeetingThreshold({rerender, lastMeetingThresholdDefaultValue, idToken}) {
  const [lastMeetingThreshold, setLastMeetingThreshold] = useState(lastMeetingThresholdDefaultValue);

  function updateLastMeetingThresholdsInWeeks() {
    api({
      endpoint: `/config`,
      token: idToken,
      type: "PUT",
      body: {
        lastMeetingThresholdsInWeeks: lastMeetingThreshold
      }
    }).then(response => {
      handleResponse(response, "Craftsperson removed", () => {
        rerender();
      });
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
