package com.calopsitaportal.client;

import com.calopsitaportal.CalopsitaPortalMod;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class CalopsitaPortalClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		EntityRendererRegistry.register(CalopsitaPortalMod.ZOMBIE_CALOPSITA, ZombieCalopsitaRenderer::new);
	}
}
