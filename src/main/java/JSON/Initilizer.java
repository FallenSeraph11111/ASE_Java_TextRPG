package JSON;

import Entitys.CreateNPC;
import Entitys.NPCManager;
import Entitys.Team;
import Mapping.EncounterType;
import Mapping.GameMap;
import Mapping.MapGraph;
import items.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Initilizer {
	private static final String ItemJsonLocation = "JSON/Items.Json";
	private static final String ActionJsonLocation = "JSON/Action.json";
	private static final String EnemyJsonLocation = "JSON/Enemy.json";
	private static final String MapJsonLocation = "JSON/map.json";
	private ActionManager am;
	private ItemManager im;
	private MapGraph map;
	private NPCManager npcM;
	private MyJsonParser mjp;
	public Initilizer(ActionManager am, ItemManager im, MapGraph map, NPCManager npc) {
		this.am = am;
		this.im = im;
		this.map = map;
		this.npcM = npc;
		this.mjp=new MyJsonParser();
	}

	public void initItems() throws MissingData {

		JSONObject jo = mjp.getJsonFromFile(ItemJsonLocation);
		JSONArray items = jo.getJSONArray("items");
		int length = items.length();
		if (items == null) {
			throw new MissingData("Item Json was empty");
		}
		for (int i = 0; i < length; i++) {
			JSONObject jItem = items.getJSONObject(i);
			ItemType type = ItemType.valueOf(jItem.getString("type"));
			if (type == ItemType.EQUIPMENT) {
				String name = jItem.getString("name");
				int value = jItem.getInt("value");
				int levelRequirement = jItem.getInt("levelRequirement");
				ArrayList<Action> actions = new ArrayList<>();
				JSONArray jActions = jItem.getJSONArray("actions");
				for (int j = 0; j < jActions.length(); j++) {
					actions.add(am.getAction(jActions.getString(j)));
				}
				double protection = jItem.getDouble("protection");
				EquipmentSlot equipmentSlot = EquipmentSlot.valueOf(jItem.getString("equipmentSlot"));
				CreateItem.CreateableItem item = CreateItem.named(name).valued(value).
						equipable(levelRequirement, actions, equipmentSlot).protects(protection);
				if (equipmentSlot != EquipmentSlot.WEAPON || equipmentSlot != EquipmentSlot.SHIELD) {
					im.addItem(item.buildEquipment());
					continue;
				}
				double baseDamage = jItem.getDouble("baseDamage");
				double damageScaling = jItem.getDouble("damageScaling");
				DamageType damageType = DamageType.valueOf(jItem.getString("DamageType"));
				item.ofTypeWeapon(baseDamage, damageScaling, damageType);
				im.addItem(item.buildEquipment());
			}
		}

	}

	public void initActions() throws MissingData {
		JSONObject jo = mjp.getJsonFromFile(ActionJsonLocation);
		JSONArray actions = jo.getJSONArray("actions");
		if (actions.length() == 0) {
			throw new MissingData("Actions were empty");
		}
		for (int i = 0; i < actions.length(); i++) {
			JSONObject jAction = actions.getJSONObject(i);
			Action action = new Action(jAction.getString("name"), jAction.getString("actionType"), jAction.getString("damageType"), jAction.getBoolean("aoe"), jAction.getDouble("baseDamage"), jAction.getDouble("damageScaling"));
			am.addAction(action);
		}
	}

	public void initEnemys() throws MissingData {
		JSONObject jo = mjp.getJsonFromFile(EnemyJsonLocation);
		JSONArray enemys = jo.optJSONArray("actions");
		if (enemys == null) {
			throw new MissingData("Enemys not found");
		}
		for (int i = 0; i < enemys.length(); i++) {
			JSONObject jEnemy = enemys.getJSONObject(i);
			String name = jEnemy.getString("name");
			double hp_base = jEnemy.getDouble("hp_base");
			CreateNPC.CreateableNPC cnpc = CreateNPC.named(name, hp_base).as(Team.ENEMY);
			if (jEnemy.has("stats")) {
				JSONObject stats = jEnemy.getJSONObject("stats");
				cnpc.withStats(stats.getInt("lvl"),
						stats.getInt("str"),
						stats.getInt("dex"),
						stats.getInt("con"),
						stats.getInt("int"),
						stats.getInt("wis"),
						stats.getInt("cha"));
			}
			if (jEnemy.has("equipment")) {
				JSONObject equipment = jEnemy.optJSONObject("equipment");
				//todo add equipment in the generation process
			}
			npcM.addNPC(cnpc.build());
		}
	}

	public GameMap initMap() throws MissingData {
		JSONObject jo = mjp.getJsonFromFile(MapJsonLocation);
		JSONArray rooms = jo.optJSONArray("rooms");
		if (rooms == null) {
			throw new MissingData("Rooms not found");
		}
		createRooms(rooms);

		JSONArray edges = jo.optJSONArray("edges");
		if (edges == null) {
			throw new MissingData("Edges not found");
		}
		createEdges(edges);


		String startingLabel = jo.getString("startingRoom");

		GameMap gameMap = new GameMap(map, map.getRoom(startingLabel));
		return gameMap;
	}

	private void createRooms(JSONArray rooms) {
		for (int i = 0; i < rooms.length(); i++) {
			try {
				JSONObject room = rooms.getJSONObject(i);
				map.addRoomNode(room.getString("label"), room.getString("encounterID"),
						EncounterType.valueOf(room.getString("EncounterType")),
						room.getInt("layer"));
			}catch (Exception e){
				continue;
			}
		}
	}

	private void createEdges(JSONArray edges) {
		for (int i = 0; i < edges.length(); i++) {
			try {
				JSONObject edge = edges.getJSONObject(i);
				JSONObject node1 = edge.getJSONObject("1");
				JSONObject node2 = edge.getJSONObject("2");
				map.addEdge(node1.getString("label"),node2.getString("label"));
			}catch (Exception e){
				continue;
			}
		}
	}
}

