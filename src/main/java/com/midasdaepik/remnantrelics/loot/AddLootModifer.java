package com.midasdaepik.remnantrelics.loot;

import com.google.common.base.Suppliers;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class AddLootModifer extends LootModifier {
    private final Item item;

    public static final Supplier<MapCodec<AddLootModifer>> MAP_CODEC = Suppliers.memoize(() ->
            RecordCodecBuilder.mapCodec(
                    inst -> LootModifier.codecStart(inst).and(
                            BuiltInRegistries.ITEM.byNameCodec().fieldOf("item").forGetter(m -> m.item)
                    ).apply(inst, AddLootModifer::new)
            )
    );

    public AddLootModifer(LootItemCondition[] conditionsIn, Item item) {
        super(conditionsIn);
        this.item = item;
    }

    @Override
    protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        generatedLoot.add(new ItemStack(this.item));
        return generatedLoot;
    }

    @Override
    public MapCodec<? extends IGlobalLootModifier> codec() {
        return MAP_CODEC.get();
    }
}