package com.zsx.md.utils;

import com.alibaba.fastjson.JSONObject;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.zsx.md.config.AllowURL;
import com.zsx.md.config.PropertiesConfig;
import com.zsx.md.config.SpringUtil;
import org.apache.commons.lang3.StringUtils;

import javax.ws.rs.core.MediaType;

public class HttpUtil {

    public static JSONObject getUserInfo(String userId) {

        PropertiesConfig propertiesConfig = SpringUtil.getBean(PropertiesConfig.class);
        String ssoApi = propertiesConfig.getSsoApi();

        Client client = Client.create();

        WebResource resource = client.resource(ssoApi + userId);

        ClientResponse response = resource.type(MediaType.APPLICATION_JSON_TYPE)
                .get(ClientResponse.class);

        String responseEntity = response.getEntity(String.class);

        System.out.println(responseEntity);

        if (StringUtils.isNotBlank(responseEntity)) {
            return JSONObject.parseObject(responseEntity);
        }
        return null;
    }


}
