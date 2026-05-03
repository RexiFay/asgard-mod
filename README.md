# AsgardMod — Minecraft Forge 1.20.1

Adds two custom god-tier dimensions to Minecraft.

---

## Dimensions

### 🌤️ GOD's Domain
A peaceful, eternal-daylight heavenly realm. No hostile mobs spawn. Ever.

**Biomes:**
| Biome | Description |
|---|---|
| Celestial Meadow | Soft cloud meadows with end-rod particles and divine trees |
| Cloud Plateau | Elevated white cloud plateaus with marble pillars |
| Divine Crystal Forest | Luminous crystal-blue trees; enchant particles drift through the air |
| Sacred Hills | Rolling hills of divine grass — home of the **Grand Castle** structure |

**Key features:**
- Fixed time at noon (`fixed_time: 6000`) — always daytime
- Full ambient light (`ambient_light: 1.0`) — no darkness, no mob spawns
- Terrain built on `divine_stone` with `divine_grass` surface
- Grand Castle structure spawns in Sacred Hills biome

---

### ⚔️ Asgard
A god-like world of jade mountains, golden forests, deep oceans, and mystical phenomena.

**Biomes:**
| Biome | Description |
|---|---|
| Jade Peaks | Giant mountains of jade stone with subtle gold-stone veins |
| Golden Forest | Vast forests of oversized gold-log trees with glowing gold leaves |
| Asgard Ocean | Deep, wide seas with jade-green water and luminous sea life |
| Asgard Plains | Open plains with gold-fleck grass particles — subtle but noticeable |
| Sword Plains | Flat plains with **ancient iron sword statues** jutting 15–30 blocks from the ground |
| Jade River Karst | Guilin-inspired river valleys flanked by tall jade limestone karst pillars |
| Heaven Pillar Forest | Zhangjiajie-inspired floating stone columns shrouded in cloud particles |
| Sky Piercer *(1 per world)* | A massive jade-and-gold plateau loaded with diamonds, emeralds, and precious minerals; extreme biome parameters ensure only one exists per world |

**Key features:**
- 384-block tall terrain with `min_y: -64`
- Gold and jade are *subtle* — veins, flecks, and accents rather than solid gold terrain
- **Convergence Arrays** — rare (1-in-400 chunks) phenomenon where the landscape arranges itself into sacred geometry. 5 types: Spiritual (Fibonacci spiral), Spatial (grid + jade pillars), Cultivation (concentric rings), Artifact Refining (8-point star), Formation (hexagonal)
- **Sword Statues** — ancient iron blades with stone-brick hilts, tapering toward the tip
- **Jade Spikes** — tapered jade-and-gold spires on mountain terrain

---

## Blocks

| Block | Use |
|---|---|
| `divine_stone` | Base stone of GOD's Domain |
| `divine_grass` | Surface grass of GOD's Domain — sky-blue tint |
| `divine_cloud` | Floaty cloud-like block; emits subtle light |
| `celestial_marble` | White marble with gold striping for castle construction |
| `jade_stone` | Base stone of Asgard |
| `jade_block` | Polished jade — surface layer on Jade Peaks and Sky Piercer |
| `gold_stone` | Stone with subtle gold veins — appears in mountain bases |
| `asgard_grass` | Emerald-green surface grass of Asgard |
| `gold_log` | Slightly gold-veined wood for Golden Forest trees |
| `gold_leaves` | Warm golden semi-transparent leaves with faint glow |
| `jade_pillar` | Carved jade column block |
| `sky_piercer_core` | Glowing jade-gold block with embedded rune texture |
| `convergence_node` | Purple-blue glowing crystal block; forms convergence arrays |
| `ancient_iron` | Dark battle-worn iron for sword statues |
| `gods_domain_portal` | Blue glowing portal frame block (right-click to travel) |
| `asgard_portal` | Gold glowing portal frame block (right-click to travel) |

---

## Items

| Item | Use |
|---|---|
| `jade_gem` | Drops from jade ore deposits |
| `convergence_shard` | Found near convergence array nodes |
| `gods_domain_key` | Placeholder key item (for future lock/lore mechanic) |
| `asgard_key` | Placeholder key item |

---

## Portal Crafting

**GOD's Domain Portal Block** (makes 4):
```
D G D
G C G
D G D
```
- `D` = Diamond, `G` = Gold Ingot, `C` = Amethyst Shard

Place the blocks to form a frame, then right-click to enter.

**Asgard Portal Block** (makes 4):
```
N E N
E A E
N E N
```
- `N` = Netherite Ingot, `E` = Emerald, `A` = Amethyst Block

---

## Setup & Building

**Requirements:** JDK 17, Gradle 8+

```bash
git clone https://github.com/RexiFay/asgard-mod.git
cd asgard-mod
./gradlew genIntellijRuns   # or genEclipseRuns
./gradlew runClient
```

**Useful in-game commands:**
```
/locate biome asgardmod:sky_piercer
/locate biome asgardmod:sword_plains
/locate biome asgardmod:jade_peaks
/locate biome asgardmod:sacred_hills
```

---

## TODO / Roadmap

- [ ] Block model JSON files and textures (24 blocks need textures)
- [ ] Grand Castle NBT structure for Sacred Hills
- [ ] Karst pillar worldgen (Guilin-style tall limestone with flat tops)
- [ ] Heaven column worldgen (Zhangjiajie-style floating columns)
- [ ] Gold tree worldgen (large custom tree feature)
- [ ] Divine tree worldgen
- [ ] Loot tables for blocks
- [ ] Advancements for entering each dimension
- [ ] Custom music disc for each dimension

---

## License
MIT
