const crypto = require('crypto');

// Function to compute MD5 hash
function md5Hash(input) {
    return crypto.createHash('md5').update(input).digest('hex');
}

// Function to compute SHA1 hash
function sha1Hash(input) {
    return crypto.createHash('sha1').update(input).digest('hex');
}

function sha256Hash(input) {
    return crypto.createHash('sha256').update(input).digest('hex');
}

function othersHash(input) {
    return crypto.createHash('sha1').update(input).digest('hex');
}

// Function to compare hashes
function compareHash(input, hash, key) {
    switch (key) {
        case 1: {
            const generatedHash = md5Hash(input);
            return generatedHash === hash;
        }
            
        case 2:{
            const generatedHash = sha1Hash(input);
            return generatedHash === hash;
        }
        case 3:{
            const generatedHash = sha256Hash(input);
            return generatedHash === hash;
        }
        case 4:{
            const generatedHash = othersHash(input);
            return generatedHash === hash;
        }
        default:
            break;
    }
}

module.exports = {
    compareHash: compareHash
};
