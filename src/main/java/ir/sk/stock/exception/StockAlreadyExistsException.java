package ir.sk.stock.exception;

public class StockAlreadyExistsException  extends RuntimeException {

    private static final String MSG_TEMPLATE = "stock with name '%s' already exists";

    public StockAlreadyExistsException(String message) {
        super(message);
    }

    public static StockAlreadyExistsException withName(String name) {
        return new StockAlreadyExistsException(MSG_TEMPLATE.formatted(name));
    }
}
