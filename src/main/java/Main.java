import Entitys.NPCManager;
import Entitys.Player;
import Mapping.GameMap;
import JSON.Initilizer;
import JSON.MissingData;
import Mapping.MapGraph;
import items.ActionManager;
import items.ItemManager;

class Main{
	private NPCManager npcManager;
	private ItemManager itemManager;
	private GameMap map;
	private ActionManager actionManager;
	private Player player;
	public static void main(String[] args) {
		Main main=new Main();
		main.initGame();
		main.startGame();
	}

	private boolean startGame() {
		//returns false if new game true if old game


		return false;
	}

	private  void gameLoop(){
		boolean running=true;
		while(running){

		}
	}

	private void initGame() {
		npcManager=new NPCManager();
		itemManager=new ItemManager();
		MapGraph mapGraph= new MapGraph();
		actionManager=new ActionManager();

		Initilizer ini = new Initilizer(actionManager,itemManager,mapGraph,npcManager);
		try {
			ini.initActions();
			ini.initItems();
			ini.initEnemys();
			map=ini.initMap();
		}catch (MissingData m){
			System.out.println("There was an error while reading the JSON resources." +
					" Please make sure that the Files are where they are supposed to be");
			System.exit(-2);
		}

		return;
	}
	


}