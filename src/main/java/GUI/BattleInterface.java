package GUI;

import Entitys.Entity;
import items.Action;

import java.util.List;

public class BattleInterface {
	private GUI gui;
	private StringBuilder log;
	public BattleInterface(GUI gui) {
		this.gui = gui;
		log=new StringBuilder();
	}

	public Action chooseAction(List<Action> actions){
		gui.printTextToOutput("What Action do you want to take");
		gui.printTextToOutput("index : Action  :  aoe  :  baseDamage");
		int i=0;
		for (Action action : actions) {
			gui.printTextToOutput(i + " : " + action.name + " : "+action.aoe + " : "+ action.baseDamage);
			i++;
		}
		while (true) {
			try {
				String input = gui.waitForInput();
				int selected = Integer.parseInt(battleInputParser(input));
				if (selected < 0 && selected >= actions.size()) {
					gui.printTextToOutput("Please enter one of the listed values");
					continue;
				}
				Action action = actions.get(selected);
				return action;
			} catch (NumberFormatException e) {
				continue;
			}
		}
    }
    public void battleLog(String lastAction){
		log.append(lastAction);
		log.append("\n\r");
		gui.addToBattleLog(log.toString());
    }

    public Entity chooseTarget(List<Entity> enemies) {
		gui.printTextToOutput("Who do you want to Target");
	    gui.printTextToOutput("index : Enemy");
		int i=0;
	    for (Entity enemy : enemies) {
		    gui.printTextToOutput(i + " : " + enemy.name);
		    i++;
	    }
	    while (true) {
		    try {
			    String input = gui.waitForInput();
			    int selected = Integer.parseInt(battleInputParser(input));
			    if (selected < 0 && selected >= enemies.size()) {
				    gui.printTextToOutput("Please enter one of the listed values");
				    continue;
			    }
			    Entity enemy = enemies.get(selected);
			    return enemy;
		    } catch (NumberFormatException e) {
			    continue;
		    }
	    }
    }

	private String battleInputParser(String input) {
		input = input.stripIndent();
		input = input.stripTrailing();
		return input;
	}
}
