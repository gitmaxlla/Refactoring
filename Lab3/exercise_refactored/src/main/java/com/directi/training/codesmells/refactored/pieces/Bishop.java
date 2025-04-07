package com.directi.training.codesmells.refactored.pieces;

import com.directi.training.codesmells.refactored.Position;

public final class Bishop extends Piece
{
    public Bishop(Color color)
    {
        super(color, 'b');
    }

    @Override
    public boolean isValidMove(Position from, Position to)
    {
        return from.sharesDiagonal(to);
    }
}
