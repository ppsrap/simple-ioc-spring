package com.example.spring.ioc.utils;

import com.example.spring.ioc.annonation.AutoWired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.FileInputStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MyIoc {
    Map<String, Object> beansMap = new HashMap<>(); // bean 容器

    private static final Logger LOGGER = LoggerFactory.getLogger(MyIoc.class);
    public MyIoc(String location) throws Exception {
        LOGGER.info("location:{}", location);
        loadBeans(location); // 通过配置文件加载bean
        initAttrs(); // 通过注解自动注入
    }

    /**
     * 注解方式实现依赖自动注入
     * 1. 遍历容器中存储的对象
     * 2. 获取对象的Fields，
     *      1. 检查Fields是否有AutoWire注解
     *      2. 如果有AutoWire注解
     *      3. 通过setAccessible(true) 和 set方法为Field注入对象
     *
     */
    private void initAttrs() throws IllegalAccessException {
        Set<String> strings = beansMap.keySet();
        for (String s : strings){
            // 依赖注入
            attrsAssign(beansMap.get(s));
        }
    }

    /** 注解方式具体实现 **/
    private void attrsAssign(Object o) throws IllegalAccessException {
        Field[] declaredFields = o.getClass().getDeclaredFields();
        for(Field f: declaredFields){
            AutoWired annotation = f.getAnnotation(AutoWired.class);
            if (annotation != null){
                String name = f.getName();
                Object bean = getBean(name);
                if (o != null){
                    f.setAccessible(true);
                    f.set(o, bean);
                }
            }
        }
    }

    /**
     * 通过xml实现bean加载
     *  1. MySpringApplicationContext(String url) 传递配置文件的低脂
     *  2. 解析xml配置文件
     *      1. 解析出 id 和 类名
     *          1. 通过Class.forName(类名) 加载对象
     *          2、 通过.newInstance()实例化
     *      2. 解析properties 用于生成具体对象
     *          1. 解析出name 和 value
     *          2. 通过 bean.getDeclaredField(name) 获取相应的Field
     *          3. 通过 Field.set(value) 为Field注入信息
     *      3. 将实例化的bean存入容器中
     * @param location
     * @throws Exception
     */
    private void loadBeans(String location) throws Exception {
        // 1. 读取xml配置文件
        FileInputStream fileInputStream = new FileInputStream(location);
        // 2. 获取xml文件中的配置信息
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(fileInputStream);
        Element root = doc.getDocumentElement();
        LOGGER.info("Element:{}", root.toString());
        NodeList nodes = root.getChildNodes();
        for (int i = 0; i < nodes.getLength(); i++){
            Node node = nodes.item(i);
            /** xml 配置方式主逻辑**/
            if (node instanceof Element && node.getNodeName().equals("bean")){
                Element ele = (Element) node;
                String id = ele.getAttribute("id");
                String className = ele.getAttribute("class");
                LOGGER.info("id:{}, className:{}", id, className);

                // 加载类
                Class beanClass = null;
                try {
                    beanClass = Class.forName(className);
                } catch (ClassNotFoundException e){
                    e.printStackTrace();
                    return;
                }
                // 实例化
                Object bean = beanClass.newInstance();
                registerBean(id, bean);

                // 获取属性
                NodeList property = ele.getElementsByTagName("property");
                for (int j = 0; j < property.getLength(); j++){
                    Element pro = (Element)property.item(j);
                    String name = pro.getAttribute("name");
                    String value = pro.getAttribute("value");
                    LOGGER.info("name:{}, value:{}", name, value);
                    // 获取相应字段
                    Field field = bean.getClass().getDeclaredField(name);
                    field.setAccessible(true);
                    if (value != null && value.length() > 0){
                        // 将bean对象上的field 设置为value
                        field.set(bean, value);
                    } else {
                        String ref = pro.getAttribute("ref");
                        LOGGER.info("ref:{}", ref);
                        if (ref == null || ref.length() == 0){
                            throw new IllegalArgumentException("ref config error");
                        }
                        field.set(bean,getBean(ref));
                    }
                }
            }
        }
        Set<String> strings = beansMap.keySet();
        LOGGER.info("------------------Beans容器内容-------------------");
        for (String s : strings){
            LOGGER.info("key:{}, value:{}]", s, beansMap.get(s));
        }
        LOGGER.info("-------------------------------------------------");
    }

    private void registerBean(String id, Object bean) {
        beansMap.put(id, bean);
    }

    public Object getBean(String ref) {
        return beansMap.get(ref);
    }
}
