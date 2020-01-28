import "@testing-library/jest-dom/extend-expect";
import { filter, filterCraftspeople } from "./filtering";

describe("filter functionality", () => {
  it("should work without accent in the search", () => {
    const craftspeople = [{ firstName: "José", lastName: "Wenzel" }];

    const filteredCraftspeople = filter(craftspeople, "jose");

    expect(filteredCraftspeople.length).toBe(1);
    expect(filteredCraftspeople[0].firstName).toBe("José");
  });

  it("should work with accent in the search", () => {
    const craftspeople = [{ firstName: "José", lastName: "Wenzel" }];

    const filteredCraftspeople = filter(craftspeople, "josé");

    expect(filteredCraftspeople.length).toBe(1);
    expect(filteredCraftspeople[0].firstName).toBe("José");
  });

  it("should exclude the craftsperson from the craftspeople list", () => {
    const craftspeople = [
      { id: 1, firstName: "José", lastName: "Wenzel" },
      { id: 2, firstName: "ed", lastName: "rixon" },
      { id: 3, firstName: "abc", lastName: "123" },
      { id: 4, firstName: "def", lastName: "456" }
    ];

    const filterResult = filterCraftspeople(craftspeople, craftspeople[0]);

    expect(filterResult.length).toBe(3);
    expect(filterResult[0].firstName).toBe("ed");
  });

  it("should exclude mentor from the craftspeople list", () => {
    const craftspeople = [
      {
        id: 1,
        firstName: "José",
        lastName: "Wenzel",
        mentor: { id: 2, firstName: "ed", lastName: "rixon" }
      },
      { id: 2, firstName: "ed", lastName: "rixon" },
      { id: 3, firstName: "abc", lastName: "123" },
      { id: 4, firstName: "def", lastName: "456" }
    ];

    const filterResult = filterCraftspeople(craftspeople, craftspeople[0]);
    expect(filterResult.length).toBe(2);
    expect(filterResult[0].firstName).toBe("abc");
  });

  it("should exclude mentee from the craftspeople list", () => {
    const craftspeople = [
      {
        id: 1,
        firstName: "José",
        lastName: "Wenzel",
        mentees: [
          { id: 3, firstName: "abc", lastName: "123" },
          { id: 4, firstName: "def", lastName: "456" }
        ]
      },
      { id: 2, firstName: "ed", lastName: "rixon" },
      { id: 3, firstName: "abc", lastName: "123" },
      { id: 4, firstName: "def", lastName: "456" }
    ];

    const filterResult = filterCraftspeople(craftspeople, craftspeople[0]);
    expect(filterResult.length).toBe(1);
    expect(filterResult[0].firstName).toBe("ed");
  });
});
