package classloading.training.truck.factory.entity;

import java.util.List;

/**
 * Interface for plant which produces some part of truck
 *
 * @author Raman_Skaskevich@epam.com
 * */
public interface Plant {
	public List<Part> getProducedParts();
}
