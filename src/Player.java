public class Player implements Health {
	private double hp;

	@Override
	public void heal(double amount) {
		hp += amount;
	}

	@Override
	public void hit(double amount) {
		hp -= amount;
	}

	@Override
	public double hp() {
		return hp;
	}
}
