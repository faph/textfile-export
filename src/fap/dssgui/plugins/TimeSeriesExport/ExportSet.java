package fap.dssgui.plugins.TimeSeriesExport;

import hec.heclib.util.HecTime;
import hec.hecmath.HecMath;
import hec.io.TimeSeriesContainer;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.w3c.dom.Node;

public class ExportSet {

    private ExportFactory parentExportFactory;
    private TimeSeriesContainer timeSeriesContainer;
    private TransformDefinition transformDefinition;
    private boolean startNewFile;

    public ExportSet(ExportFactory parentExportFactory) {
        this.parentExportFactory = parentExportFactory;
    }

    public ExportSet(ExportFactory parentExportFactory, TimeSeriesContainer timeSeriesContainer) {
        this.parentExportFactory = parentExportFactory;
        this.timeSeriesContainer = timeSeriesContainer;
        setTransformDefinition();
    }

    private void setTransformDefinition() {
        transformDefinition = new TransformDefinition(getTransformIndex(),
                getTimeSeriesParameter());
        if (transformDefinition.isSeparateFiles()) {
            startNewFile = true;
        }
    }

    private String getTimeSeriesParameter() {
        return timeSeriesContainer.parameter;
    }

    public HecTime getSelectionStartTime() {
        return parentExportFactory.getSelectionStartTime();
    }

    public String getExportFolder() {
        return parentExportFactory.getExportFolder();
    }

    public HecTime getSelectionEndTime() {
        return parentExportFactory.getSelectionEndTime();
    }

    public int getTransformIndex() {
        return parentExportFactory.getTransformIndex();
    }

    public TimeSeriesContainer getTimeSeriesContainer() {
        return timeSeriesContainer;
    }

    public void setTimeSeriesContainer(TimeSeriesContainer timeSeriesContainer) {
        this.timeSeriesContainer = timeSeriesContainer;
        setTransformDefinition();
    }

    public void setStartNewFile(boolean startNewFile) {
        this.startNewFile = startNewFile;
    }

