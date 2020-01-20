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
                endpoint: '/craftspeople/addmentee',
                type: 'PUT',
                body: {
                    'mentorId': mentorId,
                    'menteeId': mentee.id
                }
            });
            props.rerender();
        }
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
                    data-testid='craftspeople-mentee-list'
                    options={props.craftspeople}
                    placeholder="Select a mentor"
                    onChange={(selected) => addMentee(selected[0], props.craftsperson.id)}
                />
            </ListGroupItem>
        </ListGroup>
    );
}
