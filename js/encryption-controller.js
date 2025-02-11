const crypto = require('crypto');
const Blowfish = require('blowfish');

// Function to set the key for AES encryption
function setKey_AES(myKey) {
    const key = Buffer.from(myKey, 'utf-8');
    const sha256 = crypto.createHash('sha256');
    sha256.update(key);
    return sha256.digest().slice(0, 16);
}

function setKey_3DES(myKey) {
    const key = Buffer.from(myKey, 'utf-8');
    const sha256 = crypto.createHash('sha256');
    sha256.update(key);
    return sha256.digest().slice(0, 24);
}


// Function to encrypt a string using AES encryption
function encrypt_AES(str, encryptionKey) {
    try {
        const key = setKey_AES(encryptionKey);
        const cipher = crypto.createCipheriv('aes-128-ecb', key, null);
        let encrypted = cipher.update(str, 'utf-8', 'base64');
        encrypted += cipher.final('base64');
        return encrypted;
    } catch (error) {
        console.error('Encryption Error AES:', error);
        return null;
    }
}

// Function to encrypt using 3DES
function encrypt_3DES(str, encryptionKey) {
    try {
        const key = setKey_3DES(encryptionKey);
        const cipher = crypto.createCipheriv('des-ede3', key, null);
        let encrypted = cipher.update(str, 'utf-8', 'base64');
        encrypted += cipher.final('base64');
        return encrypted;
    } catch (error) {
        console.error('Encryption Error 3DES:', error);
        return null;
    }
}

// Function to encrypt using Blowfish
function encrypt_Blowfish(str, encryptionKey) {
    try {
        const bf = new Blowfish(encryptionKey);
        const encrypted = bf.encode(str);
        return Buffer.from(encrypted, 'binary').toString('base64');
    } catch (error) {
        console.error('Encryption Error Blowfish:', error);
        return null;
    }
}

module.exports = {
    encrypt_AES,
    encrypt_3DES,
    encrypt_Blowfish
};
