/**
   @version 1.21 2001-07-11
   @author Cay Horstmann
*/

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.rmi.*;
import java.rmi.server.*;
import java.util.*;
import javax.swing.*;

/**
   The warehouse client applet. 
*/
public class WarehouseApplet extends JApplet
{  
   public void init()
   {  
      initUI();

      try
      {  
         String warehouseName = getParameter("warehouse.name");
         if (warehouseName == null)
            warehouseName = "central_warehouse";
         String url = "rmi://" + getCodeBase().getHost()
            + "/" + warehouseName;
         centralWarehouse = (Warehouse)Naming.lookup(url);
      }
      catch(Exception e)
      {  
         showStatus("Error: Can't connect to warehouse. " + e);
      }
   }

   /**
      Initializes the user interface.
   */
   private void initUI()
   {  
      getContentPane().setLayout(new GridBagLayout());

      GridBagConstraints gbc = new GridBagConstraints();
      gbc.fill = GridBagConstraints.HORIZONTAL;
      gbc.weightx = 100;
      gbc.weighty = 0;

      add(new JLabel("Age:"), gbc, 0, 0, 1, 1);
      age = new JTextField(4);
      age.setText("20");
      add(age, gbc, 1, 0, 1, 1);

      male = new JCheckBox("Male", true);
      female = new JCheckBox("Female", true);
      add(male, gbc, 0, 1, 1, 1);
      add(female, gbc, 1, 1, 1, 1);

      add(new JLabel("Hobbies"), gbc, 0, 2, 1, 1);
      String[] choices = { "Gardening", "Beauty",
         "Computers", "Household", "Sports" };
      gbc.fill = GridBagConstraints.BOTH;
      hobbies = new JComboBox(choices);
      add(hobbies, gbc, 1, 2, 1, 1);

      gbc.fill = GridBagConstraints.NONE;
      JButton submitButton = new JButton("Submit");
      add(submitButton, gbc, 0, 3, 2, 1);
      submitButton.addActionListener(new
         ActionListener()
         {
            public void actionPerformed(ActionEvent evt)
            {  
               callWarehouse();
            }            
         });

      gbc.weighty = 100;
      gbc.fill = GridBagConstraints.BOTH;
      result = new JTextArea(4, 40);
      result.setEditable(false);
      add(result, gbc, 0, 4, 2, 1);

      imagePanel = new JPanel();
      imagePanel.setMinimumSize(new Dimension(0, 60));
      add(imagePanel, gbc, 0, 5, 2, 1);
   }

   /**
      Add a component to this frame.
      @param c the component to add
      @param gbc the grid bag constraints
      @param x the grid bax column
      @param y the grid bag row
      @param w the number of grid bag columns spanned
      @param h the number of grid bag rows spanned
   */
   private void add(Component c, GridBagConstraints gbc,
      int x, int y, int w, int h)
   {  gbc.gridx = x;
      gbc.gridy = y;
      gbc.gridwidth = w;
      gbc.gridheight = h;
      getContentPane().add(c, gbc);
   }

   /**
      Call the remote warehouse to find matching products.
   */
   private void callWarehouse()
   {  
      try
      {  
         Customer c = new Customer(Integer.parseInt(age.getText()),
            (male.isSelected() ? Product.MALE : 0)
            + (female.isSelected() ? Product.FEMALE : 0),
            new String[] { (String)hobbies.getSelectedItem() });
         ArrayList recommendations = centralWarehouse.find(c);
         result.setText(c + "\n");
         imagePanel.removeAll();
         for (int i = 0; i < recommendations.size(); i++)
         {  
            Product p = (Product)recommendations.get(i);
            String t = p.getDescription() + "\n";
            result.append(t);
            Image productImage
               = getImage(getCodeBase(), p.getImageFile());
            imagePanel.add(new JLabel(new ImageIcon(productImage)));
         }
      }
      catch(Exception e)
      {  
         result.setText("Exception: " + e);
      }
   }

   private static final int WIDTH = 300;
   private static final int HEIGHT = 300;
   
   private Warehouse centralWarehouse;
   private JTextField age;
   private JCheckBox male;
   private JCheckBox female;
   private JComboBox hobbies;
   private JTextArea result;
   private JPanel imagePanel;
}



