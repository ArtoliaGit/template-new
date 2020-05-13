package com.bsoft.template.web;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.bsoft.template.common.Result;
import com.bsoft.template.entity.Car;
import com.bsoft.template.entity.ExcelDemo;
import com.bsoft.template.util.GsonUtil;
import com.bsoft.template.util.HttpUtil;
import com.bsoft.template.util.RequestParamPaser;
import com.bsoft.template.util.WordUtil;
import com.bsoft.template.util.excel.ExcelUtil;
import com.bsoft.template.util.jasper.JasperUtil;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.export.ExporterInput;
import net.sf.jasperreports.export.OutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Artolia Pendragon
 * @version 1.0.0
 */
@Slf4j
@RestController
@RequestMapping("file")
public class FileController {

    @Autowired
    private HttpUtil httpUtil;

    @PostMapping
    public Result upload(MultipartFile file) {
        System.out.println(file.getName());
        System.out.println(file.getOriginalFilename());
        System.out.println(file.getSize());

        List<ExcelDemo> data = ExcelUtil.readExcel(file, ExcelDemo.class, 0, 1);
        System.out.println(data);

        return new Result().ok().data(data);
    }

    @GetMapping("/excel")
    public void downloadExcel(HttpServletResponse response) {
        List<ExcelDemo> list = getList();
        String fileName = "一个 Excel 文件";
        ExcelWriter writer = ExcelUtil.writeExcel(response, fileName, ExcelTypeEnum.XLS);
        WriteSheet sheet1 = new WriteSheet();
        sheet1.setSheetNo(1);
        sheet1.setSheetName("测试");
        sheet1.setClazz(ExcelDemo.class);
        writer.write(list, sheet1);
        sheet1.setSheetNo(2);
        sheet1.setSheetName("测试2");
        writer.write(list, sheet1);
        writer.finish();
    }

    @GetMapping("/word")
    public void downloadWord(HttpServletResponse response) {
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("username", "张三");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        dataMap.put("currDate", sdf.format(new Date()));

        dataMap.put("content", "这是正文");

        try {
            FileInputStream fis = new FileInputStream(new File("d:/picture/Devils Bridge 桥梁和绿色树木 河流 倒映6k自然风景壁纸_彼岸图网.jpg"));
            byte[] data = new byte[fis.available()];
            fis.read(data);
            fis.close();

            dataMap.put("image", Base64.getEncoder().encodeToString(data));
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Map<String, Object>> newList = new ArrayList<>();
        for (int i = 1; i <= 10; i ++) {
            Map<String, Object> map = new HashMap<>();
            map.put("title", "标题" + i);
            map.put("content", "内容" + i);
            map.put("author", "作者" + i);
            newList.add(map);
        }
        dataMap.put("newList", newList);

        Random r = new Random();
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd_HHmmss_SSS");
        StringBuilder sb = new StringBuilder();
        sb.append(sdf1.format(new Date()));
        sb.append("_");
        sb.append(r.nextInt(100));

        String filePath = "./upload";

        String fileOnlyName = "导出word" + sb + ".doc";
//		String fileName = "导出word.doc";

        WordUtil.createWord(response, dataMap, "freemarker模板.ftl", fileOnlyName);
    }

    @GetMapping("/pdf")
    public void getJasReport(HttpServletResponse response) throws JRException, IOException {
        String report = "src/main/resources/templates/jaspers/report2.jrxml";

        JasperReport jreport = JasperCompileManager.compileReport(report);

        List<Car> cars = new ArrayList<>();
        Car car = new Car();
        car.setId(1L);
        car.setName("zhangsan");
        car.setPrice(10);
        cars.add(car);
        cars.add(car);

        JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(cars);

        Map<String, Object> params = new HashMap<>();
        params.put("UserName", "战三");
        JasperPrint jprint = JasperFillManager.fillReport(jreport, params, ds);

        JasperExportManager.exportReportToPdfFile(jprint,
                "d:/temp/report2.pdf");

        JRDocxExporter exporter = new JRDocxExporter();
        ExporterInput exporterInput = new SimpleExporterInput(jprint);
        exporter.setExporterInput(exporterInput);

        OutputStreamExporterOutput exporterOutput = new SimpleOutputStreamExporterOutput("d:/temp/report2.docx");
        exporter.setExporterOutput(exporterOutput);
        exporter.exportReport();

        OutputStream outputStream = response.getOutputStream();
        JasperExportManager.exportReportToHtmlFile(jprint, "d:/temp/report2.html");
    }

    @GetMapping("/jword")
    public void getJasReportWord(HttpServletResponse response) throws JRException, IOException {
        String report = "report2.jrxml";

        List<Car> cars = new ArrayList<>();
        Car car = new Car();
        car.setId(1L);
        car.setName("zhangsan");
        car.setPrice(10);
        cars.add(car);
        cars.add(car);

        Map<String, Object> params = new HashMap<>();
        params.put("UserName", "战三");

        JasperUtil.createWord(response, report, params, cars, null);
    }

    @GetMapping("/http")
    public void httptest() {
        Map<String, String> map = new HashMap<>();
        map.put("a", "1");
        httpUtil.get("http://localhost:9090/file/gettest", Result.class, map);
        ExcelDemo model1 = new ExcelDemo();
        model1.setName("howie");
        model1.setAge("19");
        model1.setAddress("123456789");
        model1.setEmail("123456789@gmail.com");
        model1.setNumber(0.1245);
        model1.setDate(new Date());
        httpUtil.post("http://localhost:9090/file/posttest", Result.class, model1);
    }

    @GetMapping("/gettest")
    public Result gettest(HttpServletRequest request) {
        log.info(GsonUtil.objectToJson(RequestParamPaser.getParameters(request)));
        return new Result().ok();
    }

    @PostMapping("/posttest")
    public Result posttest(@RequestBody ExcelDemo demo) {
        log.info(GsonUtil.objectToJson(demo));
        return new Result().ok();
    }

    public List<ExcelDemo> getList() {
        List<ExcelDemo> list = new ArrayList<>();
        ExcelDemo model1 = new ExcelDemo();
        model1.setName("howie");
        model1.setAge("19");
        model1.setAddress("123456789");
        model1.setEmail("123456789@gmail.com");
        model1.setNumber(0.1245);
        model1.setDate(new Date());
        list.add(model1);
        ExcelDemo model2 = new ExcelDemo();
        model2.setName("harry");
        model2.setAge("20");
        model2.setAddress("198752233");
        model2.setEmail("198752233@gmail.com");
        model2.setNumber(0.6789123);
        model2.setDate(new Date());
        list.add(model2);
        return list;
    }

}
