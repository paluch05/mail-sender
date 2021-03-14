package pl.paluchsoft.springmailsender.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

@Service
public class AttachmentService {
    private final String templatesFolder;

    public AttachmentService(@Value("${mail.templates.folder}") String templatesFolder) {
        File file = new File(templatesFolder);
        if (!file.exists()) {
            throw new IllegalArgumentException("Template folder " + templatesFolder + " does not exist");
        }
        this.templatesFolder = file.getAbsolutePath();
    }

    public List<File> getAttachments(String templateName) {
        if (templateName == null || templateName.isEmpty()) {
            throw new IllegalArgumentException("TemplateName can not be null or empty");
        }
        File attachmentsFolder = Path.of(templatesFolder, templateName).toFile();
        if (!attachmentsFolder.exists() && !attachmentsFolder.isDirectory()) {
            throw new IllegalArgumentException(attachmentsFolder.getAbsolutePath() + " does not exist or is not a directory");
        }
        File[] files = attachmentsFolder.listFiles();
        return Arrays.asList(files);
    }
}