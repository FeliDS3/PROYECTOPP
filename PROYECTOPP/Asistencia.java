package PROYECTOPP;
import java.time.LocalDate;


public class Asistencia {
    private String date;

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return this.date;
    }

    public Asistencia() {
        LocalDate now = LocalDate.now();
        String n = now.toString();
        this.setDate(n);
    }
}