package skeeter144.toc.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import skeeter144.toc.banking.ContainerBank;

public class BankGui<T> extends ContainerScreen<ContainerBank>{

	   /** The ResourceLocation containing the chest GUI texture. */
    private static final ResourceLocation CHEST_GUI_TEXTURE = new ResourceLocation("textures/gui/container/generic_54.png");
    /** window height is calculated with these values; the more rows, the heigher */
    private final int bankInventoryRows = 6;
    private final int playerInventoryRows = 4;
    
    public BankGui(PlayerInventory playerInv, IInventory bankInv)
    {
        super(new ContainerBank(playerInv, bankInv, Minecraft.getInstance().player), 
        	  playerInv, new StringTextComponent("Bank"));
        
        Minecraft.getInstance().player.openContainer = this.container;
        this.ySize = 114 + this.bankInventoryRows * 18;
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
       // this.renderBackground();
        this.renderHoveredToolTip(mouseX, mouseY);
    }

    /**
     * Draw the foreground layer for the GuiContainer (everything in front of the items)
     */
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        this.font.drawString("Bank", 8, 6, 0xFFFFFFFF);
        this.font.drawString("Inventory", 8, this.ySize - 96 + 2, 0xFFFFFFFF);
    }

    /**
     * Draws the background layer of this container (behind the items).
     */
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        //GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        minecraft.getTextureManager().bindTexture(CHEST_GUI_TEXTURE);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2 - 3;
        this.blit(i, j, 0, 0, this.xSize, this.bankInventoryRows * 18 + 21);
        this.blit(i, j + this.playerInventoryRows * 18 + 60, 0, 130, this.xSize, 96);
    }
}
