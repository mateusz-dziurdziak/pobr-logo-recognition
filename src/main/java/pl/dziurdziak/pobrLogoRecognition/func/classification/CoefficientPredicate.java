package pl.dziurdziak.pobrLogoRecognition.func.classification;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Range;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import net.jcip.annotations.Immutable;
import pl.dziurdziak.pobrLogoRecognition.model.calculation.Coefficient;
import pl.dziurdziak.pobrLogoRecognition.model.calculation.SegmentCalculations;

/**
 * @author Mateusz Dziurdziak
 */
@ToString
@EqualsAndHashCode
@Immutable
public class CoefficientPredicate implements Predicate {

    private final Coefficient coefficient;
    private final Range<Double> range;

    @JsonCreator
    public CoefficientPredicate(@JsonProperty("coefficient") Coefficient coefficient,
                                @JsonProperty("min") double min,
                                @JsonProperty("max") double max) {
        this.coefficient = coefficient;
        range = Range.closed(min, max);
    }

    @Override
    public boolean fulfil(SegmentCalculations segmentCalculations) {
        return range.contains(segmentCalculations.get(coefficient));
    }
}
