package hw07.department.chain;

import hw07.department.DepATM;

@FunctionalInterface
public interface ChainCommand {
    boolean execute(DepATM atm);
}
