package com.flipkart.fsm;

import com.flipkart.fsm.core.entities.Context;
import com.flipkart.fsm.core.entities.Event;
import com.flipkart.fsm.core.entities.State;
import com.flipkart.fsm.core.entities.Transition;
import com.flipkart.fsm.services.ActionService;
import com.flipkart.fsm.services.StateManagementService;
import com.flipkart.fsm.services.TransitionService;
import com.flipkart.fsm.services.impl.ActionServiceImpl;
import com.flipkart.fsm.services.impl.StateManagementServiceImpl;
import com.flipkart.fsm.services.impl.TransitionServiceImpl;

import java.util.Collection;

/**
 * Entity by : koushikr.
 * on 23/10/15.
 */
public class MachineBuilder<C extends Context> {

    private StateMachine<C> stateMachine;

    private void addTransition(Event event, State from, State to){
        stateMachine.addTransition(from, new Transition(event, from, to));
    }

    protected MachineBuilder(State startState, TransitionService transitionService, StateManagementService stateManagementService, ActionService actionService){
        stateMachine = new StateMachine<C>(startState, transitionService, stateManagementService, actionService);
    }

    public static <C extends Context> MachineBuilder<C> start(State startState){
        return new MachineBuilder<C>(startState, new TransitionServiceImpl(), new StateManagementServiceImpl(), new ActionServiceImpl());
    }

    public <C extends Context> MachineBuilder<C> onTransition(Event event, Collection<State> fromStates, State to){
        fromStates.stream().forEach(state -> addTransition(event, state, to));
        return (MachineBuilder<C>) this;
    }

    public <C extends Context> MachineBuilder<C> onTransition(Event event, State from, State to){
        addTransition(event, from, to);
        return (MachineBuilder<C>) this;
    }

    public StateMachine<C> end(Collection<State> endStates){
        stateMachine.addEndStates(endStates);
        return stateMachine;
    }
}