package classloading.training.menu.entry;

import java.util.Arrays;
import java.util.List;

/**
 * MenuEntry enum
 *
 * @author Raman_Skaskevich@epam.com
 * */
public enum MenuEntryEnum {
	EXIT(1, "Exit: ", ""), APPEND_LINE_ENGINE(2,
			"Get package of produced parts from EnginePlant", "EnginePlant"), APPEND_LINE_LIGHTING(
			3, "Get package of produced parts from LightingPlant",
			"LightingPlant");
	private Integer sequenceNumber;
	private String message;
	private String className;
	private static final List<MenuEntryEnum> VALUES = Arrays
			.asList(MenuEntryEnum.class.getEnumConstants());

	public String getClassName() {
		return className;
	}

	public Integer getSequenceNumber() {
		return sequenceNumber;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	private MenuEntryEnum(Integer sequenceNumber, String message,
			String className) {
		this.message = message;
		this.sequenceNumber = sequenceNumber;
		this.className = className;
	}

	public String getMessage() {
		return message;
	}

	public static MenuEntryEnum getBySequenceNumber(Integer sequenceNumber) {
		MenuEntryEnum result = null;
		for (MenuEntryEnum menuEntry : VALUES) {
			if (menuEntry.getSequenceNumber() == sequenceNumber) {
				result = menuEntry;
			}
		}
		return result;

	}
}
