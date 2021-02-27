package pl.paluchsoft.springmailsender.service;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

@Service
public class TemplateService {

    private final Configuration templateConfiguration;

    private final String templatesFolder;

    public TemplateService(Configuration templateConfiguration, @Value("${mail.templates.folder}") String templatesFolder) {
        this.templateConfiguration = templateConfiguration;
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

    public String getRenderedTemplate(String templateName, String recipientName) throws IOException, TemplateException {
        if (templateName == null || recipientName == null) {
            throw new IllegalArgumentException("Template name or recipient name cannot be null");
        }
        templateConfiguration.setDirectoryForTemplateLoading(new File(templatesFolder));
        Template template = templateConfiguration.getTemplate(templateName + ".ftl");
        Map<String, Object> model = new HashMap<>();
        model.put("name", recipientName);
        return FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
    }
}
