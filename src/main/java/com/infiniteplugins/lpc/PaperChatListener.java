package com.infiniteplugins.lpc;

import io.papermc.paper.chat.ChatRenderer;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

final class PaperChatListener implements Listener {

	private static final LegacyComponentSerializer LEGACY = LegacyComponentSerializer.legacySection();
	private static final MiniMessage MINI = MiniMessage.miniMessage();

	private final LPC plugin;

	PaperChatListener(final LPC plugin) {
		this.plugin = plugin;
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onChat(final AsyncChatEvent event) {
		final Player player = event.getPlayer();
		final String format = plugin.buildFormat(player);
		if (format == null) {
			return;
		}

		final boolean mini = player.hasPermission("lpc.minimessage");
		final String raw = LEGACY.serialize(event.message());
		final String processed = plugin.processMessage(player, mini ? raw : MINI.stripTags(raw));
		event.message(mini ? MINI.deserialize(plugin.legacyToMini(processed)) : LEGACY.deserialize(processed));

		final Component rendered = MINI.deserialize(plugin.legacyToMini(format.replace("{message}", processed)));
		event.renderer(new ChatRenderer() {
			@Override
			public Component render(final Player source, final Component sourceDisplayName, final Component message, final Audience viewer) {
				return rendered;
			}
		});
	}
}
