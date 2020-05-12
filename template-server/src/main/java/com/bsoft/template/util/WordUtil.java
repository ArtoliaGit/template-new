package com.bsoft.template.util;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * 导出word
 *
 * @author Artolia Pendragon
 * @version 1.0.0
 */
public class WordUtil {

    /**
     * 将word写到磁盘
     * @param dataMap 模板数据
     * @param templateName 模板名
     * @param fileName 文件名
     */
    public static void createWord(Map<String, Object> dataMap, String templateName, String fileName) {
        try {
            Configuration configuration = new Configuration(Configuration.VERSION_2_3_28);
            configuration.setDefaultEncoding("UTF-8");
            configuration.setClassForTemplateLoading(WordUtil.class, "/templates/");
            Template template = configuration.getTemplate(templateName);
            File outFile = new File("D:/temp" + File.separator + fileName);
            if (!outFile.getParentFile().exists()) {
                boolean mkdirs = outFile.getParentFile().mkdirs();
                if (!mkdirs) {
                    return;
                }
            }
            Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile), StandardCharsets.UTF_8));
            template.process(dataMap, out);
            out.flush();
            out.close();
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将word写到响应流
     * @param response HttpServletResponse
     * @param dataMap 模板数据
     * @param templateName 模板名
     * @param fileName 文件名
     */
    public static void createWord(HttpServletResponse response, Map<String, Object> dataMap, String templateName, String fileName) {
        try {
            Configuration configuration = new Configuration(Configuration.VERSION_2_3_30);
            configuration.setDefaultEncoding("UTF-8");
            configuration.setClassForTemplateLoading(WordUtil.class, "/templates/");
            Template template = configuration.getTemplate(templateName);
            fileName = new String(fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            response.setHeader("Content-Type", "application/msword");

            OutputStream outputStream = response.getOutputStream();
            Writer out = new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8));
            template.process(dataMap, out);
            out.flush();
            out.close();
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }
    }
}
