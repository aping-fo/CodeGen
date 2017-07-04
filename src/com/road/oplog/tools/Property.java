package com.road.oplog.tools;

public class Property
{
    /* 字段名字 */
    private String name;
    /* 第一个字母大写字段名字 */
    private String upperName;
    /* 字段数据类型 */
    private String type;
    /* 字段默认值  TX特有(TX现有xml文件不能修改)*/
    private String defaultValue;
    /* 字段描述 */
    private String desc;
    /* 字段对应的父类字段名字(用这个属性跟父类的字段关联起来)*/
    private String columnName;
    /* 字段对应的父类字段名字第一个字母大写*/
    private String upperColumnName;

    public Property(String name, String upperName, String type, String desc)
    {
        this.name = name;
        this.upperName = upperName;
        this.type = type;
        this.desc = desc;
    }

    public String getName()
    {
        return name;
    }

    public String getUpperName()
    {
        return upperName;
    }

    public String getType()
    {
        return type;
    }

    public String getDefaultValue()
    {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue)
    {
        this.defaultValue = defaultValue;
    }

    public String getDesc()
    {
        return desc;
    }

    public String getColumnName()
    {
        return columnName;
    }

    public void setColumnName(String columnName)
    {
        this.columnName = columnName;
    }

    public String getUpperColumnName()
    {
        return upperColumnName;
    }

    public void setUpperColumnName(String upperColumnName)
    {
        this.upperColumnName = upperColumnName;
    }

}