    public void export() {
        String parsedFileName, parsedHeader, parsedFooter, dataLine;
        int recordIndex, recordCount;
        BufferedWriter exportFile;

        //get the file name
        parsedFileName = getFileName();
        if (startNewFile) {
            (new File(parsedFileName)).delete();
        }
        System.out.println("Records will be exported to file: " + parsedFileName);

        try {
            exportFile = new BufferedWriter(new FileWriter(parsedFileName, true));

            //write header
            if (startNewFile) {
                parsedHeader = getHeader("FILE");
                parsedHeader += getHeader("DATASET");
            } else {
                parsedHeader = getHeader("DATASET");
            }
            exportFile.write(parsedHeader);

            //write data lines
            recordCount = timeSeriesContainer.times.length;
            for (recordIndex = 0; recordIndex < recordCount; recordIndex++) {
                dataLine = getDataLine(recordIndex);
                exportFile.write(dataLine);
                parentExportFactory.increaseTotalRecordsFinished();
            }

            //write footer
            parsedFooter = getFooter();
            exportFile.write(parsedFooter);

            //close file
            exportFile.close();

        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    private String getFileName() {
        String result;
        final String FILE_PATH_SEP = File.separator;

        if (!transformDefinition.isReplaceSpaces()) {
            result = getExportFolder() + FILE_PATH_SEP + parseXmlFields(transformDefinition.getFileName());
        } else {
            result = getExportFolder() + FILE_PATH_SEP + parseXmlFields(transformDefinition.getFileName()).replaceAll(" ", "");
        }
        return result;
    }

    private String getHeader(String headerLevel) {
        String result = "";
        int lineCount, lineIndex;

        if (headerLevel.equalsIgnoreCase("FILE")) {
            lineCount = transformDefinition.getFileHeaderNodes().length;
            for (lineIndex = 0; lineIndex < lineCount; lineIndex++) {
                result += parseXmlFields(transformDefinition.getFileHeaderNodes()[lineIndex]);
                result += getNewLineChar();
            }
        } else if (headerLevel.equalsIgnoreCase("DATASET")) {
            lineCount = transformDefinition.getDataSetHeaderNodes().length;
            for (lineIndex = 0; lineIndex < lineCount; lineIndex++) {
                result += parseXmlFields(transformDefinition.getDataSetHeaderNodes()[lineIndex]);
                result += getNewLineChar();
            }
        }
        return result;
    }

    private String getFooter() {
        String result = "";
        int lineCount, lineIndex;

        lineCount = transformDefinition.getFooterNodes().length;
        for (lineIndex = 0; lineIndex < lineCount; lineIndex++) {
            result += parseXmlFields(transformDefinition.getFooterNodes()[lineIndex]);
            result += getNewLineChar();
        }
        return result;
    }

    private String getDataLine(int recordIndex) {
        String result = "";
        int columnIndex, columnCount;
        HecTime hecTime = new HecTime();
        Date javaTime = new Date();
        String valueString;

        columnCount = transformDefinition.getColumnCount();
        for (columnIndex = 0; columnIndex < columnCount; columnIndex++) {
            //date/time column:
            if ((transformDefinition.getVariables()[columnIndex]).equalsIgnoreCase("TIME")) {
                hecTime.set(timeSeriesContainer.times[recordIndex]);
                javaTime = hecTime.getJavaDate(0);
                result += ((SimpleDateFormat) transformDefinition.getFormatters()[columnIndex]).format(javaTime);
            //data value column
            } else if ((transformDefinition.getVariables()[columnIndex]).equalsIgnoreCase("VALUE")) {
                if (timeSeriesContainer.values[recordIndex] != HecMath.UNDEFINED) {
                    valueString = ((DecimalFormat) transformDefinition.getFormatters()[columnIndex]).format(timeSeriesContainer.values[recordIndex]);
                } else {
                    valueString = ((DecimalFormat) transformDefinition.getFormatters()[columnIndex]).format(transformDefinition.getMissingValues()[columnIndex]);
                }
                if (!(transformDefinition.getFormats()[columnIndex]).startsWith("##")) {
                    result += valueString;
                } else {
                    int start = valueString.length() - (transformDefinition.getFormats()[columnIndex]).length();
                    int end = valueString.length();
                    result += valueString.substring(start, end);
                }
            //hour (timestep) column
            } else if ((transformDefinition.getVariables()[columnIndex]).equalsIgnoreCase("HOURS")) {
                hecTime.set(timeSeriesContainer.times[recordIndex]);
                hecTime.subtract(getSelectionStartTime());
                valueString = ((DecimalFormat) transformDefinition.getFormatters()[columnIndex]).format(hecTime.value() / 60F);
                if (!(transformDefinition.getFormats()[columnIndex]).startsWith("##")) {
                    result += valueString;
                } else {
                    int start = valueString.length() - (transformDefinition.getFormats()[columnIndex]).length();
                    int end = valueString.length();
                    result += valueString.substring(start, end);
                }
            }
            result += transformDefinition.getSeparators()[columnIndex];
        }

        result += getNewLineChar();
        return result;
    }

    private String parseXmlFields(NodeList nodeList) {
        String result = "";

        for (int nodeIndex = 0; nodeIndex < nodeList.getLength(); nodeIndex++) {
            try {
                Node node = (Node) nodeList.item(nodeIndex);
                if (node.getNodeType() == Node.TEXT_NODE) {
                    Text text1 = (Text) node;
                    result += text1.getData();
                } else if (node.getNodeName().equalsIgnoreCase("FIELD")) {
                    String parameter = ((Element) node).getAttribute("parameter");
                    String format = ((Element) node).getAttribute("format");
                    result += parseXmlField(parameter, format);
                }
            } catch (Exception e) {
                result = "";
                System.out.println(e);
            }
        }

        return result;
    }

    private String parseXmlField(String fieldName, String fieldFormat) {
        String result = "{unknown field}";

        int hecTimeFormat;
        String javaTimeFormat;

        if (fieldName.equalsIgnoreCase("WATERSHED") ||
                fieldName.equalsIgnoreCase("CATCHMENT")) {
            result = getWatershed();
        } else if (fieldName.equalsIgnoreCase("LOCATION")) {
            result = getLocation();
        } else if (fieldName.equalsIgnoreCase("PARAMETER")) {
            result = getParameter();
        } else if (fieldName.equalsIgnoreCase("ISISUNIT")) {
            result = getIsisUnit();
        } else if (fieldName.equalsIgnoreCase("RECORDCOUNT")) {
            result = getRecordCount(fieldFormat);
        } else if (fieldName.equalsIgnoreCase("STARTTIME")) {
            try {
                hecTimeFormat = Integer.parseInt(fieldFormat);
                result = getStartTime(hecTimeFormat);
            } catch (NumberFormatException e) {
                javaTimeFormat = fieldFormat;
                result = getStartTime(javaTimeFormat);
            }
        } else if (fieldName.equalsIgnoreCase("ENDTIME")) {
            try {
                hecTimeFormat = Integer.parseInt(fieldFormat);
                result = getEndTime(hecTimeFormat);
            } catch (NumberFormatException e) {
                javaTimeFormat = fieldFormat;
                result = getEndTime(javaTimeFormat);
            }

        }

        return result;
    }

    private String getNewLineChar() {
        return System.getProperty("line.separator");
    }

    private String getWatershed() {
        return timeSeriesContainer.watershed;
    }

    private String getLocation() {
        return timeSeriesContainer.location;
    }

    private String getParameter() {
        return timeSeriesContainer.parameter;
    }

    private String getIsisUnit() {
        String result = "??BDY";
        String parameter = timeSeriesContainer.parameter.toUpperCase();

        if (parameter.startsWith("FLOW") || parameter.startsWith("DIS")) {
            result = "QTBDY";
        } else if (parameter.startsWith("STAGE") || parameter.startsWith("LEV")) {
            result = "HTBDY";
        }

        return result;
    }

    private String getRecordCount(String format) {
        String result;
        result = Integer.toString(timeSeriesContainer.numberValues);

        int formatLength = format.length();
        int countLength = result.length();

        if (formatLength > countLength) {
            result = "         " + result;
            result = result.substring(result.length() - formatLength);
        }

        return result;
    }

    private String getStartTime(String javaFormat) {
        String result = "";

        HecTime hecTime = getSelectionStartTime();
        if (hecTime.isDefined()) {
            Date javaTime = hecTime.getJavaDate(0);
            SimpleDateFormat sdf = new SimpleDateFormat(javaFormat);
            sdf.setTimeZone(TimeZone.getTimeZone("UTC")); //force it to use UTC so daylight saving is not messing things up!
            result = sdf.format(javaTime);
        } else {
            result = "UNDEFINED";
        }

        return result;
    }

    private String getStartTime(int hecFormat) {
        return getSelectionStartTime().dateAndTime(hecFormat);
    }

    private String getEndTime(String javaFormat) {
        String result = "";

        HecTime hecTime = getSelectionEndTime();
        if (hecTime.isDefined()) {
            Date javaTime = hecTime.getJavaDate(0);
            SimpleDateFormat sdf = new SimpleDateFormat(javaFormat);
            sdf.setTimeZone(TimeZone.getTimeZone("UTC")); //force it to use UTC so daylight saving is not messing things up!
            result = sdf.format(javaTime);
        } else {
            result = "UNDEFINED";
        }

        return result;
    }

    private String getEndTime(int hecFormat) {
        return getSelectionEndTime().date(hecFormat);
    }
}
