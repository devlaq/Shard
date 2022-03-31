package com.github.devlaq.shard.event.wrapper

import arc.util.Nullable
import mindustry.core.GameState
import mindustry.ctype.UnlockableContent
import mindustry.entities.units.UnitCommand
import mindustry.game.Schematic
import mindustry.game.Team
import mindustry.gen.Building
import mindustry.gen.Player
import mindustry.gen.Unit
import mindustry.net.Host
import mindustry.net.NetConnection
import mindustry.type.Item
import mindustry.type.ItemStack
import mindustry.type.Sector
import mindustry.world.Tile

class EventTypes {

    //events that occur very often
    enum class Trigger{
        shock,
        phaseDeflectHit,
        impactPower,
        thoriumReactorOverheat,
        fireExtinguish,
        acceleratorUse,
        newGame,
        tutorialComplete,
        flameAmmo,
        turretCool,
        enablePixelation,
        exclusionDeath,
        suicideBomb,
        openWiki,
        teamCoreDamage,
        socketConfigChanged,
        update,
        draw,
        preDraw,
        postDraw,
        uiDrawBegin,
        uiDrawEnd,
        //before/after bloom used, skybox or planets drawn
        universeDrawBegin,
        //skybox drawn and bloom is enabled - use Vars.renderer.planets
        universeDraw,
        //planets drawn and bloom disabled
        universeDrawEnd
    }

    companion object WinEvent
    class LoseEvent
    class ResizeEvent
    class MapMakeEvent
    class MapPublishEvent
    class SaveLoadEvent
    class ClientCreateEvent
    class ServerLoadEvent
    class DisposeEvent
    class PlayEvent
    class ResetEvent
    class WaveEvent
    class TurnEvent

    /** Called when the player places a line, mobile or desktop. */
    class LineConfirmEvent

    /** Called when a turret receives ammo, but only when the tutorial is active!  */
    class TurretAmmoDeliverEvent

    /** Called when a core receives ammo, but only when the tutorial is active!  */
    class CoreItemDeliverEvent

    /** Called when the player opens info for a specific block. */
    class BlockInfoEvent

    /** Called *after* all content has been initialized.  */
    class ContentInitEvent

    /** Called when the client game is first loaded.  */
    class ClientLoadEvent

    /** Called *after* all the modded files have been added into Vars.tree  */
    class FileTreeInitEvent

    /** Called when a game begins and the world is loaded.  */
    class WorldLoadEvent

    /** Called when a sector is destroyed by waves when you're not there.  */
    class SectorLoseEvent(val sector: Sector)

    /** Called when a sector is destroyed by waves when you're not there.  */
    class SectorInvasionEvent(val sector: Sector)

    class LaunchItemEvent(val stack: ItemStack)

    class SectorLaunchEvent(val sector: Sector)

    class SchematicCreateEvent(val schematic: Schematic)

    class CommandIssueEvent(val tile: Building, val command: UnitCommand)

    class ClientPreConnectEvent(val host: Host)

    class PlayerChatEvent(val player: Player, val message: String)

    /** Called when a sector is conquered, e.g. a boss or base is defeated.  */
    class SectorCaptureEvent(val sector: Sector)

    /** Called when the player withdraws items from a block.  */
    class WithdrawEvent(val tile: Building, val player: Player, val item: Item, val amount: Int)

    /** Called when a player deposits items to a block. */
    class DepositEvent(val tile: Building, val player: Player, val item: Item, val amount: Int)

    /** Called when the player configures a specific building.  */
    class ConfigEvent(val tile: Building, val player: Player, val value: Any)

    /** Called when a player taps any tile.  */
    class TapEvent(val player: Player, val tile: Tile)

    class PickupEvent {
        val carrier: Unit

        @Nullable
        val unit: Unit?

        @Nullable
        val build: Building?

        constructor(carrier: Unit, unit: Unit?) {
            this.carrier = carrier
            this.unit = unit
            build = null
        }

        constructor(carrier: Unit, build: Building?) {
            this.carrier = carrier
            this.build = build
            unit = null
        }
    }

    class UnitControlEvent(val player: Player, @field:Nullable @param:Nullable val unit: Unit)

    class GameOverEvent(val winner: Team)

    class TileChangeEvent(val tile: Tile)

    class StateChangeEvent(val from: GameState.State, val to: GameState.State)

    class UnlockEvent(val content: UnlockableContent)

    class ResearchEvent(val content: UnlockableContent)

    /**
     * Called when block building begins by placing down the ConstructBlock.
     * The tile's block will nearly always be a ConstructBlock.
     */
    class BlockBuildBeginEvent(val tile: Tile, val team: Team, @field:Nullable val unit: Unit, val breaking: Boolean)

    class BlockBuildEndEvent(
        val tile: Tile,
        @field:Nullable @param:Nullable val unit: Unit,
        val team: Team,
        val breaking: Boolean,
        @field:Nullable @param:Nullable val config: Any
    )

    /**
     * Called when a player or drone begins building something.
     * This does not necessarily happen when a new ConstructBlock is created.
     */
    class BuildSelectEvent(val tile: Tile, val team: Team, val builder: Unit, val breaking: Boolean)

    /** Called right before a block is destroyed.
     * The tile entity of the tile in this event cannot be null when this happens. */
    class BlockDestroyEvent(val tile: Tile)

    class UnitDestroyEvent(val unit: Unit)

    class UnitDrownEvent(val unit: Unit)

    /** Called when a unit is created in a reconstructor or factory.  */
    class UnitCreateEvent(val unit: Unit, val spawner: Building)

    /** Called when a unit is dumped from any payload block.  */
    class UnitUnloadEvent(val unit: Unit)

    class UnitChangeEvent(val player: Player, val unit: Unit)

    /** Called when a connection is established to a client.  */
    class ConnectionEvent(val connection: NetConnection)

    /** Called after connecting; when a player receives world data and is ready to play. */
    class PlayerJoin(val player: Player)

    /** Called when a player connects, but has not joined the game yet. */
    class PlayerConnect(val player: Player)

    class PlayerLeave(val player: Player)

    class PlayerBanEvent(@field:Nullable val player: Player, val uuid: String)

    class PlayerUnbanEvent(@field:Nullable val player: Player, val uuid: String)

    class PlayerIpBanEvent(val ip: String)

    class PlayerIpUnbanEvent(val ip: String)

}