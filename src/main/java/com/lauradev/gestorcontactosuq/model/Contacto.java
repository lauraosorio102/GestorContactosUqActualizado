package com.lauradev.gestorcontactosuq.model;

import javafx.scene.image.Image;
import java.time.LocalDate;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)

public class Contacto {

    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
    private String id;
    private LocalDate fechaCumpleanios;
    private Image imagenPefil;

    public Contacto(String nombre, String apellido, String id, String string, String email, String telefono) {
    }

    @Override
    public String toString() {
        return "Contacto{" + "nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", email='" + email + '\'' +
                ", telefono='" + telefono + '\'' +
                ", id='" + id + '\'' +
                ", apellido='" + apellido + '\'' +
                ", email='" + email + '\'' +
                ", id='" + id + '\'' +
                ", fechaCumpleanio=" + fechaCumpleanios +
                '}';
    }
}

