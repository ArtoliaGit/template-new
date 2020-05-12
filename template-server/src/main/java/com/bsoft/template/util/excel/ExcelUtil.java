package com.bsoft.template.util.excel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.AbstractCellStyleStrategy;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * excel工具类
 *
 * @author Artolia Pendragon
 * @version 1.0.0
 */
@Slf4j
public class ExcelUtil {

    /**
     * 读取excel文件
     * @param excel 文件
     * @return list
     */
    public static List<Object> readExcel(MultipartFile excel) {
        return readExcel(excel, null, 1, 1);
    }

    /**
     * 读取excel文件
     * @param excel 文件
     * @param rowModel 实体类映射
     * @return list
     */
    public static List<Object> readExcel(MultipartFile excel, Class<?> rowModel) {
        return readExcel(excel, rowModel, 1, 1);
    }

    /**
     * 读取第一个sheet的数据
     * @param excel 文件
     * @param rowModel 实体类映射
     * @param sheetNo sheet的序号，从0开始
     * @return list
     */
    public static List<Object> readExcel(MultipartFile excel, Class<?> rowModel, Integer sheetNo) {
        return readExcel(excel, rowModel, sheetNo, 1);
    }

    /**
     * 读取指定sheet的数据
     * @param excel 文件
     * @param rowModel 实体类映射
     * @param sheetNo sheet的序号，从0开始
     * @param headLineNum 表头行数，默认为1
     * @return 数据list
     */
    public static <M> List<M> readExcel(MultipartFile excel, Class<?> rowModel, Integer sheetNo, Integer headLineNum) {
        ExcelListener<M> listener = new ExcelListener<>();
        ExcelReader reader = getReader(excel, listener, rowModel);
        if (reader == null) {
            return null;
        }

        if (sheetNo == null) {
            sheetNo = 0;
        }
        if (headLineNum == null) {
            headLineNum = 1;
        }
        ReadSheet sheet = EasyExcel.readSheet(sheetNo).headRowNumber(headLineNum).build();
        reader.read(sheet);
        reader.finish();
        return listener.getList();
    }

    /**
     * 读取指定sheet的数据
     * @param fileName 文件名
     * @param rowModel 实体类映射
     * @param sheetNo sheet的序号，从0开始
     * @param headLineNum 表头行数，默认为1
     * @return 数据list
     */
    public static <M> List<M> readExcel(String fileName, Class<?> rowModel, Integer sheetNo, Integer headLineNum) {
        ExcelListener<M> listener = new ExcelListener<>();
        ExcelReader reader;
        if (rowModel != null) {
            reader = EasyExcel.read(fileName, rowModel, listener).build();
        } else {
            reader = EasyExcel.read(fileName, listener).build();
        }
        if (reader == null) {
            return null;
        }

        if (sheetNo == null) {
            sheetNo = 0;
        }
        if (headLineNum == null) {
            headLineNum = 1;
        }
        ReadSheet sheet = EasyExcel.readSheet(sheetNo).headRowNumber(headLineNum).build();
        reader.read(sheet);
        reader.finish();
        return listener.getList();
    }

    /**
     * 导出excel：多个sheet
     * @param response HttpServletResponse
     * @param filename 导出的文件名
     * @param fileType 导出文件格式
     * @return ExcelWriter
     */
    public static ExcelWriter writeExcel(HttpServletResponse response, String filename, ExcelTypeEnum fileType) {

        return writeExcel(response, filename, fileType, null);
    }

