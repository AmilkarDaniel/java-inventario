package test;

import datos.Conexion;
import datos.PersonaDao;
import datos.PersonaDaoJDBC;
import dominio.PersonaDTO;
import java.sql.*;
import java.util.List;

public class ManejoPersonas {
    public static void main(String[] args) {

        Connection conexion = null;
        try {
            conexion = Conexion.getConnection();
            if (conexion.getAutoCommit()) {
                conexion.setAutoCommit(false);
            }
//INSERT
//            PersonaDao personaDao = new PersonaDaoJDBC(conexion);
//            PersonaDTO nuevoPersona= new PersonaDTO();
//            nuevoPersona.setCodigo("per3");
//            nuevoPersona.setNombre("nombre persona 3");
//            nuevoPersona.setApellido("apellido persona 3");
//            nuevoPersona.setTelefono(33333333);
//            nuevoPersona.setGda(1);
//            
//            personaDao.insert(nuevoPersona);
//
//            
//            conexion.commit();

//UPDATE
            PersonaDao personaDao = new PersonaDaoJDBC(conexion);
            PersonaDTO actualizarPersona= new PersonaDTO();
            actualizarPersona.setCodigo("per4");
            actualizarPersona.setNombre("nombre persona 4");
            actualizarPersona.setApellido("apellido persona 4");
            actualizarPersona.setTelefono(44444444);
            actualizarPersona.setDireccion("4di");
            actualizarPersona.setEmail("4e");
            actualizarPersona.setGerencia("gerencia");
            actualizarPersona.setDepartamento("departaemnto");
            actualizarPersona.setArea("area");

            personaDao.update(actualizarPersona);

            
            conexion.commit();



//ELIMINAR
//            PersonaDao personaDao = new PersonaDaoJDBC(conexion);
//            PersonaDTO eliminarPersona= new PersonaDTO();
//            eliminarPersona.setCodigo("per3");
//            personaDao.delete(eliminarPersona);
//
//            
//            conexion.commit();




//-----------mostrar lista de personas
//            PersonaDao personaDao = new PersonaDaoJDBC(conexion);
//            List<PersonaDTO> personas = personaDao.select();
//            for(PersonaDTO personaDTO: personas){
//                System.out.println("Persona: " + personaDTO);
//            }

            
            System.out.println("Se ha hecho commit de la transaccion");
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
            System.out.println("Entramos al rollback");
            try {
                conexion.rollback();
            } catch (SQLException ex1) {
                ex1.printStackTrace(System.out);
            }
        }

    }
}
