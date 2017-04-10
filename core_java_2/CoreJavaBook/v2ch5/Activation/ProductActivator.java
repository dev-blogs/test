/**
   @version 1.10 1999-08-21
   @author Cay Horstmann
*/

import java.io.*;
import java.net.*;
import java.rmi.*;
//import java.rmi.server.*;
import java.rmi.activation.*;
import java.util.*;

/**
   This server program activates two remote objects and
   registers them with the naming service.
*/
public class ProductActivator
{  
   public static void main(String args[])
   {  
      try
      {  
         System.out.println
            ("Constructing activation descriptors...");

         Properties props = new Properties();
         // use the server.policy file in the current directory
         props.put("java.security.policy", 
            new File("server.policy").getCanonicalPath());
         ActivationGroupDesc group = new ActivationGroupDesc(props, null);
         ActivationGroupID id 
            = ActivationGroup.getSystem().registerGroup(group);
         MarshalledObject p1param 
            = new MarshalledObject("Blackwell Toaster");
         MarshalledObject p2param 
            = new MarshalledObject("ZapXpress Microwave Oven");

         String classDir = ".";
         // turn the class directory into a file URL
         // for this demo we assume that the classes are in the current dir
         String classURL 
            = new File(classDir).getCanonicalFile().toURL().toString();

         ActivationDesc desc1 = new ActivationDesc(id, "ProductImpl", classURL,
            p1param);
         ActivationDesc desc2 = new ActivationDesc(id, "ProductImpl", classURL,
            p2param);
         
         Product p1 = (Product)Activatable.register(desc1);
         Product p2 = (Product)Activatable.register(desc2);

         System.out.println
            ("Binding activable implementations to registry...");

         Naming.rebind("toaster", p1);
         Naming.rebind("microwave", p2);

         System.out.println
            ("Exiting...");
      }
      catch(Exception e)
      {  
         e.printStackTrace();
      }
   }
}

