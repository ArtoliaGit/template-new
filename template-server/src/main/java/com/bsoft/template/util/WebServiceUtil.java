package com.bsoft.template.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;

import java.util.Map;

/**
 * webservice工具类
 */
@Slf4j
public class WebServiceUtil {

    /**
     * 调用webservice接口
     * @param url 地址
     * @param method 方法
     * @param m 数据实体
     * @param cls 类
     */
    public static <M> Map<String, Object> send(String url, String method, M m, Class<M> cls) {
        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
        Client client = dcf.createClient(url);
        HTTPConduit conduit = (HTTPConduit) client.getConduit();
        HTTPClientPolicy policy = new HTTPClientPolicy();
        policy.setConnectionTimeout(2000);
        policy.setReceiveTimeout(2000);
        policy.setAllowChunking(false);
        conduit.setClient(policy);

        Object[] objects;
        try {
            String xml = JaxbObjectAndXmlUtil.object2Xml(cls, m);
            log.info("发送xml: {}", xml);
            objects = client.invoke(method, xml);
            log.info("返回数据: {}", objects[0]);
            String resultXml = String.valueOf(objects[0]);
            resultXml = resultXml.substring(resultXml.indexOf("<root>"));
            log.info("返回信息主体: {}", resultXml);
            return XmlUtil.xmlToMap(resultXml);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
