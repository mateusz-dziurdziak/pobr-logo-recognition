package pl.dziurdziak.pobrLogoRecognition.model.image;

import lombok.Data;
import net.jcip.annotations.Immutable;

/**
 * @author Mateusz Dziurdziak
 */
@Data
@Immutable
public class Pixel {

    public static final Pixel WHITE = new Pixel(255, 255, 255);
    public static final Pixel BLACK = new Pixel(0, 0, 0);

    private final int red;
    private final int green;
    private final int blue;
}
