import React from 'react'
import {render} from '@testing-library/react'
import '@testing-library/jest-dom/extend-expect'
import Craftsperson from './Craftsperson'

describe ('when rendering a craftsperson', () => {
    it('should show the name', () => {
        const {getByTestId} = render(<Craftsperson craftsperson={{firstName: "Arnaud", lastName: "Claudel"}} />)
        expect(getByTestId('craftspersonName')).toHaveTextContent('Arnaud Claudel')
    })
    it('should show the mentor', () => {
        const {getByTestId} = render(<Craftsperson craftsperson={{mentor: {firstName: "Etienne", lastName: "Mustow"}}} />)
        expect(getByTestId('craftspersonMentorLabel')).toHaveTextContent('Mentored by:')
        expect(getByTestId('craftspersonMentorValue')).toHaveTextContent('Etienne Mustow')
    })
    it('should show the number mentees', () => {
        const listOfMentees = [{firstName: "Etienne", lastName: "Mustow"}, {firstName: "Arnaud", lastName: "Claudel"}, {firstName: "Naruto", lastName: "Uzumaki"}]
        const {getByTestId} = render(<Craftsperson craftsperson={{mentees: listOfMentees}} />)
        expect(getByTestId('craftspersonMenteeLabel')).toHaveTextContent('Mentees:')
        expect(getByTestId('craftspersonMenteeValue')).toHaveTextContent('3')
    })
})