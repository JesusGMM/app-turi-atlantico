package app.turiatlantico.pojos;

public class Operador {
    private String direccion_comercial;
    private String geolocalizacion;
    private String nombre;
    private String telefono;
    private String id;
    public Operador() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDireccion_comercial() {
        return direccion_comercial;
    }

    public void setDireccion_comercial(String direccion_comercial) {
        this.direccion_comercial = direccion_comercial;
    }

    public String getGeolocalizacion() {
        return geolocalizacion;
    }

    public void setGeolocalizacion(String geolocalizacion) {
        this.geolocalizacion = geolocalizacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
