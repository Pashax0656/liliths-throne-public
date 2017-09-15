package com.base.rendering;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import com.base.game.KeyboardAction;
import com.base.game.character.GameCharacter;
import com.base.game.character.attributes.ArousalLevel;
import com.base.game.character.attributes.Attribute;
import com.base.game.character.attributes.CorruptionLevel;
import com.base.game.character.attributes.FitnessLevel;
import com.base.game.character.attributes.IntelligenceLevel;
import com.base.game.character.attributes.StrengthLevel;
import com.base.game.character.body.types.PenisType;
import com.base.game.character.body.types.VaginaType;
import com.base.game.character.body.valueEnums.Femininity;
import com.base.game.character.effects.Fetish;
import com.base.game.character.effects.StatusEffect;
import com.base.game.character.npc.NPC;
import com.base.game.dialogue.DialogueNodeOld;
import com.base.game.dialogue.MapDisplay;
import com.base.game.dialogue.eventLog.EventLogEntry;
import com.base.game.dialogue.utils.InventoryDialogue;
import com.base.game.dialogue.utils.NPCInventoryInteraction;
import com.base.game.dialogue.utils.UtilText;
import com.base.game.inventory.AbstractCoreItem;
import com.base.game.inventory.InventorySlot;
import com.base.game.inventory.Rarity;
import com.base.game.inventory.clothing.AbstractClothing;
import com.base.game.inventory.enchanting.TFEssence;
import com.base.game.inventory.item.AbstractItem;
import com.base.game.inventory.weapon.AbstractWeapon;
import com.base.game.sex.Sex;
import com.base.main.Main;
import com.base.utils.Colour;
import com.base.utils.Util;
import com.base.utils.Vector2i;
import com.base.world.places.GenericPlace;

/**
 * @since 0.1.0
 * @version 0.1.85
 * @author Innoxia
 */
public enum RenderingEngine {
	ENGINE;
	
	private static boolean zoomedIn = true, renderedDisabledMap = false;

	private RenderingEngine() {
	}

	private StringBuilder inventorySB = new StringBuilder();
	
	public String getInventoryPanel(GameCharacter charactersInventoryToRender, boolean buyback) {
		return getInventoryDiv(Main.game.getPlayer(), false) + (charactersInventoryToRender==null?getInventoryDivGround():getInventoryDiv(charactersInventoryToRender, buyback));
	}
	
