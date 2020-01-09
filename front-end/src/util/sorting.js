export const default_sort = (data) => {
    return data
        .sort((craftsperson1, craftsperson2) => {return craftsperson2['mentees'].length - craftsperson1['mentees'].length})
        .map((craftsperson) => {
           const person = 'Craftsperson: ' + craftsperson['firstName'] + ' ' + craftsperson['lastName'];

           let mentor = '-';
           if(craftsperson['mentor'] != null) {
                   mentor = craftsperson.mentor.firstName + ' ' + craftsperson.mentor.lastName;
            }

           let mentees = '-';
           if(craftsperson.mentees.length > 0) {
               mentees = craftsperson.mentees
                   .map(m => m.firstName + ' ' + m.lastName)
                   .reduce((m1, m2) => m1 + ', ' + m2);
           }

           return [person, 'Mentor: ' + mentor, 'Mentees: ' + mentees].join(' | ');
       });
};

