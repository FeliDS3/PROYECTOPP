package PROYECTOPP;
import javax.swing.*;
import java.security.Principal;
import java.time.LocalDate;

public class principal extends JFrame {
    private JTabbedPane p;
    private Asistencias a;
    private AlumnosPanel ap;
    private RegistrarTrabajos r;
    DBM DBM = new DBM();
    LocalDate lt = LocalDate.now();
    String n= lt.toString();
    RegistrarTA rt;
    public principal() {
        p = new JTabbedPane();
        a = new Asistencias();
        ap = new AlumnosPanel();
        r = new RegistrarTrabajos();
        rt = new RegistrarTA();
        r.setTFechaTrabajo(n);
        add(p);
        p.addTab("Asistencias", a);
        p.addTab("Alumnos", ap);
        p.addTab("Registrar Trabajos", r);
        p.addTab("Registrar Trabajos a alumnos", rt);
        setSize(1500, 900);
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setTitle("PROYECTOPP");

    }
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> new principal().setVisible(true));
    }
}
