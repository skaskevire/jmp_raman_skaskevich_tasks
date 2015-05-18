package classloading.training.menu.entry.entity;

import classloading.training.menu.entry.MenuEntryEnum;

/**
 * Implementation of {@MenuEntry}
 *
 * @author Raman_Skaskevich@epam.com
 * */
public class ExitMenuEntry extends MenuEntry {
	private boolean isExit = false;

	public boolean isExit() {
		return isExit;
	}

	public ExitMenuEntry() {
		setTitle(MenuEntryEnum.EXIT.getMessage());
	}	

	@Override
	public void performMenuAction() {
		isExit = true;
	}

}
