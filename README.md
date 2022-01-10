# Shard
Shard is a plugin that includes many features.

Currently used in Shard Korea server.

# Features
Features of this plugin.

### Commands
- [X] /vote <kick/map/skipwave/gameover> [targetPlayer/targetMap/targetWave] command
  - Vote for kicking player, changing map, skipping wave, gameover.
- [X] /gameover command (For administrators)
  - Force gameover.
- [X] /announce <message...> command (For administrators)
  - Announce message to all players
- [X] /core [coreID] command
  - Teleport to core or prints core list.

### Others
- [X] Discord chat linking

# Plans
- [ ] Permission managing
- [ ] Verifying user by discord.
- [ ] Localization.

# Plugin installation
Download plugin file from [Releases](https://github.com/devlaq/Shard/releases).
Move downloaded file to config/mods in your server directory.

# Building
If you want to compile the plugin yourself, follow these instructions. **JDK 16** is recommended.

Windows: `gradlew`

Linux/Mac OS: `./gradlew`
