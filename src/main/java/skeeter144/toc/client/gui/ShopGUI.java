package skeeter144.toc.client.gui;

import java.awt.geom.Rectangle2D;
import java.util.List;

import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.IRegistry;
import skeeter144.toc.entity.mob.npc.shopkeeper.ShopData;
import skeeter144.toc.entity.mob.npc.shopkeeper.ShopData.ItemPrice;
import skeeter144.toc.entity.mob.npc.shopkeeper.ShopData.ShopListing;
import skeeter144.toc.network.ItemTransactionPKT;
import skeeter144.toc.network.Network;
import skeeter144.toc.util.Mouse;
import skeeter144.toc.util.Reference;

public class ShopGUI extends GuiScreen{

	private static final ResourceLocation selectedIconImage = new ResourceLocation(Reference.MODID, "textures/spells/selected_icon.png");
	private static final ResourceLocation shop_background = new ResourceLocation(Reference.MODID, "textures/gui/shop_background.png");
	
	ShopData shopData;
	public ShopGUI(ShopData shopData) {
		this.shopData = shopData;
	}
	
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
	    //super.drawScreen(mouseX, mouseY, partialTicks);
	    drawBackground();
	    drawPlayersItems();
	    drawShopItems();
	    drawSelectedItem();
	    if(resized) {
	    	initGui();
	    	resized = false;
	    }
	    
