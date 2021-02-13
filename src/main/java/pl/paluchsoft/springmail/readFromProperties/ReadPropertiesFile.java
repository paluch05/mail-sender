package pl.paluchsoft.springmail.readFromProperties;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ReadPropertiesFile {

    public static String readPropFile(String filename) throws IOException {
        FileInputStream fis = null;
        Properties prop = null;
        try {
            fis = new FileInputStream(filename);
            prop = new Properties();
        } catch (IOException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        } finally {
            assert fis != null;
            fis.close();
        }
        return String.valueOf(prop);
    }
}
