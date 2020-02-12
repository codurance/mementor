import Container from "react-bootstrap/Container";
import React from "react";
import { notifyUnexpectedBackendError } from "./util/notify";
import { api } from "./util/api";

export function Audit(props) {

  const auditList = () => {
    api({
      endpoint: "/audits", token: props.idToken
    }).then(response => response.json())
      .catch(error => {
        notifyUnexpectedBackendError(error);
        props.backEndError(error);
      });
  };

  return <Container>
    <h1>HOLA MUNDO</h1>
    {auditList.map(audit => (
      <Audit details={audit}/>
    ))}
  </Container>;
}