# **ShadowHash - All in One Crypto Solution**
<br>

## **Abstract**
ShadowHash is a comprehensive cryptographic platform designed to enhance data security and integrity. Built using **Java for the backend** and **HTML/CSS for the frontend**, it is **hosted on GitHub and AWS Amplify**. The platform offers cryptographic tools such as **password security, encryption, decryption, hash generation, integrity verification, and threat detection**. It integrates **APIs like VirusTotal, HaveIBeenPwned, and Hackcheck Woventeams** for **breach detection** and **malware scanning**.
<br>

## Installation

Clone the repository and navigate to the project directory:

```bash
git clone https://github.com/AdityaBhatt3010/ShadowHash-All-in-One-Crypto-Solution.git
cd ShadowHash
```
<br>

## **Key Features**
1. **Email Breach Checker** – Checks if an email was part of a data breach using the Hackcheck Woventeams API.
2. **Password Breach Checker** – Uses HaveIBeenPwned API to verify if a password has been compromised.
3. **Password Generator** – Generates strong passwords, including user-defined secure ones.
4. **Encryption Algorithms** – Supports AES, 3DES, Blowfish, RSA, and a custom **ShadowHash special encryption algorithm**.
5. **Decryption Algorithms** – Supports decryption for the above encryption methods.
6. **Hash Creator** – Provides hashing algorithms (MD5, SHA-1, SHA-256) for data integrity.
7. **Hash Comparison Checker** – Validates data integrity by comparing hash values.
8. **Virus Detector** – Uses the VirusTotal API to scan files for potential malware.
<br>

## **Technology Stack**
- **Frontend:** HTML, CSS
- **Backend:** Initially Java, later transitioned to **JavaScript (Vanilla JS, Express, Node.js)**
- **Hosting:** AWS Amplify (integrated with GitHub for CI/CD)
- **Libraries:** `node-fetch`, `crypto`, `express-fileupload`, `path`
- **APIs Used:**
  - Hackcheck Woventeams API (Email Breach Detection)
  - HaveIBeenPwned API (Password Breach Checking)
  - VirusTotal API (File Malware Detection)
<br>

## **Methodology**
1. **Requirements Analysis** – Identified key security features needed.
2. **Design** – Created system architecture, UI, and API integrations.
3. **Development**
   - **Backend:** Secure HTTP requests, password hashing, encryption/decryption.
   - **Frontend:** User-friendly UI, responsive design.
4. **Testing** – Unit, integration, and security testing.
5. **Deployment** – Hosted on **AWS Amplify** with **monitoring via AWS CloudWatch & SNS**.
6. **Maintenance & Updates** – Continuous monitoring and security updates.
<br>

## **Results**
- Successfully implemented key **cryptographic** functionalities.
- **Seamless frontend-backend integration** ensures a smooth user experience.
- **Strong security protocols** like SHA-1 hashing for password breach checks.
- **Multi-layered encryption support** for varied user needs.
- **Real-time malware scanning** via VirusTotal API.
<br>

## **Conclusion**
ShadowHash is a **powerful, all-in-one cryptographic security platform** designed for **password protection, data encryption, integrity verification, and malware detection**. Its deployment on **AWS Amplify with security enhancements** ensures **scalability, reliability, and real-time protection**. The project continues to evolve with a focus on **advanced cybersecurity measures**.
