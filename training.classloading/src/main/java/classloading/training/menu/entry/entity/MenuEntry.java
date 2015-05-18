package classloading.training.menu.entry.entity;

/**
 * Part of menu
 *
 * @author Raman_Skaskevich@epam.com
 * */
public abstract class MenuEntry {
	protected String title;
	protected String filePath;
	
	public String getTitle() {
		return title;
	}
	
	public MenuEntry() {
	}

	public MenuEntry(String filePath) {
		this.filePath = filePath;
	}

	public abstract void performMenuAction();

	public void setTitle(String title) {
		this.title = title;
	}
}
