package pl.dziurdziak.pobrLogoRecognition.filter;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.ToString;
import net.jcip.annotations.Immutable;

/**
 * @author Mateusz Dziurdziak
 */
@ToString
@Immutable
public class MinFilter extends RankFilter {

    @JsonCreator
    public MinFilter(@JsonProperty("windowSize") int windowSize) {
        super(windowSize, 0);
    }
}
