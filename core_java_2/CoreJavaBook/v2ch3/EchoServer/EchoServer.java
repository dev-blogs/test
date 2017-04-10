/**
 * @version 1.10 1997-06-27
 * @author Cay Horstmann
 */

import java.io.*;
import java.net.*;

/**
   This program implements a simple server that listens to
   port 8189 and echoes back all client input.
*/
public class EchoServer
{  
   public static void main(String[] args )
   {  
      try
      {  
         // establish server socket
         ServerSocket s = new ServerSocket(8189);

         // wait for client connection
         Socket incoming = s.accept( );
         BufferedReader in = new BufferedReader
            (new InputStreamReader(incoming.getInputStream()));
         PrintWriter out = new PrintWriter
            (incoming.getOutputStream(), true /* autoFlush */);

         out.println( "Hello! Enter BYE to exit." );

         // echo client input
         boolean done = false;
         while (!done)
         {  
            String line = in.readLine();
            if (line == null) done = true;
            else
            {  
               out.println("Echo: " + line);

               if (line.trim().equals("BYE"))
                  done = true;
            }
         }
         incoming.close();
      }
      catch (Exception e)
      {  
         e.printStackTrace();
      }
   }
}


