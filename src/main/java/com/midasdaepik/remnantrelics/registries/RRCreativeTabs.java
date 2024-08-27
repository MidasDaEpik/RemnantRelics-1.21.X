package com.midasdaepik.remnantrelics.registries;

import com.midasdaepik.remnantrelics.RemnantRelics;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class RRCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, RemnantRelics.MOD_ID);

    public static final Supplier<CreativeModeTab> REMNANT_RELICS = CREATIVE_MODE_TABS.register("remnant_relics",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(RRItems.MOD_ICON.get()))
                    .title(Component.translatable("creativetab.remnantrelics.items"))
                    //.backgroundTexture(ResourceLocation.fromNamespaceAndPath(RemnantRelics.MOD_ID, "textures/gui/container/creative_inventory/background.png"))
                    .displayItems((pParameters, pOutput) -> {

                        pOutput.accept(RRItems.BLUEPRINT_SCROLL_ELDER.get());

                        pOutput.accept(RRItems.CUTLASS.get());

                        pOutput.accept(RRItems.ELDER_SPINE.get());

                        pOutput.accept(RRItems.CHARYBDIS.get());
                        pOutput.accept(RRItems.ELDER_CHESTPLATE.get());

                        pOutput.accept(RRItems.WHISPERWIND.get());

                        pOutput.accept(RRItems.ANCIENT_WEAPON_FRAGMENT.get());

                        pOutput.accept(RRItems.PIGLIN_WARAXE.get());

                        pOutput.accept(RRItems.BLAZE_CORE.get());

                        pOutput.accept(RRItems.FIRESTORM_KATANA.get());
                        pOutput.accept(RRItems.SEARING_STAFF.get());

                        pOutput.accept(RRItems.OBSIDIAN_BULWARK.get());

                        pOutput.accept(RRItems.WARPED_RAPIER.get());

                        pOutput.accept(RRItems.WITHERBLADE_UPGRADE_SMITHING_TEMPLATE.get());

                        pOutput.accept(RRItems.ANCIENT_TABLET_FORGING.get());
                        pOutput.accept(RRItems.ANCIENT_TABLET_INFUSION.get());
                        pOutput.accept(RRItems.ANCIENT_TABLET_REFINING.get());

                        pOutput.accept(RRItems.WITHER_ARMOR_TRIM_SMITHING_TEMPLATE.get());

                        pOutput.accept(RRItems.WITHERBLADE.get());
                        pOutput.accept(RRItems.REFINED_WITHERBLADE.get());

                        pOutput.accept(RRItems.CREEPING_CRIMSON.get());
                        pOutput.accept(RRItems.SOULEATING_SLASHER.get());
                        pOutput.accept(RRItems.WARPING_WITHER.get());

                        pOutput.accept(RRItems.RESEARCHERS_NOTES_SCULK.get());
                        pOutput.accept(RRItems.RESEARCHERS_MEMOIR_SCULK.get());

                        pOutput.accept(RRItems.ECHO_GEM.get());

                        pOutput.accept(RRItems.CATALYST_CHALICE.get());
                        pOutput.accept(RRItems.SCYLLA.get());
                        pOutput.accept(RRItems.LYRE_OF_ECHOES.get());

                        pOutput.accept(RRItems.HEXED_DICE.get());
                    })
                    .build()
    );

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
