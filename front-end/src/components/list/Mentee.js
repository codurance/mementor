import React from 'react';
import ListGroupItem from "react-bootstrap/ListGroupItem";
import Button from "react-bootstrap/Button";
import {api} from './../../util/api';


export default function Mentee({mentee, rerender}) {

    function removeMentee(e) {
        api(`craftspeople/mentee/remove/${mentee.id}`, {method:"PUT"});
        rerender();
    }

    return (
        <ListGroupItem>
            <h4 className='menteeName'>{mentee.firstName} {mentee.lastName}</h4>
            <Button data-testid="removementeebutton" onClick={e => removeMentee(e)}>-</Button>
        </ListGroupItem>
    );
}
