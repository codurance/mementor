import React from "react";
import { render, getByTestId } from "@testing-library/react";
import "@testing-library/jest-dom/extend-expect";
import ManageCraftsperson from "./ManageCraftsperson";
import CraftspersonList from "./CraftspersonList";

describe("In admin modal", () => {
    describe("when remove a craftsperson", () => {
        it("should call api with craftsperson id", () => {
            /*
            open the modal
            select a craftperson
            click delete 
            verify the call
            */

            const { getByTestId } = render(<ManageCraftsperson craftspeople={[{id:"1",firstName:"Joe",lastName:"Doe"}]}/>);
            
            expect(getByTestId('craftspersonRowItem')).toHaveTextContent('Joe Doe')

            queryByAttribute()
            expect(getByTestId(""))
        })
    })
})