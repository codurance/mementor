import { deburr } from "lodash";

export const filter = (craftspeople, searchedValue) => {
  // Deburr is a function that removes accents
  const searchedValueDeburred = deburr(searchedValue);
  return craftspeople.filter(
    craftsperson =>
      deburr(`${craftsperson.firstName} ${craftsperson.lastName}`)
        .toLowerCase()
        .indexOf(searchedValueDeburred.toLowerCase()) !== -1,
  );
};
