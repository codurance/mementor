import React, { useCallback, useState } from "react";
import { toast } from "react-toastify";
import "./App.css";
import "react-toastify/dist/ReactToastify.css";
import { Header } from "./Header";
import { ErrorFetch } from "./util/ErrorFetch";
import { Login } from "./pages/Login";
import { BrowserRouter as Router, Link, Route, Switch } from "react-router-dom";
import { MainView } from "./pages/MainView";
import { Event } from "./components/events/Event";
import { api } from "./util/api";
import { notifyUnexpectedBackendError } from "./util/notify";
import Container from "react-bootstrap/Container";
import { Button, Row } from "react-bootstrap";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faIgloo } from "@fortawesome/free-solid-svg-icons";

toast.configure();

function App() {
  const [backendFetchError, setBackendFetchError] = useState(null);
  const [idToken, setIdToken] = useState(null);
  const [
    lastMeetingThresholdsInWeeks,
    setLastMeetingThresholdsInWeeks
  ] = useState(null);

  function successfulLogin(googleUser) {
    setBackendFetchError(null);
    const id_token = googleUser.getAuthResponse().id_token;
    setIdToken(id_token);
  }

  const isUserLoggedIn = useCallback(() => {
    return idToken;
  }, [idToken]);

  const refreshConfig = useCallback(() => {
    api({ endpoint: `/config`, token: idToken })
      .then(response => response.json())
      .then(body =>
        setLastMeetingThresholdsInWeeks(body.lastMeetingThresholdsInWeeks)
      )
      .catch(notifyUnexpectedBackendError);
  }, [idToken, isUserLoggedIn]);

  return (
    <div className="App">
      <Router>
        {!isUserLoggedIn() && <Login onSuccess={successfulLogin} />}
        {isUserLoggedIn() && (
          <div>
            {backendFetchError && <ErrorFetch />}
            <Container>
              <Row>
                <Header />
              </Row>
            </Container>
            <Switch>
              <Route path="/events">
                <Container>
                  <Row>
                    <Link className="App__home-button" to="/">
                      <Button variant="secondary">
                        <FontAwesomeIcon icon={faIgloo} /> Home
                      </Button>
                    </Link>
                  </Row>
                </Container>
                <Event idToken={idToken} />
              </Route>
              <Route exact path="/">
                <MainView
                  idToken={idToken}
                  backEndError={setBackendFetchError}
                  refreshConfig={refreshConfig}
                  lastMeetingThresholdsInWeeks={lastMeetingThresholdsInWeeks}
                />
              </Route>
            </Switch>
          </div>
        )}
      </Router>
    </div>
  );
}

export default App;
