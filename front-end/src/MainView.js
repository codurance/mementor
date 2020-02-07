import Container from "react-bootstrap/Container";
import SearchBar from "./components/toolbar/SearchBar";
import { Toolbar } from "./Toolbar";
import React, { useCallback, useEffect, useState } from "react";
import { api } from "./util/api";
import { notifyUnexpectedBackendError } from "./util/notify";
import CraftspersonRow from "./components/list/CraftspersonRow";
import { filter } from "./util/filtering";
import { sortByCraftspeopleWithoutMentor, sortByLastMeetingDate, sortByNumberOfMentees } from "./util/sorting";

export function MainView(props) {

  const defaultSort = sortByNumberOfMentees;
  const [sortAlgorithm, setSortAlgorithm] = useState(() => defaultSort);
  const [craftspeople, setCraftsPeople] = useState({ list: [], id: null });
  const [lastMeetingThresholdsInWeeks, setLastMeetingThresholdsInWeeks] = useState(null);

  const refreshConfig = useCallback(() => {
    if (!props.isUserLoggedIn) {
      // the api calls will fail because we're not authorized
      return;
    }

    api({ endpoint: `/config`, token: props.idToken })
      .then(response => response.json())
      .then(body =>
        setLastMeetingThresholdsInWeeks(body.lastMeetingThresholdsInWeeks)
      )
      .catch(notifyUnexpectedBackendError);
  }, [props.idToken, props.isUserLoggedIn]);

  const [currentSearchValue, setCurrentSearchValue] = useState(null);

  function makeSortOnClickListener(sortAlgorithmToUse) {
    return () => {
      setSortAlgorithm(() => sortAlgorithmToUse);
    };

  }

  const refreshCraftspeople = useCallback((rowId) => {
    if (!props.isUserLoggedIn) {
      // the api calls will fail because we're not authorized
      return;
    }
    api({ endpoint: "/craftspeople", token: props.idToken })
      .then(response => response.json())
      .then(fetchedCraftspeople => {
        setCraftsPeople({ list: fetchedCraftspeople, id: rowId });
      })
      .catch(error => {
        notifyUnexpectedBackendError(error);
        props.backEndError(error);
      });
  }, [props.idToken, props.isUserLoggedIn]);

  useEffect(() => {
    refreshCraftspeople();
    refreshConfig();
  }, [refreshConfig, refreshCraftspeople]);

  useEffect(() => {
    const element = document.getElementById(craftspeople.id);
    if (!element) {
      // no selected row
      return;
    }
    element.style.background = "#706f6f";
    element.scrollIntoView({ behavior: "auto", block: "center" });
    setTimeout(() => (element.style.background = "none"), 1000);
  }, [craftspeople]);

  function getList() {
    return filter(craftspeople.list.slice(), currentSearchValue).sort(
      sortAlgorithm
    );
  }

  return <Container>
    <SearchBar
      searchValue={currentSearchValue}
      updateSearchValue={setCurrentSearchValue}
    />
    <Toolbar sortByMentees={makeSortOnClickListener(sortByNumberOfMentees)}
             sortByMentor={makeSortOnClickListener(sortByCraftspeopleWithoutMentor)}
             sortByLastMeeting={makeSortOnClickListener(sortByLastMeetingDate)}
             craftspeople={craftspeople}
             refreshCraftspeople={refreshCraftspeople}
             refreshConfig={refreshConfig}
             idToken={props.idToken}
             lastMeetingThresholdDefaultValue={lastMeetingThresholdsInWeeks}
    />
    <Container>
      {getList().map(craftsperson => (
        <CraftspersonRow
          key={craftsperson.id}
          craftsperson={craftsperson}
          craftspeople={craftspeople.list}
          refreshCraftspeople={refreshCraftspeople}
          lastMeetingThresholdsInWeeks={lastMeetingThresholdsInWeeks}
          idToken={props.idToken}
        />
      ))}
    </Container>
  </Container>;
}