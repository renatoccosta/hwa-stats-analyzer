package dev.ccosta.hwastatsanalyzer;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Service
public class OCRService {

    public Map<String, String> extractTextFromImage(InputStream imageInputStream, Rectangle[] areas)
            throws IOException, TesseractException {
        Map<String, String> resultMap = new HashMap<>();

        // Carrega a imagem
        BufferedImage image = ImageIO.read(imageInputStream);

        // Instancia o Tesseract
        ITesseract tesseract = new Tesseract();
        // Define o idioma, se necessário
        tesseract.setLanguage("por"); // Para português

        for (int i = 0; i < areas.length; i++) {
            Rectangle area = areas[i];
            // Recorta a área da imagem
            BufferedImage subImage = image.getSubimage(area.x, area.y, area.width, area.height);

            // Realiza a OCR na área recortada
            String result = tesseract.doOCR(subImage);

            // Adiciona o texto extraído ao mapa de resultados
            resultMap.put("area" + (i + 1), result);
        }

        return resultMap;
    }
}
