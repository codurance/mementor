export function validateLastMeetingThresoldWithCustomDate(initDate, dateToCheck) {
    dateToCheck.setMonth(dateToCheck.getMonth() +2);
    return dateToCheck.getTime() > initDate.getTime();
}