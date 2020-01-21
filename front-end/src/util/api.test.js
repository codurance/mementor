import { api } from "./api";

describe("Testing the API", () => {
  beforeEach(() => {
    fetch.resetMocks();
  });

  it("should call the endpoint once", () => {
    fetch.mockResponseOnce();

    api({
      endpoint: "/restApiTest",
    }).then(_ => {});

    expect(fetch.mock.calls.length).toEqual(1);
  });
});
