import SortableList from "./SortableList";
import React from "react";
import { shallow } from "enzyme";

describe("SortableList component", () => {

    it("should render", () => {
       const component = shallow(<SortableList/>);
       expect(component.exists()).toBe(true);
    });
});