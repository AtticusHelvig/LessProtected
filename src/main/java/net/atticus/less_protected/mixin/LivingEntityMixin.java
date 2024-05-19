package net.atticus.less_protected.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import net.atticus.less_protected.config.ModConfigs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    private boolean shouldModify() {
        if (!((Object) this instanceof PlayerEntity)) {
            return ModConfigs.MODIFY_NON_PLAYERS;
        }
        return true;
    }

    @ModifyArgs(method = "applyArmorToDamage(Lnet/minecraft/entity/damage/DamageSource;F)F", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/DamageUtil;getDamageLeft(FLnet/minecraft/entity/damage/DamageSource;FF)F"))
    private void applyArmorToDamage(Args args) {
        if (shouldModify()) {
            float armor = args.get(2);
            float armorToughness = args.get(3);
            args.set(2, armor * ModConfigs.ARMOR_MODIFIER);
            args.set(3, armorToughness * ModConfigs.ARMOR_TOUGHNESS_MODIFIER);
        }
    }

    @ModifyArg(method = "modifyAppliedDamage(Lnet/minecraft/entity/damage/DamageSource;F)F", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/DamageUtil;getInflictedDamage(FF)F"), index = 1)
    private float modifyAppliedDamage(float protection) {
        if (shouldModify()) {
            return protection * ModConfigs.PROTECTION_MODIFIER;
        }
        return protection;
    }

}
