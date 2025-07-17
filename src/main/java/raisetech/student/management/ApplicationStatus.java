package raisetech.student.management;

public enum ApplicationStatus {
    PROVISIONAL("仮申込"),
    FINAL("本申込"),
    IN_PROGRESS("受講中"),
    COMPLETED("受講終了");

    private final String label;

    ApplicationStatus(String label) {
        this.label = label;
    }
    @Override
    public String toString() {
        return label;
    }
}
