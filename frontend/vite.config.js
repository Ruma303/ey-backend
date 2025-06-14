import { defineConfig } from 'vite';
import react from '@vitejs/plugin-react';

export default defineConfig({
  plugins: [react()],
  server: {
    port: 3000,
    proxy: {
      // Tutte le richieste che iniziano con /api saranno reindirizzate a http://localhost:8080
      '/api': {
        target: 'http://localhost:8080', // URL backend Spring Boot
        changeOrigin: true, // Necessario per host basati su nomi
        secure: false,
      },
    },
  },
});
