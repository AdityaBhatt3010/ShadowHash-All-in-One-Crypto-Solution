const express = require('express');
const fileUpload = require('express-fileupload');
const bodyParser = require('body-parser');
const crypto = require('crypto');
const path = require('path');
const cors = require('cors');


const { checkPasswordStrength } = require('./password-strength-detector-controller');
const { generatePassword } = require('./random-generator-controller');
const { scanFile } = require('./virus-detection-checker');
const { encrypt_AES, encrypt_3DES, encrypt_Blowfish } = require('./encryption-controller');
const { compareHash } = require('./hash-comparison-controller');
const { decrypt_AES, decrypt_3DES, decrypt_Blowfish } = require('./decryption-controller');


const app = express();
const port = process.env.PORT || 8000;


app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());
app.use(fileUpload());
app.use(express.static(path.join(__dirname, '..', 'public')));
app.use(cors({
    origin: 'https://main.dw3q3rlukqs3q.amplifyapp.com/', // Replace with your front-end URL
    methods: ['GET', 'POST'],
    allowedHeaders: ['Content-Type', 'Authorization']
  }));



// Example endpoint to handle MD5 hashing
app.post('/hash/md5', async (req, res) => {
    const input = req.body.input;
    const hashedString = crypto.createHash('md5').update(input).digest('hex');
    res.send(`MD5 Hash: ${hashedString}`);
});

// Example endpoint to handle SHA1 hashing
app.post('/hash/sha1', async (req, res) => {
    const input = req.body.input;
    const hashedString = crypto.createHash('sha1').update(input).digest('hex');
    res.send(`SHA1 Hash: ${hashedString}`);
});

// Example endpoint to handle SHA256 hashing
app.post('/hash/sha256', async (req, res) => {
    const input = req.body.input;
    const hashedString = crypto.createHash('sha256').update(input).digest('hex');
    res.send(`SHA256 Hash: ${hashedString}`);
});

// Example endpoint to handle Others hashing
app.post('/hash/others', async (req, res) => {
    const input = req.body.input;
    const algo = req.body.algo;
    if(algo.length == 0){
        res.send('Please enter proper algorithm name');
    }
    else if(input.length == 0){
        res.send('Please enter proper message');
    }
    else { 
        try {
            const hashedString = crypto.createHash(algo).update(input).digest('hex');
            res.send(`Created Hash: ${hashedString}`);
        } catch (error) {
            const message = "Please check the algorithm and try again";
            res.send(`${message}`);
        }
    }
});

// Endpoint to handle password strength detection
app.post('/password/strength', async (req, res) => {
    const password = req.body.password;

    try {
        const strengthResult = await checkPasswordStrength(password);
        res.json(strengthResult); // Send JSON response with password rating and message
    } catch (error) {
        res.status(500).json({ error: error.message }); // Handle errors appropriately
    }
});

// Endpoint to generate a password
app.get('/generate-password', (req, res) => {
    const password = generatePassword();
    res.send(`Generated Password: ${password}`);
});

// Endpoint for comparing MD5 hash
app.post('/compare-hash/md5', (req, res) => {
    const { text, hash } = req.body;

    try {
        const isMatch = compareHash(text, hash, 1);

        if (isMatch) {
            res.send('Data is Correct');
        } else {
            res.send('Oops! Data has been tampered with');
        }
    } catch (error) {
        console.error('Error comparing hashes:', error);
        res.status(500).send('Internal Server Error');
    }
});

// Endpoint for comparing SHA1 hash
app.post('/compare-hash/sha1', (req, res) => {
    const { text, hash } = req.body;

    try {
        const isMatch = compareHash(text, hash, 2);

        if (isMatch) {
            res.send('Data is Correct');
        } else {
            res.send('Oops! Data has been tampered with');
        }
    } catch (error) {
        console.error('Error comparing hashes:', error);
        res.status(500).send('Internal Server Error');
    }
});

// Endpoint for comparing SHA256 hash
app.post('/compare-hash/sha256', (req, res) => {
    const { text, hash } = req.body;

    try {
        const isMatch = compareHash(text, hash, 3);

        if (isMatch) {
            res.send('Data is Correct');
        } else {
            res.send('Oops! Data has been tampered with');
        }
    } catch (error) {
        console.error('Error comparing hashes:', error);
        res.status(500).send('Internal Server Error');
    }
});

// Endpoint for comparing others hash
app.post('/compare-hash/others', (req, res) => {
    const { text, hash } = req.body;

    try {
        const isMatch = compareHash(text, hash, 4);

        if (isMatch) {
            res.send('Data is Correct');
        } else {
            res.send('Oops! Data has been tampered with');
        }
    } catch (error) {
        console.error('Error comparing hashes:', error);
        res.status(500).send('Internal Server Error');
    }
});


