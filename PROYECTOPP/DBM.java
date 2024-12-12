package PROYECTOPP;
import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBM {
    private Connection connection;
    private static final String DB_FILE = "miBaseDatos.db";
    public DBM() {
        try {

            connection = DriverManager.getConnection("jdbc:sqlite:" + DB_FILE);


            createTables();
            createTriggers();

        } catch (SQLException e) {
            System.err.println("Error al inicializar la base de datos: " + e.getMessage());
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    private void createTables() {
        try (Statement stmt = connection.createStatement()) {

            stmt.execute("CREATE TABLE IF NOT EXISTS ALUMNOS (" +
                    "idAlumno TEXT PRIMARY KEY," +
                    "nombre TEXT NOT NULL," +
                    "apP TEXT NOT NULL," +
                    "apM TEXT NOT NULL," +
                    "totalActividades INTEGER," +
                    "totalAsistencias INTEGER" +
                    ")");


            stmt.execute("CREATE TABLE IF NOT EXISTS TRABAJOS (" +
                    "idTrabajo INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "nombreTrabajo TEXT UNIQUE NOT NULL," +
                    "fechaTrabajo TEXT," +
                    "valor INTEGER DEFAULT 100" +
                    ")");


            stmt.execute("CREATE TABLE IF NOT EXISTS ASISTENCIAS (" +
                    "fecha TEXT PRIMARY KEY" +
                    ")");


            stmt.execute("CREATE TABLE IF NOT EXISTS ASISTENCIASALUMNOS (" +
                    "idAl TEXT NOT NULL," +
                    "fechaA TEXT NOT NULL," +
                    "PRIMARY KEY (idAl, fechaA)," +
                    "FOREIGN KEY (fechaA) REFERENCES ASISTENCIAS(fecha)," +
                    "FOREIGN KEY (idAl) REFERENCES ALUMNOS(idAlumno)" +
                    ")");


            stmt.execute("CREATE TABLE IF NOT EXISTS TRABAJOSALUMNOS (" +
                    "idAlu TEXT NOT NULL," +
                    "idT INTEGER NOT NULL," +
                    "PRIMARY KEY (idAlu, idT)," +
                    "FOREIGN KEY (idT) REFERENCES TRABAJOS(idTrabajo)," +
                    "FOREIGN KEY (idAlu) REFERENCES ALUMNOS(idAlumno)" +
                    ")");

        } catch (SQLException e) {
            System.err.println("Error al crear las tablas: " + e.getMessage());
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    // Metodo para insertar un alumno
    public void insertarAlumno(String id, String nombre, String apP, String apM) {
        String sql = "INSERT INTO ALUMNOS(idAlumno, nombre, apP, apM, totalActividades, totalAsistencias) VALUES(?,?,?,?,0,0)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, id);
            pstmt.setString(2, nombre);
            pstmt.setString(3, apP);
            pstmt.setString(4, apM);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al insertar alumno: " + e.getMessage());
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    // Metodo para cerrar la conexión
    public void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            System.err.println("Error al cerrar la conexión: " + e.getMessage());
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }//

    private void createTriggers() {
        try (Statement stmt = connection.createStatement()) {
            // Trigger para actualizar totalActividades después de INSERT
            stmt.execute(
                    "CREATE TRIGGER IF NOT EXISTS after_trabajosalumnos_insert " +
                            "AFTER INSERT ON TRABAJOSALUMNOS " +
                            "BEGIN " +
                            "   UPDATE ALUMNOS " +
                            "   SET totalActividades = (" +
                            "       SELECT COALESCE(SUM(T.valor), 0) " +
                            "       FROM TRABAJOSALUMNOS TA " +
                            "       JOIN TRABAJOS T ON TA.idT = T.idTrabajo " +
                            "       WHERE TA.idAlu = NEW.idAlu" +
                            "   ) " +
                            "   WHERE idAlumno = NEW.idAlu; " +
                            "END;"
            );

            // Trigger para actualizar totalActividades después de DELETE
            stmt.execute(
                    "CREATE TRIGGER IF NOT EXISTS after_trabajosalumnos_delete " +
                            "AFTER DELETE ON TRABAJOSALUMNOS " +
                            "BEGIN " +
                            "   UPDATE ALUMNOS " +
                            "   SET totalActividades = (" +
                            "       SELECT COALESCE(SUM(T.valor), 0) " +
                            "       FROM TRABAJOSALUMNOS TA " +
                            "       JOIN TRABAJOS T ON TA.idT = T.idTrabajo " +
                            "       WHERE TA.idAlu = OLD.idAlu" +
                            "   ) " +
                            "   WHERE idAlumno = OLD.idAlu; " +
                            "END;"
            );

            // Trigger para actualizar totalAsistencias después de INSERT
            stmt.execute(
                    "CREATE TRIGGER IF NOT EXISTS after_asistenciasalumnos_insert " +
                            "AFTER INSERT ON ASISTENCIASALUMNOS " +
                            "BEGIN " +
                            "   UPDATE ALUMNOS " +
                            "   SET totalAsistencias = (" +
                            "       SELECT COUNT(*) " +
                            "       FROM ASISTENCIASALUMNOS " +
                            "       WHERE idAl = NEW.idAl" +
                            "   ) " +
                            "   WHERE idAlumno = NEW.idAl; " +
                            "END;"
            );

            // Trigger para actualizar totalAsistencias después de DELETE
            stmt.execute(
                    "CREATE TRIGGER IF NOT EXISTS after_asistenciasalumnos_delete " +
                            "AFTER DELETE ON ASISTENCIASALUMNOS " +
                            "BEGIN " +
                            "   UPDATE ALUMNOS " +
                            "   SET totalAsistencias = (" +
                            "       SELECT COUNT(*) " +
                            "       FROM ASISTENCIASALUMNOS " +
                            "       WHERE idAl = OLD.idAl" +
                            "   ) " +
                            "   WHERE idAlumno = OLD.idAl; " +
                            "END;"
            );

        } catch (SQLException e) {
            System.err.println("Error al crear los triggers: " + e.getMessage());
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    // Métodos de prueba para verificar los triggers
    public void insertarTrabajo(String nombreTrabajo, String fecha, int valor) {
        String sql = "INSERT INTO TRABAJOS(nombreTrabajo, fechaTrabajo, valor) VALUES(?,?,?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, nombreTrabajo);
            pstmt.setString(2, fecha);
            pstmt.setInt(3, valor);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al insertar trabajo: " + e.getMessage());
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public void asignarTrabajoAlumno(String idAlumno, int idTrabajo) {
        String sql = "INSERT INTO TRABAJOSALUMNOS(idAlu, idT) VALUES(?,?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, idAlumno);
            pstmt.setInt(2, idTrabajo);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al asignar trabajo: " + e.getMessage());
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public void registrarAsistencia(String fecha) {
        String sql = "INSERT INTO ASISTENCIAS(fecha) VALUES(?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, fecha);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al registrar fecha de asistencia: " + e.getMessage());
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public void registrarAsistenciaAlumno(String idAlumno, String fecha) {
        String sql = "INSERT INTO ASISTENCIASALUMNOS(idAl, fechaA) VALUES(?,?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, idAlumno);
            pstmt.setString(2, fecha);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    public int obtenerUltimoIdTrabajo() {
        String sql = "SELECT last_insert_rowid() as lastId";
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                return rs.getInt("lastId");
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener último ID: " + e.getMessage());
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return -1;
    }

    public void limpiarDatos() {
        try (Statement stmt = connection.createStatement()) {
            // Desactivar las restricciones de clave foránea temporalmente
            stmt.execute("PRAGMA foreign_keys = OFF;");

            // Eliminar todos los datos de las tablas en orden
            stmt.execute("DELETE FROM TRABAJOSALUMNOS;");
            stmt.execute("DELETE FROM ASISTENCIASALUMNOS;");
            stmt.execute("DELETE FROM TRABAJOS;");
            stmt.execute("DELETE FROM ASISTENCIAS;");
            stmt.execute("DELETE FROM ALUMNOS;");

            // Reiniciar los contadores autoincrement
            stmt.execute("DELETE FROM sqlite_sequence WHERE name IN ('TRABAJOS');");

            // Reactivar las restricciones de clave foránea
            stmt.execute("PRAGMA foreign_keys = ON;");

            System.out.println("Datos eliminados exitosamente.");

        } catch (SQLException e) {
            System.err.println("Error al limpiar los datos: " + e.getMessage());
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public void mostrarTodosLosAlumnos() {
        String sql = "SELECT * FROM ALUMNOS";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            // Imprimir encabezado
            System.out.println("\n=== LISTA DE ALUMNOS ===");
            System.out.printf("%-8s %-15s %-15s %-15s %-15s %-15s%n",
                    "ID", "NOMBRE", "AP. PATERNO", "AP. MATERNO", "ACTIVIDADES", "ASISTENCIAS");
            System.out.println("=".repeat(80));

            // Imprimir cada alumno
            while (rs.next()) {
                System.out.printf("%-8s %-15s %-15s %-15s %-15d %-15d%n",
                        rs.getString("idAlumno"),
                        rs.getString("nombre"),
                        rs.getString("apP"),
                        rs.getString("apM"),
                        rs.getInt("totalActividades"),
                        rs.getInt("totalAsistencias")
                );
            }
            System.out.println("=".repeat(80));

        } catch (SQLException e) {
            System.err.println("Error al mostrar alumnos: " + e.getMessage());
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public  List<Map<String, Object>> obtenerTodosLosAlumnos() {
        List<Map<String, Object>> alumnos = new ArrayList<>();
        String sql = "SELECT * FROM ALUMNOS";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Map<String, Object> alumno = new HashMap<>();
                alumno.put("id", rs.getString("idAlumno"));
                alumno.put("nombre", rs.getString("nombre"));
                alumno.put("apPaterno", rs.getString("apP"));
                alumno.put("apMaterno", rs.getString("apM"));
                alumno.put("actividades", rs.getInt("totalActividades"));
                alumno.put("asistencias", rs.getInt("totalAsistencias"));
                alumnos.add(alumno);
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener alumnos: " + e.getMessage());
        }

        return alumnos;
    }

    public int obtenerTotalActividades() {
        String sql = "SELECT SUM(valor) as total FROM TRABAJOS";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt("total");
                //System.out.println(rs.getInt("total"));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener total de trabajos: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Error al obtener total de trabajos: " + e.getMessage());
        }
        return 0;
    }

    public int obtenerTotalAsistencias() {
        String sql = "SELECT COUNT(*) as total FROM ASISTENCIAS";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt("total");
                //System.out.println(rs.getInt("total"));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener total de asistencias: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Error al obtener total de asistencias: " + e.getMessage());
        }
        return 0;
    }


}//clase
