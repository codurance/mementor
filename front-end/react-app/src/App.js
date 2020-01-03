import React, { useState } from 'react';
import logo from './logo.svg';
import './App.css';
import SortableList from './components/SortableList';
import SearchBar from "./components/SearchBar";

function App() {
    const filterCraftspeople = (data) => {
        console.log(data);
        let filters = craftspeople.filter((name) => {
            return name.toLowerCase().indexOf(data) !== -1;
        });
        setFiltered(filters);
    };

    const craftspeople = ["Arnaud", "Etienne", "Riccardo", "Ed", "Jose"];
    const [filtered, setFiltered] = useState(craftspeople);
  return (
    <div className="App">
      <header className="App-header">
        <img src={logo} className="App-logo" alt="logo" />
        <p>
          Fuck you, Ed
        </p>
        <a
          className="App-link"
          href="https://reactjs.org"
          target="_blank"
          rel="noopener noreferrer"
        >
          Learn React
        </a>
      </header>
        <div>
            <SearchBar onEnter={filterCraftspeople} filter={() => filterCraftspeople()}/>
            <SortableList craftspeople={filtered}/>
        </div>
    </div>
  );
}

export default App;
