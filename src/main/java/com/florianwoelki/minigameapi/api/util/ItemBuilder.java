package com.florianwoelki.minigameapi.api.util;

import java.util.Arrays;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

/**
 * This class represents a ItemBuilder.
 * 
 * You can use this ItemBuilder to build items with everything you need.
 */
public class ItemBuilder {

	private ItemStack itemStack;

	/**
	 * Constructor with a specific material.
	 * 
	 * @param material
	 *            the material of the ItemStack
	 */
	public ItemBuilder(Material material) {
		this(material, 1);
	}

	/**
	 * Constructor with a specific ItemStack.
	 * 
	 * @param itemStack
	 *            the item stack which will be used for further transform
	 */
	public ItemBuilder(ItemStack itemStack) {
		this.itemStack = itemStack;
	}

	/**
	 * Constructor with a specific material and amount of the ItemStack.
	 * 
	 * @param material
	 *            the material of the ItemStack
	 * @param amount
	 *            the amount amount of the ItemStack
	 */
	public ItemBuilder(Material material, int amount) {
		this.itemStack = new ItemStack(material, amount);
	}

	/**
	 * Constructor with a specific material, amount and durability of the ItemStack.
	 * 
	 * @param material
	 *            the material of the ItemStack
	 * @param amount
	 *            the amount amount of the ItemStack
	 * @param durability
	 *            the durability for the durability of the ItemStack
	 */
	public ItemBuilder(Material material, int amount, byte durability) {
		this.itemStack = new ItemStack(material, amount, durability);
	}

	/**
	 * With this method you can clone the ItemStack.
	 */
	public ItemBuilder clone() {
		return new ItemBuilder(itemStack);
	}

	/**
	 * Set the durability of the ItemStack.
	 * 
	 * @param durability
	 *            the durability for the durability of the ItemStack
	 * @return the item builder with the given durability
	 */
	public ItemBuilder setDurability(short durability) {
		itemStack.setDurability(durability);
		return this;
	}

	/**
	 * Set the name of the ItemStack.
	 * 
	 * @param name
	 *            the name for the name of the ItemStack
	 * @return the item builder with the given name
	 */
	public ItemBuilder setName(String name) {
		ItemMeta itemMeta = itemStack.getItemMeta();
		itemMeta.setDisplayName(name);
		itemStack.setItemMeta(itemMeta);
		return this;
	}

	/**
	 * Add a unsafe enchantment for a ItemStack with a given level.
	 * 
	 * @param enchantment
	 *            the enchantment that need to be added to the ItemStack
	 * @param level
	 *            the level of the enchantment
	 * @return the item builder with the given add unsafe enchantment
	 */
	public ItemBuilder addUnsafeEnchantment(Enchantment enchantment, int level) {
		itemStack.addUnsafeEnchantment(enchantment, level);
		return this;
	}

	/**
	 * With this method you can remove an enchantment of the ItemStack.
	 * 
	 * @param enchantment
	 *            the enchantment that needs to be removed
	 * @return the item builder with removed enchantment of the ItemStack
	 */
	public ItemBuilder removeEnchantment(Enchantment enchantment) {
		itemStack.removeEnchantment(enchantment);
		return this;
	}

	/**
	 * Set the skull owner of the ItemStack.
	 * 
	 * This method is good for getting heads of specific players
	 * 
	 * @param owner
	 *            the owner of this head
	 * @return the item builder with given skull owner
	 */
	public ItemBuilder setSkullOwner(String owner) {
		SkullMeta im = (SkullMeta) itemStack.getItemMeta();
		im.setOwner(owner);
		itemStack.setItemMeta(im);
		return this;
	}

	/**
	 * Add a enchantment to the ItemStack with a specific level.
	 * 
	 * @param enchantment
	 *            the enchantment that needs to be added to the ItemStack
	 * @param level
	 *            the level for the enchantment
	 * @return the item builder with added enchantment
	 */
	public ItemBuilder addEnchantment(Enchantment enchantment, int level) {
		ItemMeta im = itemStack.getItemMeta();
		im.addEnchant(enchantment, level, true);
		itemStack.setItemMeta(im);
		return this;
	}

	/**
	 * Set infinite ability to the ItemStack.
	 * 
	 * @return the item builder with infinite durability on the ItemStack
	 */
	public ItemBuilder setInfinityDurability() {
		itemStack.setDurability(Short.MAX_VALUE);
		return this;
	}

	/**
	 * Set the lore for the ItemStack.
	 * 
	 * @param lore
	 *            the lore for the specific lore to be added
	 * @return the item builder with the given lore
	 */
	public ItemBuilder setLore(String... lore) {
		ItemMeta im = itemStack.getItemMeta();
		im.setLore(Arrays.asList(lore));
		itemStack.setItemMeta(im);
		return this;
	}

	/**
	 * Set the dye color of the ItemStack.
	 * 
	 * @param color
	 *            the color for the ItemStack
	 * @return the item builder with the colored ItemStack
	 */
	@SuppressWarnings("deprecation")
	public ItemBuilder setDyeColor(DyeColor color) {
		itemStack.setDurability(color.getData());
		return this;
	}

	/**
	 * Build the ItemStack with all given properties.
	 * 
	 * @return the item stack with all properties
	 */
	public ItemStack build() {
		return itemStack;
	}

}
