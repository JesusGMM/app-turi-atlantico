package app.turiatlantico.pojos;

public class Atractivo {

    private String descripcion;
    private String nombremunicipio;
    private String nombresitio;
    private String tipo;

    public Atractivo() {
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setNombremunicipio(String nombremunicipio) {
        this.nombremunicipio = nombremunicipio;
    }

    public void setNombresitio(String nombresitio) {
        this.nombresitio = nombresitio;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getNombremunicipio() {
        return nombremunicipio;
    }

    public String getNombresitio() {
        return nombresitio;
    }

    public String getTipo() {
        return tipo;
    }
}
