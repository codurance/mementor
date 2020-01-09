import React from "react";
import './SearchBar.css';
import InputGroup from 'react-bootstrap/InputGroup';
import FormControl from 'react-bootstrap/FormControl';

export default function SearchBar(props){
    return (
        <InputGroup className="mb-3">
            <InputGroup.Prepend>
                <InputGroup.Text id="inputGroup-sizing-sm">Craftsperson</InputGroup.Text>
            </InputGroup.Prepend>
            <FormControl
                onChange={(e) => props.onEnter(e.target.value)}
                aria-label="Craftsperson searchbar"
                aria-describedby="basic-addon1"
            />
      </InputGroup>
      )
}