		if(this.itemToSell != -1 || this.itemToBuy != null) {    
			buyBtn.render(mouseX, mouseY, partialTicks);
		}
	    	
	    
	    if(wasMouseClicked && !Mouse.isButtonDown(0)) {
			wasMouseClicked = false;
		}
	}
	
	private void drawSelectedItem() {
		InventoryPlayer inv = Minecraft.getInstance().player.inventory;
		TextureManager tm = Minecraft.getInstance().getTextureManager();
		MainWindow sr = Minecraft.getInstance().mainWindow;
		int iconDim = (int)(bookWidth * .07f);
	    int adjustedX = Mouse.getX() * sr.getScaledWidth() / sr.getWidth();
	    int adjustedY = sr.getScaledHeight() - Mouse.getY() * sr.getScaledHeight() / sr.getHeight();
		int x = bookX + bookWidth / 15;
		int y =  bookY + (int)(bookHeight * .4f);
		this.drawRect(x, y, x + (int)(iconDim * 1.1f), y + (int)(iconDim * 1.1f), 0x5F000000);
		if(this.itemToSell != -1 || this.itemToBuy != null) {    
			ItemStack is = (itemToSell == -1 ? itemToBuy : inv.getStackInSlot(itemToSell));
			
			this.itemRender.renderItemAndEffectIntoGUI(is, x, y);
//			this.itemRender.renderItemAndEffectIntoGUI(new ItemStack(TOCItems.gold_coin), (int)(bookX + bookWidth * .5f), (int)(bookY + bookHeight * .4f));
//			this.itemRender.renderItemAndEffectIntoGUI(new ItemStack(TOCItems.silver_coin), (int)(bookX + bookWidth * .5f) + 2 * iconDim, (int)(bookY + bookHeight * .4f) );
//			this.itemRender.renderItemAndEffectIntoGUI(new ItemStack(TOCItems.copper_coin), (int)(bookX + bookWidth * .5f) + 4 * iconDim, (int)(bookY + bookHeight * .4f));
			
			ItemPrice price;
			boolean buying = itemToBuy != null;
			if(buying)
				price = shopData.getSellPriceForItem(itemToBuy.getItem());
			else
				price = shopData.getBuyPriceForItem(inv.getStackInSlot(itemToSell).getItem());
			
			if(price != null) {
				this.fontRenderer.drawString("x" + price.gold,  (int)(bookX + bookWidth * .5f + iconDim),  (int)(bookY + bookHeight * .4f + iconDim * .2f), 0xFFFFFFFF);
				this.fontRenderer.drawString("x" + price.silver,  (int)(bookX + bookWidth * .5f + iconDim * 3),  (int)(bookY + bookHeight * .4f + iconDim * .2f), 0xFFFFFFFF);
				this.fontRenderer.drawString("x" + price.copper,  (int)(bookX + bookWidth * .5f + iconDim * 5),  (int)(bookY + bookHeight * .4f + iconDim * .2f), 0xFFFFFFFF);
				
				List<String> toolTips = getItemToolTip(is);
				
				int minX = bookX;
				for(String s : toolTips) {
					if(bookX - this.fontRenderer.getStringWidth(s) - (int)(bookWidth * .01f) < minX)
						minX = bookX - this.fontRenderer.getStringWidth(s) - (int)(bookWidth * .01f);
			   	}
				
			    int i = 0;
			    for(String s : toolTips) {
			    	this.drawString(this.fontRenderer, toolTips.get(0), minX, bookY + i * fontRenderer.FONT_HEIGHT, 0xFFFFFF);
			    	++i;
			    }
			    
				
				if(itemToBuy == null)
					buyBtn.displayString = "Sell";
				else
					buyBtn.displayString = "Buy";
					
			}
			
			if(!wasMouseClicked && Mouse.isButtonDown(0)) {
				wasMouseClicked = true;
				Rectangle2D.Float rect = new Rectangle2D.Float(x, y, iconDim, iconDim);
				if(rect.contains(adjustedX, adjustedY)) {
					itemToSell = -1;
					itemToBuy = null;
				}
			}
		}
	}

	private void drawShopItems() {
	    if(shopData != null) {
	    	TextureManager tm = Minecraft.getInstance().getTextureManager();
	    	MainWindow sr = Minecraft.getInstance().mainWindow;
	    	int iconDim = (int)(bookWidth * .07f);
		    int adjustedX = Mouse.getX() * sr.getScaledWidth() / sr.getWidth();
		    int adjustedY = sr.getScaledHeight() - Mouse.getY() * sr.getScaledHeight() / sr.getHeight();
	    	int count = 0;
	    	for(ShopListing il : shopData.listings) {
	    		Item i = IRegistry.field_212630_s.func_212608_b(new ResourceLocation(il.itemName));
	    		Item.registerItems();
	    		if(i == null) {
	    			System.out.println("WARN: " + il.itemName + " did not map to a registered item!");
	    			continue;
	    		}
	    		
	    		int x = bookX + (int)(bookWidth * .87f) - count * 20;
				int y = bookY + 10;
				this.drawRect(x, y, x + (int)(iconDim * 1.2f), y + (int)(iconDim * 1.2f), 0x5F000000);
				ItemStack is = new ItemStack(i);
				
				if(itemToBuy != null && itemToBuy.getItem().equals(i)) {
					GlStateManager.translated(0, 0, 20);
					this.drawRect(x, y, x + (int)(iconDim * 1.2f), y + (int)(iconDim * 1.2f), 0x70FFFF00);
					GlStateManager.translated(0, 0, -20);
				}

				GlStateManager.pushMatrix();	
				GlStateManager.translated(0.0F, 0.0F, 32.0F);
				RenderHelper.enableGUIStandardItemLighting();
				GlStateManager.enableLighting();
				
				this.itemRender.renderItemAndEffectIntoGUI(is, x, y);
				if(!wasMouseClicked && Mouse.isButtonDown(0)) {
					Rectangle2D.Float rect = new Rectangle2D.Float(x, y, iconDim, iconDim);
					if(rect.contains(adjustedX, adjustedY)) {
						if(itemToBuy != null && itemToBuy.getItem().equals(i))
							itemToBuy = null;
						else {							
							itemToBuy = new ItemStack(i);
							itemToSell = -1;
						}
					}
				}
				GlStateManager.popMatrix();	
				++count;
	    	}
	    }
	}


	ItemStack itemToBuy = null;
	int itemToSell = -1;
	boolean wasMouseClicked = false;
	private void drawPlayersItems() {
		TextureManager tm = Minecraft.getInstance().getTextureManager();
		MainWindow sr = Minecraft.getInstance().mainWindow;
		InventoryPlayer inv = Minecraft.getInstance().player.inventory;
		int iconDim = (int)(bookWidth * .07f);
	    int adjustedX = Mouse.getX() * sr.getScaledWidth() / sr.getWidth();
	    int adjustedY = (int)(sr.getScaledHeight() - (0f + Mouse.getY() * sr.getScaledHeight()) / sr.getHeight());

		int count = 0;
	    int column = 0;
		int row = 0;
		List<ItemStack> items =  Minecraft.getInstance().player.inventory.mainInventory;
		for(ItemStack is : items) {
			FontRenderer font = is.getItem().getFontRenderer(is);
			if (font == null) 
				font = fontRenderer;
			
			int space = iconDim / 2;
			int x = column * iconDim + bookX + bookWidth/20 + space * column;
			int y = bookY + bookHeight / 2 + 10 + row * iconDim + row * space;
			this.drawRect(x, y, x + (int)(iconDim * 1.2f), y + (int)(iconDim * 1.2f), 0x5F000000);
			
			if(itemToSell != -1 && inv.getStackInSlot(itemToSell).equals(is)) {
				this.drawRect(x, y, x + (int)(iconDim * 1.2f), y + (int)(iconDim * 1.2f), 0x70FFFF00);
			}

			if(is.isEmpty()) {
				++count;
				++column;
				if(count % 9 == 0) {
					++row;
					column = 0;
				}
				continue;
			}
			
			RenderHelper.enableGUIStandardItemLighting();
			GlStateManager.enableLighting();
			this.itemRender.renderItemAndEffectIntoGUI(is, x, y);
			GlStateManager.translated(0, 0, 200);
			font.drawStringWithShadow(is.getCount() + "", x + iconDim - font.getStringWidth(is.getCount() + ""), y + iconDim / 2, 0xFFFFFFFF);
			GlStateManager.translated(0, 0, -200);
			GlStateManager.disableLighting();
			
			if(!wasMouseClicked && Mouse.isButtonDown(0)) {
				Rectangle2D.Float rect = new Rectangle2D.Float(x, y, iconDim, iconDim);
				if(rect.contains(adjustedX, adjustedY)) {
					if(shopData.getBuyPriceForItem(is.getItem()) != null) {
						itemToBuy = null;
						itemToSell = count;
					}
				}
			}
			if(shopData.getBuyPriceForItem(is.getItem()) == null) {
				GlStateManager.translated(0, 0, 200);
				this.drawRect(x, y, x + (int)(iconDim * 1.2f), y + (int)(iconDim * 1.2f), 0xAF000000);
				GlStateManager.translated(0, 0, -200);
			}
			
			++count;
			++column;
			if(count % 9 == 0) {
				++row;
				column = 0;
			}
		}
	}
	
	
	@Override
	public boolean doesGuiPauseGame() {
	    return false;
	}
	
	static int bookWidth, bookHeight, bookX, bookY;
	private void drawBackground() {
	    GlStateManager.pushMatrix();
		GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		
		TextureManager tm = Minecraft.getInstance().getTextureManager();
		MainWindow sr = Minecraft.getInstance().mainWindow;
		tm.bindTexture(shop_background);
		
		bookWidth = (int)(sr.getScaledWidth() * .5f);
		bookHeight = (int)(sr.getScaledHeight() * .8f);
		bookX = sr.getScaledWidth() / 2 - bookWidth / 2 - 5;
		bookY = (int)(sr.getScaledHeight() * .1f);
		
		drawModalRectWithCustomSizedTexture(bookX, bookY, 0, 0, bookWidth, bookHeight, bookWidth, bookHeight);
		GlStateManager.popMatrix();
	}
	
	GuiButton buyBtn;
	public void initGui() {
		super.initGui();
		buyBtn = new GuiButton(0, bookX + (int)(bookWidth * .33f), (int)(bookY + bookHeight * .38f), "Buy") {};
		buyBtn.width = (int)(bookWidth * .07f) * 2;
		buttons.clear();
		buttons.add(buyBtn);
	}
	
	protected void actionPerformed(GuiButton button){
		if(buyBtn != null && button.id == buyBtn.id) {
			InventoryPlayer inv = Minecraft.getInstance().player.inventory;
			Item i = itemToBuy == null ? inv.getStackInSlot(itemToSell).getItem() : itemToBuy.getItem();
			Network.INSTANCE.sendToServer(new ItemTransactionPKT(itemToBuy != null, i.getRegistryName().toString(), 1, shopData.keepId));
		}
	}
	
	boolean resized = true;
	public void onResize(Minecraft mcIn, int w, int h) {
		super.onResize(mcIn, w, h);
		resized = true;
	}
}
