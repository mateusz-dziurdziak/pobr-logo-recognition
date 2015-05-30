package pl.dziurdziak.pobrLogoRecognition.util;

import pl.dziurdziak.pobrLogoRecognition.model.segment.Point;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
import static java.util.stream.Collectors.toList;

/**
 * @author Mateusz Dziurdziak
 */
public final class CommonUtils {

    private CommonUtils() {
    }

    public static <T> List<T> flatArrays(T[][] array) {
        return Arrays.stream(array)
                .flatMap(Arrays::stream)
                .collect(toList());
    }

    public static int normalize256(int value) {
        return value < 0 ?
                0
                : (value > 256) ? 256 : value;
    }

    public static <T> T[][] copy(T[][] array) {
        T[][] copy = ((Object) array.getClass() == (Object) Object[].class)
                ? (T[][]) new Object[array.length][]
                : (T[][]) Array.newInstance(array.getClass().getComponentType(), array.length);
        for (int i = 0; i < array.length; i++) {
            copy[i] = Arrays.copyOf(array[i], array[i].length);
        }
        return copy;
    }

    public static double distance(Point first, Point second) {
        return sqrt(pow(first.getRow() - second.getRow(), 2) + pow(first.getColumn() - second.getColumn(), 2));
    }
}
