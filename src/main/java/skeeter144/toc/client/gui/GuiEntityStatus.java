package skeeter144.toc.client.gui;

import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class GuiEntityStatus extends Screen {

	private static final int PADDING_FROM_EDGE = 3;
	//private static final ResourceLocation SKIN_BASIC = new ResourceLocation(ToroHealthMod.MODID, "textures/gui/default_skin_basic.png");
	//private static final ResourceLocation SKIN_HEAVY = new ResourceLocation(ToroHealthMod.MODID, "textures/gui/default_skin_heavy.png");

	private final Minecraft mc;
	private final ToroHealthDisplay entityDisplay;
	private final ToroHealthDisplay barDisplay;

	private LivingEntity entity;
	private int age = 0;
	private boolean showHealthBar = false;

	int screenX = PADDING_FROM_EDGE;
	int screenY = PADDING_FROM_EDGE;

	int displayHeight;
	int displayWidth;

	int x, y;

	
	public GuiEntityStatus() {
		this(Minecraft.getInstance());
	}

	public GuiEntityStatus(Minecraft mc) {
		super(new StringTextComponent(""));
		this.mc = mc;
		entityDisplay = new EntityDisplay(mc);
	///	heartsDisplay = new HeartsDisplay(mc, this);
	//	numericDisplay = new NumericDisplay(mc, this);
		barDisplay = new BarDisplay(mc, this);

		entityDisplay.setPosition(50, 50);
	//	heartsDisplay.setPosition(25, 150);
	//	numericDisplay.setPosition(130, 150);
	//	barDisplay.setPosition(25, 200);
	}

	@SubscribeEvent
	public void drawHealthBar(RenderGameOverlayEvent.Pre event) {
		if (!showHealthBar || event.getType() != ElementType.CHAT) {
			return;
		}
	//	updateGuiAge();
		updatePositions();
//		drawSkin();
		draw();
	}

/*	private void drawSkin() {
		if (ConfigurationHandler.skin.equals("NONE")) {
			return;
		}
		
		if (ConfigurationHandler.skin.equals("HEAVY")) {
	//		mc.getTextureManager().bindTexture(SKIN_HEAVY);
		}else{
//			mc.getTextureManager().bindTexture(SKIN_BASIC);
		}
		
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		Gui.blit(screenX - 10, screenY - 10, 0.0f, 0.0f, 160, 60, 160, 60);
	}

	private void updateGuiAge() {
		String entityStatusDisplay = ConfigurationHandler.entityStatusDisplay;
		age = age + 15;
		if (age > ConfigurationHandler.hideDelay || entityStatusDisplay.equals("OFF")) {
			hideHealthBar();
		}
	}
*/
	private void updatePositions() {
		adjustForDisplayPositionSetting();

		x = screenX;
		y = screenY;

	//	if (ConfigurationHandler.showEntityModel) {
			entityDisplay.setPosition(x, y);
			x += 40;
	//	}

	//	if (ConfigurationHandler.statusDisplayPosition.contains("BOTTOM")) {
			y += 6;
	//	}

//		numericDisplay.setPosition(x, y);
		barDisplay.setPosition(x, y);
//		heartsDisplay.setPosition(x, y);
	}

	private void draw() {
//		if (ConfigurationHandler.showEntityModel) {
			entityDisplay.draw();
//		}

//		if ("NUMERIC".equals(ConfigurationHandler.entityStatusDisplay)) {
//			numericDisplay.draw();
//		} else if ("BAR".equals(ConfigurationHandler.entityStatusDisplay)) {
			barDisplay.draw();
//		} else if ("HEARTS".equals(ConfigurationHandler.entityStatusDisplay)) {
//			heartsDisplay.draw();
//		}
	}

	private void adjustForDisplayPositionSetting() {

	//	if (ConfigurationHandler.showEntityModel) {
			displayHeight = 40;
			displayWidth = 140;
	//	} else {
//			displayHeight = 32;
//			displayWidth = 100;
	//	}

		MainWindow viewport = mc.getMainWindow();
		String displayPosition = "TOPLEFT";//ConfigurationHandler.statusDisplayPosition;

		int sh = viewport.getScaledHeight();
		int sw = viewport.getScaledWidth();

		if (displayPosition.contains("TOP") || displayPosition.equals("CUSTOM")) {
			screenY = PADDING_FROM_EDGE;
		}

		if (displayPosition.contains("BOTTOM")) {
			screenY = sh - displayHeight - PADDING_FROM_EDGE;
		}

		if (displayPosition.contains("LEFT") || displayPosition.equals("CUSTOM")) {
			screenX = PADDING_FROM_EDGE;
		}

		if (displayPosition.contains("RIGHT")) {
			screenX = sw - displayWidth - PADDING_FROM_EDGE;
		}

		if (displayPosition.contains("CENTER")) {
			screenX = (sw - displayWidth) / 2;
		}

	//	screenX += ConfigurationHandler.statusDisplayX;
	//	screenY += ConfigurationHandler.statusDisplayY;
	}

	private void showHealthBar() {
		showHealthBar = true;
	}

	private void hideHealthBar() {
		showHealthBar = false;
	}

	public void setEntity(LivingEntity entityToTrack) {
		if(entityToTrack == null) {
			entity = null;
			entityDisplay.setEntity(null);
			barDisplay.setEntity(null);
			hideHealthBar();
			return;
		}
		
		showHealthBar();
		age = 0;
		if (entity != null && entity.getUniqueID().equals(entityToTrack.getUniqueID())) {
			return;
		}
		entity = entityToTrack;
		entityDisplay.setEntity(entity);
		barDisplay.setEntity(entity);
	}
}
