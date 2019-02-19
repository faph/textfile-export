package fap.dssgui.plugins.TimeSeriesExport;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import hec.dssgui.ListSelection;
import rma.awt.FlatPanelButton;

public class ControlPlugin {

    private static final String RUN_BUTTON_CAPTION = "Export time series...";
    private static final String NO_DSS_WARNING = "Please open a HEC-DSS database file (.dss) first.";
    private static final String NO_TS_WARNING = "Please select one or more time series datasets first.";
    private static final String APP_TITLE = "HEC-DSSVue";
    private static ListSelection _listSelection;

    public static void main(Object[] args) {
        if (args.length > 0 && args[0] instanceof ListSelection) {
            _listSelection = (ListSelection) args[0];
        }

        final ListSelection listSelection = (ListSelection) args[0];
        FlatPanelButton exportButton = new FlatPanelButton(RUN_BUTTON_CAPTION);
        exportButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                String currentDirectory = listSelection.getDSSFilename();
                if (currentDirectory == null || currentDirectory.length() == 0) {
                    JOptionPane.showMessageDialog(listSelection, NO_DSS_WARNING,
                            APP_TITLE, JOptionPane.WARNING_MESSAGE);
                    return;
                }

                Object dataSet = listSelection.getSelectedPathnames(); //is a DataReferenceSet, but cannot be declared (private inner class)
                if (dataSet.toString().length() < 3) {
                    JOptionPane.showMessageDialog(listSelection, NO_TS_WARNING,
                            APP_TITLE, JOptionPane.WARNING_MESSAGE);
                    return;
                }

                ExportDialog dialog = new ExportDialog(_listSelection, true);
                dialog.setLocationRelativeTo(listSelection);
                dialog.setVisible(true);

            }
        });
        listSelection.getToolBar().add(exportButton);
    }
}
