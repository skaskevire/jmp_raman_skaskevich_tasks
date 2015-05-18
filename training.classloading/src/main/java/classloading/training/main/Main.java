package classloading.training.main;

import java.util.HashMap;
import java.util.Map;

import classloading.training.menu.entry.MenuEntryEnum;
import classloading.training.menu.entry.MenuEntryFactory;
import classloading.training.menu.entry.entity.MenuEntry;
import classloading.training.menu.impl.TruckPlantDetailsOrderMenu;

/**
 * Main class of application
 * 
 * @author Raman_Skaskevich@epam.com
 * */
public class Main {
	public static void main(String args[]) {

		Map<MenuEntryEnum, MenuEntry> menuEntryMap = new HashMap<MenuEntryEnum, MenuEntry>();
		menuEntryMap.put(MenuEntryEnum.APPEND_LINE_ENGINE, MenuEntryFactory
				.getMenuEntry(MenuEntryEnum.APPEND_LINE_ENGINE, args[0]));
		menuEntryMap.put(MenuEntryEnum.APPEND_LINE_LIGHTING, MenuEntryFactory
				.getMenuEntry(MenuEntryEnum.APPEND_LINE_LIGHTING, args[1]));
		menuEntryMap.put(MenuEntryEnum.EXIT, MenuEntryFactory
				.getMenuEntry(MenuEntryEnum.EXIT, null));

		TruckPlantDetailsOrderMenu menu = new TruckPlantDetailsOrderMenu(
				menuEntryMap);
		menu.startWork();
	}
}
