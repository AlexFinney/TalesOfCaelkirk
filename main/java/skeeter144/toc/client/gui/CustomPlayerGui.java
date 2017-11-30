package skeeter144.toc.client.gui;

import java.io.IOException;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.InventoryEffectRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import skeeter144.toc.items.armor.CustomArmor;

@SideOnly(Side.CLIENT)
public class CustomPlayerGui extends InventoryEffectRenderer
{
    /** The old x position of the mouse pointer */
    private float oldMouseX;
    /** The old y position of the mouse pointer */
    private float oldMouseY;
    private boolean widthTooNarrow;
    private boolean buttonClicked;

    public CustomPlayerGui(EntityPlayer player)
    {
        super(player.inventoryContainer);
        this.allowUserInput = true;
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        if (this.mc.playerController.isInCreativeMode())
        {
            this.mc.displayGuiScreen(new GuiContainerCreative(this.mc.player));
        }
    }

    /**
     * Adds the buttons (and other controls) to the screen in question. Called when the GUI is displayed and when the
     * window resizes, the buttonList is cleared beforehand.
     */
    public void initGui()
    {
        this.buttonList.clear();

        if (this.mc.playerController.isInCreativeMode())
        {
            this.mc.displayGuiScreen(new GuiContainerCreative(this.mc.player));
        }
        else
        {
            super.initGui();
        }

        this.widthTooNarrow = this.width < 379;
        int index = 0;
        for(Slot s : ((ContainerPlayer)this.inventorySlots).inventorySlots) {
        	if(s instanceof SlotCrafting)
        		break;
        	++index;
        }
    }

    /**
     * Draw the foreground layer for the GuiContainer (everything in front of the items)
     */
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
    	  this.drawRect(76, 07, 174, 61, 0xff000000);
    	  this.drawRect(94, 60, 174, 78, 0xff000000);
    	 
    	  int gap = 3;
    	  
    	  GlStateManager.pushMatrix();
    	  GlStateManager.scale(.75, .75, .75);
    	  this.drawString(this.fontRenderer, "Physical Resistance:", 107, 12, 0xffffffff);
    	  this.drawString(this.fontRenderer, "Magical Resistance:",  107, 12 + this.fontRenderer.FONT_HEIGHT + gap, 0xffffffff);
    	  this.drawString(this.fontRenderer, "Ranged Resistance:",   107, 12 + (this.fontRenderer.FONT_HEIGHT + gap) * 2, 0xffffffff);
    	  
    	  float physRes = 0, magRes = 0, rangedRes = 0;
    	  for(ItemStack s : Minecraft.getMinecraft().player.getArmorInventoryList()) {
    		  if(s.getItem() instanceof CustomArmor) {
    			  physRes += ((CustomArmor)s.getItem()).physicalResistance;
    			  magRes += ((CustomArmor)s.getItem()).magicalResistance;
    			  rangedRes += ((CustomArmor)s.getItem()).rangedResistance;
    		  }
    	  }
    	  
    	  this.drawString(this.fontRenderer, "%" + physRes * 100,   190, 12, 0xffffffff);
    	  this.drawString(this.fontRenderer, "%" + magRes * 100,    190, 12 + this.fontRenderer.FONT_HEIGHT + gap, 0xffffffff);
    	  this.drawString(this.fontRenderer, "%" + rangedRes * 100, 190, 12 + (this.fontRenderer.FONT_HEIGHT + gap) * 2, 0xffffffff);
    	  
    	  
    	  GlStateManager.popMatrix();
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.drawDefaultBackground();
        this.hasActivePotionEffects = true;

        if ( this.widthTooNarrow)
        {
            this.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
        }
        else
        {
            super.drawScreen(mouseX, mouseY, partialTicks);
        }

        this.renderHoveredToolTip(mouseX, mouseY);
        
      
        
