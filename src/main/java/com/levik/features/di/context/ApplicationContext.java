package com.levik.features.di.context;

import java.util.Map;

public interface ApplicationContext {

    <T> T getBean(Class<T> type);

    <T> T getBean(Class<T> type, String name);

    <T> Map<String, T> getBeans(Class<T> type);

    <T> Map<String, T> getAllBeans();

    void addBean(String name, Object objOrProxy);
}
