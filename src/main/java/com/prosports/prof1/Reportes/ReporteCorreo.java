package com.prosports.prof1.Reportes;

import com.prosports.prof1.Entidades.Merchandising;
import com.prosports.prof1.Repositorios.ProductosRepo;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

@Component
public class ReporteCorreo {
    @Autowired
    protected ProductosRepo productosRepo;

    public String generarReporteCorreo() {
        List<Merchandising> productos = new ArrayList<>();
        productosRepo.findAll().forEach(productos::add);

        VelocityEngine engine = new VelocityEngine();
        engine.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
        engine.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        engine.init();

        Template template = engine.getTemplate("templates/email.vm");

        VelocityContext context = new VelocityContext();
        context.put("productos", productos);

        StringWriter writer = new StringWriter();
        template.merge(context, writer);

        return writer.getBuffer().toString();
    }
}