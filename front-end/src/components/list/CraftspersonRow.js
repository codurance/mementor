import React from 'react';
import Craftsperson from './Craftsperson';
import Mentees from './Mentees'
import Card from 'react-bootstrap/Card';
import Accordion from 'react-bootstrap/Accordion';
import Button from 'react-bootstrap/Button';
import './Craftsperson.css'


export default function CraftspersonRow({craftsperson, craftspeople, rerender}) {
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
            <Mentees rerender={rerender} craftsperson={craftsperson} mentees={craftsperson.mentees} craftspeople={craftspeople}/>
            </Card.Body>
          </Accordion.Collapse>
        </Card>
      </div>
    </Accordion>
  );
}
