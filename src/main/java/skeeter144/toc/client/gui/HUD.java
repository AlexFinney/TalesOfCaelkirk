package skeeter144.toc.client.gui;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import skeeter144.toc.TOCMain;
import skeeter144.toc.items.weapons.ISpecialAttackWeapon;
import skeeter144.toc.player.Level;
import skeeter144.toc.player.TOCPlayer;
import skeeter144.toc.tasks.TickableTask;
import skeeter144.toc.util.Reference;

public class HUD extends GuiIngame {

	public HUD(Minecraft mcIn) {
		super(mcIn);
		
	}

	final ResourceLocation emptyBarMana = new ResourceLocation(Reference.MODID, "textures/gui/empty_bar_mana.png");
	final ResourceLocation emptyBarHealth = new ResourceLocation(Reference.MODID, "textures/gui/empty_bar_health.png");
	final ResourceLocation healthBar = new ResourceLocation(Reference.MODID, "textures/gui/health_bar.png");
	final ResourceLocation manaBar = new ResourceLocation(Reference.MODID, "textures/gui/mana_bar.png");

	final int barWidth = 182;
	final int barHeight = 20;
	final int barEndcapWidth = 20;

	public static List<XpMessage> xpMessages = new ArrayList<XpMessage>();
	public static List<String> activeEffects = new ArrayList<String>();

	public static float lightBlockedPct = 0;

	static boolean wasHoldingTorch = false;
	static float fogR = 0, fogG = 0, fogB = 0, fogStart = 5, fogEnd = 15;

	float offset = 0;

	@SubscribeEvent
	public void dizzy(EntityViewRenderEvent.CameraSetup event) {
		double ticks = event.getEntity().ticksExisted + event.getRenderPartialTicks();

		offset = (float) Math.sin(ticks / 15) * 25;

		float vert = (float) Math.sin(ticks / 20) * 40;
		boolean drunk = false;
		if(drunk) {
			 event.setRoll(event.getRoll() + offset);
			 event.setPitch(event.getPitch() + vert / 3);
			 event.setYaw(event.getYaw() + (float) (Math.sin(ticks / 40) * 3) +
			 event.getPitch() / 10);
		}
		
	}

	@SubscribeEvent
	public void fogRenderEvent(EntityViewRenderEvent.RenderFogEvent event) {
		if (activeEffects.contains("reduced_vision")) {
			ItemStack torch = new ItemStack(Blocks.TORCH);
			if (Minecraft.getInstance().player.getHeldItemMainhand().getItem().equals(torch.getItem())
					|| Minecraft.getInstance().player.getHeldItemOffhand().getItem().equals(torch.getItem())) {

				if (!wasHoldingTorch) {
					float startR = fogR, startG = fogG, startB = fogB, startStart = fogStart, startEnd = fogEnd;
					float endR = .5f, endG = 0f, endB = 0f, endStart = 5, endEnd = 70;
					TOCMain.clientTaskManager.addTask(new TickableTask(40) {
						public void tick(int worldTick) {
							float delta = worldTick - start;
							float fadeTime = 40;

							fogR = startR + (endR - startR) * delta / fadeTime;
							fogG = startG + (endG - startG) * delta / fadeTime;
							fogB = startB + (endB - startB) * delta / fadeTime;
							fogStart = startStart + (endStart - startStart) * delta / fadeTime;
							fogEnd = startEnd + (endEnd - startEnd) * delta / fadeTime;
							// HUD.lightBlockedPct = light;
						}
					});
					wasHoldingTorch = true;
				}
			} else {
				if (wasHoldingTorch) {
					float startR = fogR, startG = fogG, startB = fogB, startStart = fogStart, startEnd = fogEnd;
					float endR = .1f, endG = 0f, endB = 0f, endStart = 3, endEnd = 10;
					Minecraft.getInstance().addScheduledTask(new Runnable() {
						public void run() {
							TOCMain.clientTaskManager.addTask(new TickableTask(40) {
								public void tick(int worldTick) {
									float delta = worldTick - start;
									float fadeTime = 40;

									fogR = startR + (endR - startR) * delta / fadeTime;
									fogG = startG + (endG - startG) * delta / fadeTime;
									fogB = startB + (endB - startB) * delta / fadeTime;
									fogStart = startStart + (endStart - startStart) * delta / fadeTime;
									fogEnd = startEnd + (endEnd - startEnd) * delta / fadeTime;
									// HUD.lightBlockedPct = light;
								}
							});
						}
					});

					wasHoldingTorch = false;
				}
			}
			GlStateManager.fogStart(fogStart);
			GlStateManager.fogEnd(fogEnd);
		}
	}

