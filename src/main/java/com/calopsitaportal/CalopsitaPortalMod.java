package com.calopsitaportal;

import com.calopsitaportal.block.DarkPortalBlock;
import com.calopsitaportal.entity.ZombieCalopsitaEntity;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.material.MapColor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CalopsitaPortalMod implements ModInitializer {
	public static final String MOD_ID = "calopsita_portal";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static final ResourceKey<Level> DARK_LEVEL = ResourceKey.create(Registries.DIMENSION, id("dark"));

	public static final EntityType<ZombieCalopsitaEntity> ZOMBIE_CALOPSITA = Registry.register(
			BuiltInRegistries.ENTITY_TYPE,
			id("zombie_calopsita"),
			EntityType.Builder.of(ZombieCalopsitaEntity::new, MobCategory.MONSTER)
					.sized(0.5F, 0.9F)
					.build(ResourceKey.create(Registries.ENTITY_TYPE, id("zombie_calopsita")))
	);

	public static final Block DARK_PORTAL = Registry.register(
			BuiltInRegistries.BLOCK,
			id("dark_portal"),
			new DarkPortalBlock(BlockBehaviour.Properties.of()
					.setId(ResourceKey.create(Registries.BLOCK, id("dark_portal")))
					.mapColor(MapColor.COLOR_PURPLE)
					.strength(3.0F)
					.noCollision()
					.noOcclusion()
					.sound(SoundType.GLASS))
	);

	public static final Item DARK_PORTAL_ITEM = Registry.register(
			BuiltInRegistries.ITEM,
			id("dark_portal"),
			new BlockItem(DARK_PORTAL, new Item.Properties()
					.setId(ResourceKey.create(Registries.ITEM, id("dark_portal")))
					.useBlockDescriptionPrefix())
	);

	public static final Item ZOMBIE_CALOPSITA_SPAWN_EGG = Registry.register(
			BuiltInRegistries.ITEM,
			id("zombie_calopsita_spawn_egg"),
			new SpawnEggItem(new Item.Properties()
					.setId(ResourceKey.create(Registries.ITEM, id("zombie_calopsita_spawn_egg")))
					.spawnEgg(ZOMBIE_CALOPSITA))
	);

	@Override
	public void onInitialize() {
		FabricDefaultAttributeRegistry.register(ZOMBIE_CALOPSITA, ZombieCalopsitaEntity.createAttributes());

		SpawnPlacements.register(
				ZOMBIE_CALOPSITA,
				SpawnPlacementTypes.ON_GROUND,
				Heightmap.Types.MOTION_BLOCKING,
				(type, level, reason, pos, random) -> level.getDifficulty() != Difficulty.PEACEFUL
		);

		CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.SPAWN_EGGS)
				.register(output -> output.accept(ZOMBIE_CALOPSITA_SPAWN_EGG));
		CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.FUNCTIONAL_BLOCKS)
				.register(output -> output.accept(DARK_PORTAL_ITEM));

		LOGGER.info("Calopsita Portal loaded!");
	}

	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}
}
