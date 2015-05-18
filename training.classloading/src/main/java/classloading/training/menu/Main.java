package classloading.training.menu;

/**
 * Main class of application
 * 
 * @author Raman_Skaskevich@epam.com
 * */
public class Main {
	public static void main(String args[]) {
		TruckPlantDetailsOrderMenu menu = TruckPlantDetailsOrderMenu.getInstance();
		menu.startWork();
	}
}
