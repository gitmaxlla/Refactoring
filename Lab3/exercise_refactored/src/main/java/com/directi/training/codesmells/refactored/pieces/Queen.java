package com.directi.training.codesmells.refactored.pieces;

import com.directi.training.codesmells.refactored.Position;

public final class Queen extends Piece
{
    public Queen(Color color)
    {
        super(color, 'q');
    }

    @Override
    public boolean isValidMove(Position from, Position to) {
        return from.isStraightLineTo(to);
    }
}
