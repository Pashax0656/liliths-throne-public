package com.lilithsthrone.game.sex.positions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.lilithsthrone.game.character.GameCharacter;
import com.lilithsthrone.game.character.body.Arm;
import com.lilithsthrone.game.character.body.BodyPartInterface;
import com.lilithsthrone.game.character.body.Leg;
import com.lilithsthrone.game.character.body.Skin;
import com.lilithsthrone.game.character.body.Tail;
import com.lilithsthrone.game.character.body.Tentacle;
import com.lilithsthrone.game.dialogue.utils.UtilText;
import com.lilithsthrone.game.sex.OrgasmCumTarget;
import com.lilithsthrone.game.sex.Sex;
import com.lilithsthrone.game.sex.SexActionInteractions;
import com.lilithsthrone.game.sex.SexAreaOrifice;
import com.lilithsthrone.game.sex.SexAreaPenetration;
import com.lilithsthrone.game.sex.SexParticipantType;
import com.lilithsthrone.game.sex.positions.slots.SexSlot;
import com.lilithsthrone.game.sex.positions.slots.SexSlotUnique;
import com.lilithsthrone.game.sex.sexActions.SexActionInterface;
import com.lilithsthrone.game.sex.sexActions.SexActionPresets;
import com.lilithsthrone.game.sex.sexActions.SexActionType;
import com.lilithsthrone.game.sex.sexActions.baseActionsMisc.GenericOrgasms;
import com.lilithsthrone.game.sex.sexActions.dominion.CultistSexActions;
import com.lilithsthrone.game.sex.sexActions.dominion.GloryHole;
import com.lilithsthrone.game.sex.sexActions.dominion.PetMounting;
import com.lilithsthrone.game.sex.sexActions.dominion.PetOral;
import com.lilithsthrone.game.sex.sexActions.dominion.PixShower;
import com.lilithsthrone.game.sex.sexActions.dominion.RalphOral;
import com.lilithsthrone.game.sex.sexActions.universal.HandHolding;
import com.lilithsthrone.main.Main;
import com.lilithsthrone.utils.Util;
import com.lilithsthrone.utils.Util.Value;

/**
 * 
 * @since 0.1.97
 * @version 0.3.4
 * @author Innoxia
 */
public class SexPositionUnique {

	public static final AbstractSexPosition PET_MOUNTING = new AbstractSexPosition("Mounted",
			true,
			null,
			Util.newArrayListOfValues(PetMounting.class),
			null) {
		@Override
		public String getDescription(Map<GameCharacter, SexSlot> occupiedSlots) {
			return UtilText.parse(Sex.getCharacterInPosition(SexSlotUnique.PET_MOUNTING_ON_ALL_FOURS), Sex.getCharacterInPosition(SexSlotUnique.PET_MOUNTING_HUMPING),
					"[npc.NameIs] down on all fours, and [npc.has] been mounted by [npc2.name], who's desperate to penetrate and start humping [npc.herHim].");
		}
		@Override
		public Map<SexSlot, Map<SexSlot, SexActionInteractions>> getSlotTargets() {
			List<Value<SexSlot, Map<SexSlot, SexActionInteractions>>> interactions = new ArrayList<>();
			interactions.add(StandardSexActionInteractions.allFoursBehind.getSexActionInteractions(SexSlotUnique.PET_MOUNTING_HUMPING, SexSlotUnique.PET_MOUNTING_ON_ALL_FOURS));
			return generateSlotTargetsMap(interactions);
		}
		@Override
		protected Map<Class<? extends BodyPartInterface>,  List<SexAreaOrifice>> getForcedCreampieMap(GameCharacter cumTarget, GameCharacter cumProvider) {
			// The character being fucked can use their tails or tentacles to force a creampie:
			if(Sex.getSexPositionSlot(cumTarget)==SexSlotUnique.PET_MOUNTING_ON_ALL_FOURS
				&& Sex.getSexPositionSlot(cumProvider)==SexSlotUnique.PET_MOUNTING_HUMPING) {
				return Util.newHashMapOfValues(
						new Value<>(Tail.class, genericGroinForceCreampieAreas),
						new Value<>(Tentacle.class, genericGroinForceCreampieAreas));
			}
			return null;
		}
	};
	
