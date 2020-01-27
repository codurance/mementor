import React from "react";
import { render } from "@testing-library/react";
import userEvent from "@testing-library/user-event";
import "@testing-library/jest-dom/extend-expect";

import SearchBar from "./SearchBar";

describe("Search Component", () => {
  describe("With text", () => {
    it("should have a Button with the 'Clear Search' label", () => {
      const { getByTestId } = render(<SearchBar searchValue={'e'} updateSearchValue={() => 1} />);

      userEvent.type(getByTestId("SearchBarInput"), "Hello");

      expect(getByTestId("clearSearchButton")).toBeInTheDocument();
      expect(getByTestId("clearSearchButton")).toHaveTextContent(
        "Clear search",
      );
    });
  });
  describe("Without text", () => {
    it("should not display a <Button />", () => {
      const { queryByTestId } = render(<SearchBar searchValue={''} updateSearchValue={() => 1} />);
      expect(queryByTestId("clearSearchButton")).toBeNull();
    });
  });
});
