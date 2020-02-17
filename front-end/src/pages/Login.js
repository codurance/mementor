import Container from "react-bootstrap/Container";
import Row from "react-bootstrap/Row";
import Image from "react-bootstrap/Image";
import logo from "../mementor_logo.png";
import GoogleLogin from "react-google-login";
import React from "react";

export function Login(props) {

  function responseGoogle(response) {
    console.log(response);
  }

  return (<Container>
    <Row>
      <Image className="main-logo-login" src={logo}/>
    </Row>
    <Row>
      <GoogleLogin
        className="google-login"
        clientId="232062837025-i97turm1tg41ian5hjaq1ujao6q2569i.apps.googleusercontent.com"
        buttonText="Login"
        onSuccess={props.onSuccess}
        onFailure={responseGoogle}
        cookiePolicy={"single_host_origin"}
      />
    </Row>
    <Row>
      <p className="love">With love by the 2019/2020 apprentices</p>
    </Row>
  </Container>);
}