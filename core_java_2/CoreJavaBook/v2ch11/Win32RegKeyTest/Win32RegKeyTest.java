/**
   @version 1.01 2001-08-30
   @author Cay Horstmann
*/

import java.util.*;

public class Win32RegKeyTest
{  
   public static void main(String[] args)
   {  
      Win32RegKey key = new Win32RegKey(
         Win32RegKey.HKEY_LOCAL_MACHINE,
         "SOFTWARE\\JavaSoft\\Java Development Kit\\1.4");

      key.setValue("Default user", "Harry Hacker");
      key.setValue("Lucky number", new Integer(13));
      key.setValue("Small primes", new byte[]
         { 2, 3, 5, 7, 11 });

      Enumeration enum = key.names();

      while (enum.hasMoreElements())
      {  
         String name = (String)enum.nextElement();
         System.out.print(name + "=");

         Object value = key.getValue(name);

         if (value instanceof byte[])
         {  
            byte[] bvalue = (byte[])value;
            for (int i = 0; i < bvalue.length; i++)
               System.out.print((bvalue[i] & 0xFF) + " ");
         }
         else System.out.print(value);

         System.out.println();
      }
   }
}
