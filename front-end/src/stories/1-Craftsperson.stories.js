import React from 'react';
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

export const WithAMentor = () =>
  <Craftsperson
    craftsperson={
      {id: 1, firstName: "Arnaud", lastName: "CLAUDEEL",
      mentor: {id: 3, firstName:"Jose", lastName: "Rixon"}
      }}
    craftspeople={[]}
  />;

export const WithAnRecentLastMeeting = () =>
  (
    <div>
      <LinkTo story="SortingBar">Go back</LinkTo>
      <button onClick={linkTo('Sorting bar')}>Go to "Default"</button>
      <Craftsperson
        craftsperson={
          {id: 1, firstName: "Arnaud", lastName: "CLAUDEEL",
            mentor: {id: 3, firstName:"Jose", lastName: "Rixon"},
            lastMeeting: new Date().getTime() / 1000
          }}
        lastMeetingThresholdsInWeeks={2}
        craftspeople= {[]}
      />
    </div>);

export const WithAnOldLastMeeting = () =>
      <Craftsperson
        craftsperson={
          {id: 1, firstName: "Arnaud", lastName: "CLAUDEEL",
            mentor: {id: 3, firstName:"Jose", lastName: "Rixon"},
            lastMeeting: 15_000_0000
          }}
        craftspeople= {[]}
      />;

export const With2Mentees = () =>
  <Craftsperson
    craftsperson={
      {id: 1, firstName: "Arnaud", lastName: "CLAUDEEL",
        mentees: [
          {id: 3, firstName:"Jose", lastName: "Rixon"},
          {id: 4, firstName:"Jose", lastName: "Rixon"}]
      }}
    craftspeople={[]}
  />;
