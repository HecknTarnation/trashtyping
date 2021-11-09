package trashtyping;

/**
 *
 * @author Ben
 */
public class ControlCodes {

    public static final String ResetCursor = "\033[H";
    public static final String ResetFormatting = "\033[0m";
    public static final String Clear = ResetCursor + "\033[0J";

    public static final String RED_BACKGROUND = "\033[41m";
    public static final String YELLOW = "\033[33m";
    public static final String GREEN = "\033[32m";

}
