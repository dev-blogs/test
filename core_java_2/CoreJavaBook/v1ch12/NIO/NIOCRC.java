/**
   @version 1.00 2002-03-12
   @author Cay Horstmann
*/

import java.io.*;
import java.nio.*;
import java.nio.channels.*;
import java.util.zip.*;

/**
   This program computes the CRC checksum of a file, using
   a memory-mapped file.
   Usage: java CRC filename
*/
public class NIOCRC 
{
   public static void main(String[] args) throws Exception
   {
      FileInputStream in = new FileInputStream(args[0]);
      FileChannel channel = in.getChannel();

      CRC32 crc = new CRC32();
      long start = System.currentTimeMillis();

      MappedByteBuffer buffer = channel.map(
         FileChannel.MapMode.READ_ONLY, 0, (int)channel.size());
      while (buffer.hasRemaining()) 
         crc.update(buffer.get());

      long end = System.currentTimeMillis();
      System.out.println(Long.toHexString(crc.getValue()));
      System.out.println((end - start) + " milliseconds");
   }
}

