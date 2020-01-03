import React from "react";

export default function SortableRow(props){
    return <li key={`${props.name}`}>{props.name}</li>;
}