package skeeter144.toc.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import skeeter144.toc.util.Reference;

public class BarDisplay extends AbstractHealthDisplay implements ToroHealthDisplay {

	private static final ResourceLocation GUI_BARS_TEXTURES = new ResourceLocation(Reference.MODID, "textures/gui/bars.png");

	private static final int BAR_WIDTH = 92;

	private final Minecraft mc;
	private final Gui gui;
	private int y;
	private int barX;
	private int barY;

	public BarDisplay(Minecraft mc, Gui gui) {
		this.mc = mc;
		this.gui = gui;
	}

	@Override
	public void setPosition(int x, int y) {
		this.y = y;
		barX = x + 4;
		barY = y + 12;
	}

	@Override
	public void draw() {
		if (entity == null) {
			return;
		}
		renderBossHealth();
	}

	public void renderBossHealth() {
		String name = getEntityName();
		String health = (int) Math.ceil(entity.getHealth()) + "/" + (int) entity.getMaxHealth();

		GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.getTextureManager().bindTexture(GUI_BARS_TEXTURES);
		renderHealthBar();
		mc.fontRenderer.drawStringWithShadow(name, barX, y + 2, 16777215);
		mc.fontRenderer.drawStringWithShadow(health, barX, y + 20, 16777215);
	}

	public static enum Color {
		PINK, BLUE, RED, GREEN, YELLOW, PURPLE, WHITE;
	}

	private void renderHealthBar() {
		Color color = determineColor();
		float percent = entity.getHealth() / entity.getMaxHealth();
		gui.drawTexturedModalRect(barX, barY, 0, color.ordinal() * 5 * 2, BAR_WIDTH, 5);
		int healthWidth = (int) (percent * BAR_WIDTH);
		if (healthWidth > 0) {
			gui.drawTexturedModalRect(barX, barY, 0, color.ordinal() * 5 * 2 + 5, healthWidth, 5);
		}
	}

	public static enum Relation {
		FRIEND, FOE, UNKNOWN
	}

	private Color determineColor() {
		switch (determineRelation()) {
		case FOE:
			return Color.RED;
		case FRIEND:
			return Color.GREEN;
		default:
			return Color.WHITE;
		}
	}

}