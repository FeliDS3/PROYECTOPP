package PROYECTOPP;
import javax.swing.*;
import java.awt.*;
public class AlumnosPanel extends JPanel {
    private RegistrarAlumnos registrarAlumnos;
    private PanelAlumnos panelAlumnos;
    private JButton actualizar, seleccionar;
    private Font fuentePixelada;
    public AlumnosPanel() {
        try {
            fuentePixelada = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("Winter Lemon.ttf")).deriveFont(14f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(fuentePixelada);
        } catch (Exception e) {
            e.printStackTrace();
            fuentePixelada = new Font("Monospaced", Font.PLAIN, 12);
        }
        registrarAlumnos = new RegistrarAlumnos();
        panelAlumnos = new PanelAlumnos();
        actualizar = new JButton("Actualizar");
        actualizar.setPreferredSize(new Dimension(100,40));
        actualizar.setBackground(Color.decode("#795690"));
        actualizar.setForeground(Color.decode("#F5DFBB"));
        actualizar.setFont(fuentePixelada.deriveFont(14f));
        actualizar.addActionListener(e ->actualizarAlumnos());
        seleccionar = new JButton("Seleccionar");
        seleccionar.setPreferredSize(new Dimension(100,40));
        seleccionar.setBackground(Color.decode("#795690"));
        seleccionar.setForeground(Color.decode("#F5DFBB"));
        seleccionar.setFont(fuentePixelada.deriveFont(14f));
        seleccionar.addActionListener(e ->seleccionarAlumnos());
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        setBackground(Color.decode("#C29CE4"));
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx =0 ; gbc.gridy = 0; gbc.gridwidth =2 ; gbc.gridheight=3; gbc.fill = GridBagConstraints.BOTH;
        add(registrarAlumnos, gbc);
        gbc.gridx =2 ; gbc.gridy = 0; gbc.gridwidth =3 ; gbc.gridheight=2; gbc.fill = GridBagConstraints.BOTH;
        add(panelAlumnos, gbc);
        gbc.gridx =2 ; gbc.gridy = 2; gbc.gridwidth =1 ; gbc.gridheight=1; gbc.fill = GridBagConstraints.BOTH;
        add(actualizar,gbc);
        gbc.gridx =4 ; gbc.gridy = 2; gbc.gridwidth =1 ; gbc.gridheight=1; gbc.fill = GridBagConstraints.BOTH;
        add(seleccionar,gbc);
    }
    public void actualizarAlumnos(){
        panelAlumnos.actualizarDatos();
    }
    public void seleccionarAlumnos(){
        panelAlumnos.mostrarDetalleAlumno();
    }
}
