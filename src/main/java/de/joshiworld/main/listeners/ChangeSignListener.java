package de.joshiworld.main.listeners;

import de.joshiworld.main.Ozone;
import de.joshiworld.main.config.Money;
import de.joshiworld.main.config.Score;
import de.joshiworld.main.config.ShopSigns;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import me.lucko.luckperms.api.caching.PermissionData;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.block.tileentity.Sign;
import org.spongepowered.api.block.tileentity.TileEntity;
import org.spongepowered.api.block.tileentity.carrier.Chest;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.CauseStackManager.StackFrame;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.InteractBlockEvent;
import org.spongepowered.api.event.block.tileentity.ChangeSignEvent;
import org.spongepowered.api.event.cause.EventContextKeys;
import org.spongepowered.api.event.cause.entity.spawn.SpawnTypes;
import org.spongepowered.api.event.world.ChangeWorldBorderEvent;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.extent.Extent;

/**
 *
 * @author JoshiWorld
 */
public class ChangeSignListener {
    
    private Location chest;
    
    @Listener
    public void onSign(ChangeSignEvent e) {
        Player p = (Player) e.getSource();
        
        //<editor-fold defaultstate="collapsed" desc="Player Signs">
        if(e.getText().lines().get(0).equals(Text.of("[sell]")) ||
                e.getText().lines().get(0).equals(Text.of("[buy]"))) {
            
            
            if(e.getTargetTile().getLocation().sub(1, 0, 0).getBlockType().equals(BlockTypes.CHEST) ||
                    e.getTargetTile().getLocation().sub(1, 0, 0).getBlockType().equals(Sponge.getGame().getRegistry().getType(BlockType.class, "ironchest:iron_chest").get()) ||
                    e.getTargetTile().getLocation().sub(0, 0, 1).getBlockType().equals(BlockTypes.CHEST) ||
                    e.getTargetTile().getLocation().sub(0, 0, 1).getBlockType().equals(Sponge.getGame().getRegistry().getType(BlockType.class, "ironchest:iron_chest").get()) ||
                    e.getTargetTile().getLocation().add(0, 0, 1).getBlockType().equals(BlockTypes.CHEST) ||
                    e.getTargetTile().getLocation().add(0, 0, 1).getBlockType().equals(Sponge.getGame().getRegistry().getType(BlockType.class, "ironchest:iron_chest").get()) ||
                    e.getTargetTile().getLocation().add(1, 0, 0).getBlockType().equals(BlockTypes.CHEST) || 
                    e.getTargetTile().getLocation().add(1, 0, 0).getBlockType().equals(Sponge.getGame().getRegistry().getType(BlockType.class, "ironchest:iron_chest").get())) {
                e.setCancelled(false);

                //<editor-fold defaultstate="collapsed" desc="Verkaufen">
                if(e.getText().lines().get(0).equals(Text.of("[sell]")) &&
                        !e.getText().lines().get(2).isEmpty() && 
                        !e.getText().lines().get(3).isEmpty()) {

                    String price = e.getText().lines().get(2).toPlain();

                    e.getText().setElement(0, Text.of("§0§nZum Verkauf"));

                    if(p.getName().length() > 16) {
                        e.getText().setElement(1, Text.of("§f§l" + p.getName().substring(0, 16)));
                    } else {
                        e.getText().setElement(1, Text.of("§f§l" + p.getName()));
                    }

                    e.getText().setElement(2, Text.of("§c" + price + " §cRubine"));
                    e.getText().setElement(3, e.getText().lines().get(3));
                }
                //</editor-fold>
                
                //<editor-fold defaultstate="collapsed" desc="Kaufen">
                if(e.getText().lines().get(0).equals(Text.of("[buy]")) &&
                        !e.getText().lines().get(2).isEmpty() && 
                        !e.getText().lines().get(3).isEmpty()) {

                    String price = e.getText().lines().get(2).toPlain();

                    e.getText().setElement(0, Text.of("§0§nAnkauf"));

                    if(p.getName().length() > 16) {
                        e.getText().setElement(1, Text.of("§f§l" + p.getName().substring(0, 16)));
                    } else {
                        e.getText().setElement(1, Text.of("§f§l" + p.getName()));
                    }

                    e.getText().setElement(2, Text.of("§c" + price + " §cRubine"));
                    e.getText().setElement(3, e.getText().lines().get(3));
                }
                //</editor-fold>
                
                p.sendMessage(Text.of(Ozone.getPrefix() + " §aShop-Schild erstellt"));
            } else {
                e.setCancelled(true);
                
                //<editor-fold defaultstate="collapsed" desc="Spawn Sign">
                Extent extent = e.getTargetTile().getLocation().getExtent();
                
                p.sendMessage(Text.of(Ozone.getPrefix() + " §cDu kannst Shop-Schilder nur an einer Kiste platzieren!"));
                p.getWorld().setBlock(e.getTargetTile().getLocation().getBlockX(), 
                        e.getTargetTile().getLocation().getBlockY(), 
                        e.getTargetTile().getLocation().getBlockZ(), 
                        BlockState.builder().blockType(BlockTypes.AIR).build());
                
                Entity item = extent.createEntity(EntityTypes.ITEM, e.getTargetTile().getLocation().getPosition());
                item.offer(Keys.REPRESENTED_ITEM, ItemStack.builder().itemType(ItemTypes.SIGN).build().createSnapshot());
                
                try (StackFrame frame = Sponge.getCauseStackManager().pushCauseFrame()) {
                    frame.addContext(EventContextKeys.SPAWN_TYPE, SpawnTypes.PLACEMENT);
                    extent.spawnEntity(item);
                }    
                //</editor-fold>
            }
        } 
        //</editor-fold>
        
        //<editor-fold defaultstate="collapsed" desc="Admin Signs">
        if(e.getText().lines().get(0).equals(Text.of("[admin_s]")) ||
                e.getText().lines().get(0).equals(Text.of("[admin_b]"))) {
            PermissionData pd = Ozone.getPermsApi().getUser(p.getName()).getCachedData().getPermissionData(Ozone.getPermsApi().getContextManager().getApplicableContexts(p));

            if(pd.getPermissionValue("projectozone.money").asBoolean()) {
                e.setCancelled(false);
                
                //<editor-fold defaultstate="collapsed" desc="Admin verkaufen">
                if(e.getText().lines().get(0).equals(Text.of("[admin_s]"))) {
                    String amount = e.getText().lines().get(1).toPlain();
                    String price = e.getText().lines().get(2).toPlain();
                    String item = e.getText().lines().get(3).toPlain();

                    e.getText().setElement(0, Text.of("§4§nAdmin-Sell"));
                    e.getText().setElement(1, Text.of(amount + " Stück"));
                    e.getText().setElement(2, Text.of("§c" + price + " §cRubine"));
                    e.getText().setElement(3, Text.of(item));
                    
                    ShopSigns.load();
                    ShopSigns.cfgNode.getNode("admin", "sell", 
                            e.getTargetTile().getWorld().getName() + ", " +
                            e.getTargetTile().getLocation().getBlockX() + ", " +
                            e.getTargetTile().getLocation().getBlockY() + ", " + 
                            e.getTargetTile().getLocation().getBlockZ()).setValue(item);
                    
                    ShopSigns.save();
                }
                //</editor-fold>

                //<editor-fold defaultstate="collapsed" desc="Admin kaufen">
                if(e.getText().lines().get(0).equals(Text.of("[admin_b]"))) {
                    String amount = e.getText().lines().get(1).toPlain();
                    String price = e.getText().lines().get(2).toPlain();
                    String item = e.getText().lines().get(3).toPlain();

                    e.getText().setElement(0, Text.of("§4§nAdmin-Buy"));
                    e.getText().setElement(1, Text.of(amount + " Stück"));
                    e.getText().setElement(2, Text.of("§c" + price + " §cRubine"));
                    e.getText().setElement(3, Text.of(item));
                    
                    ShopSigns.load();
                    ShopSigns.cfgNode.getNode("admin", "buy", 
                            e.getTargetTile().getWorld().getName() + ", " +
                            e.getTargetTile().getLocation().getBlockX() + ", " +
                            e.getTargetTile().getLocation().getBlockY() + ", " + 
                            e.getTargetTile().getLocation().getBlockZ()).setValue(item);
                    
                    ShopSigns.save();
                }
                //</editor-fold>
                
                p.sendMessage(Text.of(Ozone.getPrefix() + " §aAdminshop-Schild erstellt"));
            } else {
                e.setCancelled(true);
                
                //<editor-fold defaultstate="collapsed" desc="Spawn Sign">
                Extent extent = e.getTargetTile().getLocation().getExtent();
                
                p.sendMessage(Text.of(Ozone.getPrefix() + " §cDu kannst Shop-Schilder nur an einer Kiste platzieren!"));
                p.getWorld().setBlock(e.getTargetTile().getLocation().getBlockX(), 
                        e.getTargetTile().getLocation().getBlockY(), 
                        e.getTargetTile().getLocation().getBlockZ(), 
                        BlockState.builder().blockType(BlockTypes.AIR).build());
                
                Entity item = extent.createEntity(EntityTypes.ITEM, e.getTargetTile().getLocation().getPosition());
                item.offer(Keys.REPRESENTED_ITEM, ItemStack.builder().itemType(ItemTypes.SIGN).build().createSnapshot());
                
                try (StackFrame frame = Sponge.getCauseStackManager().pushCauseFrame()) {
                    frame.addContext(EventContextKeys.SPAWN_TYPE, SpawnTypes.PLACEMENT);
                    extent.spawnEntity(item);
                }    
                //</editor-fold>
            }
        }
        //</editor-fold>
    }
    
