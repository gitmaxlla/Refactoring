package com.directi.training.codesmells.refactored.context;

public record PawnContext(boolean atInitialPosition,
                          boolean isOpponentAtForwardLeftFlank,
                          boolean isOpponentAtForwardRightFlank) {}
