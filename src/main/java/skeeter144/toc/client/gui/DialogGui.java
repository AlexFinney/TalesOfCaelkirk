package skeeter144.toc.client.gui;

import java.util.ArrayList;
import java.util.List;

import com.mojang.blaze3d.platform.GLX;
import com.mojang.blaze3d.platform.GlStateManager;

import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.button.Button.IPressable;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import skeeter144.toc.network.Network;
import skeeter144.toc.network.QuestDialogResponsePKT;
import skeeter144.toc.quest.NpcDialog;
import skeeter144.toc.util.Reference;

public class DialogGui extends Screen {

	ResourceLocation backgreoundImage = new ResourceLocation(Reference.MODID, "textures/gui/levels_background.png");
	NpcDialog npcDialog;
	int questId, dialogId;
	public LivingEntity e;
	public DialogGui(LivingEntity e, NpcDialog dialog) {
		super(new StringTextComponent("Dialog"));
		this.e = e;
		this.npcDialog = dialog;

		this.dialog = dialog.dialogText;
		
		

	}
	
	boolean buttonsInit = false;
	public void render(int mouseX, int mouseY, float partialTicks) {
		renderBackground();
		drawBookBackground();
		super.render(mouseX, mouseY, partialTicks);
		
		if (e != null) {
			pushEntityRotations();
			renderEntity();
			popEntityRotations();
		}
		drawDialog();
		
		
	}

	private void initButtons() {
		int buttonWidth = (int)(bookWidth * .45f);
		int numButtons = 0;
		int buttonBaseY = bookY + bookHeight - (int)(1.5 * 20);
		
		for (int i = 0; i < npcDialog.playerResponses.size(); ++i) {
			this.buttonTexts[i] = npcDialog.playerResponses.get(i).responseName;
			addButton(new Button(bookX + 9,
					buttonBaseY - i * 20 - i,
					buttonWidth, 20, npcDialog.playerResponses.get(i).responseName, (button) ->  {
						buttonClicked(button);
					}));
			System.out.println("Add Button");
		}
		
		for (int i = 0; i < 5; ++i) {
			if (buttonTexts[i] != null && !buttonTexts[i].equals("")) {
				++numButtons;
			}
		}
		buttonsInit = true;
	}
	
	private void renderEntity() {
		float x = bookX + bookWidth * .73f;
		float y = bookY + bookHeight * .84f;

		int scalef = (int) (bookWidth * .25f);
		GlStateManager.pushMatrix();
		GlStateManager.translatef(x, y, 50.0F);
		GlStateManager.scalef((float) (-scalef), (float) scalef, (float) scalef);
		GlStateManager.rotatef(180.0F, 0.0F, 0.0F, 1.0F);
		GlStateManager.rotatef(65.0F, 0.0F, 1.0F, 0.0F);
		GlStateManager.rotatef(-100.0F, 0.0F, 1.0F, 0.0F);
		GlStateManager.rotatef(0.0f, 1.0F, 0.0F, 0.0F);

		GlStateManager.enableColorMaterial();
		RenderHelper.enableStandardItemLighting();
		GlStateManager.translatef(0.0F, 0.0F, 0.0F);
		EntityRendererManager rendermanager = Minecraft.getInstance().getRenderManager();
		rendermanager.setPlayerViewY(180.0F);
		rendermanager.renderEntity(e, 0.0D, -.3D, 0.0D, 0.0F, 1.0F, false);
		GlStateManager.disableRescaleNormal();
		GlStateManager.popMatrix();
		RenderHelper.disableStandardItemLighting();
		
		RenderHelper.disableStandardItemLighting();
		GlStateManager.disableRescaleNormal();
		GlStateManager.activeTexture(GLX.GL_TEXTURE1);
		GlStateManager.disableTexture();
		GlStateManager.activeTexture(GLX.GL_TEXTURE0);

		FontRenderer fr = Minecraft.getInstance().fontRenderer;
		String name = e.getName().getFormattedText();
		fr.drawString(name, x - fr.getStringWidth(name) / 2, y - e.getHeight() * scalef - fr.FONT_HEIGHT * 1.5f + 25, 0x000000);
	}

	public String dialog = "";

