package pl.paluchsoft.springmail;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

@Configuration
@PropertySource("file:recipients.properties")
public class AppConfig {
    Configuration bean;

    @Value("${spring.mail.host}")
    private String host;

    @Value("${spring.mail.port}")
    private int port;

    @Value("${spring.mail.username}")
    private String username;

    @Value("${spring.mail.password}")
    private String password;

    @Value("${propIn}")
    private String propIn;

    @Bean
    public JavaMailSender getMailSender() throws IOException {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        mailSender.setHost(host);
        mailSender.setPort(port);
        mailSender.setUsername(username);
        mailSender.setPassword(password);

        Properties properties = mailSender.getJavaMailProperties();
        InputStream input = null;
        input = new FileInputStream("recipients.properties");
        properties.load(input);
        propIn = properties.getProperty("propIn");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.transport.protocol", "smtp");
        properties.put("mail.debug", "true");

        mailSender.setJavaMailProperties(properties);
        return mailSender;
    }

    public FreeMarkerConfigurationFactoryBean factoryBean() {
        FreeMarkerConfigurationFactoryBean bean = new FreeMarkerConfigurationFactoryBean();
        bean.setTemplateLoaderPath("resources/templates/");
        return bean;
    }

    public static String getContentFromTemplate(Map<String, Object> model) {
        StringBuilder content = new StringBuilder();
        try {
            content.append(FreeMarkerTemplateUtils
                    .processTemplateIntoString(bean.getTemplate("template.txt"), model));
            if (content == null) {
                throw new MyExc("Couldn't find the file");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } return content.toString();
    }

    @Bean
    public  static PropertySourcesPlaceholderConfigurer propConfigInDev() {
        return new PropertySourcesPlaceholderConfigurer();
    }

}