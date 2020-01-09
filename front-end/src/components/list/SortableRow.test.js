import SortableRow from "./SortableRow";
import { shallow } from "enzyme"
import React from "react";

describe("SortableRow component", () => {

    it("should render", () => {
        const component = shallow(<SortableRow craftsperson={{mentees: []}} />);

        expect(component.exists()).toBe(true);
    });
});
