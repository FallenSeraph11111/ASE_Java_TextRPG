package Battle;

import Entitys.*;
import GUI.BattleInterface;
import items.Action;
import items.EquipmentItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BattleController {
    private Entity player;
    private Entity[] enemies;
    private Entity[] allies;
    private final BattleInterface BI;

    public BattleController(Entity player, Entity[] enemies, Entity[] allies, BattleInterface bi) {
        this.player = player;
        this.enemies = enemies;
        this.allies = allies;
        BI = bi;
    }

    public void startBattle(){
        Team team = startingTeam();
        battleRound(team);
        while(!loseCondition() || !winCondition()) {
            battleRound(Team.nextTeam(team));
        }
    }
    private void battleRound(Team currentTeam){
        Entity[] currentTeamEntities=getTeam(currentTeam);
        Entity[] enemyTeam=getEnemies(currentTeam);
        for (Entity entity : currentTeamEntities){
            entity.resetBlocking();
            if (currentTeam==Team.PLAYER){
                parseAction(BI.chooseTarget(enemies),BI.chooseAction(player.equipment.getActions()),player);
                continue;
            }
            NPC currentNpc=entity instanceof NPC ? ((NPC) entity) : null; //todo remove this null introduce exception
            Entity target=currentNpc.chooseTarget(enemyTeam);
            Action action=currentNpc.actionChoice();
            parseAction(target,action,currentNpc);
        }
    }
    private Entity[] getEnemies(Team currentTeam){
        ArrayList<Entity> enemyTeam = new ArrayList();
        switch (currentTeam){
            case PLAYER,ALLY -> enemyTeam.addAll(List.of(enemies));
            case ENEMY -> {
                enemyTeam.addAll(List.of(allies));
                enemyTeam.add(player);
            }
            default -> enemyTeam.clear();
        }
        return (Entity[]) enemyTeam.toArray();
    }
    private Entity[] getTeam(Team currentTeam){
        Entity[] currentTeamEntities;
        switch (currentTeam){
            case PLAYER -> currentTeamEntities=new Entity[]{player};
            case ALLY -> currentTeamEntities=allies;
            case ENEMY -> currentTeamEntities=enemies;
            default -> currentTeamEntities=new Entity[0];
        }
        return currentTeamEntities;
    }
    private void parseAction(Entity choosenTarget, Action choosenAction, Entity player) {
        // this methode is used to parse what the action does, in case of an attack starts the damage calculation method
        // and does the damage to the target
        EquipmentItem equipmentItem =player.equipment.getEquipmentwithAction(choosenAction);
        switch(choosenAction.actionType){
            case ATTACK -> attackAction(choosenTarget,choosenAction,player, equipmentItem);
            case HEAL -> healingAction(choosenTarget,choosenAction,player, equipmentItem);
            case DEFEND -> defendAction(choosenAction,player, equipmentItem);
        }
    }
    private void healingAction(Entity choosenTarget, Action choosenAction, Entity player, EquipmentItem equipment){

    }
    private void attackAction(Entity choosenTarget, Action choosenAction, Entity player, EquipmentItem equipment){
        final double damage=DamageCalculation.calculateDamage(choosenAction,choosenTarget,player,equipment);
    }
    private void defendAction(Action choosenAction, Entity player, EquipmentItem equipment){

    }
    private boolean loseCondition(){
        return player.hp.alive();
    }
    private boolean winCondition(){
        long numberAlive=Arrays.stream(enemies).filter(e -> e.hp.alive() ).count();
        if (numberAlive > 0){
            return false;
        }else{
            return true;
        }

    }
    private Team startingTeam(){
        return Team.PLAYER;
    }
}
