import React from 'react';
import CraftspersonRow from "../components/list/CraftspersonRow";
import { LinkTo, linkTo } from '@storybook/addon-links'


export default {
  title: 'Craftsperson',
  component: CraftspersonRow,
};

export const Default = () =>
  <CraftspersonRow
    craftsperson={{id: 1, firstName: "Arnaud", lastName: "CLAUDEEL", mentees: []}}
    craftspeople={[]}
  />;

export const WithAMentor = () =>
  <CraftspersonRow
    craftsperson={
      {id: 1, firstName: "Arnaud", lastName: "CLAUDEEL",
      mentor: {id: 3, firstName:"Jose", lastName: "Rixon"}, mentees: []}
      }
    craftspeople={[]}
  />;

export const WithAnRecentLastMeeting = () =>
      <CraftspersonRow
        craftsperson={
          {id: 1, firstName: "Arnaud", lastName: "CLAUDEEL",
            mentor: {id: 3, firstName:"Jose", lastName: "Rixon"},
            lastMeeting: new Date().getTime() / 1000, mentees: []
          }}
        lastMeetingThresholdsInWeeks={2}
        craftspeople= {[]}
      />;

export const WithAnOldLastMeeting = () =>
      <CraftspersonRow
        craftsperson={
          {id: 1, firstName: "Arnaud", lastName: "CLAUDEEL",
            mentor: {id: 3, firstName:"Jose", lastName: "Rixon"},
            lastMeeting: 15_000_0000, mentees: []
          }}
        craftspeople= {[]}
      />;

export const With2Mentees = () =>
  <CraftspersonRow
    craftsperson={
      {id: 1, firstName: "Arnaud", lastName: "CLAUDEEL",
        mentees: [
          {id: 3, firstName:"Jose", lastName: "Rixon"},
          {id: 4, firstName:"Ed", lastName: "Wenzel"}]
      }}
    craftspeople={[]}
  />;
