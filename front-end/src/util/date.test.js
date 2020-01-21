import "@testing-library/jest-dom/extend-expect";
import { formatDate } from "./date";

describe("when converting number of seconds", () => {
  it("should return a formatted date", () => {
    expect(formatDate(1500000000)).toBe("July 2017");
    expect(formatDate(1600000000)).toBe("September 2020");
  });
  it("should return - when given null", () => {
    expect(formatDate(null)).toBe("-");
  });
  it("should return - when given nothing", () => {
    expect(formatDate()).toBe("-");
  });
  it("should return - when given undefined", () => {
    expect(formatDate(undefined)).toBe("-");
  });
});
