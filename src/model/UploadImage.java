package model;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;

public class UploadImage {

    private static HashMap<String, String> map = new HashMap<>();
    
    public static HashMap upload(HashMap<String,String> imageMap) {

        String destinationDirectory = "src/images/profileImages/";// Specify the destination directory for the image
        File destDir = new File(destinationDirectory);// Create the destination directory if it does not exist
        if (!destDir.exists()) {
            destDir.mkdirs();
        }

        String destinationPath = destinationDirectory + imageMap.get("filename");// Construct the destination file path

        // Copy the selected file to the destination directory
        try {
            Path sourcePath = new File(imageMap.get("selectedFile")).toPath();
            Path destinationFilePath = new File(destinationPath).toPath();
            Files.copy(sourcePath, destinationFilePath, StandardCopyOption.REPLACE_EXISTING);

            map.put("status", "1");
            map.put("url", String.valueOf(destinationPath));
            
            return map;
            
        } catch (IOException e) {
            e.printStackTrace();
            
            map.put("status", "2");
            return map;
        }
    }

}
