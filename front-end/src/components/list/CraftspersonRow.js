import React from "react";
import Craftsperson from "./Craftsperson";
import Mentees from "./Mentees";
import Card from "react-bootstrap/Card";
import Accordion from "react-bootstrap/Accordion";
import Button from "react-bootstrap/Button";
import "./Craftsperson.css";
import { Typeahead } from "react-bootstrap-typeahead";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faTimes } from "@fortawesome/free-solid-svg-icons";
import { api } from "../../util/api";
import { filterCraftspeople } from "../../util/filtering";

export default function CraftspersonRow({
  craftsperson,
  craftspeople,
  rerender,
}) {
  let mentorSelect = React.createRef();

  function addMentorCallBack(selectedCraftspeople) {
    if (selectedCraftspeople.length === 0) {
      // nothing to do
      return;
    }
    api({
      endpoint: "/craftspeople/mentor/add",
      type: "POST",
      body: {
        mentorId: selectedCraftspeople[0].id,
        menteeId: craftsperson.id,
      },
    });
    rerender();
  }

  function removeMentorCallback() {
    api({
      endpoint: "/craftspeople/mentor/remove",
      type: "POST",
      body: {
        menteeId: craftsperson.id,
      },
    });
    mentorSelect.current.clear();
    rerender();
  }

  function getCraftspersonMentorNameOrNull() {
    if (craftsperson.mentor === null) {
      return "";
    }
    return craftsperson.mentor.firstName + " " + craftsperson.mentor.lastName;
  }

  return (
    <Accordion>
      <div className="container">
        <Card>
          <Card.Header>
            <Accordion.Toggle
              className="craftsperson-row"
              as={Button}
              variant="light"
              eventKey="0"
            >
              <Craftsperson craftsperson={craftsperson} rerender={rerender} />
            </Accordion.Toggle>
            <div className="container">
              <div className="row justify-content-center">
                <Typeahead
                  id={"remove-mentor-" + craftsperson.id}
                  defaultInputValue={getCraftspersonMentorNameOrNull()}
                  ref={mentorSelect}
                  inputProps={{ "data-testid": "add-mentor-select" }}
                  labelKey={option => `${option.firstName} ${option.lastName}`}
                  options={filterCraftspeople(craftspeople, craftsperson)}
                  placeholder="Select a mentor"
                  onChange={addMentorCallBack}
                />
                {craftsperson.mentor && (
                  <Button
                    variant="danger"
                    data-testid="removementeebutton"
                    onClick={removeMentorCallback}
                  >
                    <FontAwesomeIcon icon={faTimes} size="lg" />
                  </Button>
                )}
              </div>
            </div>
          </Card.Header>
          <Accordion.Collapse eventKey="0">
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
