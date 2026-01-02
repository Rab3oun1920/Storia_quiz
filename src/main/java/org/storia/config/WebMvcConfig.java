package org.storia.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuration Spring MVC pour les ressources statiques
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    /**
     * Configure les handlers pour les ressources statiques
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/")
                .setCachePeriod(0);
    }

    /**
     * Configure les contrôleurs de vue simples
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // Rediriger la racine vers index.html
        registry.addViewController("/").setViewName("forward:/index.html");
    }
}
