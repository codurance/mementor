export const default_sort = (data) => {
    return data
        .sort((craftsperson1, craftsperson2) => {return craftsperson2['mentees'].length - craftsperson1['mentees'].length});
};