	public void renderFog() {
		if (activeEffects.contains("reduced_vision")) {
			//event.setRed(fogR);
			//event.setBlue(fogG);
			//event.setGreen(fogB);
		}
	}

	@SubscribeEvent
	public void onRenderExperienceBar(RenderGameOverlayEvent.Pre event) {
		if (event.getType() != ElementType.EXPERIENCE) {
			return;
		}
		drawLightBlock();
	}

  @Override
  public void renderGameOverlay(float partialTicks) {
	  System.out.println("render");
  }
	
	public void renderExpBar(int x) {
		TOCPlayer pl = TOCMain.localPlayer;
		if (pl == null)
			return;

		if(Minecraft.getInstance().player.isCreative())
			return;
		
		drawHealthBar(pl.getHealth(), pl.getMaxHealth());
		drawManaBar(pl.getMana(), pl.getMaxMana());
		drawXpMessages();
		drawEffectIcons();
	}

	void drawLightBlock() {
		TextureManager tm = Minecraft.getInstance().getTextureManager();
		MainWindow sr = Minecraft.getInstance().mainWindow;
		int screenWidth = sr.getScaledWidth();
		int screenHeight = sr.getScaledHeight();
		Byte light = new Byte((byte) (lightBlockedPct * 255));
		drawRect(0, 0, screenWidth, screenHeight, light << 24);
	}

