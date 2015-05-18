package classloading.training.menu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import classloading.training.classloader.ClassFromJarByLocalPathLoader;
import classloading.training.truck.factory.entity.Part;
import classloading.training.truck.factory.entity.Plant;

/**
 * Menu for ordering details for truck plant
 *
 * @author Raman_Skaskevich@epam.com
 * */
public class TruckPlantDetailsOrderMenu {

	private static final Logger LOGGER = LogManager.getLogger(TruckPlantDetailsOrderMenu.class);
	private static final String MENU_HEADER = "Menu:";
	private static final String EXIT_HEADER = "Exit:";
	public static final String PACKAGE_SIZE_MSG = "Package size: ";
	public static final String ENGINE_PLANT_MENU_MSG = "Get package of produced parts from EnginePlant";
	public static final String LIGHTING_PLANT_MENU_MSG = "Get package of produced parts from LightingPlant";
	public static final String ENGINE_PLANT_CLASS_NAME = "EnginePlant";
	public static final String LIGHTING_PLANT_CLASS_NAME = "LightingPlant";
	public static final String PATH_TO_LIGHTING_PLANT_JAR = "D:\\LightingPlant.jar";
	public static final String PATH_TO_ENGINE_PLANT_JAR = "D:\\EnginePlant.jar";
	private List<MenuEntry> entries = new ArrayList<MenuEntry>();
	private boolean isExit = false;
	private static TruckPlantDetailsOrderMenu instance;

	private TruckPlantDetailsOrderMenu() {
		entries.add(new MenuEntry(EXIT_HEADER) {
			@Override
			public void startWork() {
				isExit = true;
			}
		});

	}

	public static TruckPlantDetailsOrderMenu getInstance() {
		if (instance == null) {

			instance = new TruckPlantDetailsOrderMenu();
			appendMenuLine(instance, ENGINE_PLANT_MENU_MSG,
					ENGINE_PLANT_CLASS_NAME, PATH_TO_ENGINE_PLANT_JAR);
			appendMenuLine(instance, LIGHTING_PLANT_MENU_MSG,
					LIGHTING_PLANT_CLASS_NAME, PATH_TO_LIGHTING_PLANT_JAR);
		}
		return instance;
	}

	/**
	 * Starts work of menu
	 * */
	public void startWork() {
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				System.in));
		while (!isExit) {
			printMenu();
			try {
				String line = reader.readLine();
				int choice = Integer.parseInt(line);

				MenuEntry entry = (MenuEntry) entries.get(choice - 1);
				entry.startWork();
			} catch (IOException e) {
				LOGGER.error(e);
			}
		}
	}

	/**
	 * Prints menu header
	 * */
	private void printMenu() {
		LOGGER.info(MENU_HEADER);
		for (int i = 0; i < entries.size(); i++) {
			LOGGER.info(i + 1 + ": " + entries.get(i).getTitle());
		}
	}

	private void addEntry(MenuEntry menuEntry) {
		entries.add(menuEntry);
	}

	/**
	 * Appends menu line
	 * */
	private static void appendMenuLine(TruckPlantDetailsOrderMenu menu, String message,
			final String classToLoad, final String pathToClass) {
		menu.addEntry(new MenuEntry(message) {
			@Override
			public void startWork() {

				try {

					ClassFromJarByLocalPathLoader jarLoader = new ClassFromJarByLocalPathLoader(pathToClass,
							ClassLoader.getSystemClassLoader());

					// load class using classloader
					Class<?> clazz = jarLoader.loadClass(classToLoad);

					// create new instance of loaded class using default
					// constructor
					Plant plant = (Plant) clazz.newInstance();
					for (Part part : plant.getProducedParts()) {

						// log info about part
						LOGGER.info(part.toString());
					}

					// log info about size of parts which plant sent
					LOGGER.info(PACKAGE_SIZE_MSG
							+ plant.getProducedParts().size());

				} catch (InstantiationException | IllegalAccessException e) {
					LOGGER.error(e);
				} catch (ClassNotFoundException e) {
					LOGGER.error(e);
				}

			}
		});
	}

}