// Example endpoint to handle email breach check
app.post('/email/breach', async (req, res) => {
    const email = req.body.email;
    const apiUrl = `https://hackcheck.woventeams.com/api/v4/breachedaccount/${email}`;

    try {
        const response = await fetch(apiUrl);
        if (response.status === 200) {
            res.send("Your Email was found in a Data Breach");
        } else if (response.status === 404) {
            res.send("You are Safe");
        } else {
            res.send(`Error: HTTP status code ${response.status}`);
        }
    } catch (error) {
        res.send(`Error: ${error.message}`);
    }
});

// Example endpoint to handle password breach check
app.post('/password/breach', async (req, res) => {
    const password = req.body.password;
    const sha1Password = crypto.createHash('sha1').update(password).digest('hex').toUpperCase();
    const first5Chars = sha1Password.substring(0, 5);
    const tail = sha1Password.substring(5);
    const apiUrl = `https://api.pwnedpasswords.com/range/${first5Chars}`;

    try {
        const response = await fetch(apiUrl);
        const responseBody = await response.text();
        const lines = responseBody.split('\r\n');
        let found = false;
        for (const line of lines) {
            const parts = line.split(':');
            if (parts.length === 2 && parts[0] === tail) {
                res.send(`Your password was found ${parts[1]} times`);
                found = true;
                break;
            }
        }
        if (!found) {
            res.send("Your password was not found");
        }
    } catch (error) {
        res.send(`Error: ${error.message}`);
    }
});


// Endpoint for file upload and scanning
app.post('/upload', async (req, res) => {
    if (!req.files || Object.keys(req.files).length === 0) {
        return res.status(400).send('No files were uploaded.');
    }

    let uploadedFile = req.files.file;
    const uploadPath = path.join(__dirname, '..', 'uploads', uploadedFile.name);

    // Move the file to the "uploads" directory
    uploadedFile.mv(uploadPath, async function (err) {
        if (err) {
            return res.status(500).send(err);
        }

        // Scan the file
        const scanResult = await scanFile(uploadPath);

        // Respond with the scan result
        res.send(scanResult);
    });
});


// Endpoint for encrypting a string using AES
app.post('/encrypt_AES', (req, res) => {
    const { text, key } = req.body;

    if (!text || !key) {
        return res.status(400).send('Text and key are required for encryption.');
    }

    const encryptedText = encrypt_AES(text, key);
    res.send({ encryptedText });
});

// Endpoint for encrypting a string using 3DES
app.post('/encrypt_3DES', (req, res) => {
    const { text, key } = req.body;

    if (!text || !key) {
        return res.status(400).send('Text and key are required for encryption.');
    }

    const encryptedText = encrypt_3DES(text, key);
    res.send({ encryptedText });
});

// Endpoint for encrypting a string using Blowfish
app.post('/encrypt_Blowfish', (req, res) => {
    const { text, key } = req.body;

    if (!text || !key) {
        return res.status(400).send('Text and key are required for encryption.');
    }

    const encryptedText = encrypt_Blowfish(text, key);
    res.send({ encryptedText });
});


// Endpoint to handle AES decryption
app.post('/decrypt_AES', (req, res) => {
    const { encryptedText, key } = req.body;

    try {
        const decryptedText = decrypt_AES(encryptedText, key);
        res.send(decryptedText);
    } catch (error) {
        console.error('Error decrypting text:', error);
        res.status(500).send('Internal Server Error');
    }
});

// Endpoint to handle 3DES decryption
app.post('/decrypt_3DES', (req, res) => {
    const { encryptedText, key } = req.body;

    try {
        const decryptedText = decrypt_3DES(encryptedText, key);
        res.send(decryptedText);
    } catch (error) {
        console.error('Error decrypting text:', error);
        res.status(500).send('Internal Server Error');
    }
});

// Endpoint to handle AES decryption
app.post('/decrypt_Blowfish', (req, res) => {
    const { encryptedText, key } = req.body;

    try {
        const decryptedText = decrypt_Blowfish(encryptedText, key);
        res.send(decryptedText);
    } catch (error) {
        console.error('Error decrypting text:', error);
        res.status(500).send('Internal Server Error');
    }
});

app.get('/', (req, res) => {
    res.sendFile(path.join(__dirname,'..', 'index.html'));
});

app.listen(port, () => {
    console.log(`Server is running properly`);
  });
