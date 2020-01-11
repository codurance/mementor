import FIXTURE from './fixture';
import {defaultSort} from "./sorting";

const fixtureData = Array.from(FIXTURE);

describe('when given an array of objects', () =>  {
    describe('with craftpeople only with mentees', () => {
        it('should sort by length of subarray', () => {
            expect(defaultSort(fixtureData.slice(1, 4))).toEqual(
                Array.from([
                    {
                        "firstName": "Riccardo",
                        "lastName": "Toni",
                        "mentees": ["ed rixon", "jose pablo", "giulio perrone"]
                    },
                    {
                        "firstName": "Ed",
                        "lastName": "Rixon",
                        "mentees": ["arnaud claudel", "giulia mantuano"]
                    },
                    {
                        "firstName": "Etienne",
                        "lastName": "Mustow",
                        "mentees": ["riccardo toni"]
                    }
                ])
            )
    });

    })
});