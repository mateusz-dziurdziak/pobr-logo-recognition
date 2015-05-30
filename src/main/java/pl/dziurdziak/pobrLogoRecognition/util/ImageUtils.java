package pl.dziurdziak.pobrLogoRecognition.util;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.dziurdziak.pobrLogoRecognition.model.calculation.SegmentCalculations;
import pl.dziurdziak.pobrLogoRecognition.model.image.Image;
import pl.dziurdziak.pobrLogoRecognition.model.image.Pixel;
import pl.dziurdziak.pobrLogoRecognition.model.recognition.Logo;

import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.image.PixelGrabber;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;
import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.util.stream.Collectors.toList;

/**
 * @author Mateusz Dziurdziak
 */
public final class ImageUtils {

    private static final Logger LOG = LoggerFactory.getLogger(ImageUtils.class);

    private ImageUtils() {
    }

    /**
     * Converts {@link BufferedImage} to {@link Image}
     *
     * @param bi {@link BufferedImage} to be converted
     * @return converted image
     */
    @NotNull
    public static Image convertBufferedImage(BufferedImage bi) {
        checkNotNull(bi, "bufferedImage cannot be null");

        int[] values = grabPixels(bi);

        Pixel[][] pixels = new Pixel[bi.getHeight()][bi.getWidth()];
        for (int row = 0; row < bi.getHeight(); row++) {
            for (int col = 0; col < bi.getWidth(); col++) {
                int pixel = values[row * bi.getWidth() + col];
                pixels[row][col] = new Pixel((pixel >> 16) & 0xff, (pixel >> 8) & 0xff, (pixel) & 0xff);
            }
        }

        return new Image(pixels);
    }

    /**
     * Converts {@link Image} to {@link BufferedImage}
     *
     * @param image image to convert
     * @return converted image
     */
    @NotNull
    public static BufferedImage convertImage(Image image) {
        checkNotNull(image);

        BufferedImage bi = new BufferedImage(image.width(), image.height(), BufferedImage.TYPE_INT_RGB);
        for (int row = 0; row < image.height(); row++) {
            for (int col = 0; col < image.width(); col++) {
                Pixel px = image.getPixel(row, col);
                int colour = (px.getRed() << 16) | (px.getGreen() << 8) | px.getBlue();
                bi.setRGB(col, row, colour);
            }
        }

        return bi;
    }

    /**
     * Grabs pixels from {@link BufferedImage} and return them as int array (each int value contains
     * alpha, red, green and blue)
     *
     * @param bi image
     * @return values of pixels
     */
    private static int[] grabPixels(@NotNull BufferedImage bi) {
        int[] values = new int[bi.getWidth() * bi.getHeight()];
        PixelGrabber pixelGrabber = new PixelGrabber(bi, 0, 0, bi.getWidth(), bi.getHeight(), values, 0, bi.getWidth());
        try {
            pixelGrabber.grabPixels();
        } catch (InterruptedException e) {
            // should not happen
            LOG.error("", e);
            throw new RuntimeException();
        }

        checkState((pixelGrabber.getStatus() & ImageObserver.ABORT) == 0, "Image fetch aborted or errored");
        return values;
    }

    public static Image drawRectanglesOnLogos(Image image, List<Logo> logos) {
        if (logos.isEmpty()) {
            return image;
        }

        Pixel[][] pixels = image.getPixels();
        for (Logo logo : logos) {
            drawRectangle(pixels, logo);
        }

        return new Image(pixels);
    }

    private static void drawRectangle(Pixel[][] pixels, Logo logo) {
        List<SegmentCalculations> segments = logo.getClassifiedSegments().stream()
                .map(classifiedSegment -> classifiedSegment.getSegmentCalculations())
                .collect(toList());
        int minRow = pixels.length;
        int maxRow = 0;
        int minCol = pixels[0].length;
        int maxCol = 0;
        for (SegmentCalculations segment : segments) {
            minRow = min(minRow, segment.getMinRow());
            maxRow = max(maxRow, segment.getMaxRow());
            minCol = min(minCol, segment.getMinCol());
            maxCol = max(maxCol, segment.getMaxCol());
        }

        Pixel red = new Pixel(255, 0, 0);
        for (int row = minRow; row <= maxRow; row++) {
            for (int col = minCol; col <= maxCol; col++) {
                if (row == minRow || row == maxRow || col == minCol || col == maxCol) {
                    pixels[row][col] = red;
                }
            }
        }
    }
}
