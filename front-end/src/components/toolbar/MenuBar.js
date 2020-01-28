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
            <SearchBar onEnter={props.searchFilter} />
            <Row>
              <Col>
                <SortingBar
                  numberOfMenteesListener={props.filters.numberOfMentees}
                  menteesWithoutMentorListener={props.filters.craftspeopleWithoutMentors}
                  lastMeetingDateListener={props.filters.lastMeetingDate}
                />
              </Col>
              <Col>
                <ManageCraftsperson
                  craftspeople={props.craftspeople}
                  rerender={props.rerender}
                  idToken={props.idToken}
                  lastMeetingThresholdDefaultValue={props.meetingThreshold}
                />
              </Col>
            </Row>
          </Container>
        </>
    );
}
