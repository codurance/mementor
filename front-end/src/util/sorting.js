const LEFT_CRAFTSPERSON_FIRST = -1;
const RIGHT_CRAFTSPERSON_FIRST = 1;

export function sortByLastMeetingDate(leftCraftsperson, rightCraftsperson) {
  const bothHaveLastMeeting =
    leftCraftsperson.lastMeeting !== null &&
    rightCraftsperson.lastMeeting !== null;

  const bothHaveSameLastMeetingDate =
    leftCraftsperson.lastMeeting === rightCraftsperson.lastMeeting;

  if (bothHaveLastMeeting && !bothHaveSameLastMeetingDate) {
    return leftCraftsperson.lastMeeting - rightCraftsperson.lastMeeting;
  }

  if (leftCraftsperson.lastMeeting !== null) {
    return LEFT_CRAFTSPERSON_FIRST;
  }
  if (rightCraftsperson.lastMeeting !== null) {
    return RIGHT_CRAFTSPERSON_FIRST;
  }

  return sortAlphabetically(leftCraftsperson, rightCraftsperson);
}

export function sortByNumberOfMentees(leftCraftsperson, rightCraftsperson) {
  const bothHaveSameNumberOfMentees =
    leftCraftsperson.mentees.length === rightCraftsperson.mentees.length;

  if (bothHaveSameNumberOfMentees) {
    return sortAlphabetically(leftCraftsperson, rightCraftsperson);
  }

  return rightCraftsperson.mentees.length - leftCraftsperson.mentees.length;
}

export function sortByCraftspeopleWithoutMentor(
  leftCraftsperson,
  rightCraftsperson
) {
  const noneHaveMentor =
    leftCraftsperson.mentor == null && rightCraftsperson.mentor == null;
  const bothHaveMentor =
    leftCraftsperson.mentor != null && rightCraftsperson.mentor != null;

  if (noneHaveMentor || bothHaveMentor) {
    return sortAlphabetically(leftCraftsperson, rightCraftsperson);
  }

  if (leftCraftsperson.mentor == null) {
    return LEFT_CRAFTSPERSON_FIRST;
  }
  return RIGHT_CRAFTSPERSON_FIRST;
}

export function sortAlphabetically(leftCraftsperson, rightCraftsperson) {
  return (leftCraftsperson.firstName + leftCraftsperson.lastName).localeCompare(
    rightCraftsperson.firstName + rightCraftsperson.lastName
  );
}
