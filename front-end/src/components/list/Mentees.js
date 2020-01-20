import React from 'react';
import ListGroup from "react-bootstrap/ListGroup";
import Mentee from './Mentee';
import ListGroupItem from 'react-bootstrap/ListGroupItem';
import {Typeahead} from 'react-bootstrap-typeahead';
import {sortAlphabetically} from '../../util/sorting';
import {api} from '../../util/api';
import './Mentees.css';


export default function Mentees(props) {

    function addMentee(mentee, mentorId) {
        if (mentee != null) {
            api({
                endpoint: '/craftspeople/mentee/add',
                type: 'PUT',
                body: {
                    'mentorId': mentorId,
                    'menteeId': mentee.id
                }
            });
            props.rerender();
        }
    }

    function filterCraftspeopleMenteeList(){
        return props.craftspeople.filter(
            craftsperson => ![props.craftsperson.id, ...props.mentees.map(_ => _.id)].includes(craftsperson.id)
        );
    }

    return (
        <ListGroup data-testid="list">
            {props.mentees
                .sort(sortAlphabetically)
                .map(mentee => (
                    <Mentee
                        key={mentee.id}
                        rerender={props.rerender}
                        mentee={mentee}
                    />
                    )
                )}
            <ListGroupItem className="mentees-list-item" data-testid='add-mentee-row'>
                <h4>Add mentee</h4>
                <Typeahead
                    id={'add-mentee-' + props.craftsperson.id}
                    labelKey={(option) => `${option.firstName} ${option.lastName}`}
                    placeholder="Select a mentee"
                    options={filterCraftspeopleMenteeList(props.craftspeople)}
                    onChange={(selected) => addMentee(selected[0], props.craftsperson.id)}
                />
            </ListGroupItem>
        </ListGroup>
    );
}
