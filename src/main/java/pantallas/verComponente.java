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
import datos.Conexion;
import java.awt.Color;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class verComponente extends javax.swing.JFrame {

    DefaultTableModel model;

    public verComponente() throws SQLException {
        initComponents();
        this.setLocationRelativeTo(null);
        CbFiltro();
        CbFiltroPersona();
        btnBuscarPersona.setEnabled(false);

        try {
            if (UIManager.getLookAndFeel().toString().contains("Nimbus")) {
                UIManager.put("nimbusBlueGrey", new Color(52, 78, 65));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void CbFiltro() {
        cbBuscar.addItem("--Seleccionar--");
        cbBuscar.addItem("Id de componente");
        cbBuscar.addItem("Id de registro");
        cbBuscar.addItem("Nombre");
        cbBuscar.addItem("Fecha de registro");
        cbBuscar.addItem("Marca");
        cbBuscar.addItem("Estado");
        cbBuscar.addItem("Color");
        cbBuscar.addItem("Descripcion");
        cbBuscar.addItem("Creador");

    }

    void CbFiltroPersona() {
        cbBuscarPersona.addItem("--Seleccionar--");
        cbBuscarPersona.addItem("Id de componente");
        cbBuscarPersona.addItem("Nombre");
        cbBuscarPersona.addItem("Fecha de registro");
        cbBuscarPersona.addItem("Marca");
        cbBuscarPersona.addItem("Estado");
        cbBuscarPersona.addItem("Color");
        cbBuscarPersona.addItem("Descripcion");
        cbBuscarPersona.addItem("Creador");
    }

    private String[] tituloColumnas() throws SQLException {
        String[] titulos = {"Id", "Registro", "Nombre", "Registro", "Marca", "Estado", "Color", "Descripcion", "Creador"};
        return titulos;
    }

    void formatoTabla() {
        DefaultTableCellRenderer Alinear = new DefaultTableCellRenderer();
        Alinear.setHorizontalAlignment(SwingConstants.CENTER);
        tabla1.getColumnModel().getColumn(0).setPreferredWidth(10);
        tabla1.getColumnModel().getColumn(0).setCellRenderer(Alinear);
        tabla1.getColumnModel().getColumn(1).setPreferredWidth(50);
        tabla1.getColumnModel().getColumn(1).setCellRenderer(Alinear);
        tabla1.getColumnModel().getColumn(2).setCellRenderer(Alinear);
        tabla1.getColumnModel().getColumn(3).setPreferredWidth(52);
        tabla1.getColumnModel().getColumn(3).setCellRenderer(Alinear);
        tabla1.getColumnModel().getColumn(4).setCellRenderer(Alinear);
        tabla1.getColumnModel().getColumn(5).setCellRenderer(Alinear);
        tabla1.getColumnModel().getColumn(6).setCellRenderer(Alinear);
        tabla1.getColumnModel().getColumn(7).setCellRenderer(Alinear);
        tabla1.getColumnModel().getColumn(8).setPreferredWidth(50);
        tabla1.getColumnModel().getColumn(8).setCellRenderer(Alinear);

    }

    void contarRegistros() {
        int fila = tabla1.getRowCount();
        txtNumeroRegistros.setText("" + fila);
    }

    private void cargarTabla() throws SQLException {
        String[] registros;
        registros = new String[50];
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement("SELECT id, fkregistro, nombre, SUBSTRING(fecha, 1,10) AS fecha, marca, estado, color, descripcion, creador FROM componente ORDER BY fkregistro");
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
            formatoTabla();
        } finally {
            Conexion.close(rs);
            Conexion.close(stmt);
            Conexion.close(conn);

        }
    }

    private void nombreComponente() {
        String[] registros = new String[50];
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement("SELECT DISTINCT nombre FROM componente");
            rs = stmt.executeQuery();
            String[] titulo = {"Nombre de Componentes"};
            model = new DefaultTableModel(null, titulo);
            while (rs.next()) {
                registros[0] = rs.getString(1);
                model.addRow(registros);
            }
            tabla1.setModel(model);
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            Conexion.close(rs);
            Conexion.close(stmt);
            Conexion.close(conn);
        }
    }

    private void nombreDescripcion() {
        String[] registros = new String[50];
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement("SELECT nombre, caracteristica FROM nomcomp ORDER BY nombre");
            rs = stmt.executeQuery();
            String[] titulo = {"Nombre de Componentes", "Caracteristica"};
            model = new DefaultTableModel(null, titulo);
            while (rs.next()) {
                registros[0] = rs.getString(1);
                registros[1] = rs.getString(2);
                model.addRow(registros);
            }
            tabla1.setModel(model);
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            Conexion.close(rs);
            Conexion.close(stmt);
            Conexion.close(conn);
        }
    }

    private void buscar() {
        String sql = "";
        String filtro = txtBuscar.getText();
        if (cbBuscar.getSelectedIndex() == 0) {
            sql = "SELECT id, fkregistro, nombre, SUBSTRING(fecha, 1,10) AS fecha, marca, estado, color, descripcion, creador FROM componente";
        } else {
            if (cbBuscar.getSelectedIndex() == 1) {
                sql = "SELECT id, fkregistro, nombre, SUBSTRING(fecha, 1,10) AS fecha, marca, estado, color, descripcion, creador FROM componente WHERE id LIKE " + filtro + "";
            } else {
                if (cbBuscar.getSelectedIndex() == 2) {
                    sql = "SELECT id, fkregistro, nombre, SUBSTRING(fecha, 1,10) AS fecha, marca, estado, color, descripcion, creador FROM componente WHERE fkregistro LIKE '%" + filtro + "%'";
                } else {
                    if (cbBuscar.getSelectedIndex() == 3) {
                        sql = "SELECT id, fkregistro, nombre, SUBSTRING(fecha, 1,10) AS fecha, marca, estado, color, descripcion, creador FROM componente WHERE nombre LIKE '%" + filtro + "%'";
                    } else {
                        if (cbBuscar.getSelectedIndex() == 4) {
                            sql = "SELECT id, fkregistro, nombre, SUBSTRING(fecha, 1,10) AS fecha, marca, estado, color, descripcion, creador FROM componente WHERE fecha LIKE '%" + filtro + "%'";
                        } else {
                            if (cbBuscar.getSelectedIndex() == 5) {
                                sql = "SELECT id, fkregistro, nombre, SUBSTRING(fecha, 1,10) AS fecha, marca, estado, color, descripcion, creador FROM componente WHERE marca LIKE '%" + filtro + "%'";
                            } else {
                                if (cbBuscar.getSelectedIndex() == 6) {
                                    sql = "SELECT id, fkregistro, nombre, SUBSTRING(fecha, 1,10) AS fecha, marca, estado, color, descripcion, creador FROM componente WHERE estado LIKE '%" + filtro + "%'";
                                } else {
                                    if (cbBuscar.getSelectedIndex() == 7) {
                                        sql = "SELECT id, fkregistro, nombre, SUBSTRING(fecha, 1,10) AS fecha, marca, estado, color, descripcion, creador FROM componente WHERE color LIKE '%" + filtro + "%'";
                                    } else {
                                        if (cbBuscar.getSelectedIndex() == 8) {
                                            sql = "SELECT id, fkregistro, nombre, SUBSTRING(fecha, 1,10) AS fecha, marca, estado, color, descripcion, creador FROM componente WHERE descripcion LIKE '%" + filtro + "%'";
                                        } else {
                                            if (cbBuscar.getSelectedIndex() == 9) {
                                                sql = "SELECT id, fkregistro, nombre, SUBSTRING(fecha, 1,10) AS fecha, marca, estado, color, descripcion, creador FROM componente WHERE creador LIKE '%" + filtro + "%'";
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        String[] registros = new String[50];
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(sql);
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
            formatoTabla();
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {
            Conexion.close(rs);
            Conexion.close(stmt);
            Conexion.close(conn);
        }
    }

    private void buscarComponentePersona() {
        String sql = "";
        String filtro = txtBuscarPersona.getText();
        String codigo = txtCodigo.getText();

        if (cbBuscarPersona.getSelectedIndex() == 1) {
            sql = "SELECT id, fkregistro, nombre, SUBSTRING(fecha, 1,10) AS fecha, marca, estado, color, descripcion, creador FROM componente WHERE fkregistro IN (SELECT id FROM registro WHERE fkpersona LIKE '" + codigo + "') and id LIKE " + filtro + "";
        } else {
            if (cbBuscarPersona.getSelectedIndex() == 2) {
                sql = "SELECT id, fkregistro, nombre, SUBSTRING(fecha, 1,10) AS fecha, marca, estado, color, descripcion, creador FROM componente WHERE fkregistro IN (SELECT id FROM registro WHERE fkpersona LIKE '" + codigo + "') and nombre LIKE '%" + filtro + "%'";
            } else {
                if (cbBuscarPersona.getSelectedIndex() == 3) {
                    sql = "SELECT id, fkregistro, nombre, SUBSTRING(fecha, 1,10) AS fecha, marca, estado, color, descripcion, creador FROM componente WHERE fkregistro IN (SELECT id FROM registro WHERE fkpersona LIKE '" + codigo + "') and fecha LIKE '%" + filtro + "%'";
                } else {
                    if (cbBuscarPersona.getSelectedIndex() == 4) {
                        sql = "SELECT id, fkregistro, nombre, SUBSTRING(fecha, 1,10) AS fecha, marca, estado, color, descripcion, creador FROM componente WHERE fkregistro IN (SELECT id FROM registro WHERE fkpersona LIKE '" + codigo + "') and marca LIKE '%" + filtro + "%'";
                    } else {
                        if (cbBuscarPersona.getSelectedIndex() == 5) {
                            sql = "SELECT id, fkregistro, nombre, SUBSTRING(fecha, 1,10) AS fecha, marca, estado, color, descripcion, creador FROM componente WHERE fkregistro IN (SELECT id FROM registro WHERE fkpersona LIKE '" + codigo + "') and estado LIKE '%" + filtro + "%'";
                        } else {
                            if (cbBuscarPersona.getSelectedIndex() == 6) {
                                sql = "SELECT id, fkregistro, nombre, SUBSTRING(fecha, 1,10) AS fecha, marca, estado, color, descripcion, creador FROM componente WHERE fkregistro IN (SELECT id FROM registro WHERE fkpersona LIKE '" + codigo + "') and color LIKE '%" + filtro + "%'";
                            } else {
                                if (cbBuscarPersona.getSelectedIndex() == 7) {
                                    sql = "SELECT id, fkregistro, nombre, SUBSTRING(fecha, 1,10) AS fecha, marca, estado, color, descripcion, creador FROM componente WHERE fkregistro IN (SELECT id FROM registro WHERE fkpersona LIKE '" + codigo + "') and descripcion LIKE '%" + filtro + "%'";
                                } else {
                                    if (cbBuscarPersona.getSelectedIndex() == 8) {
                                        sql = "SELECT id, fkregistro, nombre, SUBSTRING(fecha, 1,10) AS fecha, marca, estado, color, descripcion, creador FROM componente WHERE fkregistro IN (SELECT id FROM registro WHERE fkpersona LIKE '" + codigo + "') and creador LIKE '%" + filtro + "%'";
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        String[] registros = new String[50];
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement(sql);
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
            formatoTabla();
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

    private void cargarRegistrosPersona() throws SQLException {
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
            formatoTabla();
        } finally {
            Conexion.close(rs);
            Conexion.close(stmt);
            Conexion.close(conn);

        }
    }

    private void cargarTablaPersona() throws SQLException {
        String[] registros;
        registros = new String[50];
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            String filtro = "%" + txtCodigo.getText() + "%";
            conn = Conexion.getConnection();
            stmt = conn.prepareStatement("SELECT id, fkregistro, nombre, SUBSTRING(fecha, 1,10) AS fecha, marca, estado, color, descripcion, creador FROM componente WHERE fkregistro IN (SELECT id FROM registro WHERE fkpersona LIKE '" + filtro + "');");
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
            formatoTabla();
        } finally {
            Conexion.close(rs);
            Conexion.close(stmt);
            Conexion.close(conn);

        }
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

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        btnExit = new javax.swing.JPanel();
        txtExit = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        btnRegresar = new javax.swing.JPanel();
        txtRegresar = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabla1 = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        txtBuscar = new javax.swing.JTextField();
        btnBuscarActivo = new javax.swing.JButton();
        cbBuscar = new javax.swing.JComboBox<>();
        btnVerActivo = new javax.swing.JButton();
        btnComponente = new javax.swing.JButton();
        btnDescripcion = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        cbNumero = new javax.swing.JComboBox<>();
        txtCodigo = new javax.swing.JTextField();
        btnVerificar = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        btnPdf = new javax.swing.JButton();
        btnRegistrosPersona = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        cbBuscarPersona = new javax.swing.JComboBox<>();
        jLabel18 = new javax.swing.JLabel();
        txtBuscarPersona = new javax.swing.JTextField();
        btnBuscarPersona = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        txtNumeroRegistros = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        setSize(getPreferredSize());

        jPanel1.setBackground(new java.awt.Color(218, 215, 205));
        jPanel1.setPreferredSize(new java.awt.Dimension(1200, 800));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnExit.setBackground(new java.awt.Color(218, 215, 205));
        btnExit.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
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

        jPanel1.add(btnExit, new org.netbeans.lib.awtextra.AbsoluteConstraints(1230, 0, -1, -1));

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
            .addGap(0, 29, Short.MAX_VALUE)
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
                .addContainerGap(1187, Short.MAX_VALUE)
                .addComponent(btnRegresar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(53, 53, 53))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(btnRegresar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel1.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1270, 40));

        jLabel1.setFont(new java.awt.Font("Open Sans", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(52, 78, 65));
        jLabel1.setText("Ver Registros de Componentes");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 560, 50));

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
        jScrollPane1.setViewportView(tabla1);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 400, 1180, 380));

        jPanel4.setBackground(new java.awt.Color(182, 176, 156));

        jLabel17.setBackground(new java.awt.Color(255, 255, 255));
        jLabel17.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(52, 78, 65));
        jLabel17.setText("Buscar por filtro:");

        txtBuscar.setBackground(new java.awt.Color(218, 215, 205));
        txtBuscar.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        txtBuscar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtBuscar.setPreferredSize(new java.awt.Dimension(7, 25));

        btnBuscarActivo.setBackground(new java.awt.Color(163, 177, 138));
        btnBuscarActivo.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        btnBuscarActivo.setText("BUSCAR");
        btnBuscarActivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActivoActionPerformed(evt);
            }
        });

        cbBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbBuscarActionPerformed(evt);
            }
        });

        btnVerActivo.setBackground(new java.awt.Color(163, 177, 138));
        btnVerActivo.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        btnVerActivo.setText("VER TODOS LOS REGISTROS");
        btnVerActivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVerActivoActionPerformed(evt);
            }
        });

        btnComponente.setBackground(new java.awt.Color(163, 177, 138));
        btnComponente.setText("LISTA DE COMPONENTES");
        btnComponente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnComponenteActionPerformed(evt);
            }
        });

        btnDescripcion.setBackground(new java.awt.Color(163, 177, 138));
        btnDescripcion.setText("LISTA DE DESCRIPCIONES");
        btnDescripcion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDescripcionActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel17)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnBuscarActivo, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(cbBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 84, Short.MAX_VALUE)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnVerActivo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnComponente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnDescripcion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(32, 32, 32))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel17)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnBuscarActivo))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(btnVerActivo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnComponente)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnDescripcion)))
                .addContainerGap(17, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, 660, 140));

        jPanel2.setBackground(new java.awt.Color(163, 177, 138));

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setFont(new java.awt.Font("Verdana", 1, 16)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(58, 90, 64));
        jLabel2.setText("Codigo de Persona:");

        cbNumero.setBackground(new java.awt.Color(103, 102, 97));
        cbNumero.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
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

        btnPdf.setBackground(new java.awt.Color(163, 177, 138));
        btnPdf.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        btnPdf.setText("Generar reporte Pdf del registro");
        btnPdf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPdfActionPerformed(evt);
            }
        });

        btnRegistrosPersona.setBackground(new java.awt.Color(163, 177, 138));
        btnRegistrosPersona.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        btnRegistrosPersona.setText("Ver todos los registros");
        btnRegistrosPersona.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrosPersonaActionPerformed(evt);
            }
        });

        jPanel3.setBackground(new java.awt.Color(182, 176, 156));

        cbBuscarPersona.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbBuscarPersonaActionPerformed(evt);
            }
        });

        jLabel18.setBackground(new java.awt.Color(255, 255, 255));
        jLabel18.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(52, 78, 65));
        jLabel18.setText("Buscar por filtro:");

        txtBuscarPersona.setBackground(new java.awt.Color(218, 215, 205));
        txtBuscarPersona.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        txtBuscarPersona.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtBuscarPersona.setPreferredSize(new java.awt.Dimension(7, 25));

        btnBuscarPersona.setBackground(new java.awt.Color(163, 177, 138));
        btnBuscarPersona.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        btnBuscarPersona.setText("BUSCAR");
        btnBuscarPersona.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarPersonaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel18)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cbBuscarPersona, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtBuscarPersona, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnBuscarPersona, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(cbBuscarPersona, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtBuscarPersona, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addComponent(btnBuscarPersona)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(25, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btnRegistrosPersona)
                    .addComponent(btnPdf, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel4))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbNumero, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(btnVerificar, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(btnVerificar))
                    .addComponent(txtCodigo, javax.swing.GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbNumero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(18, 18, 18)
                .addComponent(btnPdf)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnRegistrosPersona)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(25, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 110, 540, 280));

        jLabel3.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        jLabel3.setText("Numero de registros:");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 370, -1, -1));
        jPanel1.add(txtNumeroRegistros, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 370, 100, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    int xMouse, yMouse, opc;

    private void txtExitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtExitMouseClicked
        System.exit(0);
    }//GEN-LAST:event_txtExitMouseClicked

    private void txtExitMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtExitMouseEntered
        btnExit.setBackground(Color.red);
        txtExit.setForeground(Color.white);
    }//GEN-LAST:event_txtExitMouseEntered

    private void txtExitMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtExitMouseExited
        btnExit.setBackground(new Color(218, 215, 205));
        txtExit.setForeground(new Color(73, 79, 99));
    }//GEN-LAST:event_txtExitMouseExited

    private void btnExitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnExitMouseClicked
        System.exit(0);
    }//GEN-LAST:event_btnExitMouseClicked

    private void jPanel5MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel5MouseDragged
        int x = evt.getXOnScreen();
        int y = evt.getYOnScreen();
        this.setLocation(x - xMouse, y - yMouse);
    }//GEN-LAST:event_jPanel5MouseDragged

    private void jPanel5MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel5MousePressed
        xMouse = evt.getX();
        yMouse = evt.getY();
    }//GEN-LAST:event_jPanel5MousePressed

    private void txtRegresarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtRegresarMouseClicked
