package classloading.training.menu.entry.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import classloading.training.classloader.ClassFromJarByLocalPathLoader;
import classloading.training.menu.entry.MenuEntryEnum;
import classloading.training.truck.factory.entity.Part;
import classloading.training.truck.factory.entity.Plant;

/**
 * Implementation of {@MenuEntry}
 *
 * @author Raman_Skaskevich@epam.com
 * */
public class AppendLightingMenuLineEntry extends MenuEntry {
	public static final String PACKAGE_SIZE_MSG = "Package size: ";
	private static final Logger LOGGER = LogManager
			.getLogger(AppendLightingMenuLineEntry.class);

	public AppendLightingMenuLineEntry(String filePath) {
		super(filePath);
		setTitle(MenuEntryEnum.APPEND_LINE_LIGHTING.getMessage());
	}

	@Override
	public void performMenuAction() {
		try {

			ClassFromJarByLocalPathLoader jarLoader = new ClassFromJarByLocalPathLoader(
					filePath, ClassLoader.getSystemClassLoader());

			// load class using classloader
			Class<?> clazz = jarLoader
					.loadClass(MenuEntryEnum.APPEND_LINE_LIGHTING
							.getClassName());

			// create new instance of loaded class using default
			// constructor
			Plant plant = (Plant) clazz.newInstance();
			for (Part part : plant.getProducedParts()) {

				// log info about part
				LOGGER.info(part.toString());
			}

			// log info about size of parts which plant sent
			LOGGER.info(PACKAGE_SIZE_MSG + plant.getProducedParts().size());

		} catch (InstantiationException | IllegalAccessException e) {
			LOGGER.error(e);
		} catch (ClassNotFoundException e) {
			LOGGER.error(e);
		}

	}
}
