import React, { useEffect, useState } from "react";
import { toast } from "react-toastify";
import GoogleLogin, { GoogleLogout } from "react-google-login";
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
  const [shouldRender, setShouldRender] = useState(false);
  const [craftspeople, setCraftsPeople] = useState([]);
  const [filteredCraftspeople, setFilteredCraftspeople] = useState(
    craftspeople,
  );
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [idToken, setIdToken] = useState(null);
  const [lastMeetingThresholdsInWeeks, setLastMeetingThresholdsInWeeks] = useState(null);

  function login(googleUser) {
    const id_token = googleUser.getAuthResponse().id_token;
    setIsLoggedIn(true);
    setIdToken(id_token);
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
    if (!isLoggedIn) {
      // the api calls will fail because we're not authorized
      return;
    }
    api({ endpoint: "/craftspeople", token: idToken })
      .then(response => response.json())
      .then(fetchedCraftspeople => {
        fetchedCraftspeople.sort(sortAlgorithm);
        setCraftsPeople(fetchedCraftspeople);
        setFilteredCraftspeople(fetchedCraftspeople);
      })
      .catch(error => {
        notifyUnexpectedBackendError(error);
      });

    api({endpoint: `/config`, token: idToken})
    .then(response => response.json())
    .then(body => setLastMeetingThresholdsInWeeks(body.lastMeetingThresholdsInWeeks))
    .catch(notifyUnexpectedBackendError);
  }, [defaultSort, shouldRender]);

  if (!isLoggedIn) {
    return (
      <div>
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
      </div>
    )
  }

  return (
    <div className="App">
        <div>
          <Container>
            <Image className="main-logo" src={logo} />
          </Container>
          <Container>
            <SearchBar onEnter={filterCraftspeople} />
            <Row>
              <Col>
                <SortingBar
                  numberOfMenteesEvent={makeSortOnClickListener(sortByNumberOfMentees)}
                  menteesWithoutMentorEvent={makeSortOnClickListener(sortByCraftspeopleWithoutMentor)}
                  lastMeetingDateEvent={makeSortOnClickListener(sortByLastMeetingDate)}
                />
              </Col>
              <Col>
                <ManageCraftsperson
                  craftspeople={craftspeople}
                  rerender={rerender}
                  idToken={idToken}
                  lastMeetingThresholdDefaultValue={lastMeetingThresholdsInWeeks}
                />
              </Col>
            </Row>
          </Container>
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
    </div>
  );
}
export default App;
