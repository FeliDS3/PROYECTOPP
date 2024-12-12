package PROYECTOPP;
import javax.swing.*;
import java.awt.*;
public class RegistrarTrabajos extends JPanel {
    private JLabel nombreTrabajo, valorTrabajo, fechaTrabajo;
    private JButton registrarTrabajo, limpiar;
    private JTextField TnombreTrabajo, TvalorTrabajo, TfechaTrabajo;
    private Font fuentePixelada;
    public RegistrarTrabajos(){
        try {
            fuentePixelada = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("Winter Lemon.ttf")).deriveFont(14f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(fuentePixelada);
        } catch (Exception e) {
            e.printStackTrace();
            fuentePixelada = new Font("Monospaced", Font.PLAIN, 12);
        }
        setLayout(new GridBagLayout());
        setPreferredSize(new Dimension(800, 800));
        nombreTrabajo = new JLabel("Nombre del trabajo");
        nombreTrabajo.setForeground(Color.decode("#F5DFBB"));
        nombreTrabajo.setFont(fuentePixelada.deriveFont(14f));
        TnombreTrabajo = new JTextField();
        TnombreTrabajo.setEditable(true);
        TnombreTrabajo.setForeground(Color.decode("#391B49"));
        TnombreTrabajo.setPreferredSize(new Dimension(250, 30));
        TnombreTrabajo.setFont(fuentePixelada.deriveFont(14f));
        valorTrabajo = new JLabel("Valor del trabajo");
        valorTrabajo.setForeground(Color.decode("#F5DFBB"));
        TvalorTrabajo = new JTextField();
         TvalorTrabajo.setEditable(true);
         TvalorTrabajo.setText("100");
        TvalorTrabajo.setForeground(Color.decode("#391B49"));
        TvalorTrabajo.setPreferredSize(new Dimension(250, 30));
        TvalorTrabajo.setFont(fuentePixelada.deriveFont(14f));
       fechaTrabajo = new JLabel("fecha del trabajo");
        fechaTrabajo.setForeground(Color.decode("#F5DFBB"));
        fechaTrabajo.setFont(fuentePixelada.deriveFont(14f));
        TfechaTrabajo = new JTextField();
        TfechaTrabajo.setEditable(true);
        TfechaTrabajo.setForeground(Color.decode("#391B49"));
        TfechaTrabajo.setPreferredSize(new Dimension(250, 30));
        TfechaTrabajo.setFont(fuentePixelada.deriveFont(14f));
        registrarTrabajo = new JButton("REGISTRAR");
        registrarTrabajo.setPreferredSize(new Dimension(100,40));
        registrarTrabajo.setBackground(Color.decode("#795690"));
        registrarTrabajo.setForeground(Color.decode("#F5DFBB"));
        registrarTrabajo.addActionListener(e ->RegistrarT());
        registrarTrabajo.setFont(fuentePixelada.deriveFont(14f));
        limpiar = new JButton("LIMPIAR");
        limpiar.setPreferredSize(new Dimension(100,40));
        limpiar.setBackground(Color.decode("#795690"));
        limpiar.setForeground(Color.decode("#F5DFBB"));
        limpiar.addActionListener(e ->limpiarCampos());
        limpiar.setFont(fuentePixelada.deriveFont(14f));
        setBackground(Color.decode("#C29CE4"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx =0 ; gbc.gridy = 0; gbc.gridwidth =1 ; gbc.gridheight=1; gbc.fill = GridBagConstraints.BOTH;
        add(nombreTrabajo,gbc);
        gbc.gridx =1 ; gbc.gridy = 0; gbc.gridwidth =1 ; gbc.gridheight=1; gbc.fill = GridBagConstraints.BOTH;
        add(TnombreTrabajo,gbc);
        gbc.gridx =0 ; gbc.gridy = 1; gbc.gridwidth =1 ; gbc.gridheight=1; gbc.fill = GridBagConstraints.BOTH;
        add(valorTrabajo,gbc);
        gbc.gridx =1 ; gbc.gridy = 1; gbc.gridwidth =1 ; gbc.gridheight=1; gbc.fill = GridBagConstraints.BOTH;
        add(TvalorTrabajo,gbc);
        gbc.gridx =0 ; gbc.gridy = 2; gbc.gridwidth =1 ; gbc.gridheight=1; gbc.fill = GridBagConstraints.BOTH;
        add(fechaTrabajo,gbc);
        gbc.gridx =1 ; gbc.gridy = 2; gbc.gridwidth =1 ; gbc.gridheight=1; gbc.fill = GridBagConstraints.BOTH;
        add(TfechaTrabajo,gbc);
        gbc.gridx =0 ; gbc.gridy = 3; gbc.gridwidth =1 ; gbc.gridheight=1; gbc.fill = GridBagConstraints.BOTH;
        add(registrarTrabajo,gbc);
        gbc.gridx =1 ; gbc.gridy = 3; gbc.gridwidth =1 ; gbc.gridheight=1; gbc.fill = GridBagConstraints.BOTH;
        add(limpiar,gbc);
    }
    private void RegistrarT(){
        try{
            DBM D = new DBM();
            int n= Integer.parseInt(TvalorTrabajo.getText());
            D.insertarTrabajo(TnombreTrabajo.getText(),TfechaTrabajo.getText(),n);
            JOptionPane.showMessageDialog(null,"Trabajo registrado exitosamente");
            limpiarCampos();
        } catch (Exception e) {
            System.out.println(e);
            JOptionPane.showMessageDialog(null, "Error al registrar el trabajo");
            limpiarCampos();
        }
    }
    private void limpiarCampos(){
        TnombreTrabajo.setText("");
        TvalorTrabajo.setText("");
        TfechaTrabajo.setText("");
    }
    public void setTFechaTrabajo(String fechaTrabajo){
        TfechaTrabajo.setText(fechaTrabajo);
        TfechaTrabajo.setEditable(true);
    }
    public void setBRE(boolean a){
        registrarTrabajo.setEnabled(a);
    }

    public boolean getBRE(){
        return registrarTrabajo.isEnabled();
    }
}
