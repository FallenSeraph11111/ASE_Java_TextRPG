import Battle.BattleController;
import Mapping.Encounter;
import Mapping.EncounterManager;
import Entitys.NPCManager;
import Entitys.Player;
import GUI.GUI;
import Mapping.GameMap;
import JSON.Initilizer;
import JSON.MissingData;
import Mapping.MapGraph;
import Mapping.RoomNode;
import items.ActionManager;
import items.ItemManager;

import java.util.List;
import java.util.Map;

class Main {
	private NPCManager npcManager;
	private ItemManager itemManager;
	private GameMap map;
	private ActionManager actionManager;
	private EncounterManager em;
	private Player player;
	private Initilizer ini;
	private boolean inInventory = false;
	private boolean inMap = false;
	private boolean inLevelUp = false;
	private GUI gui;

	public static void main(String[] args) throws InterruptedException {
		Main main = new Main();
		main.initGame();
		boolean newGame = main.startUpType();
		if (newGame) {
			main.startGame();
		} else {
			main.startSaveGame();
		}
		main.gameLoop();
	}

	private boolean startUpType() {
		//returns false if new game true if old game
		gui.main();
		gui.clearOutput();
		gui.printText("Do you Want to start a new Game?");
		gui.setInputLabel("y, if you want to; n, if not");
		while (true) {
			String answer = gui.waitForInput();
			answer = inputParser(answer);
			if (answer.equals("y")) return true;
			if (answer.equals("n")) return false;
			gui.printText("Please enter either enter 'y' or 'n' and press submit. ");
		}
	}

	private void startSaveGame() throws InterruptedException {
		gui.printText("This Option is currently unavailable. Sorry but due to time constraints it is sadly not implementet");
		Thread.sleep(4000);
		startGame();
	}

	private Player createPlayer() {
		gui.clearOutput();
		List<Player> presets = ini.loadPlayerPresets();
		int i = 0;
		gui.printText("Please select Player Preset you want to use");
		gui.printText("index : preset name");
		gui.setInputLabel("");
		for (Player p : presets) {
			gui.printText(i + " : " + p.name);
			i++;
		}
		while (true) {
			try {
				String input = gui.waitForInput();
				int selected = Integer.parseInt(inputParser(input));
				if (selected < 0 && selected >= presets.size()) {
					gui.printText("Please enter one of the listed values");
					continue;
				}
				Player localPlayer = presets.get(selected);
				localPlayer.name="Player";
				return localPlayer;
			} catch (NumberFormatException e) {
				continue;
			}
		}
	}

	private void startGame() {
		player = createPlayer();
		gui.health(player.hp.getHp_max(),player.hp.getHp());
		gui.armour(player.equipment.getProtection());
		gui.damage(player.equipment.getBase_damage());
	}

	private void gameLoop() throws InterruptedException {
		boolean running = true;
		int i = 0;
		while (running) {
			boolean cleared = handleCurrentRoom(map.currentNode);
			if (!cleared) gameOver();
			map.currentNode = nextRoom();
		}
	}

	private RoomNode nextRoom() {
		Map<String, RoomNode> choices = map.GetAdjacentNodes();
		while (true) {
			gui.clearOutput();
			gui.printText("Where do you want to go?");
			gui.printText("Input  :   Room it leads to : have you beaten the room");
			StringBuilder build = new StringBuilder("Input Choices: i -> Inventory, l-> Level Screen");
			for (String choice : choices.keySet()) {
				gui.printText(choice + " : " + choices.get(choice).label + " : " + choices.get(choice).beaten);
				build.append(", " + choice);
			}
			gui.setInputLabel(build.toString());
			String selectedChoice = inputParser(gui.waitForInput());
			RoomNode nextRoom = choices.get(selectedChoice);
			if (nextRoom != null) {
				gui.clearOutput();
				gui.setInputLabel("");
				return nextRoom;
			}
			gui.printText("Please enter one of the listed room choices");
		}
	}

	private String inputParser(String input) {
		input = input.stripIndent();
		input = input.stripTrailing();
		if (input.equals("m") && !inMap) {
			inMap = true;
			handleMap();
			inMap = false;
		} else if (input.equals("i") && !inInventory) {
			inInventory = true;
			handleInventory();
			inInventory = false;
		} else if (input.equals("l") && !inLevelUp) {
			inLevelUp = true;
			levelUp();
			inLevelUp = false;
		}
		return input;
	}

	private void levelUp() {
		return;
	}

	private boolean handleCurrentRoom(RoomNode room) throws InterruptedException {
		if (room.beaten) {
			return true;
		}
		gui.clearOutput();
		gui.printText(room.text);
		Thread.sleep(2000);
		Encounter encounter = em.getEncounter(room.encounterID);
		BattleController bc = null;
		switch (encounter.type) {
			case BOSS, ENEMY -> bc = new BattleController(player, encounter.enemys, null, npcManager,gui);
			case PATH -> {
				room.beaten = true;
				return true;
			}
			case CHEST -> room.beaten=handleChestRoom(encounter);

		}
		if (bc != null) {
			boolean won = bc.startBattle();
			if (won) {
				room.beaten = true;
				return true;
			}
			return false;
		}
		return true;

	}

	private boolean handleChestRoom(Encounter encounter) {
		gui.printText("Do you want to Open the Chest?");
		gui.setInputLabel("Please enter either enter 'y' or 'n' and press submit.");
		while (true){
			String answer = gui.waitForInput();
			answer = inputParser(answer);
			if (answer.equals("y")){
				player.inventory.addItem(itemManager.getItem(encounter.loot));
				gui.printText("You found "+encounter.loot+"!");
				gui.setInputLabel("");
				return true;
			}
			else if (answer.equals("n")) {
				return false;
			}
			else{
				gui.printText("please enter either one of those Options");
			}

		}

	}

	private void handleInventory() {
		return;
	}

	private void handleMap() {
		return;
	}

	private void gameOver() {

	}

	private void initGame() {
		npcManager = new NPCManager();
		itemManager = new ItemManager();
		MapGraph mapGraph = new MapGraph();
		actionManager = new ActionManager();
		em = new EncounterManager();
		Initilizer ini = new Initilizer(actionManager, itemManager, mapGraph, npcManager, em);
		this.ini = ini;
		try {
			ini.initActions();
			ini.initItems();
			ini.initEnemys();
			map = ini.initMap();
		} catch (MissingData m) {
			System.out.println("There was an error while reading the JSON resources." +
					" Please make sure that the Files are where they are supposed to be");
			System.exit(-2);
		}
		gui = new GUI("ASE Dungeon Crawler");
		return;
	}


}