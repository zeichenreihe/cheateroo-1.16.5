package local.pixy.cheateroo.mixins.csfeature;

import org.spongepowered.asm.mixin.Mixin;

import local.pixy.cheateroo.config.CSToggle;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.Npc;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.village.Merchant;
import net.minecraft.world.World;

@Mixin(MerchantEntity.class)
public abstract class MerchantLeash extends PassiveEntity implements Npc, Merchant {
	protected MerchantLeash(EntityType<? extends PassiveEntity> entityType, World world) {
		super(entityType, world);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public boolean canBeLeashedBy(PlayerEntity player) {
		return CSToggle.MERCHANT_LEASHABLE.getBooleanValue();
	}
}
