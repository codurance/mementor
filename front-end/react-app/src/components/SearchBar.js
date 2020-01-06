import React from "react";
import './SearchBar.css'

export default function SearchBar(props){
    return <input type="text" className="search-bar" onChange={(e) => props.onEnter(e.target.value)} placeholder="Search for craftsperson"/>
}
