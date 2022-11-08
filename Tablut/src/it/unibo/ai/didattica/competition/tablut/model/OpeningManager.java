package it.unibo.ai.didattica.competition.tablut.model;

import it.unibo.ai.didattica.competition.tablut.domain.Action;
import it.unibo.ai.didattica.competition.tablut.domain.State;

public interface OpeningManager {
    boolean isOpeningMoveAvailable(State state);
    Action getOpeningMove(State state);
}
