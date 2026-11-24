package com.calopsitaportal.block;

import com.calopsitaportal.CalopsitaPortalMod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.minecraft.core.BlockPos;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.level.BlockGetter;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DarkPortalBlock extends Block {

	private static final Logger LOGGER = LoggerFactory.getLogger("calopsita_portal");
	private static final long COOLDOWN_TICKS = 100;
	private static final Map<String, Long> LAST_TELEPORT = new HashMap<>();

	public DarkPortalBlock(Properties properties) {
		super(properties);
	}

	@Override
	protected VoxelShape getEntityInsideCollisionShape(BlockState state, BlockGetter level,
												  BlockPos pos, Entity entity) {
		return Shapes.box(0.0, 0.0, 0.0, 1.0, 1.5, 1.0);
	}

	@Override
	protected void entityInside(BlockState state, Level level, BlockPos pos, Entity entity,
								InsideBlockEffectApplier applier, boolean bl) {
		if (level.isClientSide()) {
			return;
		}
		MinecraftServer server = level.getServer();
		if (server == null) {
			return;
		}

		long now = level.getGameTime();
		String cooldownKey = level + "|" + level.dimension().identifier() + "|" + entity.getUUID();
		Long last = LAST_TELEPORT.get(cooldownKey);
		if (last != null && now - last < COOLDOWN_TICKS) {
			return;
		}
		LAST_TELEPORT.put(cooldownKey, now);

		boolean goingToDark = level.dimension() != CalopsitaPortalMod.DARK_LEVEL;
		LOGGER.info("[portal] entityInside: {} em {} -> dark={}", entity.getName().getString(), level.dimension().identifier(), goingToDark);
		ServerLevel dest = server.getLevel(goingToDark ? CalopsitaPortalMod.DARK_LEVEL : Level.OVERWORLD);
		if (dest == null) {
			LOGGER.warn("[portal] destino nulo para {}", goingToDark ? CalopsitaPortalMod.DARK_LEVEL.identifier() : Level.OVERWORLD.identifier());
			return;
		}

		double x;
		double y;
		double z;
		if (goingToDark) {
			dest.getChunk(0, 0);
			int surfaceY = dest.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, 0, 0);
			x = 0.5;
			y = surfaceY + 1.0;
			z = 0.5;
		} else {
			var respawn = dest.getRespawnData();
			BlockPos spawn = respawn.dimension() == Level.OVERWORLD
					? respawn.pos()
					: new BlockPos(0, dest.getHeight(Heightmap.Types.MOTION_BLOCKING, 0, 0), 0);
			x = spawn.getX() + 0.5;
			y = spawn.getY() + 1.0;
			z = spawn.getZ() + 0.5;
		}

		float yaw = entity.getYRot();
		float pitch = entity.getXRot();
		try {
			entity.teleportTo(dest, x, y, z, Set.of(), yaw, pitch, false);
			placeReturnPortal(dest, x, z);
			LOGGER.info("[portal] teleport OK para {} em ({},{},{})", dest.dimension().identifier(), x, y, z);
		} catch (Exception e) {
			LOGGER.error("[portal] falha no teleporte", e);
		}
	}

	private void placeReturnPortal(ServerLevel level, double x, double z) {
		int bx = (int) Math.floor(x) + 2;
		int bz = (int) Math.floor(z);
		int surfaceY = level.getHeight(Heightmap.Types.MOTION_BLOCKING, bx, bz);
		BlockPos portalPos = new BlockPos(bx, surfaceY, bz);
		if (level.getBlockState(portalPos).canBeReplaced()) {
			level.setBlock(portalPos, defaultBlockState(), 3);
		}
	}
}
