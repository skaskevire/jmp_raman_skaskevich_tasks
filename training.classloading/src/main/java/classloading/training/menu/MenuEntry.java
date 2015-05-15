package classloading.training.menu;

/**
 * Part of menu
 *
 * @author Raman_Skaskevich@epam.com
 * */
public abstract class MenuEntry {
	protected String title;
	
	public String getTitle() {
		return title;
	}

	public MenuEntry(String title) {
		this.title = title;
	}

	public abstract void run();
}
