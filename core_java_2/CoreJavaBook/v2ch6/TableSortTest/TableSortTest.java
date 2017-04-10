/**
   @version 1.00 2001-07-18
   @author Cay Horstmann
*/

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;

/**
   This program demonstrates how to sort a table column.
   Double-click on a table columm to sort it.
*/
public class TableSortTest
{  
   public static void main(String[] args)
   {  
      JFrame frame = new TableSortFrame();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.show();
   }
}

/**
   This frame contains a table of planet data.
*/
class TableSortFrame extends JFrame
{  
   public TableSortFrame()
   {  
      setTitle("TableSortTest");
      setSize(WIDTH, HEIGHT);

      // set up table model and interpose sorter

      DefaultTableModel model
         = new DefaultTableModel(cells, columnNames);
      final SortFilterModel sorter = new SortFilterModel(model);

      // show table

      final JTable table = new JTable(sorter);
      getContentPane().add(new JScrollPane(table), 
         BorderLayout.CENTER);

      // set up double click handler for column headers

      table.getTableHeader().addMouseListener(new 
         MouseAdapter()
         {  
            public void mouseClicked(MouseEvent event)
            {  
               // check for double click
               if (event.getClickCount() < 2) return;

               // find column of click and
               int tableColumn
                  = table.columnAtPoint(event.getPoint());

               // translate to table model index and sort
               int modelColumn
                  = table.convertColumnIndexToModel(tableColumn);
               sorter.sort(modelColumn);
            }
         });
   }

   private Object[][] cells =
   {  
      { 
         "Mercury", new Double(2440),  new Integer(0),
         Boolean.FALSE, Color.yellow
      },
         
      { 
         "Venus", new Double(6052), new Integer(0),
         Boolean.FALSE, Color.yellow
      },
      { 
         "Earth", new Double(6378), new Integer(1),
         Boolean.FALSE, Color.blue
      },
      { 
         "Mars", new Double(3397), new Integer(2),
         Boolean.FALSE, Color.red
      },
      { 
         "Jupiter", new Double(71492), new Integer(16),
         Boolean.TRUE, Color.orange
      },
      { 
         "Saturn", new Double(60268), new Integer(18),
         Boolean.TRUE, Color.orange
      },
      { 
         "Uranus", new Double(25559), new Integer(17),
         Boolean.TRUE, Color.blue
      },
      { 
         "Neptune", new Double(24766), new Integer(8),
         Boolean.TRUE, Color.blue
      },
      { 
         "Pluto", new Double(1137), new Integer(1),
         Boolean.FALSE, Color.black
      }
   };

   private String[] columnNames =
   {  
      "Planet", "Radius", "Moons", "Gaseous", "Color"
   };

   private static final int WIDTH = 400;
   private static final int HEIGHT = 200;
}

/**
   This table model takes an existing table model and produces
   a new model that sorts the rows so that the entries in
   a particular column are sorted.
*/
class SortFilterModel extends AbstractTableModel
{
   /**
      Constructs a sort filter model.
      @param m the table model to filter
   */
   public SortFilterModel(TableModel m)
   {  
      model = m;
      rows = new Row[model.getRowCount()];
      for (int i = 0; i < rows.length; i++)
      {  
         rows[i] = new Row();
         rows[i].index = i;
      }
   }

   /**
      Sorts the rows.
      @param c the column that should become sorted
   */
   public void sort(int c)
   {  
      sortColumn = c;
      Arrays.sort(rows);
      fireTableDataChanged();
   }

   // Compute the moved row for the three methods that access
   // model elements

   public Object getValueAt(int r, int c)
   {  
      return model.getValueAt(rows[r].index, c);
   }

   public boolean isCellEditable(int r, int c)
   {  
      return model.isCellEditable(rows[r].index, c);
   }

   public void setValueAt(Object aValue, int r, int c)
   {  
      model.setValueAt(aValue, rows[r].index, c);
   }

   // delegate all remaining methods to the model

   public int getRowCount()
   {  
      return model.getRowCount();
   }

   public int getColumnCount()
   {  
      return model.getColumnCount();
   }

   public String getColumnName(int c)
   {  
      return model.getColumnName(c);
   }

   public Class getColumnClass(int c)
   {  
      return model.getColumnClass(c);
   }

   /** 
      This inner class holds the index of the model row
      Rows are compared by looking at the model row entries
      in the sort column.
   */
   private class Row implements Comparable
   {  
      public int index;
      public int compareTo(Object other)
      {  
         Row otherRow = (Row)other;
         Object a = model.getValueAt(index, sortColumn);
         Object b = model.getValueAt(otherRow.index, sortColumn);
         if (a instanceof Comparable)
            return ((Comparable)a).compareTo(b);
         else
            return a.toString().compareTo(b.toString());

            //            return index - otherRow.index;
      }
   }

   private TableModel model;
   private int sortColumn;
   private Row[] rows;
}

