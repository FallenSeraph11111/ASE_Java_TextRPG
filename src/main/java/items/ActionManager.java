package items;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ActionManager {
    private ArrayList<Action> actions;
    private Map<String,Integer> nameIndexMap =new HashMap<>();
    private Integer currentIndex=0;
	public ActionManager(){
		actions=new ArrayList<>();
	}
    public void addAction( Action newAction ){
        //Action action = new Action("","","",false,1.0d,1.0d);
        actions.add(currentIndex,newAction);
        nameIndexMap.put(newAction.name,currentIndex);
        currentIndex++;
    }
    public Action getAction(String actionName){
        return actions.get(nameIndexMap.get(actionName));
    }

}
