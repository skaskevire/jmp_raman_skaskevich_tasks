package classloading.training.classloader;

import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import classloading.training.menu.TruckPlantDetailsOrderMenu;

/**
 * Classloader implementation which loads classses from local directory from jar
 * file. For correct working of classloader you must put full path to jar file,
 * for example D:\\***.jar
 * 
 * @author Raman_Skaskevich@epam.com
 * */
public class ClassFromJarByLocalPathLoader extends ClassLoader {
	private static final Logger LOGGER = LogManager
			.getLogger(TruckPlantDetailsOrderMenu.class);
	private static final String PATH_TO_JAR_FILE = "jar:file:%s!/";
	private static final String CLASS_EXT = ".class";
	private static final char DOT_CHAR = '.';
	private static final char SLASH_CHAR = '/';
	private static final String EMPTY_STRING = "";
	private static final String NOT_CLOSED_JAR_MSG = "Jar not closed!";

	private String pathToJar;
	Map<String, Class<?>> cache = new HashMap<String, Class<?>>();

	public ClassFromJarByLocalPathLoader(String pathtojar, ClassLoader parent) {
		super(parent);
		this.pathToJar = pathtojar;
	}

	@Override
	protected Class<?> findClass(String name) {
		Class<?> c = null;
		JarFile jarFile = null;

		try {

			// find jar file by path
			jarFile = new JarFile(pathToJar);

			// get entries from jar file
			Enumeration<JarEntry> e = jarFile.entries();

			URL[] urls = { new URL(String.format(PATH_TO_JAR_FILE, pathToJar)) };

			// getting instance of url classloader by given url
			URLClassLoader cl = URLClassLoader.newInstance(urls);

			// loop by elements in jar file. It will be work until given class
			// will be founded
			while (e.hasMoreElements()) {
				JarEntry je = (JarEntry) e.nextElement();
				if (je.isDirectory() || !je.getName().endsWith(CLASS_EXT)) {
					continue;
				}
				String className = je.getName();

				// removes .class file extension
				className = className.replaceAll(CLASS_EXT, EMPTY_STRING);

				// conversion from native file path to class file path
				className = className.replace(SLASH_CHAR, DOT_CHAR);

				// loading class file with caching
				if (className.endsWith(name)) {
					if (!cache.containsKey(name)) {
						c = cl.loadClass(className);
						cache.put(className, c);
					} else {
						c = cache.get(className);
					}
				}
			}

		} catch (IOException ioex) {
			try {
				return super.findClass(name);
			} catch (ClassNotFoundException e) {
				LOGGER.error(e);
			}
		} catch (ClassNotFoundException cnfe) {
			LOGGER.error(cnfe);
		} finally {
			try {
				if (jarFile != null) {
					jarFile.close();
				} else {
					LOGGER.error(NOT_CLOSED_JAR_MSG);
				}
			} catch (IOException e) {
				LOGGER.error(NOT_CLOSED_JAR_MSG, e);
			}
		}
		return c;
	}
}
