import React from 'react';
import SearchBar from "../components/toolbar/SearchBar";
import 'bootstrap/dist/css/bootstrap.min.css';
import { SortingBar } from "../components/toolbar/SortingBar";

export default {
  title: 'Sorting bar',
  component: SortingBar,
};

export const Default = () =>
  <SortingBar
    onClick={() => {}}
    onClick1={() => {}}
    onClick2={() => {}}
  />;
