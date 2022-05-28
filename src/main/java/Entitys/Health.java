package Entitys;

public class Health {
    private double hp_max;
	private final double hp_base;
    private double hp_cur;
	private final static double hp_increase_Rate=5;
    public Health(double hp_base ,double con) {
        this.hp_base = hp_base;
		this.calculateNewMaxHP(con);
    }

    public Health(double hp_base, double hp_cur,double con) {
        this.hp_base = hp_base;
        this.hp_cur = hp_cur;
	    this.calculateNewMaxHP(con);
    }

    public double getHp() {
        return hp_cur;
    }

    public void damage(double damageAmount) {

        this.hp_cur -= damageAmount;
    }
	public void calculateNewMaxHP(double con){
		if (con==0){
			con=1;
		}
		this.hp_max=hp_base+ hp_increase_Rate*con;
	}
    public void heal(double healAmount) {

        this.hp_cur += healAmount;
    }

    public boolean alive() {
        if (this.hp_cur > 0.0d) {
            return true;
        } else {
            return false;
        }
    }
}

class IncorrectHealthInput extends Exception {
    public IncorrectHealthInput(String errorMessage) {
        super(errorMessage);
    }
}