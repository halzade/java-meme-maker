package meme;

import meme.annotation.EditMe;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static java.awt.RenderingHints.KEY_ANTIALIASING;
import static java.awt.RenderingHints.VALUE_ANTIALIAS_ON;
import static java.util.Objects.requireNonNull;

public class MemeMaker {

    /**
     * Meme image
     */
    @EditMe
    private static final String NAME = "blackbird.jpg";

    /**
     * Save generated meme image here
     * User home by default
     */
    @EditMe
    private static final String DESTINATION = System.getProperty("user.home") + "/";

    /**
     * Meme texts
     */
    @EditMe
    private static final String TEXT_1 = "Though I pass through the valley of the shadow of death, I shall fear no evil";
    @EditMe
    private static final String TEXT_2 = "For I am at 80 000 feet and climbing";

    /**
     * Font, size & color
     */
    private static final String FONT = "Calibri";
    private static final int FONT_SIZE_1 = 40;
    private static final int FONT_SIZE_2 = 42;
    private static final Color color = Color.decode("#C0C0C0");


    private static final Logger log = LogManager.getLogger(MemeMaker.class);

    public static void main(String[] args) throws IOException {

        log.info("Read input image");
        final BufferedImage inputImage = ImageIO.read(requireNonNull(MemeMaker.class.getResourceAsStream("/" + NAME)));

        log.info("Calculate perfect proportions");
        final double gold = 1 + Math.sqrt(5) / 2;
        final int block = (int) (inputImage.getHeight() / gold) / 2;

        log.info("Creates output image");
        final int width = inputImage.getWidth();
        final BufferedImage outputImage = new BufferedImage(width, inputImage.getHeight() + (2 * block), inputImage.getType());

        log.info("Draw input image to output image");
        final Graphics2D g = outputImage.createGraphics();
        g.drawImage(inputImage, 0, block, width, inputImage.getHeight(), null);

        log.info("Setup fonts");
        final Font font1 = new Font(FONT, Font.PLAIN, FONT_SIZE_1);
        final Font font2 = new Font(FONT, Font.PLAIN, FONT_SIZE_2);
        final FontMetrics fontMetrics1 = g.getFontMetrics(font1);
        final FontMetrics fontMetrics2 = g.getFontMetrics(font2);

        log.info("Calculate text positions");
        final int left1 = (width - fontMetrics1.stringWidth(TEXT_1)) / 2;
        final int left2 = (width - fontMetrics2.stringWidth(TEXT_2)) / 2;
        final int top1 = (block / 2) - (fontMetrics1.getHeight() / 2)
                + fontMetrics1.getAscent();
        final int top2 = block + inputImage.getHeight() + ((block / 2) - (fontMetrics2.getHeight() / 2))
                + fontMetrics2.getAscent();

        log.info("Draw TOP text");
        g.setRenderingHint(KEY_ANTIALIASING, VALUE_ANTIALIAS_ON);
        g.setColor(color);
        g.setFont(font1);
        g.drawString(TEXT_1, left1, top1);

        log.info("Draw BOTTOM text");
        g.setFont(font2);
        g.drawString(TEXT_2, left2, top2);
        g.dispose();

        log.info("Extract extension for output file");
        String[] name_ar = NAME.split("\\.");
        final String newName = name_ar[0] + "-meme." + name_ar[1];

        log.info("Write to output file");
        final File file = new File(DESTINATION + File.separator + newName);
        log.info(file.getAbsolutePath());
        ImageIO.write(outputImage, name_ar[1], file);

        log.info("Finished.");
    }
}
