package eu.glowmc.knockbackffa.utils;

/*
 * Copyright © 2021 HttpSimon. All Rights Reserved.
 * Created: 2021 / 21:06
 */

import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Collections;

public class ItemBuilder {
    private final ItemStack itemStack;
    private final ItemMeta itemMeta;

    public ItemBuilder(Material material) {
        itemStack = new ItemStack(material);
        itemMeta = itemStack.getItemMeta();
    }

    public ItemBuilder(Material material, int subID) {
        itemStack = new ItemStack(material, 1, (short) subID);
        itemMeta = itemStack.getItemMeta();
    }

    public ItemBuilder setDisplayName(String displayName) {
        itemMeta.setDisplayName(displayName);
        return this;
    }

    public ItemBuilder setAmount(int amount) {
        itemStack.setAmount(amount);
        return this;
    }

    public ItemBuilder setLore(String lore) {
        itemMeta.setLore(Collections.singletonList(lore));
        return this;
    }

    public ItemBuilder addItemFlag(ItemFlag itemFlag) {
        itemMeta.addItemFlags(itemFlag);
        return this;
    }

    public ItemBuilder setUnbreakable(boolean unbreakable) {
        itemMeta.spigot().setUnbreakable(unbreakable);
        return this;
    }

    public ItemBuilder givePlayerSkull(Player player) {
        ((SkullMeta) itemMeta).setOwner(player.getName());
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemBuilder setWoolColor(DyeColor woolColor) {
        if (itemStack.equals(Material.WOOL)) {
            itemStack.setDurability(woolColor.getData());
            return this;
        }
        return this;
    }

    public ItemBuilder setLeatherColor(Color leatherColor) {
        if ((itemMeta instanceof LeatherArmorMeta)) {
            ((LeatherArmorMeta) itemStack).setColor(leatherColor);
            return this;
        }
        return this;
    }

    public ItemBuilder addEnchant(Enchantment enchantment, int level) {
        itemMeta.addEnchant(enchantment, level, false);
        return this;
    }

    public ItemStack build() {
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}
