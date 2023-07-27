package org.example;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.xml.sax.InputSource;

import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class XmlParser {
    private final String objXmlPath;
    private final String hierarchyXmlPath;

    public XmlParser(String objXmlPath, String hierarchyXmlPath) {
        this.objXmlPath = objXmlPath;
        this.hierarchyXmlPath = hierarchyXmlPath;
    }

    public XmlParser(String objXmlPath) {
        this(objXmlPath, null);
    }

    private Document parse(String xml) {
        try {
            InputSource is = new InputSource(new StringReader(xml));
            SAXBuilder jdomBuilder = new SAXBuilder();
            return jdomBuilder.build(is);
        } catch (IOException | JDOMException ex) {
            throw new RuntimeException(ex);
        }
    }

    private String readXml(String path) {
        try {
            return Files.readString(Path.of(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private XmlDoc create(Document document) {
        Element root = document.getRootElement();
        switch (root.getName()) {
            case "ADDRESSOBJECTS" -> {
                return createObjDoc(root.getChildren());
            }
            case "ITEMS" -> {
                return createHierDoc(root.getChildren());
            }
            default -> throw new IllegalArgumentException();
        }
    }

    private XmlDoc createObjDoc(List<Element> children) {
        var tempList = new ArrayList<ObjectXml>(children.size());
        for (Element element : children) {
            tempList.add(new ObjectXml(element.getAttribute("ID").getValue(), element.getAttribute("OBJECTID").getValue(),
                    element.getAttribute("OBJECTGUID").getValue(), element.getAttribute("CHANGEID").getValue(),
                    element.getAttribute("NAME").getValue(), element.getAttribute("TYPENAME").getValue(),
                    element.getAttribute("LEVEL").getValue(), element.getAttribute("OPERTYPEID").getValue(),
                    element.getAttribute("PREVID").getValue(),
                    element.getAttribute("NEXTID") == null ? "-1" : element.getAttribute("NEXTID").getValue(),
                    element.getAttribute("UPDATEDATE").getValue(), element.getAttribute("STARTDATE").getValue(),
                    element.getAttribute("ENDDATE").getValue(), element.getAttribute("ISACTUAL").getValue(),
                    element.getAttribute("ISACTIVE").getValue()));
        }
        XmlDoc xmlDoc = new XmlDoc();
        xmlDoc.getObjectXmls().addAll(tempList);
        return xmlDoc;
    }

    private XmlDoc createHierDoc(List<Element> children) {
        var tempList = new ArrayList<HierarchyXml>(children.size());
        for (Element element : children) {
            tempList.add(new HierarchyXml(
                    element.getAttribute("ID").getValue(),
                    element.getAttribute("OBJECTID").getValue(),
                    element.getAttribute("PARENTOBJID").getValue(),
                    element.getAttribute("CHANGEID").getValue(),
                    element.getAttribute("PREVID").getValue(),
                    element.getAttribute("NEXTID") == null ? "-1" : element.getAttribute("NEXTID").getValue(),
                    element.getAttribute("UPDATEDATE").getValue(),
                    element.getAttribute("STARTDATE").getValue(),
                    element.getAttribute("ENDDATE").getValue(),
                    element.getAttribute("ISACTIVE").getValue()));
        }
        XmlDoc xmlDoc = new XmlDoc();
        xmlDoc.getHierarchyXmls().addAll(tempList);
        return xmlDoc;
    }

    public XmlDoc processObjXmlDoc() {
        var xml = readXml(objXmlPath);
        var document = parse(xml);
        return create(document);
    }

    public XmlDoc processHyerXmlDoc() {
        var xml = readXml(hierarchyXmlPath);
        var document = parse(xml);
        return create(document);
    }
}
