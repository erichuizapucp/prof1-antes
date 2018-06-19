package com.prosports.prof1.Controladores;

import com.prosports.prof1.Entidades.Merchandising;
import com.prosports.prof1.Reportes.ReporteCorreo;
import com.prosports.prof1.Reportes.ReporteExcel;
import com.prosports.prof1.Repositorios.ProductosRepo;
import com.prosports.prof1.Views.ExcelView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ControladorProductos {
    @Autowired
    protected ProductosRepo productosRepo;

    @Autowired
    protected ReporteCorreo reporteCorreo;

    @Autowired
    protected ReporteExcel reporteExcel;

    @RequestMapping("/")
    public ModelAndView obtenerTodosLosProductos(HttpServletRequest request, HttpServletResponse response) {
        List<Merchandising> merchandising = new ArrayList<>();
        productosRepo.findAll().forEach(merchandising :: add);

        ModelAndView model = new ModelAndView("productos");
        model.addObject("productos", merchandising);

        return model;
    }

    @RequestMapping(value = "/reporte")
    public @ResponseBody ModelAndView obtenerReporte(@RequestParam(value = "opcion", required = false) String option) {
        if (option.equals("excel")) {
            Map<String, Object> modelo = new HashMap<>();
            modelo.put("ReporteExcel", reporteExcel.generarReporteEnExcel());

            return new ModelAndView(new ExcelView(), modelo);
        }
        else {
            Map<String, Object> modelo = new HashMap<>();
            modelo.put("ReporteCorreo", reporteCorreo.generarReporteCorreo());

            return new ModelAndView("correo", modelo);
        }
    }
}