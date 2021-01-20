package ca.cmpt213.a4.onlinehangman.model;

public enum Status {
    ACTIVE("Active"),
    WON("Won"),
    LOST("Lost");

    public final String status;

    Status(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return this.status;
    }
}
