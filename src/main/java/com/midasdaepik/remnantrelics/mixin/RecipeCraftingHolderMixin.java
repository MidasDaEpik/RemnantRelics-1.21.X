package com.midasdaepik.remnantrelics.mixin;

import com.midasdaepik.remnantrelics.RemnantRelics;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.ServerAdvancementManager;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.RecipeCraftingHolder;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RecipeCraftingHolder.class)
public interface RecipeCraftingHolderMixin {
    @Inject(method = "setRecipeUsed(Lnet/minecraft/world/level/Level;Lnet/minecraft/server/level/ServerPlayer;Lnet/minecraft/world/item/crafting/RecipeHolder;)Z", at = @At("HEAD"), cancellable = true)
    private void restrictCustomRecipe(Level level, ServerPlayer player, RecipeHolder<?> recipe, CallbackInfoReturnable<Boolean> cir) {
        RemnantRelics.LOGGER.info("YIPEEE MIXIN WORKS");
        if (recipe.id().equals(ResourceLocation.fromNamespaceAndPath(RemnantRelics.MOD_ID, "firestorm_katana"))) {
            ServerAdvancementManager advancementManager = player.server.getAdvancements();
            AdvancementHolder advancementHolder = advancementManager.get(ResourceLocation.fromNamespaceAndPath(RemnantRelics.MOD_ID, "unlock/test"));
            if (advancementHolder != null && !player.getAdvancements().getOrStartProgress(advancementHolder).isDone()) {
                cir.setReturnValue(false);
            }
        }
    }
}