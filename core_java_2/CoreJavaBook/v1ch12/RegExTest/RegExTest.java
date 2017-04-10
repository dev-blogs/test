/**
   @version 1.00 2002-03-12
   @author Cay Horstmann
*/

import java.util.regex.*;
import javax.swing.*;

/**
   This program tests regular expression matching.
   Enter a pattern and strings to match, or hit Cancel
   to exit. If the pattern contains groups, the group
   boundaries are displayed in the match.
*/
public class RegExTest
{
   public static void main(String[] args)
   {
      String patternString = JOptionPane.showInputDialog(
         "Enter pattern:");
      Pattern pattern = null;
      try
      {
         pattern = Pattern.compile(patternString);
      }
      catch (PatternSyntaxException exception)
      {
         System.out.println("Pattern syntax error");
         System.exit(1);
      }

      while (true)
      {
         String input = JOptionPane.showInputDialog(
            "Enter string to match:");
         if (input == null) System.exit(0);
         
         Matcher matcher = pattern.matcher(input);
         if (matcher.matches())
         {
            System.out.println("Match");
            int g = matcher.groupCount();
            if (g > 0)
            {
               for (int i = 0; i < input.length(); i++)
               {
                  for (int j = 1; j <= g; j++)
                     if (i == matcher.start(j)) 
                        System.out.print('(');
                  System.out.print(input.charAt(i));
                  for (int j = 1; j <= g; j++)
                     if (i + 1 == matcher.end(j)) 
                        System.out.print(')');
               }
               System.out.println();
            }
         }
         else
            System.out.println("No match");
      }
   }
}

