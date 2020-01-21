import React from "react";
import { render } from "@testing-library/react";
import "@testing-library/jest-dom/extend-expect";
import Craftsperson from "./Craftsperson";

describe("when rendering a craftsperson", () => {
  it("should show the name", () => {
    const { getByTestId } = render(
      <Craftsperson
        craftsperson={{ firstName: "Arnaud", lastName: "Claudel" }}
      />,
    );
    expect(getByTestId("craftspersonName")).toHaveTextContent("Arnaud Claudel");
  });

  describe("with his mentees", () => {
    it("should show the label", () => {
      const { getByTestId } = render(
        <Craftsperson craftsperson={{ mentees: null }} />,
      );
      expect(getByTestId("craftspersonMenteeLabel")).toHaveTextContent(
        "Mentees",
      );
    });

    it("should show the number mentees", () => {
      const listOfMentees = [
        { firstName: "Etienne", lastName: "Mustow" },
        { firstName: "Arnaud", lastName: "Claudel" },
        { firstName: "Naruto", lastName: "Uzumaki" },
      ];
      const { getByTestId } = render(
        <Craftsperson craftsperson={{ mentees: listOfMentees }} />,
      );
      expect(getByTestId("craftspersonMenteeValue")).toHaveTextContent("3");
    });

    it("should show 0 if the list is null", () => {
      const { getByTestId } = render(
        <Craftsperson craftsperson={{ mentees: null }} />,
      );
      expect(getByTestId("craftspersonMenteeValue")).toHaveTextContent("0");
    });
  });

  it("should show last meeting", () => {
    const { getByTestId } = render(
      <Craftsperson craftsperson={{ mentor: {}, lastMeeting: 1500000000 }} />,
    );
    expect(getByTestId("lastMeetingDatePicker").value).toBe("14 July 2017");
  });
  it("shouldnt show date picker when no mentor", () => {
    const { queryByTestId } = render(<Craftsperson craftsperson={{}} />);
    expect(queryByTestId("lastMeetingDatePicker")).toBe(null);
  });
  if (
    ("should be empty when no last meeting",
    () => {
      const { getByTestId } = render(
        <Craftsperson craftsperson={{ mentor: {} }} />,
      );
      expect(getByTestId("lastMeetingDatePicker").value).toBe("");
    })
  );
});
