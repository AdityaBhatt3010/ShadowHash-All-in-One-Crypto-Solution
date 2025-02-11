const crypto = require('crypto');
const CryptoJS = require('crypto-js');

// Function to set the AES key
function setKey_AES(keyString) {
    let key = Buffer.from(keyString, 'utf-8');
    const sha = crypto.createHash('sha256');
    key = sha.update(key).digest();
    key = key.slice(0, 16);
    return key;
}

// Function to set the key for 3DES
function setKey_3DES(keyString) {
    let key = Buffer.from(keyString, 'utf-8');
    const sha = crypto.createHash('sha256');
    key = sha.update(key).digest();
    key = key.slice(0, 24); // 3DES key should be 24 bytes
    return key;
}



// Function to decrypt AES encrypted text
function decrypt_AES(encryptedText, decryptionKey) {
    try {
        const key = setKey_AES(decryptionKey);
        const decipher = crypto.createDecipheriv('aes-128-ecb', key, '');
        decipher.setAutoPadding(true);
        let decrypted = decipher.update(encryptedText, 'base64', 'utf8');
        decrypted += decipher.final('utf8');
        return decrypted;
    } catch (error) {
        console.error('Decryption Error AES:', error);
        return null;
    }
}

// Function to decrypt using 3DES
function decrypt_3DES(encryptedText, decryptionKey) {
    try {
        const key = setKey_3DES(decryptionKey);
        const decipher = crypto.createDecipheriv('des-ede3', key, '');
        decipher.setAutoPadding(true);
        let decrypted = decipher.update(encryptedText, 'base64', 'utf8');
        decrypted += decipher.final('utf8');
        return decrypted;
    } catch (error) {
        console.error('Decryption Error 3DES:', error);
        return null;
    }
}

// Function to decrypt using Blowfish
function decrypt_Blowfish(encryptedText, encryptionKey) {
    try {
        const bytes = CryptoJS.Blowfish.decrypt(encryptedText, CryptoJS.enc.Utf8.parse(encryptionKey), {
            mode: CryptoJS.mode.ECB,
            padding: CryptoJS.pad.Pkcs7
        });
        const decrypted = bytes.toString(CryptoJS.enc.Utf8);
        return decrypted;
    } catch (error) {
        console.error('Decryption Error Blowfish:', error);
        return null;
    }
}

module.exports = {
    decrypt_AES: decrypt_AES,
    decrypt_3DES: decrypt_3DES,
    decrypt_Blowfish: decrypt_Blowfish
};
