import "@testing-library/jest-dom/extend-expect";
import { validateInputString } from "./validate"

describe("validate input string function", () => {
    describe("should reject when", () => {
        it("is null", () => {
            expect(validateInputString(null)).toBeFalsy();
        });
        it("is empty", () => {
            expect(validateInputString("")).toBeFalsy();
        });
        it("is only whitespace", () => {
            expect(validateInputString("  ")).toBeFalsy();
        })
    });
    describe("should accept when", () => {
        it("a string is given", () => {
            expect(validateInputString("validInput")).toBeTruthy();
        });
        it("a string is preceded by whitespace", () => {
            expect(validateInputString("   valid")).toBeTruthy();
        });
        it("a string is followed by whitespace", () => {
            expect(validateInputString("valid  ")).toBeTruthy();
        });
    })
})