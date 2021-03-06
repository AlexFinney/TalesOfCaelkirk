package skeeter144.toc.client.gui;

import java.io.IOException;
import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import skeeter144.toc.network.Network;
import skeeter144.toc.network.QuestDialogResponseMessage;
import skeeter144.toc.quest.NpcDialog;
import skeeter144.toc.quest.Quest;
import skeeter144.toc.quest.QuestManager;
import skeeter144.toc.util.Reference;

public class DialogGui extends GuiScreen{

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
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
	    drawBookBackground();
	    if(e != null) {
	    	pushEntityRotations();
	    	renderEntity();
	    	popEntityRotations();
	    }
	    drawDialog();
	    if(resized) {
	    	buttonList.clear();
	    	this.initGui();
	    	resized = false;
	    }
	    super.drawScreen(mouseX, mouseY, partialTicks);
	}
	
	private void renderEntity() {		
		float x = bookX + bookWidth * .73f;
		float y = bookY + bookHeight * .84f;
		
		int scale = (int) (bookWidth * .25f);
		
		
		
		GlStateManager.enableColorMaterial();
		GlStateManager.pushMatrix();

		GlStateManager.translate(x, y, 50.0F);
		GlStateManager.scale((float) (-scale), (float) scale, (float) scale);
		GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
		GlStateManager.rotate(65.0F, 0.0F, 1.0F, 0.0F);
		GlStateManager.rotate(-100.0F, 0.0F, 1.0F, 0.0F);
		GlStateManager.rotate(0.0f, 1.0F, 0.0F, 0.0F);

		RenderHelper.enableStandardItemLighting();

		GlStateManager.translate(0.0F, 0.0F, 0.0F);
		RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
		rendermanager.setPlayerViewY(180.0F);
		rendermanager.setRenderShadow(false);
		rendermanager.renderEntity(e, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, false);
		rendermanager.setRenderShadow(true);
		
		GlStateManager.popMatrix();
		RenderHelper.disableStandardItemLighting();
		GlStateManager.disableRescaleNormal();
		GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
		GlStateManager.disableTexture2D();
		GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
	
		GlStateManager.pushMatrix();
		FontRenderer fr = Minecraft.getMinecraft().fontRenderer;
		
		String name = e.getName();
		fr.drawString(name, x - fr.getStringWidth(name) / 2, y - e.height * scale - fr.FONT_HEIGHT * 1.5f, 0x000000, false);	
		GlStateManager.popMatrix();
	}
	
	public String dialog = "";
	private void drawDialog() {
		GlStateManager.pushMatrix();
	
		FontRenderer fr = Minecraft.getMinecraft().fontRenderer;
		String[] temp = dialog.split(" ");
		ArrayList<String> tokens = new ArrayList<String>();
		for(String s : temp)
			if(!s.equals(""))
				tokens.add(s);
		
		float textScale = .75f;
		float invScale = 1 / textScale;
		float maxWidth = bookWidth * .45f;
		GlStateManager.scale(textScale, textScale, 1);
		
		String curLine = "";
		ArrayList<String> lines = new ArrayList<String>();
		for(String token : tokens) {
			if(bookX + fr.getStringWidth(curLine + token + " ") - bookWidth * .17f < bookX + maxWidth) {
				curLine += token + " ";
			}else{
				lines.add(curLine);
				curLine = token + " ";
			}
		}
		lines.add(curLine);
			
		for(int i = 0; i < lines.size(); ++i) {
			fr.drawString(lines.get(i), (bookX + bookWidth * .03f) * invScale, (bookY + bookWidth * .03f + fr.FONT_HEIGHT * i) * invScale, 0x000000, false);
		}
		
		GlStateManager.popMatrix();
	}

	public String[] buttonTexts = {"", "", "", "", ""};
	GuiButton[] buttons = new GuiButton[5];
	GuiButton buttonNext;
	@Override
	public void initGui() {
		int buttonHeight = (int) (bookWidth * .1f);
		int buttonWidth = (int) ((bookWidth / 2) * .9f);
		int numButtons = 0;
		for(int i = 0; i < 5; ++i){
			if(buttonTexts[i] != null && !buttonTexts[i].equals("")) {
				++numButtons;
			}
		}
		if(numButtons > 0) {
			GuiButton temp = new GuiButton(0, 0, 0, "");
			buttonHeight = temp.height;
		}
		
		for(int i = 0; i < 5; ++i){
			if(buttonTexts[i] != null && !buttonTexts[i].equals("")) {
				buttons[i] = new GuiButton(i, bookX + (bookWidth / 2 - buttonWidth) / 2 + 1, 
											  (int) (bookY + bookHeight - i * buttonHeight - buttonHeight * 1.2f), buttonTexts[i]);
				buttonHeight = buttons[i].height;
				buttons[i].width = buttonWidth;
				this.buttonList.add(buttons[i]);
			}
		}
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		for(NpcDialogResponse response : npcDialog.playerResponses) {
			if(response.responseName.equals(button.displayString)) {
				if(response.transitionDialog != null)
					Minecraft.getMinecraft().displayGuiScreen(new DialogGui(this.e, response.transitionDialog));
				else
					Minecraft.getMinecraft().displayGuiScreen(null);
				
				if(!response.serverEventFunc.equals(""))
					Network.INSTANCE.sendToServer(new QuestDialogResponseMessage(e.getUniqueID(), response.serverEventFunc));
			}
			
		}
		
		for(int i = 0; i < 5; ++i) {
			if(buttonTexts[i] != null && buttonTexts[i].equals(button.displayString)) {
				
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
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		
		TextureManager tm = Minecraft.getMinecraft().getTextureManager();
		ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
		
		tm.bindTexture(backgreoundImage);
		
		bookWidth = sr.getScaledWidth() / 2;
		bookHeight = (int)(bookWidth * .75f);
		bookX = sr.getScaledWidth() / 2 - bookWidth / 2 - 5;
		bookY = sr.getScaledHeight() / 10;
		
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
