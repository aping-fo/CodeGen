package com.road.oplog.tools;

import java.io.File;
import java.util.List;

public class OpBeanStandardCodeGen
{
    private static final String XML_PATH = "resource/oplog_standard.xml";
    private static final String VM_PATH = "/OpBeanStandard.vm";
    private static final String CLAZZ_PATH = "src/com/road/oplog/bean/template";
    private static final String PACKAGE_NAME = "com.road.oplog.bean.template";
    private static final String CLASS_PREFIX = "Op";
    
    private List<Clazz> clazzs;
    private String curPath;
    
    public OpBeanStandardCodeGen()
    {
        curPath = System.getProperty("user.dir") + File.separator;
    }
    
    public void parseXml()
    {
        String path = curPath + XML_PATH;
        XmlParser xmlParser = new XmlParser(path);
        clazzs = xmlParser.parseClazzs();
    }
    
    public void genCode()
    {
        VmCodeGen codeGen = new VmCodeGen(VM_PATH);
        String classPath = curPath + CLAZZ_PATH;
        
        for(Clazz clazz : clazzs)
        {
            codeGen.genClazz(classPath, PACKAGE_NAME, clazz, CLASS_PREFIX);
        }
    }
    
    public static void main(String[] args)
    {
        OpBeanStandardCodeGen gen = new OpBeanStandardCodeGen();
        gen.parseXml();
        gen.genCode();
    }
}
