package classloading.training.core;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import classloading.training.classloader.JarLoader;

/**
 * Core of application. Uses classloader for loading target .class file from
 * .jar file
 * 
 * @author Raman_Skaskevich@epam.com
 * */
public class Core {
	private static final Logger LOGGER = LogManager.getLogger(Core.class);

	/**
	 * Cache for storing loaded classes to interrupt extra envocation of
	 * classloader
	 * */
	Map<String, Class<?>> cache = new HashMap<String, Class<?>>();

	public Class<?> loadClass(String className, String pathToJar) {

		//use classloader to load class from jar file
		JarLoader jarLoader = new JarLoader(pathToJar,
				ClassLoader.getSystemClassLoader());
		Class<?> clazz = null;
		
		//implementation of logic entries in the cache
		try {
			if (!cache.containsKey(className)) {
				clazz = jarLoader.loadClass(className);
				cache.put(className, clazz);
			} else {
				clazz = cache.get(className);
			}
		} catch (ClassNotFoundException e) {
			LOGGER.error(e);
		}
		
		return clazz;
	}
}