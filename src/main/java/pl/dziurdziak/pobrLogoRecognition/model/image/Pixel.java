package pl.dziurdziak.pobrLogoRecognition.model.image;

import lombok.Data;
import net.jcip.annotations.Immutable;

/**
 * @author Mateusz Dziurdziak
 */
@Data
@Immutable
public class Pixel {

    private final int red;
    private final int green;
    private final int blue;
}
