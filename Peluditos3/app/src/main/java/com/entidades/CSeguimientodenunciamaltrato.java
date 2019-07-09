package com.entidades;

public class CSeguimientodenunciamaltrato extends CDenunciamaltrato {

    public String getSeguimiento() {
        return seguimiento;
    }

    public void setSeguimiento(String seguimiento) {
        this.seguimiento = seguimiento;
    }

    private String seguimiento;

    public String getIddenuncia() {
        return iddenuncia;
    }

    public void setIddenuncia(String iddenuncia) {
        this.iddenuncia = iddenuncia;
    }

    private String iddenuncia;
}
