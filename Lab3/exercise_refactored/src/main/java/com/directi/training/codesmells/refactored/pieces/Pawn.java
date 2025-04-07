package com.directi.training.codesmells.refactored.pieces;

import com.directi.training.codesmells.refactored.Position;
import com.directi.training.codesmells.refactored.context.PawnContext;

public final class Pawn extends Piece
{
    private PawnContext context = null;

    public Pawn(Color color)
    {
        super(color, 'p');
    }

    public void setContext(PawnContext context) {
        this.context = context;
    }

    @Override
    public boolean isValidMove(Position from, Position to)
    {
        if (this.context == null) {
            throw new IllegalStateException();
        }

        int columnsMoved = from.columnDistance(to);
        int rowsMoved = from.rowDistance(to);

        boolean result = notMovingBackwards(from, to)
               && ((columnsMoved <= 1 && rowsMoved == 1) || (columnsMoved == 0 && rowsMoved == 2))
               && isValidForwardStepAmount(from, to, this.context.atInitialPosition())
               && isValidSidewayStepAmount(from, to,
                    this.context.isOpponentAtForwardLeftFlank(),
                    this.context.isOpponentAtForwardRightFlank());

        this.context = null;
        return result;
    }

    private boolean notMovingBackwards(Position from, Position to)
    {
        return switch (getColor()) {
            case WHITE -> to.getRow() < from.getRow();
            case BLACK -> to.getRow() > from.getRow();
        };
    }

    private boolean isValidForwardStepAmount(Position from, Position to, boolean atInitialPosition)
    {
        int rowsMoved = from.rowDistance(to);
        return rowsMoved > 0 && (rowsMoved <= (atInitialPosition ? 2 : 1));
    }

    private boolean isValidSidewayStepAmount(Position from, Position to,
                                             boolean isOpponentAtForwardLeftFlank,
                                             boolean isOpponentAtForwardRightFlank)
    {
        int sideShiftOriented = getSideUnitShiftOriented(from, to);

        return sideShiftOriented == 1 && isOpponentAtForwardRightFlank
            || sideShiftOriented == -1 && isOpponentAtForwardLeftFlank
            || sideShiftOriented == 0;
    }

    private int getSideUnitShiftOriented(Position from, Position to) {
        int columnsDiff = to.getColumn() - from.getColumn();
        if (getColor().isWhite()) { columnsDiff *= -1; }
        return columnsDiff;
    }
}
