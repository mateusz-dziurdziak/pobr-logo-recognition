package pl.dziurdziak.pobrLogoRecognition.util;

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * @author Mateusz Dziurdziak
 */
public final class CommonUtils {

    private CommonUtils() {}

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
}
