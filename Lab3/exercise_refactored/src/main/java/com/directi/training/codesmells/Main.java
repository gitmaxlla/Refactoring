package com.directi.training.codesmells;

import com.directi.training.codesmells.refactored.GameEngine;
import com.directi.training.codesmells.refactored.chess.Player;

import java.util.Scanner;

public class Main
{
    private static final Scanner scanner = new Scanner(System.in);
    private static final GameEngine gameEngine = new GameEngine (
        getPlayersName(1),
        getPlayersName(2)
    );


    public static void main(String[] args)
    {
        gameEngine.initGame();
        gameEngine.startGame();
    }

    private static Player getPlayersName(int order) {
        String prompt = String.format("Enter Player %d Name: ", order);
        System.out.print(prompt);
        return new Player(scanner.nextLine());
    }
}
