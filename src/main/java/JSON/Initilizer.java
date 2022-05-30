package JSON;

import Mapping.Encounter;
import Mapping.EncounterManager;
import Entitys.*;
import Mapping.EncounterType;
import Mapping.GameMap;
import Mapping.MapGraph;
import Mapping.RoomNode;
import items.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;

public class Initilizer {
	private static final String ItemJsonLocation = "JSON/Items.Json";
	private static final String ActionJsonLocation = "JSON/Action.json";
	private static final String EnemyJsonLocation = "JSON/Enemy.json";
	private static final String MapJsonLocation = "JSON/map.json";
	private static final String PlayerPresetsLocation = "JSON/playerPresets.json";
	private ActionManager am;
	private ItemManager im;
	private MapGraph map;
	private NPCManager npcM;
	private MyJsonParser mjp;
	private EncounterManager encounterM;

	public Initilizer(ActionManager am, ItemManager im, MapGraph map, NPCManager npc, EncounterManager encounterManager) {
		this.am = am;
		this.im = im;
		this.map = map;
		;
		this.encounterM = encounterManager;
		this.npcM = npc;
		this.mjp = new MyJsonParser();
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
			String name = jItem.getString("name");
			int value = jItem.getInt("value");
			CreateItem.CreateableItem item = CreateItem.named(name).valued(value);
			if (type == ItemType.EQUIPMENT) {
				int levelRequirement = jItem.getInt("levelRequirement");
				ArrayList<Action> actions = new ArrayList<>();
				JSONArray jActions = jItem.optJSONArray("actions");
				if (jActions != null) {
					for (int j = 0; j < jActions.length(); j++) {
						actions.add(am.getAction(jActions.getString(j)));
					}
				}
				double protection = jItem.optDouble("protection",0.0);
				EquipmentSlot equipmentSlot = EquipmentSlot.valueOf(jItem.getString("equipmentSlot"));
				item.equipable(levelRequirement, actions, equipmentSlot).protects(protection);
				if (equipmentSlot != EquipmentSlot.WEAPON || equipmentSlot != EquipmentSlot.SHIELD) {
					im.addItem(item.buildEquipment());
					continue;
				}
				double baseDamage = jItem.getDouble("baseDamage");
				double damageScaling = jItem.getDouble("damageScaling");
				DamageType damageType = DamageType.getByName(jItem.getString("damageType"));
				item.ofTypeWeapon(baseDamage, damageScaling, damageType);
				im.addItem(item.buildEquipment());
				continue;
			}
			im.addItem(item.buildItem());

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
			Action action = new Action(jAction.getString("name"), jAction.getString("actionType"), jAction.optString("damageType"), jAction.getBoolean("aoe"), jAction.optDouble("baseDamage"), jAction.optDouble("damageScaling"));
			am.addAction(action);
		}
	}

	public void initEnemys() throws MissingData {
		JSONObject jo = mjp.getJsonFromFile(EnemyJsonLocation);
		JSONArray enemies = jo.optJSONArray("enemies");
		if (enemies == null) {
			throw new MissingData("Enemys not found");
		}
		for (int i = 0; i < enemies.length(); i++) {
			JSONObject jEnemy = enemies.getJSONObject(i);
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
				JSONArray equipment = jEnemy.getJSONArray("equipment");
				Equipment equip=loadPlayerEquipment(equipment);
				cnpc.withEquipment(equip);
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


		JSONArray encounters = jo.optJSONArray("encounters");
		if (encounters == null) {
			throw new MissingData("Encounters not found");
		}

		createEncounters(encounters);
		RoomNode statingRoom = map.getRoom(startingLabel);
		GameMap gameMap = new GameMap(map, statingRoom);
		return gameMap;
	}

	private void createEncounters(JSONArray encounters) {
		for (int i = 0; i < encounters.length(); i++) {
			JSONObject encounter = encounters.getJSONObject(i);
			String encounterID = encounter.getString("EncounterID");
			EncounterType type = EncounterType.valueOf(encounter.getString("EncounterType"));
			String loot = encounter.optString("loot");
			JSONArray enemiesJson = encounter.optJSONArray("enemies");
			ArrayList<String> enemies = null;
			if (enemiesJson != null) {
				enemies = new ArrayList<>();
				for (int j = 0; j < enemiesJson.length(); j++) {
					String name = enemiesJson.getJSONObject(j).getString("name");
					enemies.add(name);
				}
			}
			Encounter en;
			if (type == EncounterType.PATH) {
				en = new Encounter(encounterID, type);
			} else if (type == EncounterType.CHEST) {
				en = new Encounter(encounterID, type, loot);
			} else if (loot != null && enemies != null) {
				en = new Encounter(encounterID, type, enemies, loot);
			} else if (enemies != null) {
				en = new Encounter(encounterID, type, enemies);
			} else {
				continue;
			}
			encounterM.addEncounter(en);
		}
	}

	private void createRooms(JSONArray rooms) {
		for (int i = 0; i < rooms.length(); i++) {
			try {
				JSONObject room = rooms.getJSONObject(i);
				if(room.has("text")){
					map.addRoomNode(room.getString("label"), room.getString("encounterID"),
							EncounterType.valueOf(room.getString("EncounterType")),
							room.getInt("layer"),room.getString("text"));
				}
				map.addRoomNode(room.getString("label"), room.getString("encounterID"),
						EncounterType.valueOf(room.getString("EncounterType")),
						room.getInt("layer"));
			} catch (Exception e) {
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
				map.addEdge(node1.getString("label"), node2.getString("label"));
			} catch (Exception e) {
				continue;
			}
		}
	}

	public List<Player> loadPlayerPresets() {
		JSONObject jo = mjp.getJsonFromFile(PlayerPresetsLocation);
		JSONArray players = jo.getJSONArray("presets");
		List<Player> playerList = new ArrayList<>();
		for (int i = 0; i < players.length(); i++) {

			try {
				JSONObject playerJ = players.getJSONObject(i);
				JSONObject jHP = playerJ.getJSONObject("hp");
				JSONObject jStats = playerJ.getJSONObject("stats");
				JSONArray jEquipment = playerJ.optJSONArray("equipment");
				JSONObject jInventory = playerJ.optJSONObject("inventory");
				String label = playerJ.getString("label");
				Stats stats = loadPlayerStats(jStats);
				Health hp = loadPlayerHealth(jHP, stats);
				Equipment equip = loadPlayerEquipment(jEquipment);
				Inventory inv = loadInventory(jInventory);
				playerList.add(new Player(label, hp, stats, equip, Team.PLAYER, inv));
			} catch (JSONException j) {
				continue;
			}
		}
		return playerList;
	}

	private Inventory loadInventory(JSONObject jInventory) {

		Inventory inv;
		Set<String> keyset = jInventory.keySet();
		if (keyset.isEmpty()) {
			inv = new Inventory();
		} else {
			List<Item> itemList = new ArrayList<>();
			Map<String, Integer> itemAmounts = new HashMap<>();
			for (String item : keyset) {
				Item i = im.getItem(item);
				if (i == null) {
					continue;
				}
				itemList.add(i);
				itemAmounts.put(item, jInventory.getInt(item));
			}
			inv = new Inventory(itemList, itemAmounts);
		}
		return inv;
	}

	private Stats loadPlayerStats(JSONObject jStats) {
		int lvl = jStats.optInt("lvl", 8);
		int ststr = jStats.optInt("str", 10);
		int stdex = jStats.optInt("dex", 10);
		int stcon = jStats.optInt("con", 10);
		int stint = jStats.optInt("int", 10);
		int stwis = jStats.optInt("wis", 10);
		int stcha = jStats.optInt("cha", 10);
		Stats stats = new Stats(lvl, ststr, stdex, stcon, stint, stwis, stcha);
		return stats;
	}

	private Health loadPlayerHealth(JSONObject jhp, Stats stats) {
		Health hp;
		double hp_base = jhp.optDouble("hp_base", 10.0);
		double hp_cur = jhp.optDouble("hp_cur", -1.0);
		if (hp_cur == -1.0d) {
			hp = new Health(hp_base, stats.getStat(Stat.CON));
		} else {
			hp = new Health(hp_base, hp_cur, stats.getStat(Stat.CON));
		}
		return hp;
	}

	private Equipment loadPlayerEquipment(JSONArray jequipment) {
		List<EquipmentItem> items = new ArrayList<>();
		for (int i = 0; i < jequipment.length(); i++) {
			EquipmentItem item = im.getEquipment(jequipment.getString(i));
			items.add(item);
		}
		Equipment equipment = new Equipment(items);
		return equipment;
	}
}

