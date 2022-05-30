package Mapping;

import Entitys.NPC;
import Mapping.EncounterType;

import java.util.List;

public class Encounter {
	public final String encounterID;
	public final EncounterType type;
	public final List<String> enemys;
	public final String loot;

	public Encounter(String encounterID, EncounterType type, List<String> enemys, String loot) {
		this.encounterID = encounterID;
		this.type = type;
		this.enemys = enemys;
		this.loot = loot;
	}

	public Encounter(String encounterID, EncounterType type, String loot) {
		this.encounterID = encounterID;
		this.type = type;
		this.loot = loot;
		this.enemys=null;
	}

	public Encounter(String encounterID, EncounterType type, List<String> enemys) {
		this.encounterID = encounterID;
		this.type = type;
		this.enemys = enemys;
		this.loot=null;
	}

	public Encounter(String encounterID, EncounterType type) {
		this.encounterID = encounterID;
		this.type = type;
		this.enemys=null;
		loot=null;
	}
}
