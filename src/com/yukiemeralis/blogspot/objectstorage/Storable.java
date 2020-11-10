package com.yukiemeralis.blogspot.objectstorage;

public interface Storable 
{
    public TextObject toObjectFile();   

    public Object fromObjectFile(TextObject obj);
}
