import React, { useState } from 'react';
import logo from './logo.svg';
import './App.css';
import SortableList from './components/SortableList';
import SearchBar from "./components/SearchBar";

function App() {
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
            <SearchBar />
            <SortableList craftspeople={craftspeople}/>
        </div>
    </div>
  );
}

export default App;
