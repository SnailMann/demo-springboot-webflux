package com.snailmann.framework.proxy;

/**
 * 创建代理类接口
 * 为什么需要创建代理类接口，这是因为我们这里使用的实现类是JDK动态代理实现，有可能以后你会扩展到cglib代理
 * 所以目前我们要面向接口编程，如果出现扩展，我们也不需要大量修改代理。只需要修改传入的实现类即可
 */
public interface ProxyCreator {

    /**
     * 创建代理类
     *
     * @param type
     * @return
     */
    Object createProxy(Class<?> type);
}
