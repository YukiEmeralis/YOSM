package com.yukiemeralis.blogspot.objectstorage;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class ObjectStorage 
{
    private File target_file;

    private ArrayList<TextObject> objects = new ArrayList<>();

    private PrintWriter writer;

    /**
     * Creates an instance of object storage to use. 
     * Automatically reads from the file path provided, if the storage file exists.
     * Accepts any file format, but .yosf is preferred.
     * To add objects to be saved, use {@link ObjectStorage#addObject(TextObject)}
     * To write to the file, use {@link ObjectStorage#write()}
     * @param filePath
     */
    public ObjectStorage(String filePath)
    {
        this.target_file = new File(filePath);

        try {
            if (!target_file.exists())
            {
                target_file.getParentFile().mkdirs();
                target_file.createNewFile();
            } else {
                // Pull current information from the file
                read();
            }
        } catch (IOException error) {
            error.printStackTrace();
        }
    }

    public void addObject(TextObject obj)
    {
        objects.add(obj);
    }

    public ArrayList<TextObject> getStorage()
    {
        return objects;
    }

    //
    // Writing
    //

    public void write()
    {
        try {
            writer = new PrintWriter(target_file);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        objects.forEach(object -> {
            writeBeginning(object);

            writeLine("   " + "classType," + object.getClassName());
            object.getFields().forEach((fieldName, field) -> {
                writeField(object, fieldName, StorageField.fromRawType(field));
            });

            writeEnd(object);
        });

        // Finish and close resources
        endFile();

        writer.flush();
        writer.close();
    }  
    
    // Storage methods

    private void writeBeginning(TextObject obj)
    {
        writeLine("Begin," + obj.getObjectName());
        System.out.println("Begin," + obj.getObjectName());
    }

    private void writeEnd(TextObject obj)
    {
        writeLine("End");
        System.out.println("End");
    }

    private void endFile()
    {
        writeLine("ENDFILE");
    }

    private void writeField(TextObject obj, String fieldName, StorageField type)
    {
        writeLine("   " + fieldName + "," + type.toString() + "," + obj.getFields().get(fieldName).toString());
        System.out.println("   " + fieldName + "," + type.toString() + "," + obj.getFields().get(fieldName).toString());
    }

    private void writeLine(String line)
    {
        try {
            writer.write(line);
            writer.println();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //
    // Reading
    //

    public void read()
    {
        ArrayList<TextObject> objs = new ArrayList<>();

        try {
            Scanner reader = new Scanner(target_file);
            TextObject object = null;

            while (reader.hasNextLine())
            {
                String currentLine = reader.nextLine();

                // Begin line
                if (currentLine.startsWith("Begin"))
                {
                    System.out.println("Beginning object");
                    object = new TextObject(currentLine.split(",")[1]);
                }

                // Classtype line
                if (currentLine.trim().startsWith("classType") && currentLine.split(",").length != 3)
                {
                    System.out.println("Defining classtype");
                    object.setClassName(currentLine.split(",")[1]);
                }

                // Field line
                if (currentLine.trim().split(",").length == 3)
                {
                    
                    String trimmedCurrent = currentLine.trim();
                    String[] components = trimmedCurrent.split(",");

                    String fieldName = components[0];
                    StorageField fieldType = StorageField.valueOf(components[1]);

                    System.out.println("Parsing field of type " + fieldType.toString());

                    Object field = null;

                    switch (fieldType.toString())
                    {
                        case "STRING": field = components[2]; break;
                        case "INTEGER": field = Integer.parseInt(components[2]); break;
                        case "DOUBLE": field = Double.parseDouble(components[2]); break;
                        case "LONG": field = Long.parseLong(components[2]); break;
                    }

                    object.writeField(fieldName, field);
                }

                // End object line
                if (currentLine.equals("End"))
                {
                    System.out.println("Ending object");
                    TextObject clone = (TextObject) object.clone();
                    objs.add(clone);

                    object = null;
                }

                if (currentLine.equals("ENDFILE"))
                    break;
            }

            System.out.println("Reached terminator symbol");

            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.objects = objs;
    }
}
