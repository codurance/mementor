import React from 'react';
import SearchBar from "../components/toolbar/SearchBar";
import { SortingBar } from "../components/toolbar/SortingBar";
import Craftsperson from "../components/list/Craftsperson";
import { LinkTo, linkTo } from '@storybook/addon-links'


export default {
  title: 'Craftsperson',
  component: Craftsperson,
};

export const Default = () =>
  <Craftsperson
    craftsperson={{id: 1, firstName: "Arnaud", lastName: "CLAUDEEL"}}
    craftspeople={[]}
  />;

export const Mentored = () =>
  <Craftsperson
    craftsperson={
      {id: 1, firstName: "Arnaud", lastName: "CLAUDEEL",
      mentor: {id: 3, firstName:"Jose", lastName: "Rixon"}
      }}
    craftspeople={[]}
  />;

export const MentoredWithLastMeeting = () =>
  (
    <div>
      <LinkTo story="SortingBar">Go back</LinkTo>
      <button onClick={linkTo('Sorting bar')}>Go to "Default"</button>
      <Craftsperson
        craftsperson={
          {id: 1, firstName: "Arnaud", lastName: "CLAUDEEL",
            mentor: {id: 3, firstName:"Jose", lastName: "Rixon"},
            lastMeeting: 15_000_0000
          }}
        craftspeople= {[]}
      />
    </div>);

export const WithMentees = () =>
  <Craftsperson
    craftsperson={
      {id: 1, firstName: "Arnaud", lastName: "CLAUDEEL",
        mentees: [
          {id: 3, firstName:"Jose", lastName: "Rixon"},
          {id: 4, firstName:"Jose", lastName: "Rixon"}]
      }}
    craftspeople={[]}
  />;
