package dev.luminairis.cobblemontransformationrituals.recipe.input;

import com.almostreliable.summoningrituals.recipe.input.BaseEntityInput;
import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.mod.common.pokemon.Species;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import com.cobblemon.mod.common.CobblemonEntities;
import java.util.function.Predicate;

public record PokemonEntityInput(ResourceLocation pokemon, Predicate<Entity> predicate) implements BaseEntityInput {
    public static final Codec<PokemonEntityInput> CODEC = RecordCodecBuilder.create(i -> i.group(
            ResourceLocation.CODEC.fieldOf("pokemon").forGetter(PokemonEntityInput::pokemon)
    ).apply(i, PokemonEntityInput::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, PokemonEntityInput> STREAM_CODEC = StreamCodec.composite(
            ResourceLocation.STREAM_CODEC, PokemonEntityInput::pokemon,
            PokemonEntityInput::new
    );

    public PokemonEntityInput(ResourceLocation pokemon) {
        this(pokemon, $ -> true);
    }

    @Override
    public boolean test(ResourceLocation recipeId, Integer inputIndex, Entity entity) {
        if (entity.isAlive() && entity.getType().equals(CobblemonEntities.POKEMON)) {
            PokemonEntity pkmnEntity = (PokemonEntity) entity;
            Pokemon pkmn = pkmnEntity.getPokemon();
            Species species = pkmn.getSpecies();
            return species.getResourceIdentifier().equals(pokemon) && test(DATA_VALIDATORS, recipeId, inputIndex, entity);
        }
        return false;
    }
}
