  import React from "react";
  import { render } from "@testing-library/react";
  import "@testing-library/jest-dom/extend-expect";
  import LastMeeting from "./LastMeeting";
  import {dateToLastMeeting} from '../../util/date';

describe("last meeting component", () => {
  it("should show last meeting", () => {
    const { getByTestId } = render(
      <LastMeeting
        craftsperson={{
          mentor: { firstName: "", lastName: "" },
          lastMeeting: 1500000000,
        }}
        craftspeople={[]}
      />,
    );
    expect(getByTestId("lastMeetingDatePicker").value).toBe("14 July 2017");
  });

  it("shouldnt show date picker when no mentor", () => {
    const { queryByTestId, getByTestId } = render(
      <LastMeeting craftsperson={{}} craftspeople={[]} />,
    );
    expect(queryByTestId("lastMeetingDatePicker")).toBe(null);
    expect(getByTestId("date-picker-container")).toHaveTextContent("-");
  });

  it("should be empty when no last meeting", () => {
    const { getByTestId } = render(
      <LastMeeting craftsperson={{ mentor: {} }} craftspeople={[]} />,
    );
    expect(getByTestId("lastMeetingDatePicker").value).toBe("");
  });

  it("shouldnt show last meeting alert if it is less than 2 months ago", () => {
    const lastMeetingDate = new Date();
    lastMeetingDate.setMonth(lastMeetingDate.getMonth() - 1);
    
    const { queryByTestId } = render(
      <LastMeeting
        craftsperson={{
          mentor: { firstName: "", lastName: "" },
          lastMeeting: dateToLastMeeting(lastMeetingDate),
        }}
        craftspeople={[]}
      />,
    );
    expect(queryByTestId("last-meeting-alert")).toBeNull();
  })

  it("should show last meeting alert if it is at least 2 months ago", () => {
    const lastMeetingDate = new Date();
    lastMeetingDate.setMonth(lastMeetingDate.getMonth() - 3);
    
    const { queryByTestId } = render(
      <LastMeeting
        craftsperson={{
          mentor: { firstName: "", lastName: "" },
          lastMeeting: dateToLastMeeting(lastMeetingDate),
        }}
        craftspeople={[]}
      />,
    );
    expect(queryByTestId("last-meeting-alert")).not.toBeNull();
  })

  it("shouldnt show last meeting alert if there is no last meeting", () => {
    const { queryByTestId } = render(
      <LastMeeting
        craftsperson={{
          mentor: { firstName: "", lastName: "" },
          lastMeeting: null,
        }}
        craftspeople={[]}
      />,
    );
    expect(queryByTestId("last-meeting-alert")).toBeNull();
  })

});
