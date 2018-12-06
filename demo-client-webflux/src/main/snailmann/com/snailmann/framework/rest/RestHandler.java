package com.snailmann.framework.rest;

import com.snailmann.framework.bean.MethodInfo;
import com.snailmann.framework.bean.ServerInfo;

/**
 * rest请求调用handler
 */
public interface RestHandler {

    /**
     * 初始化服务器信息
     *
     * @param serverInfo
     */
    void init(ServerInfo serverInfo);

    /**
     * rest调用，返回接口
     *
     * @param methodInfo
     * @return
     */
    Object invokeRest(MethodInfo methodInfo);
}
