import React from 'react';
import Craftsperson from './Craftsperson';
import Mentees from './Mentees'
import Card from 'react-bootstrap/Card';
import Accordion from 'react-bootstrap/Accordion';
import Button from 'react-bootstrap/Button';
import './Craftsperson.css'
import {Typeahead} from 'react-bootstrap-typeahead';


export default function CraftspersonRow({craftsperson, craftspeople, rerender}) {

    let mentorSelect = React.createRef();

    function addMentorCallBack(selectedCraftspeople) {
        if (selectedCraftspeople.length === 0) {
            // nothing to do
            return;
        }
        fetch('/craftspeople/mentor/add',
            {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({mentorId: selectedCraftspeople[0].id, menteeId: craftsperson.id})
            }
        );
        rerender();
    }

    function getCraftspersonMentorNameOrNull() {
        if (craftsperson.mentor === null) {
            return ''
        }
        return craftsperson.mentor.firstName + ' ' + craftsperson.mentor.lastName;
    }

    return (
        <Accordion>
            <div className='container'>
                <Card>
                    <Card.Header>
                        <Accordion.Toggle className='craftsperson-row' as={Button} variant='light' eventKey='0'>
                            <Craftsperson craftsperson={craftsperson}/>
                        </Accordion.Toggle>
                        <div className="container">
                            <div className="row justify-content-center">
                                <Typeahead
                                    defaultInputValue={getCraftspersonMentorNameOrNull()}
                                    ref={mentorSelect}
                                    inputProps={{'data-testid': 'add-mentor-select'}}
                                    labelKey={(option) => `${option.firstName} ${option.lastName}`}
                                    options={craftspeople}
                                    placeholder="Select a mentor"
                                    onChange={addMentorCallBack}
                                />
                            </div>
                        </div>
                    </Card.Header>
                    <Accordion.Collapse eventKey='0'>
                        <Card.Body>
                            <Mentees
                                rerender={rerender}
                                craftsperson={craftsperson}
                                mentees={craftsperson.mentees}
                                craftspeople={craftspeople}
                            />
                        </Card.Body>
                    </Accordion.Collapse>
                </Card>
            </div>
        </Accordion>
    );
}
