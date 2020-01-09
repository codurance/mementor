import React, { useState, useEffect } from 'react';
import logo from './logo.svg';
import './App.css';
import SortableList from './components/SortableList';
import SearchBar from "./components/SearchBar";
import {api} from "./util/api";
import SortableRow from "./components/SortableRow";
import FIXTURE from "./util/fixture.json"
import {default_sort} from "./util/sorting";

function App() {
    const [craftspeople, setCraftsPeople] = useState([]);
    const [filtered, setFiltered] = useState(craftspeople);

    const filterCraftspeople = (data) => {
        let filters = craftspeople.filter((name) => {
            return name.toLowerCase().indexOf(data.toLowerCase()) !== -1;
        });
        setFiltered(filters);
    };

    const SetAndSortCraftspeople = (data) => {
        const craftspeople_rows = default_sort(data);
        setCraftsPeople(craftspeople_rows);
        setFiltered(craftspeople_rows);
    };

    useEffect(() => {
        api('craftspeople').then(data => {
            SetAndSortCraftspeople(data);
        }).catch(error => {
            console.log(error);
            const fixture_data_for_craftspeople = Array.from(FIXTURE);
            SetAndSortCraftspeople(fixture_data_for_craftspeople);
        })
    }, []);

    return (
        <div className="App">
            <div>
                <SearchBar onEnter={filterCraftspeople}/>
                <SortableList craftspeople={filtered.map(craftsperson => <SortableRow key={craftsperson.id} craftsperson={craftsperson}/>)}/>
            </div>
        </div>
    );
}

export default App;
