package pl.dziurdziak.pobrLogoRecognition.model.segment;

import lombok.Getter;

import java.util.function.IntFunction;

/**
 * @author Mateusz Dziurdziak
 */
public enum Direction {
    NORTH(row -> row - 1, col -> col),
    EAST(row -> row, col -> col + 1),
    SOUTH(row -> row + 1, col -> col),
    WEST(row -> row, col -> col - 1);

    @Getter
    private final IntFunction<Integer> rowFunction;

    @Getter
    private final IntFunction<Integer> columnFunction;

    Direction(IntFunction<Integer> rowFunction, IntFunction<Integer> columnFunction) {
        this.rowFunction = rowFunction;
        this.columnFunction = columnFunction;
    }

}
