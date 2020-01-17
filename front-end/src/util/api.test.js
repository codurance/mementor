import {api} from "./api";

describe("Testing the API", () => {
    beforeEach(() => {
        fetch.resetMocks()
    });

    it("should call the endpoint once", () => {
        fetch.mockResponseOnce();

        api("restApiTest").then(_ => {
        });

        expect(fetch.mock.calls.length).toEqual(1);
    });

    it("should return a non empty array", () => {
        // should the answer be wrapped in a "data" key?
        fetch.mockResponseOnce(JSON.stringify([{id: 1}, {id: 2}]));

        api("restApiTest").then(res => {
            expect(res.value).toBeInstanceOf(Array);
            expect(res.value).not.toBeEmpty();
        })
    });
});


