package org.fabianmarroquin.bean;


public class Vehiculos {
    private String placa;
    private String marca;
    private String modelo;
    private String tipoDeVehiculo;
    private String NIT;

    public Vehiculos() {
    }

    public Vehiculos(String placa, String marca, String modelo, String tipoDeVehiculo, String NIT) {
        this.placa = placa;
        this.marca = marca;
        this.modelo = modelo;
        this.tipoDeVehiculo = tipoDeVehiculo;
        this.NIT = NIT;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getTipoDeVehiculo() {
        return tipoDeVehiculo;
    }

    public void setTipoDeVehiculo(String tipoDeVehiculo) {
        this.tipoDeVehiculo = tipoDeVehiculo;
    }

    public String getNIT() {
        return NIT;
    }

    public void setNIT(String NIT) {
        this.NIT = NIT;
    }
    
    
}
