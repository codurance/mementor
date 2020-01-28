import React from 'react';
import Container from 'react-bootstrap/Container';
import Image from "react-bootstrap/Image";
import Col from 'react-bootstrap/Col';
import Row from 'react-bootstrap/Row';
import SearchBar from './SearchBar';
import SortingBar from './SortingBar';
import ManageCraftsperson from '../craftsperson-manager/ManageCraftsperson';

export default function MenuBar(props) {
    return (
        <>
        <Container>
            <Image className="main-logo" src={props.logo} />
          </Container>
          <Container>
            <SearchBar
              searchValue={props.currentSearchValue}
              updateSearchValue={props.searchValue}
            />
            <Row>
              <Col>
                <SortingBar
                  onClick={props.numberOfMenteesListener}
                  onClick1={props.craftspeopleWithoutMentorListener}
                  onClick2={props.lastMeetingDateListener}
                />
              </Col>
              <Col>
                <ManageCraftsperson
                  craftspeople={props.craftspeople}
                  refreshCraftspeople={props.refreshCraftspeople}
                  refreshConfig={props.refreshConfig}
                  idToken={props.idToken}
                  lastMeetingThresholdDefaultValue={props.lastMeetingThresholdDefaultValue}
                />
              </Col>
            </Row>
          </Container>
        </>
    );
}
