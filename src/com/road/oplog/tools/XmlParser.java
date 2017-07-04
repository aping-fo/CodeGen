package com.road.oplog.tools;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlParser
{
    private static Logger LOGGER = LoggerFactory.getLogger(XmlParser.class);

    private static final  String CLAZZ_TAG = "/oplog/table";
    private static final String PROPERTY_TAG = "column";
    
    private Document doc;
    private String clazzTag;
    private String propertyTag;

    public XmlParser(String path)
    {
        SAXReader reader = new SAXReader();
        try
        {
            doc = reader.read(path);
        }
        catch (DocumentException e)
        {
            LOGGER.error("parse xml error. {}", path, e);
        }

        this.clazzTag = CLAZZ_TAG;
        this.propertyTag = PROPERTY_TAG;
    }

    /**
     * 解析整个xml
     * @return
     */
    public List<Clazz> parseClazzs()
    {
        List<Clazz> clazzs = new ArrayList<>();
        @SuppressWarnings("unchecked")
        List<Element> elements = doc.selectNodes(clazzTag);
        for (Element element : elements)
        {
            Clazz clazz = genClazz(element);
            clazzs.add(clazz);
        }
        
        return clazzs;
    }

    /**
     * 解析单个类
     * @param element
     * @return
     */
    private Clazz genClazz(Element element)
    {
        String clazzName = element.attributeValue("name");
        clazzName = convertUpperName(clazzName);
        clazzName = replaceUnderline(clazzName); 
        String clazzDesc = element.attributeValue("desc");
        Clazz clazz = new Clazz(clazzName, clazzDesc);

        @SuppressWarnings("unchecked")
        List<Element> temps = element.elements();
        for (Element temp : temps)
        {
            if (temp.getName().equals(propertyTag))
            {
                String propertyName = temp.attributeValue("name");
                propertyName = convertLowerName(propertyName);
                String propertyUpperName = convertUpperName(propertyName);
                String propertyType = temp.attributeValue("type");
                propertyType = convertDateType(propertyType);
                String propertyDesc = temp.attributeValue("desc");
                Property property = new Property(propertyName, propertyUpperName, propertyType, propertyDesc);
                clazz.addProperty(property);
            }
        }

        return clazz;
    }
    
    /**
     * 去掉单词与单词之间的_(下滑线)
     * @param name
     * @return
     */
    private static String replaceUnderline(String name)
    {
        String newName = name.replaceAll("_", "");
        return newName;
    }
    
    /**
     * 第一个字母大写转换
     * @param name
     * @return
     */
    private static String convertUpperName(String name)
    {
        String firstLetter = name.substring(0, 1).toUpperCase();
        String restLetters = name.substring(1);
        return firstLetter + restLetters;
    }
    
    /**
     * 第一个字母小写转换
     * @param name
     * @return
     */
    private static String convertLowerName(String name)
    {
        String firstLetter = name.substring(0, 1).toLowerCase();
        String restLetters = name.substring(1);
        return firstLetter + restLetters;
    }
    
    /**
     * 数据类型转换
     * @param type
     * @return
     */
    private static String convertDateType(String type)
    {
        if (type.equalsIgnoreCase("bool"))
        {
            type = "boolean";
        }
        else if (type.equalsIgnoreCase("int"))
        {
            type = "int";
        }
        else if (type.equalsIgnoreCase("long"))
        {
            type = "long";
        }
        else if (type.equalsIgnoreCase("float"))
        {
            type = "float";
        }
        else if (type.equalsIgnoreCase("double"))
        {
            type = "double";
        }
        else if (type.toLowerCase().startsWith("varchar"))
        {
            type = "String";
        }
        else if (type.equalsIgnoreCase("DateTime") || type.equalsIgnoreCase("Date"))
        {
            type = "Date";
        }
        else
        {
            type = "";
        }
        
        return type;
    }

}
