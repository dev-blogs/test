/**
   @version 1.21 2001-07-05
   @author Cay Horstmann
*/

import java.net.*;
import java.sql.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;

/**
   This program uses metadata to display arbitrary tables
   in a database.
*/
public class ViewDB
{  
   public static void main(String[] args)
   {  
      JFrame frame = new ViewDBFrame();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.show();
   }
}

/**
   The frame that holds the data panel and the navigation 
   buttons.
*/
class ViewDBFrame extends JFrame
{
   public ViewDBFrame()
   {  
      setTitle("ViewDB");
      setSize(WIDTH, HEIGHT);

      Container contentPane = getContentPane();

      tableNames = new JComboBox();
      tableNames.addActionListener(new
         ActionListener()
         {
            public void actionPerformed(ActionEvent event)
            {
               showTable((String)tableNames.getSelectedItem());
            }
         });
      contentPane.add(tableNames, BorderLayout.NORTH);

      try
      {  
         conn = getConnection();         
         meta = conn.getMetaData();
         createStatement();
         getTableNames();
      }
      catch(Exception ex)
      {  
         JOptionPane.showMessageDialog(this, ex);
      }

      JPanel buttonPanel = new JPanel();
      contentPane.add(buttonPanel, BorderLayout.SOUTH);

      if (scrolling)
      {
         previousButton = new JButton("Previous");
         previousButton.addActionListener(new
            ActionListener()
            {
               public void actionPerformed(ActionEvent event)
               {
                  showPreviousRow();
               }
            });
         buttonPanel.add(previousButton);
      }

      nextButton = new JButton("Next");
      nextButton.addActionListener(new
         ActionListener()
         {
            public void actionPerformed(ActionEvent event)
            {
               showNextRow();
            }
         });
      buttonPanel.add(nextButton);

      addWindowListener(new
         WindowAdapter()
         {  
            public void windowClosing(WindowEvent e)
            {  
               try
               {  
                  stat.close();
                  conn.close();
               }
               catch(SQLException ex) 
               {
                  while (ex != null)
                  {
                     ex.printStackTrace();
                     ex = ex.getNextException();
                  }
               }
            }
        });
   }

   /**
      Creates the statement object used for executing queries.
      If the database supports scrolling cursors, the statement
      is created to yield them.
   */
   public void createStatement() throws SQLException
   {
      if (meta.supportsResultSetType(
         ResultSet.TYPE_SCROLL_INSENSITIVE))
      {
         stat = conn.createStatement(
            ResultSet.TYPE_SCROLL_INSENSITIVE, 
            ResultSet.CONCUR_READ_ONLY);
         scrolling = true;
      }
      else
      {
         stat = conn.createStatement();
         scrolling = false;
      }
   }
   
   /**
      Gets all table names of this database and adds them
      to the combo box.
   */
   public void getTableNames() throws SQLException
   {
      ResultSet mrs = meta.getTables(null, null, null,
         new String[] { "TABLE" });
      while (mrs.next())
         tableNames.addItem(mrs.getString(3));
      mrs.close();
   }

   /**
      Prepares the text fields for showing a new table, and
      shows the first row.
      @param tableName the name of the table to display
   */
   public void showTable(String tableName)
   {  
      try
      {  
         if (rs != null) rs.close();
         rs = stat.executeQuery("SELECT * FROM " + tableName);
         if (scrollPane != null) 
            getContentPane().remove(scrollPane);
         dataPanel = new DataPanel(rs);
         scrollPane = new JScrollPane(dataPanel);
         getContentPane().add(scrollPane, BorderLayout.CENTER);
         validate();
         showNextRow();
      }
      catch(Exception ex)
      {  
         JOptionPane.showMessageDialog(this, ex);
      }
   }

   /**
      Moves to the previous table row.
   */
   public void showPreviousRow()
   {  
      try
      {  
         if (rs == null || rs.isFirst()) return;
         rs.previous();
         dataPanel.showRow(rs);
      }
      catch(Exception ex)
      {  
         System.out.println("Error " + ex);
      }      
   }

   /**
      Moves to the next table row.
   */
   public void showNextRow()
   {  
      try
      {  
         if (rs == null || scrolling && rs.isLast()) return;

         if (!rs.next() && !scrolling) 
         { 
            rs.close(); 
            rs = null; 
            return; 
         }

         dataPanel.showRow(rs);
      }
      catch(Exception ex)
      {  
         System.out.println("Error " + ex);
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

   public static final int WIDTH = 300;
   public static final int HEIGHT = 200;  

   private JButton previousButton;
   private JButton nextButton;
   private DataPanel dataPanel;
   private Component scrollPane;
   private JComboBox tableNames;

   private Connection conn;
   private Statement stat;
   private DatabaseMetaData meta;
   private ResultSet rs;
   private boolean scrolling;
}

/**
   This panel displays the contents of a result set.
*/
class DataPanel extends JPanel
{
   /**
      Constructs the data panel.
      @param rs the result set whose contents this panel displays
   */
   public DataPanel(ResultSet rs) throws SQLException
   {
      fields = new ArrayList();
      setLayout(new GridBagLayout());
      GridBagConstraints gbc = new GridBagConstraints();
      gbc.weighty = 100;

      ResultSetMetaData rsmd = rs.getMetaData();
      for (int i = 1; i <= rsmd.getColumnCount(); i++)
      {  
         String columnName = rsmd.getColumnLabel(i);
         int columnWidth = rsmd.getColumnDisplaySize(i);
         JTextField tb = new JTextField(columnWidth);
         fields.add(tb);

         gbc.weightx = 0;
         gbc.anchor = GridBagConstraints.EAST;
         gbc.fill = GridBagConstraints.NONE;
         add(new JLabel(columnName), gbc, 0, i - 1, 1, 1);

         gbc.weightx = 100;
         gbc.anchor = GridBagConstraints.WEST;
         gbc.fill = GridBagConstraints.HORIZONTAL;
         add(tb, gbc, 1, i - 1, 1, 1);
      }      
   }

   /**
      Shows a database row by populating all text fields
      with the column values.
   */
   public void showRow(ResultSet rs) throws SQLException
   {  
      for (int i = 1; i <= fields.size(); i++)
      {  
         String field = rs.getString(i);
         JTextField tb
            = (JTextField)fields.get(i - 1);
         tb.setText(field);
      }
   }

   /**
      Adds a component to this panel.
      @param c the component to add
      @param gbc the grid bag constraints
      @param x the grid bax column
      @param y the grid bag row
      @param w the number of grid bag columns spanned
      @param h the number of grid bag rows spanned
   */
   private void add(Component c,
      GridBagConstraints gbc, int x, int y, int w, int h)
   {  
      gbc.gridx = x;
      gbc.gridy = y;
      gbc.gridwidth = w;
      gbc.gridheight = h;
      add(c, gbc);
   }

   private ArrayList fields;
}
