import React from "react";
import { shallow, render, mount } from "enzyme";
import "@testing-library/jest-dom/extend-expect";

import SearchBar from "./SearchBar";

describe("Search Component", () => {

    it("should render", () => {
        const component = shallow(<SearchBar/>);

        expect(component.exists()).toBe(true);
    });

    it("should render with class named 'search-bar'", () => {
        const component = shallow(<SearchBar/>);

        expect(component.hasClass("search-bar")).toBe(true);
    });

    it("should render with placeholder 'Search for craftsperson'", () => {
        const component = shallow(<SearchBar/>);

        expect(component.prop("placeholder")).toEqual("Search for craftsperson");
    });

});