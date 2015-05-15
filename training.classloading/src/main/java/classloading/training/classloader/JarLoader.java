package classloading.training.classloader;

import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import classloading.training.menu.Menu;

/**
 * Classloader implementation
 * 
 * @author Raman_Skaskevich@epam.com
 * */
public class JarLoader extends ClassLoader {
	private static final Logger LOGGER = LogManager.getLogger(Menu.class);
	private String pathtojar;

	public JarLoader(String pathtojar, ClassLoader parent) {
		super(parent);
		this.pathtojar = pathtojar;
	}

	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		Class<?> c = null;
		JarFile jarFile = null;

		try {
			//find jar file by path
			jarFile = new JarFile(pathtojar);
			Enumeration<JarEntry> e = jarFile.entries();
			URL[] urls = { new URL("jar:file:" + pathtojar + "!/") };
			URLClassLoader cl = URLClassLoader.newInstance(urls);

			while (e.hasMoreElements()) {
				JarEntry je = (JarEntry) e.nextElement();
				if (je.isDirectory() || !je.getName().endsWith(".class")) {
					continue;
				}
				// -6 because of .class
				String className = je.getName().substring(0,
						je.getName().length() - 6);
				className = className.replace('/', '.');
				if (className.endsWith(name)) {
					c = cl.loadClass(className);
				}
			}

		} catch (IOException ioex) {
			return super.findClass(name);
		} finally {
			try {
				if (jarFile != null) {
					jarFile.close();
				} else {
					LOGGER.error("Jar not closed!");
				}
			} catch (IOException e) {
				LOGGER.error("Jar not closed!", e);
			}
		}
		return c;
	}
}
