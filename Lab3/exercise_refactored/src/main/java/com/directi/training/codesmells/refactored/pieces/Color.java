package com.directi.training.codesmells.refactored.pieces;

public enum Color
{
    WHITE('W'),
    BLACK('B');

    private final String _charForColor;

    Color(Character charForColor) { _charForColor = "" + charForColor; }

    public boolean isWhite() {
        return Color.WHITE.equals(this);
    }

    public Color inverse() {
        if (isBlack())
            return Color.WHITE;
        return Color.BLACK;
    }

    public boolean isBlack() {
        return Color.BLACK.equals(this);
    }

    @Override
    public String toString() { return _charForColor; }
}
