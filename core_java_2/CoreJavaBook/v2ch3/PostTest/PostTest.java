/**
   @version 1.01 2001-06-27
   @author Cay Horstmann
*/

import java.io.*;
import java.net.*;
import java.util.*;

/**
   This program demonstrates how to use the URLConnection class
   for a POST request. The URL and form data are in a .properties
   file whose name should be supplied on the command line.
*/
public class PostTest
{  
   public static void main(String[] args)
   {  
      try
      {  
         String fileName;
         if (args.length > 0)
            fileName = args[0];
         else
            fileName = "PostTest.properties";
         Properties props = new Properties();
         FileInputStream in = new FileInputStream(fileName);
         props.load(in);

         URL url = new URL(props.getProperty("URL"));
         props.remove("URL");
         String r = doPost(url, props);
         System.out.println(r);
      }
      catch (IOException exception)
      {  
         exception.printStackTrace();
      }
   }

   /**
      Makes a POST request and returns the server response.
      @param url the URL to post to
      @param nameValuePairs a table of name/value pairs to 
      supply in the request.
      @return the server reply (either from the input stream
      or the error stream)
   */
   public static String doPost(URL url,
      Properties nameValuePairs) throws IOException
   {  
      URLConnection connection = url.openConnection();
      connection.setDoOutput(true);

      PrintWriter out
         = new PrintWriter(connection.getOutputStream());

      Enumeration enum = nameValuePairs.keys();

      while (enum.hasMoreElements())
      {  
         String name = (String)enum.nextElement();
         String value = nameValuePairs.getProperty(name);
         out.print(name);
         out.print('=');
         out.print(URLEncoder.encode(value));
         if (enum.hasMoreElements()) out.print('&'); 
      }

      out.close();

      BufferedReader in;
      StringBuffer response = new StringBuffer();
      try
      {  
         in = new BufferedReader(new
            InputStreamReader(connection.getInputStream()));
      }
      catch (IOException e)
      {  
         if (!(connection instanceof HttpURLConnection)) throw e;
         InputStream err
            = ((HttpURLConnection)connection).getErrorStream();
         if (err == null) throw e;
         in = new BufferedReader(new InputStreamReader(err));
      }
      String line;

      while ((line = in.readLine()) != null)
         response.append(line + "\n");

      in.close();
      return response.toString();
   }
}



