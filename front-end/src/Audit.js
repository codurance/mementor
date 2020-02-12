import Container from "react-bootstrap/Container";
import Col from "react-bootstrap/Col"
import Row from "react-bootstrap/Row";
import React, { useState, useEffect } from "react";
import { notifyUnexpectedBackendError } from "./util/notify";
import { api } from "./util/api";
import Spinner from "react-bootstrap/Spinner";
import AuditRow from "./AuditRow";

export function Audit(props) {

  const [events, setEvents] = useState(null);

  const eventList = () => {
    api({
      endpoint: "/events", token: props.idToken
    }).then(response => response.json())
      .then(fetchedEvents => {
        setEvents(fetchedEvents)
      })
      .catch(error => {
        notifyUnexpectedBackendError(error);
      });
  };

  useEffect(() => {
    eventList();
  }, [])

  return (
  <Container>
    {!events &&
      <Spinner animation="grow" />
    }
    {events && 
    <Container>
      {events.map(event => (
          <AuditRow message={event.message} />
      ))}
    </Container>
    }
  </Container>
  );
}