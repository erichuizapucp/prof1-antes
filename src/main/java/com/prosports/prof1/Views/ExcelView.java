package com.prosports.prof1.Views;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.AbstractView;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class ExcelView extends AbstractView {
    public ExcelView() {
        this.setContentType("application/vnd.ms-excel");
    }

    @Override
    protected void renderMergedOutputModel(Map<String, Object> model,
                                           HttpServletRequest request,
                                           HttpServletResponse response) throws Exception {
        Workbook workbook = (Workbook)model.get("ReporteExcel");
        response.setContentType(this.getContentType());
        this.renderWorkbook(workbook, response);
    }

    protected void renderWorkbook(Workbook workbook, HttpServletResponse response) throws IOException {
        ServletOutputStream out = response.getOutputStream();
        workbook.write(out);
        workbook.close();
    }
}
