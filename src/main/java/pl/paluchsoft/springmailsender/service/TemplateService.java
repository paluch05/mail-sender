package pl.paluchsoft.springmailsender.service;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class TemplateService {

    private final Configuration templateConfiguration;

    @Value("${mail.templates.folder}")
    private String templatesFolder;

    public TemplateService(Configuration templateConfiguration) {
        this.templateConfiguration = templateConfiguration;
    }

    public String getRenderedTemplate(String templateName, String recipientName) throws IOException, TemplateException {
        templateConfiguration.setDirectoryForTemplateLoading(new File(templatesFolder));
        Template template = templateConfiguration.getTemplate(templateName + ".ftl");
        Map<String, Object> model = new HashMap<>();
        model.put("name", recipientName);
        return FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
    }
}
