package com.bsoft.template.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Slf4j
@Component
public class HttpUtil {

    @Autowired
    private RestTemplate restTemplate;

    /**
     * post请求
     * @param url 地址
     * @param map 数据
     * @param cls 返回类型
     */
    public <M, T> M post(String url, Class<M> cls, T map) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        if (map != null) {
            log.info("入参：{}", GsonUtil.objectToJson(map));
        }
        HttpEntity<T> request = new HttpEntity<>(map, headers);

        ResponseEntity<M> response = restTemplate.exchange(url, HttpMethod.POST, request, cls);
        HttpStatus statusCode = response.getStatusCode();
        log.info("出参：{}", GsonUtil.objectToJson(response.getBody()));
        if (statusCode.is2xxSuccessful()) {
            return response.getBody();
        }
        log.error("请求失败");
        return null;
    }

    /**
     * get请求
     * @param url 地址
     * @param params 数据
     * @param cls 返回类型
     */
    public <M> M get(String url, Class<M> cls, Map<String, String> params) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(headers);

        StringBuilder sb = new StringBuilder();
        if (params != null) {
            for (Map.Entry<String, String> item : params.entrySet()) {
                sb.append(item.getKey()).append("=").append(item.getValue()).append("&");
            }
        }

        String paramsStr = sb.toString();
        if (paramsStr.length() > 0) {
            paramsStr = paramsStr.substring(0, paramsStr.length() - 1);
            url += "?" + paramsStr;
            log.info("入参：{}", paramsStr);
        }

        ResponseEntity<M> response = restTemplate.exchange(url, HttpMethod.GET, request, cls);
        HttpStatus statusCode = response.getStatusCode();
        log.info("出参：{}", GsonUtil.objectToJson(response.getBody()));
        if (statusCode.is2xxSuccessful()) {
            return response.getBody();
        }
        log.error("请求失败");
        return null;
    }
}
