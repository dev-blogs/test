import java.io.*;
import java.util.*;
import java.sql.*;
 
/**
   Executes all SQL statements in a file.
   Call this program as
   java -classpath driverPath:. ExecSQL commandFile
*/
class ExecSQL
{
   public static void main (String args[])
   {   
      try
      {

         Reader reader;
         if (args.length == 0)
            reader = new InputStreamReader(System.in);
         else
            reader = new FileReader(args[0]);
   
         Connection conn = getConnection();
         Statement stat = conn.createStatement();
      
         BufferedReader in = new BufferedReader(reader);
      
         boolean done = false;
         while (!done)
         {
            if (args.length == 0)
               System.out.println(
                  "Enter command or a blank line to exit:");

            String line = in.readLine();
            if (line == null || line.length() == 0) 
               done = true;
            else
            {
               try
               {
                  boolean hasResultSet = stat.execute(line);
                  if (hasResultSet)
                     showResultSet(stat);
               }
               catch (SQLException ex)
               {
                  while (ex != null)
                  {  
                     ex.printStackTrace();
                     ex = ex.getNextException();
                  }
               }
            }
         }
      
         in.close();
         stat.close();
         conn.close();
      }
      catch (Exception ex)
      {
         ex.printStackTrace();
      }
   }

   /**
      Gets a connection from the properties specified
      in the file database.properties
      @return the database connection
   */
   public static Connection getConnection()
      throws SQLException, IOException
   {  
      Properties props = new Properties();
      FileInputStream in 
         = new FileInputStream("database.properties");
      props.load(in);
      in.close();

      String drivers = props.getProperty("jdbc.drivers");
      if (drivers != null)
         System.setProperty("jdbc.drivers", drivers);
      String url = props.getProperty("jdbc.url");
      String username = props.getProperty("jdbc.username");
      String password = props.getProperty("jdbc.password");

      return
         DriverManager.getConnection(url, username, password);
   }

   /**
      Prints a result set.
      @param stat the statement whose result set should be 
      printed
   */
   public static void showResultSet(Statement stat) 
      throws SQLException
   { 
      ResultSet result = stat.getResultSet();
      ResultSetMetaData metaData = result.getMetaData();
      int columnCount = metaData.getColumnCount();

      for (int i = 1; i <= columnCount; i++)
      {  
         if (i > 1) System.out.print(", ");
         System.out.print(metaData.getColumnLabel(i));
      }
      System.out.println();

      while (result.next())
      {  
         for (int i = 1; i <= columnCount; i++)
         {  
            if (i > 1) System.out.print(", ");
            System.out.print(result.getString(i));
         }
         System.out.println();
      }
      result.close();
   }
}
