package pl.dziurdziak.pobrLogoRecognition.model.image;

import lombok.ToString;
import net.jcip.annotations.Immutable;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Mateusz Dziurdziak
 */
@ToString
@Immutable
public class Image {

    /**
     * Content of the image. First level array contains rows, second level array contains pixels.
     */
    private final Pixel[][] pixels;

    public Image(@NotNull Pixel[][] pixels) {
        checkNotNull(pixels, "Pixels cannot be null");
        this.pixels = Arrays.copyOf(pixels, pixels.length);
    }

    public int rowCount() {
        return pixels.length;
    }

    public int columnCount() {
        return pixels.length > 0 ? pixels[0].length : 0;
    }

    public Pixel getPixel(int row, int column) {
        checkArgument(row >= 0 && row < rowCount(),
                "Row %s doesn't exist. Image row count: %s", row, rowCount());
        checkArgument(column >= 0 && column < columnCount(),
                "Column %s doesn't exist. Image columnCount: %s", column, columnCount());
        return pixels[row][column];
    }
}
