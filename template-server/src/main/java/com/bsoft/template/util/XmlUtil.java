package com.bsoft.template.util;

import org.apache.commons.io.IOUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * xml工具类
 */
public class XmlUtil {

    /**
     * xml转换成map
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> xmlToMap(String xml) {
        Map<String, Object> map = null;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            InputStream stream = IOUtils.toInputStream(xml, StandardCharsets.UTF_8.name());
            Document doc = builder.parse(stream);
            map = (Map<String, Object>) elementToMap(doc.getFirstChild());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    private static Object elementToMap(Node rootNode) {
        Map<String, Object> map = new LinkedHashMap<>();
        NodeList nodeList = rootNode.getChildNodes();
        if (nodeList.getLength() == 0) {
            if (rootNode.getParentNode().getParentNode() != null) {
                return rootNode.getTextContent();
            } else {
                map.put(rootNode.getNodeName(), rootNode.getTextContent());
            }
        } else if (nodeList.getLength() == 1) {
            return elementToMap(nodeList.item(0));
        } else {

            if (nodeList.item(0).getNodeName().equals(nodeList.item(1).getNodeName())) {
                List<Object> list = new ArrayList<>();
                for (int i = 0; i < nodeList.getLength(); i++) {
                    Node node = nodeList.item(i);
                    list.add(elementToMap(node));
                }
                return list;
            } else {
                for (int i = 0; i < nodeList.getLength(); i++) {
                    Node node = nodeList.item(i);
                    map.put(node.getNodeName(), elementToMap(node));
                }
            }
        }
        return map;
    }

    public static void main(String[] args) {
        String xml = "<root><code>200</code><body><a>123</a><b><c>1</c><c>2</c></b></body><msg>成功</msg></root>";
        System.out.println(GsonUtil.objectToJson(xmlToMap(xml)));
    }

}
