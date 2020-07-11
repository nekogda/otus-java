package hw07.department.visitor;

import hw07.department.observer.GetBalanceHandler;
import hw07.department.observer.report.BalanceReport;

public class BalanceReportCtx  implements Ctx {
    private final BalanceReport report = new BalanceReport();

    @Override
    public void run(GetBalanceHandler handler) {
        report.updateBalance(handler.getCommand().execute());
    }

    public BalanceReport getReport() {
        return report;
    }
}
