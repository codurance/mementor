import React from "react";
import './SearchBar.css'

export default class SearchBar extends React.Component {
    constructor(props) {
        super(props);
        this.state = {value: ''};
    }

    _onChange(data){
      this.setState({value: data});
      console.log(`${this.state.value}`);
    }

    render(){
        return <input type="text" className="search-bar" onChange={e => this._onChange(e.target.value)} value={`${this.state.value}`} placeholder="Search for craftsperson"/>
    }
}
