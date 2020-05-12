package com.bsoft.template.util.jasper;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.export.ExporterInput;
import net.sf.jasperreports.export.OutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

/**
 * 报表工具类
 */
public class JasperUtil {

    /**
     * 导出word到磁盘
     * @param templateName 模板名
     * @param dataMap 数据
     * @param list 列表
     * @param fileName 文件名
     */
    public static <M> void createWord(String templateName, Map<String, Object> dataMap,
                                  List<M> list, String fileName) throws JRException {
        String report = "src/main/resources/templates/jaspers/" + templateName;
        JasperReport jreport = JasperCompileManager.compileReport(report);
        JRBeanCollectionDataSource ds = null;
        if (list != null) {
            ds = new JRBeanCollectionDataSource(list);
        }
        if (StringUtils.isBlank(fileName)) {
            String time = DateTimeFormatter.ofPattern("yyyyMMddHHmmss").format(LocalDateTime.now());
            fileName = "导出word(" + time + ")";
        }
        JasperPrint jprint = JasperFillManager.fillReport(jreport, dataMap, ds);
        JRDocxExporter exporter = new JRDocxExporter();
        ExporterInput exporterInput = new SimpleExporterInput(jprint);
        exporter.setExporterInput(exporterInput);

        OutputStreamExporterOutput exporterOutput = new SimpleOutputStreamExporterOutput("d:/temp/" + fileName + ".docx");
        exporter.setExporterOutput(exporterOutput);
        exporter.exportReport();
    }

    /**
     * 导出word到磁盘
     * @param templateName 模板名
     * @param dataMap 数据
     * @param list 列表
     * @param fileName 文件名
     */
    public static <M> void createWord(HttpServletResponse response, String templateName, Map<String, Object> dataMap,
                                      List<M> list, String fileName) throws JRException {
        String report = "src/main/resources/templates/jaspers/" + templateName;
        JasperReport jreport = JasperCompileManager.compileReport(report);
        JRBeanCollectionDataSource ds = null;
        if (list != null) {
            ds = new JRBeanCollectionDataSource(list);
        }
        if (StringUtils.isBlank(fileName)) {
            String time = DateTimeFormatter.ofPattern("yyyyMMddHHmmss").format(LocalDateTime.now());
            fileName = "导出word(" + time + ")";
        }
        JasperPrint jprint = JasperFillManager.fillReport(jreport, dataMap, ds);
        JRDocxExporter exporter = new JRDocxExporter();
        ExporterInput exporterInput = new SimpleExporterInput(jprint);
        exporter.setExporterInput(exporterInput);

        OutputStreamExporterOutput exporterOutput = new SimpleOutputStreamExporterOutput(
                getOutputStream(response, fileName, ".docx")
        );
        exporter.setExporterOutput(exporterOutput);
        exporter.exportReport();
    }

    /**
     * 生成outputstream
     * @param filename 文件名
     * @param response 响应
     * @return OutputStream
     */
    private static OutputStream getOutputStream(HttpServletResponse response, String filename, String fileType) {

        try {
            filename = URLEncoder.encode(filename, "UTF-8");
            String filePath = filename + fileType;
            response.addHeader("Content-Disposition", "attachment;filename=" + filePath);
            response.setHeader("Content-Type", "application/vnd.msword");
            response.setCharacterEncoding("utf-8");
            return response.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("创建文件失败！");
        }
    }
}
