package com.lauradev.gestorcontactosuq.model;

import javafx.scene.image.Image;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class GestorContactos {

    private ArrayList<Contacto> contactos;

    public GestorContactos() {
        contactos = new ArrayList<>();
    }


    public void agregarContacto(String nombre, String apellido, String id, String email, String telefono, LocalDate fechaCumpleano, Image imagen) throws Exception {
        if(!verificarContacto(telefono)){
            if(nombre.isEmpty() || apellido.isEmpty() || email.isEmpty()){
                throw new Exception("Todos los campos son obligatorios");
            }
            if(!validarCorreo(email)){
                throw new Exception("Email invalido");
            }
            if(fechaCumpleano==null){
                throw new Exception("La fecha de cumpleaños es obligatoria");
            }
            if(!validarTelefono(telefono)){
                throw new Exception("Telefono invalido");
            }
            if(imagen == null) {
                imagen = new Image(getClass().getResource("/imagenproyecto.jpg").toExternalForm());
            }
            Contacto contacto= Contacto.builder()
                    .id(id)
                    .nombre(nombre)
                    .apellido(apellido)
                    .email(email)
                    .telefono(telefono)
                    .fechaCumpleanios(fechaCumpleano)
                    .imagenPefil(imagen)
                    .build();
            contactos.add(contacto);
        } else{
            throw new Exception("Ya existe un contacto con este numero de telefono");
        }
    }
    public void editarContacto(String id, String nombre, String apellido, String email,
                               String telefono, LocalDate fechaCumpleano,Image imagen) throws Exception {
        int contactoBuscado = buscarContactoid(id);

        if(contactoBuscado == -1) {
            throw new Exception("No existe el contacto");
        }

        // Validaciones (consistentes con agregarContacto)
        if(nombre.isEmpty() || apellido.isEmpty() || email.isEmpty()) {
            throw new Exception("Todos los campos son obligatorios");
        }

        if(!validarCorreo(email)) {
            throw new Exception("Email inválido");
        }

        if(fechaCumpleano == null) {
            throw new Exception("La fecha de cumpleaños es obligatoria");
        }

        if(!validarTelefono(telefono)) {
            throw new Exception("Teléfono inválido");
        }
        if(imagen == null) {
            imagen = new Image(getClass().getResource("/imagenproyecto.jpg").toExternalForm());
        }
        // Verificar si el id ya existe en otro contacto
        Contacto contactoExistente = buscarContacto(id);
        if(contactoExistente != null && !contactoExistente.getId().equals(id)) {
            throw new Exception("Ya existe un contacto con este número de teléfono");
        }

        // Actualizar el contacto
        Contacto contacto = contactos.get(contactoBuscado);
        contacto.setNombre(nombre);
        contacto.setApellido(apellido);
        contacto.setEmail(email);
        contacto.setTelefono(telefono);
        contacto.setFechaCumpleanios(fechaCumpleano);
        contacto.setImagenPefil(imagen);
    }


    public Contacto buscarContacto(String id) {
        return contactos.stream()
                .filter(contacto -> contacto.getId().equals(id)).findFirst().orElse(null);
    }

    public List<Contacto> obtenerContactos() {
        return contactos;
    }


    public Contacto buscarContactoTelefono(String telefono){
        return contactos
                .stream()
                .filter(c -> c.getTelefono().equals(telefono))
                .findFirst().orElse(null);
    }

    public List<Contacto> buscarContactoTelefonolista(String telefono){
        return contactos
                .stream()
                .filter(c -> c.getTelefono().equals(telefono))
                .collect(Collectors.toList());
    }
    public List<Contacto> buscarContactoNombre(String nombre){
        return contactos
                .stream()
                .filter(c -> c.getNombre().equalsIgnoreCase(nombre))
                .collect(Collectors.toList());
    }

    public boolean verificarContacto(String telefono){
        boolean encontrado=false;
        for(Contacto contacto: contactos){
            if(contacto.getTelefono().equals(telefono)){
                return true;
            }
        }
        return encontrado;
    }

    //Validaciones

    public static boolean validarTelefono(String telefono){
        String regexTelefono="^\\+?\\d{1,3}?\\d{7,15}$";
        Pattern expresionValida=Pattern.compile(regexTelefono);
        Matcher matcherTelefono=expresionValida.matcher(telefono);
        boolean valido;
        valido=matcherTelefono.matches();
        return valido;
    }

    public static boolean validarCorreo(String correo){
        String regexEmail="^[a-zA-Z0-9._%+-]+@[a-zA-z0-9.-]+\\.[a-zA-Z]{2,6}$";
        Pattern expresionValida=Pattern.compile(regexEmail);
        Matcher matcher=expresionValida.matcher(correo);
        boolean valido;
        valido= matcher.matches();
        return valido;
    }

    public static boolean validarTexto(String texto){
        String regexTexto="[a-zA-ZñÑ]+";
        Pattern expresionValida= Pattern.compile(regexTexto);
        Matcher matcher= expresionValida.matcher(texto);
        return matcher.matches();
    }
    public List<Contacto> listarContactos(){
        return contactos;
    }
    public int buscarContactoid(String id){
        int posContacto=0;
        for(int i=0; i<contactos.size(); i++){
            if(contactos.get(i).getId().equals(id)){
                return posContacto=i;
            }
        }
        return-1;

    }

}
