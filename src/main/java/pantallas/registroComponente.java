package pantallas;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import datos.ComponenteDao;
import datos.ComponenteDaoJDBC;
import datos.Conexion;
import datos.DescripcioncompDaoJDBC;
import datos.RegistroDao;
import datos.RegistroDaoJDBC;
import dominio.ComponenteDTO;
import dominio.DescripcioncompDTO;
import dominio.RegistroDTO;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;

//para cambiar el color de la tabla del encabezado cambiando el colkor por defecto pero tmabien se vio que cambio los botones 
import javax.swing.UIManager;
import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;
import datos.DescripcioncompDao;

public class registroComponente extends javax.swing.JFrame {

    DefaultTableModel model;
    FileOutputStream fout;
    ByteArrayOutputStream out;

    public registroComponente() {
        initComponents();
        this.setLocationRelativeTo(null);
        btnRegistrar.setEnabled(false);
        btnNuevoRegistro.setEnabled(false);
        btnGuardar.setEnabled(false);
        btnEliminar.setEnabled(false);
        btnModificar.setEnabled(false);
        btnVerRegistros.setEnabled(false);
        btnBuscar.setEnabled(false);
        btnNuevo.setEnabled(false);
        btnPdf.setEnabled(false);
        btnQr.setEnabled(false);
        txtBuscar.setEditable(false);
        txtRegistro.setEditable(false);
        txtId.setEditable(false);
        txtCreadorComponente.setEditable(false);
        txtCreadorRegistro.setEditable(false);
        camposNoEditables();
        llenarNombreComponente();

        //cambiar de color encabezadode tabla
        try {
            if (UIManager.getLookAndFeel().toString().contains("Nimbus")) {
                UIManager.put("nimbusBlueGrey", new Color(52, 78, 65));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String[] tituloColumnas() throws SQLException {
        String[] titulos = {"Id", "Registro", "Nombre", "Registro", "Marca", "Estado", "Color", "Descripción", "Creador"};
        return titulos;
    }

    private void cargarTabla() throws SQLException {
        String[] registros;
        registros = new String[50];
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            String filtro = "%" + txtCodigo.getText() + "%";
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement("SELECT id, fkregistro, nombre, SUBSTRING(fecha, 1,10) AS fecha, marca, estado, color, descripcion, creador FROM componente WHERE fkregistro IN (SELECT id FROM registro WHERE fkpersona LIKE '" + filtro + "')");
            rs = stmt.executeQuery();
            model = new DefaultTableModel(null, tituloColumnas());
            while (rs.next()) {
                registros[0] = rs.getString(1);
                registros[1] = rs.getString(2);
                registros[2] = rs.getString(3);
                registros[3] = rs.getString(4);
                registros[4] = rs.getString(5);
                registros[5] = rs.getString(6);
                registros[6] = rs.getString(7);
                registros[7] = rs.getString(8);
                registros[8] = rs.getString(9);
                model.addRow(registros);
            }
            tabla1.setModel(model);
            formato_tabla();
        } finally {
            Conexion.close(rs);
            Conexion.close(stmt);
            Conexion.close(conn);

        }
    }

    private void formato_tabla() {
        DefaultTableCellRenderer Alinear = new DefaultTableCellRenderer();
        Alinear.setHorizontalAlignment(SwingConstants.CENTER);
        tabla1.getColumnModel().getColumn(0).setPreferredWidth(10);
        tabla1.getColumnModel().getColumn(0).setCellRenderer(Alinear);
        tabla1.getColumnModel().getColumn(1).setCellRenderer(Alinear);
        tabla1.getColumnModel().getColumn(2).setCellRenderer(Alinear);
        tabla1.getColumnModel().getColumn(3).setCellRenderer(Alinear);
        tabla1.getColumnModel().getColumn(4).setCellRenderer(Alinear);
        tabla1.getColumnModel().getColumn(5).setCellRenderer(Alinear);
        tabla1.getColumnModel().getColumn(6).setCellRenderer(Alinear);
        tabla1.getColumnModel().getColumn(7).setCellRenderer(Alinear);
        tabla1.getColumnModel().getColumn(8).setCellRenderer(Alinear);
    }

    private void cargarRegistros() throws SQLException {
        String[] registros;
        registros = new String[50];
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            String filtro = "%" + txtCodigo.getText() + "-" + cbNumero.getSelectedItem().toString() + "%";
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement("SELECT id, fkregistro, nombre, SUBSTRING(fecha, 1,10) AS fecha, marca, estado, color, descripcion, creador FROM componente WHERE fkregistro LIKE '" + filtro + "'");
            rs = stmt.executeQuery();
            model = new DefaultTableModel(null, tituloColumnas());
            while (rs.next()) {
                registros[0] = rs.getString(1);
                registros[1] = rs.getString(2);
                registros[2] = rs.getString(3);
                registros[3] = rs.getString(4);
                registros[4] = rs.getString(5);
                registros[5] = rs.getString(6);
                registros[6] = rs.getString(7);
                registros[7] = rs.getString(8);
                registros[8] = rs.getString(9);
                model.addRow(registros);
            }
            tabla1.setModel(model);
            formato_tabla();
        } finally {
            Conexion.close(rs);
            Conexion.close(stmt);
            Conexion.close(conn);

        }
    }

    private void llenarCuadrostexto() {
        int fila;
        fila = tabla1.getSelectedRow();
        txtId.setText(String.valueOf(tabla1.getValueAt(fila, 0)));
//        cbNombre.removeAllItems();
//        cbNombre.addItem(String.valueOf(tabla1.getValueAt(fila, 2)));
        txtMarca.setText(String.valueOf(tabla1.getValueAt(fila, 4)));
        txtEstado.setText(String.valueOf(tabla1.getValueAt(fila, 5)));
        txtColor.setText(String.valueOf(tabla1.getValueAt(fila, 6)));
        txtDescripcion.setText(String.valueOf(tabla1.getValueAt(fila, 7)));
        txtCreadorComponente.setText(String.valueOf(tabla1.getValueAt(fila, 8)));
    }

    int b;

    private void aniadirNewRegistro() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Connection conexion = null;
        try {
            conexion = Conexion.getConnection();
            if (conexion.getAutoCommit()) {
                conexion.setAutoCommit(false);
            }
            String filtro = "%" + txtCodigo.getText() + "%";
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement("SELECT MAX(numero) numero FROM registro WHERE fkpersona LIKE '" + filtro + "'");
            rs = stmt.executeQuery();
            if (existeRegistro(txtCodigo.getText()) == 0) {
                b = 1;
            } else {
                while (rs.next()) {
                    int a = Integer.parseInt(rs.getString("numero"));
                    b = a + 1;
                }
            }
            RegistroDao registroDao = new RegistroDaoJDBC(conexion);
            RegistroDTO nuevoRegistro = new RegistroDTO();
            String id = txtCodigo.getText() + "-" + b;
            nuevoRegistro.setId(id);
            nuevoRegistro.setNumero(b);
            nuevoRegistro.setPersona(txtCodigo.getText());
            registroDao.insert(nuevoRegistro);
            conexion.commit();

        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
            try {
                conexion.rollback();
            } catch (SQLException ex1) {
                ex1.printStackTrace(System.out);
            }
        } finally {
            Conexion.close(rs);
            Conexion.close(stmt);
            Conexion.close(conn);
        }
    }

