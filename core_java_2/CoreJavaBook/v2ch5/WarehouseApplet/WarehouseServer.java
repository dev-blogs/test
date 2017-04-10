/**
   @version 1.11 2001-07-10
   @author Cay Horstmann
*/

import java.io.*;
import java.rmi.*;
import java.rmi.server.*;

/**
   This server program instantiates a remote warehouse
   objects, registers it with the naming service, and waits 
   for clients to invoke methods.
*/
public class WarehouseServer
{  
   public static void main(String[] args)
   {  
      try
      {  
         System.out.println
            ("Constructing server implementations...");

         WarehouseImpl w = new WarehouseImpl();
         w.read(new BufferedReader(new 
            FileReader("products.dat")));

         System.out.println
            ("Binding server implementations to registry...");

         Naming.rebind("central_warehouse", w);

         System.out.println
            ("Waiting for invocations from clients...");
      }
      catch(Exception e)
      {  
         e.printStackTrace();
      }
   }
}
