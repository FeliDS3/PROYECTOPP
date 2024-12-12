package PROYECTOPP;

import javax.swing.*;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.time.LocalDate;

public class RegistrarAsist extends JPanel{
    private JLabel idAlumno,idActividad;
    private JButton OKS,PQR;
    public static JTextField tidAlumno,tidActividad;
    private JCheckBox Actrealizada;
    private Font fuentePixelada;
    LocalDate now = LocalDate.now();
    String n=now.toString();
    PlainDocument doc;
    public RegistrarAsist(){
        try {
            fuentePixelada = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("Winter Lemon.ttf")).deriveFont(14f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(fuentePixelada);
        } catch (Exception e) {
            e.printStackTrace();
            fuentePixelada = new Font("Monospaced", Font.PLAIN, 12);
        }
        setPreferredSize(new Dimension(800, 800));
        setLayout(new GridBagLayout());
        setBackground(Color.decode("#9570C6"));
        GridBagConstraints gbc = new GridBagConstraints();
        idAlumno = new JLabel("ID DEL ALUMNO");
        idAlumno.setForeground(Color.decode("#F5DFBB"));
        idAlumno.setFont(fuentePixelada.deriveFont(14f));
        tidAlumno = new JTextField();
        tidAlumno.setForeground(Color.decode("#391B49"));
        tidAlumno.setFont(fuentePixelada.deriveFont(14f));
        tidAlumno.setEditable(true);
        tidAlumno.setPreferredSize(new Dimension(150,30));
        doc = (PlainDocument) tidAlumno.getDocument();
        doc.setDocumentFilter(new LimitDocumentFilter(5));
        Actrealizada = new JCheckBox("TRABAJO REALIZADO");
        Actrealizada.setForeground(Color.decode("#F5DFBB"));
        Actrealizada.setFont(fuentePixelada.deriveFont(14f));
        Actrealizada.setPreferredSize(new Dimension(150,30));
        Actrealizada.setEnabled(false);
        Actrealizada.setBackground(Color.decode("#795690"));
        OKS = new JButton("OKEY");
        OKS.setPreferredSize(new Dimension(100,40));
        OKS.setBackground(Color.decode("#795690"));
        OKS.setForeground(Color.decode("#F5DFBB"));
        OKS.setFont(fuentePixelada.deriveFont(14f));
        OKS.addActionListener(e -> Registrar());
        PQR = new JButton("ESCANEAR QR");
        PQR.setPreferredSize(new Dimension(100,40));
        PQR.setBackground(Color.decode("#795690"));
        PQR.setForeground(Color.decode("#F5DFBB"));
        PQR.setFont(fuentePixelada.deriveFont(14f));
        PQR.addActionListener(e ->escanear());
        idActividad = new JLabel("ID DEL ACTIVIDAD");
        idActividad.setForeground(Color.decode("#F5DFBB"));
        idActividad.setFont(fuentePixelada.deriveFont(14f));
        tidActividad = new JTextField();
        tidActividad.setForeground(Color.decode("#391B49"));
        tidActividad.setFont(fuentePixelada.deriveFont(14f));
        tidActividad.setEditable(false);
        tidActividad.setPreferredSize(new Dimension(150,30));
        tidActividad.setText(null);
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx =0 ; gbc.gridy = 0; gbc.gridwidth =1 ; gbc.gridheight=1; gbc.fill = GridBagConstraints.BOTH;
        add(idAlumno,gbc);
        gbc.gridx =1 ; gbc.gridy = 0; gbc.gridwidth =1 ; gbc.gridheight=1; gbc.fill = GridBagConstraints.BOTH;
        add(tidAlumno,gbc);
        gbc.gridx =0 ; gbc.gridy = 1; gbc.gridwidth =1 ; gbc.gridheight=1; gbc.fill = GridBagConstraints.BOTH;
        add(idActividad,gbc);
        gbc.gridx =1 ; gbc.gridy = 1; gbc.gridwidth =1 ; gbc.gridheight=1; gbc.fill = GridBagConstraints.BOTH;
        add(tidActividad,gbc);
        gbc.gridx =0 ; gbc.gridy = 2; gbc.gridwidth =2 ; gbc.gridheight=1; gbc.fill = GridBagConstraints.BOTH;
        add(Actrealizada,gbc);
        gbc.gridx =0 ; gbc.gridy = 3; gbc.gridwidth =1 ; gbc.gridheight=1; gbc.fill = GridBagConstraints.BOTH;
        add(OKS,gbc);
        gbc.gridx =1 ; gbc.gridy = 3; gbc.gridwidth =1 ; gbc.gridheight=1; gbc.fill = GridBagConstraints.BOTH;
        add(PQR,gbc);
    }
    public void registrarA(){
        try{
            DBM d=new DBM();
            d.registrarAsistenciaAlumno(tidAlumno.getText(),n);
            JOptionPane.showMessageDialog(null,"ASISTENCIA REGISTRATDA");


        }catch (Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,"ERROR DE DATOS");

        }
    }
    public void registrarAcA(){
        try{
        if(Actrealizada.isSelected()){
            DBM d=new DBM();
            int n=Integer.parseInt(tidActividad.getText());
            d.asignarTrabajoAlumno(tidAlumno.getText(),n);

        }
        }catch (Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,"ERROR DE DATOS");
        }
    }
    public void Registrar(){
        if(Actrealizada.isEnabled()){
            registrarA();
            registrarAcA();
        }else{
            registrarA();

        }
        limpiarCampos();
    }
    public void limpiarCampos(){
        tidAlumno.setText("");
    }

    public void setTIdActividad(String id){
        tidActividad.setText(id);
    }

    public void setEA(boolean e){
        Actrealizada.setEnabled(e);
    }

    public void escanear(){
        SwingUtilities.invokeLater(()->{
            QRScanner q=new QRScanner();
        });
    }
}
