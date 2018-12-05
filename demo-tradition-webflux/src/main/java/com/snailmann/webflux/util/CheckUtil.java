package com.snailmann.webflux.util;

import com.snailmann.webflux.exception.advice.InVaildUserNameExecption;

import java.util.stream.Stream;

public class CheckUtil {
    private static final String[] INVALID_NAMES = {"admin", "guanliyuan"};

    /**
     * 校验名字，不成功就抛出异常
     *
     * @param name
     */
    public static void checkName(String name) {
        Stream.of(INVALID_NAMES)
                .filter(nameStr -> nameStr.equalsIgnoreCase(name))
                .findAny().ifPresent(s -> {
            throw new InVaildUserNameExecption("name", name);
        });
    }

}
