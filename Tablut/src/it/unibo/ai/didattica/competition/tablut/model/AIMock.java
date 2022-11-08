package it.unibo.ai.didattica.competition.tablut.model;

import it.unibo.ai.didattica.competition.tablut.domain.Action;
import it.unibo.ai.didattica.competition.tablut.domain.State;
import org.junit.Test;

import java.io.IOException;
import java.util.Set;

public class AIMock implements AI{

    GameEvaluator evaluator = new GameEvaluator();

    @Override
    public Action getMove(State state) {

        Set<Action> legalMoves = evaluator.getLegalMoves(state);
        System.out.println("QUESTE SONO LE NOSTRE MOSSETTE UwU");
        System.out.println(legalMoves);

        /*try {
            a = new Action("e4", "d4", state.getTurn());
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        return legalMoves.iterator().next();
    }
}
