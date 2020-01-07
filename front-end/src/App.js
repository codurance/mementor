import React, { useState, useEffect } from 'react';
import logo from './logo.svg';
import './App.css';
import SortableList from './components/SortableList';
import SearchBar from "./components/SearchBar";
import {api} from "./util/api";
import SortableRow from "./components/SortableRow";

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
            let crafts = data.map((c) => {
                if(c.mentor != null)
                    return 'Craftsperson: ' + c.firstName + ' ' + c.lastName + ' - Mentor: ' + c.mentor.firstName + ' ' + c.mentor.lastName;
                return 'Craftsperson: ' + c.firstName + ' ' + c.lastName;

            });
            setCraftsPeople(crafts);
            setFiltered(crafts);
        }).catch(error => {
            console.log(error);
            const default_craftspeople = ["Arnaud", "Etienne", "Riccardo", "Ed", "Jose"];
            setCraftsPeople(default_craftspeople);
            setFiltered(default_craftspeople);
        })
    }, []);

    return (
        <div className="App">
            <div>
                <SearchBar onEnter={filterCraftspeople}/>
                <SortableList craftspeople={filtered.map(craftsperson => <SortableRow key={`${craftsperson}`} name={craftsperson}/>)}/>
            </div>
        </div>
    );
}

export default App;
