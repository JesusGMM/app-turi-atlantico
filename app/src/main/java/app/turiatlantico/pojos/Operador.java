package app.turiatlantico.pojos;

public class Operador {
    private String direccion_comercial;
    private String geolocalizacion;
    private String operador_turistico;
    private String telefono;

    public Operador() {
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

    public String getOperador_turistico() {
        return operador_turistico;
    }

    public void setOperador_turistico(String operador_turistico) {
        this.operador_turistico = operador_turistico;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
