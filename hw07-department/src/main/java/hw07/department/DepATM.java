package hw07.department;

import hw07.department.adapter.ATMAdapter;
import hw07.department.factory.EventListenersCreator;
import hw07.department.memento.Originator;

public interface DepATM extends ATMAdapter, EventListenersCreator, Originator {
}
