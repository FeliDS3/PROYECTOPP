package PROYECTOPP;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;

public class RegistrarAlumnos extends JPanel {
    private JTextField TidAlumno,TNombre,TapP,TapM;
    private JLabel idAlumno,nombre,apP,apM;
    private JButton botonRegistrarAlumnos,limpiar;
    PlainDocument doc;
    private Font fuentePixelada;
    public RegistrarAlumnos(){
        try {
            fuentePixelada = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("Winter Lemon.ttf")).deriveFont(14f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(fuentePixelada);
        } catch (Exception e) {
            e.printStackTrace();
            fuentePixelada = new Font("Monospaced", Font.PLAIN, 12);
        }
        setPreferredSize(new Dimension(600, 600));
        setLayout(new GridBagLayout());
        idAlumno= new JLabel("ID DEL ALUMNO:");
        idAlumno.setForeground(Color.decode("#F5DFBB"));
        idAlumno.setFont(fuentePixelada.deriveFont(14f));
        TidAlumno=new JTextField();
        TidAlumno.setEditable(true);
        TidAlumno.setForeground(Color.decode("#391B49"));
        TidAlumno.setFont(fuentePixelada.deriveFont(14f));
        TidAlumno.setPreferredSize(new Dimension(250, 30));
        doc = (PlainDocument) TidAlumno.getDocument();
        doc.setDocumentFilter(new LimitDocumentFilter(5));
        nombre= new JLabel("Nombre del alumno:");
        nombre.setForeground(Color.decode("#F5DFBB"));
        nombre.setFont(fuentePixelada.deriveFont(14f));
        TNombre=new JTextField();
        TNombre.setEditable(true);
        TNombre.setForeground(Color.decode("#391B49"));
        TNombre.setFont(fuentePixelada.deriveFont(14f));
        TNombre.setPreferredSize(new Dimension(250, 30));
        apP= new JLabel("Apellido paterno del alumno:");
        apP.setForeground(Color.decode("#F5DFBB"));
        apP.setFont(fuentePixelada.deriveFont(14f));
        TapP=new JTextField();
        TapP.setEditable(true);
        TapP.setForeground(Color.decode("#391B49"));
        TapP.setFont(fuentePixelada.deriveFont(14f));
        TapP.setPreferredSize(new Dimension(250, 30));
        apM= new JLabel("Apellido materno del alumno");
        apM.setForeground(Color.decode("#F5DFBB"));
        apM.setFont(fuentePixelada.deriveFont(14f));
        TapM=new JTextField();
        TapM.setEditable(true);
        TapM.setForeground(Color.decode("#391B49"));
        TapM.setFont(fuentePixelada.deriveFont(14f));
        TapM.setPreferredSize(new Dimension(250, 30));
       botonRegistrarAlumnos = new JButton("REGISTRAR");
       botonRegistrarAlumnos.setPreferredSize(new Dimension(100,40));
       botonRegistrarAlumnos.setBackground(Color.decode("#795690"));
       botonRegistrarAlumnos.setForeground(Color.decode("#F5DFBB"));
       botonRegistrarAlumnos.setFont(fuentePixelada.deriveFont(14f));
       botonRegistrarAlumnos.addActionListener(e ->Registrar());
        limpiar = new JButton("LIMPIAR");
        limpiar.setPreferredSize(new Dimension(100,40));
        limpiar.setBackground(Color.decode("#795690"));
        limpiar.setForeground(Color.decode("#F5DFBB"));
        limpiar.setFont(fuentePixelada.deriveFont(14f));
        limpiar.addActionListener(e ->limpiarCampos());
        setBackground(Color.decode("#9570C6"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx =0 ; gbc.gridy = 0; gbc.gridwidth =1 ; gbc.gridheight=1; gbc.fill = GridBagConstraints.BOTH;
        add(idAlumno,gbc);
        gbc.gridx =0 ; gbc.gridy = 1; gbc.gridwidth =1 ; gbc.gridheight=1; gbc.fill = GridBagConstraints.BOTH;
        add(nombre,gbc);
        gbc.gridx =0 ; gbc.gridy = 2; gbc.gridwidth =1 ; gbc.gridheight=1; gbc.fill = GridBagConstraints.BOTH;
        add(apP,gbc);
        gbc.gridx =0 ; gbc.gridy = 3; gbc.gridwidth =1 ; gbc.gridheight=1; gbc.fill = GridBagConstraints.BOTH;
        add(apM,gbc);
        gbc.gridx =1; gbc.gridy = 0; gbc.gridwidth =1 ; gbc.gridheight=1; gbc.fill = GridBagConstraints.BOTH;
        add(TidAlumno,gbc);
        gbc.gridx =1 ; gbc.gridy = 1; gbc.gridwidth =1 ; gbc.gridheight=1; gbc.fill = GridBagConstraints.BOTH;
        add(TNombre,gbc);
        gbc.gridx =1 ; gbc.gridy = 2; gbc.gridwidth =1 ; gbc.gridheight=1; gbc.fill = GridBagConstraints.BOTH;
        add(TapP,gbc);
        gbc.gridx =1 ; gbc.gridy = 3; gbc.gridwidth =1 ; gbc.gridheight=1; gbc.fill = GridBagConstraints.BOTH;
        add(TapM,gbc);
        gbc.gridx =0 ; gbc.gridy = 4; gbc.gridwidth =1 ; gbc.gridheight=1; gbc.fill = GridBagConstraints.BOTH;
        add(limpiar,gbc);
        gbc.gridx =1 ; gbc.gridy = 4; gbc.gridwidth =1 ; gbc.gridheight=1; gbc.fill = GridBagConstraints.BOTH;
        add(botonRegistrarAlumnos,gbc);
    }
    private void limpiarCampos(){
        TidAlumno.setText("");
        TNombre.setText("");
        TapP.setText("");
        TapM.setText("");
    }
    private void Registrar(){
        try{
            Alumno alumno = new Alumno();
            alumno.setNombre(TNombre.getText());
            alumno.setId(TidAlumno.getText());
            alumno.setApP(TapP.getText());
            alumno.setApM(TapM.getText());
            alumno.generarQRA();
            DBM D=new DBM();
            D.insertarAlumno(TidAlumno.getText(),TNombre.getText(),TapP.getText(),TapM.getText());
            JOptionPane.showMessageDialog(null,"ALUMNO REGISTRADO");
            limpiarCampos();
            D.closeConnection();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,e);
            limpiarCampos();
        }
    }
}