	public static final AbstractSexPosition PET_ORAL = new AbstractSexPosition("Pet Oral",
			true,
			null,
			Util.newArrayListOfValues(PetOral.class),
			null) {
		@Override
		public String getDescription(Map<GameCharacter, SexSlot> occupiedSlots) {
			return UtilText.parse(Sex.getCharacterInPosition(SexSlotUnique.PET_ORAL_ON_ALL_FOURS), Sex.getCharacterInPosition(SexSlotUnique.PET_ORAL_COCKED_LEG),
					"[npc.NameIs] down on all fours, with [npc2.namePos] [npc2.leg] hooked over [npc.her] neck, leaving [npc.her] face just [unit.sizes] away from [npc2.namePos] [npc2.cock+].");
		}
		@Override
		public Map<SexSlot, Map<SexSlot, SexActionInteractions>> getSlotTargets() {
			List<Value<SexSlot, Map<SexSlot, SexActionInteractions>>> interactions = new ArrayList<>();
			interactions.add(StandardSexActionInteractions.performingOral.getSexActionInteractions(SexSlotUnique.PET_ORAL_ON_ALL_FOURS, SexSlotUnique.PET_ORAL_COCKED_LEG));
			return generateSlotTargetsMap(interactions);
		}
	};
	
	public static final AbstractSexPosition UNDER_DESK_RALPH = new AbstractSexPosition("Under desk",
			false,
			null,
			Util.newArrayListOfValues(RalphOral.class),
			null) {
		@Override
		public String getDescription(Map<GameCharacter, SexSlot> occupiedSlots) {
			return "You're kneeling under Ralph's counter, with your face just [unit.sizes] away from his crotch.";
		}
		@Override
		public Map<SexSlot, Map<SexSlot, SexActionInteractions>> getSlotTargets() {
			List<Value<SexSlot, Map<SexSlot, SexActionInteractions>>> interactions = new ArrayList<>();
			interactions.add(StandardSexActionInteractions.performingOralRalph.getSexActionInteractions(SexSlotUnique.RALPH_SUB, SexSlotUnique.RALPH_DOM));
			return generateSlotTargetsMap(interactions);
		}
		@Override
		protected Map<Class<? extends BodyPartInterface>,  List<SexAreaOrifice>> getForcedCreampieMap(GameCharacter cumTarget, GameCharacter cumProvider) {
			// The character taking oral can use their arms to force a creampie:
			if(Sex.getSexPositionSlot(cumTarget)==SexSlotUnique.RALPH_SUB
				&& Sex.getSexPositionSlot(cumProvider)==SexSlotUnique.RALPH_DOM) {
				return Util.newHashMapOfValues(
						new Value<>(Arm.class, genericFaceForceCreampieAreas));
			}
			// The character being fucked can use their legs to force a creampie:
			if(Sex.getSexPositionSlot(cumTarget)==SexSlotUnique.RALPH_SUB
					&& Sex.getSexPositionSlot(cumProvider)==SexSlotUnique.RALPH_DOM) {
					return Util.newHashMapOfValues(
							new Value<>(Leg.class, genericGroinForceCreampieAreas));
				}
			return null;
		}
	};
	
	public static final AbstractSexPosition SHOWER_TIME_PIX = new AbstractSexPosition("Shower sex",
			false,
			null,
			Util.newArrayListOfValues(PixShower.class),
			null) {
		@Override
		public String getDescription(Map<GameCharacter, SexSlot> occupiedSlots) {
			return "You're standing with your face pressed up against one wall of the shower, and behind you, Pix is growling hungrily into your ear.";
		}
		@Override
		public Map<SexSlot, Map<SexSlot, SexActionInteractions>> getSlotTargets() {
			List<Value<SexSlot, Map<SexSlot, SexActionInteractions>>> interactions = new ArrayList<>();
			interactions.add(StandardSexActionInteractions.standingBehindCharacterFacingWall.getSexActionInteractions(SexSlotUnique.FACE_TO_WALL_FACING_TARGET_SHOWER_PIX, SexSlotUnique.FACE_TO_WALL_AGAINST_WALL_SHOWER_PIX));
			return generateSlotTargetsMap(interactions);
		}
	};
	
