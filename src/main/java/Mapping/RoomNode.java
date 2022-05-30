package Mapping;

import java.util.Objects;

public class RoomNode {
	public final String label;
	public final EncounterType type;
	public final String encounterID;
	public final int layer;
	public boolean beaten = false;
	public final String text;
	RoomNode(String label, EncounterType type, String encounterID, int layer,String text) {
		this.label = label;
		this.encounterID = encounterID;
		this.type = type;
		this.layer = layer;
		this.text=text;
	}
	RoomNode(String label, EncounterType type, String encounterID, int layer) {
		this.label = label;
		this.encounterID = encounterID;
		this.type = type;
		this.layer = layer;
		this.text="";
	}

	RoomNode(String label, EncounterType type, String encounterID, int layer, boolean beaten,String text) {
		this.label = label;
		this.encounterID = encounterID;
		this.type = type;
		this.layer = layer;
		this.beaten = beaten;
		this.text=text;
	}
	RoomNode(String label, EncounterType type, String encounterID, int layer, boolean beaten) {
		this.label = label;
		this.encounterID = encounterID;
		this.type = type;
		this.layer = layer;
		this.beaten = beaten;
		this.text="";
	}

	public void beatenRoom() {
		beaten = true;
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
