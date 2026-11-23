package com.calopsitaportal.block;

import com.calopsitaportal.CalopsitaPortalMod;
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
import java.util.UUID;

public class DarkPortalBlock extends Block {

	private static final long COOLDOWN_TICKS = 100;
	private static final Map<UUID, Long> LAST_TELEPORT = new HashMap<>();

	public DarkPortalBlock(Properties properties) {
		super(properties);
	}

	@Override
	protected VoxelShape getEntityInsideCollisionShape(BlockState state, BlockGetter level,
												  BlockPos pos, Entity entity) {
		return Shapes.block();
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
		Long last = LAST_TELEPORT.get(entity.getUUID());
		if (last != null && now - last < COOLDOWN_TICKS) {
			return;
		}
		LAST_TELEPORT.put(entity.getUUID(), now);

		boolean goingToDark = level.dimension() != CalopsitaPortalMod.DARK_LEVEL;
		ServerLevel dest = server.getLevel(goingToDark ? CalopsitaPortalMod.DARK_LEVEL : Level.OVERWORLD);
		if (dest == null) {
			return;
		}

		double x;
		double y;
		double z;
		if (goingToDark) {
			int surfaceY = dest.getHeight(Heightmap.Types.MOTION_BLOCKING, 0, 0);
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
		entity.teleportTo(dest, x, y, z, Set.of(), yaw, pitch, false);

		placeReturnPortal(dest, x, z);
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
