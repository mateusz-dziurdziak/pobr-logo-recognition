package pl.dziurdziak.pobrLogoRecognition.model.recognition;

import com.google.common.collect.ImmutableList;
import pl.dziurdziak.pobrLogoRecognition.model.classification.ClassifiedSegment;
import pl.dziurdziak.pobrLogoRecognition.model.segment.Point;
import pl.dziurdziak.pobrLogoRecognition.util.CommonUtils;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static java.util.stream.Collectors.toList;

/**
 * @author Mateusz Dziurdziak
 */
public class RaiffeisenLogoRecognizer implements Recognizer {

    @Override
    public List<Logo> recognize(List<ClassifiedSegment> classifiedSegments) {
        List<ClassifiedSegment> outSegments = classifiedSegments.stream()
                .filter(classifiedSegment -> "out".equals(classifiedSegment.getClassifiedAs()))
                .collect(toList());
        List<ClassifiedSegment> innerSegments = classifiedSegments.stream()
                .filter(classifiedSegment -> "inner".equals(classifiedSegment.getClassifiedAs()))
                .collect(toList());

        List<Logo> logos = newArrayList();
        for (ClassifiedSegment outSegment : outSegments) {
            Point outCenter = new Point(outSegment.getSegmentCalculations().getGeomCenterRow(),
                    outSegment.getSegmentCalculations().getGeomCenterCol());
            for (ClassifiedSegment innerSegment : innerSegments) {
                Point inCenter = new Point(innerSegment.getSegmentCalculations().getGeomCenterRow(),
                        innerSegment.getSegmentCalculations().getGeomCenterCol());
                if (CommonUtils.distance(outCenter, inCenter) < 40) {
                    logos.add(new Logo(ImmutableList.of(outSegment, innerSegment)));
                }
            }
        }

        return logos;
    }
}
