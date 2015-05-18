package classloading.training.menu.entry;

import classloading.training.menu.entry.entity.AppendEngineMenuLineEntry;
import classloading.training.menu.entry.entity.AppendLightingMenuLineEntry;
import classloading.training.menu.entry.entity.ExitMenuEntry;
import classloading.training.menu.entry.entity.MenuEntry;

/**
 * Factory class for MenuEntry
 *
 * @author Raman_Skaskevich@epam.com
 * */
public class MenuEntryFactory {

	public static MenuEntry getMenuEntry(MenuEntryEnum menuEntry,
			String filePath) {
		MenuEntry entry = null;
		switch (menuEntry) {
		case EXIT:
			entry = new ExitMenuEntry();
			break;
		case APPEND_LINE_ENGINE:
			entry = new AppendEngineMenuLineEntry(filePath);
			break;
		case APPEND_LINE_LIGHTING:
			entry = new AppendLightingMenuLineEntry(filePath);
			break;

		}
		return entry;
	}
}
