/**
   @version 1.20 1999-09-24
   @author Cay Horstmann
*/

import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.io.*;
import javax.swing.*;

/**
   A bean for specifying file names.
*/
public class FileNameBean extends JPanel
{
   public FileNameBean()
   {
      dialogButton = new JButton("...");
      nameField = new JTextField(30);

      chooser = new JFileChooser();
      chooser.setCurrentDirectory(new File("."));

      chooser.setFileFilter(new
         javax.swing.filechooser.FileFilter()
         {
            public boolean accept(File f)
            {
               String name = f.getName().toLowerCase();
               return name.endsWith("." + defaultExtension)
                  || f.isDirectory();
            }
            public String getDescription()
            {
               return defaultExtension + " files";
            }
         });

      setLayout(new GridBagLayout());
      GridBagConstraints gbc = new GridBagConstraints();
      gbc.weightx = 100;
      gbc.weighty = 100;
      gbc.anchor = GridBagConstraints.WEST;
      gbc.fill = GridBagConstraints.BOTH;
      add(nameField, gbc, 0, 0, 1, 1);

      dialogButton.addActionListener(
         new ActionListener()
         {
            public void actionPerformed(ActionEvent evt)
            {
               int r = chooser.showOpenDialog(null);
               if(r == JFileChooser.APPROVE_OPTION)
               {
                  File f = chooser.getSelectedFile();
                  try
                  {
                     String name = f.getCanonicalPath();
                     setFileName(name);
                  }
                  catch (IOException exception)
                  {
                  }
               }
            }
         });
      nameField.setEditable(false);

      gbc.weightx = 0;
      gbc.anchor = GridBagConstraints.EAST;
      gbc.fill = GridBagConstraints.NONE;
      add(dialogButton, gbc, 1, 0, 1, 1);
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
      add(c, gbc);
   }

   /**
      Sets the fileName property.
      @param newValue the new file name
   */
   public void setFileName(String newValue)
   {
      String oldValue = nameField.getText();
      nameField.setText(newValue);
      firePropertyChange("fileName", oldValue, newValue);
   }

   /**
      Gets the fileName property.
      @return the name of the selected file
   */
   public String getFileName()
   {
      return nameField.getText();
   }

   /**
      Sets the defaultExtension property.
      @param s the new default extension
   */
   public void setDefaultExtension(String s)
   {
      defaultExtension = s;
   }

   /**
      Gets the defaultExtension property.
      @return the default extension in the file chooser
   */
   public String getDefaultExtension()
   {
      return defaultExtension;
   }

   public Dimension getPreferredSize()
   {
      return new Dimension(XPREFSIZE, YPREFSIZE);
   }

   private static final int XPREFSIZE = 200;
   private static final int YPREFSIZE = 20;
   private JButton dialogButton;
   private JTextField nameField;
   private JFileChooser chooser;
   private String defaultExtension = "gif";
}

