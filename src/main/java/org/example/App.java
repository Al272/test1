package org.example;

import shaded.org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

/**
 * Hello world!
 */
public class App {

    public static void main(String[] args) {
        Properties props = loadProperties();
        String objXmlPath = props.getProperty("as_addr_ibj_path", "src/main/resources/AS_ADDR_OBJ.XML");
        String hierarchyXmlPath = props.getProperty("as_adm_hierarhy_path", "src/main/resources/AS_ADM_HIERARCHY.XML");

        App app = new App();
        app.firstTask(objXmlPath);
        app.secondTask(objXmlPath, hierarchyXmlPath);

    }

    private void secondTask(String objXmlPath, String hierarchyXmlPath) {
        XmlParser xmlParser = new XmlParser(objXmlPath, hierarchyXmlPath);
        DataReader dataReader = new DataReader(xmlParser.processObjXmlDoc(), xmlParser.processHyerXmlDoc());
        dataReader.showFullAdressesCain("2018-07-16", "1460267", "1460128", "1460061");
    }

    private void firstTask(String objXmlPath) {
        XmlParser xmlParser = new XmlParser(objXmlPath);
        DataReader dataReader = new DataReader(xmlParser.processObjXmlDoc());
        dataReader.showAdresses("2017-11-19", "1422396", "1423071", "1422127");
    }

    private static Properties loadProperties() {
        try {
            File file = new File("src/main/java/config/props.properties");

            Properties prop = new Properties();
            String s = FileUtils.readFileToString(file, String.valueOf(StandardCharsets.UTF_8));
            StringReader r = new StringReader(s);
            prop.load(r);
            return prop;
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
