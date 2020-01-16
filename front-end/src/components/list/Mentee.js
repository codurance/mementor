import React from 'react';
import ListGroupItem from "react-bootstrap/ListGroupItem";
import Button from "react-bootstrap/Button";
import Container from "react-bootstrap/Container";
import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";
import {api} from './../../util/api';


export default function Mentee({mentee, rerender}) {

    function removeMentee(e) {
        api(`craftspeople/mentee/remove/${mentee.id}`, {method:"PUT"});
        rerender();
    }

    return (
        <ListGroupItem>
            <Container>
                <Row>
                    <Col />
                    <Col>
                        <h4 className='menteeName'>{mentee.firstName} {mentee.lastName}</h4>
                    </Col>
                    <Col>
                        <Button variant="danger" data-testid="removementeebutton" onClick={e => removeMentee(e)}>
                            <strong>X</strong>
                        </Button>
                    </Col>
            </Row>
            </Container>
        </ListGroupItem>
    );
}
