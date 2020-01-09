import React from "react";
import Craftsperson from "./Craftsperson";

export default function SortableRow(props){
    return <li><Craftsperson craftsperson={props.craftsperson}/></li>;
}
