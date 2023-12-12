package ir.sk.stock.exception;

/**
 * Created by sad.kayvanfar on 12/22/2021
 */
public class StockNotFoundException extends RuntimeException {

    private static final String MSG_TEMPLATE = "stock %d not found";

    public StockNotFoundException(String message) {
        super(message);
    }

    public static StockNotFoundException withId(Long id) {
        return new StockNotFoundException(MSG_TEMPLATE.formatted(id));
    }
}
