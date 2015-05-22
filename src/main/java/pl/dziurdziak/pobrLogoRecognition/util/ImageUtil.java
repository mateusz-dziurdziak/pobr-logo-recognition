package pl.dziurdziak.pobrLogoRecognition.util;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.dziurdziak.pobrLogoRecognition.model.image.Image;
import pl.dziurdziak.pobrLogoRecognition.model.image.Pixel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.image.PixelGrabber;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

/**
 * @author Mateusz Dziurdziak
 */
public final class ImageUtil {

    private static final Logger LOG = LoggerFactory.getLogger(ImageUtil.class);

    private ImageUtil() {
    }

    public static Image readImageFromFile(@NotNull String filePath) {
        checkNotNull(filePath, "filePath cannot be null");
        File file = new File(filePath);
        checkArgument(file.exists(), "File %s doesn't exists", filePath);
        checkArgument(!file.isDirectory(), "File %s is a directory", filePath);

        try (FileInputStream fis = new FileInputStream(file)) {
            BufferedImage bufferedImage = ImageIO.read(fis);
            return convertBufferedImage(bufferedImage);
        } catch (IOException e) {
            LOG.error("Error while reading file {}", e, filePath);
            throw new IllegalStateException("IOException", e);
        }
    }

    public static Image convertBufferedImage(@NotNull BufferedImage bi) {
        checkNotNull(bi, "bufferedImage cannot be null");

        int[] values = grabPixels(bi);

        Pixel[][] pixels = new Pixel[bi.getHeight()][bi.getWidth()];
        for (int row = 0; row < bi.getHeight(); row++) {
            for (int col = 0; col < bi.getWidth(); col++) {
                int pixel = values[row * bi.getWidth() + col];
                pixels[row][row] = new Pixel((pixel >> 16) & 0xff, (pixel >> 8) & 0xff, (pixel) & 0xff);
            }
        }

        return new Image(pixels);
    }

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
}
