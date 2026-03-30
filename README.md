# Mischievous Skull

## 📖 What's this mod?

The Mischievous Skull mod introduces a new block into the game—a mysterious skull that offers a unique twist. Once destroyed, the skull grants the player a random Level 1 effect (such as Strength, Speed, Haste, etc.) with an infinite duration. This feature is perfect for adventure map creators looking to add an element of unpredictability and strategy, placing these skulls in key locations to enhance the gameplay experience.

The design of the skull is inspired by the [Traits charms]([https://huntshowdown.fandom.com/wiki/World?file=Trait_Charm.jpg](https://huntshowdown.fandom.com/wiki/World#Lootable_Items)) from the game Hunt Showdown, adding a touch of mischievousness to your adventures. Whether you're setting up a challenging quest or simply want to experiment with new gameplay dynamics, the Mischievous Skull mod is sure to add some fun surprises to your world.

## Customizing effects

You can change which status effects the skull can roll, and how long each one lasts, by providing a **data resource** at:

`data/mischievous-skull/skull_effects.json`

That file can come from:

- A **datapack** under your world folder (for example `saves/<world>/datapacks/<your_pack>/data/mischievous-skull/skull_effects.json`).
- A **resource pack** that includes a `data/` tree (the same path under the pack root).

The mod ships a default `skull_effects.json` inside the mod JAR. Packs with higher priority override lower ones, like other Minecraft data. On a dedicated server, run **`/reload`** after changing the file so server data is reloaded.

### Format

- **`effects`**: array of objects.
- **`id`**: status effect identifier (`namespace:path`), e.g. vanilla `minecraft:strength` or another mod’s effect `examplemod:my_effect`.
- **`duration`** (optional): duration in **game ticks** (20 ticks = 1 second). Omit the field or set it to **`0`** for **infinite** duration. Values greater than `0` apply a timed effect.

If the file is missing, invalid, or `effects` is empty, the mod falls back to its built-in list (the same nine positive effects as before). Unknown effect IDs are skipped with a warning in the log.

### Example (vanilla + timed effect)

```json
{
  "effects": [
    { "id": "minecraft:strength", "duration": 0 },
    { "id": "minecraft:speed", "duration": 200 }
  ]
}
```

Here, Strength can roll with infinite duration, and Speed with 200 ticks (10 seconds) when applied.

### Example (effect from another mod)

Another mod must **register** that status effect and be **loaded** in your instance. Use that mod’s namespace and effect path (check its documentation or data).

```json
{
  "effects": [
    { "id": "minecraft:regeneration", "duration": 0 },
    { "id": "examplemod:my_custom_effect", "duration": 600 }
  ]
}
```

If `examplemod` is not installed or does not register `examplemod:my_custom_effect`, that line is ignored at reload time and a warning is logged; the rest of the list still applies.

## Screenshots

![the skull](https://cdn.modrinth.com/data/8B0Xv5Jn/images/2daae6390ac777e219b2685e4b633b2a54bc2144.png)

The Mischievous Skull

![Random effects with infinite duration](https://cdn.modrinth.com/data/8B0Xv5Jn/images/06ae725d53713e5c1bf22b317fcf2e93faf8ee07.png)
Random effect give by the skull
