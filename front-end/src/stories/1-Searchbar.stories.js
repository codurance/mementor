import React from 'react';
import SearchBar from "../components/toolbar/SearchBar";
import 'bootstrap/dist/css/bootstrap.min.css';

export default {
  title: 'Search bar',
  component: SearchBar,
};

export const Default = () =>
  <SearchBar onEnter={() => {}}/>;

export const WithText = () =>
  <SearchBar defaultValue={"Arnaud"} onEnter={() => {}}/>;