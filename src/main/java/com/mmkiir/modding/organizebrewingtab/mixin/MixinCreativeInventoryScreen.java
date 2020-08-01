package com.mmkiir.modding.organizebrewingtab.mixin;

import net.minecraft.client.gui.screen.ingame.AbstractInventoryScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemGroup;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;

@Mixin(CreativeInventoryScreen.class)
public abstract class MixinCreativeInventoryScreen extends AbstractInventoryScreen<CreativeInventoryScreen.CreativeScreenHandler> {

	public MixinCreativeInventoryScreen(CreativeInventoryScreen.CreativeScreenHandler screenHandler, PlayerInventory playerInventory, Text text) {
		super(screenHandler, playerInventory, text);
	}

	@Inject(at = @At("TAIL"), method = "setSelectedTab")
	private void setSelectedTab(ItemGroup group, CallbackInfo callbackInfo) {
		if (group == ItemGroup.BREWING) {
		    // Move brewing ingredients to the start.
			this.handler.itemList.sort((a, b) -> {
                if (!a.toString().contains("potion") && b.toString().contains("potion"))
                    return -1;
                if (a.toString().contains("potion") && !b.toString().contains("potion"))
                    return 1;
                return 0;
            });
		}
		this.handler.scrollItems(0.0F); // Force re-render.
	}
}
