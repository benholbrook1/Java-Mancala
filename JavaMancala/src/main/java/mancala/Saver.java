package mancala;

import java.io.IOException;
import java.io.Serializable;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;


public class Saver{

    // Public Methods

    /**
     * Saves an object that implements serializable
     * 
     * @param toSave the class that we are looking to save
     * @param filename the name of the file that we are going to save the object to
     */

    public static void saveObject(final Serializable toSave, final String filename) throws IOException{

        final Path currentDir = Paths.get(System.getProperty("user.dir"));
        final String folderName = "assets/";
        final Path assetsFolder = currentDir.resolve(folderName);
        final Path assetsFolderPath = currentDir.resolve(folderName + filename);

        Files.createDirectories(assetsFolder);

        final FileOutputStream outputStream = new FileOutputStream(assetsFolderPath.toString());
        final ObjectOutputStream outputDest = new ObjectOutputStream(outputStream);

        
        outputDest.writeObject(toSave);

        outputDest.close();
        outputStream.close();
    }

    /**
     * This method loads an object previously saved with saveObject
     * 
     * @param filename the name of the file that the obejct was saved to
     */
    public static Serializable loadObject(final String filename) throws IOException{

        Serializable returnObject;

        final Path currentDir = Paths.get(System.getProperty("user.dir"));
        final String folderName = "assets/";

        final Path assetsFolderPath = currentDir.resolve(folderName + filename);

        final FileInputStream inputFile = new FileInputStream(assetsFolderPath.toString());
        final ObjectInputStream inputStream = new ObjectInputStream(inputFile);
            
        try{
            returnObject = (Serializable) inputStream.readObject();
        } catch (ClassNotFoundException ex){
            throw new IOException(ex);
        }

        inputFile.close();
        inputStream.close();

        return returnObject;
    }
}