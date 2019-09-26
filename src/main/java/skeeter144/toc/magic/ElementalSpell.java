package skeeter144.toc.magic;

public abstract class ElementalSpell extends ShootableSpell{
	
	protected int damage;
	public ElementalSpell(String name, String iconName, int damage, int cooldown, int trailId) {
		super(name, iconName, cooldown, trailId);
		this.damage = damage;
	}


	
}