	private String getInventoryDiv(GameCharacter charactersInventoryToRender, boolean buyback) {
		
		inventorySB.setLength(0);
		
		String idModifier = (charactersInventoryToRender.isPlayer()?"PLAYER_":"NPC_");
		AbstractClothing clothing;
		Set<InventorySlot> blockedSlots = new HashSet<>();
		
		for (AbstractClothing c : charactersInventoryToRender.getClothingCurrentlyEquipped()) {
			if (c.getClothingType().getIncompatibleSlots() != null) {
				for (InventorySlot is : c.getClothingType().getIncompatibleSlots()) {
					blockedSlots.add(is);
				}
			}
		}
		
		inventorySB.append("<div class='inventory-container"+(charactersInventoryToRender.isPlayer()?" left":" right")+"'>");

		inventorySB.append(
				"<p style='width:100%; text-align:center; padding:0 margin:0;'>"
					+(charactersInventoryToRender.isPlayer()
						?"<b style='color:"+Femininity.valueOf(charactersInventoryToRender.getFemininity()).getColour().toWebHexString()+";'>Your</b> <b>Inventory</b>"
						:"<b style='color:"+Femininity.valueOf(charactersInventoryToRender.getFemininity()).getColour().toWebHexString()+";'>"+Util.capitaliseSentence(charactersInventoryToRender.getName())+"'s</b> <b>Inventory</b>")
				+"</p>");
		
		
		// EQUIPPED:
		inventorySB.append("<div class='inventory-equipped'>");
		
		// Draw each inventory slot:
		InventorySlot[] inventorySlots = {
				InventorySlot.HEAD, InventorySlot.EYES, InventorySlot.FACE, InventorySlot.MOUTH, InventorySlot.NECK,
				InventorySlot.TORSO_OVER, InventorySlot.TORSO_UNDER, InventorySlot.CHEST, InventorySlot.NIPPLES, InventorySlot.STOMACH,
				InventorySlot.WRIST,  InventorySlot.HAND, InventorySlot.FINGER, InventorySlot.HIPS, InventorySlot.ANUS,
				InventorySlot.LEG, InventorySlot.GROIN, InventorySlot.ANKLE, InventorySlot.FOOT, InventorySlot.SOCK };
		
		for (InventorySlot invSlot : inventorySlots) {
			
			clothing = charactersInventoryToRender.getClothingInSlot(invSlot);
			
			if (clothing != null) {
				// add to content:
				inventorySB.append(
						// If slot is sealed:
						"<div class='inventory-item-slot" + getClassRarityIdentifier(clothing.getRarity()) + "'"
								+ (clothing.isSealed() ? "style='border-width:1vw; border-color:#" + Colour.SEALED.toWebHexString() + "; border-style:solid;'" : "") + ">"
								// If clothing is displaced:
								+ (!clothing.getDisplacedList().isEmpty() ? "<div class='displacedIcon'>" + SVGImages.SVG_IMAGE_PROVIDER.getDisplacedIcon() + "</div>" : "")
								// If clothing is cummed in:
								+ (clothing.isDirty() ? "<div class='cummedIcon'>" + SVGImages.SVG_IMAGE_PROVIDER.getCummedInIcon() + "</div>" : "")
								// If clothing is too masculine:
								+ (clothing.getClothingType().getFemininityMaximum() < charactersInventoryToRender.getFemininity() ? "<div class='femininityIcon'>" + SVGImages.SVG_IMAGE_PROVIDER.getMasculineWarningIcon() + "</div>" : "")
								// If clothing is too feminine:
								+ (clothing.getClothingType().getFemininityMinimum() > charactersInventoryToRender.getFemininity() ? "<div class='femininityIcon'>" + SVGImages.SVG_IMAGE_PROVIDER.getFeminineWarningIcon() + "</div>" : "")

								// Picture:
								+ "<div class='inventory-icon-content'>"+clothing.getSVGString()+"</div>"

								+ "<div class='overlay' id='" + idModifier + invSlot.toString() + "Slot'>" + "</div>" + "</div>");

			} else {
				// add to content:
				if (blockedSlots.contains(invSlot)) {
					inventorySB.append("<div class='inventory-item-slot disabled'><div class='overlay' id='" + idModifier + invSlot.toString() + "Slot'></div></div>");
					
				} else if (invSlot.slotBlockedByRace(charactersInventoryToRender) != null) {
					inventorySB.append(
							"<div class='inventory-item-slot disabled'>"
								+ "<div class='overlay' id='" + idModifier + invSlot.toString() + "Slot'></div>"
								+ "<div class='raceBlockIcon'>" + invSlot.slotBlockedByRace(charactersInventoryToRender).getStatusEffect().getSVGString(charactersInventoryToRender) + "</div>"
							+ "</div>");
					
				} else {
					inventorySB.append("<div class='inventory-item-slot' id='" + idModifier + invSlot.toString() + "Slot'></div>");
				}
			}
		}

		inventorySB.append("</div>");
		
		
		// Right panel:
		inventorySB.append("<div class='inventory-accessories'>");

//		// Weapons:
		// Main weapon:
		if (charactersInventoryToRender.getMainWeapon() != null) {
			inventorySB.append(
					"<div class='inventory-item-slot weapon" + getClassRarityIdentifier(charactersInventoryToRender.getMainWeapon().getRarity()) + "'>"
						+ "<div class='inventory-icon-content'>"+charactersInventoryToRender.getMainWeapon().getSVGString()+"</div>"
						+ "<div class='overlay' id='" + idModifier + InventorySlot.WEAPON_MAIN.toString()+ "Slot'></div>"
					+ "</div>");
		} else {
			inventorySB.append("<div class='inventory-item-slot weapon' id='" + idModifier + InventorySlot.WEAPON_MAIN.toString() + "Slot'></div>");
		}
		
		// Offhand weapon:
		if (charactersInventoryToRender.getOffhandWeapon() != null) {
			inventorySB.append("<div class='inventory-item-slot weapon" + getClassRarityIdentifier(charactersInventoryToRender.getOffhandWeapon().getRarity()) + "'>"
					+ "<div class='inventory-icon-content'>"+charactersInventoryToRender.getOffhandWeapon().getSVGString()+"</div>"
					+ "<div class='overlay' id='" + idModifier + InventorySlot.WEAPON_OFFHAND.toString() + "Slot'></div>"
					+ "</div>");
		} else {
			inventorySB.append("<div class='inventory-item-slot weapon' id='" + idModifier + InventorySlot.WEAPON_OFFHAND.toString() + "Slot'></div>");
		}

		inventorySB.append("<div class='weapon-spacer'></div>");

		// Piercings:
		InventorySlot[] piercingSlots = {
				InventorySlot.PIERCING_EAR,
				InventorySlot.PIERCING_NOSE,
				InventorySlot.PIERCING_TONGUE,
				InventorySlot.PIERCING_LIP,
				InventorySlot.PIERCING_NIPPLE,
				InventorySlot.PIERCING_STOMACH,
				InventorySlot.PIERCING_VAGINA,
				InventorySlot.PIERCING_PENIS };
		for (InventorySlot invSlot : piercingSlots) {

			if (charactersInventoryToRender != null) {
				clothing = charactersInventoryToRender.getClothingInSlot(invSlot);
			} else {
				clothing = null;
			}
			
			if (clothing != null) {
				// add to content:
				inventorySB.append(
						// If slot is sealed:
						"<div class='inventory-item-slot piercing" + getClassRarityIdentifier(clothing.getRarity()) + "'"
								+ (clothing.isSealed() ? "style='height:10vw;width:10vw;border-width:1vw;border-color:#" + Colour.SEALED.toWebHexString() + ";border-style:solid;'" : "") + ">"
								// If clothing is displaced:
								+ (!clothing.getDisplacedList().isEmpty() ? "<div class='displacedIcon'>" + SVGImages.SVG_IMAGE_PROVIDER.getDisplacedIcon() + "</div>" : "")
								// If clothing is cummed in:
								+ (clothing.isDirty() ? "<div class='cummedIcon'>" + SVGImages.SVG_IMAGE_PROVIDER.getCummedInIcon() + "</div>" : "")
								// If clothing is too masculine:
								+ (clothing.getClothingType().getFemininityMaximum() < charactersInventoryToRender.getFemininity() ? "<div class='femininityIcon'>" + SVGImages.SVG_IMAGE_PROVIDER.getMasculineWarningIcon() + "</div>" : "")
								// If clothing is too feminine:
								+ (clothing.getClothingType().getFemininityMinimum() > charactersInventoryToRender.getFemininity() ? "<div class='femininityIcon'>" + SVGImages.SVG_IMAGE_PROVIDER.getFeminineWarningIcon() + "</div>" : "")

								// Picture:
								+ "<div class='inventory-icon-content'>"+clothing.getSVGString()+"</div>"

								+ "<div class='overlay' id='" + idModifier + invSlot.toString() + "Slot'>" + "</div>" + "</div>");

			} else {
				// add to content:
				if (blockedSlots.contains(invSlot))
					inventorySB.append("<div class='inventory-item-slot piercing'><div class='overlay disabled' id='" + idModifier + invSlot.toString() + "Slot'>" + "</div></div>");
				else{
					switch(invSlot){
						case PIERCING_VAGINA:
							if(charactersInventoryToRender.getVaginaType()==VaginaType.NONE || !charactersInventoryToRender.isPiercedVagina())
								inventorySB.append("<div class='inventory-item-slot piercing'><div class='overlay disabled'  id='" + idModifier + invSlot.toString() + "Slot'>" + "</div></div>");
							else
								inventorySB.append("<div class='inventory-item-slot piercing'  id='" + idModifier + invSlot.toString() + "Slot'></div>");
							break;
						case PIERCING_EAR:
							if(!charactersInventoryToRender.isPiercedEar())
								inventorySB.append("<div class='inventory-item-slot piercing'><div class='overlay disabled'  id='" + idModifier + invSlot.toString() + "Slot'>" + "</div></div>");
							else
								inventorySB.append("<div class='inventory-item-slot piercing'  id='" + idModifier + invSlot.toString() + "Slot'></div>");
							break;
						case PIERCING_LIP:
							if(!charactersInventoryToRender.isPiercedLip())
								inventorySB.append("<div class='inventory-item-slot piercing'><div class='overlay disabled'  id='" + idModifier + invSlot.toString() + "Slot'>" + "</div></div>");
							else
								inventorySB.append("<div class='inventory-item-slot piercing'  id='" + idModifier + invSlot.toString() + "Slot'></div>");
							break;
						case PIERCING_NIPPLE:
							if(!charactersInventoryToRender.isPiercedNipple())
								inventorySB.append("<div class='inventory-item-slot piercing'><div class='overlay disabled'  id='" + idModifier + invSlot.toString() + "Slot'>" + "</div></div>");
							else
								inventorySB.append("<div class='inventory-item-slot piercing'  id='" + idModifier + invSlot.toString() + "Slot'></div>");
							break;
						case PIERCING_NOSE:
							if(!charactersInventoryToRender.isPiercedNose())
								inventorySB.append("<div class='inventory-item-slot piercing'><div class='overlay disabled'  id='" + idModifier + invSlot.toString() + "Slot'>" + "</div></div>");
							else
								inventorySB.append("<div class='inventory-item-slot piercing'  id='" + idModifier + invSlot.toString() + "Slot'></div>");
							break;
						case PIERCING_PENIS:
							if(charactersInventoryToRender.getPenisType()==PenisType.NONE || !charactersInventoryToRender.isPiercedPenis())
								inventorySB.append("<div class='inventory-item-slot piercing'><div class='overlay disabled'  id='" + idModifier + invSlot.toString() + "Slot'>" + "</div></div>");
							else
								inventorySB.append("<div class='inventory-item-slot piercing'  id='" + idModifier + invSlot.toString() + "Slot'></div>");
							break;
						case PIERCING_STOMACH:
							if(!charactersInventoryToRender.isPiercedNavel())
								inventorySB.append("<div class='inventory-item-slot piercing'><div class='overlay disabled'  id='" + idModifier + invSlot.toString() + "Slot'>" + "</div></div>");
							else
								inventorySB.append("<div class='inventory-item-slot piercing'  id='" + idModifier + invSlot.toString() + "Slot'></div>");
							break;
						case PIERCING_TONGUE:
							if(!charactersInventoryToRender.isPiercedTongue())
								inventorySB.append("<div class='inventory-item-slot piercing'><div class='overlay disabled'  id='" + idModifier + invSlot.toString() + "Slot'>" + "</div></div>");
							else
								inventorySB.append("<div class='inventory-item-slot piercing'  id='" + idModifier + invSlot.toString() + "Slot'></div>");
							break;
						default:
							break;
					}
				}
			}
		}
		
		inventorySB.append("<p style='text-align:center; display:inline-block; height:48px; vertical-align: middle; top:0;'>"+UtilText.formatAsMoney(charactersInventoryToRender.getMoney())+"</div>");

		inventorySB.append("<div class='inventory-not-equipped'>");
		if(buyback) {
			for (int i = Main.game.getPlayer().getBuybackStack().size() - 1; i >= 0; i--) {
				
				AbstractCoreItem itemBuyback = Main.game.getPlayer().getBuybackStack().get(i).getAbstractItemSold();

				if (itemBuyback != null) {
					// Clothing:
					int itemPrice = Main.game.getPlayer().getBuybackStack().get(i).getPrice();
					if (itemBuyback instanceof AbstractClothing) {
						inventorySB.append(getBuybackItemPanel(itemBuyback, "CLOTHING_BUYBACK_" + i, itemPrice));

					// Weapon:
					} else if (itemBuyback instanceof AbstractWeapon) {
						inventorySB.append(getBuybackItemPanel(itemBuyback, "WEAPON_BUYBACK_" + i, itemPrice));
						
					// Item:
					} else {
						inventorySB.append(getBuybackItemPanel(itemBuyback, "ITEM_BUYBACK_" + i, itemPrice));
					}
				}
			}

			// Fill space:
			for (int i = 24; i > Main.game.getPlayer().getBuybackStack().size(); i--) {
				inventorySB.append("<div class='inventory-item-slot unequipped'></div>");
			}
			
		} else if(InventoryDialogue.getNPCInventoryInteraction() == NPCInventoryInteraction.TRADING && !charactersInventoryToRender.isPlayer()) {
			// Weapons:
			if (charactersInventoryToRender.getWeaponCount() > 0) {
				for (Entry<AbstractWeapon, Integer> entry : charactersInventoryToRender.getMapOfDuplicateWeapons().entrySet()) {
					inventorySB.append(
							"<div class='inventory-item-slot unequipped " + entry.getKey().getDisplayRarity() + "'>"
								+ "<div class='inventory-icon-content'>"+entry.getKey().getSVGString()+"</div>"
								+"<div class='overlay' id='WEAPON_TRADER_" + entry.getKey().hashCode() + "'>"
									+ getItemCountDiv(entry.getValue())
									+ getItemPriceDiv(entry.getKey().getPrice(((NPC) charactersInventoryToRender).getSellModifier()))
								+ "</div>"
							+ "</div>");
				}
			}
			
			// Clothing:
			if (charactersInventoryToRender.getClothingCount() > 0) {
				for (Entry<AbstractClothing, Integer> entry : charactersInventoryToRender.getMapOfDuplicateClothing().entrySet()) {
					inventorySB.append(
							"<div class='inventory-item-slot unequipped " + entry.getKey().getDisplayRarity() + "'>"
								+ "<div class='inventory-icon-content'>"+entry.getKey().getSVGString()+"</div>"
								+ "<div class='overlay' id='CLOTHING_TRADER_" + entry.getKey().hashCode() + "'>"
										+ getItemCountDiv(entry.getValue())
										+ getItemPriceDiv(entry.getKey().getPrice(((NPC) charactersInventoryToRender).getSellModifier()))
								+ "</div>"
							+ "</div>");
				}
			}
			// Items:
			if (charactersInventoryToRender.getItemCount() > 0) {
				for (Entry<AbstractItem, Integer> entry : charactersInventoryToRender.getMapOfDuplicateItems().entrySet()) {
					inventorySB.append(
							"<div class='inventory-item-slot unequipped " + entry.getKey().getDisplayRarity() + "'>"
								+ "<div class='inventory-icon-content'>"+entry.getKey().getSVGString()+"</div>"
								+ "<div class='overlay' id='ITEM_TRADER_" + entry.getKey().hashCode() + "'>"
										+ getItemCountDiv(entry.getValue())
										+ getItemPriceDiv(entry.getKey().getPrice(((NPC) charactersInventoryToRender).getSellModifier()))
								+ "</div>"
							+ "</div>");
				}
			}
			// Fill space:
			for (int i = charactersInventoryToRender.getMaximumInventorySpace(); i > charactersInventoryToRender.getInventorySlotsTaken(); i--) {
				inventorySB.append("<div class='inventory-item-slot unequipped'></div>");
			}
			
		} else {
				// Weapons:
				if (charactersInventoryToRender.getWeaponCount() > 0) {
					appendDivsForItemsToInventory(charactersInventoryToRender, inventorySB, charactersInventoryToRender.getMapOfDuplicateWeapons(), idModifier+"WEAPON_");
				}
				// Clothing:
				if (charactersInventoryToRender.getClothingCount() > 0) {
					appendDivsForItemsToInventory(charactersInventoryToRender, inventorySB, charactersInventoryToRender.getMapOfDuplicateClothing(), idModifier+"CLOTHING_");
				}
				// Items:
				if (charactersInventoryToRender.getItemCount() > 0) {
					appendDivsForItemsToInventory(charactersInventoryToRender, inventorySB, charactersInventoryToRender.getMapOfDuplicateItems(), idModifier+"ITEM_");
				}
				// Fill space:
				for (int i = charactersInventoryToRender.getMaximumInventorySpace(); i > charactersInventoryToRender.getInventorySlotsTaken(); i--) {
					inventorySB.append("<div class='inventory-item-slot unequipped'></div>");
				}
		}
		inventorySB.append("</div>");
		
		
		inventorySB.append("<div style='float:left; display:block; text-align:center; margin:0 auto; height:36px; padding:8px 0 8px 0;'>");
		for(TFEssence essence : TFEssence.values()) {
			inventorySB.append(
					"<div style='width:28px; display:inline-block; margin:0 4px 0 4px;'>"
						+ "<div class='item-inline " + essence.getRarity().getName() + "'>"
							+ essence.getSVGString()
							+ "<div class='overlay no-pointer' id='ESSENCE_"+essence.hashCode()+"'></div>"
						+ "</div>"
						+ " <div style='display:inline-block; height:20px; vertical-align: middle;'>"
							+ "<b>"+charactersInventoryToRender.getEssenceCount(essence)+"</b>"
						+ "</div>"
					+ "</div>"
					);
		}
		inventorySB.append("</div>");
		
		inventorySB.append("</div>");

		inventorySB.append("</body>");
		
		return inventorySB.toString();
	}
	
	
	@SuppressWarnings("unused")
	private String getInventoryDivGround() {
		
		inventorySB.setLength(0);
		
		inventorySB.append("<div class='inventory-container right'>");

		inventorySB.append(
				"<p style='width:100%; text-align:center; padding:0 margin:0;'>"
					+"<b style='color:"+Colour.BASE_TAN.toWebHexString()+";'>In this Area</b>"
				+"</p>");
		
		
		// EQUIPPED:
		inventorySB.append("<div class='inventory-equipped'>");
		// Draw each inventory slot:
		InventorySlot[] inventorySlots = {
				InventorySlot.HEAD, InventorySlot.EYES, InventorySlot.FACE, InventorySlot.MOUTH, InventorySlot.NECK,
				InventorySlot.TORSO_OVER, InventorySlot.TORSO_UNDER, InventorySlot.CHEST, InventorySlot.NIPPLES, InventorySlot.STOMACH,
				InventorySlot.WRIST,  InventorySlot.HAND, InventorySlot.FINGER, InventorySlot.HIPS, InventorySlot.ANUS,
				InventorySlot.LEG, InventorySlot.GROIN, InventorySlot.ANKLE, InventorySlot.FOOT, InventorySlot.SOCK };
		
		for (InventorySlot invSlot : inventorySlots) {
			inventorySB.append("<div class='inventory-item-slot disabled'></div>");
		}
		inventorySB.append("</div>");
		
		
		// Right panel:
		inventorySB.append("<div class='inventory-accessories'>");

		// Weapons:
		inventorySB.append("<div class='inventory-item-slot weapon disabled'></div>");
		inventorySB.append("<div class='inventory-item-slot weapon disabled'></div>");

		inventorySB.append("<div class='weapon-spacer'></div>");

		// Piercings:
		InventorySlot[] piercingSlots = {
				InventorySlot.PIERCING_EAR,
				InventorySlot.PIERCING_NOSE,
				InventorySlot.PIERCING_TONGUE,
				InventorySlot.PIERCING_LIP,
				InventorySlot.PIERCING_NIPPLE,
				InventorySlot.PIERCING_STOMACH,
				InventorySlot.PIERCING_VAGINA,
				InventorySlot.PIERCING_PENIS };
		for (InventorySlot invSlot : piercingSlots) {
			inventorySB.append("<div class='inventory-item-slot piercing disabled'></div>");
		}
		
		inventorySB.append("<p style='text-align:center; display:inline-block; height:48px; vertical-align: middle; top:0;'>"+UtilText.formatAsMoney(Main.game.getPlayerCell().getInventory().getMoney())+"</div>");

		inventorySB.append("<div class='inventory-not-equipped'>");
		// Weapons:
		if (Main.game.getPlayerCell().getInventory().getWeaponCount() > 0) {
			appendDivsForItemsToInventory(null, inventorySB, Main.game.getPlayerCell().getInventory().getMapOfDuplicateWeapons(), "WEAPON_FLOOR_");
		}
		// Clothing:
		if (Main.game.getPlayerCell().getInventory().getClothingCount() > 0) {
			appendDivsForItemsToInventory(null, inventorySB, Main.game.getPlayerCell().getInventory().getMapOfDuplicateClothing(), "CLOTHING_FLOOR_");
		}
		// Items:
		if (Main.game.getPlayerCell().getInventory().getItemCount() > 0) {
			appendDivsForItemsToInventory(null, inventorySB, Main.game.getPlayerCell().getInventory().getMapOfDuplicateItems(), "ITEM_FLOOR_");
		}
		// Fill space:
		for (int i = Main.game.getPlayerCell().getInventory().getMaximumInventorySpace(); i > Main.game.getPlayerCell().getInventory().getInventorySlotsTaken(); i--) {
			inventorySB.append("<div class='inventory-item-slot unequipped'></div>");
		}
		inventorySB.append("</div>");
		
		
		inventorySB.append("<div style='float:left; display:block; text-align:center; margin:0 auto; height:36px; padding:8px 0 8px 0;'>");
		inventorySB.append("</div>");
		
		inventorySB.append("</div>");

		inventorySB.append("</body>");
		
		return inventorySB.toString();
	}
	
