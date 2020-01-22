export function validateLastMeetingThresold(lastMeeting) {
    validateLastMeetingThresoldWithCustomDate(new Date(), lastmeetingToDate(lastMeeting));
}

export function validateLastMeetingThresoldWithCustomDate(initDate, dateToCheck) {
    dateToCheck.setMonth(dateToCheck.getMonth() +2);
    return dateToCheck.getTime() > initDate.getTime();
}

export function lastmeetingToDate(lastMeeting) {
    return new Date(lastMeeting * 1000);
}

export function dateToLastMeeting(date) {
    return date.getTime() / 1000;
}