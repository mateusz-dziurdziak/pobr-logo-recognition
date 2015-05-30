package pl.dziurdziak.pobrLogoRecognition.model.image;

import lombok.ToString;
import net.jcip.annotations.Immutable;
import org.jetbrains.annotations.NotNull;
import pl.dziurdziak.pobrLogoRecognition.util.CommonUtils;

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
        this.pixels = CommonUtils.copy(pixels);
    }

    public int height() {
        return pixels.length;
    }

    public int width() {
        return pixels.length > 0 ? pixels[0].length : 0;
    }

    public Pixel getPixel(int row, int column) {
        checkArgument(row >= 0 && row < height(),
                "Row %s doesn't exist. Image height: %s", row, height());
        checkArgument(column >= 0 && column < width(),
                "Column %s doesn't exist. Image width: %s", column, width());
        return pixels[row][column];
    }

    public Pixel[][] getPixels() {
        return CommonUtils.copy(pixels);
    }
}
