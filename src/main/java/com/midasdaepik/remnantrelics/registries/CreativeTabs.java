package com.midasdaepik.remnantrelics.registries;

import com.midasdaepik.remnantrelics.RemnantRelics;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class CreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, RemnantRelics.MOD_ID);

    public static final Supplier<CreativeModeTab> REMNANT_RELICS = CREATIVE_MODE_TABS.register("remnant_relics",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(Items.MOD_ICON.get()))
                    .title(Component.translatable("creativetab.remnantrelics.items"))
                    //.backgroundTexture(ResourceLocation.fromNamespaceAndPath(RemnantRelics.MOD_ID, "textures/gui/container/creative_inventory/background.png"))
                    .displayItems((pParameters, pOutput) -> {

                        pOutput.accept(Items.BLUEPRINT_SCROLL_ELDER.get());

                        pOutput.accept(Items.CUTLASS.get());

                        pOutput.accept(Items.ELDER_SPINE.get());

                        pOutput.accept(Items.CHARYBDIS.get());
                        pOutput.accept(Items.ELDER_CHESTPLATE.get());

                        pOutput.accept(Items.WHISPERWIND.get());

                        pOutput.accept(Items.WITHERBLADE_UPGRADE_SMITHING_TEMPLATE.get());

                        pOutput.accept(Items.ANCIENT_TABLET_FORGING.get());
                        pOutput.accept(Items.ANCIENT_TABLET_INFUSION.get());
                        pOutput.accept(Items.ANCIENT_TABLET_REFINING.get());

                        pOutput.accept(Items.ANCIENT_WEAPON_FRAGMENT.get());

                        pOutput.accept(Items.PIGLIN_WARAXE.get());

                        pOutput.accept(Items.BLAZE_CORE.get());

                        pOutput.accept(Items.FIRESTORM_KATANA.get());
                        pOutput.accept(Items.SEARING_STAFF.get());

                        pOutput.accept(Items.OBSIDIAN_BULWARK.get());

                        pOutput.accept(Items.WARPED_RAPIER.get());

                        pOutput.accept(Items.WITHER_ARMOR_TRIM_SMITHING_TEMPLATE.get());

                        pOutput.accept(Items.WITHERBLADE.get());
                        pOutput.accept(Items.CREEPING_CRIMSON.get());
                        pOutput.accept(Items.SOULEATING_SLASHER.get());
                        pOutput.accept(Items.WARPING_WITHER.get());

                        pOutput.accept(Items.RESEARCHERS_NOTES_SCULK.get());
                        pOutput.accept(Items.RESEARCHERS_MEMOIR_SCULK.get());

                        pOutput.accept(Items.ECHO_GEM.get());

                        pOutput.accept(Items.CATALYST_CHALICE.get());
                        pOutput.accept(Items.SCYLLA.get());
                        pOutput.accept(Items.LYRE_OF_ECHOES.get());

                        pOutput.accept(Items.HEXED_DICE.get());
                    })
                    .build()
    );

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
