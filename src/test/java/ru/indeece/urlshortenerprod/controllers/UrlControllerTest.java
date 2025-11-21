package ru.indeece.urlshortenerprod.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.indeece.urlshortenerprod.services.impl.UrlServiceImpl;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest(
        controllers = UrlController.class,
        excludeAutoConfiguration = {
                org.springdoc.core.configuration.SpringDocConfiguration.class,
                org.springdoc.webmvc.api.OpenApiWebMvcResource.class,
                org.springdoc.webmvc.ui.SwaggerConfig.class
        }
)
class UrlControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UrlServiceImpl urlService;

    @Test
    void generateSlug_shouldReturnShortUrl() throws Exception {
        when(urlService.generateShortUrl("https://google.com")).thenReturn("abc123");

        mockMvc.perform(post("/api/v1/generate-slug")
                        .param("url", "https://google.com"))
                .andExpect(status().isOk())
                .andExpect(content().string("abc123"));
    }

    @Test
    void redirectUrl_shouldReturn302() throws Exception {
        when(urlService.redirectToUrl("abc123")).thenReturn("https://google.com");

        mockMvc.perform(get("/api/v1/redirect-url")
                        .param("slug", "abc123"))
                .andExpect(status().isFound())
                .andExpect(header().string("Location", "https://google.com"));
    }
}
