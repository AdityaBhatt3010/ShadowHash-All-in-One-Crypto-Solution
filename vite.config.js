import { defineConfig } from 'vite';
import { resolve } from 'path';

export default defineConfig({
  root: 'src', // Set the root directory to /src
  build: {
    outDir: '../dist', // Output directory for the build (relative to /src)
    emptyOutDir: true, // Clear the output directory before each build
    rollupOptions: {
      input: {
        main: resolve(__dirname, 'src/index.html'),
        about: resolve(__dirname, 'src/pages/about-us.html'),
        terms: resolve(__dirname, 'src/pages/terms-of-use.html'),
        email: resolve(__dirname, 'src/pages/email-checker.html'),
        psd: resolve(__dirname, 'src/pages/password-strength-checker.html'),
        generator: resolve(__dirname, 'src/pages/random-generator.html'),
        encryption: resolve(__dirname, 'src/pages/3DES_encryption.html'),
        decryption: resolve(__dirname, 'src/pages/3DES_decryption.html'),
        hashCreator: resolve(__dirname, 'src/pages/MD5-creator.html'),
        comparisionChecker: resolve(__dirname, 'src/pages/MD5_comparision.html'),
        file: resolve(__dirname, 'src/pages/file-virus-checker.html'),
        // Add more pages here as needed
      },
    },
  },
  server: {
    open: true, // Automatically open the app in the browser on server start
  },
  publicDir: '../public', // Specify the directory for static assets
  resolve: {
    alias: {
      '@': resolve(__dirname, 'src'), // Set up an alias for the /src directory
    },
  },
});
