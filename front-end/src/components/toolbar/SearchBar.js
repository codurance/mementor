import React, { useState } from "react";
import InputGroup from "react-bootstrap/InputGroup";
import FormControl from "react-bootstrap/FormControl";
import Button from "react-bootstrap/Button";
import ReactDOM from "react-dom";
import faSearch from "@fortawesome/free-solid-svg-icons/faSearch";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";

export default function SearchBar({searchValue, updateSearchValue}) {
  let searchInputRef = null;

  const clearSearchInput = () => {
    updateSearchValue("");
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
        onChange={e => {
          updateSearchValue(e.target.value);
        }}
      />
      {searchValue && (
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
