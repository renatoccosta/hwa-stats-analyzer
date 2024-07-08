package dev.ccosta.hwastatsanalyzer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.util.Map;

@RestController
@RequestMapping("/ocr")
public class OCRController {

    @Autowired
    private OCRService ocrService;

    @PostMapping("/extract")
    public ResponseEntity<Map<String, String>> extractText(@RequestParam("image") MultipartFile file) {
        // Áreas específicas para realizar a OCR (em pixels: x, y, largura, altura)
        Rectangle[] areas = {
            new Rectangle(50, 50, 200, 100),
            new Rectangle(300, 200, 150, 100)
        };

        Map<String, String> resultMap;
        try {
            resultMap = ocrService.extractTextFromImage(file.getInputStream(), areas);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }

        return ResponseEntity.ok(resultMap);
    }
}
