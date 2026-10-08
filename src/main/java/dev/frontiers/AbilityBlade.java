package dev.frontiers;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

/** Server-authoritative right-click abilities; compile verification pending. */
public final class AbilityBlade extends Item {
    public enum Ability { EMBER, FROST, WIND, LIFE }
    private final Ability ability;
    public AbilityBlade(Properties properties, Ability ability) { super(properties); this.ability = ability; }
    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (player.getCooldowns().isOnCooldown(stack)) return InteractionResult.PASS;
        if (ability == Ability.LIFE && player.getFoodData().getFoodLevel() < 6) return InteractionResult.FAIL;
        if (!level.isClientSide()) {
            int cooldown;
            switch (ability) {
                case EMBER -> {
                    player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 200, 0));
                    player.addEffect(new MobEffectInstance(MobEffects.STRENGTH, 100, 0));
                    cooldown = 600;
                }
                case FROST -> {
                    player.addEffect(new MobEffectInstance(MobEffects.RESISTANCE, 120, 1));
                    player.addEffect(new MobEffectInstance(MobEffects.SLOWNESS, 120, 0));
                    cooldown = 480;
                }
                case WIND -> {
                    player.addEffect(new MobEffectInstance(MobEffects.SPEED, 100, 2));
                    player.addEffect(new MobEffectInstance(MobEffects.JUMP_BOOST, 100, 1));
                    cooldown = 360;
                }
                case LIFE -> {
                    player.getFoodData().setFoodLevel(player.getFoodData().getFoodLevel() - 4);
                    player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 160, 1));
                    cooldown = 800;
                }
                default -> throw new IllegalStateException("Unknown blade ability");
            }
            player.getCooldowns().addCooldown(stack, cooldown);
        }
        return InteractionResult.SUCCESS;
    }
}