	public static final AbstractSexPosition HANDS_ROSE = new AbstractSexPosition("Hand-holding",
			false,
			null,
			Util.newArrayListOfValues(HandHolding.class, GenericOrgasms.class),
			null) {
		@Override
		public String getDescription(Map<GameCharacter, SexSlot> occupiedSlots) {
			return "You and the cat-girl maid, Rose, are standing facing one another, ready to perform lewd acts with one another's hands.";
		}
		@Override
		public Map<SexSlot, Map<SexSlot, SexActionInteractions>> getSlotTargets() {
			List<Value<SexSlot, Map<SexSlot, SexActionInteractions>>> interactions = new ArrayList<>();
			interactions.add(StandardSexActionInteractions.handHolding.getSexActionInteractions(SexSlotUnique.HAND_SEX_DOM_ROSE, SexSlotUnique.HAND_SEX_SUB_ROSE));
			return generateSlotTargetsMap(interactions);
		}
	};
	
	public static final AbstractSexPosition KNEELING_ORAL_CULTIST = new AbstractSexPosition("Kneeling",
			true,
			null,
			Util.newArrayListOfValues(CultistSexActions.class),
			null) {
		@Override
		public String getDescription(Map<GameCharacter, SexSlot> occupiedSlots) {
			return "You're kneeling at the feet of [npc.name], with your [pc.face+] hovering just [unit.sizes] away from [npc.her] groin.";
		}
		@Override
		public Map<SexSlot, Map<SexSlot, SexActionInteractions>> getSlotTargets() {
			List<Value<SexSlot, Map<SexSlot, SexActionInteractions>>> interactions = new ArrayList<>();
			interactions.add(StandardSexActionInteractions.performingOral.getSexActionInteractions(SexSlotUnique.KNEELING_PERFORMING_ORAL_CULTIST, SexSlotUnique.KNEELING_RECEIVING_ORAL_CULTIST));
			return generateSlotTargetsMap(interactions);
		}
		@Override
		protected Map<Class<? extends BodyPartInterface>,  List<SexAreaOrifice>> getForcedCreampieMap(GameCharacter cumTarget, GameCharacter cumProvider) {
			// The character taking oral can use their arms to force a creampie:
			if(Sex.getSexPositionSlot(cumTarget)==SexSlotUnique.KNEELING_PERFORMING_ORAL_CULTIST
				&& Sex.getSexPositionSlot(cumProvider)==SexSlotUnique.KNEELING_RECEIVING_ORAL_CULTIST) {
				return Util.newHashMapOfValues(
						new Value<>(Arm.class, genericFaceForceCreampieAreas));
			}
			return null;
		}
	};
	
	public static final AbstractSexPosition MISSIONARY_ALTAR_CULTIST = new AbstractSexPosition("Missionary on altar",
			true,
			null,
			Util.newArrayListOfValues(CultistSexActions.class),
			null) {
		@Override
		public String getDescription(Map<GameCharacter, SexSlot> occupiedSlots) {//TODO
			if(Sex.getSexPositionSlot(Main.game.getPlayer())==SexSlotUnique.MISSIONARY_ALTAR_LYING_ON_ALTAR) {
				if(Sex.getSexPositionSlot(Sex.getActivePartner())==SexSlotUnique.MISSIONARY_ALTAR_STANDING_BETWEEN_LEGS) {
					return "You're lying back on top of the chapel's altar, and [npc.namePos] standing between your [pc.legs], ready to have some fun with you in the missionary position.";
				} else {
					return "You're lying back on top of the chapel's altar, and [npc.namePos] kneeling down between your [pc.legs], ready to have some oral fun with you in the missionary position.";
				}
				
			} else if(Sex.getSexPositionSlot(Main.game.getPlayer())==SexSlotUnique.MISSIONARY_ALTAR_STANDING_BETWEEN_LEGS) {
				return "[npc.Name] is lying back on top of the chapel's altar, and you're standing between [npc.her] [npc.legs], ready to have some fun in the missionary position.";
				
			} else {
				return "[npc.Name] is lying back on top of the chapel's altar, and you're kneeling down between [npc.her] [npc.legs], ready to have some oral fun in the missionary position.";
			}
		}
		@Override
		public Map<SexSlot, Map<SexSlot, SexActionInteractions>> getSlotTargets() {
			List<Value<SexSlot, Map<SexSlot, SexActionInteractions>>> interactions = new ArrayList<>();
			interactions.add(StandardSexActionInteractions.performingOral.getSexActionInteractions(SexSlotUnique.MISSIONARY_ALTAR_KNEELING_BETWEEN_LEGS, SexSlotUnique.MISSIONARY_ALTAR_LYING_ON_ALTAR));
			interactions.add(StandardSexActionInteractions.missionaryAndMatingPress.getSexActionInteractions(SexSlotUnique.MISSIONARY_ALTAR_STANDING_BETWEEN_LEGS, SexSlotUnique.MISSIONARY_ALTAR_LYING_ON_ALTAR));
			return generateSlotTargetsMap(interactions);
		}
		@Override
		protected Map<Class<? extends BodyPartInterface>,  List<SexAreaOrifice>> getForcedCreampieMap(GameCharacter cumTarget, GameCharacter cumProvider) {
			// The character being fucked can use their legs, tails, or tentacles to force a creampie:
			if(Sex.getSexPositionSlot(cumTarget)==SexSlotUnique.MISSIONARY_ALTAR_LYING_ON_ALTAR
				&& Sex.getSexPositionSlot(cumProvider)==SexSlotUnique.MISSIONARY_ALTAR_STANDING_BETWEEN_LEGS) {
				return Util.newHashMapOfValues(
						new Value<>(Leg.class, genericGroinForceCreampieAreas),
						new Value<>(Tail.class, genericGroinForceCreampieAreas),
						new Value<>(Tentacle.class, genericGroinForceCreampieAreas));
				
			// The character on top can use their body weight to force a creampie:
			} else if(Sex.getSexPositionSlot(cumTarget)==SexSlotUnique.MISSIONARY_ALTAR_STANDING_BETWEEN_LEGS
					&& Sex.getSexPositionSlot(cumProvider)==SexSlotUnique.MISSIONARY_ALTAR_LYING_ON_ALTAR) {
					return Util.newHashMapOfValues(
							new Value<>(Skin.class, genericGroinForceCreampieAreas));
			}
			return null;
		}
	};
	
