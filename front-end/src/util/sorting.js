export function sortByLastMetDate(leftCraftsperson, rightCraftsperson) {
  function bothHaveLastMetDate() {
    return leftCraftsperson.lastMeeting !== null && rightCraftsperson.lastMeeting !== null;
  }

  if (bothHaveLastMetDate(leftCraftsperson, rightCraftsperson)) {
    return sortAlphabetically(leftCraftsperson, rightCraftsperson);
  }
}

export function sortByNumberOfMentees(leftCraftsperson, rightCraftsperson) {
  function bothHaveSameNumberOfMentees() {
    return leftCraftsperson.mentees.length === rightCraftsperson.mentees.length;
  }

  if (bothHaveSameNumberOfMentees()) {
    return sortAlphabetically(leftCraftsperson, rightCraftsperson);
  }

  return rightCraftsperson.mentees.length - leftCraftsperson.mentees.length;
}

export function sortByCraftspeopleWithoutMentor(leftCraftsperson, rightCraftsperson) {
  function noneHaveMentor() {
    return leftCraftsperson.mentor == null && rightCraftsperson.mentor == null;
  }

  function bothHaveMentor() {
    return leftCraftsperson.mentor != null && rightCraftsperson.mentor != null;
  }

  if (noneHaveMentor() || bothHaveMentor()) {
    return sortAlphabetically(leftCraftsperson, rightCraftsperson);
  }

  if (leftCraftsperson.mentor == null) {
    return -1;
  }
  return 1;
}

export function sortAlphabetically(leftCraftsperson, rightCraftsperson) {
  return (leftCraftsperson.firstName + leftCraftsperson.lastName).localeCompare(
    rightCraftsperson.firstName + rightCraftsperson.lastName
  );
}
