import React from 'react';
import Craftsperson from './Craftsperson';
import Card from 'react-bootstrap/Card';
import Accordion from 'react-bootstrap/Accordion';
import Button from 'react-bootstrap/Button';
import ListGroup from 'react-bootstrap/ListGroup';
import {sortAlphabetically} from '../../util/sorting';
import './Craftsperson.css'

export default function CraftspersonRow({craftsperson}) {
  return (
    <Accordion>
      <div className='container'>
        <Card>
          <Card.Header>
            <Accordion.Toggle className='craftsperson-row' as={Button} variant='light' eventKey='0'>
              <Craftsperson craftsperson={craftsperson} />
            </Accordion.Toggle>
          </Card.Header>
          <Accordion.Collapse eventKey='0'>
            <Card.Body>
              <ListGroup data-testid='menteesList'>
                {craftsperson.mentees
                  .sort(sortAlphabetically)
                  .map(mentee => {
                  return (
                    <ListGroup.Item key={mentee.id} >
                      <h4 className="menteeName">{mentee.firstName} {mentee.lastName}</h4>
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
