package com.utils;

import javax.servlet.http.HttpServletRequest;

public class UrlTool {
    /**
     * 获取服务器地址
     * @param request
     * @return
     */
    public static String getServerAddress(HttpServletRequest request) {
        StringBuilder url = new StringBuilder();
        url.append(request.getScheme());
        url.append("://");
        url.append(request.getServerName());
        url.append(":");
        url.append(request.getServerPort());
        url.append("/");

        return url.toString();
    }
}
