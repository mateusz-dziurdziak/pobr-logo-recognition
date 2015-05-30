package pl.dziurdziak.pobrLogoRecognition.model.classification;

import lombok.Data;
import net.jcip.annotations.Immutable;
import pl.dziurdziak.pobrLogoRecognition.model.calculation.SegmentCalculations;

/**
 * @author Mateusz Dziurdziak
 */
@Immutable
@Data
public class ClassifiedSegment {

    private final SegmentCalculations segmentCalculations;
    private final String classifiedAs;

    public ClassifiedSegment(SegmentCalculations segmentCalculations, String classifiedAs) {
        this.segmentCalculations = segmentCalculations;
        this.classifiedAs = classifiedAs;
    }
}
