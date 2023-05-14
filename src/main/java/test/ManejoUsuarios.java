package test;

import datos.Conexion;
import datos.UsuarioDao;
import datos.UsuarioDaoJDBC;
import dominio.UsuarioDTO;
import java.sql.*;
import java.util.List;

public class ManejoUsuarios {
    public static void main(String[] args) {

        Connection conexion = null;
        try {
            conexion = Conexion.getConnection();
            if (conexion.getAutoCommit()) {
                conexion.setAutoCommit(false);
            }
//INSERT
//            UsuarioDao usuarioDao = new UsuarioDaoJDBC(conexion);
//            UsuarioDTO nuevoUsuario= new UsuarioDTO();
//            nuevoUsuario.setContrasena("contrasena3");
//            nuevoUsuario.setTipo(1);
//            nuevoUsuario.setPersona("per3");
//            usuarioDao.insert(nuevoUsuario);
//
//            
//            conexion.commit();

//UPDATE
//            UsuarioDao usuarioDao = new UsuarioDaoJDBC(conexion);
//            UsuarioDTO actualizarUsuario= new UsuarioDTO();
//            actualizarUsuario.setId(5);
//            actualizarUsuario.setContrasena("gerencia5");
//            actualizarUsuario.setTipo("departamento5");
//            actualizarUsuario.setPersona("area5");
//            usuarioDao.update(actualizarUsuario);
//
//            
//            conexion.commit();



//ELIMINAR
//            UsuarioDao usuarioDao = new UsuarioDaoJDBC(conexion);
//            UsuarioDTO eliminarUsuario= new UsuarioDTO();
//            eliminarUsuario.setId(5);
//            usuarioDao.delete(eliminarUsuario);
//
//            
//            conexion.commit();




//-----------mostrar lista de usuarios
//            UsuarioDao usuarioDao = new UsuarioDaoJDBC(conexion);
//            List<UsuarioDTO> usuarios = usuarioDao.select();
//            for(UsuarioDTO usuarioDTO: usuarios){
//                //System.out.println("Usuario: " + usuarioDTO);
//                System.out.println(usuarioDTO);
//                if(usuarioDTO.equals("0, null, 0, per1")){
//                    System.out.println(usuarioDTO);
//                }
//                else {
//                    System.out.println("wash");
//                }
//            }
//            


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