    /**
     * 导出excel：多个sheet
     * @param response HttpServletResponse
     * @param filename 导出的文件名
     * @param fileType 导出文件格式
     * @param writeHandler 样式
     * @return ExcelWriter
     */
    public static ExcelWriter writeExcel(HttpServletResponse response, String filename,
                                         ExcelTypeEnum fileType, AbstractCellStyleStrategy writeHandler) {
        if (StringUtils.isBlank(filename)) {
            String time = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());
            filename = "导出数据(" + time + ")";
        }
        if (fileType == null) {
            fileType = ExcelTypeEnum.XLSX;
        }
        String suffix = "." + fileType.toString().toLowerCase();
        ExcelWriter writer;
        if (writeHandler != null) {
            writer = EasyExcel.write(getOutputStream(response, filename, suffix))
                    .registerWriteHandler(writeHandler)
                    .excelType(fileType).build();
        } else {
            writer = EasyExcel.write(getOutputStream(response, filename, suffix))
                    .registerWriteHandler(getDefaultStyle())
                    .excelType(fileType).build();
        }
        return writer;
    }

    /**
     * 根据模板导出excel
     * @param response HttpServletResponse
     * @param template excel模板
     * @param filename 文件名
     * @param fileType 文件类型
     * @return ExcelWriter
     */
    public static ExcelWriter writeExcel(HttpServletResponse response, String template, String filename,
                                         ExcelTypeEnum fileType) throws IOException {
        ClassPathResource classPathResource = new ClassPathResource("templates/" + template);
        InputStream inputStream = classPathResource.getInputStream();

        if (StringUtils.isBlank(filename)) {
            String time = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());
            filename = "导出数据(" + time + ")";
        }
        if (fileType == null) {
            fileType = ExcelTypeEnum.XLSX;
        }
        String suffix = "." + fileType.toString().toLowerCase();
        ExcelWriter writer = EasyExcel.write(getOutputStream(response, filename, suffix))
                .withTemplate(inputStream)
                .excelType(fileType).build();
        inputStream.close();
        return writer;
    }

    /**
     * 导出excel到磁盘
     * @param filename 导出的文件名
     * @param fileType 导出文件格式
     * @return ExcelWriter
     */
    public static ExcelWriter writeExcel(String filename, ExcelTypeEnum fileType) {
        return writeExcel(filename, fileType, null);
    }

    /**
     * 导出excel到磁盘
     * @param filename 导出的文件名
     * @param fileType 导出文件格式
     * @return ExcelWriter
     */
    public static ExcelWriter writeExcel(String filename, ExcelTypeEnum fileType, AbstractCellStyleStrategy writeHandler) {
        if (StringUtils.isBlank(filename)) {
            String time = DateTimeFormatter.ofPattern("yyyyMMddHHmmss").format(LocalDateTime.now());
            filename = "导出数据(" + time + ")";
        }
        if (fileType == null) {
            fileType = ExcelTypeEnum.XLSX;
        }

        String suffix = "." + fileType.toString().toLowerCase();
        String filePath = "D:/temp/" + filename + suffix;
        File file = new File(filePath);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }

        ExcelWriter writer;
        if (writeHandler != null) {
            writer = EasyExcel.write(file).registerWriteHandler(writeHandler).excelType(fileType).build();
        } else {
            writer = EasyExcel.write(file).registerWriteHandler(getDefaultStyle()).excelType(fileType).build();
        }
        return writer;
    }

    /**
     * 获取ExcelReader
     * @param excel 文件
     * @param listener 监听器
     * @return ExcelReader
     */
    private static <M> ExcelReader getReader(MultipartFile excel, ExcelListener<M> listener, Class<?> rowModel) {
        String filename = excel.getOriginalFilename();
        if (filename == null || !StringUtils.endsWithAny(filename.toLowerCase(), ".xls", ".xlsx")) {
            throw new RuntimeException("文件类型错误！");
        }

        try {
            if (rowModel != null) {
                return EasyExcel.read(excel.getInputStream(), rowModel, listener).build();
            }
            return EasyExcel.read(excel.getInputStream(), listener).build();
        } catch (IOException e) {
            log.error("文件读取失败");
            e.printStackTrace();
        }
        return null;
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
            response.setHeader("Content-Type", "application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            return response.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("创建文件失败！");
        }
    }

    /**
     * 获取默认样式
     * @return HorizontalCellStyleStrategy
     */
    public static HorizontalCellStyleStrategy getDefaultStyle() {
        // 头的策略
        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        // 背景设置为灰色
        headWriteCellStyle.setFillForegroundColor(IndexedColors.GREY_40_PERCENT.getIndex());
        WriteFont headWriteFont = new WriteFont();
        headWriteFont.setFontHeightInPoints((short)14);
        headWriteCellStyle.setWriteFont(headWriteFont);
        // 内容的策略
        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
        // 这里需要指定 FillPatternType 为FillPatternType.SOLID_FOREGROUND 不然无法显示背景颜色.头默认了 FillPatternType所以可以不指定
        contentWriteCellStyle.setFillPatternType(FillPatternType.SOLID_FOREGROUND);
        // 背景白色
        contentWriteCellStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());
        WriteFont contentWriteFont = new WriteFont();
        // 字体大小
        contentWriteFont.setFontHeightInPoints((short)14);
        contentWriteCellStyle.setWriteFont(contentWriteFont);
        contentWriteCellStyle.setBorderBottom(BorderStyle.THIN);
        contentWriteCellStyle.setBorderTop(BorderStyle.THIN);
        contentWriteCellStyle.setBorderLeft(BorderStyle.THIN);
        contentWriteCellStyle.setBorderRight(BorderStyle.THIN);
        // 这个策略是 头是头的样式 内容是内容的样式 其他的策略可以自己实现

        return new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);
    }
}
