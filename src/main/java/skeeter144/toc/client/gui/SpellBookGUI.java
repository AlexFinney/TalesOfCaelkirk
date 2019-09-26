package skeeter144.toc.client.gui;

import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.Map;

import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import skeeter144.toc.client.Keybindings;
import skeeter144.toc.items.magic.BasicWand;
import skeeter144.toc.magic.Spell;
import skeeter144.toc.magic.Spells;
import skeeter144.toc.network.Network;
import skeeter144.toc.network.WandEmbueMessage;
import skeeter144.toc.util.Mouse;
import skeeter144.toc.util.Reference;

public class SpellBookGUI extends GuiScreen {

	static int selectedIcon = 0;
	ResourceLocation spellBookImage = new ResourceLocation(Reference.MODID, "textures/gui/magic_book_background.png");
	ResourceLocation selectedIconImage = new ResourceLocation(Reference.MODID, "textures/spells/selected_icon.png");

	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		//super.drawScreen(mouseX, mouseY, partialTicks);

		drawBookBackground();
		drawSpellIcons();
	}

	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}

	boolean wasMouseClicked = false;
	static Map<String, Rectangle2D> spellIcons;

	private void drawSpellIcons() {
		if (spellIcons == null)
			spellIcons = new HashMap<String, Rectangle2D>();

		GlStateManager.pushMatrix();
		GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);

		TextureManager tm = Minecraft.getInstance().getTextureManager();
		MainWindow sr = Minecraft.getInstance().mainWindow;
		FontRenderer fr = Minecraft.getInstance().fontRenderer;

		int adjustedX = Mouse.getX() * sr.getScaledWidth() / sr.getWidth();
		int adjustedY = sr.getScaledHeight()
				- Mouse.getY() * sr.getScaledHeight() / sr.getHeight();

		drawRect(adjustedX - 2, adjustedY - 2, adjustedX + 2, adjustedY + 2, 0xFFFFFFFF);

		int baseX = bookX + bookWidth / 20;
		int baseY = bookY + 10;

		int iconDim = bookWidth / 16;
		int iconSpace = bookWidth / 18;

		int buttonWidth = bookWidth / 6;
		int buttonHeight = buttonWidth / 2;
		int buttonX = baseX + bookWidth / 10;
		int buttonY = bookY + (int) (bookHeight / 1.2f);
		
		int count = 0;
		int row = 0;
		int col = 0;
		for (Spell s : Spells.spells) {
			tm.bindTexture(s.icon);
			int iconX = baseX + col * iconDim + iconSpace * col;
			int iconY = baseY + row * iconDim + iconSpace / 2 * row;

			drawModalRectWithCustomSizedTexture(iconX, iconY, 0, 0, iconDim, iconDim, iconDim, iconDim);

			Rectangle2D.Float rect = new Rectangle2D.Float(iconX, iconY, iconDim, iconDim);
			if (spellIcons.get(s.getName()) == null || !rect.equals(spellIcons.get(s.getName()))) {
				spellIcons.put(s.getName(), rect);
			}

			if (!wasMouseClicked && Mouse.isButtonDown(0)) {
				if (rect.contains(adjustedX, adjustedY)) {
					wasMouseClicked = true;
					// TODO: level check to make sure player can select the spell
					selectedIcon = count;
				}

			}

			if (count == selectedIcon) {
				tm.bindTexture(selectedIconImage);
				drawModalRectWithCustomSizedTexture(iconX, iconY, 0, 0, iconDim, iconDim, iconDim, iconDim);
			}
			
			if (rect.contains(adjustedX, adjustedY)) {
				String str = s.getName();
				fr.drawString(str, buttonX + buttonWidth / 2 - fr.getStringWidth(str) / 2, buttonY - fr.FONT_HEIGHT * .8f, 0xFFFF0000);
				fr.drawString("", 0, 0, 0xFFFFFFFF);
			}

			count++;
			col = count % 4;
			if (count % 4 == 0) {
				row++;
				col = 0;
			}
		}

		
		// buttonY = buttonY < 50 ? 500 : buttonY;

		Rectangle2D.Double embueBtn = new Rectangle2D.Double(buttonX, buttonY, buttonWidth, buttonHeight);
		if (embueBtn.contains(adjustedX, adjustedY)) {
			tm.bindTexture(new ResourceLocation(Reference.MODID, "textures/gui/embue_spell_active.png"));

		} else {
			tm.bindTexture(new ResourceLocation(Reference.MODID, "textures/gui/embue_spell_inactive.png"));
		}

		drawModalRectWithCustomSizedTexture(buttonX, buttonY, 0, 0, buttonWidth, buttonHeight, buttonWidth,
				buttonHeight);

		if (embueBtn.contains(adjustedX, adjustedY)) {
			String str = "Embue: " + Spells.getSpell(selectedIcon).getName();
			fr.drawString(str, buttonX - fr.getStringWidth(str) / 5, buttonY - 10, 0xFF1010);

			if (!wasMouseClicked && Mouse.isButtonDown(0) && selectedIcon >= 0) {
				wasMouseClicked = true;

				ItemStack right = Minecraft.getInstance().player.getHeldItemMainhand();
				ItemStack left = Minecraft.getInstance().player.getHeldItemOffhand();

				ItemStack held = null;

				if (right.getItem() instanceof BasicWand || left.getItem() instanceof BasicWand) {
					if (right.getItem() instanceof BasicWand) {
						held = right;
					} else {
						held = left;
					}
				}

				if (held != null) {
					NBTTagCompound nbt = held.getTag();
					if (nbt == null) {
						nbt = new NBTTagCompound();
						held.setTag(nbt);
					}

					nbt.setInt("embued_spell", selectedIcon);
					nbt.setString("owner", Minecraft.getInstance().player.getUniqueID().toString());
					int hand = held == right ? 1 : 0;

					Network.INSTANCE
							.sendToServer(new WandEmbueMessage(Minecraft.getInstance().player, hand, selectedIcon));
				} else {
					Minecraft.getInstance().player
							.sendChatMessage("You must be holding a wand to embue a spell onto it!");
				}
			}
		}

		if (wasMouseClicked && !Mouse.isButtonDown(0)) {
			wasMouseClicked = false;
		}

		GlStateManager.popAttrib();
	}

	static int bookWidth, bookHeight, bookX, bookY;

	private void drawBookBackground() {
		GlStateManager.pushMatrix();
		GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);

		TextureManager tm = Minecraft.getInstance().getTextureManager();
		MainWindow sr = Minecraft.getInstance().mainWindow;

		tm.bindTexture(spellBookImage);

		bookWidth = sr.getScaledWidth() / 2;
		bookHeight = (int) (bookWidth * .75f);
		bookX = sr.getScaledWidth() / 2 - bookWidth / 2 - 5;
		bookY = sr.getScaledHeight() / 10;

		drawModalRectWithCustomSizedTexture(bookX, bookY, 0, 0, bookWidth, bookHeight, bookWidth, bookHeight);

		GlStateManager.popAttrib();
	}

	@Override
	public boolean charTyped(char typedChar, int keyCode){
		if (keyCode == Keybindings.SPELLBOOK_KEYBIND.getKey().getKeyCode())
			Minecraft.getInstance().displayGuiScreen(null);
		return super.charTyped(typedChar, keyCode);
	}

}
