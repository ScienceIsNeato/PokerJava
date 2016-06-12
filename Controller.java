class Controller implements java.awt.event.ActionListener
{
	Model model;
	View view;

	Controller()
	{

	}

	public void actionPerformed(java.awt.event.ActionEvent e)
	{
		if(e.getActionCommand() == "Deal Hands")
		{
			model.getDeck().shuffleCards(); // shuffle a new deck
			if (!model.parseNumPlayers(view.getNumPlayers()))
			{
				// We can safely get the number of players as the input has already been parsed
				model.generatePlayers(model.getNumPlayers());
				if (model.dealCards(model.getNumPlayers()))
				{
					model.printHands();
					model.incrementDealNumber();
				}
				else
				{
					// Logic in model should make this impossible, but catch it just in case
					view.displayError("Error dealing cards - no more cards remaining.");
				}
			}
			else
			{
				view.displayError("Number of players must be between 1 and 10. Can't play with <" + view.getNumPlayers() + "> players!");
			}
		}

	}

	public void addModel(Model m){
		this.model = m;
	}

	public void addView(View v){
		this.view = v;
	}

}
