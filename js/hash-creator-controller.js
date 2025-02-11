const crypto = require('crypto');

function md5Hash(input) {
    try {
        const hash = crypto.createHash('md5').update(input).digest('hex');
        return hash;
    } catch (error) {
        console.error('Error in MD5 hashing:', error);
        return '';
    }
}

function sha1Hash(input) {
    try {
        const hash = crypto.createHash('sha1').update(input).digest('hex');
        return hash;
    } catch (error) {
        console.error('Error in SHA1 hashing:', error);
        return '';
    }
}

function sha256Hash(input) {
    try {
        const hash = crypto.createHash('sha256').update(input).digest('hex');
        return hash;
    } catch (error) {
        console.error('Error in SHA256 hashing:', error);
        return '';
    }
}

function others(input, algo) {
    try {
        const hash = crypto.createHash(algo).update(input).digest('hex');
        return hash;
    } catch (error) {
        alert('The algorithm entered is incorrect.');
        const message = "Please check the algorithm and try again";
        return message;
    }
}

module.exports = { md5Hash, sha1Hash, sha256Hash, others };
