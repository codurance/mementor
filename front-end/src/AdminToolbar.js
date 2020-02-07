import { Link } from "react-router-dom";
import ManageCraftsperson from "./components/admin/ManageCraftsperson";
import * as PropTypes from "prop-types";
import React from "react";

export function AdminToolbar(props) {
  return <>
    <Link to="/activities">Activities</Link>
    <ManageCraftsperson
      craftspeople={props.craftspeople.list}
      refreshCraftspeople={props.refreshCraftspeople}
      refreshConfig={props.refreshConfig}
      idToken={props.idToken}
      lastMeetingThresholdDefaultValue={
        props.lastMeetingThresholdDefaultValue
      }
    />
  </>;
}

AdminToolbar.propTypes = {
  craftspeople: PropTypes.shape({ id: PropTypes.any, list: PropTypes.any }),
  refreshCraftspeople: PropTypes.func,
  refreshConfig: PropTypes.func,
  idToken: PropTypes.any,
  lastMeetingThresholdDefaultValue: PropTypes.any
};