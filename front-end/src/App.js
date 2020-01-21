import React, { useEffect, useState } from "react";
import "./App.css";
import SearchBar from "./components/toolbar/SearchBar";
import { api } from "./util/api";
import CraftspersonRow from "./components/list/CraftspersonRow";
import {
  sortByCraftspeopleWithoutMentor,
  sortByNumberOfMentees,
} from "./util/sorting";
import { filter } from "./util/filtering";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import ManageCraftsperson from "./components/admin/ManageCraftsperson";
import logo from "./mementor_logo.png";
import { SortingBar } from "./components/toolbar/SortingBar";
import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import GoogleLogin from "react-google-login";
import { GoogleLogout } from "react-google-login";

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

  function login() {
    setIsLoggedIn(true);
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
    api({ endpoint: "/craftspeople" })
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
  }, [defaultSort, shouldRender]);

  return (
    <div className="App">
      {isLoggedIn && (
        <div>
          <div className="container">
            <img src={logo} className="main-logo" alt="Mementor Logo" />
            <GoogleLogout
              className="logout-button"
              clientId="658977310896-knrl3gka66fldh83dao2rhgbblmd4un9.apps.googleusercontent.com"
              buttonText="Logout"
              onLogoutSuccess={logout}
            ></GoogleLogout>
          </div>
          <div className="container">
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
                />
              </Col>
            </Row>
          </div>
          {backendFetchError && (
            <div className="alert alert-danger container" role="alert">
              <strong>Oh snap!</strong> Looks like there was an error while
              fetching the data.
            </div>
          )}
          {filteredCraftspeople.map(craftsperson => (
            <CraftspersonRow
              key={craftsperson.id}
              craftsperson={craftsperson}
              craftspeople={craftspeople}
              rerender={rerender}
            />
          ))}
        </div>
      )}
      {!isLoggedIn && (
        <div className="container">
          <Row>
            <img src={logo} className="main-logo-login" alt="Mementor Logo" />
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
        </div>
      )}
    </div>
  );
}
export default App;
