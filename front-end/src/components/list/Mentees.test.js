import React from "react";
import { render, getByTestId } from "@testing-library/react";
import "@testing-library/jest-dom/extend-expect";
import Mentees from "./Mentees";

describe("when rendering", () => {
  describe("with initial state", () => {
    describe("and no mentees", () => {
      it("should render the add mentee row", () => {
        const { getByTestId } = render(
          <Mentees
            rerender={() => {}}
            craftsperson={[]}
            mentees={[]}
            craftspeople={[]}
          />,
        );
        expect(getByTestId("add-mentee-row")).toBeInTheDocument();
      });
    });
    describe("and a mentee", () => {
      it("should render the mentee and the add-mentee row", () => {
        const { getByTestId } = render(
          <Mentees
            rerender={() => {}}
            craftsperson={[]}
            mentees={[
              {
                id: 1,
                firstName: "Giulio",
                lastName: "Perrone",
              },
            ]}
            craftspeople={[]}
          />,
        );
        expect(getByTestId("mentee-1")).toBeInTheDocument();
        expect(getByTestId("add-mentee-row")).toBeInTheDocument();
      });
    });
  });
});
