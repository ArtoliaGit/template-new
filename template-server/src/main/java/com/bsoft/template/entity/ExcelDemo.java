package com.bsoft.template.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.format.NumberFormat;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.Data;

import java.util.Date;

/**
 * @author Artolia Pendragon
 * @version 1.0.0
 */
@Data
public class ExcelDemo {

    @ExcelProperty(value = "姓名", index = 0)
    private String name;

    @ExcelProperty(value = "年龄", index = 1)
    private String age;

    @ColumnWidth(40)
    @ExcelProperty(value = "邮箱", index = 2)
    private String email;

    @ColumnWidth(20)
    @ExcelProperty(value = "地址", index = 3)
    private String address;

    @NumberFormat("#.##%")
    @ColumnWidth(10)
    @ExcelProperty(value = "数字", index = 4)
    private Double number;

    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    @ColumnWidth(40)
    @ExcelProperty(value = "创建时间",index = 5)
    private Date date;
}