	public static final AbstractSexPosition MISSIONARY_ALTAR_SEALED_CULTIST = new AbstractSexPosition("Missionary on altar",
			true,
			null,
			Util.newArrayListOfValues(CultistSexActions.class),
			null) {
		@Override
		public String getDescription(Map<GameCharacter, SexSlot> occupiedSlots) {//TODO
			if(Sex.getSexPositionSlot(Main.game.getPlayer())==SexSlotUnique.MISSIONARY_ALTAR_SEALED_LYING_ON_ALTAR) {
				return "You're lying back on top of the chapel's altar, and [npc.namePos] standing between your [pc.legs], ready to have some fun with you in the missionary position.";
			} else if(Sex.getSexPositionSlot(Main.game.getPlayer())==SexSlotUnique.MISSIONARY_ALTAR_SEALED_STANDING_BETWEEN_LEGS) {
				return "[npc.Name] is lying back on top of the chapel's altar, and you're standing between [npc.her] [npc.legs], ready to have some fun in the missionary position.";
			} else {
				return "[npc.Name] is lying back on top of the chapel's altar, and you're kneeling down between [npc.her] [npc.legs], ready to have some oral fun in the missionary position.";
			}
		}
		@Override
		public Map<SexSlot, Map<SexSlot, SexActionInteractions>> getSlotTargets() {
			List<Value<SexSlot, Map<SexSlot, SexActionInteractions>>> interactions = new ArrayList<>();
			interactions.add(StandardSexActionInteractions.performingOral.getSexActionInteractions(SexSlotUnique.MISSIONARY_ALTAR_SEALED_KNEELING_BETWEEN_LEGS, SexSlotUnique.MISSIONARY_ALTAR_SEALED_LYING_ON_ALTAR));
			interactions.add(StandardSexActionInteractions.missionaryAndMatingPress.getSexActionInteractions(SexSlotUnique.MISSIONARY_ALTAR_SEALED_STANDING_BETWEEN_LEGS, SexSlotUnique.MISSIONARY_ALTAR_SEALED_LYING_ON_ALTAR));
			return generateSlotTargetsMap(interactions);
		}
		@Override
		public boolean isActionBlocked(GameCharacter performer, GameCharacter target, SexActionInterface action) {
			
			if(Sex.getSexPositionSlot(performer) == SexSlotUnique.MISSIONARY_ALTAR_SEALED_LYING_ON_ALTAR) {
				if((action.getActionType()==SexActionType.ONGOING
						|| action.getActionType()==SexActionType.START_ONGOING
						|| action.getActionType()==SexActionType.REQUIRES_NO_PENETRATION
						|| action.getActionType()==SexActionType.REQUIRES_EXPOSED
						|| action.getActionType()==SexActionType.REQUIRES_NO_PENETRATION_AND_EXPOSED)) {
					return true;
				}
			}
			return super.isActionBlocked(performer, target, action);
		}
		@Override
		protected Map<Class<? extends BodyPartInterface>,  List<SexAreaOrifice>> getForcedCreampieMap(GameCharacter cumTarget, GameCharacter cumProvider) {
			// The character on top can use their body weight to force a creampie:
			if(Sex.getSexPositionSlot(cumTarget)==SexSlotUnique.MISSIONARY_ALTAR_SEALED_STANDING_BETWEEN_LEGS
					&& Sex.getSexPositionSlot(cumProvider)==SexSlotUnique.MISSIONARY_ALTAR_SEALED_LYING_ON_ALTAR) {
					return Util.newHashMapOfValues(
							new Value<>(Skin.class, genericGroinForceCreampieAreas));
			}
			return null;
		}
	};
	
