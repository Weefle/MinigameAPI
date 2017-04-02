package com.florianwoelki.minigameapi.api.util;

import java.util.Arrays;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class ItemBuilder {

	private ItemStack itemStack;

	public ItemBuilder(Material material) {
		this(material, 1);
	}

	public ItemBuilder(ItemStack itemStack) {
		this.itemStack = itemStack;
	}

	public ItemBuilder(Material material, int amount) {
		this.itemStack = new ItemStack(material, amount);
	}

	public ItemBuilder(Material material, int amount, byte durability) {
		this.itemStack = new ItemStack(material, amount, durability);
	}

	public ItemBuilder clone() {
		return new ItemBuilder(itemStack);
	}

	public ItemBuilder setDurability(short durability) {
		itemStack.setDurability(durability);
		return this;
	}

	public ItemBuilder setName(String name) {
		ItemMeta itemMeta = itemStack.getItemMeta();
		itemMeta.setDisplayName(name);
		itemStack.setItemMeta(itemMeta);
		return this;
	}

	public ItemBuilder addUnsafeEnchantment(Enchantment enchantment, int level) {
		itemStack.addUnsafeEnchantment(enchantment, level);
		return this;
	}

	public ItemBuilder removeEnchantment(Enchantment enchantment) {
		itemStack.removeEnchantment(enchantment);
		return this;
	}

	public ItemBuilder setSkullOwner(String owner) {
		SkullMeta im = (SkullMeta) itemStack.getItemMeta();
		im.setOwner(owner);
		itemStack.setItemMeta(im);
		return this;
	}

	public ItemBuilder addEnchant(Enchantment enchantment, int level) {
		ItemMeta im = itemStack.getItemMeta();
		im.addEnchant(enchantment, level, true);
		itemStack.setItemMeta(im);
		return this;
	}

	public ItemBuilder setInfinityDurability() {
		itemStack.setDurability(Short.MAX_VALUE);
		return this;
	}

	public ItemBuilder setLore(String... lore) {
		ItemMeta im = itemStack.getItemMeta();
		im.setLore(Arrays.asList(lore));
		itemStack.setItemMeta(im);
		return this;
	}

	@SuppressWarnings("deprecation")
	public ItemBuilder setDyeColor(DyeColor color) {
		itemStack.setDurability(color.getData());
		return this;
	}

	public ItemStack build() {
		return itemStack;
	}

}