	void drawEffectIcons() {
		if (activeEffects.size() > 0) {
			GlStateManager.pushMatrix();
			GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);

			TextureManager tm = Minecraft.getInstance().getTextureManager();
			MainWindow sr = Minecraft.getInstance().mainWindow;

			int screenWidth = sr.getScaledWidth();
			int screenHeight = sr.getScaledHeight();

			int y = screenHeight - 87;
			int iconSize = (int) screenWidth / 20;
			for (int i = 0; i < activeEffects.size(); ++i) {
				int iconX = (int) (screenWidth / 2 + .05f * screenWidth * i - screenWidth / 50);
				ResourceLocation icon = new ResourceLocation(Reference.MODID,
						"textures/icons/effects/" + activeEffects.get(i) + ".png");
				tm.bindTexture(icon);
				drawScaledCustomSizeModalRect((int) (iconX - iconSize / 2f), (int) (y), 0, 0, iconSize, iconSize,
						iconSize, iconSize, iconSize, iconSize);
			}

			GlStateManager.popMatrix();
		}
	}

	void drawHealthBar(int curHealth, int maxhealth) {
		float scale = curHealth / (float) maxhealth;
		GlStateManager.pushMatrix();
		GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);

		TextureManager tm = Minecraft.getInstance().getTextureManager();
		MainWindow sr = Minecraft.getInstance().mainWindow;
		
		int screenWidth = sr.getScaledWidth();
		int screenHeight = sr.getScaledHeight();

		int barX = screenWidth / 2 - barWidth / 2;
		int barY = (screenHeight - 41 - barHeight);

		drawRect(barX, barY, barX + barWidth, barY + barHeight, 0xFF000000);
		GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		tm.bindTexture(healthBar);
		drawScaledCustomSizeModalRect(barX, barY, 0, 0, barWidth, barHeight, (int) (barWidth * scale), barHeight,
				barWidth, barHeight);
		tm.bindTexture(emptyBarHealth);
		drawModalRectWithCustomSizedTexture(barX, barY, 0, 0, barWidth, barHeight, barWidth, barHeight);

		String s = curHealth + "/" + maxhealth;
		drawString(Minecraft.getInstance().fontRenderer, s, barX + barWidth / 2 - 20, barY + barHeight / 2 - 4,
				0xFFFFFF);

		GlStateManager.scaled(.75, .75, .75);
		drawString(Minecraft.getInstance().fontRenderer, String.format("%.2f", TOCMain.localPlayer.getHealthRegen()),
				(int) ((barX + barWidth - 23) * 1.333f), (int) ((barY + barHeight / 2 - 3) * 1.333f), 0xFFFFFF);
		GlStateManager.scalef(1.333f, 1.333f, 1.333f);

		GlStateManager.enableRescaleNormal();
		GlStateManager.enableBlend();
		GlStateManager.popAttrib();

		if (((EntityPlayer) TOCMain.localPlayer.mcEntity).getHeldItem(EnumHand.MAIN_HAND)
				.getItem() instanceof ISpecialAttackWeapon) {
			GlStateManager.pushMatrix();
			GlStateManager.scaled(.25, .25, .25);

			scale = .5f;
			String name = ((EntityPlayer) TOCMain.localPlayer.mcEntity).getHeldItem(EnumHand.MAIN_HAND).getItem()
					.getRegistryName().toString();
			Pair p = TOCMain.localPlayer.specialAttackCooldowns.get(name);

			if (p != null) {
				scale = 1 - (float) (Integer) p.getLeft() / (float) (Integer) p.getRight();
			} else {
				scale = 1;
			}

			tm.bindTexture(healthBar);
			drawScaledCustomSizeModalRect(barX * 4 + barWidth * 4 - barWidth, barY * 4 - barHeight, 0, 0, barWidth,
					barHeight, (int) (barWidth * scale), barHeight, barWidth, barHeight);

			if (scale == 1) {
				drawScaledCustomSizeModalRect(barX * 4 + (int) (barWidth * 4.05f), barY * 4 - barHeight, 0, 0, barWidth,
						barHeight, (int) (barWidth * .1f), barHeight, barWidth, barHeight);
			}

			tm.bindTexture(emptyBarHealth);
			drawModalRectWithCustomSizedTexture(barX * 4 + barWidth * 4 - barWidth, barY * 4 - barHeight, 0, 0,
					barWidth, barHeight, barWidth, barHeight);

			GlStateManager.popMatrix();
		}
	}

	void drawManaBar(int curMana, int maxMana) {
		float scale = curMana / (float) maxMana;
		GlStateManager.pushMatrix();
		GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);

		TextureManager tm = Minecraft.getInstance().getTextureManager();
		MainWindow sr = Minecraft.getInstance().mainWindow;

		int screenWidth = sr.getScaledWidth();
		int screenHeight = sr.getScaledHeight();

		int barX = screenWidth / 2 - barWidth / 2;
		int barY = (screenHeight - 41);

		drawRect(barX, barY, barX + barWidth, barY + barHeight, 0xFF000000);
		GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		tm.bindTexture(manaBar);
		drawScaledCustomSizeModalRect(barX, barY, 0, 0, barWidth, barHeight, (int) (barWidth * scale), barHeight,
				barWidth, barHeight);
		tm.bindTexture(emptyBarMana);
		drawModalRectWithCustomSizedTexture(barX, barY, 0, 0, barWidth, barHeight, barWidth, barHeight);

		String s = curMana + "/" + maxMana;
		int stringWidth = Minecraft.getInstance().fontRenderer.getStringWidth(s);
		drawString(Minecraft.getInstance().fontRenderer, s, barX + barWidth / 2 - 20, barY + barHeight / 2 - 4,
				0xFFFFFF);

		GlStateManager.scaled(.75, .75, .75);
		drawString(Minecraft.getInstance().fontRenderer, String.format("%.2f", TOCMain.localPlayer.getManaRegen()),
				(int) ((barX + barWidth - 23) * 1.333f), (int) ((barY + barHeight / 2 - 2) * 1.333f), 0xFFFFFF);
		GlStateManager.scalef(1.333f, 1.333f, 1.333f);

		GlStateManager.enableRescaleNormal();
		GlStateManager.enableBlend();
		GlStateManager.popAttrib();
	}

	float xpMessageSpeed = -1;

	void drawXpMessages() {
		MainWindow sr = Minecraft.getInstance().mainWindow;
		int screenWidth = sr.getScaledWidth();
		int screenHeight = sr.getScaledHeight();

		int iconDim = (int) (screenWidth / 40);

		FontRenderer fr = Minecraft.getInstance().fontRenderer;
		TextureManager tm = Minecraft.getInstance().getTextureManager();
		for (XpMessage msg : xpMessages) {
			fr.drawString(msg.string, msg.x, msg.y, 0x000000);
			tm.bindTexture(new ResourceLocation(msg.icon));
			GlStateManager.color3f(1f, 1f, 1f);
			drawModalRectWithCustomSizedTexture(msg.x - iconDim, (int) (msg.y - iconDim / 4f), iconDim, iconDim,
					iconDim, iconDim, iconDim, iconDim);
			msg.y += xpMessageSpeed;
		}

	}

	public static void addNewXpMessage(Level level, int amount) {
		MainWindow sr = Minecraft.getInstance().mainWindow;
		int screenWidth = sr.getScaledWidth();
		int screenHeight = sr.getScaledHeight();

		int vertGap = screenHeight / 20;
		int startingY = screenHeight / 2;

		int bottomY = 0;
		if (xpMessages.size() > 0) {
			bottomY = xpMessages.get(xpMessages.size() - 1).y;
		}

		if (bottomY + vertGap > startingY)
			startingY = bottomY + vertGap;

		XpMessage msg = new XpMessage();
		msg.string = "xp: " + amount;
		msg.icon = level.iconName;
		msg.x = screenWidth - screenWidth / 6;
		msg.y = startingY;
		xpMessages.add(msg);
	}
}

class XpMessage {
	public String string;
	public String icon;
	public int x;
	public int y;

	public XpMessage() {
	}

}
