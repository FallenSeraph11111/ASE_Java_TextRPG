package Mapping;

import java.util.*;

public class MapGraph {
	private Map<RoomNode, List<RoomNode>> adjVertices;
	private Map<String, RoomNode> rooms;

	public MapGraph() {
		this.adjVertices = new HashMap<>();
		this.rooms = new HashMap<>();
	}

	// standard constructor, getters, setters
	public void addRoomNode(String label, String encounterID, EncounterType type,int layer) {
		RoomNode node = new RoomNode(label, type, encounterID,layer);
		adjVertices.putIfAbsent(node, new ArrayList<>());
		rooms.putIfAbsent(label, node);
	}
	public void addRoomNode(String label, String encounterID, EncounterType type,int layer,String text) {
		RoomNode node = new RoomNode(label, type, encounterID,layer,text);
		adjVertices.putIfAbsent(node, new ArrayList<>());
		rooms.putIfAbsent(label, node);
	}

	public void removeRoomNode(String label, String encounterID, EncounterType type,int layer) {
		RoomNode v = new RoomNode(label, type, encounterID,layer);
		adjVertices.values().stream().forEach(e -> e.remove(v));
		adjVertices.remove(new RoomNode(label, type, encounterID,layer));
	}

	public void addEdge(String label1, String label2) {
		RoomNode v1 = rooms.getOrDefault(label1, new RoomNode(label1, EncounterType.PATH, "0",0));
		RoomNode v2 = rooms.getOrDefault(label2, new RoomNode(label2, EncounterType.PATH, "0",0));
		adjVertices.get(v1).add(v2);
		adjVertices.get(v2).add(v1);
	}

	public void removeEdge(String label1, String label2) {
		RoomNode v1 = rooms.getOrDefault(label1, new RoomNode(label1, EncounterType.PATH, "0",0));
		RoomNode v2 = rooms.getOrDefault(label2, new RoomNode(label2, EncounterType.PATH, "0",0));
		List<RoomNode> eV1 = adjVertices.get(v1);
		List<RoomNode> eV2 = adjVertices.get(v2);
		if (eV1 != null)
			eV1.remove(v2);
		if (eV2 != null)
			eV2.remove(v1);
	}

	public List<RoomNode> getAdjVertices(String label) {
		RoomNode node = rooms.getOrDefault(label, new RoomNode(label, EncounterType.PATH, "0",0));
		return adjVertices.getOrDefault(node, null);
	}
	public RoomNode getRoom(String label){
		return rooms.getOrDefault(label,null);
	}
}
