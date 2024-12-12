package PROYECTOPP;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class Qr {
        private String texto;
        private String rutaArchivo;
        private final int ancho = 400;
        private final int alto = 400;

    public void setRutaArchivo(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
    }
    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getRutaArchivo() {
        return rutaArchivo;
    }
    public String getTexto() {
        return texto;
    }
    public int getAlto() {
            return alto;
        }
        public int getAncho(){
            return ancho;
        }

    public void generarQR()
       throws WriterException, IOException {
        generarQR(null, null, 0, 0);
    }

    public void generarQR(String texto, String rutaArchivo, int ancho, int alto)
        throws WriterException, IOException {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(texto, BarcodeFormat.QR_CODE, ancho, alto);
            Path ruta = FileSystems.getDefault().getPath(rutaArchivo);
            MatrixToImageWriter.writeToPath(bitMatrix, "PNG", ruta);
        }
    public void leerQr(){

    }
}