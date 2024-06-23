package com.github.burgerguy.hudtweaks.mixin;

import net.minecraft.client.gui.hud.BossBarHud;
import net.minecraft.client.gui.hud.ClientBossBar;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;
import java.util.UUID;

@Mixin(BossBarHud.class)
public interface BossBarHudAccessor {
	@Accessor("bossBars")
	Map<UUID, ClientBossBar> getBossBars();
}
