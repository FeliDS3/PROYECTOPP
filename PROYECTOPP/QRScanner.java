package PROYECTOPP;
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamException;
import com.github.sarxos.webcam.WebcamPanel;
import com.google.zxing.*;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import javax.swing.*;
import java.awt.*;
import java.awt.Dimension;
import java.awt.image.BufferedImage;

public class QRScanner {
    private JFrame frame;
    private Webcam webcam;
    private JLabel Label;
    private String texto="";
    private JButton listo;

    public void setTexto(String texto) {
        this.texto = texto;
    }
    public String getTexto() {
        return texto;
    }

    public QRScanner() {
        initialize();
    }
    private void initialize() {
        // Crear la ventana principal
        frame = new JFrame("Escáner de QR");
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setSize(800, 600);
        // Configurar la cámara
        this.webcam = Webcam.getDefault();
        if (this.webcam == null) {
            throw new WebcamException("No webcam detected. Please check your device connection.");
        }
        this.webcam.setViewSize(new Dimension(640, 480));
        webcam.setViewSize(new Dimension(640, 480));
        WebcamPanel webcamPanel = new WebcamPanel(webcam);
        webcamPanel.setFPSDisplayed(true);

        // Etiqueta para mostrar resultados
        Label = new JLabel("Escanea un código QR...", JLabel.CENTER);
        Label.setFont(new Font("Arial", Font.BOLD, 18));

        // Añadir componentes al frame
        frame.add(webcamPanel, BorderLayout.CENTER);
        frame.add(Label, BorderLayout.SOUTH);

        frame.setVisible(true);
        new Thread(this::scanQRCode).start();
    }

    public void scanQRCode() {
        while (true) {
            if (webcam.isOpen()) {
                BufferedImage image = webcam.getImage();
                if (image != null) {
                    String qrText = decodeQRCode(image);
                    if (qrText != null) {
                        Label.setText("Código QR detectado: " + qrText);
                        break; // Salimos del bucle si se encuentra un QR
                    }
                }
            }
            try {
                Thread.sleep(100); // Pequeña pausa para evitar saturar el procesador
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        webcam.close(); // Cerrar la cámara después de escanear
    }

    public String decodeQRCode(BufferedImage image) {
        try {
            LuminanceSource source = new BufferedImageLuminanceSource(image);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
            Result result = new MultiFormatReader().decode(bitmap);
            setTexto(result.getText());
            RegistrarAsist.tidAlumno.setText(texto);
            return result.getText();
        } catch (NotFoundException e) {
            // No se encontró un código QR en la imagen
            return null;
        }
    }

}