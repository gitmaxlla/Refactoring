package com.directi.training.codesmells.refactored.pieces;

import com.directi.training.codesmells.refactored.Position;

public abstract class Piece
{
    private final Color _color;
    private final char _type;

    public Piece(Color color, char type)
    {
        _color = color;
        _type = type;
    }

    public Color getColor()
    {
        return _color;
    }

    public boolean isValidMove(Position from, Position to) {
        return false;
    }

    @Override
    public String toString()
    {
        return "" + _type;
    }
}
