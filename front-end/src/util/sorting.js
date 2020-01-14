export const defaultSort = function applyDefaultSortToCraftpeople(data) {
    return data
        .sort((leftCraftsperson, rightCraftsperson) => {
            if (leftCraftsperson.mentees.length === rightCraftsperson.mentees.length) {
                return (leftCraftsperson.firstName + leftCraftsperson.lastName)
                    .localeCompare(rightCraftsperson.firstName + rightCraftsperson.lastName);
            }

            if (leftCraftsperson.mentees.length || rightCraftsperson.mentees.length) {
                return rightCraftsperson.mentees.length - leftCraftsperson.mentees.length;
            }
         });
};

export const craftspeopleWithoutMentorSort = (leftCraftsperson, rightCraftsperson) => {
    if(leftCraftsperson.mentor == null) return -1;
    if(rightCraftsperson.mentor == null) return 1;
    return 0
};