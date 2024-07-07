package dev.ccosta.hwa_stats_analyzer;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/ocr")
public class OcrController {

    @PostMapping("/extract")
    public ResponseEntity<Map<String, String>> extractText(@RequestParam("image") MultipartFile file) {
        // Áreas específicas para realizar a OCR (em pixels: x, y, largura, altura)
        Rectangle[] areas = {
            new Rectangle(50, 50, 200, 100),
            new Rectangle(300, 200, 150, 100)
        };

        Map<String, String> resultMap = new HashMap<>();

        try {
            // Carrega a imagem
            BufferedImage image = ImageIO.read(file.getInputStream());

            // Instancia o Tesseract
            ITesseract tesseract = new Tesseract();
            // Define o idioma, se necessário
            tesseract.setLanguage("por");  // Para português

            for (int i = 0; i < areas.length; i++) {
                Rectangle area = areas[i];
                // Recorta a área da imagem
                BufferedImage subImage = image.getSubimage(area.x, area.y, area.width, area.height);

                // Realiza a OCR na área recortada
                String result = tesseract.doOCR(subImage);

                // Adiciona o texto extraído ao mapa de resultados
                resultMap.put("area" + (i + 1), result);
            }
        } catch (IOException | TesseractException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }

        return ResponseEntity.ok(resultMap);
    }
}
