package pl.dziurdziak.pobrLogoRecognition.model.calculation;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import lombok.Getter;
import net.jcip.annotations.Immutable;
import pl.dziurdziak.pobrLogoRecognition.model.segment.Direction;
import pl.dziurdziak.pobrLogoRecognition.model.segment.Point;
import pl.dziurdziak.pobrLogoRecognition.model.segment.Segment;

import java.util.LinkedHashMap;
import java.util.Map;

import static com.google.common.base.Preconditions.checkArgument;
import static java.lang.Math.PI;
import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
import static pl.dziurdziak.pobrLogoRecognition.model.calculation.CentralMoment.M_00;
import static pl.dziurdziak.pobrLogoRecognition.model.calculation.CentralMoment.M_01;
import static pl.dziurdziak.pobrLogoRecognition.model.calculation.CentralMoment.M_02;
import static pl.dziurdziak.pobrLogoRecognition.model.calculation.CentralMoment.M_03;
import static pl.dziurdziak.pobrLogoRecognition.model.calculation.CentralMoment.M_10;
import static pl.dziurdziak.pobrLogoRecognition.model.calculation.CentralMoment.M_11;
import static pl.dziurdziak.pobrLogoRecognition.model.calculation.CentralMoment.M_12;
import static pl.dziurdziak.pobrLogoRecognition.model.calculation.CentralMoment.M_20;
import static pl.dziurdziak.pobrLogoRecognition.model.calculation.CentralMoment.M_21;
import static pl.dziurdziak.pobrLogoRecognition.model.calculation.CentralMoment.M_30;
import static pl.dziurdziak.pobrLogoRecognition.model.calculation.Coefficient.W_1;
import static pl.dziurdziak.pobrLogoRecognition.model.calculation.Coefficient.W_2;
import static pl.dziurdziak.pobrLogoRecognition.model.calculation.Coefficient.W_3;
import static pl.dziurdziak.pobrLogoRecognition.model.calculation.Coefficient.W_7;
import static pl.dziurdziak.pobrLogoRecognition.model.calculation.Coefficient.W_8;
import static pl.dziurdziak.pobrLogoRecognition.model.calculation.Coefficient.W_9;
import static pl.dziurdziak.pobrLogoRecognition.model.calculation.MomentInvariant.M_1;
import static pl.dziurdziak.pobrLogoRecognition.model.calculation.MomentInvariant.M_2;
import static pl.dziurdziak.pobrLogoRecognition.model.calculation.MomentInvariant.M_3;
import static pl.dziurdziak.pobrLogoRecognition.model.calculation.MomentInvariant.M_4;
import static pl.dziurdziak.pobrLogoRecognition.model.calculation.MomentInvariant.M_5;
import static pl.dziurdziak.pobrLogoRecognition.model.calculation.MomentInvariant.M_6;
import static pl.dziurdziak.pobrLogoRecognition.model.calculation.MomentInvariant.M_7;
import static pl.dziurdziak.pobrLogoRecognition.model.calculation.MomentInvariant.M_8;
import static pl.dziurdziak.pobrLogoRecognition.model.calculation.MomentInvariant.M_9;

/**
 * @author Mateusz Dziurdziak
 */
@Immutable
public class SegmentCalculations {

    @Getter
    private final Segment segment;
    private final Table<Integer, Integer, Double> normalMoments;
    @Getter
    private double centerI, centerJ; // effectively final
    private final Map<CentralMoment, Double> centralMoments;
    private final Map<MomentInvariant, Double> momentInvariants;
    @Getter
    private int minRow, maxRow, minCol, maxCol; // effectively final
    @Getter
    private int width, height; // effectively final
    @Getter
    private int geomCenterRow, geomCenterCol;  // effectively final
    @Getter
    private final int area;
    @Getter
    private int perimeter; // effectively final
    @Getter
    private int lMax; // effectively final
    @Getter
    private double rMin, rMax; // effectively final
    private final Map<Coefficient, Double> coefficients;

