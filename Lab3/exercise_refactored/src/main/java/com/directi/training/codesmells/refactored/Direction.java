package com.directi.training.codesmells.refactored;

public record Direction(int rowOffset, int columnOffset) {
    public static Direction getUnitShift(Position from, Position to) {
        return new Direction(normalizedCompare(to.getRow(), from.getRow()),
            normalizedCompare(to.getColumn(), from.getColumn()));
    }

    private static int normalizedCompare(int x, int y)
    {
        if (x == y) return 0;
        return x > y ? 1 : -1;
    }
}
