package com.directi.training.codesmells;

import com.directi.training.codesmells.refactored.GameEngine;
import com.directi.training.codesmells.refactored.chess.Player;

import java.util.Scanner;

public class Main
{
    private static final Scanner scanner = new Scanner(System.in);
    private static final GameEngine gameEngine = new GameEngine();


    public static void main(String[] args)
    {
        gameEngine.initGame();
        gameEngine.startGame();
    }
}
