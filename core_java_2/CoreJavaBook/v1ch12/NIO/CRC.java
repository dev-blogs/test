/**
   @version 1.00 2002-03-12
   @author Cay Horstmann
*/

import java.io.*;
import java.util.zip.*;

/**
   This program computes the CRC checksum of a file, using
   an input stream.
   Usage: java CRC filename
*/
public class CRC
{
   public static void main(String[] args) throws IOException
   {
      InputStream in = new FileInputStream(args[0]);
      CRC32 crc = new CRC32();
      int c;
      long start = System.currentTimeMillis();
      while((c = in.read()) != -1)   
         crc.update(c);
      long end = System.currentTimeMillis();
      System.out.println(Long.toHexString(crc.getValue()));
      System.out.println((end - start) + " milliseconds");
   }

}
