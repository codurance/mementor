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

export const filterCraftspeople = (craftspeople_list, craftsperson) => {
  let set_ids_craftspeople = new Set(craftspeople_list.map(_ => _.id));
  const id_yourself = craftsperson.id;

  if (craftsperson.mentees) {
    const set_ids_mentee = new Set(craftsperson.mentees.map(_ => _.id));
    set_ids_craftspeople = new Set(
      [...set_ids_craftspeople].filter(
        craftsperson => !set_ids_mentee.has(craftsperson),
      ),
    );
  }

  if (craftsperson.mentor) {
    const id_mentor = craftsperson.mentor.id;
    set_ids_craftspeople.delete(id_mentor);
  }

  set_ids_craftspeople.delete(id_yourself);
  return craftspeople_list.filter(craftsperson =>
    set_ids_craftspeople.has(craftsperson.id),
  );
};
