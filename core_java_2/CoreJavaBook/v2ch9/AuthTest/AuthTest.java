import java.lang.reflect.*;
import java.security.*;
import javax.security.auth.*;
import javax.security.auth.login.*;

/**
   This program authenticates a user via the Unix login
   and then executes the program specified by the command line
   with the user's principals. 
*/
public class AuthTest
{
   public static void main(final String[] args)
   {
      try 
      {
         System.setSecurityManager(new SecurityManager());

         LoginContext context = new LoginContext("Login1");
         context.login();
         System.out.println("Authentication successful.");
         Subject subject = context.getSubject();
         Subject.doAs(subject, new
            PrivilegedExceptionAction()
            {
               public Object run() throws Exception
               {
                  // invoke the main method of the class
                  // specified in args[0], with command line
                  // arguments args[1] args[2] . . .
                  Class cl = Class.forName(args[0]);
                  Method mainMethod = cl.getMethod("main", 
                     new Class[] { String[].class });
                  String[] args1 = new String[args.length - 1];
                  System.arraycopy(args, 1, 
                     args1, 0, args1.length);
                  mainMethod.invoke(null, 
                     new Object[] { args1 });
                  return null;
               }
            });

         context.logout();
      } 
      catch (LoginException exception) 
      {
         exception.printStackTrace();
      }      
      catch (PrivilegedActionException exception)
      {
         exception.printStackTrace();
      }      
   }
}

