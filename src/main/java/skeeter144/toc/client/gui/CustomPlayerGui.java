package skeeter144.toc.client.gui;

import java.io.IOException;

import com.mojang.blaze3d.platform.GlStateManager;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.DisplayEffectsScreen;
import net.minecraft.client.gui.screen.inventory.CreativeScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.CraftingResultSlot;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CustomPlayerGui<T extends Container> extends DisplayEffectsScreen<T>
{
    public CustomPlayerGui(T container, PlayerInventory inventory, ITextComponent title) {
		super(container, inventory, title);
	}

	/** The old x position of the mouse pointer */
    private float oldMouseX;
    /** The old y position of the mouse pointer */
    private float oldMouseY;
    private boolean widthTooNarrow;
    private boolean buttonClicked;

   

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        if (this.minecraft.playerController.isInCreativeMode())
        {
            this.minecraft.displayGuiScreen(new CreativeScreen(this.minecraft.player));
        }
    }

    /**
     * Adds the buttons (and other controls) to the screen in question. Called when the GUI is displayed and when the
     * window resizes, the buttonList is cleared beforehand.
     */
    public void initGui()
    {
        buttons.clear();

        if (this.minecraft.playerController.isInCreativeMode())
        {
            this.minecraft.displayGuiScreen(new CreativeScreen(this.minecraft.player));
        }
        else
        {
            super.init();
        }

        this.widthTooNarrow = this.width < 379;
        int index = 0;
        for(Slot s : this.container.inventorySlots) {
        	if(s instanceof CraftingResultSlot)
        		break;
        	++index;
        }
    }

    /**
     * Draw the foreground layer for the GuiContainer (everything in front of the items)
     */
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
    	  fill(76, 07, 174, 61, 0xff000000);
    	  fill(94, 60, 174, 78, 0xff000000);
    	 
    	  int gap = 3;
    	  
    	  GlStateManager.pushMatrix();
    	  GlStateManager.scaled(.75, .75, .75);
    	  this.drawString(font, "Physical Resistance:", 107, 12, 0xffffffff);
    	  this.drawString(font, "Magical Resistance:",  107, 12 + font.FONT_HEIGHT + gap, 0xffffffff);
    	  this.drawString(font, "Ranged Resistance:",   107, 12 + (font.FONT_HEIGHT + gap) * 2, 0xffffffff);
    	  
    	  float physRes = 0, magRes = 0, rangedRes = 0;
    	  for(ItemStack s : Minecraft.getInstance().player.getArmorInventoryList()) {
//    		  if(s.getItem() instanceof CustomArmor) {
//    			  physRes += ((CustomArmor)s.getItem()).physicalResistance;
//    			  magRes += ((CustomArmor)s.getItem()).magicalResistance;
//    			  rangedRes += ((CustomArmor)s.getItem()).rangedResistance;
//    		  }
    	  }
    	  
    	  this.drawString(font, "%" + physRes * 100,   190, 12, 0xffffffff);
    	  this.drawString(font, "%" + magRes * 100,    190, 12 + font.FONT_HEIGHT + gap, 0xffffffff);
    	  this.drawString(font, "%" + rangedRes * 100, 190, 12 + (font.FONT_HEIGHT + gap) * 2, 0xffffffff);
    	  
    	  
    	  GlStateManager.popMatrix();
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        renderBackground();
        this.hasActivePotionEffects = true;

        if ( this.widthTooNarrow)
        {
            this.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
        }
        else
        {
        	//TODO:
            //super.drawScreen(mouseX, mouseY, partialTicks);
        }

        this.renderHoveredToolTip(mouseX, mouseY);
        
      
        
        this.oldMouseX = (float)mouseX;
        this.oldMouseY = (float)mouseY;
    }

    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bindTexture(INVENTORY_BACKGROUND);
        int i = this.guiLeft;
        int j = this.guiTop;
        this.blit(i, j, 0, 0, this.xSize, this.ySize);
        drawEntityOnScreen(i + 51, j + 75, 30, (float)(i + 51) - this.oldMouseX, (float)(j + 75 - 50) - this.oldMouseY, this.minecraft.player);
        
    }

    /**
     * Draws an entity on the screen looking toward the cursor.
     */
    public static void drawEntityOnScreen(int posX, int posY, int scale, float mouseX, float mouseY, LivingEntity ent)
    {
        GlStateManager.enableColorMaterial();
        GlStateManager.pushMatrix();
        GlStateManager.translatef((float)posX, (float)posY, 50.0F);
        GlStateManager.scalef((float)(-scale), (float)scale, (float)scale);
        GlStateManager.rotatef(180.0F, 0.0F, 0.0F, 1.0F);
        float f = ent.renderYawOffset;
        float f1 = ent.rotationYaw;
        float f2 = ent.rotationPitch;
        float f3 = ent.prevRotationYawHead;
        float f4 = ent.rotationYawHead;
        GlStateManager.rotatef(135.0F, 0.0F, 1.0F, 0.0F);
        RenderHelper.enableStandardItemLighting();
        GlStateManager.rotatef(-135.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotatef(-((float)Math.atan((double)(mouseY / 40.0F))) * 20.0F, 1.0F, 0.0F, 0.0F);
        ent.renderYawOffset = (float)Math.atan((double)(mouseX / 40.0F)) * 20.0F;
        ent.rotationYaw = (float)Math.atan((double)(mouseX / 40.0F)) * 40.0F;
        ent.rotationPitch = -((float)Math.atan((double)(mouseY / 40.0F))) * 20.0F;
        ent.rotationYawHead = ent.rotationYaw;
        ent.prevRotationYawHead = ent.rotationYaw;
        GlStateManager.translatef(0.0F, 0.0F, 0.0F);
        EntityRendererManager rendermanager = Minecraft.getInstance().getRenderManager();
        //rendermanager. setPlayerViewY(180.0F);
        rendermanager.setRenderShadow(false);
        //rendermanager.renderEntity(ent, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, false);
        rendermanager.setRenderShadow(true);
        ent.renderYawOffset = f;
        ent.rotationYaw = f1;
        ent.rotationPitch = f2;
        ent.prevRotationYawHead = f3;
        ent.rotationYawHead = f4;
        GlStateManager.popMatrix();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableRescaleNormal();
        //GlStateManager.setActiveTexture(OpenGlHelper.);
//        GlStateManager.disableTexture2D();
        //GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
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
    protected void actionPerformed(Button button) throws IOException
    {
//        if (button.id == 10)
//        {
//            this.buttonClicked = true;
//        }
    }

    /**
     * Fired when a key is typed (except F11 which toggles full screen). This is the equivalent of
     * KeyListener.keyTyped(KeyEvent e). Args : character (character on the key), keyCode (lwjgl Keyboard key code)
     */
    protected void keyTyped(char typedChar, int keyCode) throws IOException
    {
            super.charTyped(typedChar, keyCode);
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
        //super.onGuiClosed();
    }

   
}