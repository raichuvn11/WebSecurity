package ultil;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
public class SessionUtils {
    public static  boolean  isValidUser (HttpServletRequest request, HttpServletResponse response){
        HttpSession session = request.getSession(false);

        return session != null && session.getAttribute("customer") != null;
    }
}
