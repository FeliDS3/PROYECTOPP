package PROYECTOPP;
import javax.swing.*;
import javax.swing.text.PlainDocument;
import java.awt.*;
public class RegistrarTA extends JPanel {
    private JLabel idTrabajo,idAlumno;
    private JTextField tidTrabajo,tidAlumno;
    private JButton registrar;
    private Font fuentePixelada;
    PlainDocument doc;
    public RegistrarTA() {
        try {
            fuentePixelada = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("Winter Lemon.ttf")).deriveFont(14f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(fuentePixelada);
        } catch (Exception e) {
            e.printStackTrace();
            fuentePixelada = new Font("Monospaced", Font.PLAIN, 12);
        }
        setLayout(new GridBagLayout());
        setBackground(Color.decode("#C29CE4"));
        idTrabajo = new JLabel("ID DEL TRABAJO");
        idTrabajo.setForeground(Color.decode("#F5DFBB"));
        idTrabajo.setFont(fuentePixelada.deriveFont(14f));
        tidTrabajo = new JTextField();
        tidTrabajo.setEditable(true);
        tidTrabajo.setForeground(Color.decode("#391B49"));
        tidTrabajo.setFont(fuentePixelada.deriveFont(14f));
        tidTrabajo.setPreferredSize(new Dimension(250, 30));
        idAlumno = new JLabel("Alumno");
        idAlumno.setFont(fuentePixelada.deriveFont(14f));
        idAlumno.setForeground(Color.decode("#F5DFBB"));
        tidAlumno = new JTextField();
        doc = (PlainDocument) tidAlumno.getDocument();
        doc.setDocumentFilter(new LimitDocumentFilter(5));
        tidAlumno.setEditable(true);
        tidAlumno.setForeground(Color.decode("#391B49"));
        tidAlumno.setPreferredSize(new Dimension(250, 30));
        tidAlumno.setFont(fuentePixelada.deriveFont(14f));
        registrar = new JButton("Registrar");
        registrar.setPreferredSize(new Dimension(100,40));
        registrar.setBackground(Color.decode("#795690"));
        registrar.setForeground(Color.decode("#F5DFBB"));
        registrar.setFont(fuentePixelada.deriveFont(14f));
        registrar.addActionListener(e ->Registrar());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx =0 ; gbc.gridy = 0; gbc.gridwidth =1 ; gbc.gridheight=1; gbc.fill = GridBagConstraints.BOTH;
        add(idTrabajo, gbc);
        gbc.gridx =0 ; gbc.gridy = 1; gbc.gridwidth =1 ; gbc.gridheight=1; gbc.fill = GridBagConstraints.BOTH;
        add(idAlumno, gbc);
        gbc.gridx =1 ; gbc.gridy = 0; gbc.gridwidth =1 ; gbc.gridheight=1; gbc.fill = GridBagConstraints.BOTH;
        add(tidTrabajo, gbc);
        gbc.gridx =1 ; gbc.gridy = 1; gbc.gridwidth =1 ; gbc.gridheight=1; gbc.fill = GridBagConstraints.BOTH;
        add(tidAlumno, gbc);
        gbc.gridx =0 ; gbc.gridy = 2; gbc.gridwidth =2 ; gbc.gridheight=1; gbc.fill = GridBagConstraints.BOTH;
        add(registrar, gbc);
        setPreferredSize(new Dimension(800,800));

    }
    public void Registrar() {
        DBM d=new DBM();
        int n=Integer.parseInt(tidTrabajo.getText());
        d.asignarTrabajoAlumno(tidAlumno.getText(),n);
        limpiar();
    }
    public void limpiar() {
        tidTrabajo.setText("");
        tidAlumno.setText("");
    }
}
