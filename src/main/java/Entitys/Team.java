package Entitys;

public enum Team {
    PLAYER(0),
    ENEMY(1),
    ALLY(2);
    public int ID;
    Team(int ID){
        this.ID=ID;
    }
    public static Team nextTeam(Team currentTeam){
        return Team.values()[Math.floorMod(currentTeam.ID+1,Team.values().length)];
    }
}
