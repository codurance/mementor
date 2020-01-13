import React from 'react'
import {render} from '@testing-library/react'
import '@testing-library/jest-dom/extend-expect'
import Craftsperson from './Craftsperson'

describe ('when rendering a craftsperson', () => {
    it('should show the name', () => {
        const {getByTestId} = render(<Craftsperson craftsperson={{firstName: "Arnaud", lastName: "Claudel"}} />)
        expect(getByTestId('craftspersonName')).toHaveTextContent('Arnaud Claudel')
    })

    describe('with his mentor', () => {
        it('should show the label', () => {
            const {getByTestId} = render(<Craftsperson craftsperson={{mentor: null}} />)
            expect(getByTestId('craftspersonMentorLabel')).toHaveTextContent('Mentored by:')
        })
        it('should show the mentor name', () => {
            const {getByTestId} = render(<Craftsperson craftsperson={{mentor: {firstName: "Etienne", lastName: "Mustow"}}} />)
            expect(getByTestId('craftspersonMentorLabel')).toHaveTextContent('Mentored by:')
            expect(getByTestId('craftspersonMentorValue')).toHaveTextContent('Etienne Mustow')
        })
        it('should show a dash if there is no mentor', () => {
            const {getByTestId} = render(<Craftsperson craftsperson={{mentor: null}}/>)
            expect(getByTestId('craftspersonMentorValue')).toHaveTextContent('-')
        })
    })

    describe('with his mentees', () => {
        it('should show the label', () => {
            const {getByTestId} = render(<Craftsperson craftsperson={{mentees: null}} />)
            expect(getByTestId('craftspersonMenteeLabel')).toHaveTextContent('Mentees:')
        })
        it('should show the number mentees', () => {
            const listOfMentees = [
                    {firstName: "Etienne", lastName: "Mustow"}, 
                    {firstName: "Arnaud", lastName: "Claudel"}, 
                    {firstName: "Naruto", lastName: "Uzumaki"}]
            const {getByTestId} = render(<Craftsperson craftsperson={{mentees: listOfMentees}} />)
            expect(getByTestId('craftspersonMenteeValue')).toHaveTextContent('3')
        })
        it('should show 0 if the list is null', () => {
            const {getByTestId} = render(<Craftsperson craftsperson={{mentees: null}} />)
            expect(getByTestId('craftspersonMenteeValue')).toHaveTextContent('0')
        })
    })  
})