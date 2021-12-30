package local.pixy.cheateroo.util; // this file is from masa's tweakeroo: fi.dy.masa.tweakeroo.util.InventoryUtils

import java.util.ArrayList;
import java.util.List;

import org.jetbrains.annotations.Nullable;

import fi.dy.masa.malilib.util.Constants;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.packet.c2s.play.UpdateSelectedSlotC2SPacket;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;

import local.pixy.cheateroo.config.FeatureToggle;

/**
 * @author pixy
 * 
 */
public class InventoryUtils {
	private static final List<EquipmentSlot> REPAIR_MODE_SLOTS = new ArrayList<>();

	/**
	 * Returns the equipment type for the given slot number, assuming that the slot
	 * number is for the player's main inventory container
	 * 
	 * @param slotNum
	 * @param player
	 * @return
	 */
	@Nullable
	private static EquipmentSlot getEquipmentTypeForSlot(int slotNum, PlayerEntity player) {
		if (REPAIR_MODE_SLOTS.contains(EquipmentSlot.MAINHAND) && (slotNum - 36) == player.inventory.selectedSlot) {
			return EquipmentSlot.MAINHAND;
		}

		switch (slotNum) {
		case 45:
			return EquipmentSlot.OFFHAND;
		case 5:
			return EquipmentSlot.HEAD;
		case 6:
			return EquipmentSlot.CHEST;
		case 7:
			return EquipmentSlot.LEGS;
		case 8:
			return EquipmentSlot.FEET;
		}

		return null;
	}

	/**
	 * Returns the slot number for the given equipment type in the player's
	 * inventory container
	 * 
	 * @param type
	 * @param player
	 * @return
	 */
	private static int getSlotNumberForEquipmentType(EquipmentSlot type, @Nullable PlayerEntity player) {
		switch (type) {
		case MAINHAND:
			return player != null ? player.inventory.selectedSlot + 36 : -1;
		case OFFHAND:
			return 45;
		case HEAD:
			return 5;
		case CHEST:
			return 6;
		case LEGS:
			return 7;
		case FEET:
			return 8;
		}

		return -1;
	}

	/**
	 * @param player
	 * @param row
	 */
	public static void swapHotbarWithInventoryRow(PlayerEntity player, int row) {
		ScreenHandler container = player.playerScreenHandler;
		row = MathHelper.clamp(row, 0, 2);
		int slot = row * 9 + 9;

		for (int hotbarSlot = 0; hotbarSlot < 9; hotbarSlot++) {
			fi.dy.masa.malilib.util.InventoryUtils.swapSlots(container, slot, hotbarSlot);
			slot++;
		}
	}

	/**
	 * Tries to restock the totem.
	 * 
	 * @param player
	 * @param allowHotbar
	 */
	public static void tryRestockTotem(PlayerEntity player, boolean allowHotbar) {
		if (FeatureToggle.AUTO_TOTEM.getBooleanValue() && !player.isCreative()) {
			if (player.getOffHandStack().isEmpty() && player.currentScreenHandler == player.playerScreenHandler) {
				swapItemToHand(player, Hand.OFF_HAND, fi.dy.masa.malilib.util.InventoryUtils
						.findSlotWithItem(player.currentScreenHandler, new ItemStack(Items.TOTEM_OF_UNDYING), false));
			}
		}
	}

	/**
	 * Returns if the slot's id is >= 36 and <= 44
	 * 
	 * @param slot
	 * @return
	 */
	public static boolean isHotbarSlot(Slot slot) {
		return slot.id >= 36 && slot.id <= 44;
	}