    void sumarRegistro() throws SQLException {
        if (existeRegistro(txtCodigo.getText()) == 0) {
            String idReg = txtCodigo.getText() + "-1";
            txtRegistro.setText(idReg);
        } else {
            Connection conn = null;
            PreparedStatement stmt = null;
            ResultSet rs = null;
            try {
                String filtro = "%" + txtCodigo.getText() + "%";
                conn = Conexion.getConnection();
                stmt = conn.prepareStatement("SELECT MAX(numero) numero FROM registro WHERE fkpersona LIKE '" + filtro + "'");
                rs = stmt.executeQuery();
                while (rs.next()) {
                    int a = Integer.parseInt(rs.getString("numero"));
                    int b = a + 1;
                    String idReg = txtCodigo.getText() + "-" + b;
                    txtRegistro.setText(idReg);
                }
            } catch (SQLException ex) {
                ex.printStackTrace(System.out);
            } finally {
                Conexion.close(rs);
                Conexion.close(stmt);
                Conexion.close(conn);
            }
        }
        aniadirNewRegistro();
    }

    public void limpiar() {
        cbNombre.setSelectedItem("--Seleccionar--");
        txtMarca.setText("");
        txtEstado.setText("");
        txtColor.setText("");
        txtDescripcion.setText("");
        txtCreadorComponente.setText("");
        txtId.setText("");
    }

