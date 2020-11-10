# YOSM
**Y**uki's **O**bject **S**torage **M**edium.
GSON doesn't work the way I need it to on modern java versions, so I wrote my own object storage format.

# Example usage:
```
String filePath = "./storage.yosf";

//
// For writing
//
public static void main(String[] args)
{
	ObjectStorage storage = new ObjectStorage(filePath);

	Dog dog = new Dog("Ralph", 5); // Dog implements Storable interface
	storage.addObject(dog.toObjectFile()); // Mark the object to be stored

	storage.write(); // Write all marked objects to the file given
}

// 
// For reading
//
public static void main(String[] args)
{
	ObjectStorage storage = new ObjectStorage(filePath); // ObjectStorage automatically reads the file given, as long as it exists

	Dog dummy_dog = new Dog("Dummy"); // Dog implements Storable interface

	Dog dog = (Dog) dummy_dog.fromObjectFile(storage.getStorage().get(0));

	// Print out the dog's name as loaded from the storage file
	System.out.println("Name: " + dog.getName());
	System.out.println("Age: : + dog.getAge());
}
```
