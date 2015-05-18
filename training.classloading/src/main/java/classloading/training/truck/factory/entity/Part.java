package classloading.training.truck.factory.entity;

import classloading.training.truck.factory.enumeration.PartsEnum;

/**
 * Part of truck
 *
 * @author Raman_Skaskevich@epam.com
 * */
public class Part {
	private PartsEnum type;
	private String serialNumber;

	public PartsEnum getType() {
		return type;
	}

	public void setType(PartsEnum partType) {
		this.type = partType;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	@Override
	public String toString() {
		return "Part: " + type.toString() + " S/N: " + serialNumber;
	}
}
