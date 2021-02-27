package pl.paluchsoft.springmailsender.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class SubjectService {

    private final String templatesFolder;

    public SubjectService(@Value("${mail.templates.folder}") String templatesFolder) {
        if (!Files.exists(Path.of(templatesFolder))) {
            File file = new File(templatesFolder);
            if (!file.exists()) {
                throw new RuntimeException("Template folder " + templatesFolder + " does not exist");
            }
            this.templatesFolder = file.getAbsolutePath();
        } else {
            this.templatesFolder = templatesFolder;
        }
    }


    public String getSubject(String templateName) throws IOException {
        if (templateName == null) {
            throw new IllegalArgumentException("File name cannot be null");
        }
        Path path = Path.of(templatesFolder, templateName + ".txt");
        if (!Files.exists(path)) {
            throw new FileNotFoundException("File " + templateName + " does not exist");
        }
        return Files.readString(path);
    }
}
