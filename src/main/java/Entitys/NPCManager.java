package Entitys;

import items.Action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NPCManager {
	private ArrayList<NPC> npc = new ArrayList<>();
	private Map<String, Integer> npcIndexMap = new HashMap<>();
	private Map<String, Integer> enemyIndexMap = new HashMap<>();
	private Integer currenNPCIndex = 0;

	public void addNPC(NPC npc) {
		this.npc.add(currenNPCIndex, npc);
		if (npc.team == Team.ENEMY) {
			enemyIndexMap.put(npc.name, currenNPCIndex);
		} else {
			npcIndexMap.put(npc.name, currenNPCIndex);
		}
		currenNPCIndex++;
	}

	public NPC getNPC(String npcName, Team team) {
		NPC result = null;
		if (team == Team.ENEMY) {
			int index = enemyIndexMap.getOrDefault(npcName, null);
			result = npc.get(index);
		} else {
			int index = npcIndexMap.getOrDefault(npcName, null);
			result = npc.get(index);
		}
		return result;
	}
}