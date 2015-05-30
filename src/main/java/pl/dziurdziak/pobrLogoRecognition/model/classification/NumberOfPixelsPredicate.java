package pl.dziurdziak.pobrLogoRecognition.model.classification;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Range;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import net.jcip.annotations.Immutable;
import pl.dziurdziak.pobrLogoRecognition.model.calculation.SegmentCalculations;

/**
 * @author Mateusz Dziurdziak
 */
@ToString
@EqualsAndHashCode
@Immutable
public class NumberOfPixelsPredicate implements Predicate {

    private final Range<Integer> range;

    @JsonCreator
    public NumberOfPixelsPredicate(@JsonProperty("min") int min, @JsonProperty("max") int max) {
        range = Range.closed(min, max);
    }

    @Override
    public boolean fulfil(SegmentCalculations segmentCalculations) {
        return range.contains(segmentCalculations.getSegment().getSize());
    }

}