//        login lo = new login();
//        lo.setVisible(true);
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
//        login lo = new login();
//        lo.setVisible(true);
        dispose();
    }//GEN-LAST:event_btnRegresarMouseClicked

    private void btnBuscarActivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActivoActionPerformed
        if (cbBuscar.getSelectedItem().equals("--Seleccionar--")) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar un filtro");
        } else {
            txtCodigo.setText("");
            cbNumero.removeAllItems();
            btnPdf.setEnabled(false);
            btnBuscarPersona.setEnabled(false);
            if (txtBuscar.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Debe ingresar el texto a buscar");
            } else {
                buscar();
                contarRegistros();
            }
        }

    }//GEN-LAST:event_btnBuscarActivoActionPerformed

    private void cbBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbBuscarActionPerformed
        btnBuscarActivo.setEnabled(true);
    }//GEN-LAST:event_cbBuscarActionPerformed

    private void btnVerActivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerActivoActionPerformed
        try {
            cargarTabla();
            contarRegistros();
            txtCodigo.setText("");
            cbNumero.removeAllItems();
            btnPdf.setEnabled(false);
        } catch (SQLException ex) {
            Logger.getLogger(verComponente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnVerActivoActionPerformed

    private void btnComponenteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnComponenteActionPerformed
        nombreComponente();
        contarRegistros();
    }//GEN-LAST:event_btnComponenteActionPerformed

    private void cbNumeroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbNumeroActionPerformed
        try {
            if (existeRegistro(txtCodigo.getText()) == 0) {

            } else {
                cargarRegistrosPersona();
                contarRegistros();
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);

        }
    }//GEN-LAST:event_cbNumeroActionPerformed

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
                } else {
                    buscarRegistros();
                    cargarRegistrosPersona();
                    contarRegistros();
                    btnRegistrosPersona.setEnabled(true);
                    btnPdf.setEnabled(true);
                    btnBuscarPersona.setEnabled(true);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }
    }//GEN-LAST:event_btnVerificarActionPerformed

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

    private void btnRegistrosPersonaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrosPersonaActionPerformed
        try {
            cargarTablaPersona();
        } catch (SQLException ex) {
            Logger.getLogger(verComponente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnRegistrosPersonaActionPerformed

    private void cbBuscarPersonaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbBuscarPersonaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbBuscarPersonaActionPerformed

    private void btnBuscarPersonaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarPersonaActionPerformed
        buscarComponentePersona();
        contarRegistros();
    }//GEN-LAST:event_btnBuscarPersonaActionPerformed

    private void btnDescripcionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDescripcionActionPerformed
        nombreDescripcion();
    }//GEN-LAST:event_btnDescripcionActionPerformed

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(verComponente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(verComponente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(verComponente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(verComponente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new verComponente().setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(verComponente.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscarActivo;
    private javax.swing.JButton btnBuscarPersona;
    private javax.swing.JButton btnComponente;
    private javax.swing.JButton btnDescripcion;
    private javax.swing.JPanel btnExit;
    private javax.swing.JButton btnPdf;
    private javax.swing.JButton btnRegistrosPersona;
    private javax.swing.JPanel btnRegresar;
    private javax.swing.JButton btnVerActivo;
    private javax.swing.JButton btnVerificar;
    private javax.swing.JComboBox<String> cbBuscar;
    private javax.swing.JComboBox<String> cbBuscarPersona;
    private javax.swing.JComboBox<String> cbNumero;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tabla1;
    private javax.swing.JTextField txtBuscar;
    private javax.swing.JTextField txtBuscarPersona;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JLabel txtExit;
    private javax.swing.JTextField txtNumeroRegistros;
    private javax.swing.JLabel txtRegresar;
    // End of variables declaration//GEN-END:variables
}
