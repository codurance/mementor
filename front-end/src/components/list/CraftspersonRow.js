import React from 'react';
import Craftsperson from './Craftsperson';
import Card from 'react-bootstrap/Card';
import Accordion from 'react-bootstrap/Accordion';
import Button from 'react-bootstrap/Button';
import ListGroup from 'react-bootstrap/ListGroup';
import {sortAlphabetically} from '../../util/sorting';
import './Craftsperson.css'
import {Typeahead} from 'react-bootstrap-typeahead';


export default function CraftspersonRow({craftsperson, craftspeople}) {
  
  function addMentorCallBack(selectedCraftspeople) {
    fetch('/craftspeople/mentor/add', 
      {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify({mentorId: 1, menteeId: 2})
      }
    )
  }
  
  return (
    <Accordion>
      <div className='container'>
        <Card>
          <Card.Header>
            <Accordion.Toggle className='craftsperson-row' as={Button} variant='light' eventKey='0'>
              <Craftsperson craftsperson={craftsperson} />
            </Accordion.Toggle>
          </Card.Header>
          <Typeahead
            inputProps={{'data-testid': 'add-mentor-select'}}
            labelKey={(option) => `${option.firstName} ${option.lastName}`}
            options={craftspeople}
            placeholder="Select a mentor"
            onChange={addMentorCallBack}
          />
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
