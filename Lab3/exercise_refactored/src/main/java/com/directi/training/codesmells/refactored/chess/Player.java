package com.directi.training.codesmells.refactored.chess;

import com.directi.training.codesmells.refactored.pieces.Color;

public class Player
{
    private final String _name;
    private int _gamesWon;
    private Color _color;

    public Player(String name)
    {
        _name = name;
        _gamesWon = 0;
        _color = Color.BLACK;
    }

    public String getName()
    {
        return _name;
    }

    public void incrementWinCount()
    {
        _gamesWon++;
    }

    public Color getColor()
    {
        return _color;
    }

    public void setColor(Color color)
    {
        _color = color;
    }

    @Override
    public String toString()
    {
        return String.format("NAME: %s; GAMES WON: %d", _name, _gamesWon);
    }
}
