import React from 'react';
import Craftsperson from './Craftsperson';
import Card from 'react-bootstrap/Card';
import Accordion from 'react-bootstrap/Accordion';
import Button from 'react-bootstrap/Button';
import ListGroup from 'react-bootstrap/ListGroup';
import './Craftsperson.css'

export default function SortableRow(props) {
  return (
    <Accordion>
      <div class='container'>
        <Card>
          <Card.Header>
            <Accordion.Toggle className='craftsperson-row' as={Button} variant='light' eventKey='0'>
              <Craftsperson craftsperson={props.craftsperson} />
            </Accordion.Toggle>
          </Card.Header>
          <Accordion.Collapse eventKey='0'>
            <Card.Body>
              <ListGroup>
                {props.craftsperson.mentees.map(mentee => {
                  return (
                    <ListGroup.Item>
                      <h4>{`${mentee.firstName} ${mentee.lastName}`}</h4>
                    </ListGroup.Item>
                  );
                })}
              </ListGroup>
            </Card.Body>
          </Accordion.Collapse>
        </Card>
      </div>
    </Accordion>
  );
}
