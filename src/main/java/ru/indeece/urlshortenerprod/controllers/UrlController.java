package ru.indeece.urlshortenerprod.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.indeece.urlshortenerprod.exceptions.MissingUrlException;
import ru.indeece.urlshortenerprod.services.impl.UrlServiceImpl;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "URL Shortener", description = "API для создания коротких ссылок")
public class UrlController {

    private final UrlServiceImpl urlService;

    public UrlController(UrlServiceImpl urlService) {
        this.urlService = urlService;
    }

    @PostMapping("/generate-slug")
    @Operation(summary = "Генерация короткой ссылки",
            description = "Создаёт короткий код для длинного URL")
    public ResponseEntity<String> generateSlug(
            @Parameter(description = "Длинный URL для сокращения", example = "https://google.com")
            @RequestParam String url
    ) {
        return ResponseEntity.ok(urlService.generateShortUrl(url));
    }

    @GetMapping("/redirect-url")
    @Operation(summary = "Редирект по короткому URL", description = "Перенаправляет на исходный длинный URL")
    public ResponseEntity<Void> redirectUrl(
            @Parameter(description = "Короткий код", example = "aB3dE1")
            @RequestParam String slug
    ) {
        String longUrl = urlService.redirectToUrl(slug);

        if (longUrl == null) {
            throw new MissingUrlException("Could not redirect to url: " + slug);
        }

        return ResponseEntity.status(302)
                .header("Location", longUrl)
                .build();

    }
}
