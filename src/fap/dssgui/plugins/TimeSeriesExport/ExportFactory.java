package fap.dssgui.plugins.TimeSeriesExport;

import hec.heclib.util.HecTime;
import hec.io.TimeSeriesContainer;
import java.util.List;

/**
 *
 * @author HOLLEBRANDSEF
 */
public class ExportFactory {

    //private List[] dataList; //list of datacontainers
    private TimeSeriesContainer[] timeSeriesContainers; //array of timeseriescontainers
    private long totalRecordCount,  totalRecordsFinished;
    private HecTime selectionStartTime,  selectionEndTime;
    private String exportFolder;
    private int transformIndex;

    public ExportFactory() {
    }

    public ExportFactory(List[] dataList,
            HecTime selectionStartTime,
            HecTime selectionEndTime,
            String exportFolder, int transformIndex) {

        this.timeSeriesContainers = new TimeSeriesContainer[dataList[0].size()];
        for (int tscIndex = 0; tscIndex < dataList[0].size(); tscIndex++) {
            this.timeSeriesContainers[tscIndex] =
                    (TimeSeriesContainer) dataList[0].get(tscIndex);
        }
        this.totalRecordCount = calculateTotalRecordCount();
        this.selectionStartTime = selectionStartTime;
        this.selectionEndTime = selectionEndTime;
        this.exportFolder = exportFolder;
        this.transformIndex = transformIndex;
    }

    public ExportFactory(TimeSeriesContainer[] timeSeriesContainers,
            HecTime selectionStartTime,
            HecTime selectionEndTime,
            String exportFolder, int transformIndex) {
        this.timeSeriesContainers = timeSeriesContainers;
        this.totalRecordCount = calculateTotalRecordCount();
        this.selectionStartTime = selectionStartTime;
        this.selectionEndTime = selectionEndTime;
        this.exportFolder = exportFolder;
        this.transformIndex = transformIndex;
    }

    public void run() {
        int tscCount, tscIndex;
        ExportSet exportSet;

        tscCount = timeSeriesContainers.length;
        for (tscIndex = 0; tscIndex < tscCount; tscIndex++) {
            exportSet = new ExportSet(this);
            exportSet.setTimeSeriesContainer(timeSeriesContainers[tscIndex]);
            if (tscIndex == 0) {
                exportSet.setStartNewFile(true);
            }
            exportSet.export();
        }
        setProgress(100);
    }

    private long calculateTotalRecordCount() {
        long result = 0;

        for (int tscIndex = 0; tscIndex < timeSeriesContainers.length; tscIndex++) {
            result += timeSeriesContainers[tscIndex].numberValues;
        }
        return result;
    }

    public TimeSeriesContainer[] getTimeSeriesContainers() {
        return timeSeriesContainers;
    }

    public void setTimeSeriesContainers(TimeSeriesContainer[] timeSeriesContainers) {
        this.timeSeriesContainers = timeSeriesContainers;
        this.totalRecordCount = calculateTotalRecordCount();

    }

    public void setTimeSeriesContainers(List[] dataList) {
        this.timeSeriesContainers = new TimeSeriesContainer[dataList[0].size()];
        for (int tscIndex = 0; tscIndex < dataList[0].size(); tscIndex++) {
            this.timeSeriesContainers[tscIndex] =
                    (TimeSeriesContainer) dataList[0].get(tscIndex);
        }
        this.totalRecordCount = calculateTotalRecordCount();

    }

    public long getTotalRecordCount() {
        return totalRecordCount;
    }

    public HecTime getSelectionEndTime() {
        return selectionEndTime;
    }

    public void setSelectionEndTime(HecTime selectionEndTime) {
        this.selectionEndTime = selectionEndTime;
    }

    public String getExportFolder() {
        return exportFolder;
    }

    public void setExportFolder(String exportFolder) {
        this.exportFolder = exportFolder;
    }

    public HecTime getSelectionStartTime() {
        return selectionStartTime;
    }

    public void setSelectionStartTime(HecTime selectionStartTime) {
        this.selectionStartTime = selectionStartTime;
    }

    public int getTransformIndex() {
        return transformIndex;
    }

    public void setTransformIndex(int transformIndex) {
        this.transformIndex = transformIndex;
    }

    public long getTotalRecordsFinished() {
        return totalRecordsFinished;
    }

    public void increaseTotalRecordsFinished() {
        this.totalRecordsFinished += 1;

        if (((totalRecordsFinished) % (Math.floor((totalRecordCount / 98F))) == 0) || (totalRecordsFinished == totalRecordCount)) {
            setProgress((int) ((totalRecordsFinished + 0F) / totalRecordCount * 98F));
        }
    }

    private int progress;
    private java.beans.PropertyChangeSupport propertyChangeSupport = new java.beans.PropertyChangeSupport(this);

    public void addPropertyChangeListener(java.beans.PropertyChangeListener l) {
        propertyChangeSupport.addPropertyChangeListener(l);
    }

    public void removePropertyChangeListener(java.beans.PropertyChangeListener l) {
        propertyChangeSupport.removePropertyChangeListener(l);
    }

    public int getProgress() {
        return this.progress;
    }

    public void setProgress(int progress) {
        int oldProgress = this.progress;
        this.progress = progress;
        propertyChangeSupport.firePropertyChange("progress", new Integer(oldProgress), new Integer(progress));
    }
}
