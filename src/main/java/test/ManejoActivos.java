package test;

import datos.Conexion;
import datos.ActivoDao;
import datos.ActivoDaoJDBC;
import dominio.ActivoDTO;
import java.sql.*;
import java.util.List;

public class ManejoActivos {
    public static void main(String[] args) {

        Connection conexion = null;
        try {
            conexion = Conexion.getConnection();
            if (conexion.getAutoCommit()) {
                conexion.setAutoCommit(false);
            }
//INSERT
//            ActivoDao activoDao = new ActivoDaoJDBC(conexion);
//            ActivoDTO nuevoActivo= new ActivoDTO();
//            nuevoActivo.setRegistro("per1-1");
//            nuevoActivo.setNombre("nombre");
//            nuevoActivo.setMarca("marca");
//            nuevoActivo.setEstado("estado");
//            nuevoActivo.setTamanio("tamanio");
//            nuevoActivo.setColor("color");
//            nuevoActivo.setMaterial("material");
//            nuevoActivo.setNum_cajas(0);
//            nuevoActivo.setNum_patas(2);
//            nuevoActivo.setNum_puertas(4);
//            nuevoActivo.setAccesorio("accesorio");
//            nuevoActivo.setAccesorio2("accesorio2");
//            activoDao.insert(nuevoActivo);
//
//            
//            conexion.commit();

//UPDATE
//            ActivoDao activoDao = new ActivoDaoJDBC(conexion);
//            ActivoDTO actualizarActivo= new ActivoDTO();
//            actualizarActivo.setId_activo(38);
//            actualizarActivo.setRegistro("per1-1");
//            actualizarActivo.setNombre("nombre");
//            actualizarActivo.setMarca("marca");
//            actualizarActivo.setEstado("estado");
//            actualizarActivo.setTamanio("tamanio");
//            actualizarActivo.setColor("color");
//            actualizarActivo.setMaterial("material");
//            actualizarActivo.setNum_cajas(0);
//            actualizarActivo.setNum_patas(2);
//            actualizarActivo.setNum_puertas(4);
//            actualizarActivo.setAccesorio("accesorio");
//            actualizarActivo.setAccesorio2(".");
//            
//            
//            activoDao.update(actualizarActivo);
//
//            
//            conexion.commit();



//ELIMINAR
//            ActivoDao activoDao = new ActivoDaoJDBC(conexion);
//            ActivoDTO eliminarActivo= new ActivoDTO();
//            eliminarActivo.setId_activo(8);
//            activoDao.delete(eliminarActivo);
//
//            
//            conexion.commit();









//-----------mostrar lista de activos
//            ActivoDao activoDao = new ActivoDaoJDBC(conexion);
//            List<ActivoDTO> activos = activoDao.select();
//            for(ActivoDTO activoDTO: activos){
//                System.out.println("Activo: " + activoDTO);
//            }
            
            ActivoDao activoDao = new ActivoDaoJDBC(conexion);
            List<ActivoDTO> activos = activoDao.select();
            for(ActivoDTO activoDTO: activos){
                System.out.println("Activo: " + activoDTO);
            }


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
