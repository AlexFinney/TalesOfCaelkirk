package skeeter144.toc.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import skeeter144.toc.banking.ContainerBank;

public class BankGui extends GuiContainer{

	   /** The ResourceLocation containing the chest GUI texture. */
    private static final ResourceLocation CHEST_GUI_TEXTURE = new ResourceLocation("textures/gui/container/generic_54.png");
    /** window height is calculated with these values; the more rows, the heigher */
    private final int bankInventoryRows = 6;
    private final int playerInventoryRows = 4;
    
    public BankGui(IInventory playerInv, IInventory bankInv)
    {
        super(new ContainerBank(playerInv, bankInv, Minecraft.getMinecraft().player));
        
        Minecraft.getMinecraft().player.openContainer = this.inventorySlots;
        this.ySize = 114 + this.bankInventoryRows * 18;
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }

    /**
     * Draw the foreground layer for the GuiContainer (everything in front of the items)
     */
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        this.fontRenderer.drawString("Bank", 8, 6, 0xFFFFFFFF);
        this.fontRenderer.drawString("Inventory", 8, this.ySize - 96 + 2, 0xFFFFFFFF);
    }

    /**
     * Draws the background layer of this container (behind the items).
     */
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(CHEST_GUI_TEXTURE);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2 - 3;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.bankInventoryRows * 18 + 21);
        this.drawTexturedModalRect(i, j + this.playerInventoryRows * 18 + 60, 0, 130, this.xSize, 96);
    }
}
