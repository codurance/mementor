import React, { useCallback, useEffect, useState } from "react";
import { toast } from "react-toastify";
import GoogleLogin from "react-google-login";
import { api } from "./util/api";
import { filter } from "./util/filtering";
import { sortByCraftspeopleWithoutMentor, sortByLastMeetingDate, sortByNumberOfMentees } from "./util/sorting";
import SearchBar from "./components/toolbar/SearchBar";
import CraftspersonRow from "./components/list/CraftspersonRow";
import Row from "react-bootstrap/Row";
import Image from "react-bootstrap/Image";
import Container from "react-bootstrap/Container";
import "./App.css";
import logo from "./mementor_logo.png";
import "react-toastify/dist/ReactToastify.css";
import { notifyUnexpectedBackendError } from "./util/notify";
import { BrowserRouter as Router } from "react-router-dom";
import { Header } from "./Header";
import { Toolbar } from "./Toolbar";
import { ErrorFetch } from "./ErrorFetch";


toast.configure();

function App() {
  const defaultSort = sortByNumberOfMentees;
  const [sortAlgorithm, setSortAlgorithm] = useState(() => defaultSort);
  const [backendFetchError, setBackendFetchError] = useState(null);
  const [craftspeople, setCraftsPeople] = useState({ list: [], id: null });
  const [idToken, setIdToken] = useState(null);
  const [
    lastMeetingThresholdsInWeeks,
    setLastMeetingThresholdsInWeeks
  ] = useState(null);
  const [currentSearchValue, setCurrentSearchValue] = useState(null);

  const isUserLoggedIn = useCallback(() => {
    return idToken;
  }, [idToken]);

  function login(googleUser) {
    setBackendFetchError(null);
    const id_token = googleUser.getAuthResponse().id_token;
    setIdToken(id_token);
  }

  function responseGoogle(response) {
    console.log(response);
  }

  function makeSortOnClickListener(sortAlgorithmToUse) {
    return () => {
      setSortAlgorithm(() => sortAlgorithmToUse);
    };
  }

  const refreshConfig = useCallback(() => {
    if (!isUserLoggedIn()) {
      // the api calls will fail because we're not authorized
      return;
    }

    api({ endpoint: `/config`, token: idToken })
      .then(response => response.json())
      .then(body =>
        setLastMeetingThresholdsInWeeks(body.lastMeetingThresholdsInWeeks)
      )
      .catch(notifyUnexpectedBackendError);
  }, [idToken, isUserLoggedIn]);

  const refreshCraftspeople = useCallback((rowId) => {
    if (!isUserLoggedIn()) {
      // the api calls will fail because we're not authorized
      return;
    }
    api({ endpoint: "/craftspeople", token: idToken })
      .then(response => response.json())
      .then(fetchedCraftspeople => {
        setCraftsPeople({ list: fetchedCraftspeople, id: rowId });
      })
      .catch(error => {
        notifyUnexpectedBackendError(error);
        setBackendFetchError(error);
      });
  }, [idToken, isUserLoggedIn]);

  useEffect(() => {
    refreshCraftspeople();
    refreshConfig();
  }, [refreshConfig, refreshCraftspeople]);

  useEffect(() => {
    const element = document.getElementById(craftspeople.id);
    if (!element) {
      // no selected row
      return;
    }
    element.style.background = "#706f6f";
    element.scrollIntoView({ behavior: "auto", block: "center" });
    setTimeout(() => (element.style.background = "none"), 1000);
  }, [craftspeople]);

  function getList() {
    return filter(craftspeople.list.slice(), currentSearchValue).sort(
      sortAlgorithm
    );
  }

  return (
    <div className="App">
      <Router>
        {isUserLoggedIn() && <div>
          {backendFetchError && (
            <ErrorFetch/>
          )}
          <Header/>
          <Container>
            <SearchBar
              searchValue={currentSearchValue}
              updateSearchValue={searchValue => {
                setCurrentSearchValue(searchValue);
              }}
            />
            <Toolbar onClick={makeSortOnClickListener(sortByNumberOfMentees)} onClick1={makeSortOnClickListener(
              sortByCraftspeopleWithoutMentor
            )} onClick2={makeSortOnClickListener(sortByLastMeetingDate)} craftspeople={craftspeople}
                     refreshCraftspeople={refreshCraftspeople} refreshConfig={refreshConfig} idToken={idToken}
                     lastMeetingThresholdDefaultValue={lastMeetingThresholdsInWeeks}/>
            <Container>
              {getList().map(craftsperson => (
                <CraftspersonRow
                  key={craftsperson.id}
                  craftsperson={craftsperson}
                  craftspeople={craftspeople.list}
                  refreshCraftspeople={refreshCraftspeople}
                  lastMeetingThresholdsInWeeks={lastMeetingThresholdsInWeeks}
                  idToken={idToken}
                />
              ))}
            </Container>
          </Container>
        </div>}
        {!isUserLoggedIn() && (
          <Container>
            <Row>
              <Image className="main-logo-login" src={logo}/>
            </Row>
            <Row>
              <GoogleLogin
                className="google-login"
                clientId="232062837025-i97turm1tg41ian5hjaq1ujao6q2569i.apps.googleusercontent.com"
                buttonText="Login"
                onSuccess={login}
                onFailure={responseGoogle}
                cookiePolicy={"single_host_origin"}
              />
            </Row>
            <Row>
              <p className="love">With love by the 2019/2020 apprentices</p>
            </Row>
          </Container>
        )}
      </Router>
    </div>
  );
}

export default App;
