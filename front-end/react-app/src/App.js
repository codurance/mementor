import React from 'react';
import logo from './logo.svg';
import './App.css';
import SortableList from './SortableList';

function App() {
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
            <SortableList user="text" user2={"dddd"}/>
        </div>
    </div>
  );
}

export default App;
