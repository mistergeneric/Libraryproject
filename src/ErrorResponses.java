import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ErrorResponses {
    private static final Map<String,String> errors = new HashMap<String, String>();
    static {
        errors.put("memberId", "memberId must be comprised of 7 integers");
        errors.put("firstName", "first name must be less than 16 characters");
        errors.put("lastName", "first name must be less than 16 characters");
        errors.put("gender", "gender should be M for Male, F for Female or O for other");
        errors.put("query", "Please enter either \"books\" or \"dvds\"");
    }

    public static Map getErrors() {
        return Collections.unmodifiableMap(errors);
    }
}
