import Container from "react-bootstrap/Container";
import Table from "react-bootstrap/Table";
import React, { useState, useEffect } from "react";
import { notifyUnexpectedBackendError } from "../../util/notify";
import { api } from "../../util/api";
import Spinner from "react-bootstrap/Spinner";
import EventRow from "./EventRow";

export function Event(props) {

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

  function sortedEvents() {
    return events.sort(events.created).reverse();
  }
  
  return (
  <Container>
    {!events &&
      <Spinner animation="grow" />
    }
    {events && 
    <Container>
      <Table striped bordered>
      <thead>
        <tr>
          <th>Date</th>
          <th>Message</th>
        </tr>
      </thead>
      <tbody>
      {sortedEvents().map(event => (
          <EventRow event={event} />
      ))}
      </tbody>
      </Table>
    </Container>
    }
  </Container>
  );
}