	public static final AbstractSexPosition BREEDING_STALL_FRONT = new AbstractSexPosition("Breeding Stall",
			true,
			null,
			Util.newArrayListOfValues(),
			Util.newHashMapOfValues(
					new Value<>(
							SexSlotUnique.BREEDING_STALL_FRONT,
							Util.newHashMapOfValues(
							new Value<>(
									SexSlotUnique.BREEDING_STALL_FUCKING,
									new SexActionInteractions(
											Util.mergeMaps(
													SexActionPresets.tailToAllAreas,
													SexActionPresets.tentacleToAllAreas),
											Util.newArrayListOfValues(
													OrgasmCumTarget.FLOOR))))),
					new Value<>(
							SexSlotUnique.BREEDING_STALL_FUCKING,
							Util.newHashMapOfValues(
							new Value<>(
									SexSlotUnique.BREEDING_STALL_FRONT,
									new SexActionInteractions(
											Util.mergeMaps(
													SexActionPresets.appendagesToLowerHalf,
													SexActionPresets.groinToGroin,
													SexActionPresets.groinToAss),
											Util.newArrayListOfValues(
													OrgasmCumTarget.ASS,
													OrgasmCumTarget.GROIN,
													OrgasmCumTarget.LEGS,
													OrgasmCumTarget.FEET,
													OrgasmCumTarget.FLOOR))))))) {
		@Override
		public String getDescription(Map<GameCharacter, SexSlot> occupiedSlots) {
			if(Sex.getSexPositionSlot(Main.game.getPlayer())==SexSlotUnique.BREEDING_STALL_FRONT) {
				return "You're lying on your front on the padded bench, with your legs and lower abdomen projecting out of the hole in the wall, exposing your pussy to the breeders beyond."
						+ (Main.game.getPlayer().hasTail()
								?" As you get into position, someone on the other side of the wall fastens your "
									+(Main.game.getPlayer().getTailCount()>1?"[pc.tailCount] [pc.tails] to the wall by means of metal clamps":" [pc.tail] to the wall by means of a metal clamp")
									+ ", in order to prevent you from using"+(Main.game.getPlayer().getTailCount()>1?"them":"it")+" to block your [pc.pussy+]."
								:"");
			} else {
				GameCharacter character = Sex.getCharacterInPosition(SexSlotUnique.BREEDING_STALL_FRONT);
				if(character!=null) {
					return "[npc.Name] is lying on [npc.her] front on the padded bench, with [npc.her] legs and lower abdomen projecting out of the hole in the wall. [npc.Her] pussy is completely exposed to you, ready for breeding."
							+ (character.hasTail()
									?" As [npc.she] gets into position, Epona steps forwards and fastens [npc.her] "
										+(character.getTailCount()>1?"[npc.tailCount] [npc.tails] to the wall by means of metal clamps":" [npc.tail] to the wall by means of a metal clamp")
										+ ", in order to prevent [npc.herHim] from using"+(character.getTailCount()>1?"them":"it")+" to block [npc.her] [npc.pussy+]."
									:"");
				} else {
					return "";
				}
			}
		}

		@Override
		public boolean isActionBlocked(GameCharacter performer, GameCharacter target, SexActionInterface action) {
			if((Sex.getSexPositionSlot(performer)==SexSlotUnique.BREEDING_STALL_FRONT
					&& action.getSexAreaInteractions().keySet().contains(SexAreaPenetration.TAIL))
				|| (Sex.getSexPositionSlot(performer)==SexSlotUnique.BREEDING_STALL_FUCKING
						&& action.getSexAreaInteractions().values().contains(SexAreaPenetration.TAIL)
						&& action.getParticipantType()!=SexParticipantType.SELF)) {
				return true;
			}
			
			return super.isActionBlocked(performer, target, action);
		}

		@Override
		protected Map<Class<? extends BodyPartInterface>,  List<SexAreaOrifice>> getForcedCreampieMap(GameCharacter cumTarget, GameCharacter cumProvider) {
			// The character being fucked can use their tails or tentacles to force a creampie:
			if(Sex.getSexPositionSlot(cumTarget)==SexSlotUnique.BREEDING_STALL_FRONT
				&& Sex.getSexPositionSlot(cumProvider)==SexSlotUnique.BREEDING_STALL_FUCKING) {
				return Util.newHashMapOfValues(
						new Value<>(Tail.class, genericGroinForceCreampieAreas),
						new Value<>(Tentacle.class, genericGroinForceCreampieAreas));
			}
			return null;
		}
	};
	
