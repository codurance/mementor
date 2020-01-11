export const default_sort = function applyDefaultSortToCraftpeople(data){
    return data
        .sort((leftCraftsperson, rightCraftsperson) => {
            if (leftCraftsperson.mentees.length || rightCraftsperson.mentees.length){
                return rightCraftsperson.mentees.length - leftCraftsperson.mentees.length;
            }
            else{
                return (leftCraftsperson.firstName + leftCraftsperson.lastName).localeCompare(rightCraftsperson.firstName + rightCraftsperson.lastName);
            }
         });
};



