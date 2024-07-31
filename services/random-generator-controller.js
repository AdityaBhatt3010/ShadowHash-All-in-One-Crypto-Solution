// password-generator-controller.js

const UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
const LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
const DIGITS = "0123456789";
const SPECIAL = "!@#$%^&*()-_=+<>?";

const randomInt = (min, max) => Math.floor(Math.random() * (max - min + 1)) + min;
const randomElement = (str) => str.charAt(randomInt(0, str.length - 1));

const generatePassword = () => {
    const minLength = 8;
    const maxLength = 14;
    const length = randomInt(minLength, maxLength);

    let password = [];

    // Ensure at least one character from each category
    password.push(randomElement(UPPERCASE));
    password.push(randomElement(LOWERCASE));
    password.push(randomElement(DIGITS));
    password.push(randomElement(SPECIAL));

    // Fill the remaining characters randomly
    const allChars = UPPERCASE + LOWERCASE + DIGITS + SPECIAL;
    for (let i = password.length; i < length; i++) {
        let nextChar;
        do {
            nextChar = randomElement(allChars);
        } while (password.length > 0 && nextChar === password[password.length - 1]);
        password.push(nextChar);
    }

    // Shuffle to ensure randomness
    password.sort(() => Math.random() - 0.5);

    return password.join('');
};

module.exports = { generatePassword };
