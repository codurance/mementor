  import React from "react";
  import { render } from "@testing-library/react";
  import "@testing-library/jest-dom/extend-expect";
  import LastMeeting from "./LastMeeting";

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

  it("should show last meeting alert if there is a last meeting", () => {
    const { queryByTestId } = render(
      <LastMeeting
        craftsperson={{
          mentor: { firstName: "", lastName: "" },
          lastMeeting: 1500000000,
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
