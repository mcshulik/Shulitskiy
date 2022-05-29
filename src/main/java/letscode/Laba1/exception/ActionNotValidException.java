
package letscode.Laba1.exception;

        //import org.springframework.web.bind.annotation.ResponseStatus;

        import org.springframework.http.HttpStatus;
        import org.springframework.web.bind.annotation.ResponseStatus;

//public class ActionNotValidException extends RuntimeException {
//@ResponseStatus(code = HttpStatus.BAD_REQUEST.BAD_REQUEST, reason = "Invalid data")
@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Invalid data")
//@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class ActionNotValidException extends Exception {

    public ActionNotValidException() {
        super("Action is not valid");
    }
    public ActionNotValidException(String message) {
        super(message);
    }

    public ActionNotValidException(String message, Throwable cause) {
        super(message, cause);
    }

    public ActionNotValidException(Throwable cause) {
        super(cause);
    }
}
