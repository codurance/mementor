import React, { useEffect, useState } from "react";
import { toast } from "react-toastify";
import GoogleLogin, { GoogleLogout } from "react-google-login";
import { api } from "./util/api";
import { filter } from "./util/filtering";
import {
  sortByCraftspeopleWithoutMentor,
  sortByNumberOfMentees,
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

toast.configure();

function App() {
  const defaultSort = sortByNumberOfMentees;
  const [sortAlgorithm, setSortAlgorithm] = useState(() => defaultSort);
  const [backendFetchError, setBackendFetchError] = useState(null);
  const [shouldRender, setShouldRender] = useState(false);
  const [craftspeople, setCraftsPeople] = useState([]);
  const [filteredCraftspeople, setFilteredCraftspeople] = useState(
    craftspeople,
  );
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [idToken, setIdToken] = useState(null);
  const [lastMeetingThresholdsInWeeks, setLastMeetingThresholdsInWeeks] = useState(null);

  function login(googleUser) {
    setIsLoggedIn(true);
    setBackendFetchError(null);
    const id_token = googleUser.getAuthResponse().id_token;
    setIdToken(id_token);
    rerender();
  }

  function logout() {
    setIsLoggedIn(false);
    rerender();
  }

  function responseGoogle(response) {
    console.log(response);
  }

  function rerender() {
    setShouldRender(!shouldRender);
  }

  function filterCraftspeople(searchedValue) {
    setFilteredCraftspeople(filter(craftspeople, searchedValue));
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

  useEffect(() => {
    api({ endpoint: "/craftspeople", token: idToken })
      .then(response => response.json())
      .then(fetchedCraftspeople => {
        fetchedCraftspeople.sort(sortAlgorithm);
        setCraftsPeople(fetchedCraftspeople);
        setFilteredCraftspeople(fetchedCraftspeople);
      })
      .catch(error => {
        console.log(error);
        setBackendFetchError(error);
      });

    api({endpoint: `/config`})
    .then(response => response.json())
    .then(body => setLastMeetingThresholdsInWeeks(body.lastMeetingThresholdsInWeeks));
  }, [defaultSort, shouldRender]);

  return (
    <div className="App">
      {isLoggedIn && (
        <div>
          <Container>
            <Image className="main-logo" src={logo} />
            <GoogleLogout
              className="logout-button"
              clientId="658977310896-knrl3gka66fldh83dao2rhgbblmd4un9.apps.googleusercontent.com"
              buttonText="Logout"
              onLogoutSuccess={logout}
            ></GoogleLogout>
          </Container>
          <Container>
            <SearchBar onEnter={filterCraftspeople} />
            <Row>
              <Col>
                <SortingBar
                  onClick={makeSortOnClickListener(sortByNumberOfMentees)}
                  onClick1={makeSortOnClickListener(
                    sortByCraftspeopleWithoutMentor,
                  )}
                />
              </Col>
              <Col>
                <ManageCraftsperson
                  craftspeople={craftspeople}
                  rerender={rerender}
                  idToken={idToken}
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
          {filteredCraftspeople.map(craftsperson => (
            <CraftspersonRow
              key={craftsperson.id}
              craftsperson={craftsperson}
              craftspeople={craftspeople}
              rerender={rerender}
              lastMeetingThresholdsInWeeks={lastMeetingThresholdsInWeeks}
              idToken={idToken}
            />
          ))}
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
              clientId="677831756912-90mpqndj2c96nac10mgjciibcdoiinra.apps.googleusercontent.com"
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
