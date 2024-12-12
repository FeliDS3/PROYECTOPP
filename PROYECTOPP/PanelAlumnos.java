package PROYECTOPP;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PanelAlumnos extends JPanel {
    private JTable tablaAlumnos;
    private DefaultTableModel modeloTabla;
    private JScrollPane scrollPane;
    private JLabel titleLabel;

    public PanelAlumnos() {
        setLayout(new BorderLayout(10, 10));
        setPreferredSize(new Dimension(800,600));
        inicializarComponentes();
        setBackground(Color.decode("#C29CE4"));
        cargarDatos();
    }

    private void inicializarComponentes() {
        titleLabel = new JLabel("Listado de Alumnos");
        titleLabel.setForeground(Color.decode("#F5DFBB"));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        add(titleLabel, BorderLayout.NORTH);

        // Crear modelo de tabla
        String[] columnas = {"ID", "Nombre", "Apellido Paterno",
                "Apellido Materno", "Actividades", "Asistencias"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hacer la tabla no editable
            }
        };

        tablaAlumnos = new JTable(modeloTabla);
        tablaAlumnos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaAlumnos.getTableHeader().setReorderingAllowed(false);
        tablaAlumnos.setRowHeight(25);
        tablaAlumnos.setIntercellSpacing(new Dimension(10, 0));
        tablaAlumnos.setGridColor(Color.decode("#391B49"));
        tablaAlumnos.setForeground(Color.decode("#F5DFBB"));
        tablaAlumnos.setBackground(Color.decode("#795690"));
        TableColumnModel columnModel = tablaAlumnos.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(100);  // ID
        columnModel.getColumn(1).setPreferredWidth(150); // Nombre
        columnModel.getColumn(2).setPreferredWidth(150); // Apellido Paterno
        columnModel.getColumn(3).setPreferredWidth(150); // Apellido Materno
        columnModel.getColumn(4).setPreferredWidth(100); // Actividades
        columnModel.getColumn(5).setPreferredWidth(100); // Asistencias
        scrollPane = new JScrollPane(tablaAlumnos);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(scrollPane, BorderLayout.CENTER);
    }

    public void cargarDatos() {
        try {
            modeloTabla.setRowCount(0);
            DBM dbm = new DBM();
            List<Map<String, Object>> alumnos = dbm.obtenerTodosLosAlumnos();

            // Agregar cada alumno a la tabla
            for (Map<String, Object> alumno : alumnos) {
                Object[] fila = {
                        alumno.get("id"),
                        alumno.get("nombre"),
                        alumno.get("apPaterno"),
                        alumno.get("apMaterno"),
                        alumno.get("actividades"),
                        alumno.get("asistencias")
                };
                modeloTabla.addRow(fila);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error al cargar los datos: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    public void actualizarDatos() {
        cargarDatos();
    }
    public Map<String, Object> getAlumnoSeleccionado() {
        int filaSeleccionada = tablaAlumnos.getSelectedRow();
        if (filaSeleccionada != -1) {
            Map<String, Object> alumno = new HashMap<>();
            alumno.put("id", tablaAlumnos.getValueAt(filaSeleccionada, 0));
            alumno.put("nombre", tablaAlumnos.getValueAt(filaSeleccionada, 1));
            alumno.put("apPaterno", tablaAlumnos.getValueAt(filaSeleccionada, 2));
            alumno.put("apMaterno", tablaAlumnos.getValueAt(filaSeleccionada, 3));
            alumno.put("actividades", tablaAlumnos.getValueAt(filaSeleccionada, 4));
            alumno.put("asistencias", tablaAlumnos.getValueAt(filaSeleccionada, 5));
            return alumno;
        }
        return null;
    }

    public void mostrarDetalleAlumno() {
        Map<String, Object> alumno = getAlumnoSeleccionado();
        if (alumno != null) {
            // Crear el diálogo
            JDialog dialogoDetalle = new JDialog((Frame) SwingUtilities.getWindowAncestor(this),
                    "Detalle del Alumno",
                    true);
            dialogoDetalle.setLayout(new BorderLayout(10, 10));
            JPanel panelDetalle = new JPanel(new GridBagLayout());
            panelDetalle.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.anchor = GridBagConstraints.WEST;
            gbc.insets = new Insets(5, 5, 5, 5);

            Font labelFont = new Font("Arial", Font.BOLD, 12);
            Font valueFont = new Font("Arial", Font.PLAIN, 12);
            addField(panelDetalle, gbc, "ID:", alumno.get("id").toString(), 0, labelFont, valueFont);
            addField(panelDetalle, gbc, "Nombre:", alumno.get("nombre").toString(), 1, labelFont, valueFont);
            addField(panelDetalle, gbc, "Apellido Paterno:", alumno.get("apPaterno").toString(), 2, labelFont, valueFont);
            addField(panelDetalle, gbc, "Apellido Materno:", alumno.get("apMaterno").toString(), 3, labelFont, valueFont);
            addField(panelDetalle, gbc, "Total Actividades:", alumno.get("actividades").toString(), 4, labelFont, valueFont);
            addField(panelDetalle, gbc, "Total Asistencias:", alumno.get("asistencias").toString(), 5, labelFont, valueFont);

            DBM d= new DBM();
            int actividades = Integer.parseInt(alumno.get("actividades").toString());
            int asistencias = Integer.parseInt(alumno.get("asistencias").toString());
            int t= d.obtenerTotalActividades();
            int tA=d.obtenerTotalAsistencias();
            System.out.println(t);
            System.out.println(actividades);
            float porcentajeA=((float)asistencias/tA)*100;
            float porcentaje =((float)actividades/t)*100;
            System.out.println(porcentaje);
            addField(panelDetalle, gbc, "Porcentaje de Actividades:",String.format("%.2f%%", porcentaje), 6, labelFont, valueFont);
            addField(panelDetalle, gbc, "Porcentaje de Asistencias:",String.format("%.2f%%", porcentajeA), 7, labelFont, valueFont);
            JButton btnCerrar = new JButton("Cerrar");
            btnCerrar.addActionListener(e -> dialogoDetalle.dispose());
            JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.CENTER));
            panelBoton.add(btnCerrar);
            dialogoDetalle.add(panelDetalle, BorderLayout.CENTER);
            dialogoDetalle.add(panelBoton, BorderLayout.SOUTH);
            dialogoDetalle.pack();
            dialogoDetalle.setLocationRelativeTo(this);
            dialogoDetalle.setMinimumSize(new Dimension(400, 300));
            dialogoDetalle.setResizable(false);
            dialogoDetalle.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this,
                    "Por favor, seleccione un alumno de la tabla",
                    "Aviso",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void addField(JPanel panel, GridBagConstraints gbc, String label, String value, int row, Font labelFont, Font valueFont) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 1;
        JLabel lblField = new JLabel(label);
        lblField.setFont(labelFont);
        panel.add(lblField, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        JLabel lblValue = new JLabel(value);
        lblValue.setFont(valueFont);
        panel.add(lblValue, gbc);
        gbc.weightx = 0.0;
    }
}