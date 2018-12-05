package com.snailmann.webflux;

import com.snailmann.webflux.api.UserApi;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import com.snailmann.framework.proxy.*;

@SpringBootApplication
public class ClientWebfluxApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClientWebfluxApplication.class, args);
    }

    @Bean
    ProxyCreator jdkProxyCreator(){
        return new JDKProxyCreator();
    }

    @Bean
    FactoryBean<UserApi> userAPi(ProxyCreator proxyCreator) {
        return new FactoryBean<UserApi>() {

            /**
             * 返回代理对象
             * @return
             */
            @Override
            public UserApi getObject() {
                return (UserApi) proxyCreator.createProxy(this.getObjectType());
            }

            /**
             * 返回类型
             * @return
             */
            @Override
            public Class<?> getObjectType() {
                return UserApi.class;
            }
        };
}
}
