export const default_sort = (data) => {
    return data
        .sort((a, b) => {
            return b['mentees'].length - a['mentees'].length})
        .map((craftsperson) => {
            return craftsperson["firstName"] + ' ' + craftsperson["lastName"];
        });
};