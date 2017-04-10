/**
   @version 1.20 2001-08-23
   @author Cay Horstmann
*/

import java.util.*;
import java.io.*;
import java.lang.reflect.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
   This program demonstrates a custom class loader that decrypts
   class files.
*/
public class ClassLoaderTest
{
   public static void main(String[] args)
   {
      JFrame frame = new ClassLoaderFrame();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.show();
   }
}

/**
   This frame contains two text fields for the name of the class
   to load and the decryption key.
*/
class ClassLoaderFrame extends JFrame
{
   public ClassLoaderFrame()
   {
      setTitle("ClassLoaderTest");
      setSize(WIDTH, HEIGHT);
      getContentPane().setLayout(new GridBagLayout());
      GridBagConstraints gbc = new GridBagConstraints();
      gbc.weightx = 0;
      gbc.weighty = 100;
      gbc.fill = GridBagConstraints.NONE;
      gbc.anchor = GridBagConstraints.EAST;
      add(new JLabel("Class"), gbc, 0, 0, 1, 1);
      add(new JLabel("Key"), gbc, 0, 1, 1, 1);
      gbc.weightx = 100;
      gbc.fill = GridBagConstraints.HORIZONTAL;
      gbc.anchor = GridBagConstraints.WEST;
      add(nameField, gbc, 1, 0, 1, 1);
      add(keyField, gbc, 1, 1, 1, 1);
      gbc.fill = GridBagConstraints.NONE;
      gbc.anchor = GridBagConstraints.CENTER;
      JButton loadButton = new JButton("Load");
      add(loadButton, gbc, 0, 2, 2, 1);
      loadButton.addActionListener(new
         ActionListener()
         {
            public void actionPerformed(ActionEvent event)
            {
               runClass(nameField.getText(), keyField.getText());
            }
         });
   }

   /**
      A convenience method to add a component to given grid bag
      layout locations.
      @param c the component to add
      @param gbc the grid bag constraints to use
      @param x the x grid position
      @param y the y grid position
      @param w the grid width
      @param h the grid height
   */
   public void add(Component c, GridBagConstraints gbc,
      int x, int y, int w, int h)
   {
      gbc.gridx = x;
      gbc.gridy = y;
      gbc.gridwidth = w;
      gbc.gridheight = h;
      getContentPane().add(c, gbc);
   }

   /**
      Runs the main method of a given class.
      @param name the class name
      @param key the decryption key for the class files
   */
   public void runClass(String name, String key)
   {
      try
      {
         ClassLoader loader
            = new CryptoClassLoader(Integer.parseInt(key));
         Class c = loader.loadClass(name);
         String[] args = new String[] {};

         Method m = c.getMethod("main",
            new Class[] { args.getClass() });
         m.invoke(null, new Object[] { args });
      }
      catch (Throwable e)
      {
         JOptionPane.showMessageDialog(this, e);
      }
   }

   private JTextField keyField = new JTextField("3", 4);
   private JTextField nameField = new JTextField(30);
   private static final int WIDTH = 300;
   private static final int HEIGHT = 200;
}

/**
   This class loader loads encrypted class files.
*/
class CryptoClassLoader extends ClassLoader
{
   /**
      Constructs a crypto class loader.
      @param k the decryption key
   */
   public CryptoClassLoader(int k)
   {
      key = k;
   }

   protected Class findClass(String name)
      throws ClassNotFoundException
   {
      byte[] classBytes = null;
      try
      {
         classBytes = loadClassBytes(name);
      }
      catch (IOException exception)
      {
         throw new ClassNotFoundException(name);
      }

      Class cl = defineClass(name, classBytes,
         0, classBytes.length);
      if (cl == null)
            throw new ClassNotFoundException(name);
      return cl;
   }

   /**
      Loads and decrypt the class file bytes.
      @param name the class name
      @return an array with the class file bytes
   */
   private byte[] loadClassBytes(String name)
      throws IOException
   {
      String cname = name.replace('.', '/') + ".caesar";
      FileInputStream in = null;
      try
      {
         in = new FileInputStream(cname);
         ByteArrayOutputStream buffer
            = new ByteArrayOutputStream();
         int ch;
         while ((ch = in.read()) != -1)
         {
            byte b = (byte)(ch - key);
            buffer.write(b);
         }
         in.close();
         return buffer.toByteArray();
      }
      finally
      {
         if (in != null)
            in.close();
      }
   }

   private Map classes = new HashMap();
   private int key;
}
