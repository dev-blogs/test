import java.io.*;

/**
   This is a demonstration program that can be launched from
   the AuthTest program. It simply counts the lines in all
   files whose names are specified as command-line arguments.
*/
public class LineCount
{
   public static void main(String[] args) throws Exception
   {
      for (int i = 0; i < args.length; i++)
      {
         int count = 0;
         String line;
         BufferedReader reader 
            = new BufferedReader(new FileReader(args[i]));
         while ((line = reader.readLine()) != null) count++;
         System.out.println(args[i] + ": " + count);
      }
   }
}
