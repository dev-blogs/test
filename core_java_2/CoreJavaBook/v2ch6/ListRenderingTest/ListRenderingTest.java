/**
   @version 1.21 2001-07-16
   @author Cay Horstmann
*/

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

/**
   This program demonstrates the use of cell renderers in
   a list box.
*/
public class ListRenderingTest
{  
   public static void main(String[] args)
   {  
      JFrame frame = new ListRenderingFrame();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.show();
   }
}

/**
   This frame contains a list with a set of fonts and a text
   area that is set to the selected font.
*/
class ListRenderingFrame extends JFrame
{  
   public ListRenderingFrame()
   {  
      setTitle("ListRenderingTest");
      setSize(WIDTH, HEIGHT);

      ArrayList fonts = new ArrayList();
      final int SIZE = 24;
      fonts.add(new Font("Serif", Font.PLAIN, SIZE));
      fonts.add(new Font("SansSerif", Font.PLAIN, SIZE));
      fonts.add(new Font("Monospaced", Font.PLAIN, SIZE));
      fonts.add(new Font("Dialog", Font.PLAIN, SIZE));
      fonts.add(new Font("DialogInput", Font.PLAIN, SIZE));
      fontList = new JList(fonts.toArray());
      fontList.setVisibleRowCount(4);
      fontList.setSelectionMode
         (ListSelectionModel.SINGLE_SELECTION);
      fontList.setCellRenderer(new FontCellRenderer());
      JScrollPane scrollPane = new JScrollPane(fontList);

      JPanel p = new JPanel();
      p.add(scrollPane);
      fontList.addListSelectionListener(new
         ListSelectionListener()
         {
            public void valueChanged(ListSelectionEvent evt)
            {  
               Font font = (Font)fontList.getSelectedValue();
               text.setFont(font);
            }

         });

      Container contentPane = getContentPane();
      contentPane.add(p, BorderLayout.SOUTH);
      text = new JTextArea(
         "The quick brown fox jumps over the lazy dog");
      text.setFont((Font)fonts.get(0));
      text.setLineWrap(true);
      text.setWrapStyleWord(true);
      contentPane.add(text, BorderLayout.CENTER);
   }

   private JTextArea text;
   private JList fontList;
   private static final int WIDTH = 400;
   private static final int HEIGHT = 300;
}

/**
   A cell renderer for Font objects that renders the font name
   in its own font.
*/
class FontCellRenderer implements ListCellRenderer
{  
   public Component getListCellRendererComponent
      (final JList list, final Object value,
      final int index, final boolean isSelected,
      final boolean cellHasFocus)
   {  
      return new 
         JPanel()
         {  
            public void paintComponent(Graphics g)
            {  
               Font font = (Font)value;
               String text = font.getFamily();
               FontMetrics fm = g.getFontMetrics(font);
               g.setColor(isSelected
                  ? list.getSelectionBackground()
                  : list.getBackground());
               g.fillRect(0, 0, getWidth(), getHeight());
               g.setColor(isSelected
                  ? list.getSelectionForeground()
                  : list.getForeground());
               g.setFont(font);
               g.drawString(text, 0, fm.getAscent());
            }

            public Dimension getPreferredSize()
            {  
               Font font = (Font)value;
               String text = font.getFamily();
               Graphics g = getGraphics();
               FontMetrics fm = g.getFontMetrics(font);
               return new Dimension(fm.stringWidth(text),
                  fm.getHeight());
            }
         };
   }
}

