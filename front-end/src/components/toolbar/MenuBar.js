import React from "react";
import Container from "react-bootstrap/Container";
import Image from "react-bootstrap/Image";
import Col from "react-bootstrap/Col";
import Row from "react-bootstrap/Row";
import SearchBar from "./SearchBar";
import SortingBar from "./SortingBar";
import ManageCraftsperson from "../craftsperson-manager/ManageCraftsperson";

export default function MenuBar(props) {
  
  const filters = [
    {
      filterName: 'Number of mentees',
      filter: props.numberOfMenteesListener
    },
    {
      filterName: 'Unmentored craftsperson',
      filter: props.craftspeopleWithoutMentorListener
    },
    {
      filterName: 'Last meeting',
      filter: props.lastMeetingDateListener
    }
  ]

  return (
    <>
      <Container>
        <Image className="main-logo" src={props.logo} />
        <SearchBar
          searchValue={props.currentSearchValue}
          updateSearchValue={props.searchValue}
        />
        <Row>
          <Col>
            <SortingBar filters={filters}/>
          </Col>
          <Col>
            <ManageCraftsperson
              craftspeople={props.craftspeople}
              refreshCraftspeople={props.refreshCraftspeople}
              refreshConfig={props.refreshConfig}
              idToken={props.idToken}
              lastMeetingThresholdDefaultValue={
                props.lastMeetingThresholdDefaultValue
              }
            />
          </Col>
        </Row>
      </Container>
    </>
  );
}
