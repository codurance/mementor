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

    useEffect(() => {
        api('craftspeople').then(data => {
            const craftspeople_names = default_sort(data);
            setCraftsPeople(craftspeople_names);
            setFiltered(craftspeople_names);
        }).catch(error => {
            const craftspeople_names = default_sort(Array.from(FIXTURE));
            setCraftsPeople(craftspeople_names);
            setFiltered(craftspeople_names);
        })
    }, []);

    function convertCraftspeopleToSortableRows() {
        return filtered.map(craftsperson => <SortableRow key={`${craftsperson}`} name={craftsperson}/>);
    }

    return (
        <div className="App">
            <div>
                <SearchBar onEnter={filterCraftspeople}/>
                <SortableList craftspeople={convertCraftspeopleToSortableRows()}/>
            </div>
        </div>
    );
}

export default App;
