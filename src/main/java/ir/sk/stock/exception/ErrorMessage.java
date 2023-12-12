package ir.sk.stock.exception;

public record ErrorMessage(int code, String message) {
    public static ErrorMessage of(int code, String message) {
        return new ErrorMessage(code, message);
    }
}