    public SegmentCalculations(Segment segment) {
        checkArgument(segment.getSize() > 0, "Segment cannot be empty");
        this.segment = segment;
        normalMoments = HashBasedTable.create();
        calculateWeightCenter();
        centralMoments = new LinkedHashMap<>(CentralMoment.values().length);
        calculateCentralMoments();
        momentInvariants = new LinkedHashMap<>(MomentInvariant.values().length);
        calculateMomentInvariants();
        findExtremePoints();
        calculateWidthAndHeight();
        calculateGeometricCenter();
        area = segment.getSize();
        calculateContourParams(segment);
        coefficients = new LinkedHashMap<>(Coefficient.values().length);
        calculateCoefficients();
    }

    /**
     * Returns normal geometric moment. If moment was not calculated before performs calculation
     *
     * @param p variable p
     * @param q variable q
     * @return value of normal geometric moment
     */
    private double m(int p, int q) {
        if (!normalMoments.contains(p, q)) {
            normalMoments.put(p, q, calculateNormalMoment(p, q));
        }
        return normalMoments.get(p, q);
    }

    private double calculateNormalMoment(int p, int q) {
        double sum = 0;
        // in both loops we are counting from 1
        for (int i = 1; i <= segment.imageHeight(); i++) {
            for (int j = 1; j <= segment.imageWidth(); j++) {
                if (segment.isMember(i - 1, j - 1)) {
                    sum += pow(i, p) * pow(j, q);
                }
            }
        }
        return sum;
    }

    private void calculateWeightCenter() {
        centerI = m(1, 0) / m(0, 0);
        centerJ = m(0, 1) / m(0, 0);
    }

    private void calculateCentralMoments() {
        centralMoments.put(M_00, m(0, 0));
        centralMoments.put(M_01, m(0, 1) - (m(0, 1) / m(0, 0)) * m(0, 0));
        centralMoments.put(M_10, m(1, 0) - (m(1, 0) / m(0, 0)) * m(0, 0));
        centralMoments.put(M_11, m(1, 1) - m(1, 0) * m(0, 1) / m(0, 0));
        centralMoments.put(M_20, m(2, 0) - pow(m(1, 0), 2) / m(0, 0));
        centralMoments.put(M_02, m(0, 2) - pow(m(0, 1), 2) / m(0, 0));
        centralMoments.put(M_21, m(2, 1) - 2 * m(1, 1) * centerI - m(2, 0) * centerJ + 2 * m(0, 1) * pow(centerI, 2));
        centralMoments.put(M_12, m(1, 2) - 2 * m(1, 1) * centerJ - m(0, 2) * centerI + 2 * m(1, 0) * pow(centerJ, 2));
        centralMoments.put(M_30, m(3, 0) - 3 * m(2, 0) * centerI + 2 * m(1, 0) * pow(centerI, 2));
        centralMoments.put(M_03, m(0, 3) - 3 * m(0, 2) * centerJ + 2 * m(0, 1) * pow(centerJ, 2));
    }

    public double get(CentralMoment centralMoment) {
        return centralMoments.get(centralMoment);
    }

