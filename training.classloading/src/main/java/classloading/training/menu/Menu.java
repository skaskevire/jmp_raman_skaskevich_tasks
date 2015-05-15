package classloading.training.menu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Menu
 *
 * @author Raman_Skaskevich@epam.com
 * */
public class Menu {
	private static final Logger LOGGER = LogManager.getLogger(Menu.class);
	private static final String MENU_HEADER = "Menu:";
	private static final String EXIT_HEADER = "Exit:";
	private List<MenuEntry> entries = new ArrayList<MenuEntry>();
	private boolean isExit = false;

	public Menu() {
		entries.add(new MenuEntry(EXIT_HEADER) {
			@Override
			public void run() {
				isExit = true;
			}
		});

	}

	/**
	 * Menu work logic
	 * */
	public void run() {
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				System.in));
		while (!isExit) {
			printMenu();
			try {
				String line = reader.readLine();
				int choice = Integer.parseInt(line);

				MenuEntry entry = (MenuEntry) entries.get(choice - 1);
				entry.run();
			} catch (IOException e) {
				LOGGER.error(e);
			}
		}
	}

	/**
	 * Prints menu header
	 * */
	public void printMenu() {
		LOGGER.info(MENU_HEADER);
		for (int i = 0; i < entries.size(); i++) {
			LOGGER.info(i + 1 + ": " + entries.get(i).getTitle());
		}
	}

	public void addEntry(MenuEntry menuEntry) {
		entries.add(menuEntry);
	}

}
