package GUI;

import Entitys.Entity;
import Entitys.NPC;
import Entitys.Player;
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
		gui.printText("What Action do you want to take");
		gui.printText("index : Action  :  aoe  :  baseDamage");
		int i=0;
		for (Action action : actions) {
			gui.printText(i + " : " + action.name + " : "+action.aoe + " : "+ action.baseDamage);
			i++;
		}
		while (true) {
			try {
				String input = gui.waitForInput();
				int selected = Integer.parseInt(battleInputParser(input));
				if (selected < 0 && selected >= actions.size()) {
					gui.printText("Please enter one of the listed values");
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
		gui.setBattleLog(log.toString());
    }

    public Entity chooseTarget(List<Entity> enemies) {
		gui.printText("Who do you want to Target");
	    gui.printText("index : Enemy");
		int i=0;
	    for (Entity enemy : enemies) {
		    gui.printText(i + " : " + enemy.name);
		    i++;
	    }
	    while (true) {
		    try {
			    String input = gui.waitForInput();
			    int selected = Integer.parseInt(battleInputParser(input));
			    if (selected < 0 && selected >= enemies.size()) {
				    gui.printText("Please enter one of the listed values");
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