	private static void appendDivsForItemsToInventory(GameCharacter charactersInventoryToRender, StringBuilder stringBuilder, Map<? extends AbstractCoreItem, Integer> map, String idPrefix) {
		for (Entry<? extends AbstractCoreItem, Integer> entry : map.entrySet()) {
			stringBuilder.append(
					"<div class='inventory-item-slot unequipped "+ entry.getKey().getDisplayRarity() + "'>"
							+ "<div class='inventory-icon-content'>"+entry.getKey().getSVGString()+"</div>"
					+ "<div class='overlay"
						+ (charactersInventoryToRender!=null && InventoryDialogue.getNPCInventoryInteraction() == NPCInventoryInteraction.TRADING
							? (InventoryDialogue.getInventoryNPC().willBuy(entry.getKey()) ? "" : " dark")
							: (entry.getKey() instanceof AbstractItem
									?((Main.game.isInSex() && !((AbstractItem)entry.getKey()).isAbleToBeUsedInSex()) || (Main.game.isInCombat() && !((AbstractItem)entry.getKey()).isAbleToBeUsedInCombat())?" disabled":"")
									:(Main.game.isInSex() || Main.game.isInCombat() ?" disabled":"")))
					+ "' id='" + idPrefix + entry.getKey().hashCode() + "'>"
					+ getItemCountDiv(entry.getValue())
					+ (charactersInventoryToRender != null && InventoryDialogue.getNPCInventoryInteraction() == NPCInventoryInteraction.TRADING
						? (InventoryDialogue.getInventoryNPC().willBuy(entry.getKey())
								? getItemPriceDiv(entry.getKey().getPrice(InventoryDialogue.getInventoryNPC().getBuyModifier())) 
								: "") 
							: "")
					+ "</div>" + "</div>");
		}
	}
	
	private static String getItemCountDiv(int amount) {
		if (amount > 1) {
			return "<div class='item-count'><b>x" + amount + "</b></div>";
		}
		return "";
	}
	
	private static String getItemPriceDiv(int price) {
		return "<div class='item-price'>"
				+ UtilText.formatAsItemPrice(price)
			+ "</div>";
	}
	
	private static String getBuybackItemPanel(AbstractCoreItem itemBuyback, String id, int price) {
		return "<div class='inventory-item-slot unequipped " + itemBuyback.getDisplayRarity() + "'>"
					+ "<div class='inventory-icon-content'>"+itemBuyback.getSVGString()+"</div>"
					+ "<div class='overlay' id='" + id + "'>"
						+ getItemPriceDiv(price)
					+ "</div>"
				+ "</div>";
	}

	// DecimalFormat decimalFormatter = new DecimalFormat("#,###");
	private StringBuilder uiAttributeSB = new StringBuilder();

	private static float renderedStrengthValue = 0, renderedIntelligenceValue = 0, renderedFitnessValue = 0,
			renderedHealthValue = 0, renderedManaValue = 0, renderedStaminaValue = 0,
			renderedPlayerCorruptionValue = 0, renderedPartnerCorruptionValue = 0,
			renderedPlayerArousalValue = 0, renderedPartnerArousalValue = 0;

	private DialogueNodeOld renderedDialogueNode = null;

