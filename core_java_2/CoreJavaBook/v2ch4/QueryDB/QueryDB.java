/**
   @version 1.21 2001-07-06
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
   This program demonstrates several complex database queries.
*/
public class QueryDB
{  
   public static void main(String[] args)
   {  
      JFrame frame = new QueryDBFrame();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.show();
   }
}

/**
   This frame displays combo boxes for query parameters,
   a text area for command results, and buttons to launch
   a query and an update.
*/
class QueryDBFrame extends JFrame
{  
   public QueryDBFrame()
   {  
      setTitle("QueryDB");
      setSize(WIDTH, HEIGHT);
      getContentPane().setLayout(new GridBagLayout());
      GridBagConstraints gbc = new GridBagConstraints();

      authors = new JComboBox();
      authors.setEditable(false);
      authors.addItem("Any");

      publishers = new JComboBox();
      publishers.setEditable(false);
      publishers.addItem("Any");

      result = new JTextArea(4, 50);
      result.setEditable(false);

      priceChange = new JTextField(8);
      priceChange.setText("-5.00");

      try
      {  
         conn = getConnection();
         stat = conn.createStatement();

         String query = "SELECT Name FROM Authors";
         ResultSet rs = stat.executeQuery(query);
         while (rs.next())
            authors.addItem(rs.getString(1));
         rs.close();

         query = "SELECT Name FROM Publishers";
         rs = stat.executeQuery(query);
         while (rs.next())
            publishers.addItem(rs.getString(1));
         rs.close();
      }
      catch(SQLException ex)
      {  
         result.setText("");
         while (ex != null)
         {
            result.append("" + ex);
            ex = ex.getNextException();
         }
      }
      catch (IOException ex)
      {
         result.setText("" + ex);
      }

      gbc.fill = GridBagConstraints.NONE;
      gbc.weightx = 100;
      gbc.weighty = 100;
      add(authors, gbc, 0, 0, 2, 1);

      add(publishers, gbc, 2, 0, 2, 1);

      gbc.fill = GridBagConstraints.NONE;
      JButton queryButton = new JButton("Query");
      queryButton.addActionListener(new
         ActionListener()
         {
            public void actionPerformed(ActionEvent event)
            {
               executeQuery();
            }
         });
      add(queryButton, gbc, 0, 1, 1, 1);

      JButton changeButton = new JButton("Change prices");
      changeButton.addActionListener(new
         ActionListener()
         {
            public void actionPerformed(ActionEvent event)
            {
               changePrices();
            }
         });
      add(changeButton, gbc, 2, 1, 1, 1);

      gbc.fill = GridBagConstraints.HORIZONTAL;
      add(priceChange, gbc, 3, 1, 1, 1);

      gbc.fill = GridBagConstraints.BOTH;
      add(new JScrollPane(result), gbc, 0, 2, 4, 1);

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
   {  
      gbc.gridx = x;
      gbc.gridy = y;
      gbc.gridwidth = w;
      gbc.gridheight = h;
      getContentPane().add(c, gbc);
   }

   /**
      Executes the selected query.
   */
   private void executeQuery()
   {
      ResultSet rs = null;
      try
      {  
         String author
            = (String)authors.getSelectedItem();
         String publisher
            = (String)publishers.getSelectedItem();
         if (!author.equals("Any")
            && !publisher.equals("Any"))
         {  
            if (authorPublisherQueryStmt == null)
            {  
               String authorPublisherQuery =
"SELECT Books.Price, Books.Title " +
"FROM Books, BooksAuthors, Authors, Publishers " +
"WHERE Authors.Author_Id = BooksAuthors.Author_Id AND " +
"BooksAuthors.ISBN = Books.ISBN AND " +
"Books.Publisher_Id = Publishers.Publisher_Id AND " +
"Authors.Name = ? AND " +
"Publishers.Name = ?";
               authorPublisherQueryStmt
                  = conn.prepareStatement(authorPublisherQuery);
            }
            authorPublisherQueryStmt.setString(1, author);
            authorPublisherQueryStmt.setString(2,
               publisher);
            rs = authorPublisherQueryStmt.executeQuery();
         }
         else if (!author.equals("Any")
            && publisher.equals("Any"))
         {  
            if (authorQueryStmt == null)
            {  
               String authorQuery =
"SELECT Books.Price, Books.Title " +
"FROM Books, BooksAuthors, Authors " +
"WHERE Authors.Author_Id = BooksAuthors.Author_Id AND " +
"BooksAuthors.ISBN = Books.ISBN AND " +
"Authors.Name = ?";
               authorQueryStmt
                  = conn.prepareStatement(authorQuery);
            }
            authorQueryStmt.setString(1, author);
            rs = authorQueryStmt.executeQuery();
         }
         else if (author.equals("Any")
            && !publisher.equals("Any"))
         {  
            if (publisherQueryStmt == null)
            {  
               String publisherQuery =
"SELECT Books.Price, Books.Title " +
"FROM Books, Publishers " +
"WHERE Books.Publisher_Id = Publishers.Publisher_Id AND " +
"Publishers.Name = ?";
               publisherQueryStmt
                  = conn.prepareStatement(publisherQuery);
            }
            publisherQueryStmt.setString(1, publisher);
            rs = publisherQueryStmt.executeQuery();
         }
         else
         {  
            if (allQueryStmt == null)
            {  
               String allQuery =
"SELECT Books.Price, Books.Title FROM Books";
               allQueryStmt
                  = conn.prepareStatement(allQuery);
            }
            rs = allQueryStmt.executeQuery();
         }

         result.setText("");
         while (rs.next())
         {
            result.append(rs.getString(1));
            result.append(", ");
            result.append(rs.getString(2));
            result.append("\n");
         }
         rs.close();
      }
      catch(SQLException ex)
      {
         result.setText("");
         while (ex != null)
         {
            result.append("" + ex);
            ex = ex.getNextException();
         }
      }
   }

   /**
      Executes an update statement to change prices.
   */
   public void changePrices()
   {
      String publisher
         = (String)publishers.getSelectedItem();
      if (publisher.equals("Any"))
      {
         result.setText
            ("I am sorry, but I cannot do that.");
         return;
      }
      try
      {  
         String updateStatement =
"UPDATE Books " +
"SET Price = Price + " + priceChange.getText() +
" WHERE Books.Publisher_Id = " +
"(SELECT Publisher_Id FROM Publishers WHERE Name = '" +
            publisher + "')";
         int r = stat.executeUpdate(updateStatement);
         result.setText(r + " records updated.");
      }     
      catch(SQLException ex)
      {
         result.setText("");
         while (ex != null)
         {
            result.append("" + ex);
            ex = ex.getNextException();
         }
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

   public static final int WIDTH = 400;
   public static final int HEIGHT = 400;  
  
   private JComboBox authors;
   private JComboBox publishers;
   private JTextField priceChange;
   private JTextArea result;
   private Connection conn;
   private Statement stat;
   private PreparedStatement authorQueryStmt;
   private PreparedStatement authorPublisherQueryStmt;
   private PreparedStatement publisherQueryStmt;
   private PreparedStatement allQueryStmt;
}
