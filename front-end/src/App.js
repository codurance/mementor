import React, { useState, useEffect } from 'react';
import logo from './logo.svg';
import './App.css';
import SortableList from './components/list/SortableList';
import SearchBar from "./components/toolbar/SearchBar";
import {api} from "./util/api";
import SortableRow from "./components/list/SortableRow";
import FIXTURE from "./util/fixture.json"
import { defaultSort } from "./util/sorting";
import Header from './components/header/Header';

function App() {
    const [craftspeople, setCraftsPeople] = useState([]);
    const [filtered, setFiltered] = useState(craftspeople);

    const filterCraftspeople = (data) => {
        let filters = craftspeople.filter((craftsperson) => {
            return `${craftsperson.firstName} ${craftsperson.lastName}`.toLowerCase().indexOf(data.toLowerCase()) !== -1;
        });
        setFiltered(filters);
    };

    const SetAndSortCraftspeople = (data) => {
        const craftspeople_rows = defaultSort(data);
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
                <Header />
                <SearchBar onEnter={filterCraftspeople}/>
                <SortableList craftspeople={filtered.map(craftsperson => <SortableRow key={craftsperson.id} craftsperson={craftsperson}/>)}/>
            </div>
        </div>
    );
}

export default App;
