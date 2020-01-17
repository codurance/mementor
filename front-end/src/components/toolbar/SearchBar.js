import React, {useState} from "react";
import InputGroup from "react-bootstrap/InputGroup";
import FormControl from "react-bootstrap/FormControl";
import Button from "react-bootstrap/Button";
import ReactDOM from "react-dom";
import {FontAwesomeIcon} from '@fortawesome/react-fontawesome'
import {faSearch} from '@fortawesome/free-solid-svg-icons'

export default function SearchBar(props) {
    const [searchValue, setSearchValue] = useState(null);
    let searchInput = null;

    const clearSearch = () => {
        setSearchValue("");
        props.onEnter("");
        ReactDOM.findDOMNode(searchInput).value = "";
    };

    return (
        <InputGroup className="search-bar mb-3">
            <InputGroup.Prepend>
                <InputGroup.Text id="inputGroup-sizing-sm">
                    <span><FontAwesomeIcon icon={faSearch}/> Search</span>
                </InputGroup.Text>
            </InputGroup.Prepend>
            <FormControl
                ref={searchInputElement => (searchInput = searchInputElement)}
                className="searchBarInput"
                data-testid="SearchBarInput"
                aria-label="Craftsperson searchbar"
                aria-describedby="basic-addon1"
                placeholder="Find Craftsperson..."
                onChange={e => {
                    props.onEnter(e.target.value);
                    setSearchValue(e.target.value);
                }}
            />
            {searchValue && (
                <Button
                    className="clear-search"
                    onClick={clearSearch}
                    data-testid="clearSearchButton"
                    variant="Light"
                >
                    Clear search
                </Button>
            )}
        </InputGroup>
    );
}