	public static final AbstractSexPosition BREEDING_STALL_BACK = new AbstractSexPosition("Breeding Stall",
			true,
			null,
			Util.newArrayListOfValues(),
			Util.newHashMapOfValues(
					new Value<>(
							SexSlotUnique.BREEDING_STALL_BACK,
							Util.newHashMapOfValues(
							new Value<>(
									SexSlotUnique.BREEDING_STALL_FUCKING,
									new SexActionInteractions(
											Util.mergeMaps(
													SexActionPresets.tailToAllAreas,
													SexActionPresets.tentacleToAllAreas),
											Util.newArrayListOfValues(
													OrgasmCumTarget.SELF_STOMACH,
													OrgasmCumTarget.GROIN,
													OrgasmCumTarget.STOMACH,
													OrgasmCumTarget.FLOOR))))),
					new Value<>(
							SexSlotUnique.BREEDING_STALL_FUCKING,
							Util.newHashMapOfValues(
							new Value<>(
									SexSlotUnique.BREEDING_STALL_BACK,
									new SexActionInteractions(
											Util.mergeMaps(
													SexActionPresets.appendagesToLowerHalf,
													SexActionPresets.groinToGroin,
													SexActionPresets.groinToAss),
											Util.newArrayListOfValues(
													OrgasmCumTarget.ASS,
													OrgasmCumTarget.GROIN,
													OrgasmCumTarget.LEGS,
													OrgasmCumTarget.FEET,
													OrgasmCumTarget.FLOOR))))))) {
		@Override
		public String getDescription(Map<GameCharacter, SexSlot> occupiedSlots) {
			if(Sex.getSexPositionSlot(Main.game.getPlayer())==SexSlotUnique.BREEDING_STALL_FRONT) {
				return "You're lying on your back on the padded bench, with your legs and lower abdomen projecting out of the hole in the wall, exposing your pussy to the breeders beyond."
						+ (Main.game.getPlayer().hasTail()
								?" As you get into position, someone on the other side of the wall fastens your "
									+(Main.game.getPlayer().getTailCount()>1?"[pc.tailCount] [pc.tails] to the wall by means of metal clamps":" [pc.tail] to the wall by means of a metal clamp")
									+ ", in order to prevent you from using"+(Main.game.getPlayer().getTailCount()>1?"them":"it")+" to block your [pc.pussy+]."
								:"");
			} else {
				GameCharacter character = Sex.getCharacterInPosition(SexSlotUnique.BREEDING_STALL_FRONT);
				if(character!=null) {
					return "[npc.Name] is lying on [npc.her] back on the padded bench, with [npc.her] legs and lower abdomen projecting out of the hole in the wall. [npc.Her] pussy is completely exposed to you, ready for breeding."
							+ (character.hasTail()
									?" As [npc.she] gets into position, Epona steps forwards and fastens [npc.her] "
										+(character.getTailCount()>1?"[npc.tailCount] [npc.tails] to the wall by means of metal clamps":" [npc.tail] to the wall by means of a metal clamp")
										+ ", in order to prevent [npc.herHim] from using"+(character.getTailCount()>1?"them":"it")+" to block [npc.her] [npc.pussy+]."
									:"");
				} else {
					return "";
				}
			}
		}

		@Override
		public boolean isActionBlocked(GameCharacter performer, GameCharacter target, SexActionInterface action) {
			if((Sex.getSexPositionSlot(performer)==SexSlotUnique.BREEDING_STALL_BACK
					&& action.getSexAreaInteractions().keySet().contains(SexAreaPenetration.TAIL))
				|| (Sex.getSexPositionSlot(performer)==SexSlotUnique.BREEDING_STALL_FUCKING
						&& action.getSexAreaInteractions().values().contains(SexAreaPenetration.TAIL)
						&& action.getParticipantType()!=SexParticipantType.SELF)) {
				return true;
			}
			
			return super.isActionBlocked(performer, target, action);
		}

		@Override
		protected Map<Class<? extends BodyPartInterface>,  List<SexAreaOrifice>> getForcedCreampieMap(GameCharacter cumTarget, GameCharacter cumProvider) {
			// The character being fucked can use their legs, tails, or tentacles to force a creampie:
			if(Sex.getSexPositionSlot(cumTarget)==SexSlotUnique.BREEDING_STALL_BACK
				&& Sex.getSexPositionSlot(cumProvider)==SexSlotUnique.BREEDING_STALL_FUCKING) {
				return Util.newHashMapOfValues(
						new Value<>(Leg.class, genericGroinForceCreampieAreas),
						new Value<>(Tail.class, genericGroinForceCreampieAreas),
						new Value<>(Tentacle.class, genericGroinForceCreampieAreas));
			}
			return null;
		}
	};
	
