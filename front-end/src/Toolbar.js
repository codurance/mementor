import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import { SortingBar } from "./components/toolbar/SortingBar";
import { AdminToolbar } from "./AdminToolbar";
import * as PropTypes from "prop-types";
import React from "react";

export function Toolbar(props) {
  return <Row>
    <Col>
      <SortingBar
        sortByMentees={props.sortByMentees}
        sortByMentor={props.sortByMentor}
        sortByLastMeeting={props.sortByLastMeeting}
      />
    </Col>
    <Col>
      <AdminToolbar craftspeople={props.craftspeople}
                    refreshCraftspeople={props.refreshCraftspeople}
                    refreshConfig={props.refreshConfig}
                    idToken={props.idToken}
                    lastMeetingThresholdDefaultValue={props.lastMeetingThresholdDefaultValue}/>
    </Col>
  </Row>;
}

Toolbar.propTypes = {
  sortByMentees: PropTypes.func,
  sortByMentor: PropTypes.func,
  sortByLastMeeting: PropTypes.func,
  craftspeople: PropTypes.shape({ id: PropTypes.any, list: PropTypes.any }),
  refreshCraftspeople: PropTypes.func,
  refreshConfig: PropTypes.func,
  idToken: PropTypes.any,
  lastMeetingThresholdDefaultValue: PropTypes.any
};