/**
   @version 1.20 1999-08-23
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
   The client for the warehouse program. 
*/
public class WarehouseClient
{  
   public static void main(String[] args)
   {
      System.setProperty("java.security.policy", "client.policy");
      System.setSecurityManager(new RMISecurityManager());
      JFrame frame = new WarehouseClientFrame();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.show();
   }
}

/**
   A frame to select the customer's age, sex, and hobbies, and to
   show the matching products resulting from a remote call to the
   warehouse.
*/
class WarehouseClientFrame extends JFrame
{  
   public WarehouseClientFrame()
   {  
      setTitle("WarehouseClient");
      setSize(WIDTH, HEIGHT);
      initUI();

      try
      {  
         Properties props = new Properties();
         String fileName = "WarehouseClient.properties";
         FileInputStream in = new FileInputStream(fileName);
         props.load(in);
         String url = props.getProperty("warehouse.url");
         if (url == null)
            url = "rmi://localhost/central_warehouse";

         centralWarehouse = (Warehouse)Naming.lookup(url);
      }
      catch(Exception e)
      {  
         System.out.println("Error: Can't connect to warehouse. " + e);
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
         for (int i = 0; i < recommendations.size(); i++)
         {  
            Product p = (Product)recommendations.get(i);
            String t = p.getDescription() + "\n";
            result.append(t);
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
}





