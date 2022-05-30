package Battle;

import items.Action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EncounterManager {
	private ArrayList<Encounter> encounter;
	private Map<String,Integer> nameIndexMap =new HashMap<>();
	private Integer currentIndex=0;
	public EncounterManager(){
		encounter=new ArrayList<>();
	}
	public void addEncounter( Encounter newEncounter ){
		//Action action = new Action("","","",false,1.0d,1.0d);
		encounter.add(currentIndex,newEncounter);
		nameIndexMap.put(newEncounter.encounterID,currentIndex);
		currentIndex++;
	}
	public Encounter getEncounter(String encounterID){
		return encounter.get(nameIndexMap.get(encounterID));
	}

}
