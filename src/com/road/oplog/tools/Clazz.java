package com.road.oplog.tools;

import java.util.ArrayList;
import java.util.List;


public class Clazz
{
    /* 类名 */
    private String name;
    /* 描述 */
    private String desc;
    /* 父类的名字 TX特有(TX现有xml文件不能修改)*/
    private String parentClassName;
    /* 类的属性列表 */
    private List<Property> properties;

    public Clazz(String name, String desc)
    {
        this.name = name;
        this.desc = desc;
        this.properties = new ArrayList<>();
    }

    public void addProperty(Property property)
    {
        this.properties.add(property);
    }

    public String getName()
    {
        return name;
    }

    public String getDesc()
    {
        return desc;
    }

    public String getParentClassName()
    {
        return parentClassName;
    }

    public void setParentClassName(String parentClassName)
    {
        this.parentClassName = parentClassName;
    }

    public List<Property> getProperties()
    {
        return properties;
    }
}
