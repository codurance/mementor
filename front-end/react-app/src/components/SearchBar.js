import React from "react";
import './SearchBar.css'

export default class SearchBar extends React.Component {
    constructor(props) {
        super(props);
        this.state = {value: ''};
        this._onChange = this._onChange.bind(this);
    }

    _onChange(e) {
        // this.setState({value: e.target.value});
        // console.log(`${this.state.value}`);
        console.log("THIS IS E:" + e.target.value);
        this.props.onEnter(e.target.value);
    };

    render(){
        // return <input type="text" className="search-bar" onChange={this.props.onChange} value={`${this.state.value}`} placeholder="Search for craftsperson"/>
        return <input type="text" className="search-bar" onChange={this._onChange} placeholder="Search for craftsperson"/>
    }

}
