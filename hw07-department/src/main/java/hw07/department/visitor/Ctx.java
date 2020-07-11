package hw07.department.visitor;

import hw07.department.observer.GetBalanceHandler;

public interface Ctx {
    void run(GetBalanceHandler handler);
}
