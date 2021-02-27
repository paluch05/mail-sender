package pl.paluchsoft.springmailsender.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.paluchsoft.springmailsender.model.Recipient;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecipientsService {
    @Value("${mail.recipients.folder}")
    private String recipientsFolder;

    public List<Recipient> getRecipients(String name) throws IOException {
        if (name == null) {
            throw new IllegalArgumentException("List name cannot be null");
        }
        Path path = Path.of(recipientsFolder, name + ".txt");
        if (!Files.exists(path)) {
            throw new FileNotFoundException("The list " + name + " does not exist");
        }
        return Files.lines(path).filter(line -> !line.trim().isEmpty()).map(line -> {
            String[] split = line.split(",");
            return new Recipient(split[0], split[1]);
        }).collect(Collectors.toList());
    }


}