    @Listener
    public void onSignInteract(InteractBlockEvent.Secondary.MainHand e) {
        if(e.getSource() instanceof Player) {
            Player p = (Player) e.getSource();
            
            if(!Ozone.shopsign.containsKey(p)) {
                if(e.getTargetBlock().getLocation().get().hasTileEntity()) {
                    Optional<TileEntity> tile = e.getTargetBlock().getLocation().get().getTileEntity();

                    if(tile.get() instanceof Sign) {
                        Sign s = (Sign) tile.get();

                        //<editor-fold defaultstate="collapsed" desc="Sell">
                        if(s.lines().get(0).equals(Text.of("§0§nZum Verkauf"))) {
                            if(Money.getMoney(p.getName()) < Integer.valueOf(s.lines().get(2).toPlain().replaceAll("\\D+", ""))) {
                                p.sendMessage(Text.of(Ozone.getPrefix() + " §cDu hast nicht genug Rubine, um dir das zu kaufen!"));
                            } else {
                                List<Location> pChests = new ArrayList<>();
                                pChests.add(e.getTargetBlock().getLocation().get().add(1, 0, 0));
                                pChests.add(e.getTargetBlock().getLocation().get().add(0, 0, 1));
                                pChests.add(e.getTargetBlock().getLocation().get().sub(1, 0, 0));
                                pChests.add(e.getTargetBlock().getLocation().get().sub(0, 0, 1));

                                for(Location chestLoc : pChests) {
                                    if(chestLoc.getBlockType().equals(BlockTypes.CHEST)) {
                                        chest = chestLoc;
                                    }
                                }

                                for(BlockType blockType : Sponge.getRegistry().getAllOf(BlockType.class)) {
                                    String id = blockType.getId();

                                    if(id.equalsIgnoreCase(s.lines().get(3).toPlain())) {
                                        p.sendMessage(Text.of(blockType.getName()));
                                    }
                                }

                                if(!Sponge.getRegistry().getAllOf(BlockType.class).contains(Sponge.getGame().getRegistry().getType(BlockType.class, "ironchest:iron_chest").get())) {
                                    Sponge.getRegistry().getAllOf(BlockType.class).add(Sponge.getGame().getRegistry().getType(BlockType.class, "ironchest:iron_chest").get());
                                }

                                Chest ch = (Chest) chest.getTileEntity().get();

                                if(ch.getInventory().totalItems() <= 0) {
                                    p.sendMessage(Text.of(Ozone.getPrefix() + " §cDieser Artikel ist zurzeit nicht erhältlich"));
                                    return;
                                }

                                //<editor-fold defaultstate="collapsed" desc="Chest action">
                                for(Inventory slot : ch.getInventory().slots()) {
                                    Optional<ItemStack> itemOpt = slot.peek();

                                    if(itemOpt.isPresent()) {
                                        ItemStack item = itemOpt.get();

                                        if(!item.getType().equals(ItemTypes.AIR)) {
                                            ItemType type = item.getType();

                                            if(item.getQuantity() > 1) {
                                                type = item.getType();
                                                item.setQuantity(item.getQuantity() - 1);
                                            } else {
                                                type = item.getItem();
                                                item = ItemStack.of(ItemTypes.AIR, 0);
                                            }

                                            slot.set(item);

                                            p.getInventory().offer(ItemStack.of(type));

                                            Money.addMoney(s.lines().get(1).toPlain().substring(4, s.lines().get(1).toPlain().length()), Integer.valueOf(s.lines().get(2).toPlain().replaceAll("\\D+", "")));
                                            Money.removeMoney(p.getName(), Integer.valueOf(s.lines().get(2).toPlain().replaceAll("\\D+", "")));

                                            p.sendMessage(Text.of(Ozone.getPrefix() + " §aDu hast noch §c" + Money.getMoney(p.getName()) + " §aRubine"));
                                            return;
                                        }
                                    }
                                }
                                //</editor-fold>
                                
                                Score.setScore(p);

                                //<editor-fold defaultstate="collapsed" desc="Old method">
                                /*
                                for(int i = 0; i < 27; i++) {
                                    if(ch.getInventory().totalItems() > 0) {
                                        ItemStack item = ch.getInventory().query(QueryOperationTypes.INVENTORY_PROPERTY.of(SlotIndex.of(i))).peek().get();

                                        if(!item.getType().equals(ItemTypes.AIR)) {
                                            item.setQuantity(item.getQuantity() - 1);

                                            if(item.getQuantity() < 1) {
                                                item = ItemStack.of(ItemTypes.AIR, 0);
                                            }

                                            ch.getInventory().query(QueryOperationTypes.INVENTORY_PROPERTY.of(SlotIndex.of(i))).set(item);

                                            p.getInventory().offer(ItemStack.of(item.getType()));

                                            p.sendMessage(Text.of(item.getType().getTranslation().get() + " §a" + i));
                                            p.sendMessage(Text.of(Ozone.getPrefix() + " §aDu hast noch §c" + Money.getMoney(p.getName()) + " §aRubine"));
                                            return;
                                        } 
                                    } else {
                                        p.sendMessage(Text.of(Ozone.getPrefix() + " §cDieser Artikel ist zurzeit nicht erhältlich"));
                                        return;
                                    }


                                    if(ch.getInventory().query(QueryOperationTypes.INVENTORY_PROPERTY.of(SlotIndex.of(i))).contains(
                                    Sponge.getGame().getRegistry().getType(ItemType.class, "forbidden_arcanus:arcane_gold_ingot").get())) {

                                        ch.getInventory().query(QueryOperationTypes.INVENTORY_PROPERTY.of(SlotIndex.of(i))).set(ItemStack.of(
                                                Sponge.getGame().getRegistry().getType(ItemType.class, "forbidden_arcanus:arcane_gold_ingot").get(), 
                                                ch.getInventory().query(QueryOperationTypes.INVENTORY_PROPERTY.of(SlotIndex.of(i))).size() - 1));


                                        p.getInventory().query(QueryOperationTypes.INVENTORY_PROPERTY.of(SlotIndex.of(20)))
                                                .offer(ItemStack.of(Sponge.getGame().getRegistry().getType(ItemType.class, "forbidden_arcanus:arcane_gold_ingot").get()));


                                        p.sendMessage(Text.of(Sponge.getGame().getRegistry().getType(ItemType.class, 
                                                ch.getInventory().query(QueryOperationTypes.INVENTORY_PROPERTY.of(SlotIndex.of(i))).poll().get().getType().getId())
                                                .get().getTranslation().get()));
                                        p.sendMessage(Text.of(Ozone.getPrefix() + " §aDu hast noch §c" + Money.getMoney(p.getName()) + " §aRubine"));
                                        return;
                                    }

                                }
                                */

                                //Sponge.getCommandManager().process(p.getCommandSource().get(), "money");
                                //</editor-fold>
                                
                                e.setCancelled(true);
                            }
                        }
                        //</editor-fold>

                        //<editor-fold defaultstate="collapsed" desc="Buy">
                        if(s.lines().get(0).equals(Text.of("§0§nAnkauf"))) {
                            if(Money.getMoney(s.lines().get(1).toPlain().substring(4, s.lines().get(1).toPlain().length())) < Integer.valueOf(s.lines().get(2).toPlain().replaceAll("\\D+", ""))) {
                                p.sendMessage(Text.of(Ozone.getPrefix() + " §cDieser Spieler hat nicht genügend Geld, um dir das abzukaufen!"));
                            } else {
                                List<Location> pChests = new ArrayList<>();
                                pChests.add(e.getTargetBlock().getLocation().get().add(1, 0, 0));
                                pChests.add(e.getTargetBlock().getLocation().get().add(0, 0, 1));
                                pChests.add(e.getTargetBlock().getLocation().get().sub(1, 0, 0));
                                pChests.add(e.getTargetBlock().getLocation().get().sub(0, 0, 1));

                                for(Location chestLoc : pChests) {
                                    if(chestLoc.getBlockType().equals(BlockTypes.CHEST)) {
                                        chest = chestLoc;
                                    }
                                }

                                for(BlockType blockType : Sponge.getRegistry().getAllOf(BlockType.class)) {
                                    String id = blockType.getId();

                                    if(id.equalsIgnoreCase(s.lines().get(3).toPlain())) {
                                        p.sendMessage(Text.of(blockType.getName()));
                                    }
                                }

                                Chest ch = (Chest) chest.getTileEntity().get();

                                if(!p.getItemInHand(HandTypes.MAIN_HAND).isPresent() || p.getItemInHand(HandTypes.MAIN_HAND).get().equalTo(ItemStack.of(ItemTypes.AIR))) {
                                    p.sendMessage(Text.of(Ozone.getPrefix() + " §cDu musst ein Item in der Hand haben!"));
                                    return;
                                }

                                //<editor-fold defaultstate="collapsed" desc="Chest action">
                                for(Inventory slot : ch.getInventory().slots()) {
                                    Optional<ItemStack> itemOpt = slot.peek();

                                    if(!itemOpt.isPresent()) {
                                        ItemStack item = p.getItemInHand(HandTypes.MAIN_HAND).get();

                                        if(!item.getType().equals(ItemTypes.AIR)) {
                                            ItemType type = item.getType();

                                            if(item.getQuantity() > 1) {
                                                type = item.getType();
                                                item.setQuantity(item.getQuantity() - 1);
                                            } else {
                                                type = item.getItem();
                                                item = ItemStack.of(ItemTypes.AIR, 0);
                                            }

                                            slot.offer(ItemStack.of(type));

                                            p.setItemInHand(HandTypes.MAIN_HAND, item);

                                            Money.addMoney(p.getName(), Integer.valueOf(s.lines().get(2).toPlain().replaceAll("\\D+", "")));

                                            p.sendMessage(Text.of(Ozone.getPrefix() + " §aDu hast jetzt §c" + Money.getMoney(p.getName()) + " §aRubine"));
                                            return;
                                        }
                                    } else {
                                        ItemStack itemHand = p.getItemInHand(HandTypes.MAIN_HAND).get();
                                        ItemStack item = itemOpt.get();

                                        if(item.getType().equals(itemHand.getType())) {
                                            if(itemHand.getQuantity() > 1) {
                                                itemHand.setQuantity(itemHand.getQuantity() - 1);
                                            } else {
                                                itemHand = ItemStack.of(ItemTypes.AIR, 0);
                                            }

                                            if(item.getQuantity() < item.getMaxStackQuantity()) {
                                                item.setQuantity(item.getQuantity() + 1);
                                            } else {
                                                p.sendMessage(Text.of(Ozone.getPrefix() + " §cDas Lager dieses Spielers ist voll!"));
                                                return;
                                            }

                                            slot.set(item);

                                            p.setItemInHand(HandTypes.MAIN_HAND, itemHand);

                                            Money.addMoney(p.getName(), Integer.valueOf(s.lines().get(2).toPlain().replaceAll("\\D+", "")));

                                            p.sendMessage(Text.of(Ozone.getPrefix() + " §aDu hast jetzt §c" + Money.getMoney(p.getName()) + " §aRubine"));
                                            return;
                                        } 
                                    }
                                }
                                //</editor-fold>
                                
                                Score.setScore(p);
                                
                                e.setCancelled(true);
                            }
                        }
                        //</editor-fold>

                        //<editor-fold defaultstate="collapsed" desc="Admin Sell">
                        if(s.lines().get(0).equals(Text.of("§4§nAdmin-Sell"))) {
                            if(Money.getMoney(p.getName()) < Integer.valueOf(s.lines().get(2).toPlain().replaceAll("\\D+", ""))) {
                                p.sendMessage(Text.of(Ozone.getPrefix() + " §cDu hast nicht genug Rubine, um dir das zu kaufen!"));
                            } else {
                                String location = ShopSigns.getNode().getNode("admin", "sell", 
                                        s.getWorld().getName() + ", " +
                                        s.getLocation().getBlockX() + ", " +
                                        s.getLocation().getBlockY() + ", " +
                                        s.getLocation().getBlockZ()).getString();
                                ItemType type = Sponge.getRegistry().getType(ItemType.class, location).get();

                                p.getInventory().offer(ItemStack.of(type, Integer.valueOf(s.lines().get(1).toPlain().replaceAll("\\D+", ""))));

                                Money.removeMoney(p.getName(), Integer.valueOf(s.lines().get(2).toPlain().replaceAll("\\D+", "")));

                                p.sendMessage(Text.of(Ozone.getPrefix() + " §aDu hast noch §c" + Money.getMoney(p.getName()) + " §aRubine"));
                                
                                Score.setScore(p);
                            }
                            
                            e.setCancelled(true);
                        }
                        //</editor-fold>

                        //<editor-fold defaultstate="collapsed" desc="Admin Buy">
                        if(s.lines().get(0).equals(Text.of("§4§nAdmin-Buy"))) {
                            if (!p.getItemInHand(HandTypes.MAIN_HAND).isPresent() || p.getItemInHand(HandTypes.MAIN_HAND).get().equalTo(ItemStack.of(ItemTypes.AIR))) {
                                p.sendMessage(Text.of(Ozone.getPrefix() + " §cDu musst ein Item in der Hand haben!"));
                                return;
                            }

                            String location = ShopSigns.getNode().getNode("admin", "buy", 
                                    s.getWorld().getName() + ", " +
                                    s.getLocation().getBlockX() + ", " +
                                    s.getLocation().getBlockY() + ", " +
                                    s.getLocation().getBlockZ()).getString();
                            ItemType type = Sponge.getRegistry().getType(ItemType.class, location).get();

                            if(p.getItemInHand(HandTypes.MAIN_HAND).get().getType().equals(type)) {
                                ItemStack item = p.getItemInHand(HandTypes.MAIN_HAND).get();

                                if(item.getQuantity() < Integer.valueOf(s.lines().get(1).toPlain().replaceAll("\\D+", ""))) {
                                    p.sendMessage(Text.of(Ozone.getPrefix() + " §cDu hast zu wenig von diesem Artikel"));
                                } else {
                                    item.setQuantity(item.getQuantity() - Integer.valueOf(s.lines().get(1).toPlain().replaceAll("\\D+", "")));

                                    if(item.getQuantity() < 1) {
                                        item = ItemStack.of(ItemTypes.AIR, 0);
                                    }

                                    p.setItemInHand(HandTypes.MAIN_HAND, item);

                                    Money.addMoney(p.getName(), Integer.valueOf(s.lines().get(2).toPlain().replaceAll("\\D+", "")));
                                    p.sendMessage(Text.of(Ozone.getPrefix() + " §aDu hast jetzt §c" + Money.getMoney(p.getName()) + " §aRubine"));
                                    Score.setScore(p);
                                }
                            } else {
                                p.sendMessage(Text.of(Ozone.getPrefix() + " §cDiesen Artikel kannst du hier nicht verkaufen"));
                            }
                            
                            e.setCancelled(true);
                        }
                        //</editor-fold>
                    }
                }
            }
        }
    }
    
}