	public void renderAttributesPanel() {
		uiAttributeSB.setLength(0);
		
		uiAttributeSB.append(
				"<body onLoad='scrollEventLogToBottom()'>"
					+ " <script>"
						+"function scrollEventLogToBottom() {document.getElementById('event-log-inner-id').scrollTop = document.getElementById('event-log-inner-id').scrollHeight;}"
					+ "</script>"
					+ "<div class='full'>"
						
						// Name box:
						+ "<div class='attribute-container'>"
							+ "<div class='full-width-container'>"
								+ "<p class='player-name' style='color:"+ Femininity.valueOf(Main.game.getPlayer().getFemininity()).getColour().toWebHexString() + ";'>"
									+ (Main.game.getPlayer().getName().length() == 0 ? (Main.game.getPlayer().getFemininity() <= Femininity.MASCULINE.getMaximumFemininity() ? "Hero" : "Heroine") : Main.game.getPlayer().getName())
								+ "</p>"
								+ "<div class='overlay' id='EXTRA_ATTRIBUTES'></div>"
							+ "</div>"
							+ "<div class='full-width-container' style='margin:0;padding:0;'>"
								+ "<p style='text-align:center;'>"
									+ "<b>Level " + Main.game.getPlayer().getLevel()+ "</b> "
										+ (Main.game.getPlayer().getRaceStage().getName()!=""
											?"<b style='color:"+Main.game.getPlayer().getRaceStage().getColour().toWebHexString()+";'>" + Util.capitaliseSentence(Main.game.getPlayer().getRaceStage().getName())+"</b> ":"")
										+ "<b style='color:"+Main.game.getPlayer().getRace().getColour().toWebHexString()+";'>"
										+ (Main.game.getPlayer().isFeminine()?Util.capitaliseSentence(Main.game.getPlayer().getRace().getSingularFemaleName()):Util.capitaliseSentence(Main.game.getPlayer().getRace().getSingularMaleName()))
										+ "</b>"
								+"</p>"
								+ "<div class='barBackgroundExp'>"
								+ (Main.game.getPlayer().getLevel() != 20
									? "<div style=' mix-blend-mode: difference; width:" + (Main.game.getPlayer().getExperience() / (Main.game.getPlayer().getLevel() * 10f)) * 90 + "vw; height:2vw; background:" + Colour.CLOTHING_BLUE_LIGHT.toWebHexString()
											+ "; float:left; border-radius: 2;'></div>"
									: "<div style=' mix-blend-mode: difference; width:90vw; height:2vw; background:" + Colour.GENERIC_EXCELLENT.toWebHexString() + "; float:left; border-radius: 2;'></div>")
								+ "</div>"
								+ "<div class='overlay' id='PLAYER_" + Attribute.EXPERIENCE.getName() + "' style='cursor:pointer;'></div>"
							+ "</div>"
						+ "</div>");
						
		if(Main.game.isInSex()) {
			
			uiAttributeSB.append(
					"<div class='attribute-container'>"
						+ "<p style='text-align:center;padding:0;margin:0;'>"
							+ "<b style='color:"+Femininity.valueOf(Main.game.getPlayer().getFemininity()).getColour().toWebHexString()+";'>"+Main.game.getPlayer().getName()+"</b>"
							+ "</br>"
							+ "<b>"+(Sex.isPlayerDom()?"Dom":"Sub")+":</b> <b style='color:"+Sex.getSexPacePlayer().getColour().toWebHexString()+";'>"+Util.capitaliseSentence(Sex.getSexPacePlayer().getName())+"</b> | <b>Orgasms:</b> "
								+(Sex.getNumberOfPlayerOrgasms()==0
									?"<b>0</b>"
									:"<b style='"+Colour.GENERIC_ARCANE.toWebHexString()+"'>"+Sex.getNumberOfPlayerOrgasms()+"</b>")
						+ "</p>"
			
						// Arousal:
						+ "<div class='full-width-container' style='margin:8 0 0 0; margin:0; padding:0;'>"
							+ "<div class='statusEffectBackground'>" + ArousalLevel.getArousalLevelFromValue(Main.game.getPlayer().getArousal()).getRelatedStatusEffect().getSVGString(Main.game.getPlayer()) + "</div>"
							+ "<div class='barBackgroundAtt'>"
								+ "<div style='width:" + Main.game.getPlayer().getArousal() * 0.65 + "vw; height:5vw; background:"
									+ ArousalLevel.getArousalLevelFromValue(Main.game.getPlayer().getArousal()).getColour().toWebHexString() + "; float:left; border-radius: 2;'></div>"
							+ "</div>"
							+ "<p style='text-align:center; margin:2vw 0 0 0; padding:0;color:"+ (renderedPlayerArousalValue < Main.game.getPlayer().getArousal()
									? (Colour.GENERIC_GOOD.toWebHexString())
									: (renderedPlayerArousalValue > Main.game.getPlayer().getArousal() ? (Colour.GENERIC_BAD.toWebHexString()) : "default"))+ ";'>"
									+ (int) Math.ceil(Main.game.getPlayer().getArousal())
							+ "</p>"
							+ "<div class='overlay' id='PLAYER_" + Attribute.AROUSAL.getName() + "'></div>"
						+ "</div>"
						
						// Corruption:
						+ "<div class='full-width-container' style='padding:0;'>"
							+ "<div class='statusEffectBackground'>"
								+ CorruptionLevel.getCorruptionLevelFromValue(Main.game.getPlayer().getAttributeValue(Attribute.CORRUPTION)).getRelatedStatusEffect().getSVGString(Main.game.getPlayer()) + "</div>"
							+ "<div class='barBackgroundAtt corruption'>"
								+ "<div style='width:" + Main.game.getPlayer().getAttributeValue(Attribute.CORRUPTION) * 0.65 + "vw; height:5vw; background:"
									+ Attribute.CORRUPTION.getColour().toWebHexString() + "; float:left; border-radius: 2;'></div>"
							+ "</div>"
							+ "<p style='text-align:center; margin:2vw 0 0 0; padding:0;color:" + (renderedPlayerCorruptionValue < Main.game.getPlayer().getAttributeValue(Attribute.CORRUPTION)
									? (Colour.GENERIC_GOOD.toWebHexString())
									: (renderedPlayerCorruptionValue > Main.game.getPlayer().getAttributeValue(Attribute.CORRUPTION) ? (Colour.GENERIC_BAD.toWebHexString()) : "default")) + ";'>"
									+ (int) Math.ceil(Main.game.getPlayer().getAttributeValue(Attribute.CORRUPTION))
							+ "</p>"
							+ "<div class='overlay' id='PLAYER_" + Attribute.CORRUPTION.getName() + "'></div>"
						+ "</div>");
										
			// Status effects:

			boolean hasStatusEffects = false;
			for (Fetish f : Main.game.getPlayer().getFetishes()) {
				uiAttributeSB.append(
						"<div class='statusEffectBackground'>"
								+ f.getSVGString()
								+ "<div class='overlay' id='FETISH_PLAYER_" + f + "'></div>"
						+ "</div>");
				hasStatusEffects = true;
			}
			
			for (StatusEffect se : Main.game.getPlayer().getStatusEffects()) {
				if (se.isSexEffect()) {
					uiAttributeSB.append(
							"<div class='statusEffectBackground'>"
									+ se.getSVGString(Main.game.getPlayer())
									+ "<div class='overlay' id='SE_PLAYER_" + se + "'></div>"
							+ "</div>");
					hasStatusEffects = true;
				}
			}
			if(!hasStatusEffects) {
				uiAttributeSB.append("<p style='text-align:center;padding:0;margin:0;height:12vw;'><b style='color:"+Colour.TEXT_GREY.toWebHexString()+";'>No sex effects</b></p>");
			}
								
			uiAttributeSB.append("</div>"
							
					+ "<div class='attribute-container'>"
						+ "<div class='full-width-container' style='margin:0;padding:0;'>"
							+ "<p style='text-align:center;padding:0;margin:0;'><b style='color:"+Femininity.valueOf(Sex.getPartner().getFemininity()).getColour().toWebHexString()+";'>"
								+Util.capitaliseSentence(Sex.getPartner().getName())+"</b></p>"
							+ "<p style='text-align:center;'>"
								+ (Sex.getPartner().getRaceStage().getName()!=""
									?"<b style='color:"+Sex.getPartner().getRaceStage().getColour().toWebHexString()+";'>" + Util.capitaliseSentence(Sex.getPartner().getRaceStage().getName())+"</b> ":"")
								+ "<b style='color:"+Sex.getPartner().getRace().getColour().toWebHexString()+";'>"
								+ (Sex.getPartner().isFeminine()?Util.capitaliseSentence(Sex.getPartner().getRace().getSingularFemaleName()):Util.capitaliseSentence(Sex.getPartner().getRace().getSingularMaleName()))
								+ "</b>"
							+"</p>"
							+ "<div class='overlay' id='PARTNER_"+Attribute.EXPERIENCE.getName()+"'></div>"
						+ "</div>"
							
						+ "<p style='text-align:center;padding:0;margin:0;'>"
							+ "<b>"+(!Sex.isPlayerDom()?"Dom":"Sub")+":</b> <b style='color:"+Sex.getSexPacePartner().getColour().toWebHexString()+";'>"+Util.capitaliseSentence(Sex.getSexPacePartner().getName())+"</b> | <b>Orgasms:</b> "
								+(Sex.getNumberOfPartnerOrgasms()==0
									?"<b>0</b>"
									:"<b style='"+Colour.GENERIC_ARCANE.toWebHexString()+"'>"+Sex.getNumberOfPartnerOrgasms()+"</b>")
						+ "</p>"
							
						// Partner arousal:
						+ "<div class='full-width-container' style='margin:8 0 0 0; margin:0; padding:0;'>"
							+ "<div class='statusEffectBackground'>" + ArousalLevel.getArousalLevelFromValue(Sex.getPartner().getArousal()).getRelatedStatusEffect().getSVGString(Sex.getPartner()) + "</div>"
							+ "<div class='barBackgroundAtt'>"
								+ "<div style='width:" + Sex.getPartner().getArousal() * 0.65 + "vw; height:5vw; background:"
									+ ArousalLevel.getArousalLevelFromValue(Sex.getPartner().getArousal()).getColour().toWebHexString() + "; float:left; border-radius: 2;'></div>"
							+ "</div>"
							+ "<p style='text-align:center; margin:2vw 0 0 0; padding:0;color:"+ (renderedPartnerArousalValue < Sex.getPartner().getArousal()
									? (Colour.GENERIC_GOOD.toWebHexString())
									: (renderedPartnerArousalValue > Sex.getPartner().getArousal() ? (Colour.GENERIC_BAD.toWebHexString()) : "default"))+ ";'>"
									+ (int) Math.ceil(Sex.getPartner().getArousal())
							+ "</p>"
							+ "<div class='overlay' id='PARTNER_" + Attribute.AROUSAL.getName() + "'></div>"
						+ "</div>"
						
						// Corruption:
						+ "<div class='full-width-container' style='padding:0;'>"
							+ "<div class='statusEffectBackground'>"
								+ CorruptionLevel.getCorruptionLevelFromValue(Sex.getPartner().getAttributeValue(Attribute.CORRUPTION)).getRelatedStatusEffect().getSVGString(Sex.getPartner()) + "</div>"
							+ "<div class='barBackgroundAtt corruption'>"
								+ "<div style='width:" + Sex.getPartner().getAttributeValue(Attribute.CORRUPTION) * 0.65 + "vw; height:5vw; background:"
									+ Attribute.CORRUPTION.getColour().toWebHexString() + "; float:left; border-radius: 2;'></div>"
							+ "</div>"
							+ "<p style='text-align:center; margin:2vw 0 0 0; padding:0;color:" + (renderedPartnerCorruptionValue < Sex.getPartner().getAttributeValue(Attribute.CORRUPTION)
									? (Colour.GENERIC_GOOD.toWebHexString())
									: (renderedPartnerCorruptionValue > Sex.getPartner().getAttributeValue(Attribute.CORRUPTION) ? (Colour.GENERIC_BAD.toWebHexString()) : "default")) + ";'>"
									+ (int) Math.ceil(Sex.getPartner().getAttributeValue(Attribute.CORRUPTION))
							+ "</p>"
							+ "<div class='overlay' id='PARTNER_" + Attribute.CORRUPTION.getName() + "'></div>"
						+ "</div>");
						
				hasStatusEffects = false;
				for (Fetish f : Sex.getPartner().getFetishes()) {
					uiAttributeSB.append(
							"<div class='statusEffectBackground'>"
									+ f.getSVGString()
									+ "<div class='overlay' id='FETISH_PARTNER_" + f + "'></div>"
							+ "</div>");
					hasStatusEffects = true;
				}
				
				for (StatusEffect se : Sex.getPartner().getStatusEffects()) {
					if (se.isSexEffect()) {
						uiAttributeSB.append(
								"<div class='statusEffectBackground'>"
										+ se.getSVGString(Sex.getPartner())
										+ "<div class='overlay' id='SE_PARTNER_" + se + "'></div>"
								+ "</div>");
						hasStatusEffects = true;
					}
				}
				if(!hasStatusEffects) {
					uiAttributeSB.append("<p style='text-align:center;padding:0;margin:0;height:12vw;'><b style='color:"+Colour.TEXT_GREY.toWebHexString()+";'>No sex effects</b></p>");
				}
						
		} else {
		
			uiAttributeSB.append(
					"<div class='attribute-container'>"
						+ "<p style='text-align:center;padding:0;margin:0;'><b>Attributes</b></p>"
			
						// Strength:
						+ "<div class='full-width-container' style='margin:8 0 0 0; margin:0; padding:0;'>"
							+ "<div class='statusEffectBackground'>"
								+ StrengthLevel.getStrengthLevelFromValue(Main.game.getPlayer().getAttributeValue(Attribute.STRENGTH)).getRelatedStatusEffect().getSVGString(Main.game.getPlayer()) + "</div>"
							+ "<div class='barBackgroundAtt'>"
								+ "<div style='width:" + Main.game.getPlayer().getAttributeValue(Attribute.STRENGTH) * 0.65 + "vw; height:5vw; background:" + Colour.GENERIC_ATTRIBUTE.toWebHexString() + "; float:left; border-radius: 2;'></div>"
							+ "</div>"
							+ "<p style='text-align:center; margin:2vw 0 0 0; padding:0;color:"+ (renderedStrengthValue < Main.game.getPlayer().getAttributeValue(Attribute.STRENGTH)
									? (Colour.GENERIC_GOOD.toWebHexString())
									: (renderedStrengthValue > Main.game.getPlayer().getAttributeValue(Attribute.STRENGTH) ? (Colour.GENERIC_BAD.toWebHexString()) : "default"))+ ";'>"
									+ (int) Math.ceil(Main.game.getPlayer().getAttributeValue(Attribute.STRENGTH))
							+ "</p>"
							+ "<div class='overlay' id='PLAYER_" + Attribute.STRENGTH.getName() + "'></div>"
						+ "</div>"
	
						// Intelligence:
						+ "<div class='full-width-container' style='margin:0;padding:0;'>"
							+ "<div class='statusEffectBackground'>"
								+ IntelligenceLevel.getIntelligenceLevelFromValue(Main.game.getPlayer().getAttributeValue(Attribute.INTELLIGENCE)).getRelatedStatusEffect().getSVGString(Main.game.getPlayer()) + "</div>"
							+ "<div class='barBackgroundAtt'>"
								+ "<div style='width:" + Main.game.getPlayer().getAttributeValue(Attribute.INTELLIGENCE) * 0.65 + "vw; height:5vw; background:" + Colour.GENERIC_ATTRIBUTE.toWebHexString() + "; float:left; border-radius: 2;'></div>"
							+ "</div>"
							+ "<p style='text-align:center; margin:2vw 0 0 0; padding:0;color:" + (renderedIntelligenceValue < Main.game.getPlayer().getAttributeValue(Attribute.INTELLIGENCE)
									? (Colour.GENERIC_GOOD.toWebHexString())
									: (renderedIntelligenceValue > Main.game.getPlayer().getAttributeValue(Attribute.INTELLIGENCE) ? (Colour.GENERIC_BAD.toWebHexString()) : "default")) + ";'>"
									+ (int) Math.ceil(Main.game.getPlayer().getAttributeValue(Attribute.INTELLIGENCE))
							+ "</p>"
							+ "<div class='overlay' id='PLAYER_" + Attribute.INTELLIGENCE.getName() + "'></div>"
						+ "</div>"
								
						// Fitness:
						+ "<div class='full-width-container' style='margin:0;padding:0;'>"
							+ "<div class='statusEffectBackground'>"
								+ FitnessLevel.getFitnessLevelFromValue(Main.game.getPlayer().getAttributeValue(Attribute.FITNESS)).getRelatedStatusEffect().getSVGString(Main.game.getPlayer()) + "</div>"
							+ "<div class='barBackgroundAtt'>"
								+ "<div style='width:" + Main.game.getPlayer().getAttributeValue(Attribute.FITNESS) * 0.65 + "vw; height:5vw; background:" + Colour.GENERIC_ATTRIBUTE.toWebHexString() + "; float:left; border-radius: 2;'></div>"
							+ "</div>"
							+ "<p style='text-align:center; margin:2vw 0 0 0; padding:0;color:" + (renderedFitnessValue < Main.game.getPlayer().getAttributeValue(Attribute.FITNESS)
									? (Colour.GENERIC_GOOD.toWebHexString())
									: (renderedFitnessValue > Main.game.getPlayer().getAttributeValue(Attribute.FITNESS) ? (Colour.GENERIC_BAD.toWebHexString()) : "default")) + ";'>"
									+ (int) Math.ceil(Main.game.getPlayer().getAttributeValue(Attribute.FITNESS))
							+ "</p>"
							+ "<div class='overlay' id='PLAYER_" + Attribute.FITNESS.getName() + "'></div>"
						+ "</div>"
								
						// Corruption:
						+ "<div class='full-width-container' style='padding:0;'>"
							+ "<div class='statusEffectBackground'>"
								+ CorruptionLevel.getCorruptionLevelFromValue(Main.game.getPlayer().getAttributeValue(Attribute.CORRUPTION)).getRelatedStatusEffect().getSVGString(Main.game.getPlayer()) + "</div>"
							+ "<div class='barBackgroundAtt corruption'>"
								+ "<div style='width:" + Main.game.getPlayer().getAttributeValue(Attribute.CORRUPTION) * 0.65 + "vw; height:5vw; background:"
									+ Attribute.CORRUPTION.getColour().toWebHexString() + "; float:left; border-radius: 2;'></div>"
							+ "</div>"
							+ "<p style='text-align:center; margin:2vw 0 0 0; padding:0;color:" + (renderedPlayerCorruptionValue < Main.game.getPlayer().getAttributeValue(Attribute.CORRUPTION)
									? (Colour.GENERIC_GOOD.toWebHexString())
									: (renderedPlayerCorruptionValue > Main.game.getPlayer().getAttributeValue(Attribute.CORRUPTION) ? (Colour.GENERIC_BAD.toWebHexString()) : "default")) + ";'>"
									+ (int) Math.ceil(Main.game.getPlayer().getAttributeValue(Attribute.CORRUPTION))
							+ "</p>"
							+ "<div class='overlay' id='PLAYER_" + Attribute.CORRUPTION.getName() + "'></div>"
						+ "</div>"
								
					+ "</div>"
					
					// Health, mana and experience:
					+"<div class='attribute-container'>"
						+ "<p style='text-align:center;padding:0;margin:0;'><b>Combat</b></p>"
						
						+ "<div class='full-width-container' style='margin:0;padding:0;'>"
							+ "<div class='imageIcon'>" + Attribute.HEALTH_MAXIMUM.getSVGString() + "</div>" + "<div class='barBackgroundAtt'>" + "<div style='width:"
							+ (Main.game.getPlayer().getHealth() / Main.game.getPlayer().getAttributeValue(Attribute.HEALTH_MAXIMUM)) * 65 + "vw; height:5vw; background:" + Colour.ATTRIBUTE_HEALTH.toWebHexString()
							+ "; float:left; border-radius: 2;'></div>" + "</div>" + "<p style='text-align:center; margin:1 0 0 0; padding:0;color:"
							+ (renderedHealthValue < Main.game.getPlayer().getHealth() ? Colour.GENERIC_GOOD.toWebHexString() : renderedHealthValue > Main.game.getPlayer().getHealth() ? (Colour.GENERIC_BAD.toWebHexString()) : "default") + ";'>"
							+ (int) Math.ceil(Main.game.getPlayer().getHealth()) + "</p>" + "<div class='overlay' id='PLAYER_" + Attribute.HEALTH_MAXIMUM.getName() + "'></div>"
						+ "</div>"
	
						+ "<div class='full-width-container' style='margin:0;padding:0;'>"
							+ "<div class='imageIcon'>" + Attribute.MANA_MAXIMUM.getSVGString() + "</div>" + "<div class='barBackgroundAtt'>" + "<div style='width:"
							+ (Main.game.getPlayer().getMana() / Main.game.getPlayer().getAttributeValue(Attribute.MANA_MAXIMUM)) * 65 + "vw; height:5vw; background:" + Colour.ATTRIBUTE_MANA.toWebHexString()
							+ "; float:left; border-radius: 2;'></div>" + "</div>" + "<p style='text-align:center; margin:1 0 0 0; padding:0;color:"
							+ (renderedManaValue < Main.game.getPlayer().getMana() ? (Colour.GENERIC_GOOD.toWebHexString()) : (renderedManaValue > Main.game.getPlayer().getMana() ? (Colour.GENERIC_BAD.toWebHexString()) : "default")) + ";'>"
							+ (int) Math.ceil(Main.game.getPlayer().getMana()) + "</p>" + "<div class='overlay' id='PLAYER_" + Attribute.MANA_MAXIMUM.getName() + "'></div>"
						+ "</div>"
	
						+ "<div class='full-width-container' style='margin:0;padding:0;'>"
							+ "<div class='imageIcon'>" + Attribute.STAMINA_MAXIMUM.getSVGString() + "</div>" + "<div class='barBackgroundAtt'>"
							+ "<div style='width:" + (Main.game.getPlayer().getStaminaPercentage() * 65) + "vw; height:5vw; background:" + Colour.ATTRIBUTE_FITNESS.toWebHexString() + "; float:left; border-radius: 2;'></div>" + "</div>"
							+ "<p style='text-align:center; margin:1 0 0 0; padding:0;color:"
							+ (renderedStaminaValue < Main.game.getPlayer().getStamina() ? (Colour.GENERIC_GOOD.toWebHexString()) : (renderedStaminaValue > Main.game.getPlayer().getStamina() ? (Colour.GENERIC_BAD.toWebHexString()) : "default"))
							+ ";'>" + (int) Math.ceil(Main.game.getPlayer().getStamina()) + "</p>" + "<div class='overlay' id='PLAYER_" + Attribute.STAMINA_MAXIMUM.getName() + "'></div>"
						+ "</div>"
						
					+ "</div>");
			
			
			// Status effects:
			uiAttributeSB.append("<div class='attribute-container'>"
									+ "<p style='text-align:center;padding:0;margin:0;'><b>Effects</b></p>");
			
			// Infinite duration:
			for (StatusEffect se : Main.game.getPlayer().getStatusEffects()) {
				if (!se.isCombatEffect() && Main.game.getPlayer().getStatusEffectDuration(se)==-1 && se.renderInEffectsPanel())
					uiAttributeSB.append(
							"<div class='statusEffectBackground" + (se.isCombatEffect() && !se.isBeneficial() ? " negativeCombat" : "") + (se.isCombatEffect() && se.isBeneficial() ? " positiveCombat" : "") + "'>"
									+ se.getSVGString(Main.game.getPlayer())
									+ "<div class='overlay' id='SE_PLAYER_" + se + "'></div>"
							+ "</div>");
			}
			// Timed:
			for (StatusEffect se : Main.game.getPlayer().getStatusEffects()) {
				if (!se.isCombatEffect() && Main.game.getPlayer().getStatusEffectDuration(se)!=-1 && se.renderInEffectsPanel()) {
					int timerHeight = (int) ((Main.game.getPlayer().getStatusEffectDuration(se)/(60*6f))*100);

					Colour timerColour = Colour.STATUS_EFFECT_TIME_HIGH;
					
					if(timerHeight>100) {
						timerHeight=100;
						timerColour = Colour.STATUS_EFFECT_TIME_OVERFLOW;
					} else if(timerHeight<15) {
						timerColour = Colour.STATUS_EFFECT_TIME_LOW;
					} else if (timerHeight<50) {
						timerColour = Colour.STATUS_EFFECT_TIME_MEDIUM;
					}
					
					uiAttributeSB.append(
							"<div class='statusEffectBackground" + (se.isCombatEffect() && !se.isBeneficial() ? " negativeCombat" : "") + (se.isCombatEffect() && se.isBeneficial() ? " positiveCombat" : "") + "'>"
							
									+ "<div style='float:left;width:12vw;'>"+se.getSVGString(Main.game.getPlayer())+"</div>"
									
									+"<div style='position:absolute; right:0; height:100%; width:2vw; background:#222; border-radius: 2;'>"
										+ "<div style='position:absolute; bottom:0; height:"+timerHeight+"%; width:2vw; background:"
										+ timerColour.toWebHexString() + "; float:left; border-radius: 2;'></div>"
									+ "</div>"
									
									+ "<div class='overlay' id='SE_PLAYER_" + se + "'></div>"
									
							+ "</div>");
				}
			}
		}
						
		uiAttributeSB.append(
					"</div>"
					+ "<div class='event-log'>"
					+ "<p style='text-align:center;padding:0;margin:0;'><b>Event Log</b></p>"
						+ "<div class='event-log-inner' id='event-log-inner-id'>");
		
		if(Main.game.getEventLog().isEmpty()) {
			uiAttributeSB.append("<span style='color:"+Colour.TEXT_GREY.toWebHexString()+";'>No events yet...</span>");
		}
		int count = 0;
		if(Main.game.getEventLog().size()>50) {
			for(EventLogEntry event : Main.game.getEventLog().subList(Main.game.getEventLog().size()-50, Main.game.getEventLog().size()-1)) {
				if(count==0) {
					uiAttributeSB.append("<div class='event-log-entry' style='background:#222;'>"+event.getFormattedEntry()+"</div>");
				} else {
					if(count%2==0) {
						uiAttributeSB.append("<div class='event-log-entry' style='background:#222222;'>"+event.getFormattedEntry()+"</div>");
					} else {
						uiAttributeSB.append("<div class='event-log-entry' style='background:#292929;'>"+event.getFormattedEntry()+"</div>");
					}
				}
				count++;
			}
		} else {
			for(EventLogEntry event : Main.game.getEventLog()) {
				if(count==0) {
					uiAttributeSB.append("<div class='event-log-entry' style='background:#222;'>"+event.getFormattedEntry()+"</div>");
				} else {
					if(count%2==0) {
						uiAttributeSB.append("<div class='event-log-entry' style='background:#222222;'>"+event.getFormattedEntry()+"</div>");
					} else {
						uiAttributeSB.append("<div class='event-log-entry' style='background:#292929;'>"+event.getFormattedEntry()+"</div>");
					}
				}
				count++;
			}
		}
		uiAttributeSB.append("</div>"
					+ "</div>"
				+"</div>"
				+ "</body>");

		if (Main.mainController != null) {
			if (Main.game.getCurrentDialogueNode() != null) {
				if (!Main.game.getCurrentDialogueNode().isNoTextForContinuesDialogue() && renderedDialogueNode != Main.game.getCurrentDialogueNode()) {

					renderedHealthValue = Main.game.getPlayer().getHealth();
					renderedManaValue = Main.game.getPlayer().getMana();
					renderedStaminaValue = Main.game.getPlayer().getStamina();
					if (Main.game.isInSex()) {
						renderedPlayerArousalValue = Main.game.getPlayer().getArousal();
						renderedPartnerArousalValue = Sex.getPartner().getArousal();
						renderedPartnerCorruptionValue = Sex.getPartner().getAttributeValue(Attribute.CORRUPTION);
					}
					renderedStrengthValue = Main.game.getPlayer().getAttributeValue(Attribute.STRENGTH);
					renderedIntelligenceValue = Main.game.getPlayer().getAttributeValue(Attribute.INTELLIGENCE);
					renderedFitnessValue = Main.game.getPlayer().getAttributeValue(Attribute.FITNESS);
					renderedPlayerCorruptionValue = Main.game.getPlayer().getAttributeValue(Attribute.CORRUPTION);
					

					renderedDialogueNode = Main.game.getCurrentDialogueNode();

				}
			}
		}
		
		Main.mainController.setAttributePanelContent(uiAttributeSB.toString());
	}

