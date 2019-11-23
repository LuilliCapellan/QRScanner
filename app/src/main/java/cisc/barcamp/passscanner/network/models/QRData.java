package cisc.barcamp.passscanner.network.models;

import com.google.gson.annotations.SerializedName;

public class QRData {
    @SerializedName("id")
    private String id;
    @SerializedName("nombre")
    private String nombre;
    @SerializedName("cedula")
    private String cedula;
    @SerializedName("size")
    private String size;

    public QRData() {
    }

    public QRData(String id, String nombre, String cedula, String size) {
        this.id = id;
        this.nombre = nombre;
        this.cedula = cedula;
        this.size = size;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
