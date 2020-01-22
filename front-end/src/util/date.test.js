import "@testing-library/jest-dom/extend-expect";
import { validateLastMeetingThresoldWithCustomDate } from "./date";

describe("last meeting thresold function", () => {
  describe("with custom init date", () => {
    function doTest(initDate, dateToCheck, expectedResult) {
      expect(
        validateLastMeetingThresoldWithCustomDate(initDate, dateToCheck),
      ).toBe(expectedResult);
    }

    it("should reject dates that are 2 months before init date", () => {
      doTest(new Date(2019, 11, 0), new Date(2019, 8, 0), false);
      doTest(new Date(2019, 11, 0), new Date(2019, 9, 0), false);
    });

    it("should accept dates that are at less than 2 months before init date", () => {
      doTest(new Date(2019, 11, 0), new Date(2019, 10, 0), true);
      doTest(new Date(2019, 11, 0), new Date(2019, 9, 3), true);
      doTest(new Date(2019, 11, 0), new Date(2020, 0, 3), true);
    });
  });
});
