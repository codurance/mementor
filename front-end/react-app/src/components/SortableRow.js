import React from "react";

export default function SortableRow(){
    let els = [];
    for (let i of ["A", "B", "C"]){
        els.push(<li key={i.charCodeAt(0) + i.charCodeAt(0)}>{`Value is ${i}`}</li>);
    }
    return els;
}