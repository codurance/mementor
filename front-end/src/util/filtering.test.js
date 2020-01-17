import React from "react";
import "@testing-library/jest-dom/extend-expect";
import {filter} from "./filtering";

describe("filter functionality", () => {
    it("should work without accent in the search", () => {
        const craftspeople = [{firstName: "José", lastName: "Wenzel"}];

        const filteredCraftspeople = filter(craftspeople, "jose");

        expect(filteredCraftspeople.length).toBe(1);
        expect(filteredCraftspeople[0].firstName).toBe("José");
    });

    it("should work with accent in the search", () => {
        const craftspeople = [{firstName: "José", lastName: "Wenzel"}];

        const filteredCraftspeople = filter(craftspeople, "josé");

        expect(filteredCraftspeople.length).toBe(1);
        expect(filteredCraftspeople[0].firstName).toBe("José");
    });
});
