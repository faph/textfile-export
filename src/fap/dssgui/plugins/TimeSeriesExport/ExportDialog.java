package fap.dssgui.plugins.TimeSeriesExport;

import hec.dssgui.ListSelection;
import hec.heclib.util.HecTime;
import hec.lang.DSSPathString;
import java.awt.Cursor;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

public class ExportDialog extends javax.swing.JDialog implements PropertyChangeListener {

    private static final String TRANSFORMS_DEF_FILE
            = java.util.ResourceBundle.getBundle("fap/dssgui/plugins/TimeSeriesExport/settings").getString("transformDefinitionsFile");
    //messages
    private static final String NO_TS_SELECTED_WARNING = "Please select one or more time series datasets first.";
    private static final String NO_TS_FOUND_WARNING = "No time series datasets could be found.\n"
            + "Please check the data selection and the time window.";
    private static final String APP_TITLE = "HEC-DSSVue";
    private static final String FOLDER_DIALOG_TITLE = "Select an export folder";
    //fields
    private static ListSelection listSelection;
    private HistoryHandler foldersHistoryHandler;

    public ExportDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        listSelection = (ListSelection) parent;
        initComponents();
        populateComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jScrollPane2 = new javax.swing.JScrollPane();
        dataSetsTable = new javax.swing.JTable();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel3 = new javax.swing.JLabel();
        startDateText = new javax.swing.JTextField();
        endDateText = new javax.swing.JTextField();
        endTimeText = new javax.swing.JTextField();
        startTimeText = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        browseButton = new javax.swing.JButton();
        folderCombo = new javax.swing.JComboBox();
        jLabel7 = new javax.swing.JLabel();
        exportTypeCombo = new javax.swing.JComboBox();
        exportProgress = new javax.swing.JProgressBar();
        jLabel1 = new javax.swing.JLabel();
        buttonsPanel = new javax.swing.JPanel();
        okButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Export timeseries");
        setResizable(false);
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jScrollPane2.setPreferredSize(new java.awt.Dimension(600, 200));

        dataSetsTable.setModel(new TimeSeriesTableModel());
        jScrollPane2.setViewportView(dataSetsTable);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(15, 10, 5, 10);
        getContentPane().add(jScrollPane2, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 10, 0);
        getContentPane().add(jSeparator1, gridBagConstraints);

