package PROYECTOPP;

import com.google.zxing.WriterException;

import java.io.IOException;

public class Alumno{
    private String id;
    private String nombre,apP,apM;//name
    private int tt;//tt: total of "trabajos" , td: day's "trabajos"
    private String qr;
    private int asistencias;
    /* setters and getters */
    public void setQr(String qr){
        this.qr = qr;
    }
    public String getQr(){
        return qr;
    }
   public void setNombre(String n){
        nombre = n;
   }
   public String getNombre(){
        return nombre;
   }
   public void setApP(String ap){
        apP = ap;
    }
    public String getApP(){
        return apP;
    }
    public void setApM(String am){
        apM= am;
    }
    public String getApM(){
        return apM;
    }
    public void setTt(int t){
        tt = t;
    }
    public int getTt(){
        return tt;
    }
    public void setId(String i){
        id = i;
    }
    public String getId(){
        return id;
    }
    public void generarQRA(){
        String p= this.getNombre()+this.getApP()+this.getApM()+".png";
        Qr q= new Qr();
        q.setTexto(this.getId());
        q.setRutaArchivo(p);
        try{
            q.generarQR(q.getTexto(),q.getRutaArchivo(),q.getAncho(),q.getAlto());
        } catch (WriterException e) {
            System.out.println("Error al generar el código QR: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error al guardar el archivo: " + e.getMessage());
        }
    }
}