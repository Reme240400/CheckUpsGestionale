package Interfaces;

public interface DialogInterface {
    public FieldsCheckResponse areFieldsValid();

    public class FieldsCheckResponse {
        private boolean valid;
        private String errorMessage;

        public FieldsCheckResponse(boolean valid, String errorMessage) {
            this.valid = valid;
            this.errorMessage = errorMessage;
        }

        public FieldsCheckResponse() {
            this(true, "");
        }

        public FieldsCheckResponse(String errorMessage) {
            this(false, errorMessage);
        }

        public boolean isValid() {
            return valid;
        }

        public String getErrorMessage() {
            return errorMessage;
        }
    }
}
