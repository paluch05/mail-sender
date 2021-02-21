package pl.paluchsoft.springmailsender.model;

import java.util.Objects;

public class Recipient {
    private final String name;
    private final String email;

    public Recipient(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Recipient recipient = (Recipient) o;
        return Objects.equals(name, recipient.name) &&
                Objects.equals(email, recipient.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, email);
    }
}
