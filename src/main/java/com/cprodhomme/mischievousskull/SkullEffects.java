package com.cprodhomme.mischievousskull;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;

import org.jetbrains.annotations.Nullable;

public final class SkullEffects {
  public static final Identifier RESOURCE_ID = Identifier.of(Mischievousskull.MOD_ID, "skull_effects.json");

  public record Entry(RegistryEntry<StatusEffect> effect, int durationTicks) {
    /**
     * @return duration for {@link StatusEffectInstance}: {@link StatusEffectInstance#INFINITE} when the config uses 0 (infinite).
     */
    public int instanceDuration() {
      return this.durationTicks <= 0 ? StatusEffectInstance.INFINITE : this.durationTicks;
    }
  }

  private static final List<Entry> DEFAULT_ENTRIES = List.of(
    new Entry(StatusEffects.STRENGTH, 0),
    new Entry(StatusEffects.HASTE, 0),
    new Entry(StatusEffects.JUMP_BOOST, 0),
    new Entry(StatusEffects.SPEED, 0),
    new Entry(StatusEffects.NIGHT_VISION, 0),
    new Entry(StatusEffects.SATURATION, 0),
    new Entry(StatusEffects.CONDUIT_POWER, 0),
    new Entry(StatusEffects.OOZING, 0),
    new Entry(StatusEffects.FIRE_RESISTANCE, 0)
  );

  private static final AtomicReference<List<Entry>> ENTRIES = new AtomicReference<>(DEFAULT_ENTRIES);

  private SkullEffects() {
  }

  public static void registerReloadListener() {
    ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(new SimpleSynchronousResourceReloadListener() {
      @Override
      public Identifier getFabricId() {
        return Identifier.of(Mischievousskull.MOD_ID, "skull_effects");
      }

      @Override
      public void reload(ResourceManager manager) {
        ENTRIES.set(load(manager));
      }
    });
  }

  /**
   * Shuffled copy of configured entries (order changes each call).
   */
  public static List<Entry> shuffledEntries() {
    List<Entry> copy = new ArrayList<>(ENTRIES.get());
    Collections.shuffle(copy);
    return copy;
  }

  static List<Entry> load(ResourceManager manager) {
    return manager.getResource(RESOURCE_ID)
      .map(resource -> {
        try (var stream = resource.getInputStream()) {
          String text = new String(stream.readAllBytes(), StandardCharsets.UTF_8);
          return parse(JsonParser.parseString(text));
        } catch (IOException | JsonParseException e) {
          Mischievousskull.LOGGER.warn("Failed to read {}, using defaults: {}", RESOURCE_ID, e.getMessage());
          return DEFAULT_ENTRIES;
        }
      })
      .orElseGet(() -> {
        Mischievousskull.LOGGER.warn("Missing {}, using defaults", RESOURCE_ID);
        return DEFAULT_ENTRIES;
      });
  }

  static List<Entry> parse(com.google.gson.JsonElement root) {
    if (!root.isJsonObject()) {
      Mischievousskull.LOGGER.warn("skull_effects.json must be a JSON object, using defaults");
      return DEFAULT_ENTRIES;
    }
    JsonObject obj = root.getAsJsonObject();
    if (!JsonHelper.hasArray(obj, "effects")) {
      Mischievousskull.LOGGER.warn("skull_effects.json missing \"effects\" array, using defaults");
      return DEFAULT_ENTRIES;
    }
    JsonArray effects = JsonHelper.getArray(obj, "effects");
    List<Entry> out = new ArrayList<>();
    for (int i = 0; i < effects.size(); i++) {
      @Nullable JsonObject el = effects.get(i).isJsonObject() ? effects.get(i).getAsJsonObject() : null;
      if (el == null) {
        Mischievousskull.LOGGER.warn("skull_effects.json effects[{}] is not an object, skipping", i);
        continue;
      }
      String idStr = JsonHelper.getString(el, "id");
      Identifier effectId = Identifier.tryParse(idStr);
      if (effectId == null) {
        Mischievousskull.LOGGER.warn("Invalid effect id \"{}\", skipping", idStr);
        continue;
      }
      int duration = JsonHelper.getInt(el, "duration", 0);
      if (duration < 0) {
        Mischievousskull.LOGGER.warn("Negative duration for {}, skipping", effectId);
        continue;
      }
      var opt = Registries.STATUS_EFFECT.getEntry(effectId);
      if (opt.isEmpty()) {
        Mischievousskull.LOGGER.warn("Unknown status effect \"{}\", skipping", effectId);
        continue;
      }
      out.add(new Entry(opt.get(), duration));
    }
    if (out.isEmpty()) {
      Mischievousskull.LOGGER.warn("skull_effects.json produced no valid entries, using defaults");
      return DEFAULT_ENTRIES;
    }
    return List.copyOf(out);
  }
}
