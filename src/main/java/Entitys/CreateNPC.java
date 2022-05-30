package Entitys;

public class CreateNPC {
    private String name;
    private Team team;
    private Health hp;
	private double hp_base;
	private int statStr=10,statDex=10,statCon=10,statInt=10,statWis=10,statCha=10;
	private int level=1;
    private Equipment equipment;
    private Stats stats;
    private Double xp;

    private CreateNPC(String name, double hp_base) {
        this.name = name;
        this.hp_base = hp_base;
    }
    public static CreateNPC named(String name, Double hp_base ){

        return new CreateNPC(name,hp_base);
    }
    public CreateableNPC as(Team team){
        this.team = team;
        return  new CreateableNPC();
    }
    public class CreateableNPC{
        private CreateableNPC() {}
        public CreateableNPC withEquipment(Equipment equipment){
            CreateNPC.this.equipment=equipment;
            return this;
        }
        public CreateableNPC withStats(int level,int statStr, int statDex, int statCon, int statInt, int statWis, int statCha){
            CreateNPC.this.stats=new Stats(level, statStr, statDex, statCon, statInt, statWis, statCha);
            return this;
        }
		public NPC build(){
			return CreateNPC.this.build();
		}
    }
    private NPC build(){
		if (this.stats==null){
			this.stats=new Stats(this.level,this.statStr,statDex,statCon,statInt,statWis,statCha);
		}
		if (this.equipment == null){
			this.equipment=new Equipment();
		}
        return new NPC(this.name,new Health(this.hp_base,this.stats.getStat(Stat.CON)),this.stats,this.equipment,this.team,this.xp);
    }
}
