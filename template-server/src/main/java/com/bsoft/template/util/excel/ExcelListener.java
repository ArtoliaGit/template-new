package com.bsoft.template.util.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.exception.ExcelDataConvertException;
import com.bsoft.template.util.GsonUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * excel监听器
 *
 * @author Artolia Pendragon
 * @version 1.0.0
 */
@Slf4j
public class ExcelListener<T> extends AnalysisEventListener<T> {

    private List<T> list = new ArrayList<>();

    @Override
    public void invoke(T data, AnalysisContext context) {
        log.info("解析到一条数据: {}", GsonUtil.objectToJson(data));
        list.add(data);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        //datas.clear();
        log.info("所有数据解析完成");
    }

    @Override
    public void onException(Exception exception, AnalysisContext context) {
        log.error("解析失败，继续读取下一行：{}", exception.getMessage());
        if (exception instanceof ExcelDataConvertException) {
            ExcelDataConvertException excelDataConvertException = (ExcelDataConvertException)exception;
            log.error("第{}行，第{}列解析异常", excelDataConvertException.getRowIndex(),
                    excelDataConvertException.getColumnIndex());
        }
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
