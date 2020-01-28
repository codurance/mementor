import FIXTURE from "./fixture";
import {
  sortByNumberOfMentees,
  sortByCraftspeopleWithoutMentor,
  sortAlphabetically,
  sortByLastMeetingDate
} from "./sorting";

const fixtureData = Array.from(FIXTURE);

describe("when given an array of objects", () => {
  describe("only with craftpeople with mentees", () => {
    it("should sort by number of mentees", () => {
      expect(fixtureData.slice(1, 4).sort(sortByNumberOfMentees)).toEqual(
        Array.from([
          {
            firstName: "Riccardo",
            lastName: "Toni",
            mentees: ["ed rixon", "jose pablo", "giulio perrone"]
          },
          {
            firstName: "Ed",
            lastName: "Rixon",
            mentees: ["arnaud claudel", "giulia mantuano"]
          },
          {
            firstName: "Etienne",
            lastName: "Mustow",
            mentees: ["riccardo toni"]
          }
        ])
      );
    });
  });

  describe("with craftspeople only without mentees", () => {
    it("should sort alphabetically", () => {
      expect(
        fixtureData
          .slice(fixtureData.length - 3, fixtureData.length)
          .sort(sortByNumberOfMentees)
      ).toEqual(
        Array.from([
          {
            firstName: "Giulio",
            lastName: "Perrone",
            mentees: []
          },
          {
            firstName: "Jose",
            lastName: "Ernesto",
            mentees: []
          },
          {
            firstName: "Jose",
            lastName: "Pablo",
            mentees: []
          }
        ])
      );
    });
  });

  describe("with craftspeople with mentees and without", () => {
    it("should sort by number of mentees and then alphabetically", function() {
      expect(fixtureData.sort(sortByNumberOfMentees)).toEqual(
        Array.from([
          {
            firstName: "Riccardo",
            lastName: "Toni",
            mentees: ["ed rixon", "jose pablo", "giulio perrone"]
          },
          {
            firstName: "Ed",
            lastName: "Rixon",
            mentees: ["arnaud claudel", "giulia mantuano"]
          },
          {
            firstName: "Arnaud",
            lastName: "Claudel",
            mentor: "El hombre",
            mentees: ["etienne mustow"]
          },
          {
            firstName: "Etienne",
            lastName: "Mustow",
            mentees: ["riccardo toni"]
          },
          {
            firstName: "Giulio",
            lastName: "Perrone",
            mentees: []
          },
          {
            firstName: "Jose",
            lastName: "Ernesto",
            mentees: []
          },
          {
            firstName: "Jose",
            lastName: "Pablo",
            mentees: []
          }
        ])
      );
    });
  });
});

describe("craftspeople without mentor sort", () => {
  it("should put the craftsperson with a mentor last", () => {
    const craftpeople = [
      { id: 0, firstName: "Arnaud", lastName: "Claudel", mentor: null },
      { id: 1, firstName: "Brnaud", lastName: "Claudel", mentor: {} },
      { id: 2, firstName: "Drnaud", lastName: "Claudel", mentor: null }
    ];
    const sortedCraftpeople = craftpeople.sort(sortByCraftspeopleWithoutMentor);
    expect(sortedCraftpeople[2].id).toBe(1);
  });

  it("should put the craftsperson without a mentor first", () => {
    const craftpeople = [
      { id: 0, firstName: "Arnaud", lastName: "Claudel", mentor: {} },
      { id: 1, firstName: "Brnaud", lastName: "Claudel", mentor: null },
      { id: 2, firstName: "Drnaud", lastName: "Claudel", mentor: {} }
    ];
    const sortedCraftpeople = craftpeople.sort(sortByCraftspeopleWithoutMentor);
    expect(sortedCraftpeople[0].id).toBe(1);
  });

  it("should also sort alphabetically if equal", () => {
    const craftpeople = [
      { id: 0, firstName: "Arnaud", lastName: "Claudel", mentor: null },
      { id: 3, firstName: "Brnaud", lastName: "Claudel", mentor: {} },
      { id: 1, firstName: "Drnaud", lastName: "Claudel", mentor: {} },
      { id: 2, firstName: "Crnaud", lastName: "Claudel", mentor: null }
    ];
    const sortedCraftpeople = craftpeople.sort(sortByCraftspeopleWithoutMentor);
    expect(sortedCraftpeople[0].id).toBe(0);
    expect(sortedCraftpeople[1].id).toBe(2);
    expect(sortedCraftpeople[2].id).toBe(3);
    expect(sortedCraftpeople[3].id).toBe(1);
  });
});

describe("Alphabetical sort", () => {
  it("should sort alphabetically craftspeople", () => {
    const craftspeople = [
      { id: 0, firstName: "Etienne", lastName: "Mustow" },
      { id: 1, firstName: "Arnaud", lastName: "Claudel" },
      { id: 2, firstName: "Naruto", lastName: "Uzumaki" }
    ];

    craftspeople.sort(sortAlphabetically);
    expect(craftspeople[0].id).toBe(1);
    expect(craftspeople[1].id).toBe(0);
    expect(craftspeople[2].id).toBe(2);
  });
});

describe("Sort by last met date", () => {
  it("should sort by oldest meeting date", () => {
    const craftspeople = [
      {
        id: 0,
        firstName: "Etienne",
        lastName: "Mustow",
        lastMeeting: new Date(2020, 1, 15)
      },
      {
        id: 1,
        firstName: "Arnaud",
        lastName: "Claudel",
        lastMeeting: new Date(2020, 1, 14)
      },
      {
        id: 2,
        firstName: "Naruto",
        lastName: "Uzumaki",
        lastMeeting: new Date(2020, 1, 13)
      }
    ];

    craftspeople.sort(sortByLastMeetingDate);
    expect(craftspeople[0].id).toBe(2);
    expect(craftspeople[1].id).toBe(1);
    expect(craftspeople[2].id).toBe(0);
  });

  it("should give precedence to craftsperson with meeting date over craftsperson with no meeting date", () => {
    const craftspeople = [
      {
        id: 0,
        firstName: "Ade",
        lastName: "Adegboye",
        mentor: null,
        mentees: [],
        lastMeeting: null
      },
      {
        id: 1,
        firstName: "Alasdair",
        lastName: "Smith",
        mentor: { id: 5, firstName: "Liam", lastName: "Griffin-Jowett" },
        mentees: [],
        lastMeeting: 1577750400
      }
    ];

    craftspeople.sort(sortByLastMeetingDate);

    expect(craftspeople[0].id).toBe(1);
    expect(craftspeople[1].id).toBe(0);
  });
});
