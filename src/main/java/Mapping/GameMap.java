package Mapping;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameMap {
	public MapGraph graph;
	RoomNode currentNode;
	public GameMap(MapGraph graph, RoomNode currentNode) {
		this.graph = graph;
		this.currentNode = currentNode;
	}


	public Map<String,RoomNode> GetAdjacentNodes(){
		Map<String,RoomNode> nodes= new HashMap<String, RoomNode>();
		List<RoomNode> adjacent=graph.getAdjVertices(currentNode.label);
		int index=0;
		for (RoomNode entry: adjacent) {
			nodes.putIfAbsent(Integer.toString(index),entry);
			index++;
		}
		return nodes;
	}
}

