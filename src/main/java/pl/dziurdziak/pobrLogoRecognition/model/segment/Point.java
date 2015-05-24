package pl.dziurdziak.pobrLogoRecognition.model.segment;

import lombok.Data;
import net.jcip.annotations.Immutable;

/**
 * @author Mateusz Dziurdziak
 */
@Data
@Immutable
public class Point {

    private final int row;
    private final int column;
}
