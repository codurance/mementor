export function sortByNumberOfMentees(leftCraftsperson, rightCraftsperson) {
  if (bothHaveSameNumberOfMentees(leftCraftsperson, rightCraftsperson)) {
    return sortAlphabetically(leftCraftsperson, rightCraftsperson);
  }

  return rightCraftsperson.mentees.length - leftCraftsperson.mentees.length;
}

export function sortByCraftspeopleWithoutMentor(
  leftCraftsperson,
  rightCraftsperson,
) {
  if (
    noneHaveMentor(leftCraftsperson, rightCraftsperson) ||
    bothHaveMentor(leftCraftsperson, rightCraftsperson)
  ) {
    return sortAlphabetically(leftCraftsperson, rightCraftsperson);
  }

  if (leftCraftsperson.mentor == null) {
    return -1;
  }
  return 1;
}

export function sortAlphabetically(leftCraftsperson, rightCraftsperson) {
  return (leftCraftsperson.firstName + leftCraftsperson.lastName).localeCompare(
    rightCraftsperson.firstName + rightCraftsperson.lastName,
  );
}

function bothHaveSameNumberOfMentees(leftCraftsperson, rightCraftsperson) {
  return leftCraftsperson.mentees.length === rightCraftsperson.mentees.length;
}

function noneHaveMentor(leftCraftsperson, rightCraftsperson) {
  return leftCraftsperson.mentor == null && rightCraftsperson.mentor == null;
}

function bothHaveMentor(leftCraftsperson, rightCraftsperson) {
  return leftCraftsperson.mentor != null && rightCraftsperson.mentor != null;
}