	private StringBuilder mapSB = new StringBuilder();

	public String renderedHTMLMap() {

		mapSB.setLength(0);

		mapSB.append("<div class='map-container'>");

		int mapSize = zoomedIn ? 2 : 3;
		float unit = zoomedIn ? 4.5f : 3f, borderSizeReduction = 2.5f;

		Vector2i playerPosition = Main.game.getPlayer().getLocation();

		// It looks messy, but it works...
		for (int y = playerPosition.getY() + mapSize; y >= playerPosition.getY() - mapSize; y--) {
			for (int x = playerPosition.getX() - mapSize; x <= playerPosition.getX() + mapSize; x++) {
				if (x < Main.game.getActiveWorld().WORLD_WIDTH && x >= 0 && y < Main.game.getActiveWorld().WORLD_HEIGHT && y >= 0) {// If within  bounds of map:

					if (Main.game.getActiveWorld().getCell(x, y).isDiscovered() || Main.game.isDebugMode()) { // If the tile is discovered:

						if (Main.game.getActiveWorld().getCell(x, y).getPlace() == GenericPlace.IMPASSABLE) {
							mapSB.append("<div class='map-tile blank' style='width:" + (4 * unit) + "%; height:" + (4 * unit) + "%;'></div>");
						} else {

							// This is the "move North" tile:
							if (y == playerPosition.getY() + 1 && x == playerPosition.getX() && Main.game.getActiveWorld().getCell(x, y).getPlace() != GenericPlace.IMPASSABLE) {
								if(Main.game.getActiveWorld().getCell(x, y).getPlace().isDangerous()) {
									mapSB.append("<div class='map-tile movement dangerous' id='upButton' style='width:" + (4 * unit - borderSizeReduction) + "%; height:"+ (4 * unit - borderSizeReduction) + "%; border-width:1%;'>");
									
								} else {
									mapSB.append("<div class='map-tile movement' id='upButton' style='width:" + (4 * unit - borderSizeReduction) + "%; height:"+ (4 * unit - borderSizeReduction) + "%; border-width:1%; border-color:"+
												(Main.game.getPlayer().getFemininity()<=Femininity.MASCULINE.getMaximumFemininity()
														?Colour.MASCULINE_PLUS
														:(Main.game.getPlayer().getFemininity()<=Femininity.ANDROGYNOUS.getMaximumFemininity()
																?Colour.ANDROGYNOUS
																		:Colour.FEMININE_PLUS)).toWebHexString()+";'>");
								}
								
								// Put place icon onto tile:
								if (Main.game.getActiveWorld().getCell(x, y).getPlace().getSVGString() != null)
									mapSB.append("<div class='place-icon'>" + Main.game.getActiveWorld().getCell(x, y).getPlace().getSVGString() + "</div>");

								mapSB.append("<b class='hotkey-icon" + (Main.game.getActiveWorld().getCell(x, y).getPlace().isDangerous() ? " dangerous" : "") + "'>"
										+ (Main.getProperties().hotkeyMapPrimary.get(KeyboardAction.MOVE_NORTH) == null ? "" : Main.getProperties().hotkeyMapPrimary.get(KeyboardAction.MOVE_NORTH).getFullName()) + "</b>");
								
								appendNPCIcon(x, y);
								appendItemsInAreaIcon(x, y);
								
								// Close the tile's div:
								mapSB.append("</div>");

								// This is the "move South" tile:
							} else if (y == playerPosition.getY() - 1 && x == playerPosition.getX() && Main.game.getActiveWorld().getCell(x, y).getPlace() != GenericPlace.IMPASSABLE) {
								if(Main.game.getActiveWorld().getCell(x, y).getPlace().isDangerous()) {
									mapSB.append("<div class='map-tile movement dangerous' id='downButton' style='width:" + (4 * unit - borderSizeReduction) + "%; height:"+ (4 * unit - borderSizeReduction) + "%; border-width:1%;'>");
									
								} else {
									mapSB.append("<div class='map-tile movement' id='downButton' style='width:" + (4 * unit - borderSizeReduction) + "%; height:"+ (4 * unit - borderSizeReduction) + "%; border-width:1%; border-color:"+
												(Main.game.getPlayer().getFemininity()<=Femininity.MASCULINE.getMaximumFemininity()
														?Colour.MASCULINE_PLUS
														:(Main.game.getPlayer().getFemininity()<=Femininity.ANDROGYNOUS.getMaximumFemininity()
																?Colour.ANDROGYNOUS
																		:Colour.FEMININE_PLUS)).toWebHexString()+";'>");
								}

								// Put place icon onto tile:
								if (Main.game.getActiveWorld().getCell(x, y).getPlace().getSVGString() != null)
									mapSB.append("<div class='place-icon'>" + Main.game.getActiveWorld().getCell(x, y).getPlace().getSVGString() + "</div>");

								mapSB.append("<b class='hotkey-icon" + (Main.game.getActiveWorld().getCell(x, y).getPlace().isDangerous() ? " dangerous" : "") + "'>"
										+ (Main.getProperties().hotkeyMapPrimary.get(KeyboardAction.MOVE_SOUTH) == null ? "" : Main.getProperties().hotkeyMapPrimary.get(KeyboardAction.MOVE_SOUTH).getFullName()) + "</b>");

								appendNPCIcon(x, y);
								appendItemsInAreaIcon(x, y);
								
								// Close the tile's div:
								mapSB.append("</div>");

								// This is the "move West" tile:
							} else if (y == playerPosition.getY() && x == playerPosition.getX() - 1 && Main.game.getActiveWorld().getCell(x, y).getPlace() != GenericPlace.IMPASSABLE) {
								if(Main.game.getActiveWorld().getCell(x, y).getPlace().isDangerous()) {
									mapSB.append("<div class='map-tile movement dangerous' id='leftButton' style='width:" + (4 * unit - borderSizeReduction) + "%; height:"+ (4 * unit - borderSizeReduction) + "%; border-width:1%;'>");
									
								} else {
									mapSB.append("<div class='map-tile movement' id='leftButton' style='width:" + (4 * unit - borderSizeReduction) + "%; height:"+ (4 * unit - borderSizeReduction) + "%; border-width:1%; border-color:"+
												(Main.game.getPlayer().getFemininity()<=Femininity.MASCULINE.getMaximumFemininity()
														?Colour.MASCULINE_PLUS
														:(Main.game.getPlayer().getFemininity()<=Femininity.ANDROGYNOUS.getMaximumFemininity()
																?Colour.ANDROGYNOUS
																		:Colour.FEMININE_PLUS)).toWebHexString()+";'>");
								}

								// Put place icon onto tile:
								if (Main.game.getActiveWorld().getCell(x, y).getPlace().getSVGString() != null)
									mapSB.append("<div class='place-icon'>" + Main.game.getActiveWorld().getCell(x, y).getPlace().getSVGString() + "</div>");

								mapSB.append("<b class='hotkey-icon" + (Main.game.getActiveWorld().getCell(x, y).getPlace().isDangerous() ? " dangerous" : "") + "'>"
										+ (Main.getProperties().hotkeyMapPrimary.get(KeyboardAction.MOVE_WEST) == null ? "" : Main.getProperties().hotkeyMapPrimary.get(KeyboardAction.MOVE_WEST).getFullName()) + "</b>");

								appendNPCIcon(x, y);
								appendItemsInAreaIcon(x, y);
								
								// Close the tile's div:
								mapSB.append("</div>");

								// This is the "move East" tile:
							} else if (y == playerPosition.getY() && x == playerPosition.getX() + 1 && Main.game.getActiveWorld().getCell(x, y).getPlace() != GenericPlace.IMPASSABLE) {
								if(Main.game.getActiveWorld().getCell(x, y).getPlace().isDangerous()) {
									mapSB.append("<div class='map-tile movement dangerous' id='rightButton' style='width:" + (4 * unit - borderSizeReduction) + "%; height:"+ (4 * unit - borderSizeReduction) + "%; border-width:1%;'>");
									
								} else {
									mapSB.append("<div class='map-tile movement' id='rightButton' style='width:" + (4 * unit - borderSizeReduction) + "%; height:"+ (4 * unit - borderSizeReduction) + "%; border-width:1%; border-color:"+
												(Main.game.getPlayer().getFemininity()<=Femininity.MASCULINE.getMaximumFemininity()
														?Colour.MASCULINE_PLUS
														:(Main.game.getPlayer().getFemininity()<=Femininity.ANDROGYNOUS.getMaximumFemininity()
																?Colour.ANDROGYNOUS
																		:Colour.FEMININE_PLUS)).toWebHexString()+";'>");
								}

								// Put place icon onto tile:
								if (Main.game.getActiveWorld().getCell(x, y).getPlace().getSVGString() != null)
									mapSB.append("<div class='place-icon'>" + Main.game.getActiveWorld().getCell(x, y).getPlace().getSVGString() + "</div>");

								mapSB.append("<b class='hotkey-icon" + (Main.game.getActiveWorld().getCell(x, y).getPlace().isDangerous() ? " dangerous" : "") + "'>"
										+ (Main.getProperties().hotkeyMapPrimary.get(KeyboardAction.MOVE_EAST) == null ? "" : Main.getProperties().hotkeyMapPrimary.get(KeyboardAction.MOVE_EAST).getFullName()) + "</b>");

								appendNPCIcon(x, y);
								appendItemsInAreaIcon(x, y);
								
								// Close the tile's div:
								mapSB.append("</div>");

							} else {
								mapSB.append("<div class='map-tile" + (Main.game.getActiveWorld().getCell(x, y).getPlace().isDangerous() ? (y == playerPosition.getY() && x == playerPosition.getX() ? " player dangerous" : " dangerous")
										: (y == playerPosition.getY() && x == playerPosition.getX() ? " player" : "")) + "' style='width:" + (4 * unit) + "%; height:" + (4 * unit) + "%;'>");

								// Put place icon onto tile:
								if (Main.game.getActiveWorld().getCell(x, y).getPlace().getSVGString() != null) {
									if (y == playerPosition.getY() && x == playerPosition.getX()) {
										if (Main.game.getActiveWorld().getCell(x, y).getPlace().isDangerous()) {
											mapSB.append("<div class='place-icon' style='margin:7%;width:86%;height:86%;'>" + SVGImages.SVG_IMAGE_PROVIDER.getPlayerPlaceMapDangerousIcon() + "</div>");
											
										} else {
											if(Main.game.getPlayer().getFemininity()<=Femininity.MASCULINE.getMaximumFemininity()) {
												mapSB.append("<div class='place-icon' style='margin:7%;width:86%;height:86%;'>" + SVGImages.SVG_IMAGE_PROVIDER.getPlayerPlaceMapIconMasculine() + "</div>");
												
											} else if(Main.game.getPlayer().getFemininity()<=Femininity.ANDROGYNOUS.getMaximumFemininity()) {
												mapSB.append("<div class='place-icon' style='margin:7%;width:86%;height:86%;'>" + SVGImages.SVG_IMAGE_PROVIDER.getPlayerPlaceMapIconAndrogynous() + "</div>");
												
											} else{
												mapSB.append("<div class='place-icon' style='margin:7%;width:86%;height:86%;'>" + SVGImages.SVG_IMAGE_PROVIDER.getPlayerPlaceMapIconFeminine() + "</div>");
											}
										}
									}
									mapSB.append("<div class='place-icon' style='margin:18%;width:64%;height:64%;'>" + Main.game.getActiveWorld().getCell(x, y).getPlace().getSVGString() + "</div>");

								} else if (y == playerPosition.getY() && x == playerPosition.getX()) {

									if (Main.game.getActiveWorld().getCell(x, y).getPlace().isDangerous()) {
										mapSB.append("<div class='place-icon' style='margin:18%;width:64%;height:64%;'>" + SVGImages.SVG_IMAGE_PROVIDER.getPlayerMapDangerousIcon() + "</div>");
									} else {
										if(Main.game.getPlayer().getFemininity()<=Femininity.MASCULINE.getMaximumFemininity()) {
											mapSB.append("<div class='place-icon' style='margin:18%;width:64%;height:64%;'>" + SVGImages.SVG_IMAGE_PROVIDER.getPlayerMapIconMasculine() + "</div>");
											
										} else if(Main.game.getPlayer().getFemininity()<=Femininity.ANDROGYNOUS.getMaximumFemininity()) {
											mapSB.append("<div class='place-icon' style='margin:18%;width:64%;height:64%;'>" + SVGImages.SVG_IMAGE_PROVIDER.getPlayerMapIconAndrogynous() + "</div>");
											
										} else{
											mapSB.append("<div class='place-icon' style='margin:18%;width:64%;height:64%;'>" + SVGImages.SVG_IMAGE_PROVIDER.getPlayerMapIconFeminine() + "</div>");
										}
									}
								}
								
								appendNPCIcon(x, y);
								appendItemsInAreaIcon(x, y);

								// Close the tile's div:
								mapSB.append("</div>");
							}

						}
						
					} else {
						mapSB.append("<div class='map-tile blank' style='width:" + (4 * unit) + "%; height:" + (4 * unit) + "%;'></div>");
					}
					
				} else {
					mapSB.append("<div class='map-tile blank' style='width:" + (4 * unit) + "%; height:" + (4 * unit) + "%;'></div>");
				}
			}

		}
		
		if (Main.game.getCurrentDialogueNode().isTravelDisabled()) {
			mapSB.append("<div style='left:0; top:0; margin:0; padding:0; width:100%; height:100%; background-color:#000; opacity:0.7; border-radius:5px;'></div>");
			renderedDisabledMap = true;
		} else {
			renderedDisabledMap = false;
		}
		
		mapSB.append("</div>");

		
		

		return mapSB.toString();
	}
	
