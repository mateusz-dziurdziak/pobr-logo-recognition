package pl.dziurdziak.pobrLogoRecognition.func.recognition;

import pl.dziurdziak.pobrLogoRecognition.model.classification.ClassifiedSegment;
import pl.dziurdziak.pobrLogoRecognition.model.recognition.Logo;

import java.util.List;

import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;

/**
 * @author Mateusz Dziurdziak
 */
public class AllClasifiedSegmentRecognizer implements Recognizer {

    @Override
    public List<Logo> recognize(List<ClassifiedSegment> classifiedSegments) {
        return classifiedSegments.stream()
                .map(classifiedSegment -> new Logo(singletonList(classifiedSegment)))
                .collect(toList());
    }

}
