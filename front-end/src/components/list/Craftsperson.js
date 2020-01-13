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
        <h5><span className='mentorLabel' data-testid='craftspersonMentorLabel'>Mentored by:</span><br />
        <span className='mentor' data-testid='craftspersonMentorValue'>
          {craftsperson.mentor
            ? `${craftsperson.mentor.firstName} ${craftsperson.mentor.lastName}`
            : '-'}
        </span></h5>
      </div>
      <div className='col-lg-4'>
        <span className='menteeLabel' data-testid='craftspersonMenteeLabel'>Mentees: </span>
        <span className='mentee-count'>
          <h2 data-testid='craftspersonMenteeValue'>{craftsperson.mentees ? craftsperson.mentees.length : '0'}</h2>
        </span>
      </div>
    </div>
  );
}
