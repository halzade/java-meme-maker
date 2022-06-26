package meme;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static java.awt.RenderingHints.KEY_ANTIALIASING;
import static java.awt.RenderingHints.VALUE_ANTIALIAS_ON;

public class MemeMaker {

    private static final String PATH = "/home/lukas/Downloads/";
    private static final String NAME = "blackbird.jpg";
    private static final String TEXT_1 = "Java memes be like";
    private static final String TEXT_2 = "ahh, code.";

    /**
     * Font, size & color
     */
    private static final String FONT = "Calibri";
    private static final int FONT_SIZE_1 = 40;
    private static final int FONT_SIZE_2 = 42;
    private static final Color color = Color.decode("#C0C0C0");


    private static final Logger log = LogManager.getRootLogger();

    public static void main(String[] args) throws IOException {

        log.info("read input image");
        final File inputFile = new File(PATH + NAME);
        final BufferedImage inputImage = ImageIO.read(inputFile);

        log.info("creates output image");
        final int width = inputImage.getWidth();
        final double gold = 1 + Math.sqrt(5) / 2;
        final int block = (int) (inputImage.getHeight() / gold) / 2;
        final BufferedImage outputImage = new BufferedImage(width, inputImage.getHeight() + (2 * block), inputImage.getType());

        log.info("draw input image to output image");
        final Graphics2D g = outputImage.createGraphics();
        g.drawImage(inputImage, 0, block, width, inputImage.getHeight(), null);

        log.info("setup");
        final Font font1 = new Font(FONT, Font.PLAIN, FONT_SIZE_1);
        final Font font2 = new Font(FONT, Font.PLAIN, FONT_SIZE_2);
        final FontMetrics fontMetrics1 = g.getFontMetrics(font1);
        final FontMetrics fontMetrics2 = g.getFontMetrics(font2);

        log.info("calculate perfect proportions");
        final int left1 = (width - fontMetrics1.stringWidth(TEXT_1)) / 2;
        final int left2 = (width - fontMetrics2.stringWidth(TEXT_2)) / 2;
        final int top1 = (block / 2) - (fontMetrics1.getHeight() / 2)
                + fontMetrics1.getAscent();
        final int top2 = block + inputImage.getHeight() + ((block / 2) - (fontMetrics2.getHeight() / 2))
                + fontMetrics2.getAscent();

        log.info("draw top text");
        g.setRenderingHint(KEY_ANTIALIASING, VALUE_ANTIALIAS_ON);
        g.setColor(color);
        g.setFont(font1);
        g.drawString(TEXT_1, left1, top1);

        log.info("draw bottom text");
        g.setFont(font2);
        g.drawString(TEXT_2, left2, top2);
        g.dispose();

        log.info("extract extension for output file");
        String[] name_ar = NAME.split("\\.");
        final String newName = name_ar[0] + "-meme." + name_ar[1];

        log.info("write to output file");
        ImageIO.write(outputImage, name_ar[1], new File(PATH + newName));

        log.info("finished.");
    }
}
