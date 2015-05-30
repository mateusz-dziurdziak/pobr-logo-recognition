package pl.dziurdziak.pobrLogoRecognition.model.recognition;

import com.google.common.collect.ImmutableList;
import lombok.Data;
import net.jcip.annotations.Immutable;
import pl.dziurdziak.pobrLogoRecognition.model.classification.ClassifiedSegment;

import java.util.List;

/**
 * @author Mateusz Dziurdziak
 */
@Immutable
@Data
public class Logo {

    private final List<ClassifiedSegment> classifiedSegments;

    public Logo(List<ClassifiedSegment> classifiedSegments) {
        this.classifiedSegments = ImmutableList.copyOf(classifiedSegments);
    }
}
