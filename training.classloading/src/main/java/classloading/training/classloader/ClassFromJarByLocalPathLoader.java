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

import classloading.training.menu.impl.TruckPlantDetailsOrderMenu;

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
	private static final char CLASS_DELIM = '.';
	private static final char NATIVE_FILE_PATH_DELIM = '/';
	private static final String EMPTY_STRING = "";
	private static final String NOT_CLOSED_JAR_MSG = "Jar not closed!";
	private ClassLoader parent;

	private String pathToJar;
	Map<String, Class<?>> cache = new HashMap<String, Class<?>>();

	public ClassFromJarByLocalPathLoader(String pathtojar, ClassLoader parent) {
		super(parent);
		this.parent = parent;
		this.pathToJar = pathtojar;
	}
	
	public ClassFromJarByLocalPathLoader(String pathtojar) {
		super(ClassFromJarByLocalPathLoader.class.getClassLoader());
		this.parent = ClassFromJarByLocalPathLoader.class.getClassLoader();
		this.pathToJar = pathtojar;
	}

	@Override
	public synchronized Class<?> loadClass(String name)
			throws ClassNotFoundException {
		// First, check if the class has already been loaded
		Class<?> c = findLoadedClass(name);
		if (c == null) {
			try {
				if (parent != null) {
					c = parent.loadClass(name);
				} else {
					c = ClassLoader.getSystemClassLoader().getParent()
							.loadClass(name);
				}
			} catch (ClassNotFoundException e) {
				// If still not found, then invoke findClass in order
				// to find the class.
				c = findClass(name);
			}
		}
		return c;
	}

	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {

		Class<?> c = findLoadedClass(name);
		if (c == null) {
			JarFile jarFile = null;
			try {
				// find jar file by path
				jarFile = new JarFile(pathToJar);

				// get entries from jar file
				Enumeration<JarEntry> e = jarFile.entries();

				URL[] urls = { new URL(String.format(PATH_TO_JAR_FILE,
						pathToJar)) };

				// getting instance of url classloader by given url
				URLClassLoader cl = URLClassLoader.newInstance(urls);

				// loop by elements in jar file. It will be work until given
				// class
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
					className = className.replace(NATIVE_FILE_PATH_DELIM,
							CLASS_DELIM);

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
				throw new ClassNotFoundException(ioex.getMessage(), ioex);
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
		}
		return c;
	}
}
