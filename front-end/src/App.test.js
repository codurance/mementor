import React from 'react';
import {shallow, render, mount } from "enzyme";
import "@testing-library/jest-dom/extend-expect";
import App from './App';
import SearchBar from "./components/SearchBar";
import SortableList from "./components/SortableList";

const api = (path, options={}) => {
  return fetch(`http://localhost:8080/${path}`, options).then(
      res => res.json()
  )
};

describe("Testing API integration", () => {
  beforeEach(() => {
    fetch.resetMocks()
  });

  it("calls the back-end and returns data", () => {
    fetch.mockResponseOnce(JSON.stringify({data: '12345'}));

    api("restApiTest").then(res => {
      expect(res.data).toEqual('12345')
    });

    expect(fetch.mock.calls.length).toEqual(1);
    expect(fetch.mock.calls[0][0]).toEqual("http://localhost:8080/restApiTest")
  })
});
