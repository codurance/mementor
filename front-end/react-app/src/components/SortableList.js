import React from 'react';
import SortableRow from "./SortableRow";

export default class SortableList extends React.Component{

    constructor(props){
        super(props);
        this.state = {
            craftspeople: props.craftspeople.map((name) => <SortableRow name={name}/>),
            filtered: []
        };
    }

    componentWillMount() {
        this.setState({filtered: this.state.craftspeople})
    }

    filterCraftpeople = (name) => {
        let filters = this.state.craftsperson.filter((name) => {
            return filters.indexOf(name) !== -1
        });
        this.setState({filtered: filters});

    }

    render(){
        return <ul onChange={this.filterCraftpeople}>{this.state.craftspeople}</ul>
    }
}
