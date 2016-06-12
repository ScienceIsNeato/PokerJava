public class RunMVC {

	public RunMVC() {

		Model pokerModel = new Model();
		View pokerView = new View();

		pokerModel.addObserver(pokerView);

		Controller pokerController = new Controller();
		pokerController.addModel(pokerModel);
		pokerController.addView(pokerView);

		pokerView.addController(pokerController);

	}

}
