package pl.dziurdziak.pobrLogoRecognition.func.recognition;

import pl.dziurdziak.pobrLogoRecognition.model.classification.ClassifiedSegment;
import pl.dziurdziak.pobrLogoRecognition.model.recognition.Logo;

import java.util.List;

/**
 * @author Mateusz Dziurdziak
 */
public interface Recognizer {

    List<Logo> recognize(List<ClassifiedSegment> classifiedSegment);

}
