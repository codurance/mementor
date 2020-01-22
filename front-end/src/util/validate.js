export function validateInputString(stringToValidate) {
  return stringToValidate && stringToValidate.trim().length > 0;
}

export function validateName(firstName, lastName) {
  return {
    firstNameValid: validateInputString(firstName),
    lastNameValid: validateInputString(lastName)
  };
}
