package com.ey.backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration // Indica a Spring che questa è una classe di configurazione
public class CorsConfig implements WebMvcConfigurer {

    /**
     * Configura le regole CORS per l'applicazione.
     * In questo esempio, si permette a qualsiasi origine, metodo e header
     * di accedere a tutte le API sotto "/api/**".
     *
     * ATTENZIONE: Questa configurazione è ideale per l'ambiente di SVILUPPO.
     * In un ambiente di PRODUZIONE, dovresti limitare le origini (allowedOrigins)
     * solo ai domini noti del tuo frontend per motivi di sicurezza.
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("http://localhost:5173", "http://localhost:3000")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600);
    }
}
    