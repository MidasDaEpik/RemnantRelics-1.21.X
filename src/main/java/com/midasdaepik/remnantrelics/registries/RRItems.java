package com.midasdaepik.remnantrelics.registries;

import com.midasdaepik.remnantrelics.RemnantRelics;
import com.midasdaepik.remnantrelics.item.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class RRItems {
    public static final DeferredRegister.Items ITEMS =
            DeferredRegister.createItems(RemnantRelics.MOD_ID);

    public static final DeferredItem<Item> CUTLASS = ITEMS.register("cutlass",
            () -> new Cutlass(new Item.Properties()));

    public static final DeferredItem<Item> ELDER_SPINE = ITEMS.register("elder_spine",
            () -> new Item(new Item.Properties().rarity(RREnumExtensions.RARITY_ELDER.getValue())));

    public static final DeferredItem<Item> CHARYBDIS = ITEMS.register("charybdis",
            () -> new Charybdis(new Item.Properties()));
    public static final DeferredItem<ArmorItem> ELDER_CHESTPLATE = ITEMS.register("elder_chestplate",
            () -> new ElderChestplate(new Item.Properties()));

    public static final DeferredItem<Item> WHISPERWIND = ITEMS.register("whisperwind",
            () -> new Whisperwind(new Item.Properties()));

    public static final DeferredItem<Item> PIGLIN_WARAXE = ITEMS.register("piglin_waraxe",
            () -> new PiglinWaraxe(new Item.Properties()));

    public static final DeferredItem<Item> FIRESTORM_KATANA = ITEMS.register("firestorm_katana",
            () -> new FirestormKatana(new Item.Properties()));
    public static final DeferredItem<Item> SEARING_STAFF = ITEMS.register("searing_staff",
            () -> new SearingStaff(new Item.Properties()));

    public static final DeferredItem<Item> OBSIDIAN_BULWARK = ITEMS.register("obsidian_bulwark",
            () -> new ObsidianBulwark(new Item.Properties()));

    public static final DeferredItem<Item> WARPED_RAPIER = ITEMS.register("warped_rapier",
            () -> new WarpedRapier(new Item.Properties()));

    public static final DeferredItem<Item> WITHERBLADE_UPGRADE_SMITHING_TEMPLATE = ITEMS.register("witherblade_upgrade_smithing_template",
            UpgradeTemplateItem::createWitherbladeUpgradeTemplate);

    public static final DeferredItem<Item> ANCIENT_TABLET_FUSION = ITEMS.register("ancient_tablet_fusion",
            AncientKnowledgeItem::createAncientTabletFusion);
    public static final DeferredItem<Item> ANCIENT_TABLET_IMBUEMENT = ITEMS.register("ancient_tablet_imbuement",
            AncientKnowledgeItem::createAncientTabletImbuement);
    public static final DeferredItem<Item> ANCIENT_TABLET_REINFORCEMENT = ITEMS.register("ancient_tablet_reinforcement",
            AncientKnowledgeItem::createAncientTabletReinforcement);

    public static final DeferredItem<Item> ATROPHY_ARMOR_TRIM_SMITHING_TEMPLATE = ITEMS.register("atrophy_armor_trim_smithing_template",
            () -> SmithingTemplateItem.createArmorTrimTemplate(ResourceLocation.fromNamespaceAndPath(RemnantRelics.MOD_ID, "atrophy")));

    public static final DeferredItem<Item> WITHERBLADE = ITEMS.register("witherblade",
            () -> new Witherblade(new Item.Properties()));
    public static final DeferredItem<Item> REFINED_WITHERBLADE = ITEMS.register("refined_witherblade",
            () -> new RefinedWitherblade(new Item.Properties()));

    public static final DeferredItem<Item> MYCORIS = ITEMS.register("mycoris",
            () -> new Mycoris(new Item.Properties()));
    public static final DeferredItem<Item> PYROSWEEP = ITEMS.register("pyrosweep",
            () -> new Pyrosweep(new Item.Properties()));
    public static final DeferredItem<Item> SOULGORGE = ITEMS.register("soulgorge",
            () -> new Soulgorge(new Item.Properties()));
    public static final DeferredItem<Item> WARPTHISTLE = ITEMS.register("warpthistle",
            () -> new Warpthistle(new Item.Properties()));

    public static final DeferredItem<Item> RESEARCHERS_NOTES_SCULK = ITEMS.register("researchers_notes_sculk",
            () -> new Item(new Item.Properties().rarity(RREnumExtensions.RARITY_SCULK.getValue())));
    public static final DeferredItem<Item> RESEARCHERS_MEMOIR_SCULK = ITEMS.register("researchers_memoir_sculk",
            AncientKnowledgeItem::createResearchersMemoirSculk);

    public static final DeferredItem<Item> ECHO_GEM = ITEMS.register("echo_gem",
            () -> new Item(new Item.Properties().rarity(RREnumExtensions.RARITY_SCULK.getValue())));

    public static final DeferredItem<Item> CATALYST_CHALICE = ITEMS.register("catalyst_chalice",
            () -> new CatalystChalice(new Item.Properties()));
    public static final DeferredItem<Item> SCYLLA = ITEMS.register("scylla",
            () -> new Scylla(new Item.Properties()));
    public static final DeferredItem<Item> LYRE_OF_ECHOES = ITEMS.register("lyre_of_echoes",
            () -> new LyreOfEchoes(new Item.Properties()));

    public static final DeferredItem<Item> TYRANT_ARMOR_TRIM_SMITHING_TEMPLATE = ITEMS.register("tyrant_armor_trim_smithing_template",
            () -> SmithingTemplateItem.createArmorTrimTemplate(ResourceLocation.fromNamespaceAndPath(RemnantRelics.MOD_ID, "tyrant")));

    public static final DeferredItem<Item> DRAGONBONE = ITEMS.register("dragonbone",
            () -> new Dragonbone(new Item.Properties().rarity(RREnumExtensions.RARITY_DRAGON.getValue())));

    public static final DeferredItem<Item> DRAGONS_RAGE = ITEMS.register("dragons_rage",
            () -> new DragonsRage(new Item.Properties()));
    public static final DeferredItem<Item> DRAGONS_BREATH_ARBALEST = ITEMS.register("dragons_breath_arbalest",
            () -> new DragonsBreathArbalest(new Item.Properties()));

    public static final DeferredItem<Item> HEXED_DICE = ITEMS.register("hexed_dice",
            () -> new HexedDice(new Item.Properties()));

    public static final DeferredItem<Item> MOD_ICON = ITEMS.register("mod_icon",
            () -> new Item(new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