	private void drawDialog() {
		GlStateManager.pushMatrix();
		String[] temp = dialog.split(" ");
		ArrayList<String> tokens = new ArrayList<String>();
		for (String s : temp)
			if (!s.equals(""))
				tokens.add(s);

		float textscalef = .75f;
		float invscalef = 1 / textscalef;
		float maxWidth = bookWidth * .45f;
		GlStateManager.scalef(textscalef, textscalef, 1);

		String curLine = "";
		ArrayList<String> lines = new ArrayList<String>();
		for (String token : tokens) {
			if (bookX + font.getStringWidth(curLine + token + " ") - bookWidth * .17f < bookX + maxWidth) {
				curLine += token + " ";
			} else {
				lines.add(curLine);
				curLine = token + " ";
			}
		}
		lines.add(curLine);

		for (int i = 0; i < lines.size(); ++i) {
			font.drawString(lines.get(i), (bookX + bookWidth * .03f) * invscalef,
					(bookY + bookWidth * .03f + font.FONT_HEIGHT * i) * invscalef, 0x000000);
		}
		GlStateManager.popMatrix();
	}

	public String[] buttonTexts = { "", "", "", "", "" };
	
	@Override
	protected void init() {
		bookWidth = 300;
		bookHeight = 230;
		
		bookX = this.width / 2 - bookWidth / 2;
		bookY = (this.height - bookHeight) / 2;
		
		initButtons();
//		if (numButtons > 0) {
//			Button temp = new Button(0, 0, 0, 0, "", new IPressable() {
//				@Override
//				public void onPress(Button p_onPress_1_) {
//					
//				}
//			});
//		}
//
//		for (int i = 4; i >= 0; --i) {
//			if (buttonTexts[i] != null && !buttonTexts[i].equals("")) {
//
//				Button btn = new Button(bookX + bookWidth / 2 - buttonWidth - 7, buttonBaseY - i * 20 - i, buttonWidth,
//						20, buttonTexts[i], new IPressable() {
//							@Override
//							public void onPress(Button button) {
//								buttonClicked(button);
//							}
//						});
//
//				this.addButton(btn);
//			}
//		}
	}

	protected void buttonClicked(Button btn) {
		for (NpcDialogResponse response : npcDialog.playerResponses) {
			if (response.responseName.equals(btn.getMessage())) {
				if (response.transitionDialog != null)
					Minecraft.getInstance().displayGuiScreen(new DialogGui(this.e, response.transitionDialog));
				else
					Minecraft.getInstance().displayGuiScreen(null);

				if (!response.serverEventFunc.equals(""))
					Network.INSTANCE
							.sendToServer(new QuestDialogResponsePKT(e.getUniqueID(), response.serverEventFunc));
			}

		}

		for (int i = 0; i < 5; ++i) {
			if (buttonTexts[i] != null && buttonTexts[i].equals(btn.getMessage())) {

			}
		}
	}

	@Override
	public void resize(Minecraft p_resize_1_, int p_resize_2_, int p_resize_3_) {
		super.resize(p_resize_1_, p_resize_2_, p_resize_3_);
	}
	
	int bookWidth, bookHeight, bookX, bookY;

	private void drawBookBackground() {
		GlStateManager.pushMatrix();
		GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);

		TextureManager tm = Minecraft.getInstance().getTextureManager();
		

		tm.bindTexture(backgreoundImage);

		

		blit(bookX, bookY, 0, 0, bookWidth, bookHeight, bookWidth, bookHeight);
		GlStateManager.popMatrix();
	}

	float prevRenderYawOffset;
	float prevRotationYaw;
	float prevRotationPitch;
	float prevRotationYawHead;
	float prevPrevRotationYawHead;

	private void pushEntityRotations() {
		prevRenderYawOffset = e.renderYawOffset;
		prevRotationYaw = e.rotationYaw;
		prevRotationPitch = e.rotationPitch;
		prevRotationYawHead = e.rotationYawHead;
		prevPrevRotationYawHead = e.prevRotationYawHead;

		e.renderYawOffset = 0;
		e.rotationYaw = 0;
		e.rotationPitch = 0;
		e.rotationYawHead = 0;
		e.prevRotationYawHead = 0;
	}

	private void popEntityRotations() {
		e.renderYawOffset = prevRenderYawOffset;
		e.rotationYaw = prevRotationYaw;
		e.rotationPitch = prevRotationPitch;
		e.rotationYawHead = prevRotationYawHead;
		e.prevRotationYawHead = prevPrevRotationYawHead;
	}

	@Override
	public boolean isPauseScreen() {
		return false;
	}
}
