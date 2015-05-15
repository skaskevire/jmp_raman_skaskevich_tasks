package classloading.training.menu;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import classloading.training.core.Core;
import classloading.training.truck.factory.entity.Part;
import classloading.training.truck.factory.entity.Plant;

/**
 * Main class of application
 * 
 * @author Raman_Skaskevich@epam.com
 * */
public class Main {
	private static final Logger LOGGER = LogManager.getLogger(Main.class);
	public static final String PACKAGE_SIZE_MSG = "Package size: ";
	public static final String ENGINE_PLANT_MENU_MSG = "Get package of produced parts from EnginePlant";
	public static final String LIGHTING_PLANT_MENU_MSG = "Get package of produced parts from LightingPlant";
	public static final String ENGINE_PLANT_CLASS_NAME = "EnginePlant";
	public static final String LIGHTING_PLANT_CLASS_NAME = "LightingPlant";
	public static final String PATH_TO_LIGHTING_PLANT_JAR = "D:\\LightingPlant.jar";
	public static final String PATH_TO_ENGINE_PLANT_JAR = "D:\\EnginePlant.jar";

	public static void main(String args[]) {
		Menu menu = buildMenu();
		menu.run();
	}

	public static Menu buildMenu() {
		
		final Core core = new Core();

		Menu menu = new Menu();
		appendMenuLine(menu, core, ENGINE_PLANT_MENU_MSG,
				ENGINE_PLANT_CLASS_NAME, PATH_TO_ENGINE_PLANT_JAR);
		appendMenuLine(menu, core, LIGHTING_PLANT_MENU_MSG,
				LIGHTING_PLANT_CLASS_NAME, PATH_TO_LIGHTING_PLANT_JAR);
		return menu;
	}

	/**
	 * Appends menu line
	 * */
	private static void appendMenuLine(Menu menu, final Core core,
			String message, final String classToLoad, final String pathToClass) {
		menu.addEntry(new MenuEntry(message) {
			@Override
			public void run() {
				
				//load class using classloader
				Class<?> clazz = core.loadClass(classToLoad, pathToClass);
				try {
					
					//create new instance of loaded class using default constructor
					Plant plant = (Plant) clazz.newInstance();
					for (Part part : plant.getProducedParts()) {
						
						//log info about part
						LOGGER.info(part.toString());
					}
					
					//log info about size of parts which plant sent
					LOGGER.info(PACKAGE_SIZE_MSG
							+ plant.getProducedParts().size());

				} catch (InstantiationException | IllegalAccessException e) {
					LOGGER.error(e);
				}

			}
		});
	}
}
