package pl.dziurdziak.pobrLogoRecognition.model.recognition;

import pl.dziurdziak.pobrLogoRecognition.model.classification.ClassifiedSegment;

import java.util.List;

/**
 * @author Mateusz Dziurdziak
 */
public interface Recognizer {

    List<Logo> recognize(List<ClassifiedSegment> classifiedSegment);

}
