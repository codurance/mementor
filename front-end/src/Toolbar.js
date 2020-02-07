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
        onClick={props.onClick}
        onClick1={props.onClick1}
        onClick2={props.onClick2}
      />
    </Col>
    <Col>
      <AdminToolbar craftspeople={props.craftspeople} refreshCraftspeople={props.refreshCraftspeople}
                    refreshConfig={props.refreshConfig} idToken={props.idToken}
                    lastMeetingThresholdDefaultValue={props.lastMeetingThresholdDefaultValue}/>
    </Col>
  </Row>;
}

Toolbar.propTypes = {
  onClick: PropTypes.func,
  onClick1: PropTypes.func,
  onClick2: PropTypes.func,
  craftspeople: PropTypes.shape({ id: PropTypes.any, list: PropTypes.any }),
  refreshCraftspeople: PropTypes.func,
  refreshConfig: PropTypes.func,
  idToken: PropTypes.any,
  lastMeetingThresholdDefaultValue: PropTypes.any
};