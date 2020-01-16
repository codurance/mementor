import React from 'react';

export default function Craftsperson({ craftsperson }) {
  return (
    <div className='row'>
      <div className='col-lg-4'>
        <h2 className='craftspersonName' data-testid='craftspersonName'>
          {craftsperson.firstName} {craftsperson.lastName}
        </h2>
      </div>
      <div className='col-lg-4'>
        <h5><span className='mentorLabel' data-testid='craftspersonMentorLabel'>Mentored by:</span></h5>
      </div>
      <div className='col-lg-4'>
        <span className='mentee-count'>
          <h2 data-testid='craftspersonMenteeValue'>{craftsperson.mentees ? craftsperson.mentees.length : '0'}</h2>
        </span>
        <span className='menteeLabel' data-testid='craftspersonMenteeLabel'><i>Mentees</i></span>
      </div>
    </div>
  );
}
