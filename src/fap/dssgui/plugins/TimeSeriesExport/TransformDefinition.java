package fap.dssgui.plugins.TimeSeriesExport;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.TimeZone;
import org.w3c.dom.NodeList;

public class TransformDefinition {

    private static final String TRANSFORMS_DEF_FILE
            = java.util.ResourceBundle.getBundle("fap/dssgui/plugins/TimeSeriesExport/settings").getString("transformDefinitionsFile");
    private int transformIndex;
    private String parameter; //Individual TransformDefinitions should be created for different parameters (e.g. FLOW, STAGE).
    private String name;
    private NodeList fileName;
    private boolean replaceSpaces;
    private boolean separateFiles;
    private NodeList[] fileHeaderNodes, dataSetHeaderNodes, footerNodes;
    private String[] variables, formats, separators;
    private Object[] formatters;
    private double[] missingValues;
    private int columnCount;

    public TransformDefinition(int transformIndex, String parameter) {
        this.transformIndex = transformIndex;
        this.parameter = parameter;
        setParameters();
    }

    public void setParameters() {
        int columnIndex;
        XmlHandler transformsXmlHandler = new XmlHandler(TRANSFORMS_DEF_FILE);
        int xmlTransformIndex = transformIndex + 1;
        String xPathPart1 = "/transforms/transform[" + xmlTransformIndex + "]/";
        String fileHeaderXPath, dataSetHeaderXPath;
        String specificParameterXPath = "[@parameter='" + parameter + "']";
        String genericParameterXPath = "[not(@parameter)]"; //"[@parameter='']";
        String specificLevelXPath = "[@level='DATASET']";
        String genericLevelXPath = "[not(@level)]"; //"[@level='']";

        //meta data parameters
        name = transformsXmlHandler.getNodeTextValue(xPathPart1 + "name");
        fileName = transformsXmlHandler.getNodeListNodes(xPathPart1 + "file/name")[0];
        replaceSpaces = transformsXmlHandler.getNodeBooleanValue(xPathPart1 + "file/removeSpaces", replaceSpaces);
        separateFiles = transformsXmlHandler.getNodeBooleanValue(xPathPart1 + "file/separateFiles", separateFiles);

        fileHeaderXPath = xPathPart1;
        if (transformsXmlHandler.isNodeExists(xPathPart1 + "header[@level='FILE']" + specificParameterXPath)) {
            fileHeaderXPath += "header[@level='FILE']" + specificParameterXPath + "/line";
        } else {
            fileHeaderXPath += "header[@level='FILE']" + genericParameterXPath + "/line";
        }
        fileHeaderNodes = transformsXmlHandler.getNodeListNodes(fileHeaderXPath);

        dataSetHeaderXPath = xPathPart1;
        if (transformsXmlHandler.isNodeExists(xPathPart1 + "header" + specificParameterXPath)) {
            if (transformsXmlHandler.isNodeExists(xPathPart1 + "header" + specificLevelXPath + specificParameterXPath)) {
                dataSetHeaderXPath += "header" + specificLevelXPath + specificParameterXPath + "/line";
            } else {
                dataSetHeaderXPath += "header" + genericLevelXPath + specificParameterXPath + "/line";
            }
        } else {
            if (transformsXmlHandler.isNodeExists(xPathPart1 + "header" + specificLevelXPath)) {
                dataSetHeaderXPath += "header" + specificLevelXPath + genericParameterXPath + "/line";
            } else {
                dataSetHeaderXPath += "header" + genericLevelXPath + genericParameterXPath + "/line";
            }
        }
        dataSetHeaderNodes = transformsXmlHandler.getNodeListNodes(dataSetHeaderXPath);

        footerNodes = transformsXmlHandler.getNodeListNodes(xPathPart1 + "footer/line");    //data parameters
        columnCount = transformsXmlHandler.getNodeCount(xPathPart1 + "data/column");
        variables = transformsXmlHandler.getNodeListTextValue(xPathPart1 + "data/column/variable");
        formats = transformsXmlHandler.getNodeListTextValue(xPathPart1 + "data/column/format");
        formatters = defineDataFormatters();
        separators = new String[columnCount];
        missingValues = new double[columnCount];
        for (columnIndex = 0; columnIndex < columnCount; columnIndex++) {
            //optional parameters, however, we need to get (default) values for each column!
            separators[columnIndex] = transformsXmlHandler.getNodeTextValue(xPathPart1
                    + "data/column[" + (columnIndex + 1) + "]/separator");
            missingValues[columnIndex] = transformsXmlHandler.getNodeDoubleValue(xPathPart1
                    + "data/column[" + (columnIndex + 1) + "]/missingValue");
        }

    }

    private Object[] defineDataFormatters() {
        Object[] result = new Object[columnCount];
        for (int columnIndex = 0; columnIndex
                < columnCount; columnIndex++) {
            if ((variables[columnIndex]).equalsIgnoreCase("TIME")) {
                SimpleDateFormat sdf = new SimpleDateFormat(formats[columnIndex]);
                sdf.setTimeZone(TimeZone.getTimeZone("UTC")); //force it to use UTC so daylight saving is not messing things up!
                result[columnIndex] = sdf;
            } else {
                if (!(formats[columnIndex]).startsWith("##")) {
                    result[columnIndex] = new DecimalFormat(formats[columnIndex]);
                } else {
                    result[columnIndex] = new DecimalFormat("'                     '" + formats[columnIndex]);
                }
            }
        }
        return result;
    }

    public NodeList getFileName() {
        return fileName;
    }

    public NodeList[] getDataSetHeaderNodes() {
        return dataSetHeaderNodes;
    }

    public NodeList[] getFileHeaderNodes() {
        return fileHeaderNodes;
    }

    public NodeList[] getFooterNodes() {
        return footerNodes;
    }

    public String[] getFormats() {
        return formats;
    }

    public int getColumnCount() {
        return columnCount;
    }

    public Object[] getFormatters() {
        return formatters;
    }

    public String getName() {
        return name;
    }

    public String getParameter() {
        return parameter;
    }

    public boolean isReplaceSpaces() {
        return replaceSpaces;
    }

    public boolean isSeparateFiles() {
        return separateFiles;
    }

    public String[] getSeparators() {
        return separators;
    }

    public double[] getMissingValues() {
        return missingValues;
    }

    public String[] getVariables() {
        return variables;
    }
}
