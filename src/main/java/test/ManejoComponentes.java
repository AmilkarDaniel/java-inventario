
package test;

import datos.ComponenteDao;
import datos.ComponenteDaoJDBC;
import datos.Conexion;
import dominio.ComponenteDTO;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ManejoComponentes {
    
    
    public static void main(String[] args){
        Connection conexion = null;
        try {
            conexion = Conexion.getConnection();
            if (conexion.getAutoCommit()) {
                conexion.setAutoCommit(false);
            }
//INSERT
//            ComponenteDao componenteDao = new ComponenteDaoJDBC(conexion);
//            ComponenteDTO nuevoComponente= new ComponenteDTO();
//            nuevoComponente.setNombre("nombre");
//            nuevoComponente.setMarca("marca");
//            nuevoComponente.setEstado("estado");
//            nuevoComponente.setSerie("serie");
//            nuevoComponente.setColor("color");
//            nuevoComponente.setTamanio("tamanio");
//            nuevoComponente.setDescripcion("descripcion");
//            componenteDao.insert(nuevoComponente);
//            conexion.commit();

//UPDATE-----------------  update no daaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa 
//            ComponenteDao componenteDao = new ComponenteDaoJDBC(conexion);
//            ComponenteDTO actualizarComponente= new ComponenteDTO();
//            actualizarComponente.setId(1);
//            actualizarComponente.setNombre("nombre");
//            actualizarComponente.setMarca("marca");
//            actualizarComponente.setEstado("estado");
//            actualizarComponente.setSerie("serie");
//            actualizarComponente.setColor("color");
//            actualizarComponente.setTamanio("tamanio");
//            actualizarComponente.setDescripcion("descripcionN");
//            componenteDao.update(actualizarComponente);
//
//            
//            conexion.commit();



//ELIMINAR
//            ComponenteDao componenteDao = new ComponenteDaoJDBC(conexion);
//            ComponenteDTO eliminarComponente= new ComponenteDTO();
//            eliminarComponente.setId(1);
//            componenteDao.delete(eliminarComponente);
//
//            
//            conexion.commit();




//-----------mostrar lista de componentes
//            ComponenteDao componenteDao = new ComponenteDaoJDBC(conexion);
//            List<ComponenteDTO> componentes = componenteDao.select();
//            for(ComponenteDTO componenteDTO: componentes){
//                System.out.println("Componente: " + componenteDTO);
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