	private void appendNPCIcon(int x, int y) {
		List<String> mapIcons = new ArrayList<>();
		
		for(NPC npc : Main.game.getNPCList()) {
			if(npc.getLocation().equals(x, y) && npc.getWorldLocation()==Main.game.getActiveWorld().getWorldType()) {
				mapIcons.add(npc.getMapIcon());
			}
		}
		
		for(int i = mapIcons.size() ; i>0 ; i--) {
			mapSB.append("<div class='npc-icon' style='left:"+(((i-1)*6)+2)+"px;'>"+mapIcons.get(i-1)+"</div>");
		}
	}
	
	private void appendItemsInAreaIcon(int x, int y) {
		if(Main.game.getActiveWorld().getCell(x, y).getInventory().getInventorySlotsTaken() != 0) {
			mapSB.append("<div class='item-icon'>"+SVGImages.SVG_IMAGE_PROVIDER.getItemsOnFloorIcon()+"</div>");
		}
	}

	public void renderButtons() {
		Main.mainController.setButtonsContent(
				"<div class='quarterContainer'>"
					+ "<div class='button" + (!Main.game.getCurrentDialogueNode().isOptionsDisabled() ? "" : " disabled") + "' id='mainMenu'>"
						+ SVGImages.SVG_IMAGE_PROVIDER.getMenuIcon() + (!Main.game.getCurrentDialogueNode().isOptionsDisabled() ? "" : "<div class='disabledLayer'></div>")
					+ "</div>"
				+ "</div>"

				+ "<div class='quarterContainer'>"
					+ "<div class='button" + (
							Main.game.getPlayer().isMainQuestUpdated()
							|| Main.game.getPlayer().isSideQuestUpdated()
							|| Main.game.getPlayer().isRomanceQuestUpdated()
							|| Main.getProperties().isNewWeaponDiscovered()
							|| Main.getProperties().isNewClothingDiscovered()
							|| Main.getProperties().isNewItemDiscovered()
							|| Main.getProperties().isNewRaceDiscovered()
							|| Main.game.getPlayer().getPerkPoints()>0
							|| (Main.game.getPlayer().getLevelUpPoints()>0
									&& (Main.game.getPlayer().getBaseAttributeValue(Attribute.STRENGTH) + Main.game.getPlayer().getBaseAttributeValue(Attribute.INTELLIGENCE) + Main.game.getPlayer().getBaseAttributeValue(Attribute.FITNESS))<300)
								?" highlight"
								:"")
						+ (!Main.game.getCurrentDialogueNode().isOptionsDisabled() ? "" : " disabled") + "' id='journal'>" + SVGImages.SVG_IMAGE_PROVIDER.getJournalIcon()
						+ (!Main.game.getCurrentDialogueNode().isOptionsDisabled() ? "" : "<div class='disabledLayer'></div>")
					+ "</div>"
				+ "</div>"

				+ "<div class='quarterContainer'>"
					+ "<div class='button" + (Main.game.getActiveWorld().getCell(Main.game.getPlayer().getLocation()).getInventory().getInventorySlotsTaken()>0 ? " highlight" : "")
					+ (Main.mainController.isInventoryDisabled() ? " disabled" : "") + "' id='inventory'>"
						+ SVGImages.SVG_IMAGE_PROVIDER.getInventoryIcon()
						+ (Main.mainController.isInventoryDisabled() ? "<div class='disabledLayer'></div>" : "")
					+ "</div>"
				+ "</div>"

				+ "<div class='quarterContainer'>"
					+ "<div class='button" + (Main.game.getCharactersPresent().isEmpty() && Main.game.getCurrentDialogueNode().getMapDisplay() != MapDisplay.CHARACTERS_PRESENT ? " disabled" : "")
					+ "' id='charactersPresent'>" + SVGImages.SVG_IMAGE_PROVIDER.getPeopleIcon()
						+ (Main.game.getCharactersPresent().isEmpty() && Main.game.getCurrentDialogueNode().getMapDisplay() != MapDisplay.CHARACTERS_PRESENT ? "<div class='disabledLayer'></div>" : "")
					+ "</div>"
				+ "</div>"

				+ "<div class='quarterContainer'>"
					+ "<div class='button" + (Main.game.getCurrentDialogueNode().isTravelDisabled() ? " disabled" : "") + "' id='mapZoom'>"
						+ (RenderingEngine.isZoomedIn() ? SVGImages.SVG_IMAGE_PROVIDER.getZoomOutIcon() : SVGImages.SVG_IMAGE_PROVIDER.getZoomInIcon()) + (Main.game.getCurrentDialogueNode().isTravelDisabled() ? "<div class='disabledLayer'></div>" : "")
					+ "</div>"
				+ "</div>");
	}

	public static boolean isZoomedIn() {
		return zoomedIn;
	}

	public static void setZoomedIn(boolean zoomedIn) {
		RenderingEngine.zoomedIn = zoomedIn;
	}

	public static boolean isRenderedDisabledMap() {
		return renderedDisabledMap;
	}

	public static void setRenderedDisabledMap(boolean renderedDisabledMap) {
		RenderingEngine.renderedDisabledMap = renderedDisabledMap;
	}
	
	private String getClassRarityIdentifier(Rarity rarity) {
		return (rarity == Rarity.COMMON ? " common" : "")
				+ (rarity == Rarity.UNCOMMON ? " uncommon" : "")
				+ (rarity == Rarity.RARE ? " rare" : "")
				+ (rarity == Rarity.EPIC ? " epic" : "")
				+ (rarity == Rarity.LEGENDARY ? " legendary" : "")
				+ (rarity == Rarity.JINXED ? " jinxed" : "");
	}

}
