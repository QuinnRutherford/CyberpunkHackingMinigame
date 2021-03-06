package softwaredesign;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FileReader {
    public String readFile(String fileName) throws IOException {
        InputStream is = getFileFromResourceAsStream(fileName);
        String fileContent = inputStreamToString(is);
        if(fileContent.equals("")){
            System.out.println("Game unable to read file from /puzzles");
            System.exit(1);
        }
        return fileContent;
    }

    private InputStream getFileFromResourceAsStream(String fileName) {

        // The class loader that loaded the class
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);

        // the stream holding the file content
        if (inputStream == null) {
            throw new IllegalArgumentException("file not found! " + fileName);
        } else {
            return inputStream;
        }
    }

    private String inputStreamToString(InputStream i) throws IOException {
        String fileContent = "";
        BufferedReader r = new BufferedReader(new InputStreamReader(i));
        String l;
        while((l = r.readLine()) != null) {
            fileContent += l + "\n";
        }
        i.close();
        return fileContent;
    }
}
