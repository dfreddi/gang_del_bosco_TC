package it.unibo.ai.didattica.competition.tablut.model;

import it.unibo.ai.didattica.competition.tablut.domain.Action;
import it.unibo.ai.didattica.competition.tablut.domain.State;
import org.junit.Test;

import java.io.IOException;

public class AIMock implements AI{

    @Override
    public Action getMove(State state) {
        Action a = null;
        try {
            a = new Action("e4", "d4", state.getTurn());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return a;
    }
}