	/**
	 * 
	 * @param player
	 * @param hand
	 * @param slotNumber
	 */
	private static void swapItemToHand(PlayerEntity player, Hand hand, int slotNumber) { // function from tweakeroo
		if (slotNumber != -1 && player.currentScreenHandler == player.playerScreenHandler) {
			MinecraftClient mc = MinecraftClient.getInstance();
			ScreenHandler container = player.playerScreenHandler;

			if (hand == Hand.MAIN_HAND) {
				int currentHotbarSlot = player.inventory.selectedSlot;
				Slot slot = container.getSlot(slotNumber);

				if (slot != null && isHotbarSlot(slot)) {
					player.inventory.selectedSlot = slotNumber - 36;
					mc.getNetworkHandler().sendPacket(new UpdateSelectedSlotC2SPacket(player.inventory.selectedSlot));
				} else {
					mc.interactionManager.clickSlot(container.syncId, slotNumber, currentHotbarSlot,
							SlotActionType.SWAP, mc.player);
				}
			} else if (hand == Hand.OFF_HAND) {
				int currentHotbarSlot = player.inventory.selectedSlot;
				// Swap the requested slot to the current hotbar slot
				mc.interactionManager.clickSlot(container.syncId, slotNumber, currentHotbarSlot, SlotActionType.SWAP,
						mc.player);

				// Swap the requested item from the hotbar slot to the offhand
				mc.interactionManager.clickSlot(container.syncId, 45, currentHotbarSlot, SlotActionType.SWAP,
						mc.player);

				// Swap the original item back to the hotbar slot
				mc.interactionManager.clickSlot(container.syncId, slotNumber, currentHotbarSlot, SlotActionType.SWAP,
						mc.player);
			}
		}
	}

	/**
	 * @param player
	 * @param type
	 * @param sourceSlotNumber
	 */
	private static void swapItemToEquipmentSlot(PlayerEntity player, EquipmentSlot type, int sourceSlotNumber) {
		if (sourceSlotNumber != -1 && player.currentScreenHandler == player.playerScreenHandler) {
			MinecraftClient mc = MinecraftClient.getInstance();
			ScreenHandler container = player.playerScreenHandler;

			if (type == EquipmentSlot.MAINHAND) {
				int currentHotbarSlot = player.inventory.selectedSlot;
				mc.interactionManager.clickSlot(container.syncId, sourceSlotNumber, currentHotbarSlot,
						SlotActionType.SWAP, mc.player);
			} else if (type == EquipmentSlot.OFFHAND) {
				// Use a hotbar slot that isn't the current slot
				int tempSlot = (player.inventory.selectedSlot + 1) % 9;
				// Swap the requested slot to the temp slot
				mc.interactionManager.clickSlot(container.syncId, sourceSlotNumber, tempSlot, SlotActionType.SWAP,
						mc.player);

				// Swap the requested item from the hotbar slot to the offhand
				mc.interactionManager.clickSlot(container.syncId, 45, tempSlot, SlotActionType.SWAP, mc.player);

				// Swap the original item back to the hotbar slot
				mc.interactionManager.clickSlot(container.syncId, sourceSlotNumber, tempSlot, SlotActionType.SWAP,
						mc.player);
			}
			// Armor slots
			else {
				int armorSlot = getSlotNumberForEquipmentType(type, player);
				int tempSlot = (player.inventory.selectedSlot + 1) % 9;
				// Swap the requested slot's item with the temp hotbar slot
				mc.interactionManager.clickSlot(container.syncId, sourceSlotNumber, tempSlot, SlotActionType.SWAP,
						mc.player);
				// Swap the temp hotbar slot with the armor slot
				mc.interactionManager.clickSlot(container.syncId, armorSlot, tempSlot, SlotActionType.SWAP, mc.player);
				// Swap the original item back to the temp hotbar slot
				mc.interactionManager.clickSlot(container.syncId, sourceSlotNumber, tempSlot, SlotActionType.SWAP,
						mc.player);
			}
		}
	}

	/**
	 * cleans BlockEntitiyTag.Items, from a ShulkerBox item
	 * 
	 * @param stack
	 * @return
	 */
	public static boolean cleanUpShulkerBoxNBT(ItemStack stack) {
		boolean changed = false;
		CompoundTag nbt = stack.getTag();

		if (nbt != null) {
			if (nbt.contains("BlockEntityTag", Constants.NBT.TAG_COMPOUND)) {
				CompoundTag tag = nbt.getCompound("BlockEntityTag");

				if (tag.contains("Items", Constants.NBT.TAG_LIST)
						&& tag.getList("Items", Constants.NBT.TAG_COMPOUND).size() == 0) {
					tag.remove("Items");
					changed = true;
				}

				if (tag.isEmpty()) {
					nbt.remove("BlockEntityTag");
				}
			}

			if (nbt.isEmpty()) {
				stack.setTag(null);
				changed = true;
			}
		}

		return changed;
	}
}
