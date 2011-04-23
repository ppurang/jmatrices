package org.jmatrices.dbl.build;

import org.jdom.Document;
import org.jdom.JDOMException;
import org.jdom.Element;
import org.jdom.DataConversionException;
import org.jdom.xpath.XPath;
import org.jdom.input.SAXBuilder;
import org.jmatrices.dbl.Matrix;
import org.jmatrices.dbl.MatrixFactory;
import org.jmatrices.dbl.MutableMatrixProducer;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * XMLMatrixFeeder.
 *
 * @author ppurang
 *         created  09.04.2005 - 16:06:41
 */
public class XMLMatrixFeeder implements MatrixFeeder {
    private Document doc;
    private String rootElement = "matrix";
    private String rowsElement = "rows";
    private String colsElement = "cols";


    private String rowElement = "row";
    private String rowAttribute = "num";
    private String colElement = "col";
    private String colAttribute = "num";

    private String typeElement = "type";


    public void setFile(String pathToFile) {
        try {
            doc= new SAXBuilder(false).build(pathToFile);
        } catch (JDOMException e) {
            throw new RuntimeException("Wrapped a JDOMException", e);
        } catch (IOException e) {
            throw new RuntimeException("Wrapped an IOException", e);
        }
    }

    public void setFile(File file) {
        try {
            doc= new SAXBuilder(true).build(file);
        } catch (JDOMException e) {
            throw new RuntimeException("Wrapped a JDOMException", e);
        } catch (IOException e) {
            throw new RuntimeException("Wrapped an IOException", e);
        }
    }

    public int rows() {
        return getIntegerFromElement("/" + rootElement + "/" + rowsElement);
    }

    public int cols() {
        return getIntegerFromElement("/" + rootElement + "/" + colsElement);
    }

    private int getIntegerFromElement(String xpath) {
        return Integer.parseInt(getStringFromElement(xpath).trim());
    }

    private String getStringFromElement(String xpath) {
        String content = "";
        try {
            XPath x  = XPath.newInstance("string(" + xpath + ")");
            content = (String)x.selectSingleNode(doc);
        } catch (JDOMException e) {
            throw new RuntimeException("Wrapped a JDOMException", e);
        }

        return content;
    }


    private String getMatrixType() {
        return getStringFromElement("/" + rootElement + "/" + typeElement).trim();
    }

    public Matrix getMatrix() {
        MutableMatrixProducer producer = MatrixFactory.getInstance().getProducer(getMatrixType());
        Matrix result = producer.getMatrix(rows(),cols());
        populateMatrix(result);
        return result;
    }

    private void populateMatrix(Matrix result) {
        try {
            XPath rows_xpath = XPath.newInstance("/"+ rootElement+ "/"+ rowElement);
            XPath cols_xpath = XPath.newInstance( colElement);
            List rows = rows_xpath.selectNodes(doc);
            for (int i = 0; i < rows.size(); i++) {
                Element row = (Element) rows.get(i);
                int row_index = getAttributeAsInt(row, rowAttribute);
                List cols = cols_xpath.selectNodes(row);
                for (int j = 0; j < cols.size(); j++) {
                    Element col = (Element) cols.get(j);
                    int col_index = getAttributeAsInt(col, colAttribute);
                    double value = 0;
                    try {
                        value = Double.valueOf(col.getTextTrim()).doubleValue();
                    } catch (NumberFormatException e) {
                        throw new RuntimeException("Wrapped a NumberFormatException", e);
                    }
                    result.setValue(row_index, col_index, value);
                }

            }
        } catch (JDOMException e) {
            throw new RuntimeException("Wrapped a JDOMException", e);
        }
    }

    private int getAttributeAsInt(Element element, String attribute) {
        try {
            return element.getAttribute(attribute).getIntValue();
        } catch (DataConversionException e) {
            throw new RuntimeException("Wrapped a DataConversionException", e);
        }
    }

    /*
    public Row getRow(int index) {
        return null;
    }

    public Column getColumn(int index) {
        return null;
    }*/
}
