import React from "react";
import { render, waitForElementToBeRemoved } from "@testing-library/react";
import userEvent from "@testing-library/user-event";
import "@testing-library/jest-dom/extend-expect";
import { filter } from "./filtering";

describe("filter functionality", () => {
  it("should consider accents", () => {
    const craftspeople = [{ firstName: "José", lastName: "Wenzel" }];

    const filteredCraftspeople = filter(craftspeople, "jose");

    expect(filteredCraftspeople.length).toBe(1);
    expect(filteredCraftspeople[0].firstName).toBe("José");
  });
});
