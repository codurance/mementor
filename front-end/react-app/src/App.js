import React, { useState, useEffect } from 'react';
import logo from './logo.svg';
import './App.css';
import SortableList from './components/SortableList';
import SearchBar from "./components/SearchBar";

function App() {
    // const craftspeople = ["Arnaud", "Etienne", "Riccardo", "Ed", "Jose"];
    const [craftspeople, setCraftsPeople] = useState([]);
    const [filtered, setFiltered] = useState(craftspeople);

    const filterCraftspeople = (data) => {
        let filters = craftspeople.filter((name) => {
            return name.toLowerCase().indexOf(data.toLowerCase()) !== -1;
        });
        setFiltered(filters);
    };

    useEffect(() => {
        fetch("http://localhost:8080/restApiTest").then(res => {
            return res.json();
        }).then(data => {
            let crafts = data.map((c) => {
                return c.firstName + ' ' + c.lastName;
            });
            setCraftsPeople(crafts);
            setFiltered(crafts);
        });
    }, []);

    return (
        <div className="App">
            <div>
                <SearchBar onEnter={filterCraftspeople}/>
                <SortableList craftspeople={filtered}/>
            </div>
        </div>
    );
}

export default App;