	public static final AbstractSexPosition GLORY_HOLE = new AbstractSexPosition("Glory hole oral",
			true,
			null,
			Util.newArrayListOfValues(GloryHole.class), Util.newHashMapOfValues(
					new Value<>(
							SexSlotUnique.GLORY_HOLE_RECEIVING_ORAL_ONE,
							Util.newHashMapOfValues(
							new Value<>(
									SexSlotUnique.GLORY_HOLE_KNEELING,
									new SexActionInteractions(
										Util.mergeMaps(
											SexActionPresets.groinToMouth),
										Util.newArrayListOfValues(
												OrgasmCumTarget.BREASTS,
												OrgasmCumTarget.HAIR,
												OrgasmCumTarget.FACE,
												OrgasmCumTarget.FLOOR))))),
					new Value<>(
							SexSlotUnique.GLORY_HOLE_RECEIVING_ORAL_TWO,
							Util.newHashMapOfValues(
							new Value<>(
									SexSlotUnique.GLORY_HOLE_KNEELING,
									new SexActionInteractions(
										Util.mergeMaps(
											SexActionPresets.groinToMouth),
										Util.newArrayListOfValues(
												OrgasmCumTarget.BREASTS,
												OrgasmCumTarget.HAIR,
												OrgasmCumTarget.FACE,
												OrgasmCumTarget.FLOOR))))),
					new Value<>(
							SexSlotUnique.GLORY_HOLE_KNEELING,
							Util.newHashMapOfValues(
							new Value<>(
									SexSlotUnique.GLORY_HOLE_RECEIVING_ORAL_ONE,
									new SexActionInteractions(
										Util.mergeMaps(
											SexActionPresets.fingerToPenis,
											SexActionPresets.fingerToVagina),
										Util.newArrayListOfValues(
												OrgasmCumTarget.SELF_STOMACH,
												OrgasmCumTarget.SELF_GROIN,
												OrgasmCumTarget.SELF_LEGS,
												OrgasmCumTarget.FLOOR))),
							new Value<>(
									SexSlotUnique.GLORY_HOLE_RECEIVING_ORAL_TWO,
									new SexActionInteractions(
										Util.mergeMaps(
											SexActionPresets.fingerToPenis,
											SexActionPresets.fingerToVagina),
										Util.newArrayListOfValues(
												OrgasmCumTarget.SELF_STOMACH,
												OrgasmCumTarget.SELF_GROIN,
												OrgasmCumTarget.SELF_LEGS,
												OrgasmCumTarget.FLOOR))))))) {
		@Override
		public String getDescription(Map<GameCharacter, SexSlot> occupiedSlots) {
			List<GameCharacter> characters = new ArrayList<>();
			characters.add(Sex.getCharacterInPosition(SexSlotUnique.GLORY_HOLE_KNEELING));
			characters.add(Sex.getCharacterInPosition(SexSlotUnique.GLORY_HOLE_RECEIVING_ORAL_ONE));
			
			if(Sex.getTotalParticipantCount(false)==3) {
				characters.add(Sex.getCharacterInPosition(SexSlotUnique.GLORY_HOLE_RECEIVING_ORAL_TWO));
				return UtilText.parse(characters,
						"[npc.NameIsFull] kneeling on the floor of the toilet, ready to serve [npc2.namePos] [npc2.cock+] on one side, an [npc3.namePos] [npc3.cock+] on the other.");
			} else {
				return UtilText.parse(characters,
						"[npc.NameIsFull] kneeling on the floor of the toilet, [npc.her] mouth up against the glory hole in preparation to serve whatever set of genitals [npc2.name] [npc2.has].");
			}
		}
	};
	
