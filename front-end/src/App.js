import React, {useEffect, useState} from "react";
import "./App.css";
import SearchBar from "./components/toolbar/SearchBar";
import {api} from "./util/api";
import CraftspersonRow from "./components/list/CraftspersonRow";
import {sortByCraftspeopleWithoutMentor, sortByNumberOfMentees} from "./util/sorting";
import {filter} from "./util/filtering";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import ManageCraftsperson from "./components/admin/ManageCraftsperson";
import logo from "./mementor_logo.png";
import {SortingBar} from "./components/toolbar/SortingBar";
import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

toast.configure();

function App() {
    const defaultSort = sortByNumberOfMentees;

    const [sortAlgorithm, setSortAlgorithm] = useState(() => defaultSort);
    const [backendFetchError, setBackendFetchError] = useState(null);
    const [shouldRender, setShouldRender] = useState(false);
    const [craftspeople, setCraftsPeople] = useState([]);
    const [filteredCraftspeople, setFilteredCraftspeople] = useState(craftspeople);

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

        api({endpoint: '/craftspeople'})
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
          <div>
            <div className="container">
              <img src={logo} className="main-logo" alt="Mementor Logo"/>
            </div>
            <div className="container">
              <SearchBar onEnter={filterCraftspeople}/>
              <Row>
                <Col>
                  <SortingBar
                      onClick={makeSortOnClickListener(sortByNumberOfMentees)}
                      onClick1={makeSortOnClickListener(sortByCraftspeopleWithoutMentor)}
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
                  <strong>Oh snap!</strong> Looks like there was an error while fetching the data.
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
        </div>
    );
}

export default App;
