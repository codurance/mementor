export function sortByNumberOfMentees(leftCraftsperson, rightCraftsperson) {
    if (leftCraftsperson.mentees.length === rightCraftsperson.mentees.length) {
        return (leftCraftsperson.firstName + leftCraftsperson.lastName)
            .localeCompare(rightCraftsperson.firstName + rightCraftsperson.lastName);
    }

    return rightCraftsperson.mentees.length - leftCraftsperson.mentees.length;
}

export function sortByCraftspeopleWithoutMentor(leftCraftsperson, rightCraftsperson) {
    if (leftCraftsperson.mentor == null) return -1;
    if (rightCraftsperson.mentor == null) return 1;
    return 0
};