	public static final AbstractSexPosition GLORY_HOLE_SEX = new AbstractSexPosition("Glory hole sex",
			true,
			null,
			Util.newArrayListOfValues(GloryHole.class), Util.newHashMapOfValues(
					new Value<>(
							SexSlotUnique.GLORY_HOLE_RECEIVING_ORAL_ONE,
							Util.newHashMapOfValues(
							new Value<>(
									SexSlotUnique.GLORY_HOLE_FUCKED,
									new SexActionInteractions(
										Util.mergeMaps(
											SexActionPresets.groinToMouth),
										Util.newArrayListOfValues(
												OrgasmCumTarget.BREASTS,
												OrgasmCumTarget.HAIR,
												OrgasmCumTarget.FACE,
												OrgasmCumTarget.FLOOR))))),
					new Value<>(
							SexSlotUnique.GLORY_HOLE_FUCKING,
							Util.newHashMapOfValues(
							new Value<>(
									SexSlotUnique.GLORY_HOLE_FUCKED,
									new SexActionInteractions(
											SexActionPresets.penisToVagina,
											Util.newArrayListOfValues(
													OrgasmCumTarget.ASS,
													OrgasmCumTarget.GROIN,
													OrgasmCumTarget.LEGS,
													OrgasmCumTarget.FLOOR))))),
					new Value<>(
							SexSlotUnique.GLORY_HOLE_FUCKED,
							Util.newHashMapOfValues(
							new Value<>(
									SexSlotUnique.GLORY_HOLE_RECEIVING_ORAL_ONE,
									new SexActionInteractions(
											Util.mergeMaps(
												SexActionPresets.fingerToPenis,
												SexActionPresets.fingerToVagina),
											Util.newArrayListOfValues(
													OrgasmCumTarget.SELF_LEGS,
													OrgasmCumTarget.FLOOR))),
							new Value<>(
									SexSlotUnique.GLORY_HOLE_FUCKING,
									new SexActionInteractions(
											SexActionPresets.anusToPenis,
											Util.newArrayListOfValues(
													OrgasmCumTarget.SELF_LEGS,
													OrgasmCumTarget.FLOOR))))))) {
		@Override
		public String getDescription(Map<GameCharacter, SexSlot> occupiedSlots) {
			List<GameCharacter> characters = new ArrayList<>();
			characters.add(Sex.getCharacterInPosition(SexSlotUnique.GLORY_HOLE_FUCKED));
			characters.add(Sex.getCharacterInPosition(SexSlotUnique.GLORY_HOLE_FUCKING));

			if(Sex.getTotalParticipantCount(false)==3) {
				characters.add(Sex.getCharacterInPosition(SexSlotUnique.GLORY_HOLE_RECEIVING_ORAL_ONE));
				return UtilText.parse(characters,
						"[npc.NameIsFull] pressing [npc.her] [npc.ass+] up against [npc2.namePos] glory hole, ready to get penetrated by [npc2.her] [npc2.cock+],"
								+ " while bringing [npc.her] mouth down to [npc3.namePos] [npc3.cock+] on the other side of the narrow toilet stall.");
				
			} else {
				return UtilText.parse(characters,
						"[npc.NameIsFull] pressing [npc.her] [npc.ass+] up against [npc2.namePos] glory hole, ready to get penetrated by [npc2.her] [npc2.cock+].");
			}
		}
	};

}
