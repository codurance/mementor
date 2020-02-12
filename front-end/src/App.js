import React, { useCallback, useState } from "react";
import { toast } from "react-toastify";
import "./App.css";
import "react-toastify/dist/ReactToastify.css";
import { Header } from "./Header";
import { ErrorFetch } from "./ErrorFetch";
import { Login } from "./Login";
import { BrowserRouter as Router, Link, Route, Switch } from "react-router-dom";
import { MainView } from "./MainView";
import { Audit } from "./Audit";
import { api } from "./util/api";
import { notifyUnexpectedBackendError } from "./util/notify";

toast.configure();

function App() {
  const [backendFetchError, setBackendFetchError] = useState(null);
  const [idToken, setIdToken] = useState(null);
  const [lastMeetingThresholdsInWeeks, setLastMeetingThresholdsInWeeks] = useState(null);

  function successfulLogin(googleUser) {
    setBackendFetchError(null);
    const id_token = googleUser.getAuthResponse().id_token;
    setIdToken(id_token);
  }

  const isUserLoggedIn = useCallback(() => {
    return idToken;
  }, [idToken]);

  const refreshConfig = useCallback(() => {
    if (!isUserLoggedIn) {
      // the api calls will fail because we're not authorized
      return;
    }

    api({ endpoint: `/config`, token: idToken })
      .then(response => response.json())
      .then(body =>
        setLastMeetingThresholdsInWeeks(lastMeetingThresholdsInWeeks)
      )
      .catch(notifyUnexpectedBackendError);
  }, [idToken, isUserLoggedIn]);

  return (
    <div className="App">
      <Router>
        {!isUserLoggedIn() && (
          <Login onSuccess={successfulLogin}/>
        )}
        {isUserLoggedIn() && <div>
          {backendFetchError && (
            <ErrorFetch/>
          )}
          <Header/>
          <Switch>
            <Route path="/activities">
              <Audit idToken={idToken}/>
              <Link to="/">Home</Link>
            </Route>
            <Route exact path="/">
              <MainView idToken={idToken}
                        isUserLoggedIn={isUserLoggedIn}
                        backEndError={setBackendFetchError}
                        refreshConfig={refreshConfig}
                        lastMeetingThresholdsInWeeks={lastMeetingThresholdsInWeeks}
              />
            </Route>
          </Switch>
        </div>}
      </Router>
    </div>
  );
}

export default App;
