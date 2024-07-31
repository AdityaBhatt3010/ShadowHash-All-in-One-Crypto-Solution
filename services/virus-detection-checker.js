const fs = require('fs');
const path = require('path');
const axios = require('axios');
const FormData = require('form-data');

const API_KEY = '4e4384731df1a03a46d83945ae7454fec28d5f5d4627d510461c264e1d24b5c9';
const VIRUSTOTAL_API_URL = 'https://www.virustotal.com/vtapi/v2/file/scan';

async function scanFile(filePath) {
    try {
        const fileContent = fs.readFileSync(filePath);
        const form = new FormData();
        form.append('apikey', API_KEY);
        form.append('file', fs.createReadStream(filePath));

        const response = await axios.post(VIRUSTOTAL_API_URL, form, {
            headers: form.getHeaders()
        });

        const responseBody = response.data;
        console.log(responseBody);

        if (responseBody.verbose_msg && responseBody.verbose_msg.includes('virus detected')) {
            return 'File Contains Virus';
        } else {
            return 'No Virus Found';
        }
    } catch (error) {
        return 'Error: Virus detection failed.';
    }
}

module.exports = {
    scanFile
};
