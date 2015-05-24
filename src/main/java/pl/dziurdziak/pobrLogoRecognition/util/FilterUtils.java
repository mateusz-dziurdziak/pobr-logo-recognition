package pl.dziurdziak.pobrLogoRecognition.util;

import com.google.common.annotations.VisibleForTesting;
import org.jetbrains.annotations.NotNull;
import pl.dziurdziak.pobrLogoRecognition.model.image.Image;
import pl.dziurdziak.pobrLogoRecognition.model.image.Pixel;

/**
 * @author Mateusz Dziurdziak
 */
public class FilterUtils {

    /**
     * Interface for filters using windows
     */
    public interface WindowProcessor {

        /**
         * Converts window of pixels to one pixel witch will be placed in output image
         *
         * @param window window
         * @return calculated pixel
         */
        @NotNull
        Pixel process(Pixel[][] window);
    }

    /**
     * Filter image window by window using {@link WindowProcessor} to calculate next pixels.
     *
     * @param image           image
     * @param windowSize      window size
     * @param windowProcessor window processor
     * @return filtered image
     */
    @NotNull
    public static Image filterUsingWindow(Image image, int windowSize, WindowProcessor windowProcessor) {
        int windowSide = (int) Math.sqrt(windowSize);

        Pixel[][] pixels = new Pixel[image.height()][image.width()];
        for (int row = 0; row < image.height(); row++) {
            for (int col = 0; col < image.width(); col++) {
                if (row < windowSide / 2 || row >= image.height() - windowSide / 2
                        || col < windowSide / 2 || col >= image.width() - windowSide / 2) {
                    pixels[row][col] = image.getPixel(row, col);
                } else {
                    Pixel[][] window = extractWindow(image, windowSide, row, col);
                    pixels[row][col] = windowProcessor.process(window);
                }
            }
        }
        return new Image(pixels);
    }

    /**
     * <p>Extract window of pixels (as multi-dimensional {@link Pixel} array) which center is at specified
     * position.</p>
     * <p>
     * <p>Method should be used only with valid coordinates (row/column)</p>
     *
     * @param image      image
     * @param windowSide length of window side
     * @param row        row of center (in image)
     * @param col        column of center (in image)
     * @return extracted window
     */
    @NotNull
    @VisibleForTesting
    static Pixel[][] extractWindow(Image image, int windowSide, int row, int col) {
        Pixel[][] window = new Pixel[windowSide][windowSide];
        for (int i = 0; i < windowSide; i++) {
            for (int j = 0; j < windowSide; j++) {
                window[i][j] = image.getPixel(row - windowSide / 2 + i, col - windowSide / 2 + j);
            }
        }
        return window;
    }
}
