const express = require('express');
const path = require('path');
const crypto = require('crypto');
const fetch = (...args) => import('node-fetch').then(({default: fetch}) => fetch(...args));

const app = express();
const PORT = 3000;

// Middleware to parse URL-encoded bodies
app.use(express.urlencoded({ extended: true }));

// Serve static files from the 'public' directory
app.use(express.static(path.join(__dirname, 'public')));

// Endpoint to handle email breach check
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

// Endpoint to handle password breach check
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

// Catch-all route for serving index.html
app.get('*', (req, res) => {
    res.sendFile(path.join(__dirname, 'public', 'email-checker.html'));
});

// Start the server
app.listen(PORT, () => {
    console.log(`Server is running on http://localhost:${PORT}`);
});