    private void buscarNombreComponente() {
        String[] registros = new String[50];
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            String codigo = txtCodigo.getText();
            String filtro = txtBuscar.getText();

            conn = Conexion.getConnection();
            stmt = conn.prepareStatement("SELECT id, fkregistro, nombre, SUBSTRING(fecha, 1,10) AS fecha, marca, estado, color, descripcion, creador FROM componente WHERE fkregistro IN (SELECT id FROM registro WHERE fkpersona LIKE '" + codigo + "') and nombre = '" + filtro + "'");
            rs = stmt.executeQuery();
            model = new DefaultTableModel(null, tituloColumnas());
            while (rs.next()) {
                registros[0] = rs.getString(1);
                registros[1] = rs.getString(2);
                registros[2] = rs.getString(3);
                registros[3] = rs.getString(4);
                registros[4] = rs.getString(5);
                registros[5] = rs.getString(6);
                registros[6] = rs.getString(7);
                registros[7] = rs.getString(8);
                registros[8] = rs.getString(9);
                model.addRow(registros);
            }
            tabla1.setModel(model);
            formato_tabla();
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            Conexion.close(rs);
            Conexion.close(stmt);
            Conexion.close(conn);
        }
    }

    public int existePersona(String usuario) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        conn = Conexion.getConnection();

        String sql = "SELECT count(cod_persona) FROM persona WHERE cod_persona = ?";

        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, usuario);
            rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);

            }
            return 1;
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
            return 1;
        } finally {
            Conexion.close(rs);
            Conexion.close(stmt);
            Conexion.close(conn);
        }
    }

    public int existeRegistro(String usuario) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        conn = Conexion.getConnection();

        String sql = "SELECT count(fkpersona) FROM registro WHERE fkpersona = ?";

        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, usuario);
            rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 1;
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
            return 1;
        } finally {
            Conexion.close(rs);
            Conexion.close(stmt);
            Conexion.close(conn);
        }
    }

    void registrarComponente() {
        Connection conexion = null;
        try {
            conexion = Conexion.getConnection();
            if (conexion.getAutoCommit()) {
                conexion.setAutoCommit(false);
            }
            ComponenteDao componenteDao = new ComponenteDaoJDBC(conexion);
            ComponenteDTO nuevoComponente = new ComponenteDTO();
            int id = Integer.parseInt(txtId.getText());
            nuevoComponente.setId(id);
            nuevoComponente.setRegistro(txtRegistro.getText());
            String nombre = String.valueOf(cbNombre.getSelectedItem());
            nuevoComponente.setNombre(nombre);
            nuevoComponente.setMarca(txtMarca.getText());
            nuevoComponente.setEstado(txtEstado.getText());
            nuevoComponente.setColor(txtColor.getText());
            nuevoComponente.setDescripcion(txtDescripcion.getText());
            nuevoComponente.setCreador(txtCreadorComponente.getText());
            componenteDao.insert(nuevoComponente);
            conexion.commit();
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
            try {
                conexion.rollback();
            } catch (SQLException ex1) {
                ex1.printStackTrace(System.out);
            }
        }
    }

    private void buscarRegistros() throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            cbNumero.removeAllItems();
            String filtro = "%" + txtCodigo.getText() + "%";
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement("SELECT numero  FROM registro WHERE fkpersona LIKE '" + filtro + "'");
            rs = stmt.executeQuery();
            while (rs.next()) {
                cbNumero.addItem(rs.getString("numero"));
                cargarTabla();
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            Conexion.close(rs);
            Conexion.close(stmt);
            Conexion.close(conn);
        }
    }

    private void eliminarComponente() {
        Connection conexion = null;
        try {
            conexion = Conexion.getConnection();
            if (conexion.getAutoCommit()) {
                conexion.setAutoCommit(false);
            }
            ComponenteDao componenteDao = new ComponenteDaoJDBC(conexion);
            ComponenteDTO eliminarComponente = new ComponenteDTO();
            String i = txtId.getText();
            int j = Integer.parseInt(i);
            eliminarComponente.setId(j);
            componenteDao.delete(eliminarComponente);
            conexion.commit();
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
            try {
                conexion.rollback();
            } catch (SQLException ex1) {
                ex1.printStackTrace(System.out);
            }
        }
    }

    private void eliminarDescripcion() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            String filtro = txtId.getText();
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement("SELECT id  FROM descripcioncomponente WHERE id_componente = " + filtro + ";");
            rs = stmt.executeQuery();
            while (rs.next()) {
                int id = Integer.parseInt(rs.getString("id"));

                Connection conexion2 = null;
                try {
                    conexion2 = Conexion.getConnection();
                    if (conexion2.getAutoCommit()) {
                        conexion2.setAutoCommit(false);
                    }
                    DescripcioncompDao componenteDao = new DescripcioncompDaoJDBC(conexion2);
                    DescripcioncompDTO eliminarDescripcion = new DescripcioncompDTO();
                    eliminarDescripcion.setId(id);
                    componenteDao.delete(eliminarDescripcion);
                    conexion2.commit();
                } catch (SQLException ex) {
                    ex.printStackTrace(System.out);
                    try {
                        conexion2.rollback();
                    } catch (SQLException ex1) {
                        ex1.printStackTrace(System.out);
                    }
                }

            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            Conexion.close(rs);
            Conexion.close(stmt);
            Conexion.close(conn);
        }
    }

    private void modificarComponente() {
        Connection conexion = null;
        try {
            conexion = Conexion.getConnection();
            if (conexion.getAutoCommit()) {
                conexion.setAutoCommit(false);
            }
            ComponenteDao componenteDao = new ComponenteDaoJDBC(conexion);
            ComponenteDTO actualizarComponente = new ComponenteDTO();
            String i = txtId.getText();
            int j = Integer.parseInt(i);
            actualizarComponente.setId(j);
            actualizarComponente.setRegistro(txtRegistro.getText());
            String nombre = String.valueOf(cbNombre.getSelectedItem());
            actualizarComponente.setNombre(nombre);
            actualizarComponente.setMarca(txtMarca.getText());
            actualizarComponente.setEstado(txtEstado.getText());
            actualizarComponente.setColor(txtColor.getText());
            actualizarComponente.setDescripcion(txtDescripcion.getText());
            actualizarComponente.setCreador(txtCreadorComponente.getText());
            componenteDao.update(actualizarComponente);
            conexion.commit();
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
            try {
                conexion.rollback();
            } catch (SQLException ex1) {
                ex1.printStackTrace(System.out);
            }
        }
    }

    void camposNoEditables() {

        cbNombre.setEnabled(false);
        txtMarca.setEditable(false);
        txtEstado.setEditable(false);
        txtColor.setEditable(false);
        txtDescripcion.setEditable(false);
    }

    private void reportePDF() {
        Document documento = new Document(PageSize.LETTER.rotate());
        try {
            JFileChooser fc = new JFileChooser();
            int rpt = fc.showSaveDialog(jPanel1);

            if (rpt == JFileChooser.APPROVE_OPTION) {
                String ruta = fc.getSelectedFile().getAbsolutePath();
                PdfWriter.getInstance(documento, new FileOutputStream(ruta + ".pdf"));
            }
//            String ruta = System.getProperty("user.home");
//            PdfWriter.getInstance(documento, new FileOutputStream(ruta + "/Desktop/" + txtCodigo.getText().trim() + ".pdf"));

            //header es la cabecera del pdf
            com.itextpdf.text.Image header = com.itextpdf.text.Image.getInstance("src/main/java/imagenes/login.png");
            header.scaleToFit(100, 200);
            header.setAlignment(Chunk.ALIGN_CENTER);
            //que texto queremos mostrar en pantalla
            Paragraph parrafo = new Paragraph();
            parrafo.setAlignment(Paragraph.ALIGN_CENTER);
            parrafo.add("Información de la persona. \n \n");
            parrafo.setFont(FontFactory.getFont("Tahoma", 14, Font.BOLD, BaseColor.DARK_GRAY));

            documento.open();
            documento.add(header);
            documento.add(parrafo);
            Font fontChinese = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL);

            PdfPTable tablaPersona = new PdfPTable(6);
            tablaPersona.setWidthPercentage(80);

            PdfPCell Pcodigo = new PdfPCell(new Phrase("Codigo", fontChinese));
            Pcodigo.setBackgroundColor(BaseColor.ORANGE);

            //hace un poco mas centrado pero no lo lo centra el texto
            Pcodigo.setPadding(5);
            Pcodigo.setUseAscender(true);
            Pcodigo.setUseDescender(true);

            Pcodigo.setHorizontalAlignment(Element.ALIGN_CENTER);

            PdfPCell Pnombre = new PdfPCell(new Phrase("Nombre", fontChinese));
            Pnombre.setBackgroundColor(BaseColor.ORANGE);
            Pnombre.setHorizontalAlignment(Element.ALIGN_CENTER);

            PdfPCell Papellido = new PdfPCell(new Phrase("Apellido", fontChinese));
            Papellido.setBackgroundColor(BaseColor.ORANGE);
            Papellido.setHorizontalAlignment(Element.ALIGN_CENTER);

            PdfPCell Ptelefono = new PdfPCell(new Phrase("Telefono", fontChinese));
            Ptelefono.setBackgroundColor(BaseColor.ORANGE);
            Ptelefono.setHorizontalAlignment(Element.ALIGN_CENTER);

            PdfPCell Pdireccion = new PdfPCell(new Phrase("Dirección", fontChinese));
            Pdireccion.setBackgroundColor(BaseColor.ORANGE);
            Pdireccion.setHorizontalAlignment(Element.ALIGN_CENTER);

            PdfPCell Pemail = new PdfPCell(new Phrase("Email", fontChinese));
            Pemail.setBackgroundColor(BaseColor.ORANGE);
            Pemail.setHorizontalAlignment(Element.ALIGN_CENTER);

            tablaPersona.addCell(Pcodigo);
            tablaPersona.addCell(Pnombre);
            tablaPersona.addCell(Papellido);
            tablaPersona.addCell(Ptelefono);
            tablaPersona.addCell(Pdireccion);
            tablaPersona.addCell(Pemail);

            Connection conn = null;
            PreparedStatement stmt = null;
            ResultSet rs = null;
            try {
                conn = Conexion.getConnection();
                stmt = conn.prepareStatement("SELECT cod_persona, nombre, apellido, telefono, direccion, email FROM inventario.persona WHERE cod_persona = '" + txtCodigo.getText() + "'");
                rs = stmt.executeQuery();

                if (rs.next()) {
                    do {
                        PdfPCell codigoP = new PdfPCell(new Phrase(rs.getString(1), fontChinese));
                        tablaPersona.addCell(codigoP);

                        PdfPCell nombreP = new PdfPCell(new Phrase(rs.getString(2), fontChinese));
                        tablaPersona.addCell(nombreP);

                        PdfPCell apellidoP = new PdfPCell(new Phrase(rs.getString(3), fontChinese));
                        tablaPersona.addCell(apellidoP);

                        PdfPCell telefonoP = new PdfPCell(new Phrase(rs.getString(4), fontChinese));
                        tablaPersona.addCell(telefonoP);

                        PdfPCell direccionP = new PdfPCell(new Phrase(rs.getString(5), fontChinese));
                        tablaPersona.addCell(direccionP);

                        PdfPCell emailP = new PdfPCell(new Phrase(rs.getString(6), fontChinese));
                        tablaPersona.addCell(emailP);
                    } while (rs.next());

                    documento.add(tablaPersona);

                    Conexion.close(rs);
                    Conexion.close(stmt);
                    Conexion.close(conn);
                }

                Paragraph parrafo2 = new Paragraph();
                parrafo2.setAlignment(Paragraph.ALIGN_CENTER);
                parrafo2.add("\n \n Componentes registrados. \n \n");
                parrafo2.setFont(FontFactory.getFont("Tahoma", 14, Font.BOLD, BaseColor.DARK_GRAY));

                Paragraph parrafo3 = new Paragraph();
                parrafo3.setAlignment(Paragraph.ALIGN_JUSTIFIED);
                String numeroRegistro = String.valueOf(cbNumero.getSelectedItem());
                parrafo3.add("\n \n Registro numero:  " + numeroRegistro + "\n \n");
                parrafo3.setFont(FontFactory.getFont("Tahoma", 12, Font.BOLD, BaseColor.DARK_GRAY));

                documento.add(parrafo2);
                documento.add(parrafo3);

                PdfPTable tablaComponente = new PdfPTable(8);
                tablaComponente.setWidthPercentage(100);
                float[] medidaCeldas = {0.35f, 0.65f, 0.65f, 0.65f, 0.65f, 0.65f, 0.65f, 0.65f};
                tablaComponente.setWidths(medidaCeldas);

                PdfPCell Cregistro = new PdfPCell(new Phrase("Id", fontChinese));
                Cregistro.setBackgroundColor(BaseColor.ORANGE);

                Cregistro.setPadding(5);
                Cregistro.setUseAscender(true);
                Cregistro.setUseDescender(true);

                Cregistro.setHorizontalAlignment(Element.ALIGN_CENTER);

                PdfPCell Cnombre = new PdfPCell(new Phrase("Nombre", fontChinese));
                Cnombre.setBackgroundColor(BaseColor.ORANGE);
                Cnombre.setHorizontalAlignment(Element.ALIGN_CENTER);

                PdfPCell Cfecha = new PdfPCell(new Phrase("Registro", fontChinese));
                Cfecha.setBackgroundColor(BaseColor.ORANGE);
                Cfecha.setHorizontalAlignment(Element.ALIGN_CENTER);

                PdfPCell Cmarca = new PdfPCell(new Phrase("Marca", fontChinese));
                Cmarca.setBackgroundColor(BaseColor.ORANGE);
                Cmarca.setHorizontalAlignment(Element.ALIGN_CENTER);

                PdfPCell Cestado = new PdfPCell(new Phrase("Estado", fontChinese));
                Cestado.setBackgroundColor(BaseColor.ORANGE);
                Cestado.setHorizontalAlignment(Element.ALIGN_CENTER);

                PdfPCell Ccolor = new PdfPCell(new Phrase("Color", fontChinese));
                Ccolor.setBackgroundColor(BaseColor.ORANGE);
                Ccolor.setHorizontalAlignment(Element.ALIGN_CENTER);

                PdfPCell Cdescripcion = new PdfPCell(new Phrase("Descripción", fontChinese));
                Cdescripcion.setBackgroundColor(BaseColor.ORANGE);
                Cdescripcion.setHorizontalAlignment(Element.ALIGN_CENTER);

                PdfPCell Ccreador = new PdfPCell(new Phrase("Cod. Creador", fontChinese));
                Ccreador.setBackgroundColor(BaseColor.ORANGE);
                Ccreador.setHorizontalAlignment(Element.ALIGN_CENTER);

                tablaComponente.addCell(Cregistro);
                tablaComponente.addCell(Cnombre);
                tablaComponente.addCell(Cfecha);
                tablaComponente.addCell(Cmarca);
                tablaComponente.addCell(Cestado);
                tablaComponente.addCell(Ccolor);
                tablaComponente.addCell(Cdescripcion);
                tablaComponente.addCell(Ccreador);

                Connection conn2 = null;
                PreparedStatement stmt2 = null;
                ResultSet rs2 = null;

                try {

                    String cod = txtCodigo.getText();
                    String filtro = txtCodigo.getText() + "-" + cbNumero.getSelectedItem();
                    conn2 = Conexion.getConnection();
                    stmt2 = conn2.prepareStatement("SELECT id, nombre, SUBSTRING(fecha, 1,10) AS fecha, marca, estado, color, descripcion, creador FROM componente WHERE fkregistro IN (SELECT id FROM registro WHERE fkpersona LIKE '" + cod + "') and fkregistro = '" + filtro + "'");
                    rs2 = stmt2.executeQuery();

                    if (rs2.next()) {
                        do {
                            PdfPCell idC = new PdfPCell(new Phrase(rs2.getString(1), fontChinese));
                            tablaComponente.addCell(idC);

                            PdfPCell nombreC = new PdfPCell(new Phrase(rs2.getString(2), fontChinese));
                            tablaComponente.addCell(nombreC);

                            PdfPCell fechaC = new PdfPCell(new Phrase(rs2.getString(3), fontChinese));
                            tablaComponente.addCell(fechaC);

                            PdfPCell marcaC = new PdfPCell(new Phrase(rs2.getString(4), fontChinese));
                            tablaComponente.addCell(marcaC);

                            PdfPCell estadoC = new PdfPCell(new Phrase(rs2.getString(5), fontChinese));
                            tablaComponente.addCell(estadoC);

                            PdfPCell colorC = new PdfPCell(new Phrase(rs2.getString(6), fontChinese));
                            tablaComponente.addCell(colorC);

                            PdfPCell descripcionC = new PdfPCell(new Phrase(rs2.getString(7), fontChinese));
                            tablaComponente.addCell(descripcionC);

                            PdfPCell creadorC = new PdfPCell(new Phrase(rs2.getString(8), fontChinese));
                            tablaComponente.addCell(creadorC);
                        } while (rs2.next());
                        documento.add(tablaComponente);

                        Conexion.close(rs2);
                        Conexion.close(stmt2);
                        Conexion.close(conn2);
                    }

                } catch (SQLException e) {
                    //activos
                    e.printStackTrace(System.out);
                }
            } catch (SQLException e) {
                //persona
                e.printStackTrace(System.out);
            }
            documento.close();
            JOptionPane.showMessageDialog(null, "Reporte creado correctamente.");

        } catch (DocumentException | IOException e) {
            System.err.println("Error en PDF o ruta de imagen" + e);
            JOptionPane.showMessageDialog(null, "Error al generar PDF, contacte al administrador");
        }
    }

    //ve si el nombre del componente existe en los registros
    public int existeComponente(String nombre) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        conn = Conexion.getConnection();

        String sql = "SELECT count(fkregistro) FROM componente WHERE fkregistro IN (SELECT id FROM registro WHERE fkpersona LIKE '" + txtCodigo.getText() + "') and nombre = ?";
        try {
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, nombre);
            rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 1;
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
            return 1;
        } finally {
            Conexion.close(rs);
            Conexion.close(stmt);
            Conexion.close(conn);
        }
    }

    private void ultimoRegistro() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Connection conexion = null;
        try {
            conexion = Conexion.getConnection();
            if (conexion.getAutoCommit()) {
                conexion.setAutoCommit(false);
            }
            String filtro = "%" + txtCodigo.getText() + "%";
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement("SELECT MAX(numero) numero FROM registro WHERE fkpersona LIKE '" + filtro + "'");
            rs = stmt.executeQuery();

            while (rs.next()) {
                int a = Integer.parseInt(rs.getString("numero"));
                txtRegistro.setText(txtCodigo.getText() + "-" + a);
            }

        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
            try {
                conexion.rollback();
            } catch (SQLException ex1) {
                ex1.printStackTrace(System.out);
            }
        } finally {
            Conexion.close(rs);
            Conexion.close(stmt);
            Conexion.close(conn);
        }
    }

    public int ultimoRegistroSelect(String codigo) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String sql = "SELECT max(numero) FROM registro WHERE fkpersona = ?";
        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, codigo);
            rs = stmt.executeQuery();
            if (rs.next()) {
                int a = rs.getInt(1);
                return a;
            }

        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            Conexion.close(rs);
            Conexion.close(stmt);
            Conexion.close(conn);
        }
        return 0;
    }

    void recibirCodigoCreadorComponente() {
        String codigo = menu.txtUsuario.getText();
        txtCreadorComponente.setText(codigo);
    }

    void recibirCodigoCreadorRegistro() {
        String codigo = menu.txtUsuario.getText();
        txtCreadorRegistro.setText(codigo);
    }

    private void codigoCreadorRegistro() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            String codigo = txtCodigo.getText();
            String numero = String.valueOf(cbNumero.getSelectedItem());
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement("SELECT creador  FROM registro WHERE fkpersona = '" + codigo + "' and numero = '" + numero + "'");
            rs = stmt.executeQuery();
            while (rs.next()) {
                txtCreadorRegistro.setText(rs.getString("creador"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            Conexion.close(rs);
            Conexion.close(stmt);
            Conexion.close(conn);
        }
    }

    void guardarImagenQr() {
        JFileChooser fc = new JFileChooser();
        int rpt = fc.showSaveDialog(pnlQr1);
        if (rpt == JFileChooser.APPROVE_OPTION) {
            try {
                String ruta = fc.getSelectedFile().getAbsolutePath() + ".png";
                fout = new FileOutputStream(ruta);
                fout.write(out.toByteArray());
                fout.flush();
                fout.close();
                JOptionPane.showMessageDialog(null, "Guardado exitoso", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        }
    }

    void datosQr() {
        int fila;
        fila = tabla1.getSelectedRow();
        String datos = "Id: " + String.valueOf(tabla1.getValueAt(fila, 0)) + "\n"
                + "Registro: " + String.valueOf(tabla1.getValueAt(fila, 1)) + "\n"
                + "Nombre: " + String.valueOf(tabla1.getValueAt(fila, 2)) + "\n"
                + "Fecha de registro: " + String.valueOf(tabla1.getValueAt(fila, 3)) + "\n"
                + "Marca: " + String.valueOf(tabla1.getValueAt(fila, 4)) + "\n"
                + "Estado: " + String.valueOf(tabla1.getValueAt(fila, 5)) + "\n"
                + "Color: " + String.valueOf(tabla1.getValueAt(fila, 6)) + "\n"
                + "Descripcion: " + String.valueOf(tabla1.getValueAt(fila, 7)) + "\n"
                + "Cod. Creador: " + String.valueOf(tabla1.getValueAt(fila, 8));

        out = QRCode.from(datos).withSize(300, 300).to(ImageType.PNG).stream();
        try {
            fout = new FileOutputStream(new File("temporal.png"));
            fout.write(out.toByteArray());
            fout.flush();
            fout.close();
            BufferedImage myqr = ImageIO.read(new File("temporal.png"));
            JLabel label = new JLabel(new ImageIcon(myqr));
            Graphics g = pnlQr2.getGraphics();
            g.drawImage(myqr, 0, 0, label);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace(System.out);
        } catch (IOException ex) {
            ex.printStackTrace(System.out);
        }
    }

    private void llenarNombreComponente() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            cbNombre.removeAllItems();
            cbNombre.addItem("--Seleccionar--");
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement("SELECT distinct nombre FROM nomcomp");
            rs = stmt.executeQuery();
            while (rs.next()) {
                cbNombre.addItem(rs.getString("nombre"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            Conexion.close(rs);
            Conexion.close(stmt);
            Conexion.close(conn);
        }
    }

    private void nuevoId() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement("SELECT max(id) FROM componente");
            rs = stmt.executeQuery();
            int id;
            while (rs.next()) {
                id = Integer.parseInt(rs.getString("max(id)"));
                int d = id + 1;
                String Id = String.valueOf(d);
                txtId.setText(Id);
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            Conexion.close(rs);
            Conexion.close(stmt);
            Conexion.close(conn);
        }

    }

    private void enviarCaracteristicas() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            String filtro = String.valueOf(cbNombre.getSelectedItem());
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement("SELECT caracteristica FROM nomcomp WHERE nombre LIKE '" + filtro + "'");
            rs = stmt.executeQuery();
            while (rs.next()) {
                descripcionComponente.cbCaracteristica.addItem(rs.getString("caracteristica"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            Conexion.close(rs);
            Conexion.close(stmt);
            Conexion.close(conn);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        background = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        cbNumero = new javax.swing.JComboBox<>();
        txtCodigo = new javax.swing.JTextField();
        btnVerificar = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        btnNuevoRegistro = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabla1 = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtMarca = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtEstado = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtColor = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        btnRegistrar = new javax.swing.JButton();
        txtId = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtDescripcion = new javax.swing.JTextArea();
        txtCreadorComponente = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        cbNombre = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        btnGuardar = new javax.swing.JButton();
        btnNuevo = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        btnVerRegistros = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        txtBuscar = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        btnExit = new javax.swing.JPanel();
        txtExit = new javax.swing.JLabel();
        btnRegresar = new javax.swing.JPanel();
        txtRegresar = new javax.swing.JLabel();
        pnlQr1 = new javax.swing.JPanel();
        btnPdf = new javax.swing.JButton();
        pnlQr2 = new javax.swing.JPanel();
        btnQr = new javax.swing.JButton();
        txtCreadorRegistro = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        txtRegistro = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        setSize(getPreferredSize());

        background.setBackground(new java.awt.Color(218, 215, 205));
        background.setPreferredSize(new java.awt.Dimension(1200, 800));
        background.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Open Sans", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(52, 78, 65));
        jLabel1.setText("Registro Componente");
        background.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 400, 50));

        jPanel1.setBackground(new java.awt.Color(163, 177, 138));

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setFont(new java.awt.Font("Verdana", 1, 16)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(58, 90, 64));
        jLabel2.setText("Codigo de Persona:");

        cbNumero.setBackground(new java.awt.Color(103, 102, 97));
        cbNumero.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        cbNumero.setBorder(null);
        cbNumero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbNumeroActionPerformed(evt);
            }
        });

        txtCodigo.setBackground(new java.awt.Color(218, 215, 205));
        txtCodigo.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        txtCodigo.setForeground(new java.awt.Color(102, 102, 102));
        txtCodigo.setText("Ingrese codigo");
        txtCodigo.setBorder(null);
        txtCodigo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                txtCodigoMousePressed(evt);
            }
        });

        btnVerificar.setBackground(new java.awt.Color(52, 78, 65));
        btnVerificar.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        btnVerificar.setForeground(new java.awt.Color(255, 255, 255));
        btnVerificar.setText("Verificar Codigo");
        btnVerificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVerificarActionPerformed(evt);
            }
        });

        jLabel4.setBackground(new java.awt.Color(255, 255, 255));
        jLabel4.setFont(new java.awt.Font("Verdana", 1, 16)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(58, 90, 64));
        jLabel4.setText("Numero de Registro:");

        btnNuevoRegistro.setBackground(new java.awt.Color(52, 78, 65));
        btnNuevoRegistro.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        btnNuevoRegistro.setForeground(new java.awt.Color(255, 255, 255));
        btnNuevoRegistro.setText("Nuevo Registro");
        btnNuevoRegistro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoRegistroActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel4))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbNumero, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnVerificar, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNuevoRegistro, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCodigo)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(btnVerificar)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbNumero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(btnNuevoRegistro))
                .addContainerGap())
        );

        background.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 520, 90));

        tabla1.setBackground(new java.awt.Color(218, 215, 205));
        tabla1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tabla1.setSelectionBackground(new java.awt.Color(163, 177, 138));
        tabla1.setSelectionForeground(new java.awt.Color(218, 215, 205));
        tabla1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabla1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabla1);

        background.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 170, 1180, 190));

        jPanel3.setBackground(new java.awt.Color(163, 177, 138));

        jLabel3.setFont(new java.awt.Font("Open Sans", 1, 28)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(52, 78, 65));
        jLabel3.setText("Componente");

        jLabel5.setBackground(new java.awt.Color(255, 255, 255));
        jLabel5.setFont(new java.awt.Font("Verdana", 0, 16)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(58, 90, 64));
        jLabel5.setText("Nombre del componente:");

        txtMarca.setBackground(new java.awt.Color(218, 215, 205));
        txtMarca.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        txtMarca.setBorder(null);
        txtMarca.setPreferredSize(new java.awt.Dimension(7, 25));

        jLabel6.setBackground(new java.awt.Color(255, 255, 255));
        jLabel6.setFont(new java.awt.Font("Verdana", 0, 16)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(58, 90, 64));
        jLabel6.setText("Marca del componente:");

        txtEstado.setBackground(new java.awt.Color(218, 215, 205));
        txtEstado.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        txtEstado.setBorder(null);
        txtEstado.setPreferredSize(new java.awt.Dimension(7, 25));

        jLabel7.setBackground(new java.awt.Color(255, 255, 255));
        jLabel7.setFont(new java.awt.Font("Verdana", 0, 16)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(58, 90, 64));
        jLabel7.setText("Estado del componente:");

        jLabel9.setBackground(new java.awt.Color(255, 255, 255));
        jLabel9.setFont(new java.awt.Font("Verdana", 0, 16)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(58, 90, 64));
        jLabel9.setText("Color del componente:");

        txtColor.setBackground(new java.awt.Color(218, 215, 205));
        txtColor.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        txtColor.setBorder(null);
        txtColor.setPreferredSize(new java.awt.Dimension(7, 25));

        jLabel11.setBackground(new java.awt.Color(255, 255, 255));
        jLabel11.setFont(new java.awt.Font("Verdana", 0, 16)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(58, 90, 64));
        jLabel11.setText("Descripción del componente:");

        btnRegistrar.setBackground(new java.awt.Color(163, 177, 138));
        btnRegistrar.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        btnRegistrar.setText("Registrar Componente");
        btnRegistrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarActionPerformed(evt);
            }
        });

        txtId.setBackground(new java.awt.Color(218, 215, 205));
        txtId.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        txtId.setBorder(null);
        txtId.setPreferredSize(new java.awt.Dimension(7, 25));

        jLabel17.setBackground(new java.awt.Color(255, 255, 255));
        jLabel17.setFont(new java.awt.Font("Verdana", 0, 16)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(58, 90, 64));
        jLabel17.setText("Id del componente:");

        txtDescripcion.setBackground(new java.awt.Color(218, 215, 205));
        txtDescripcion.setColumns(20);
        txtDescripcion.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        txtDescripcion.setRows(5);
        txtDescripcion.setBorder(null);
        jScrollPane2.setViewportView(txtDescripcion);

        txtCreadorComponente.setBackground(new java.awt.Color(218, 215, 205));
        txtCreadorComponente.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        txtCreadorComponente.setBorder(null);
        txtCreadorComponente.setPreferredSize(new java.awt.Dimension(7, 25));

        jLabel13.setBackground(new java.awt.Color(255, 255, 255));
        jLabel13.setFont(new java.awt.Font("Verdana", 0, 16)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(58, 90, 64));
        jLabel13.setText("Cod creador de componente:");

        cbNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbNombreActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtCreadorComponente, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(417, 417, 417))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7)
                            .addComponent(jLabel5)
                            .addComponent(jLabel9)
                            .addComponent(jLabel17))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtColor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtMarca, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtEstado, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cbNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(txtId, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel11)))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(124, 124, 124)
                                .addComponent(btnRegistrar)))
                        .addGap(406, 406, 406))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtCreadorComponente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addGap(23, 23, 23)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(cbNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addGap(18, 18, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txtMarca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(11, 11, 11)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(txtColor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel17)
                            .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addComponent(btnRegistrar, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(47, 47, 47))
        );

        background.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 430, 780, 360));

        jPanel2.setBackground(new java.awt.Color(88, 129, 87));

        btnGuardar.setBackground(new java.awt.Color(163, 177, 138));
        btnGuardar.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        btnNuevo.setBackground(new java.awt.Color(163, 177, 138));
        btnNuevo.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        btnNuevo.setText("Nuevo ");
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });

        btnEliminar.setBackground(new java.awt.Color(163, 177, 138));
        btnEliminar.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        btnModificar.setBackground(new java.awt.Color(163, 177, 138));
        btnModificar.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        btnModificar.setText("Modificar");
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });

        btnVerRegistros.setBackground(new java.awt.Color(163, 177, 138));
        btnVerRegistros.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        btnVerRegistros.setText("Ver todos los registros");
        btnVerRegistros.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVerRegistrosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14)
                .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 367, Short.MAX_VALUE)
                .addComponent(btnVerRegistros)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNuevo)
                    .addComponent(btnGuardar)
                    .addComponent(btnEliminar)
                    .addComponent(btnModificar)
                    .addComponent(btnVerRegistros))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        background.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, 1180, 50));

        jPanel4.setBackground(new java.awt.Color(182, 176, 156));

        jLabel16.setBackground(new java.awt.Color(255, 255, 255));
        jLabel16.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(52, 78, 65));
        jLabel16.setText("Nombre del componente:");

        txtBuscar.setBackground(new java.awt.Color(218, 215, 205));
        txtBuscar.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        txtBuscar.setBorder(null);
        txtBuscar.setPreferredSize(new java.awt.Dimension(8, 25));

        btnBuscar.setBackground(new java.awt.Color(163, 177, 138));
        btnBuscar.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        btnBuscar.setText("Buscar componente");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnBuscar)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(70, 70, 70))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnBuscar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        background.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 70, 350, 90));

        jPanel5.setBackground(new java.awt.Color(218, 215, 205));
        jPanel5.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jPanel5MouseDragged(evt);
            }
        });
        jPanel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jPanel5MousePressed(evt);
            }
        });

        btnExit.setBackground(new java.awt.Color(218, 215, 205));
        btnExit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnExitMouseClicked(evt);
            }
        });

        txtExit.setBackground(new java.awt.Color(73, 79, 99));
        txtExit.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        txtExit.setText("x");
        txtExit.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        txtExit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtExitMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                txtExitMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                txtExitMouseExited(evt);
            }
        });

        javax.swing.GroupLayout btnExitLayout = new javax.swing.GroupLayout(btnExit);
        btnExit.setLayout(btnExitLayout);
        btnExitLayout.setHorizontalGroup(
            btnExitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btnExitLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(txtExit, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        btnExitLayout.setVerticalGroup(
            btnExitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnExitLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtExit, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnRegresar.setBackground(new java.awt.Color(218, 215, 205));
        btnRegresar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnRegresar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnRegresarMouseClicked(evt);
            }
        });

        txtRegresar.setBackground(new java.awt.Color(73, 79, 99));
        txtRegresar.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        txtRegresar.setText("<");
        txtRegresar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        txtRegresar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtRegresarMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                txtRegresarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                txtRegresarMouseExited(evt);
            }
        });

        javax.swing.GroupLayout btnRegresarLayout = new javax.swing.GroupLayout(btnRegresar);
        btnRegresar.setLayout(btnRegresarLayout);
        btnRegresarLayout.setHorizontalGroup(
            btnRegresarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 30, Short.MAX_VALUE)
            .addGroup(btnRegresarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(btnRegresarLayout.createSequentialGroup()
                    .addGap(0, 6, Short.MAX_VALUE)
                    .addComponent(txtRegresar)
                    .addGap(0, 6, Short.MAX_VALUE)))
        );
        btnRegresarLayout.setVerticalGroup(
            btnRegresarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(btnRegresarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(btnRegresarLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(txtRegresar)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGap(0, 1124, Short.MAX_VALUE)
                .addComponent(btnRegresar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnExit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnExit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnRegresar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        background.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1200, 40));

        pnlQr1.setBackground(new java.awt.Color(182, 176, 156));

        btnPdf.setBackground(new java.awt.Color(163, 177, 138));
        btnPdf.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        btnPdf.setText("Generar Reporte PDF");
        btnPdf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPdfActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlQr2Layout = new javax.swing.GroupLayout(pnlQr2);
        pnlQr2.setLayout(pnlQr2Layout);
        pnlQr2Layout.setHorizontalGroup(
            pnlQr2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
        pnlQr2Layout.setVerticalGroup(
            pnlQr2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 286, Short.MAX_VALUE)
        );

        btnQr.setBackground(new java.awt.Color(163, 177, 138));
        btnQr.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        btnQr.setText("Guardar Qr como Imagen");
        btnQr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQrActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlQr1Layout = new javax.swing.GroupLayout(pnlQr1);
        pnlQr1.setLayout(pnlQr1Layout);
        pnlQr1Layout.setHorizontalGroup(
            pnlQr1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlQr1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnPdf)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnQr)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlQr1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(pnlQr2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33))
        );
        pnlQr1Layout.setVerticalGroup(
            pnlQr1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlQr1Layout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addComponent(pnlQr2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlQr1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnPdf, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
                    .addComponent(btnQr, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        background.add(pnlQr1, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 430, 370, 360));

        txtCreadorRegistro.setEditable(false);
        txtCreadorRegistro.setBackground(new java.awt.Color(218, 215, 205));
        txtCreadorRegistro.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        txtCreadorRegistro.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        txtCreadorRegistro.setPreferredSize(new java.awt.Dimension(7, 25));
        background.add(txtCreadorRegistro, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 130, 180, -1));

        jLabel19.setBackground(new java.awt.Color(255, 255, 255));
        jLabel19.setFont(new java.awt.Font("Verdana", 0, 16)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(58, 90, 64));
        jLabel19.setText("Codigo creador del registro:");
        background.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 100, -1, -1));

        jLabel18.setBackground(new java.awt.Color(255, 255, 255));
        jLabel18.setFont(new java.awt.Font("Verdana", 0, 16)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(58, 90, 64));
        jLabel18.setText("Codigo Registro:");
        background.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 70, -1, -1));

        txtRegistro.setEditable(false);
        txtRegistro.setBackground(new java.awt.Color(218, 215, 205));
        txtRegistro.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        txtRegistro.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtRegistro.setPreferredSize(new java.awt.Dimension(7, 25));
        background.add(txtRegistro, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 70, 130, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(background, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(background, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    int xMouse, yMouse, opc;
    private void jPanel5MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel5MousePressed
        xMouse = evt.getX();
        yMouse = evt.getY();
    }//GEN-LAST:event_jPanel5MousePressed

    private void jPanel5MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel5MouseDragged
        int x = evt.getXOnScreen();
        int y = evt.getYOnScreen();
        this.setLocation(x - xMouse, y - yMouse);
    }//GEN-LAST:event_jPanel5MouseDragged

    private void btnExitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnExitMouseClicked
        System.exit(0);
    }//GEN-LAST:event_btnExitMouseClicked

    private void txtExitMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtExitMouseEntered
        btnExit.setBackground(Color.red);
        txtExit.setForeground(Color.white);
    }//GEN-LAST:event_txtExitMouseEntered

    private void txtExitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtExitMouseClicked
        System.exit(0);
    }//GEN-LAST:event_txtExitMouseClicked

    private void txtExitMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtExitMouseExited
        btnExit.setBackground(new Color(218, 215, 205));
        txtExit.setForeground(new Color(73, 79, 99));
    }//GEN-LAST:event_txtExitMouseExited

    private void btnVerRegistrosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerRegistrosActionPerformed
        try {
            if (txtCodigo.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Debe ingresar el codigo de la persona");
            } else {

                if (existePersona(txtCodigo.getText()) == 0) {
                    JOptionPane.showMessageDialog(null, "Codigo no encontrado");
                } else {
                    cargarTabla();
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }
    }//GEN-LAST:event_btnVerRegistrosActionPerformed

    private void txtCodigoMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtCodigoMousePressed
        if (txtCodigo.getText().equals("Ingrese codigo")) {
            txtCodigo.setText("");
            txtCodigo.setForeground(Color.black);
        }
    }//GEN-LAST:event_txtCodigoMousePressed

    private void btnVerificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerificarActionPerformed
        try {
            if (existePersona(txtCodigo.getText()) == 0) {
                cbNumero.removeAllItems();
                cbNumero.addItem("Ingrese codigo");
                JOptionPane.showMessageDialog(null, "No se encontro el codigo: " + txtCodigo.getText());
                txtCodigo.setText("");
            } else {
                if (existeRegistro(txtCodigo.getText()) == 0) {
                    cbNumero.removeAllItems();
                    cbNumero.addItem("No se encontraron registros");
                    btnNuevoRegistro.setEnabled(true);
                    btnPdf.setEnabled(true);
                    for (int i = 0; i < tabla1.getRowCount(); i++) {
                        model.removeRow(i);
                        i -= 1;
                    }
                } else {
                    buscarRegistros();
                    cargarRegistros();
                    ultimoRegistro();
                    btnNuevoRegistro.setEnabled(true);
                    btnVerRegistros.setEnabled(true);
                    btnNuevo.setEnabled(true);
                    btnBuscar.setEnabled(true);
                    txtBuscar.setEditable(true);
                    btnPdf.setEnabled(true);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }
    }//GEN-LAST:event_btnVerificarActionPerformed

    private void btnNuevoRegistroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoRegistroActionPerformed
        int mesj;

        try {

            if (opc == 0) {
                mesj = JOptionPane.showConfirmDialog(null, "Añadir un nuevo registro", "Registro", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (mesj == JOptionPane.YES_OPTION) {
                    recibirCodigoCreadorRegistro();
                    sumarRegistro();
                    buscarRegistros();
                    JOptionPane.showMessageDialog(this, "Registro Añadido");
                    ultimoRegistro();
                    btnNuevoRegistro.setEnabled(true);
                    btnVerRegistros.setEnabled(true);
                    btnNuevo.setEnabled(true);
                    btnBuscar.setEnabled(true);
                    txtBuscar.setEditable(true);
                    btnPdf.setEnabled(true);
                } else {
                    JOptionPane.showMessageDialog(this, "Registro no Añadido", "Sistema", JOptionPane.ERROR_MESSAGE);
                }
            }

        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }
    }//GEN-LAST:event_btnNuevoRegistroActionPerformed


    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        try {

            if (txtBuscar.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Debe ingresar el nombre del componente");
            } else {
                if (existeComponente(txtBuscar.getText()) == 0) {
                    JOptionPane.showMessageDialog(null, "No se encontro el componente: " + txtBuscar.getText());
                    txtBuscar.setText("");
                } else {
                    buscarNombreComponente();
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        btnRegistrar.setEnabled(true);
        limpiar();
        cbNombre.setEnabled(true);
        nuevoId();
        recibirCodigoCreadorComponente();
        
        
        
  //nuevo id tiene la falla de los registros


    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        try {
            modificarComponente();
            cargarTabla();
            limpiar();
            btnGuardar.setEnabled(false);
            txtId.setText("");
            camposNoEditables();
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        cbNombre.setEnabled(false);

        int fila;
        fila = tabla1.getSelectedRow();
        cbNombre.setSelectedItem(tabla1.getValueAt(fila, 2));
        descripcionComponente.txtMarca.setText(txtMarca.getText());
        descripcionComponente.txtEstado.setText(txtEstado.getText());
        descripcionComponente.txtColor.setText(txtColor.getText());

        btnGuardar.setEnabled(true);
        btnModificar.setEnabled(false);
        btnEliminar.setEnabled(false);

        descripcionComponente.btnRegistrar.setEnabled(false);
        descripcionComponente.btnGuardarComponente.setEnabled(true);

    }//GEN-LAST:event_btnModificarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        try {
            int mesj;
            btnEliminar.setEnabled(false);
            if (opc == 0) {
                mesj = JOptionPane.showConfirmDialog(null, "Eliminar registro", "Registro", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (mesj == JOptionPane.YES_OPTION) {
                    eliminarComponente();
                    eliminarDescripcion();
                    cargarTabla();
                    JOptionPane.showMessageDialog(this, "Registro Eliminado");
                } else {
                    JOptionPane.showMessageDialog(this, "Registro no Eliminado", "Sistema", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnRegistrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarActionPerformed

        try {
            registrarComponente();
            btnRegistrar.setEnabled(false);
            limpiar();
            cargarTabla();
            JOptionPane.showMessageDialog(this, "Componente registrado");
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }

    }//GEN-LAST:event_btnRegistrarActionPerformed

    private void tabla1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabla1MouseClicked
        llenarCuadrostexto();
        btnRegistrar.setEnabled(false);
        btnQr.setEnabled(true);
        camposNoEditables();
        datosQr();
        int fila;
        fila = tabla1.getSelectedRow();
        String full = String.valueOf(tabla1.getValueAt(fila, 1));
        if (full.equals(txtRegistro.getText())) {
            btnEliminar.setEnabled(true);
            btnModificar.setEnabled(true);
        } else {
            btnEliminar.setEnabled(false);
            btnModificar.setEnabled(false);
        }
    }//GEN-LAST:event_tabla1MouseClicked

    private void btnPdfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPdfActionPerformed
        try {
            if (txtCodigo.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Debe ingresar el codigo de la persona");
            } else {

                if (existePersona(txtCodigo.getText()) == 0) {
                    JOptionPane.showMessageDialog(null, "Codigo no encontrado");
                } else {
                    reportePDF();
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }
    }//GEN-LAST:event_btnPdfActionPerformed

    private void btnQrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQrActionPerformed
        guardarImagenQr();
    }//GEN-LAST:event_btnQrActionPerformed

    private void txtRegresarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtRegresarMouseClicked
//        menu me = new menu();
//        me.setVisible(true);
        dispose();
    }//GEN-LAST:event_txtRegresarMouseClicked

    private void txtRegresarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtRegresarMouseEntered
        btnRegresar.setBackground(Color.white);
        txtRegresar.setForeground(Color.red);
    }//GEN-LAST:event_txtRegresarMouseEntered

    private void txtRegresarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtRegresarMouseExited
        btnRegresar.setBackground(new Color(218, 215, 205));
        txtRegresar.setForeground(Color.BLACK);
    }//GEN-LAST:event_txtRegresarMouseExited

    private void btnRegresarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnRegresarMouseClicked
//        menu me = new menu();
//        me.setVisible(true);
        dispose();
    }//GEN-LAST:event_btnRegresarMouseClicked

    private void cbNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbNombreActionPerformed
        String nombre = String.valueOf(cbNombre.getSelectedItem());
        if (nombre.equals("--Seleccionar--")) {

        } else {
            descripcionComponente dc;
            try {
                dc = new descripcionComponente();
                dc.setVisible(true);
                descripcionComponente.txtNombre.setText(nombre);
                descripcionComponente.txtIdComponente.setText(txtId.getText());
                enviarCaracteristicas();
                descripcionComponente.btnGuardarComponente.setEnabled(false);

            } catch (SQLException ex) {
                Logger.getLogger(registroComponente.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }//GEN-LAST:event_cbNombreActionPerformed

    private void cbNumeroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbNumeroActionPerformed
        try {
            if (existeRegistro(txtCodigo.getText()) == 0) {
                String idReg = "No se encontraron registros";
                txtRegistro.setText(idReg);

            } else {
                cargarRegistros();
                codigoCreadorRegistro();
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);

        }
    }//GEN-LAST:event_cbNumeroActionPerformed

    public static void main(String args[]) {

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(registroComponente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(registroComponente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(registroComponente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(registroComponente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new registroComponente().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel background;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JPanel btnExit;
    public static javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JButton btnNuevoRegistro;
    private javax.swing.JButton btnPdf;
    private javax.swing.JButton btnQr;
    public static javax.swing.JButton btnRegistrar;
    private javax.swing.JPanel btnRegresar;
    private javax.swing.JButton btnVerRegistros;
    private javax.swing.JButton btnVerificar;
    private javax.swing.JComboBox<String> cbNombre;
    private javax.swing.JComboBox<String> cbNumero;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPanel pnlQr1;
    private javax.swing.JPanel pnlQr2;
    private javax.swing.JTable tabla1;
    private javax.swing.JTextField txtBuscar;
    private javax.swing.JTextField txtCodigo;
    public static javax.swing.JTextField txtColor;
    private javax.swing.JTextField txtCreadorComponente;
    private javax.swing.JTextField txtCreadorRegistro;
    public static javax.swing.JTextArea txtDescripcion;
    public static javax.swing.JTextField txtEstado;
    private javax.swing.JLabel txtExit;
    public static javax.swing.JTextField txtId;
    public static javax.swing.JTextField txtMarca;
    private javax.swing.JTextField txtRegistro;
    private javax.swing.JLabel txtRegresar;
    // End of variables declaration//GEN-END:variables
}
