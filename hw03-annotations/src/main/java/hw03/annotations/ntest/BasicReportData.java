package hw03.annotations.ntest;

import java.util.*;

class BasicReportData {

    class Row {
        int id;
        String testName;
        TestCaseStatus status;
        String errCause;

        public Row(int id, String testName, TestCaseStatus status, String errCause) {
            this.id = id;
            this.testName = testName;
            this.status = status;
            this.errCause = errCause;
        }

    }

    private final ArrayList<Row> rows = new ArrayList<>();
    private int numOfSuccess;

    void addRow(int id, String testName, TestCaseStatus status, String errCause) {
        rows.add(new Row(id, testName, status, errCause));
    }

    void setNumOfSuccess(int numOfSuccess) {
        this.numOfSuccess = numOfSuccess;
    }

    int getNumOfSuccess() {
        return numOfSuccess;
    }

    ArrayList<Row> getRows() {
        return rows;
    }
}

