import React, { useState } from "react";
import Modal from "react-bootstrap/Modal";
import Button from "react-bootstrap/Button";
import Container from "react-bootstrap/Container";
import Row from "react-bootstrap/Row";
import CraftspersonList from "./CraftspersonList";
import "./ManageCraftsperson.css";
import { api } from "../../util/api";

export default function ManageCraftsperson(props) {
  const [show, setShow] = useState(false);
  const handleShow = () => setShow(true);
  const [idToDelete, setIdToDelete] = useState(null);
  
  const handleClose = () => {
    setIdToDelete(null);
    setShow(false);
  };
  
  function getSelectedId(id) {
    return setIdToDelete(id);
  }
  
  function deleteCraftsperson(id) {
    if (id){
    api(`craftspeople/${id}`, {
      method: "DELETE"
    })
      .then(() => {
        props.rerender();
      })
      .catch(error => {
        console.log(error);
      });
    }      
  }

  return (
    <div className="row admin-button">
      <Button
        variant="secondary"
        data-testid="adminPopupButton"
        onClick={handleShow}
      >
        <strong>+/-</strong> Craftspeople
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
    </div>
  );
}
