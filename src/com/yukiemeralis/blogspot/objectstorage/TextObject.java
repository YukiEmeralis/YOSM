package com.yukiemeralis.blogspot.objectstorage;

import java.util.HashMap;
import java.util.UUID;

public class TextObject implements Cloneable
{
    private String uuid;
    private HashMap<String, Object> fields = new HashMap<>();

    private String objName;

    private String className;

    public TextObject(String objName, Class<?> type)
    {
        this.objName = objName;
        this.className = type.getName();
        this.uuid = UUID.randomUUID().toString();
    }

    public TextObject(String objName)
    {
        this.objName = objName;
        this.uuid = UUID.randomUUID().toString();
    }

    public HashMap<String, Object> getFields()
    {
        return fields;
    }

    public void writeField(String field, Object obj)
    {
        fields.put(field, obj);
    }

    public String getUUID()
    {
        return this.uuid;
    }

    public String getObjectName()
    {
        return objName;
    }

    public String getClassName()
    {
        return this.className;
    }

    public void setClassName(String className)
    {
        this.className = className;
    }


    @Override
    public Object clone()
    {
        try {
            return super.clone();
        } catch (CloneNotSupportedException error) {
            error.printStackTrace();
            return null;
        }
    }
}
