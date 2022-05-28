package Mapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

class RoomNode {
	public final String label;
	public final EncounterType type;
	public final String encounterID;
	public final int layer;
	public boolean beaten=false;

	RoomNode(String label, EncounterType type, String encounterID, int layer) {
		this.label = label;
		this.encounterID = encounterID;
		this.type=type;
		this.layer=layer;
	}
	RoomNode(String label, EncounterType type, String encounterID, int layer, boolean beaten) {
		this.label = label;
		this.encounterID = encounterID;
		this.type=type;
		this.layer=layer;
		this.beaten=beaten;
	}
	public void beatenRoom(){
		beaten=true;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		RoomNode roomNode = (RoomNode) o;
		return encounterID == roomNode.encounterID && layer == roomNode.layer && label.equals(roomNode.label) && type == roomNode.type;
	}

	@Override
	public int hashCode() {
		return Objects.hash(label);
	}
}
public class MapGraph {
	private Map<RoomNode, List<RoomNode>> adjVertices;
	private Map<String, RoomNode> rooms;

	// standard constructor, getters, setters
	public void addRoomNode(String label, String encounterID, EncounterType type,int layer) {
		RoomNode node = new RoomNode(label, type, encounterID,layer);
		adjVertices.putIfAbsent(node, new ArrayList<>());
		rooms.putIfAbsent("label", node);
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
