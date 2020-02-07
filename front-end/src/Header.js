import Container from "react-bootstrap/Container";
import Image from "react-bootstrap/Image";
import logo from "./mementor_logo.png";
import React from "react";

export function Header() {
  return <Container>
    <Image className="main-logo" src={logo}/>
  </Container>;
}