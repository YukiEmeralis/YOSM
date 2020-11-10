package com.yukiemeralis.blogspot.objectstorage;

public enum StorageField 
{
    BEGIN,
    END,
    
    INTEGER,
    DOUBLE,
    STRING,
    LONG,

    ARRAY_BEGIN,
    ARRAY_END
    ;    

    public static StorageField fromRawType(Object obj)
    {
        return valueOf(obj.getClass().getSimpleName().toUpperCase());
    }
}
