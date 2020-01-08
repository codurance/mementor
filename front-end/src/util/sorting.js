export const default_sort = (data) => {
    return data
        .sort((a, b) => {
            return b['mentees'].length - a['mentees'].length})
        .map((craftsperson) => {
            if(craftsperson['mentor'] != null)
                return 'Craftsperson: ' + craftsperson['firstName'] + ' ' + craftsperson['lastName'] + ' - Mentor: ' + craftsperson['mentor'].split(' ')[0] + ' ' + craftsperson['mentor'].split(' ')[1];
            return 'Craftsperson: ' + craftsperson['firstName'] + ' ' + craftsperson['lastName'];
        });
};