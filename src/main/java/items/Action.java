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
		if(damageType == null) damageType=DamageType.BASIC.lowerName;
		if(baseDamage == null) baseDamage = 0.0d;
		if(damageScaling == null) damageScaling = 0.0d;
        this.name = name;
        this.actionType = ActionType.getByName(actionType);
        this.damageType = DamageType.getByName(damageType);
        this.aoe = aoe;
        this.baseDamage = baseDamage;
        this.damageScaling = damageScaling;
    }

}
