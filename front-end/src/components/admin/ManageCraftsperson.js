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
} from "../../util/notify";
import LastMeetingThreshold from "./LastMeetingThreshold";

export default function ManageCraftsperson(props) {
  const [show, setShow] = useState(false);
  const handleShow = () => setShow(true);
  const [idToDelete, setIdToDelete] = useState(null);
  const [firstName, setFirstName] = useState(null);
  const [lastName, setLastName] = useState(null);
  
  const handleClose = () => {
    setIdToDelete(null);
    setShow(false);
  };

  function getSelectedId(id) {
    return setIdToDelete(id);
  }

  function addCraftsperson() {
    const { firstNameValid, lastNameValid } = validateName(firstName, lastName);

    if (!firstNameValid && !lastNameValid) {
      notifyFormValidationError("You must enter a first and last name!");
      return;
    }
    if (!firstNameValid) {
      notifyFormValidationError("You must enter a first name!");
      return;
    }
    if (!lastNameValid) {
      notifyFormValidationError("You must enter a last name!");
      return;
    }
    api({
      endpoint: "/craftspeople/add",
      token: props.idToken,
      type: "POST",
      body: {
        firstName,
        lastName
      }
    }).then(response => {
      handleResponse(response, "Craftsperson added", props.rerender);
    });
  }

  const handleFirstName = event => {
    setFirstName(event.target.value);
  };

  const handleLastName = event => {
    setLastName(event.target.value);
  };

  function deleteCraftsperson(id) {
    if (id) {
      api({
        endpoint: `/craftspeople/${id}`,
        token: props.idToken,
        type: "DELETE"
      }).then(response => {
        handleResponse(response, "Craftsperson removed", () => {
          setIdToDelete(null);
          props.rerender();
        });
      });
    }
  }

  return (
    <Row className="admin-button">
      <Button
        variant="secondary"
        data-testid="adminPopupButton"
        onClick={handleShow}
      >
        <FontAwesomeIcon icon={faCog} /> Craftspeople
      </Button>

      <Modal show={show} onHide={handleClose}>
        <Modal.Header closeButton>
          <Container>
            <Row>
              <Modal.Title>Add or Remove Craftsperson</Modal.Title>
            </Row>
          </Container>
        </Modal.Header>
        <Modal.Body>
          <LastMeetingThreshold
            lastMeetingThresholdDefaultValue={
              props.lastMeetingThresholdDefaultValue
            }
            idToken={props.idToken}
            rerender={props.rerender}
          />
          <InputGroup className="mb-3">
            <FormControl
              required
              onChange={handleFirstName}
              placeholder="First Name..."
            />
            <FormControl
              required
              onChange={handleLastName}
              placeholder="Last Name..."
            />
            <InputGroup.Append>
              <Button onClick={() => addCraftsperson()}>Add</Button>
            </InputGroup.Append>
          </InputGroup>
          <CraftspersonList
            craftspeople={props.craftspeople}
            click={getSelectedId}
          />
        </Modal.Body>
        <Modal.Footer>
          <Button variant="light" onClick={handleClose}>
            Cancel
          </Button>
          <Button
            data-testid="removeCraftspersonButton"
            variant="danger"
            onClick={() => deleteCraftsperson(idToDelete)}
          >
            Remove
          </Button>
        </Modal.Footer>
      </Modal>
    </Row>
  );
}
