package com.chen.spring;

import com.chen.spring.anno.ChenResource;
import com.chen.spring.anno.ChenService;
import com.chen.utils.ClassUtil;
import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ChenClassPathXmlApplicationContext {

    //扫包范围
    private String packName;

    private static ConcurrentHashMap<String,Object> beans = null;

    public ChenClassPathXmlApplicationContext(String packName) throws Exception {
        beans = new ConcurrentHashMap<String, Object>();
        this.packName = packName;
        initBeans();
        initFileds();
    }

    //初始化对象
    public void initBeans() throws Exception {

        // 1.使用java的反射机制扫包,获取当前包下所有的类
        List<Class<?>> classes = ClassUtil.getClasses(packName);
        // 2.判断类上是否存在注入bean的注解
        ConcurrentHashMap<String, Object> classExisAnnotation = findClassAnno(classes);
        if (classExisAnnotation == null || classExisAnnotation.isEmpty()) {
            throw new Exception("该包下没有任何类加上注解");
        }
        //3.使用java反射机制进行对象初始化
    }
    private void initFileds() throws Exception {

        //1.遍历容器中的所有对象
        for(Map.Entry<String,Object> entry:beans.entrySet()){

            Object obj = entry.getValue();
            assign(obj);

        }
    }

    public void assign(Object object) throws Exception {

        // 1.使用反射机制,获取当前类的所有属性
        Class<? extends Object> classInfo = object.getClass();
        Field[] declaredFields = classInfo.getDeclaredFields();

        // 2.判断当前类属性是否存在注解
        for (Field field : declaredFields) {
            ChenResource extResource = field.getAnnotation(ChenResource.class);
            if (extResource != null) {
                // 获取属性名称
                String beanId = field.getName();
                Object bean = getBean(beanId);
                if (bean != null) {
                    // 3.默认使用属性名称，查找bean容器对象 1参数 当前对象 2参数给属性赋值
                    field.setAccessible(true); // 允许访问私有属性
                    field.set(object, bean);
                }

            }
        }
    }


    public Object getBean(String beanId) throws Exception {
        if(StringUtils.isEmpty(beanId)){
            throw new Exception("beanid不能为空");
        }
        // 1.从spring容器获取bean
        Object object = beans.get(beanId);
        // attriAssign(object);
        return object;
    }

    // 初始化对象
    public Object newInstance(Class<?> classInfo)
            throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        return classInfo.newInstance();
    }

    //判断是否有ChenService注解
    public ConcurrentHashMap<String,Object> findClassAnno(List<Class<?>> classes) throws InstantiationException, IllegalAccessException, ClassNotFoundException {

        for(Class<?> classInfo:classes){

            ChenService annotation = classInfo.getAnnotation(ChenService.class);

            if(annotation != null){
                String className = classInfo.getSimpleName();
                String beanId = toLowerCaseFirstOne(className);
                Object newInstance = newInstance(classInfo);
                beans.put(beanId, newInstance);
            }
        }
        return beans;
    }

    // 依赖注入注解原理
    public void attriAssign(Object object) throws Exception {

        // 1.使用反射机制,获取当前类的所有属性
        Class<? extends Object> classInfo = object.getClass();
        Field[] declaredFields = classInfo.getDeclaredFields();

        // 2.判断当前类属性是否存在注解
        for (Field field : declaredFields) {
            ChenResource extResource = field.getAnnotation(ChenResource.class);
            if (extResource != null) {
                // 获取属性名称
                String beanId = field.getName();
                Object bean = getBean(beanId);
                if (bean != null) {
                    // 3.默认使用属性名称，查找bean容器对象 1参数 当前对象 2参数给属性赋值
                    field.setAccessible(true); // 允许访问私有属性
                    field.set(object, bean);
                }

            }
        }

    }





    // 首字母转小写
    public static String toLowerCaseFirstOne(String s) {
        if (Character.isLowerCase(s.charAt(0)))
            return s;
        else
            return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
    }
}
