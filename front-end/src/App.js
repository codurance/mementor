import React, { useState, useEffect } from "react";
import "./App.css";
import SearchBar from "./components/toolbar/SearchBar";
import { api } from "./util/api";
import SortableRow from "./components/list/SortableRow";
import FIXTURE from "./util/fixture.json";
import { sortByNumberOfMentees } from "./util/sorting";
import { filter } from "./util/filtering";
import Header from "./components/header/Header";
import ButtonToolbar from 'react-bootstrap/ButtonToolbar'
import ToggleButton from 'react-bootstrap/ToggleButton';
import ToggleButtonGroup from 'react-bootstrap/ToggleButtonGroup';

function App() {
  const [craftspeople, setCraftsPeople] = useState([]);
  const [filtered, setFiltered] = useState(craftspeople);

  const filterCraftspeople = searchedValue => {
    const filteredCraftspeople = filter(craftspeople, searchedValue);
    setFiltered(filteredCraftspeople);
  };

    const SetAndSortCraftspeople = (craftspeople) => {
        const craftspeople_rows = craftspeople.sort(sortByNumberOfMentees)
        setCraftsPeople(craftspeople_rows);
        setFiltered(craftspeople_rows);
    };

  useEffect(() => {
    api("craftspeople")
      .then(data => {
        SetAndSortCraftspeople(data);
      })
      .catch(error => {
        console.log(error);
        const fixture_data_for_craftspeople = Array.from(FIXTURE);
        SetAndSortCraftspeople(fixture_data_for_craftspeople);
      });
  }, []);

    return (
        <div className='App'>
            <div>
                <Header />
                <div className="container">
                    <SearchBar onEnter={filterCraftspeople} />
                    <ButtonToolbar>
                        <ToggleButtonGroup type="radio" name="options" defaultValue={1}>
                            <ToggleButton variant="light" prechecked value={1}>Sort by number of mentees</ToggleButton>
                            <ToggleButton variant="light" value={2}>Sort by mentor</ToggleButton>
                        </ToggleButtonGroup>
                    </ButtonToolbar>
                </div>
                {filtered.map(craftsperson => <SortableRow key={craftsperson.id} craftsperson={craftsperson} />)}
            </div>
        </div>
    );
}

export default App;