        jLabel3.setText("End date:");
        jLabel3.setMaximumSize(new java.awt.Dimension(80, 14));
        jLabel3.setMinimumSize(new java.awt.Dimension(80, 14));
        jLabel3.setPreferredSize(new java.awt.Dimension(80, 14));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 5, 0);
        getContentPane().add(jLabel3, gridBagConstraints);

        startDateText.setEditable(false);
        startDateText.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        startDateText.setText("31DEC1999");
        startDateText.setMinimumSize(new java.awt.Dimension(100, 20));
        startDateText.setPreferredSize(new java.awt.Dimension(100, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 3, 0);
        getContentPane().add(startDateText, gridBagConstraints);

        endDateText.setEditable(false);
        endDateText.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        endDateText.setText("31DEC1999");
        endDateText.setMinimumSize(new java.awt.Dimension(100, 20));
        endDateText.setPreferredSize(new java.awt.Dimension(100, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 3, 0);
        getContentPane().add(endDateText, gridBagConstraints);

        endTimeText.setEditable(false);
        endTimeText.setText("23:45");
        endTimeText.setMinimumSize(new java.awt.Dimension(100, 20));
        endTimeText.setPreferredSize(new java.awt.Dimension(100, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 3, 0);
        getContentPane().add(endTimeText, gridBagConstraints);

        startTimeText.setEditable(false);
        startTimeText.setText("23:45");
        startTimeText.setMinimumSize(new java.awt.Dimension(100, 20));
        startTimeText.setPreferredSize(new java.awt.Dimension(100, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 3, 0);
        getContentPane().add(startTimeText, gridBagConstraints);

        jLabel5.setText("Time:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 5, 0);
        getContentPane().add(jLabel5, gridBagConstraints);

        jLabel4.setText("Time:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 5, 0);
        getContentPane().add(jLabel4, gridBagConstraints);

        jLabel2.setText("Start date:");
        jLabel2.setPreferredSize(new java.awt.Dimension(80, 14));
        jLabel2.setVerifyInputWhenFocusTarget(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 5, 0);
        getContentPane().add(jLabel2, gridBagConstraints);

        jLabel6.setText("Progress:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 0, 0);
        getContentPane().add(jLabel6, gridBagConstraints);

        browseButton.setText("...");
        browseButton.setPreferredSize(new java.awt.Dimension(23, 23));
        browseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                browseButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 1, 10);
        getContentPane().add(browseButton, gridBagConstraints);

        folderCombo.setEditable(true);
        folderCombo.setMaximumRowCount(10);
        folderCombo.setLightWeightPopupEnabled(false);
        folderCombo.setMaximumSize(new java.awt.Dimension(800, 50));
        folderCombo.setMinimumSize(new java.awt.Dimension(200, 20));
        folderCombo.setOpaque(false);
        folderCombo.setPreferredSize(new java.awt.Dimension(300, 22));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 1, 40);
        getContentPane().add(folderCombo, gridBagConstraints);

        jLabel7.setText("Format:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 5, 0);
        getContentPane().add(jLabel7, gridBagConstraints);

        exportTypeCombo.setPreferredSize(new java.awt.Dimension(80, 22));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 1, 0);
        getContentPane().add(exportTypeCombo, gridBagConstraints);

        exportProgress.setFocusable(false);
        exportProgress.setMaximumSize(new java.awt.Dimension(800, 21));
        exportProgress.setStringPainted(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 5, 10);
        getContentPane().add(exportProgress, gridBagConstraints);

        jLabel1.setText("Export to folder:");
        jLabel1.setMinimumSize(new java.awt.Dimension(20, 14));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 5, 0);
        getContentPane().add(jLabel1, gridBagConstraints);

        buttonsPanel.setLayout(new java.awt.GridBagLayout());

        okButton.setText("Export");
        okButton.setMinimumSize(new java.awt.Dimension(80, 23));
        okButton.setPreferredSize(new java.awt.Dimension(80, 23));
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHEAST;
        buttonsPanel.add(okButton, gridBagConstraints);

        cancelButton.setText("Close");
        cancelButton.setMinimumSize(new java.awt.Dimension(80, 23));
        cancelButton.setPreferredSize(new java.awt.Dimension(80, 23));
        cancelButton.setSelected(true);
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        buttonsPanel.add(cancelButton, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(20, 0, 10, 10);
        getContentPane().add(buttonsPanel, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public void propertyChange(PropertyChangeEvent propertyChangeEvent) {
        if ("progress" == propertyChangeEvent.getPropertyName()) {
            Integer prog = (Integer) propertyChangeEvent.getNewValue();
            exportProgress.setValue(prog.intValue());
        }
    }

    private void populateComponents() {
        HecTime timeStart = listSelection.getStartTime();
        HecTime timeEnd = listSelection.getEndTime();
        startDateText.setText(timeStart.date(8));
        endDateText.setText(timeEnd.date(8));
        startTimeText.setText(timeStart.time());
        endTimeText.setText(timeEnd.time());

        foldersHistoryHandler = new HistoryHandler(folderCombo, "exportFolders");

        XmlHandler formatsXmlHandler = new XmlHandler(TRANSFORMS_DEF_FILE);
        formatsXmlHandler.pushToCombo("/transforms/transform[*]/name", exportTypeCombo);
        if (exportTypeCombo.getItemCount() > 0) {
            int exportTypeDefault = PreferencesHandler.getPreferenceInt("exportTypeDefault");
            exportTypeCombo.setSelectedIndex(exportTypeDefault);
        } else {
            okButton.setEnabled(false);
        }
    }

   private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okButtonActionPerformed
       okButton.setEnabled(false);
       setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
       exportProgress.setValue(0);
       exportProgress.setIndeterminate(true);
       saveSettings();

       final ExportFactory exportFactory = new ExportFactory();
       exportFactory.addPropertyChangeListener(this);

       final SwingWorker worker = new SwingWorker() {

           @Override
           public Object construct() {

               final List[] dataList = listSelection.getSelectedDataContainers();
               if (dataList == null) {
                   JOptionPane.showMessageDialog(listSelection, NO_TS_SELECTED_WARNING,
                           APP_TITLE, JOptionPane.WARNING_MESSAGE);
                   return null;
               }
               // The first list contains a list of TimeSeriesContainers
               // The second is PairedDataContainers, third is text, fourth is gridded
               if ((dataList[0] == null) || (dataList[0].isEmpty())) {
                   JOptionPane.showMessageDialog(listSelection, NO_TS_FOUND_WARNING,
                           APP_TITLE, JOptionPane.WARNING_MESSAGE);
               } else {

                   exportProgress.setIndeterminate(false);

                   //set up the export factory
                   exportFactory.setTimeSeriesContainers(dataList);
                   exportFactory.setSelectionStartTime(listSelection.getStartTime());
                   exportFactory.setSelectionEndTime(listSelection.getEndTime());
                   exportFactory.setExportFolder(foldersHistoryHandler.getItem(folderCombo.getSelectedIndex()));
                   exportFactory.setTransformIndex(exportTypeCombo.getSelectedIndex());
                   //and run the export factory!
                   exportFactory.run();
               }

               okButton.setEnabled(true);
               setCursor(null);

               return null;
           }
       };
       worker.start();

   }//GEN-LAST:event_okButtonActionPerformed

    class TimeSeriesTableModel extends AbstractTableModel {

        private String[] columnNames = {"A part", "B Part", "C part", "D part / range", "F part"};
        private Object[][] data = getDataSetsTable();

        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        @Override
        public int getRowCount() {
            return data.length;
        }

        @Override
        public String getColumnName(int col) {
            return columnNames[col];
        }

        @Override
        public Object getValueAt(int row, int col) {
            return data[row][col];
        }

        @Override
        public Class getColumnClass(int c) {
            return getValueAt(0, c).getClass();
        }
    }

    private Object[][] getDataSetsTable() {
        int i;

        Object dataSet = listSelection.getSelectedPathnames(); //is a DataReferenceSet, but cannot be declared (private inner class)
        String dataPath = dataSet.toString();
        String dataPaths[] = dataPath.substring(1, dataPath.length() - 1).split(",");
        Object result[][];
        result = new Object[dataPaths.length][5];
        for (i = 0; i < dataPaths.length; i++) {
            DSSPathString DSSdataPath = new DSSPathString(dataPaths[i]);
            result[i][0] = DSSdataPath.getAPart();
            result[i][1] = DSSdataPath.getBPart();
            result[i][2] = DSSdataPath.getCPart();
            result[i][3] = DSSdataPath.getDPart();
            result[i][4] = DSSdataPath.getFPart();
        }
        return result;
    }

   private void browseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_browseButtonActionPerformed
       JFileChooser chooser = new JFileChooser();
       try {
           File f = new File(new File(".").getCanonicalPath());
           chooser.setCurrentDirectory(f);
       } catch (IOException e) {
       }
       chooser.setDialogTitle(FOLDER_DIALOG_TITLE);
       chooser.setFileSelectionMode(javax.swing.JFileChooser.DIRECTORIES_ONLY);
       int result = chooser.showOpenDialog(listSelection);
       switch (result) {
           case JFileChooser.APPROVE_OPTION:
               File file = chooser.getSelectedFile();
               foldersHistoryHandler.addItem(file.getPath());
               break;
           case JFileChooser.CANCEL_OPTION:
               break;
           case JFileChooser.ERROR_OPTION:
               break;
       }
   }//GEN-LAST:event_browseButtonActionPerformed

   private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
       this.dispose();
   }//GEN-LAST:event_cancelButtonActionPerformed

    private void saveSettings() {
        //export format
        PreferencesHandler.setPreference("exportTypeDefault", exportTypeCombo.getSelectedIndex());
        //export folder
        foldersHistoryHandler.moveToTop(folderCombo.getSelectedIndex());
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new ExportDialog(listSelection, true).setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton browseButton;
    private javax.swing.JPanel buttonsPanel;
    private javax.swing.JButton cancelButton;
    private javax.swing.JTable dataSetsTable;
    private javax.swing.JTextField endDateText;
    private javax.swing.JTextField endTimeText;
    private javax.swing.JProgressBar exportProgress;
    private javax.swing.JComboBox exportTypeCombo;
    private javax.swing.JComboBox folderCombo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JButton okButton;
    private javax.swing.JTextField startDateText;
    private javax.swing.JTextField startTimeText;
    // End of variables declaration//GEN-END:variables
}