import React, { useState } from "react";
import InputGroup from "react-bootstrap/InputGroup";
import FormControl from "react-bootstrap/FormControl";
import Button from "react-bootstrap/Button";
import ReactDOM from "react-dom";
import faSearch from "@fortawesome/free-solid-svg-icons/faSearch";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";

export default function SearchBar(props) {
  let searchInputRef = null;

  const [searchedValue, setSearchedValue] = useState(props.defaultValue);

  const clearSearchInput = () => {
    setSearchedValue("");
    props.onEnter("");
    ReactDOM.findDOMNode(searchInputRef).value = "";
  };

  return (
    <InputGroup className="search-bar mb-3">
      <InputGroup.Prepend>
        <InputGroup.Text id="inputGroup-sizing-sm">
          <span>
            <FontAwesomeIcon icon={faSearch} /> Search
          </span>
        </InputGroup.Text>
      </InputGroup.Prepend>
      <FormControl
        ref={searchedInputElement => (searchInputRef = searchedInputElement)}
        className="searchBarInput"
        data-testid="SearchBarInput"
        aria-label="Craftsperson searchbar"
        aria-describedby="basic-addon1"
        placeholder="Find Craftsperson..."
        value={searchedValue}
        onChange={e => {
          props.onEnter(e.target.value);
          setSearchedValue(e.target.value);
        }}
      />
      {searchedValue && (
        <Button
          className="clear-search"
          onClick={clearSearchInput}
          data-testid="clearSearchButton"
          variant="Light"
        >
          Clear search
        </Button>
      )}
    </InputGroup>
  );
}
