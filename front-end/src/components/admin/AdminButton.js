import React from "react";
import { useState } from "react";
import Modal from "react-bootstrap/Modal";
import Button from "react-bootstrap/Button";
import Container from "react-bootstrap/Container";
import Row from "react-bootstrap/Row";
import CraftspersonList from "./CraftspersonList";
import "./AdminButton.css";

export default function AdminButton() {
  const [show, setShow] = useState(false);

  const handleClose = () => setShow(false);
  const handleShow = () => setShow(true);

  return (
    <div className="row admin-button">
      <Button variant="secondary" onClick={handleShow}>
        AdminSomething
      </Button>

      <Modal show={show} onHide={handleClose}>
        <Modal.Header closeButton>
          <Container>
            <Row>
              <Modal.Title>Add or Remove Craftsperson</Modal.Title>
            </Row>
            <Row className="add-button-container">
              <Button
                className="add-button"
                variant="primary"
                onClick={handleClose}
              >
                <strong>+</strong> Add
              </Button>
            </Row>
          </Container>
        </Modal.Header>
        <Modal.Body>
          <CraftspersonList />
        </Modal.Body>
        <Modal.Footer>
          <Button variant="light" onClick={handleClose}>
            Cancel
          </Button>
          <Button variant="danger" onClick={handleClose}>
            Remove
          </Button>
        </Modal.Footer>
      </Modal>
    </div>
  );
}
