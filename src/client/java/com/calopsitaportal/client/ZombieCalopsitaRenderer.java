package com.calopsitaportal.client;

import com.calopsitaportal.CalopsitaPortalMod;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ParrotRenderer;
import net.minecraft.client.renderer.entity.state.ParrotRenderState;
import net.minecraft.resources.Identifier;

public class ZombieCalopsitaRenderer extends ParrotRenderer {

	private static final Identifier TEXTURE = CalopsitaPortalMod.id("textures/entity/zombie_calopsita.png");

	public ZombieCalopsitaRenderer(EntityRendererProvider.Context context) {
		super(context);
	}

	@Override
	public Identifier getTextureLocation(ParrotRenderState state) {
		return TEXTURE;
	}
}
