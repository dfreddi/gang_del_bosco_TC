package it.unibo.ai.didattica.competition.tablut.model;

import it.unibo.ai.didattica.competition.tablut.domain.Action;
import it.unibo.ai.didattica.competition.tablut.domain.State;

public class OpeningManagerMock implements OpeningManager{
    @Override
    public boolean isOpeningMoveAvailable(State state) {
        return false;
    }

    @Override
    public Action getOpeningMove(State state) {
        return null;
    }
}
