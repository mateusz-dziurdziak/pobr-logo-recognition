package pl.dziurdziak.pobrLogoRecognition.func.classification;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import pl.dziurdziak.pobrLogoRecognition.model.calculation.SegmentCalculations;

/**
 * @author Mateusz Dziurdziak
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({@JsonSubTypes.Type(value = MomentInvariantPredicate.class, name = "MomentInvariant"),
        @JsonSubTypes.Type(value = CoefficientPredicate.class, name = "Coefficient")})
public interface Predicate {

    boolean fulfil(SegmentCalculations segmentCalculations);

}
