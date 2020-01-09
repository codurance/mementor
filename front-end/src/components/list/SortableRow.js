import React from "react";
import Craftsperson from "./Craftsperson";
import Card from 'react-bootstrap/Card';
import Accordion from 'react-bootstrap/Accordion';
import Button from 'react-bootstrap/Button';
import ListGroup from 'react-bootstrap/ListGroup';

export default function SortableRow(props){
    return (
        <Accordion>
            <Card>
                <Card.Header>
                <Accordion.Toggle as={Button} variant="link" eventKey="0">
                    <Craftsperson craftsperson={props.craftsperson}/>
                </Accordion.Toggle>
                </Card.Header>
                <Accordion.Collapse eventKey="0">
                <Card.Body>
                <ListGroup>
                    {props.craftsperson.mentees.map(mentee => {
                            return <ListGroup.Item>{mentee.firstName + ' ' + mentee.lastName}</ListGroup.Item>
                    })}
                </ListGroup>   
                     
                </Card.Body>
                </Accordion.Collapse>
            </Card>
        </Accordion>
    ) 
}
