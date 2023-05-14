package test;

import datos.Conexion;
import datos.RegistroDao;
import datos.RegistroDaoJDBC;
import dominio.RegistroDTO;
import java.sql.*;
import java.util.List;

public class ManejoRegistros {
    public static void main(String[] args) {

        Connection conexion2 = null;
        try {
            conexion2 = Conexion.getConnection();
            if (conexion2.getAutoCommit()) {
                conexion2.setAutoCommit(false);
            }
////INSERT
//            RegistroDao registroDao = new RegistroDaoJDBC(conexion);
//            RegistroDTO nuevoRegistro= new RegistroDTO();
//            nuevoRegistro.setId("per2-3");
////            HAY QUE VER ESTO DE CURDATE
//            nuevoRegistro.setFecha("CUREDATE()");
//            nuevoRegistro.setNumero(3);
//            nuevoRegistro.setPersona("per2");
//            
//            registroDao.insert(nuevoRegistro);
//
//            
//            conexion.commit();

////UPDATE
//            RegistroDao registroDao = new RegistroDaoJDBC(conexion);
//            RegistroDTO actualizarRegistro= new RegistroDTO();
//            actualizarRegistro.setId("per2-3");
//            actualizarRegistro.setFecha("2022-04-06");
//            actualizarRegistro.setNumero(4);
//            actualizarRegistro.setPersona("per2");
//            actualizarRegistro.setId("per2-4");
//            registroDao.update(actualizarRegistro);
//          me sale que los datos estan truncando
//
//            
//            conexion.commit();



////ELIMINAR
//            RegistroDao registroDao = new RegistroDaoJDBC(conexion);
//            RegistroDTO eliminarRegistro= new RegistroDTO();
//            eliminarRegistro.setId("per2-3");
//            registroDao.delete(eliminarRegistro);
//
//            
//            conexion.commit();




//-----------mostrar lista de registros
//            RegistroDao registroDao = new RegistroDaoJDBC(conexion);
//            List<RegistroDTO> registros = registroDao.select();
//            for(RegistroDTO registroDTO: registros){
//                System.out.println("Registro: " + registroDTO);
//            }
            


            System.out.println("Se ha hecho commit de la transaccion");
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
            System.out.println("Entramos al rollback");
            try {
                conexion2.rollback();
            } catch (SQLException ex1) {
                ex1.printStackTrace(System.out);
            }
        }

    }
}

