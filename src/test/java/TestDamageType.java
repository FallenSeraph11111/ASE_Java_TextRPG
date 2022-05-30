import items.DamageType;
import org.junit.Assert;
import org.junit.Test;

public class TestDamageType {
	@Test
	public void getDamageTypePerName(){
		assert(DamageType.getByName("basic")).equals(DamageType.BASIC);
		Assert.assertNotEquals(DamageType.getByName("test"),DamageType.BLEEDING);
	}
	@Test
	public void getDamageTypeByValueOf(){
		assert(DamageType.BLEEDING).equals(DamageType.valueOf("BLEEDING"));
	}
}
