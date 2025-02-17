package uk.ac.aber.cs221.group5.gui;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import uk.ac.aber.cs221.group5.logic.Task;
import uk.ac.aber.cs221.group5.logic.Task.Element;

/**
 * This Class handles the GUI Window for viewing Task Elements. It spawns a GIU
 * Window and loads Element Data into the Table contained within the Window.
 * 
 * @author Ben Dudley (bed19)
 * @author David Fairbrother (daf5)
 * @author Jonathan Englund (jee17)
 * @author Josh Doyle (jod32)
 * 
 * @version 1.0.0
 * @since 1.0.0
 * @see MainWindow.java
 */

public class ViewElementsWindowGUI {

   private JFrame frmViewTaskElements;
   private JTable table;
   private int selectedRow;

   private static final String TASK_SAVE_PATH = "taskSaveFile.txt";

   /**
    * Create the application.
    * 
    * @param tableRow
    *           the current task in the table
    * 
    * @throws IOException
    *            if the local Tasks save file cannot be read.
    */
   public ViewElementsWindowGUI(int tableRow) throws IOException {
      this.selectedRow = tableRow;
      initialize();
   }

   /**
    * Initialize the contents of the Frame and display the Frame.
    * 
    * @throws IOException
    *            if the local Tasks save file cannot be read.
    */
   private void initialize() throws IOException {
      frmViewTaskElements = new JFrame();
      frmViewTaskElements.setTitle("View Task Elements");
      frmViewTaskElements.setBounds(100, 100, 540, 647);
      frmViewTaskElements.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
      GridBagLayout gridBagLayout = new GridBagLayout();
      gridBagLayout.columnWidths = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
      gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
      gridBagLayout.columnWeights = new double[] { 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
      gridBagLayout.rowWeights = new double[] { 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
            0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
      frmViewTaskElements.getContentPane().setLayout(gridBagLayout);

      JScrollPane scrollPane = new JScrollPane();
      GridBagConstraints gbc_scrollPane = new GridBagConstraints();
      gbc_scrollPane.gridwidth = 9;
      gbc_scrollPane.gridheight = 18;
      gbc_scrollPane.insets = new Insets(0, 0, 5, 0);
      gbc_scrollPane.fill = GridBagConstraints.BOTH;
      gbc_scrollPane.gridx = 0;
      gbc_scrollPane.gridy = 0;
      frmViewTaskElements.getContentPane().add(scrollPane, gbc_scrollPane);

      table = new JTable();
      table.setFont(new Font("Tahoma", Font.PLAIN, 18));
      table.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "Element Name", "Element Comment" }) {
         Class[] columnTypes = new Class[] { String.class, String.class };

         public Class getColumnClass(int columnIndex) {
            return columnTypes[columnIndex];
         }
      });
      scrollPane.setViewportView(table);

      JButton btnClose = new JButton("Close");
      btnClose.setFont(new Font("Tahoma", Font.PLAIN, 18));
      btnClose.addMouseListener(new MouseAdapter() {
         @Override
         public void mouseClicked(MouseEvent e) {
            frmViewTaskElements.dispose();
         }
      });
      GridBagConstraints gbc_btnClose = new GridBagConstraints();
      gbc_btnClose.gridwidth = 9;
      gbc_btnClose.insets = new Insets(0, 0, 0, 5);
      gbc_btnClose.gridx = 0;
      gbc_btnClose.gridy = 18;
      frmViewTaskElements.getContentPane().add(btnClose, gbc_btnClose);
      this.populateTable(selectedRow);
      frmViewTaskElements.setVisible(true);
   }

   /**
    * Displays Task Element data in the Table contained within the Frame.
    * 
    * @param tableIndex
    *           The position in the TaskList of the Task that was selected to
    *           view Elements
    * @throws IOException
    *            if the local Tasks save file cannot be read.
    */
   private void populateTable(int tableIndex) throws IOException {
      MainWindow main = new MainWindow(); // Used for loading elements and will
                                          // not spawn a GUI

      try {
         main.loadTasks(TASK_SAVE_PATH);
      } catch (Exception e) {
         main.updateLocalFiles(TASK_SAVE_PATH);
         try {
            main.loadTasks(TASK_SAVE_PATH);
         } catch (Exception e1) {

            main.displayError("Could not show local tasks", "Error Loading");
         }
      }

      Task displayTask = main.getTaskList().getTask(selectedRow);

      DefaultTableModel model = (DefaultTableModel) (table.getModel());
      model.setRowCount(0);

      for (int tableRow = 0; tableRow < displayTask.getNumElements(); tableRow++) {
         Element displayElement = displayTask.getElement(tableRow);
         model.addRow(new Object[] { displayElement.getName(), displayElement.getComment() });
      }
      table.setVisible(true);
   }
}
