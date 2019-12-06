package skeeter144.toc.client.gui;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import com.mojang.blaze3d.platform.GlStateManager;

import net.minecraft.block.Blocks;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.IngameGui;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effects;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.GameType;
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

public class HUD extends IngameGui {

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
		double ticks = Minecraft.getInstance().player.ticksExisted + event.getRenderPartialTicks();

		offset = (float) Math.sin(ticks / 15) * 25;

		float vert = (float) Math.sin(ticks / 20) * 40;
		boolean drunk = false;
		if (drunk) {
			event.setRoll(event.getRoll() + offset);
			event.setPitch(event.getPitch() + vert / 3);
			event.setYaw(event.getYaw() + (float) (Math.sin(ticks / 40) * 3) + event.getPitch() / 10);
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
					Minecraft.getInstance().deferTask(new Runnable() {
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

	@SubscribeEvent
	public void fogRenderEvent(EntityViewRenderEvent.FogColors event) {
		if (activeEffects.contains("reduced_vision")) {
			event.setRed(fogR);
			event.setBlue(fogG);
			event.setGreen(fogB);
		}
	}

	@SubscribeEvent
	public void onRenderExperienceBar(RenderGameOverlayEvent.Pre event) {
		if (event.getType() != ElementType.EXPERIENCE) {
			return;
		}
		drawLightBlock();
	}

	public void renderGameOverlay(float partialTicks) {
		renderVanillaModified(partialTicks);

		if (!Minecraft.getInstance().player.isCreative()) {
			TOCPlayer pl = TOCMain.localPlayer;
			if (pl != null) {
				drawHealthBar(pl.getHealth(), pl.getMaxHealth());
				drawManaBar(pl.getMana(), pl.getMaxMana());
			}
		}

		drawXpMessages();
		drawEffectIcons();

		renderChat();
	}

	void renderChat() {
		GlStateManager.enableBlend();
		GlStateManager.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA,
				GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE,
				GlStateManager.DestFactor.ZERO);
		GlStateManager.disableAlphaTest();
		GlStateManager.pushMatrix();
		GlStateManager.translatef(0.0F, (float) (this.scaledHeight - 48), 0.0F);
		this.mc.getProfiler().startSection("chat");
		this.persistantChatGUI.render(this.ticks);
		this.mc.getProfiler().endSection();
		GlStateManager.popMatrix();
	}

	Method renderPlayerStats = null;
	Method renderVehicleHealth = null;

	
	void drawLightBlock() {
		TextureManager tm = Minecraft.getInstance().getTextureManager();
		MainWindow sr = Minecraft.getInstance().mainWindow;
		int screenWidth = sr.getScaledWidth();
		int screenHeight = sr.getScaledHeight();
		Byte light = new Byte((byte) (lightBlockedPct * 255));
		fill(0, 0, screenWidth, screenHeight, light << 24);
	}

	void drawEffectIcons() {
		if (activeEffects.size() > 0) {
			GlStateManager.pushMatrix();
			GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);

			TextureManager tm = Minecraft.getInstance().getTextureManager();
			MainWindow sr = Minecraft.getInstance().mainWindow;

			int screenWidth = sr.getScaledWidth();
			int screenHeight = sr.getScaledHeight();

			int y = screenHeight - 80;
			int iconSize = (int) screenWidth / 20;
			for (int i = 0; i < activeEffects.size(); ++i) {
				int iconX = (int) (screenWidth / 2 + .05f * screenWidth * i - screenWidth / 50);
				ResourceLocation icon = new ResourceLocation(Reference.MODID,
						"textures/icons/effects/" + activeEffects.get(i).toLowerCase() + ".png");
				tm.bindTexture(icon);
				iconSize = 16;
				blit((int) (iconX - iconSize / 2f), (int) (y), 0, 0, iconSize, iconSize, iconSize, iconSize);
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

		fill(barX, barY, barX + barWidth, barY + barHeight, 0xFF000000);
		GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		tm.bindTexture(healthBar);
		blit(barX, barY, 0, 0, (int) (barWidth * scale), barHeight, barWidth , barHeight);
		tm.bindTexture(emptyBarHealth);
		blit(barX, barY, 0, 0, barWidth, barHeight, barWidth, barHeight);

		String s = curHealth + "/" + maxhealth;
		drawString(Minecraft.getInstance().fontRenderer, s, barX + barWidth / 2 - 20, barY + barHeight / 2 - 4,
				0xFFFFFF);

		GlStateManager.scaled(.75, .75, .75);
		drawString(Minecraft.getInstance().fontRenderer, String.format("%.2f", TOCMain.localPlayer.getHealthRegen()),
				(int) ((barX + barWidth - 23) * 1.333f), (int) ((barY + barHeight / 2 - 3) * 1.333f), 0xFFFFFF);
		GlStateManager.scalef(1.333f, 1.333f, 1.333f);

		GlStateManager.enableRescaleNormal();
		GlStateManager.enableBlend();
		GlStateManager.popMatrix();

		if (((PlayerEntity) TOCMain.localPlayer.mcEntity).getHeldItem(Hand.MAIN_HAND)
				.getItem() instanceof ISpecialAttackWeapon) {
			GlStateManager.pushMatrix();
			GlStateManager.scaled(.25, .25, .25);

			scale = .5f;
			String name = ((PlayerEntity) TOCMain.localPlayer.mcEntity).getHeldItem(Hand.MAIN_HAND).getItem()
					.getRegistryName().toString();
			Pair p = TOCMain.localPlayer.specialAttackCooldowns.get(name);

			if (p != null) {
				scale = 1 - (float) (Integer) p.getLeft() / (float) (Integer) p.getRight();
			} else {
				scale = 1;
			}
			
			GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
			tm.bindTexture(healthBar);
			blit(barX * 4 + barWidth * 4 - barWidth, barY * 4 - barHeight, 0, 0, (int) (barWidth * scale), barHeight,
					barWidth, barHeight);

			tm.bindTexture(emptyBarHealth);
			blit(barX * 4 + barWidth * 4 - barWidth, barY * 4 - barHeight, 0, 0, barWidth, barHeight, barWidth, barHeight);

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

		fill(barX, barY, barX + barWidth, barY + barHeight, 0xFF000000);
		GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		tm.bindTexture(manaBar);
		blit(barX, barY, 0, 0, barWidth, barHeight, (int) (barWidth * scale), barHeight);
		tm.bindTexture(emptyBarMana);
		blit(barX, barY, 0, 0, barWidth, barHeight, barWidth, barHeight);

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
		GlStateManager.popMatrix();
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
			blit(msg.x - iconDim, (int) (msg.y - iconDim / 4f), iconDim, iconDim, iconDim, iconDim, iconDim, iconDim);
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
	
	void renderVanillaModified(float partialTicks) {
		// super.renderGameOverlay(partialTicks);
		this.scaledWidth = this.mc.mainWindow.getScaledWidth();
		this.scaledHeight = this.mc.mainWindow.getScaledHeight();
		FontRenderer fontrenderer = this.getFontRenderer();
		GlStateManager.enableBlend();
		if (Minecraft.isFancyGraphicsEnabled()) {
			renderVignette(mc.getRenderViewEntity());
		} else {
			GlStateManager.enableDepthTest();
			GlStateManager.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA,
					GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE,
					GlStateManager.DestFactor.ZERO);
		}

		ItemStack itemstack = this.mc.player.inventory.armorItemInSlot(3);
		if (this.mc.gameSettings.thirdPersonView == 0 && itemstack.getItem() == Blocks.CARVED_PUMPKIN.asItem()) {
			this.renderPumpkinOverlay();
		}

		if (!this.mc.player.isPotionActive(Effects.NAUSEA)) {
			float f = MathHelper.lerp(partialTicks, this.mc.player.prevTimeInPortal, this.mc.player.timeInPortal);
			if (f > 0.0F) {
				this.renderPortal(f);
			}
		}

		if (this.mc.playerController.getCurrentGameType() == GameType.SPECTATOR) {
			this.spectatorGui.renderTooltip(partialTicks);
		} else if (!this.mc.gameSettings.hideGUI) {
			this.renderHotbar(partialTicks);
		}

		if (!this.mc.gameSettings.hideGUI) {
			GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
			this.mc.getTextureManager().bindTexture(GUI_ICONS_LOCATION);
			GlStateManager.enableBlend();
			GlStateManager.enableAlphaTest();
			this.renderAttackIndicator();
			GlStateManager.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA,
					GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE,
					GlStateManager.DestFactor.ZERO);
			this.mc.getProfiler().startSection("bossHealth");
			this.overlayBoss.render();
			this.mc.getProfiler().endSection();
			GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
			this.mc.getTextureManager().bindTexture(GUI_ICONS_LOCATION);
			if (this.mc.playerController.shouldDrawHUD()) {
				if (renderPlayerStats == null) {
					try {
						renderPlayerStats = IngameGui.class.getDeclaredMethod("renderPlayerStats");
						renderPlayerStats.setAccessible(true);
						renderVehicleHealth = IngameGui.class.getDeclaredMethod("renderVehicleHealth");
						renderVehicleHealth.setAccessible(true);
					} catch (Exception e) {
						TOCMain.LOGGER.error(e.getLocalizedMessage());
					}
				} else {
					try {
						renderPlayerStats.invoke(this);
						renderVehicleHealth.invoke(this);
					} catch (Exception e) {

					}
				}
			}

			GlStateManager.disableBlend();
			int k = this.scaledWidth / 2 - 91;
			if (this.mc.player.isRidingHorse()) {
				this.renderHorseJumpBar(k);
			} else if (this.mc.playerController.gameIsSurvivalOrAdventure()) {
				this.renderExpBar(k);
			}

			if (this.mc.gameSettings.heldItemTooltips
					&& this.mc.playerController.getCurrentGameType() != GameType.SPECTATOR) {
				this.renderSelectedItem();
			} else if (this.mc.player.isSpectator()) {
				this.spectatorGui.renderSelectedItem();
			}

			this.renderPotionEffects();
			if (this.mc.gameSettings.showDebugInfo) {
				this.overlayDebug.render();
			}

			if (this.mc.gameSettings.heldItemTooltips
					&& this.mc.playerController.getCurrentGameType() != GameType.SPECTATOR) {
				this.renderSelectedItem();
			} else if (this.mc.player.isSpectator()) {
				this.spectatorGui.renderSelectedItem();
			}
		}

		if (this.mc.gameSettings.showDebugInfo) {
			this.overlayDebug.render();
		}

		if (!this.mc.gameSettings.hideGUI) {
			if (this.overlayMessageTime > 0) {
				this.mc.getProfiler().startSection("overlayMessage");
				float f3 = (float) this.overlayMessageTime - partialTicks;
				int i1 = (int) (f3 * 255.0F / 20.0F);
				if (i1 > 255) {
					i1 = 255;
				}

				if (i1 > 8) {
					GlStateManager.pushMatrix();
					GlStateManager.translatef((float) (this.scaledWidth / 2), (float) (this.scaledHeight - 68), 0.0F);
					GlStateManager.enableBlend();
					GlStateManager.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA,
							GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE,
							GlStateManager.DestFactor.ZERO);
					int k1 = 16777215;
					if (this.animateOverlayMessageColor) {
						k1 = MathHelper.hsvToRGB(f3 / 50.0F, 0.7F, 0.6F) & 16777215;
					}

					int j = i1 << 24 & -16777216;
					this.func_212909_a(fontrenderer, -4, fontrenderer.getStringWidth(this.overlayMessage));
					fontrenderer.drawString(this.overlayMessage,
							(float) (-fontrenderer.getStringWidth(this.overlayMessage) / 2), -4.0F, k1 | j);
					GlStateManager.disableBlend();
					GlStateManager.popMatrix();
				}

				this.mc.getProfiler().endSection();
			}

			if (this.titlesTimer > 0) {
				this.mc.getProfiler().startSection("titleAndSubtitle");
				float f4 = (float) this.titlesTimer - partialTicks;
				int j1 = 255;
				if (this.titlesTimer > this.titleFadeOut + this.titleDisplayTime) {
					float f5 = (float) (this.titleFadeIn + this.titleDisplayTime + this.titleFadeOut) - f4;
					j1 = (int) (f5 * 255.0F / (float) this.titleFadeIn);
				}

				if (this.titlesTimer <= this.titleFadeOut) {
					j1 = (int) (f4 * 255.0F / (float) this.titleFadeOut);
				}

				j1 = MathHelper.clamp(j1, 0, 255);
				if (j1 > 8) {
					GlStateManager.pushMatrix();
					GlStateManager.translatef((float) (this.scaledWidth / 2), (float) (this.scaledHeight / 2), 0.0F);
					GlStateManager.enableBlend();
					GlStateManager.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA,
							GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE,
							GlStateManager.DestFactor.ZERO);
					GlStateManager.pushMatrix();
					GlStateManager.scalef(4.0F, 4.0F, 4.0F);
					int l1 = j1 << 24 & -16777216;
					int i2 = fontrenderer.getStringWidth(this.displayedTitle);
					this.func_212909_a(fontrenderer, -10, i2);
					fontrenderer.drawStringWithShadow(this.displayedTitle, (float) (-i2 / 2), -10.0F, 16777215 | l1);
					GlStateManager.popMatrix();
					if (!this.displayedSubTitle.isEmpty()) {
						GlStateManager.pushMatrix();
						GlStateManager.scalef(2.0F, 2.0F, 2.0F);
						int k = fontrenderer.getStringWidth(this.displayedSubTitle);
						this.func_212909_a(fontrenderer, 5, k);
						fontrenderer.drawStringWithShadow(this.displayedSubTitle, (float) (-k / 2), 5.0F,
								16777215 | l1);
						GlStateManager.popMatrix();
					}

					GlStateManager.disableBlend();
					GlStateManager.popMatrix();
				}

				this.mc.getProfiler().endSection();
			}

			this.overlaySubtitle.render();
			Scoreboard scoreboard = this.mc.world.getScoreboard();
			ScoreObjective scoreobjective = null;
			ScorePlayerTeam scoreplayerteam = scoreboard.getPlayersTeam(this.mc.player.getScoreboardName());
			if (scoreplayerteam != null) {
				int j2 = scoreplayerteam.getColor().getColorIndex();
				if (j2 >= 0) {
					scoreobjective = scoreboard.getObjectiveInDisplaySlot(3 + j2);
				}
			}

			ScoreObjective scoreobjective1 = scoreobjective != null ? scoreobjective
					: scoreboard.getObjectiveInDisplaySlot(1);
			if (scoreobjective1 != null) {
				this.renderScoreboard(scoreobjective1);
			}

			//Vanilla chat render here
			
			scoreobjective1 = scoreboard.getObjectiveInDisplaySlot(0);
			if (!this.mc.gameSettings.keyBindPlayerList.isKeyDown() || this.mc.isIntegratedServerRunning()
					&& this.mc.player.connection.getPlayerInfoMap().size() <= 1 && scoreobjective1 == null) {
				this.overlayPlayerList.setVisible(false);
			} else {
				this.overlayPlayerList.setVisible(true);
				this.overlayPlayerList.render(this.scaledWidth, scoreboard, scoreobjective1);
			}
		}

		GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		GlStateManager.disableLighting();
		GlStateManager.enableAlphaTest();
	}

	// Copied from IngameGui
	private void func_212909_a(FontRenderer p_212909_1_, int p_212909_2_, int p_212909_3_) {
		int i = this.mc.gameSettings.func_216841_b(0.0F);
		if (i != 0) {
			int j = -p_212909_3_ / 2;
			fill(j - 2, p_212909_2_ - 2, j + p_212909_3_ + 2, p_212909_2_ + 9 + 2, i);
		}

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
