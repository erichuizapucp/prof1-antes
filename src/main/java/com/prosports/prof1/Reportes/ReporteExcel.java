package com.prosports.prof1.Reportes;

import com.prosports.prof1.Entidades.Merchandising;
import com.prosports.prof1.Repositorios.ProductosRepo;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ReporteExcel {
    private final int PRIMERA_FILA = 1;
    private final int PRIMERA_COLUMNA = 0;

    private final int CABECERA_TABLA_TAMANO_FUENTE = 12;
    private final int CONTENIDO_TAMANO_FUENTE = 12;

    private final String FUENTE_FAMILIA = "Arial";

    @Autowired
    protected ProductosRepo productosRepo;

    public Workbook generarReporteEnExcel() {
        Workbook libroTrabajo = new HSSFWorkbook();
        String nombreHojaCalculo = "Listado de Productos";

        Sheet hojaTrabajo = libroTrabajo.createSheet(nombreHojaCalculo);

        int indiceFilas = 2;
        agregarCabeceras(libroTrabajo, hojaTrabajo);

        List<Merchandising> productos = new ArrayList<>();
        productosRepo.findAll().forEach(productos::add);

        for (Merchandising producto : productos) {
            agregarFilas(libroTrabajo, hojaTrabajo, producto, (short)indiceFilas++);
        }

        return libroTrabajo;
    }

    private void agregarCabeceras(Workbook libroTrabajo, Sheet hojaTrabajo) {
        Row primeraFila = hojaTrabajo.createRow((short)PRIMERA_FILA);
        Font fuente = obtenerFuenteDeCabecera(libroTrabajo);
        CellStyle estilo = obtenerEstilosDeCabecera(libroTrabajo, fuente);

        agregarCeldaDeCabecera("Código", primeraFila, estilo, PRIMERA_COLUMNA);
        agregarCeldaDeCabecera("Nombre", primeraFila, estilo, PRIMERA_COLUMNA + 1);
        agregarCeldaDeCabecera("Descripción", primeraFila, estilo, PRIMERA_COLUMNA + 2);
        agregarCeldaDeCabecera("Precio", primeraFila, estilo, PRIMERA_COLUMNA + 3);
    }

    private void agregarFilas(Workbook libroTrabajo, Sheet hojaTrabajo, Merchandising producto, short indice) {
        Row fila = hojaTrabajo.createRow(indice);
        Font fuente = obtenerFuenteDeFila(libroTrabajo);

        CellStyle estilo = obtenerEstilosDeFilas(libroTrabajo, fuente);
        estilo.setVerticalAlignment(VerticalAlignment.CENTER);

        agregarCeldaContenido(Integer.toString(producto.getCodigo()), fila, estilo, PRIMERA_COLUMNA);
        agregarCeldaContenido(producto.getNombre(), fila, estilo, PRIMERA_COLUMNA + 1);
        agregarCeldaContenido(producto.getDescripcion(), fila, estilo, PRIMERA_COLUMNA + 2);
        agregarCeldaContenido(Double.toString(producto.getPrecio()), fila, estilo, PRIMERA_COLUMNA + 3);
    }

    private void agregarCeldaDeCabecera(String titulo, Row fila, CellStyle estilo, int indice) {
        Cell cell = fila.createCell(indice);
        cell.setCellValue(titulo);

        cell.setCellStyle(estilo);
    }

    private CellStyle obtenerEstilosDeCabecera(Workbook libroTrabajo, Font fuente) {
        CellStyle estilo = libroTrabajo.createCellStyle();

        estilo.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        estilo.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        estilo.setFont(fuente);

        return estilo;
    }

    private CellStyle obtenerEstilosDeFilas(Workbook libroTrabajo, Font fuente) {
        CellStyle estilo = libroTrabajo.createCellStyle();
        estilo.setFont(fuente);

        return estilo;
    }

    private Font obtenerFuenteDeCabecera(Workbook libroTrabajo) {
        return obtenerFuenteNegra(libroTrabajo, CABECERA_TABLA_TAMANO_FUENTE, FUENTE_FAMILIA, false);
    }

    private Font obtenerFuentePersonalizada(Workbook libroTrabajo, int tamano, String familia, short color, boolean negrita) {
        Font fuente = libroTrabajo.createFont();

        fuente.setBold(negrita);
        fuente.setColor(color);
        fuente.setFontName(familia);
        fuente.setFontHeightInPoints((short)tamano);

        return fuente;
    }

    private Font obtenerFuenteDeFila(Workbook libroTrabajo) {
        return obtenerFuenteNegra(libroTrabajo, CONTENIDO_TAMANO_FUENTE, FUENTE_FAMILIA, false);
    }

    private Font obtenerFuenteNegra(Workbook libroTrabajo, int tamano, String familia, boolean negrita) {
        return obtenerFuentePersonalizada(libroTrabajo, tamano, familia, HSSFColor.BLACK.index, negrita);
    }

    private void agregarCeldaContenido(String valor, Row fila, CellStyle estilo, int columna) {
        Cell celda = fila.createCell(columna);
        celda.setCellValue(valor);
        celda.setCellStyle(estilo);
    }
}