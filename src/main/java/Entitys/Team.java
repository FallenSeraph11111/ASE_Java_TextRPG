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
	    Team nextTeam = switch (currentTeam){
		    case PLAYER -> Team.ENEMY;
		    case ENEMY -> Team.ALLY;
		    case ALLY -> Team.PLAYER;
	    };

		//Team nextTeam=Team.values()[currentTeam.ID+1%(Team.values().length)]; //this dosn't work i don't get why
	    System.out.println(nextTeam);
        return nextTeam;
    }
}
