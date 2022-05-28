package Entitys;

import items.Action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NPCManager {
	private ArrayList<NPC> npc;
	private Map<String, Integer> npcIndexMap = new HashMap<>();
	private Map<String, Integer> enemyIndexMap = new HashMap<>();
	private Integer currenNPCIndex = 0;

	public void addNPC(NPC npc) {
		Action action = new Action("", "", "", false, 1.0d, 1.0d);
		this.npc.add(currenNPCIndex, npc);
		if(npc.team == Team.ENEMY){
			enemyIndexMap.put(action.name, currenNPCIndex);
		}else{
			npcIndexMap.put(action.name, currenNPCIndex);
		}
		currenNPCIndex++;
	}

	public NPC getNPC(String npcName, Team team) {
		NPC result = null;
		if (team == Team.ENEMY) {
			int index = enemyIndexMap.get(npcName);
			result = npc.get(index);
		} else {
			int index = npcIndexMap.get(npcName);
			result = npc.get(index);
		}
		return result;
	}
}