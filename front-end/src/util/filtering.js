import { deburr } from "lodash";

export const filter = (craftspeople, searchedValue) =>
  craftspeople
  .filter(
    craftsperson =>
      deburr(`${craftsperson.firstName} ${craftsperson.lastName}`)
        .toLowerCase()
        .indexOf(searchedValue.toLowerCase()) !== -1
  );
