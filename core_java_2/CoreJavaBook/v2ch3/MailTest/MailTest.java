 /**
   @version 1.01 2001-06-26
   @author Cay Horstmann
*/

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.net.*;
import java.io.*;
import javax.swing.*;

/**
   This program shows how to use sockets to send plain text
   mail messages.
*/
public class MailTest
{  
   public static void main(String[] args)
   {  
      JFrame frame = new MailTestFrame();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.show();
   }
}

/**
   The frame for the mail GUI.
*/
class MailTestFrame extends JFrame
{  
   public MailTestFrame()
   {  
      setSize(WIDTH, HEIGHT);
      setTitle("MailTest");

      getContentPane().setLayout(new GridBagLayout());

      GridBagConstraints gbc = new GridBagConstraints();
      gbc.fill = GridBagConstraints.HORIZONTAL;
      gbc.weightx = 0;
      gbc.weighty = 0;

      gbc.weightx = 0;
      add(new JLabel("From:"), gbc, 0, 0, 1, 1);
      gbc.weightx = 100;
      from = new JTextField(20);
      add(from, gbc, 1, 0, 1, 1);

      gbc.weightx = 0;
      add(new JLabel("To:"), gbc, 0, 1, 1, 1);
      gbc.weightx = 100;
      to = new JTextField(20);
      add(to, gbc, 1, 1, 1, 1);

      gbc.weightx = 0;
      add(new JLabel("SMTP server:"), gbc, 0, 2, 1, 1);
      gbc.weightx = 100;
      smtpServer = new JTextField(20);
      add(smtpServer, gbc, 1, 2, 1, 1);
 
      gbc.fill = GridBagConstraints.BOTH;
      gbc.weighty = 100;
      message = new JTextArea();
      add(new JScrollPane(message), gbc, 0, 3, 2, 1);

      communication = new JTextArea();
      add(new JScrollPane(communication), gbc, 0, 4, 2, 1);

      gbc.weighty = 0;
      JButton sendButton = new JButton("Send");
      sendButton.addActionListener(new
         ActionListener()
         {
            public void actionPerformed(ActionEvent evt)
            {  
               new 
                  Thread()
                  {
                     public void run()
                     {   
                        sendMail();
                     }
                  }.start();
            }            
         });
      JPanel buttonPanel = new JPanel();
      buttonPanel.add(sendButton);
      add(buttonPanel, gbc, 0, 5, 2, 1);
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
      Sends the mail message that has been authored in the GUI.
   */
   public void sendMail()
   {  
      try
      {  
         Socket s = new Socket(smtpServer.getText(), 25);

         out = new PrintWriter(s.getOutputStream());
         in = new BufferedReader(new
            InputStreamReader(s.getInputStream()));

         String hostName
            = InetAddress.getLocalHost().getHostName();

         receive();
         send("HELO " + hostName);
         receive();
         send("MAIL FROM: <" + from.getText() + ">");
         receive();
         send("RCPT TO: <" + to.getText() + ">");
         receive();
         send("DATA");
         receive();
         StringTokenizer tokenizer = new StringTokenizer(
            message.getText(), "\n");
         while (tokenizer.hasMoreTokens())
            send(tokenizer.nextToken());
         send(".");
         receive();
         s.close();
      }
      catch (IOException exception)
      {  
         communication.append("Error: " + exception);
      }
   }

   /**
      Sends a string to the socket and echoes it in the
      communication text area.
      @param s the string to send.
   */
   public void send(String s) throws IOException
   {  
      communication.append(s);
      communication.append("\n");
      out.print(s);
      out.print("\r\n");
      out.flush();
   }

   /**
      Receives a string from the socket and displays it
      in the communication text area.
   */
   public void receive() throws IOException
   {
      String line = in.readLine();
      if (line != null)
      {
         communication.append(line);
         communication.append("\n");
      }
   }

   private BufferedReader in;
   private PrintWriter out;
   private JTextField from;
   private JTextField to;
   private JTextField smtpServer;
   private JTextArea message;
   private JTextArea communication;

   public static final int WIDTH = 300;
   public static final int HEIGHT = 300;  
}

