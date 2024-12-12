package PROYECTOPP;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Asistencias extends JPanel{
    private JCheckBox cActividad;
    private JButton NuevaAsistencia, Listo;
    private RegistrarAsist a;
    private RegistrarTrabajos t;
    private Font fuentePixelada;
    public Asistencias(){
        try {
            fuentePixelada = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("Winter Lemon.ttf")).deriveFont(14f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(fuentePixelada);
        } catch (Exception e) {
            e.printStackTrace();
            fuentePixelada = new Font("Monospaced", Font.PLAIN, 12);
        }
        cActividad = new JCheckBox();
        cActividad.setSelected(false);
        cActividad.setText("Asistencia y Actividad");
        cActividad.setForeground(Color.decode("#F5DFBB"));
        cActividad.setBackground(Color.decode("#795690"));
        cActividad.setFont(fuentePixelada.deriveFont(14f));
        NuevaAsistencia = new JButton();
        NuevaAsistencia.setText("Nueva Asistencia");
        NuevaAsistencia.setPreferredSize(new Dimension(100,40));
        NuevaAsistencia.setBackground(Color.decode("#795690"));
        NuevaAsistencia.setForeground(Color.decode("#F5DFBB"));
        NuevaAsistencia.setFont(fuentePixelada.deriveFont(14f));
        NuevaAsistencia.addActionListener(e ->botonAsistencias());
        Listo = new JButton();
        Listo.setText("Trabajo Listo");
        Listo.setPreferredSize(new Dimension(100,40));
        Listo.setBackground(Color.decode("#795690"));
        Listo.setForeground(Color.decode("#F5DFBB"));
        Listo.setFont(fuentePixelada.deriveFont(14f));
        Listo.setVisible(false);
        Listo.addActionListener(e ->botonListo());
        a = new RegistrarAsist();
        a.setVisible(false);
        t = new RegistrarTrabajos();
        t.setVisible(false);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        setBackground(Color.decode("#C29CE4"));
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx =0 ; gbc.gridy = 0; gbc.gridwidth =1 ; gbc.gridheight=1; gbc.fill = GridBagConstraints.BOTH;
        add(cActividad,gbc);
        gbc.gridx =0 ; gbc.gridy = 1; gbc.gridwidth =1 ; gbc.gridheight=1; gbc.fill = GridBagConstraints.BOTH;
        add(NuevaAsistencia,gbc);
        gbc.gridx =0 ; gbc.gridy = 2; gbc.gridwidth =1 ; gbc.gridheight=1; gbc.fill = GridBagConstraints.BOTH;
        add(Listo,gbc);
        gbc.gridx =1 ; gbc.gridy = 0; gbc.gridwidth =1 ; gbc.gridheight=3; gbc.fill = GridBagConstraints.BOTH;
        add(t,gbc);
        gbc.gridx =1 ; gbc.gridy = 0; gbc.gridwidth =1 ; gbc.gridheight=3; gbc.fill = GridBagConstraints.BOTH;
        add(a,gbc);
        setPreferredSize(new Dimension(800,600));
    }
    public void botonAsistencias(){
        Asistencia aa = new Asistencia();
        DBM d=new DBM();
        d.registrarAsistencia(aa.getDate());
        if(cActividad.isSelected()){
            NuevaAsistencia.setEnabled(false);
            t.setVisible(true);
            t.setTFechaTrabajo(aa.getDate());
            a.setEA(true);
            Listo.setVisible(true);
        }else{
            NuevaAsistencia.setEnabled(false);
            t.setVisible(false);
            a.setVisible(true);
        }
    }
    public void botonListo(){
        t.setVisible(false);
        a.setVisible(true);
        DBM d=new DBM();
        Listo.setEnabled(false);
        if(!Listo.isEnabled()){
            //d.obtenerUltimoIdTrabajo();
            int ut= d.obtenerUltimoIdTrabajo()+1;
            String temp= String.valueOf(ut);
            a.setTIdActividad(temp);
            a.setEA(true);
        }
    }
}
