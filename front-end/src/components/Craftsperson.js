import React from "react";

export default function Craftsperson({craftsperson}){
    return (
        <div className="craftsperson-row">
            <span className="firstName">{craftsperson.firstName} </span>
            <span className="lastName">{craftsperson.lastName}, </span>
            <span className="mentorLabel">Mentor: </span>
            <span className="mentor">{craftsperson.mentor ? craftsperson.mentor.firstName + ' ' + craftsperson.mentor.lastName : '-'} </span>
            <span className="menteeLabel">Mentees: </span>
            {craftsperson.mentees.map(mentee => {
                return <span className="mentees">{mentee.firstName + ' ' + mentee.lastName} | </span>
            })}
            <span className="mentee-count">{craftsperson.mentees ? craftsperson.mentees.length : '0'}</span>
        </div>
    )
}
    