    private void calculateMomentInvariants() {
        momentInvariants.put(M_1, (get(M_20) + get(M_02)) / pow(m(0, 0), 2));
        momentInvariants.put(M_2, (pow(get(M_20) - get(M_02), 2) + 4 * pow(get(M_11), 2)) / pow(m(0, 0), 4));
        momentInvariants.put(M_3, (pow(get(M_30) - 3 * get(M_12), 2) + pow(3 * get(M_21) - get(M_03), 2)) / pow(m(0, 0), 5));
        momentInvariants.put(M_4, (pow(get(M_30) + get(M_12), 2) + pow(get(M_21) + get(M_03), 2)) / pow(m(0, 0), 5));
        momentInvariants.put(M_5, ((get(M_30) - 3 * get(M_12)) * (get(M_30) + get(M_12)) *
                (pow(get(M_30) + get(M_12), 2) - 3 * pow(get(M_21) + get(M_03), 2))
                + (3 * get(M_21) - get(M_03)) * (get(M_21) + get(M_03)) * (3 * pow(get(M_30) + get(M_12), 2) - pow(get(M_21) + get(M_03), 2))) /
                pow(m(0, 0), 10));
        momentInvariants.put(M_6, ((get(M_20) - get(M_02)) * (pow(get(M_30) + get(M_12), 2) - pow(get(M_21) + get(M_03), 2))
                + 4 * get(M_11) * (get(M_30) + get(M_12)) * (get(M_21) + get(M_03))) / pow(m(0, 0), 7));
        momentInvariants.put(M_7, (get(M_20) * get(M_02) - pow(get(M_11), 2)) / pow(m(0, 0), 4));
        momentInvariants.put(M_8, (get(M_30) * get(M_12) + get(M_21) * get(M_03) - pow(get(M_12), 2) - pow(get(M_21), 2)) / pow(m(0, 0), 5));
        momentInvariants.put(M_9, (get(M_20) * (get(M_21) * get(M_03) - pow(get(M_12), 2)) + get(M_02) * (get(M_03) * get(M_12) - pow(get(M_21), 2))
                - get(M_11) * (get(M_30) * get(M_03) - get(M_21) * get(M_12))) / pow(m(0, 0), 7));
        momentInvariants.put(MomentInvariant.M_10, (pow(get(M_30) * get(M_03) - get(M_12) * get(M_21), 2) - 4 * (get(M_30) * get(M_12) - pow(get(M_21), 2))
                * (get(M_03) * get(M_21) - get(M_12))) / pow(m(0, 0), 10));
    }

    public double get(MomentInvariant momentInvariant) {
        return momentInvariants.get(momentInvariant);
    }

    private void findExtremePoints() {
        minRow = minCol = Integer.MAX_VALUE;
        maxRow = maxCol = Integer.MIN_VALUE;
        for (Point point : segment.getPoints()) {
            minRow = min(minRow, point.getRow());
            maxRow = max(maxRow, point.getRow());
            minCol = min(minCol, point.getColumn());
            maxCol = max(maxCol, point.getColumn());
        }
    }

    private void calculateWidthAndHeight() {
        width = maxCol - minCol + 1;
        height = maxRow - minRow + 1;
    }

    private void calculateGeometricCenter() {
        geomCenterRow = (minRow + maxRow) / 2;
        geomCenterCol = (minCol + maxCol) / 2;
    }

    private void calculateContourParams(Segment segment) {
        rMin = Integer.MAX_VALUE;
        rMax = Integer.MIN_VALUE;
        for (int row = minRow; row <= maxRow; row++) {
            for (int col = minCol; col <= maxCol; col++) {
                if (segment.isMember(row, col)) {
                    for (Direction direction : Direction.values()) {
                        int neighbourRow = direction.getRowFunction().apply(row);
                        int neighbourCol = direction.getColumnFunction().apply(col);
                        boolean isNeighbourInSegment = neighbourRow >= minRow && neighbourRow <= maxRow
                                && neighbourCol >= minCol && neighbourCol <= maxCol
                                && segment.isMember(neighbourRow, neighbourCol);
                        if (!isNeighbourInSegment) {
                            perimeter++;
                            double distance = sqrt(pow(centerI - row, 2) + pow(centerJ - col, 2));
                            rMin = min(rMin, distance);
                            rMax = max(rMax, distance);
                        }
                    }
                }
            }
        }
        lMax = max(maxRow - minRow, maxCol - minCol);
    }

    private void calculateCoefficients() {
        coefficients.put(W_1, 2 * sqrt(area / PI));
        coefficients.put(W_2, perimeter / PI);
        coefficients.put(W_3, perimeter / (2 * sqrt(PI * area)) - 1);
        coefficients.put(W_7, rMin / rMax);
        coefficients.put(W_8, (double) lMax / perimeter);
        coefficients.put(W_9, 2 * sqrt(PI / area) / perimeter);
    }

    public double get(Coefficient coefficient) {
        return coefficients.get(coefficient);
    }

}
