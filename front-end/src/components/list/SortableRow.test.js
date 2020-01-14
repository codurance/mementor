import SortableRow from "./SortableRow";
import {render} from '@testing-library/react'
import React from "react";
import Craftsperson from "./Craftsperson";

describe("SortableRow Component", () => {

    it("should render with mentees", () => {
        const listOfMentees = [
            {id: 0, firstName: "Etienne", lastName: "Mustow"}, 
            {id: 1, firstName: "Arnaud", lastName: "Claudel"}, 
            {id: 2, firstName: "Naruto", lastName: "Uzumaki"}]
        const {getByTestId} = render(<SortableRow craftsperson={{mentees: listOfMentees}} />);

        const getMenteeName = (index) => getByTestId('menteesList').children[index].getElementsByClassName('menteeName')[0].textContent

        expect(getByTestId('menteesList').children.length).toBe(3)
        expect(getMenteeName(0)).toBe('Etienne Mustow')
        expect(getMenteeName(1)).toBe('Arnaud Claudel')
        expect(getMenteeName(2)).toBe('Naruto Uzumaki')
    });
});
