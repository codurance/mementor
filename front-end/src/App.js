import React, { useEffect, useState} from "react";
import { toast } from "react-toastify";
import GoogleLogin from "react-google-login";
import { api } from "./util/api";
import { filter } from "./util/filtering";
import {
  sortByCraftspeopleWithoutMentor,
  sortByNumberOfMentees,
  sortByLastMeetingDate
} from "./util/sorting";
import SearchBar from "./components/toolbar/SearchBar";
import { SortingBar } from "./components/toolbar/SortingBar";
import CraftspersonRow from "./components/list/CraftspersonRow";
import ManageCraftsperson from "./components/admin/ManageCraftsperson";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import Image from "react-bootstrap/Image";
import Container from "react-bootstrap/Container";
import "./App.css";
import logo from "./mementor_logo.png";
import "react-toastify/dist/ReactToastify.css";
import { notifyUnexpectedBackendError } from "./util/notify";

toast.configure();

function App() {
  const defaultSort = sortByNumberOfMentees;
  const [sortAlgorithm, setSortAlgorithm] = useState(() => defaultSort);
  const [backendFetchError, setBackendFetchError] = useState(null);
  const [craftspeople, setCraftsPeople] = useState({list: [], id: null});
  const [filteredCraftspeople, setFilteredCraftspeople] = useState(
    craftspeople,
  );
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [idToken, setIdToken] = useState(null);
  const [lastMeetingThresholdsInWeeks, setLastMeetingThresholdsInWeeks] = useState(null);
  const [currentSearchValue, setCurrentSearchValue] = useState(null);
  const [activeRow] = useState(null);
  const [fetchConfig, setFetchConfig] = useState(null);

  function login(googleUser) {
    setIsLoggedIn(true);
    setBackendFetchError(null);
    const id_token = googleUser.getAuthResponse().id_token;
    setIdToken(id_token);
    rerender();
  }

  function responseGoogle(response) {
    console.log(response);
  }

  function rerender(rowId) {
    fetchCraftspeople(rowId);
  }

  function rerenderAndScrollToActiveRow(rowId) {
    rerender(rowId);
  } 


  function makeSortOnClickListener(sortAlgorithmToUse) {
    return () => {
      setSortAlgorithm(() => sortAlgorithmToUse);
      // here we don't use the current algorithm because it's outdated
      craftspeople.sort(sortAlgorithmToUse);
      filteredCraftspeople.sort(sortAlgorithmToUse);
      setFilteredCraftspeople(filteredCraftspeople);
    };
  }
  console.log('app rerenders for');

  function doFetchConfig() {
    if (!isLoggedIn) {
      // the api calls will fail because we're not authorized
      return;
    }

    api({endpoint: `/config`, token: idToken})
    .then(response => response.json())
    .then(body => setLastMeetingThresholdsInWeeks(body.lastMeetingThresholdsInWeeks))
    .catch(notifyUnexpectedBackendError);
  }

  useEffect(() => {
    doFetchConfig();
  }, [fetchConfig]);

  function fetchCraftspeople(rowId) {
    if (!isLoggedIn) {
      // the api calls will fail because we're not authorized
      return;
    }
    console.log('fetching ..');
    api({ endpoint: "/craftspeople", token: idToken })
      .then(response => response.json())
      .then(fetchedCraftspeople => {
          setCraftsPeople({list: fetchedCraftspeople, id: rowId});  
      })
      .catch(error => {
        notifyUnexpectedBackendError(error);
        setBackendFetchError(error);
      });
  }

  useEffect(() => {
    console.log('updating active row .. ')
  },[activeRow]);

  useEffect(() => {
    fetchCraftspeople(1);
    doFetchConfig();
  }, [defaultSort, idToken]);

  useEffect(() => {
    console.log('scrolling')
    const element = document.getElementById(craftspeople.id);
    if(!element) {
      // no selected row
      return;
    }
    element.style.background = '#706f6f';
    element.scrollIntoView({behavior: "auto", block: "center"});
    setTimeout(() => element.style.background = 'none', 1000);
  }, [craftspeople]);

  function getList() {
    return filter(craftspeople.list.slice(), currentSearchValue)
      .sort(sortAlgorithm);
  }

  return (
    <div className="App">
      {isLoggedIn && (
        <div>
          <Container>
            <Image className="main-logo" src={logo} />
          </Container>
          <Container>
            <SearchBar searchValue={currentSearchValue} updateSearchValue={(searchValue) => {
              setCurrentSearchValue(searchValue);
              }} />
            <Row>
              <Col>
                <SortingBar
                  onClick={makeSortOnClickListener(sortByNumberOfMentees)}
                  onClick1={makeSortOnClickListener(
                    sortByCraftspeopleWithoutMentor
                  )}
                  onClick2={makeSortOnClickListener(sortByLastMeetingDate)}
                />
              </Col>
              <Col>
                <ManageCraftsperson
                  craftspeople={craftspeople.list}
                  rerender={rerender}
                  setFetchConfig={() => setFetchConfig(!fetchConfig)}
                  idToken={idToken}
                  lastMeetingThresholdDefaultValue={lastMeetingThresholdsInWeeks}
                />
              </Col>
            </Row>
          </Container>
          {backendFetchError && (
            <Container className="alert alert-danger" role="alert">
              <strong>Oh snap!</strong> Looks like there was an error while
              fetching the data.
            </Container>
          )}
          <Container>
            {getList()
              .map(craftsperson => (
              <CraftspersonRow
                key={craftsperson.id}
                craftsperson={craftsperson}
                craftspeople={craftspeople.list}
                rerenderAndScrollToActiveRow={rerenderAndScrollToActiveRow}
                lastMeetingThresholdsInWeeks={lastMeetingThresholdsInWeeks}
                idToken={idToken}
              />
            ))}
          </Container>
        </div>
      )}
      {!isLoggedIn && (
        <Container>
          <Row>
            <Image className="main-logo-login" src={logo} />
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
    </div>
  );
}
export default App;