        this.oldMouseX = (float)mouseX;
        this.oldMouseY = (float)mouseY;
    }

    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(INVENTORY_BACKGROUND);
        int i = this.guiLeft;
        int j = this.guiTop;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
        drawEntityOnScreen(i + 51, j + 75, 30, (float)(i + 51) - this.oldMouseX, (float)(j + 75 - 50) - this.oldMouseY, this.mc.player);
        
    }

    /**
     * Draws an entity on the screen looking toward the cursor.
     */
    public static void drawEntityOnScreen(int posX, int posY, int scale, float mouseX, float mouseY, EntityLivingBase ent)
    {
        GlStateManager.enableColorMaterial();
        GlStateManager.pushMatrix();
        GlStateManager.translate((float)posX, (float)posY, 50.0F);
        GlStateManager.scale((float)(-scale), (float)scale, (float)scale);
        GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
        float f = ent.renderYawOffset;
        float f1 = ent.rotationYaw;
        float f2 = ent.rotationPitch;
        float f3 = ent.prevRotationYawHead;
        float f4 = ent.rotationYawHead;
        GlStateManager.rotate(135.0F, 0.0F, 1.0F, 0.0F);
        RenderHelper.enableStandardItemLighting();
        GlStateManager.rotate(-135.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(-((float)Math.atan((double)(mouseY / 40.0F))) * 20.0F, 1.0F, 0.0F, 0.0F);
        ent.renderYawOffset = (float)Math.atan((double)(mouseX / 40.0F)) * 20.0F;
        ent.rotationYaw = (float)Math.atan((double)(mouseX / 40.0F)) * 40.0F;
        ent.rotationPitch = -((float)Math.atan((double)(mouseY / 40.0F))) * 20.0F;
        ent.rotationYawHead = ent.rotationYaw;
        ent.prevRotationYawHead = ent.rotationYaw;
        GlStateManager.translate(0.0F, 0.0F, 0.0F);
        RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
        rendermanager.setPlayerViewY(180.0F);
        rendermanager.setRenderShadow(false);
        rendermanager.doRenderEntity(ent, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, false);
        rendermanager.setRenderShadow(true);
        ent.renderYawOffset = f;
        ent.rotationYaw = f1;
        ent.rotationPitch = f2;
        ent.prevRotationYawHead = f3;
        ent.rotationYawHead = f4;
        GlStateManager.popMatrix();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableRescaleNormal();
        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.disableTexture2D();
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
    }

    /**
     * Test if the 2D point is in a rectangle (relative to the GUI). Args : rectX, rectY, rectWidth, rectHeight, pointX,
     * pointY
     */
    protected boolean isPointInRegion(int rectX, int rectY, int rectWidth, int rectHeight, int pointX, int pointY)
    {
        return !this.widthTooNarrow && super.isPointInRegion(rectX, rectY, rectWidth, rectHeight, pointX, pointY);
    }

    /**
     * Called when the mouse is clicked. Args : mouseX, mouseY, clickedButton
     */
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
    {
		if (!this.widthTooNarrow) {
			super.mouseClicked(mouseX, mouseY, mouseButton);
		}
    }

    /**
     * Called when a mouse button is released.
     */
    protected void mouseReleased(int mouseX, int mouseY, int state)
    {
        if (this.buttonClicked)
        {
            this.buttonClicked = false;
        }
        else
        {
            super.mouseReleased(mouseX, mouseY, state);
        }
    }

    protected boolean hasClickedOutside(int p_193983_1_, int p_193983_2_, int p_193983_3_, int p_193983_4_)
    {
       return p_193983_1_ < p_193983_3_ || p_193983_2_ < p_193983_4_ || p_193983_1_ >= p_193983_3_ + this.xSize || p_193983_2_ >= p_193983_4_ + this.ySize;
    }

    /**
     * Called by the controls from the buttonList when activated. (Mouse pressed for buttons)
     */
    protected void actionPerformed(GuiButton button) throws IOException
    {
        if (button.id == 10)
        {
            this.buttonClicked = true;
        }
    }

    /**
     * Fired when a key is typed (except F11 which toggles full screen). This is the equivalent of
     * KeyListener.keyTyped(KeyEvent e). Args : character (character on the key), keyCode (lwjgl Keyboard key code)
     */
    protected void keyTyped(char typedChar, int keyCode) throws IOException
    {
            super.keyTyped(typedChar, keyCode);
    }

    /**
     * Called when the mouse is clicked over a slot or outside the gui.
     */
    protected void handleMouseClick(Slot slotIn, int slotId, int mouseButton, ClickType type)
    {
        super.handleMouseClick(slotIn, slotId, mouseButton, type);
    }

    public void recipesUpdated()
    {
    }

    /**
     * Called when the screen is unloaded. Used to disable keyboard repeat events
     */
    public void onGuiClosed()
    {
        super.onGuiClosed();
    }

   
}