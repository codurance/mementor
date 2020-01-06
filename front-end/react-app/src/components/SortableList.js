import React from 'react';
import SortableRow from "./SortableRow";

export default class SortableList extends React.Component{

    constructor(props){
        super(props);
    }

    render(){
        return (
            <ul>
                {this.props.craftspeople.map((name) => <SortableRow name={name} key={`${name}`}/>)}
            </ul>
        )
    }
}
