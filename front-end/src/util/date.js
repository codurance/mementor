export function lastMeetingToDate(lastMeeting) {
  return new Date(lastMeeting * 1000);
}

export function dateToLastMeeting(date) {
  return date.getTime() / 1000;
}

export function validateLastMeetingThresold(lastMeeting) {
  return validateLastMeetingThresoldWithCustomDate(
    new Date(),
    lastMeetingToDate(lastMeeting)
  );
}

export function validateLastMeetingThresoldWithCustomThreshold(
  lastMeeting,
  thresholdInWeeks
) {
  return validateLastMeetingThresoldWithCustomDateAndThreshold(
    new Date(),
    lastMeetingToDate(lastMeeting),
    thresholdInWeeks
  );
}

export function validateLastMeetingThresoldWithCustomDate(
  initDate,
  dateToCheck
) {
  const dateToCheckCopy = new Date(dateToCheck.getTime());
  dateToCheckCopy.setMonth(dateToCheckCopy.getMonth() + 2);
  return dateToCheckCopy.getTime() > initDate.getTime();
}

export function validateLastMeetingThresoldWithCustomDateAndThreshold(
  initDate,
  dateToCheck,
  thresholdInWeeks
) {
  const dateToCheckCopy = new Date(dateToCheck.getTime());
  dateToCheckCopy.setDate(dateToCheckCopy.getDate() + thresholdInWeeks * 7);
  return dateToCheckCopy.getTime() > initDate.getTime();
}
