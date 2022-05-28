package items;

import org.json.JSONArray;

import java.util.Map;

public class Action {
    public final String name;
    public final ActionType actionType;
    public final DamageType damageType;
    public final Boolean aoe;
    public final double baseDamage;
    public final double damageScaling;

    public Action(String name, String actionType, String damageType, Boolean aoe, Double baseDamage, Double damageScaling) {
        this.name = name;
	    System.out.println(actionType +"  "+ActionType.ATTACK);
        this.actionType = ActionType.valueOf(actionType.toUpperCase());
        this.damageType = DamageType.valueOf(damageType);
        this.aoe = aoe;
        this.baseDamage = baseDamage;
        this.damageScaling = damageScaling;
    }

}
