package pl.paluchsoft.springmail;

public class MyExc extends Exception {
    public MyExc(String message) {
        super(message);
    }

    public MyExc(String message, Throwable cause) {
        super(message, cause);
    }
}
