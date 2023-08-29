package api;

public class UnSuccessRegistration {
    private String error;

    public UnSuccessRegistration(String error) {
        this.error = error;
    }

    public UnSuccessRegistration() {
        super();
    }

    public String getError() {
        return error;
    }
}


