/**
   @version 1.01 2001-07-10
   @author Cay Horstmann
*/

import java.io.*;
import java.rmi.*;
import java.util.*;
import java.rmi.server.*;
import java.util.*;

/**
   This class is the implementation for the remote
   Warehouse interface.
*/
public class WarehouseImpl
   extends UnicastRemoteObject
   implements Warehouse
{  
   /**
      Constructs a warehouse implementation.
   */
   public WarehouseImpl()
      throws RemoteException
   {  
      products = new ArrayList();
      coreJavaBook = new ProductImpl("Core Java Book",
         0, 200, Product.BOTH, "Computers");
   }

   /**
      Reads in a set of product descriptions. Each line has t
      the format
      name|sex|age1|age2|hobby, e.g.
      Blackwell Toaster|BOTH|18|200|Household
      @param reader the reader to read from
   */
   public void read(BufferedReader reader) throws IOException
   {
      String line;
      while ((line = reader.readLine()) != null)
      {
         StringTokenizer tokenizer = new StringTokenizer(line, 
            "|");
         String name = tokenizer.nextToken();
         String s = tokenizer.nextToken();         
         int sex = 0;
         if (s.equals("MALE")) sex = Product.MALE;
         else if (s.equals("FEMALE")) sex = Product.FEMALE;
         else if (s.equals("BOTH")) sex = Product.BOTH;
         int age1 = Integer.parseInt(tokenizer.nextToken());
         int age2 = Integer.parseInt(tokenizer.nextToken());
         String hobby = tokenizer.nextToken();
         add(new ProductImpl(name, sex, age1, age2, hobby));
      }      
   }

   /**
      Add a product to the warehouse. Note that this is a local
      method.
      @param p the product to add
   */
   public synchronized void add(ProductImpl p)
   {  
      products.add(p);
   }

   public synchronized ArrayList find(Customer c)
      throws RemoteException
   {  
      ArrayList result = new ArrayList();
      // add all matching products
      for (int i = 0; i < products.size(); i++)
      {  
         ProductImpl p = (ProductImpl)products.get(i);
         if (p.match(c)) result.add(p);
      }
      // add the product that is a good match for everyone
      result.add(coreJavaBook);

      // we reset c just to show that c is a copy of the
      // client object
      c.reset(); 
      return result;
   }

   private ArrayList products;
   private ProductImpl coreJavaBook;
}
