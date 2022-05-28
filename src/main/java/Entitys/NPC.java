package Entitys;

import items.Action;

import java.util.Random;

public class NPC extends Entity{

    public Double xp;

    public NPC(String name, Health hp, Stats stats, Equipment equipment, Team team, Double xp) {
        super(name, hp, stats, equipment, team);
        this.xp = xp;
    }

    public Action actionChoice(){
        Action[] actions =equipment.getActions();
        Random rand = new Random();
        int choice=rand.nextInt(actions.length);
        return actions[choice];
    }
    public Entity chooseTarget(Entity[] enemies){
        return null;
    }

}
