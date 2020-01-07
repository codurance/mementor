import React, { useState, useEffect } from 'react';
import logo from './logo.svg';
import './App.css';
import SortableList from './components/SortableList';
import SearchBar from "./components/SearchBar";
import {api} from "./util/api";
import SortableRow from "./components/SortableRow";
import FIXTURE from "./util/fixture.json"

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
            let craftspeople_names = data.map((craftsperson) => {
                return craftsperson["firstName"] + ' ' + craftsperson["lastName"];
            });
            setCraftsPeople(craftspeople_names);
            setFiltered(craftspeople_names);
        }).catch(error => {
            const default_craftspeople = Array.from(FIXTURE)
                .sort((a, b) => {
                    return b['mentee'].length - a['mentee'].length})
                .map((craft) => {
                    return craft["firstName"] + " " + craft["lastName"];
            });
            setCraftsPeople(default_craftspeople);
            setFiltered(default_craftspeople);
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
