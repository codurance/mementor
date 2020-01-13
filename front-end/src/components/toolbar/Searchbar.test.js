import React from "react";
import { render } from "@testing-library/react";
import "@testing-library/jest-dom/extend-expect";

import SearchBar from "./SearchBar";

describe("Search Component", () => {
  it("should have a Button with the 'Clear Search' label", () => {
    const { getByTestId } = render(<SearchBar />);

    expect(getByTestId("clearSearchButton")).toHaveTextContent("Clear search");
  });

  it("should display a <Button /> when is not empty", () => {
    const { getByTestId } = render(<SearchBar />);

    expect(getByTestId("clearSearchButton")).not.toHaveClass("is-hidden");
  });
});
