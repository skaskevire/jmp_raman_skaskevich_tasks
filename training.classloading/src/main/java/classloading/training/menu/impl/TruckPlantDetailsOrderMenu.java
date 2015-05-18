package classloading.training.menu.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import classloading.training.menu.ITruckPlantDetailsOrderMenu;
import classloading.training.menu.entry.MenuEntryEnum;
import classloading.training.menu.entry.entity.ExitMenuEntry;
import classloading.training.menu.entry.entity.MenuEntry;

/**
 * Implementation of {@ITruckPlantDetailsOrderMenu}
 *
 * @author Raman_Skaskevich@epam.com
 * */
public class TruckPlantDetailsOrderMenu implements ITruckPlantDetailsOrderMenu{

	private static final Logger LOGGER = LogManager
			.getLogger(TruckPlantDetailsOrderMenu.class);
	private static final String MENU_HEADER = "Menu:";
	private static final String MENU_LINE = "%s: %s";

	private Map<MenuEntryEnum, MenuEntry> entries = new HashMap<MenuEntryEnum, MenuEntry>();

	public TruckPlantDetailsOrderMenu(Map<MenuEntryEnum, MenuEntry> entries) {
		this.entries = entries;
	}

	/**
	 * Starts work of menu
	 * */
	@Override
	public void startWork() {
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				System.in));
		while (!((ExitMenuEntry) entries.get(MenuEntryEnum.EXIT)).isExit()) {
			printMenu();
			try {
				String line = reader.readLine();
				int choice = Integer.parseInt(line);

				MenuEntry entry = (MenuEntry) entries.get(MenuEntryEnum
						.getBySequenceNumber(choice));
				entry.performMenuAction();
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

		for (MenuEntryEnum entry : entries.keySet()) {
			LOGGER.info(String.format(MENU_LINE, entry.getSequenceNumber(),
					entry.getMessage()));
		}
	}
}
