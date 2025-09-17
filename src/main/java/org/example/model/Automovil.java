package org.example.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "automoviles")
public class Automovil {
    @Id
    private String matricula;
    private String marca;
    private String modelo;
    private String color;
    private double precio;

    public Automovil() {}

    public Automovil(String matricula, String marca, String modelo, String color, double precio) {
        this.matricula = matricula;
        this.marca = marca;
        this.modelo = modelo;
        this.color = color;
        this.precio = precio;
    }

    // getters y setters
    public String getMatricula() { return matricula; }
    public void setMatricula(String matricula) { this.matricula = matricula; }
    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }
    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }
    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }
    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }
}

