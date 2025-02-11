const path = require('path');
// Function to check password strength
async function checkPasswordStrength(password) {
    let rating = 0;

    // Parameter 1: Length (at least 8 characters)
    if (password.length >= 8) {
        rating++;
    }

    // Parameter 2: Uppercase letters
    if (/[A-Z]/.test(password)) {
        rating++;
    }

    // Parameter 3: Lowercase letters
    if (/[a-z]/.test(password)) {
        rating++;
    }

    // Parameter 4: Digits
    if (/\d/.test(password)) {
        rating++;
    }

    // Parameter 5: Special characters
    if (/[^A-Za-z0-9]/.test(password)) {
        rating++;
    }

    // Parameter 6: No consecutive characters
    if (!/(.)\1/.test(password)) {
        rating++;
    }

    // Parameter 7: No common patterns (read from file)
    try {
        const commonPatterns = await readCommonPatternsFromFile('./public/assets/passwordlist.txt');
        if (!commonPatterns.includes(password)) {
            rating += 2;
        }
    } catch (error) {
        rating+=2;
    }

    // Parameter 8: No sequential characters
    if (!isSequential(password)) {
        rating += 2;
    }

    return {
        rating: rating,
        message: rating >= 8 ? 'Password is strong!' : 'Password is weak. Try to improve it!'
    };
}

// Function to read common patterns from file
async function readCommonPatternsFromFile(filename) {
    const filePath = path.join(__dirname, '..', 'public', 'assets', filename); // Adjust path as per your directory structure
    const data = await fs.readFile(filePath, 'utf-8');
    return data.split('\n');
}

// Function to check for sequential characters
function isSequential(password) {
    for (let i = 0; i < password.length - 3; i++) {
        const seq = password.substring(i, i + 3);
        if (/^[a-zA-Z]+$/.test(seq) || /^\d+$/.test(seq)) {
            return true;
        }
    }
    return false;
}

module.exports = {
    checkPasswordStrength: checkPasswordStrength
};