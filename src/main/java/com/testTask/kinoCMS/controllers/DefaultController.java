package com.testTask.kinoCMS.controllers;

import java.util.HashMap;
import java.util.Map;

public abstract class DefaultController {
    protected Object just(String name, String value) {
        Map<String, Object> map = new HashMap<>();
        map.put(name, value);
        return map;
    }
    protected Object just(String name, Long value) {
        return this.just(name, String.valueOf(value));
    }

    private final String problemMessageName = "problemMessage";

    protected Object problem(String value) {
        return this.just(problemMessageName, value);
    }

    protected Object problem(Long value) {
        return this.just(problemMessageName, value);
    }


}
