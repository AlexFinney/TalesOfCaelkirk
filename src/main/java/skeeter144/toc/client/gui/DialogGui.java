package skeeter144.toc.client.gui;

import java.util.ArrayList;

import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import skeeter144.toc.network.Network;
import skeeter144.toc.network.QuestDialogResponsePKT;
import skeeter144.toc.quest.NpcDialog;
import skeeter144.toc.util.Reference;

public class DialogGui extends GuiScreen {

	ResourceLocation backgreoundImage = new ResourceLocation(Reference.MODID, "textures/gui/levels_background.png");
	NpcDialog npcDialog;
	int questId, dialogId;
	public EntityLivingBase e;

	public DialogGui(EntityLivingBase e, NpcDialog dialog) {
		super();
		this.e = e;
		this.npcDialog = dialog;

		this.dialog = dialog.dialogText;

		for (int i = 0; i < 5; ++i) {
			this.buttonTexts[i] = "";
		}

		for (int i = 0; i < dialog.playerResponses.size(); ++i) {
			this.buttonTexts[i] = dialog.playerResponses.get(i).responseName;
		}
	}

	public void render(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		drawBookBackground();
		super.render(mouseX, mouseY, partialTicks);
		if (e != null) {
			pushEntityRotations();
			renderEntity();
			popEntityRotations();
		}
		drawDialog();
		if (resized) {
			super.buttons.clear();
			this.initGui();
			resized = false;
		}
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
		RenderManager rendermanager = Minecraft.getInstance().getRenderManager();
		rendermanager.setPlayerViewY(180.0F);
		rendermanager.setRenderShadow(false);
		rendermanager.renderEntity(e, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, false);
		rendermanager.setRenderShadow(true);
		GlStateManager.popMatrix();
		RenderHelper.disableStandardItemLighting();
		GlStateManager.disableRescaleNormal();
		GlStateManager.activeTexture(OpenGlHelper.GL_TEXTURE1);
		GlStateManager.disableTexture2D();
		GlStateManager.activeTexture(OpenGlHelper.GL_TEXTURE0);

		FontRenderer fr = Minecraft.getInstance().fontRenderer;
		String name = e.getName().getFormattedText();
		fr.drawString(name, x - fr.getStringWidth(name) / 2, y - e.height * scalef - fr.FONT_HEIGHT * 1.5f, 0x000000);
	}

	public String dialog = "";

	private void drawDialog() {

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
			if (bookX + fontRenderer.getStringWidth(curLine + token + " ") - bookWidth * .17f < bookX + maxWidth) {
				curLine += token + " ";
			} else {
				lines.add(curLine);
				curLine = token + " ";
			}
		}
		lines.add(curLine);

		for (int i = 0; i < lines.size(); ++i) {
			fontRenderer.drawString(lines.get(i), (bookX + bookWidth * .03f) * invscalef,
					(bookY + bookWidth * .03f + fontRenderer.FONT_HEIGHT * i) * invscalef, 0x000000);
		}
	}

	public String[] buttonTexts = { "", "", "", "", "" };
	@Override
	public void initGui() {
		super.initGui();
		this.buttons.clear();
		this.children.clear();
		int buttonWidth = (int)(bookWidth * .45f);
		int numButtons = 0;
		for (int i = 0; i < 5; ++i) {
			if (buttonTexts[i] != null && !buttonTexts[i].equals("")) {
				++numButtons;
			}
		}
		if (numButtons > 0) {
			GuiButton temp = new GuiButton(0, 0, 0, "") {};
			//buttonHeight = temp.height;
		}

		int buttonBaseY = bookY + bookHeight - (int)(1.5 * 20);
		for (int i = 4; i >= 0; --i) {
			if (buttonTexts[i] != null && !buttonTexts[i].equals("")) {

				GuiButton btn = new GuiButton(i, 
						bookX + bookWidth / 2 - buttonWidth - 7, 
						buttonBaseY - i * 20 -  i,
						buttonWidth,
						20,
						buttonTexts[i]) {
					public void onClick(double mouseX, double mouseY) {
						buttonClicked(this);
					}
				};

				this.addButton(btn);
			}
		}
	}

	protected void buttonClicked(GuiButton button) {
		for (NpcDialogResponse response : npcDialog.playerResponses) {
			if (response.responseName.equals(button.displayString)) {
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
			if (buttonTexts[i] != null && buttonTexts[i].equals(button.displayString)) {

			}
		}
	}

	boolean resized = true;

	@Override
	public void onResize(Minecraft mcIn, int w, int h) {
		super.onResize(mcIn, w, h);
		resized = true;
	}

	static int bookWidth, bookHeight, bookX, bookY;

	private void drawBookBackground() {
		GlStateManager.pushMatrix();
		GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);

		TextureManager tm = Minecraft.getInstance().getTextureManager();
		MainWindow sr = Minecraft.getInstance().mainWindow;

		tm.bindTexture(backgreoundImage);

		bookWidth = (int)(sr.getScaledWidth() * .7f);
		bookHeight = (int) (bookWidth * .7f);
		bookX = sr.getScaledWidth() / 2 - bookWidth / 2 - 5;
		bookY = (int)(sr.getScaledHeight() * .02f);

		drawModalRectWithCustomSizedTexture(bookX, bookY, 0, 0, bookWidth, bookHeight, bookWidth, bookHeight);
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
	public boolean doesGuiPauseGame() {
		return false;
	}
}
