package com.innometrics.integration.app.ml.recommender.utils;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author andrew, Innometrics
 */
public class Singletons {
    private static final Logger logger;
    private static Map<String, Object> objectMap;
    private static PropertiesConfiguration applicationSettings;
    public static String CONFIGURATION_FILE = "settings.properties";

    static {
        logger = Logger.getLogger(Singletons.class.getCanonicalName());
        objectMap = new HashMap<>();
    }

    public synchronized static PropertiesConfiguration getApplicationSettings() {
        if (applicationSettings == null) {
            File location = new File(CONFIGURATION_FILE);
            if (location.exists() && location.canRead()) {
                try {
                    applicationSettings = new PropertiesConfiguration(location);
                } catch (ConfigurationException e) {
                    logger.warning(e.getMessage());
                }
                applicationSettings.setThrowExceptionOnMissing(true);
            } else {
                logger.warning("Can not find configuration file in classpath:" + CONFIGURATION_FILE);
            }
        }
        return applicationSettings;
    }

    public static <T> T getObjectSingletonByConfig(String config, Class<T> targetClass) throws IncompatibleClassChangeError, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        return getObjectSingleton(getApplicationSettings().getString(config), targetClass);
    }

    public static <T> T getObjectSingleton(String className, Class<T> targetClass) throws IncompatibleClassChangeError, ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        Object object = getObjectSingleton(className);
        if (object != null && targetClass.isInstance(object)) {
            return targetClass.cast(object);
        } else if (object == null) {
            return null;
        } else {
            throw new IncompatibleClassChangeError(className + " is not an implementation of " + targetClass.getCanonicalName());
        }
    }


    public synchronized static Object getObjectSingleton(String implementationClassName) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Class<?> classToInit = Class.forName(implementationClassName);
        return getObjectSingleton(classToInit);
    }

    public synchronized static <T> T getObjectSingleton(Class<T> implementationClassName) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        String qualifiedKey = implementationClassName.getCanonicalName();
        if (!objectMap.containsKey(qualifiedKey) || objectMap.get(qualifiedKey) == null) {
            logger.info("Creating object:" + implementationClassName);
            try {
                Constructor<?> constructor;
                constructor = implementationClassName.getConstructor();
                objectMap.put(qualifiedKey, constructor.newInstance());

            } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
                logger.log(Level.WARNING, "Error while initialize Object:" + qualifiedKey, e);
                throw e;
            } catch (NoSuchMethodException ex2) {
                throw new NoSuchMethodException("Can not find default constructor.");
            }
        }
        return implementationClassName.cast(objectMap.get(qualifiedKey));
    }
}
