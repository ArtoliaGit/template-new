package com.bsoft.template.util.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * excel监听器
 *
 * @author Artolia Pendragon
 * @version 1.0.0
 */
public class ExcelListener<T> extends AnalysisEventListener<T> {

    private List<T> datas = new ArrayList<>();

    @Override
    public void invoke(T object, AnalysisContext analysisContext) {
        datas.add(object);

    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        //datas.clear();
    }

    public List<T> getDatas() {
        return datas;
    }

    public void setDatas(List<T> datas) {
        this.datas = datas;
    }
}
