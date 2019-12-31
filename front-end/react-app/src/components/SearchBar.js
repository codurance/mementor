import React from "react";
import './SearchBar.css'

export default class SearchBar extends React.Component {
    constructor(props) {
        super(props);
        this.state = {value: ''};
    }

    _onChange(e){
      this.setState({value: e.target.value});
      console.log(`${this.state.value}`);
    }

    render(){
        return <input type="text" className="search-bar" onChange={e => this._onChange(e)} value={`${this.state.value}`} placeholder="Search for craftsperson"/>
    }
}
