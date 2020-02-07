import Container from "react-bootstrap/Container";
import React from "react";

export function ErrorFetch() {
  return (
    <Container className="alert alert-danger" role="alert">
      <strong>Oh snap!</strong> Looks like there was an error while
      fetching the data.
    </Container>);
}