package com.lilithsthrone.game.character.body;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.lilithsthrone.game.Game;
import com.lilithsthrone.game.PropertyValue;
import com.lilithsthrone.game.character.CharacterUtils;
import com.lilithsthrone.game.character.GameCharacter;
import com.lilithsthrone.game.character.Litter;
import com.lilithsthrone.game.character.PregnancyPossibility;
import com.lilithsthrone.game.character.body.types.AbstractBreastType;
import com.lilithsthrone.game.character.body.types.AbstractLegType;
import com.lilithsthrone.game.character.body.types.AntennaType;
import com.lilithsthrone.game.character.body.types.ArmType;
import com.lilithsthrone.game.character.body.types.AssType;
import com.lilithsthrone.game.character.body.types.BodyCoveringType;
import com.lilithsthrone.game.character.body.types.BreastType;
import com.lilithsthrone.game.character.body.types.EarType;
import com.lilithsthrone.game.character.body.types.EyeType;
import com.lilithsthrone.game.character.body.types.FaceType;
import com.lilithsthrone.game.character.body.types.FootStructure;
import com.lilithsthrone.game.character.body.types.HairType;
import com.lilithsthrone.game.character.body.types.HornType;
import com.lilithsthrone.game.character.body.types.LegType;
import com.lilithsthrone.game.character.body.types.PenisType;
import com.lilithsthrone.game.character.body.types.SkinType;
import com.lilithsthrone.game.character.body.types.TailType;
import com.lilithsthrone.game.character.body.types.TentacleType;
import com.lilithsthrone.game.character.body.types.VaginaType;
import com.lilithsthrone.game.character.body.types.WingType;
import com.lilithsthrone.game.character.body.valueEnums.AreolaeShape;
import com.lilithsthrone.game.character.body.valueEnums.BodyCoveringSkinToneColorHelper;
import com.lilithsthrone.game.character.body.valueEnums.BodyHair;
import com.lilithsthrone.game.character.body.valueEnums.BodyMaterial;
import com.lilithsthrone.game.character.body.valueEnums.BodyShape;
import com.lilithsthrone.game.character.body.valueEnums.BodySize;
import com.lilithsthrone.game.character.body.valueEnums.BreastShape;
import com.lilithsthrone.game.character.body.valueEnums.Capacity;
import com.lilithsthrone.game.character.body.valueEnums.CoveringModifier;
import com.lilithsthrone.game.character.body.valueEnums.CoveringPattern;
import com.lilithsthrone.game.character.body.valueEnums.EyeShape;
import com.lilithsthrone.game.character.body.valueEnums.Femininity;
import com.lilithsthrone.game.character.body.valueEnums.FluidModifier;
import com.lilithsthrone.game.character.body.valueEnums.FluidRegeneration;
import com.lilithsthrone.game.character.body.valueEnums.FluidTypeBase;
import com.lilithsthrone.game.character.body.valueEnums.GenitalArrangement;
import com.lilithsthrone.game.character.body.valueEnums.HairStyle;
import com.lilithsthrone.game.character.body.valueEnums.Height;
import com.lilithsthrone.game.character.body.valueEnums.HornLength;
import com.lilithsthrone.game.character.body.valueEnums.LegConfiguration;
import com.lilithsthrone.game.character.body.valueEnums.Muscle;
import com.lilithsthrone.game.character.body.valueEnums.NippleShape;
import com.lilithsthrone.game.character.body.valueEnums.OrificeModifier;
import com.lilithsthrone.game.character.body.valueEnums.PenetrationModifier;
import com.lilithsthrone.game.character.body.valueEnums.PenisGirth;
import com.lilithsthrone.game.character.body.valueEnums.StartingSkinTone;
import com.lilithsthrone.game.character.body.valueEnums.TesticleSize;
import com.lilithsthrone.game.character.body.valueEnums.TongueModifier;
import com.lilithsthrone.game.character.body.valueEnums.Wetness;
import com.lilithsthrone.game.character.body.valueEnums.WingSize;
import com.lilithsthrone.game.character.effects.Perk;
import com.lilithsthrone.game.character.effects.StatusEffect;
import com.lilithsthrone.game.character.gender.Gender;
import com.lilithsthrone.game.character.race.Race;
import com.lilithsthrone.game.character.race.RaceStage;
import com.lilithsthrone.game.character.race.RacialBody;
import com.lilithsthrone.game.character.race.Subspecies;
import com.lilithsthrone.game.dialogue.utils.UtilText;
import com.lilithsthrone.game.sex.SexAreaOrifice;
import com.lilithsthrone.game.sex.SexAreaPenetration;
import com.lilithsthrone.game.sex.SexParticipantType;
import com.lilithsthrone.game.sex.SexType;
import com.lilithsthrone.main.Main;
import com.lilithsthrone.utils.Builder;
import com.lilithsthrone.utils.Colour;
import com.lilithsthrone.utils.Units;
import com.lilithsthrone.utils.Util;
import com.lilithsthrone.utils.XMLSaving;

/**
 * @since 0.1.0
 * @version 0.3.3.10
 * @author Innoxia
 */
public class Body implements XMLSaving {
	
	// Required:
	private Arm arm;
	private Ass ass;
	private Breast breast;
	private Face face;
	private Eye eye;
	private Ear ear;
	private Hair hair;
	private Leg leg;
	private Skin skin;
	private BodyMaterial bodyMaterial;

	// Optional:
	private Antenna antenna;
	private BreastCrotch breastCrotch;
	private Horn horn;
	private Penis penis;
	private Penis secondPenis;
	private Tail tail;
	private Tentacle tentacle;
	private Vagina vagina;
	private Wing wing;
	
	private GenitalArrangement genitalArrangement;

	private Map<Race, Integer> raceWeightMap = new ConcurrentHashMap<>();
	private Subspecies subspecies;
	private RaceStage raceStage;
	private boolean piercedStomach = false;
	private Subspecies subspeciesOverride = null;
	private int height;
	private int femininity;
	private int bodySize;
	private int muscle;
	private BodyHair pubicHair;
	
	private Map<BodyCoveringType, Covering> coverings;
	private Set<BodyCoveringType> coveringsDiscovered;

	private List<BodyPartInterface> allBodyParts;

	private boolean takesAfterMother = true;
	
	
	public static class BodyBuilder implements Builder<Body> {
		// Required parameters:
		private final Arm arm;
		private final Ass ass;
		private final Breast breast;
		private final Face face;
		private final Eye eye;
		private final Ear ear;
		private final Hair hair;
		private final Leg leg;
		private final Skin skin;
		private final BodyMaterial bodyMaterial;
		private GenitalArrangement genitalArrangement;
		private final int height;
		private final int femininity, bodySize, muscle;
		
		// Optional parameters - initialised to null values:
		private Antenna antenna = new Antenna(AntennaType.NONE);
		private BreastCrotch breastCrotch = new BreastCrotch(BreastType.NONE, BreastShape.ROUND, 0, 0, 1, 1, NippleShape.NORMAL, 1, 1, 0, 0, 0, true);
		private Horn horn = new Horn(HornType.NONE, 0);
		private Penis penis = new Penis(PenisType.NONE, 0, false, 0, 0, 0, 0);
		private Penis secondPenis = new Penis(PenisType.NONE, 0, false, 0, 0, 0, 0);
		private Tail tail = new Tail(TailType.NONE);
		private Tentacle tentacle = new Tentacle(TentacleType.NONE);
		private Vagina vagina = new Vagina(VaginaType.NONE, 0, 0, 0, 0, 3, 3, true);
		private Wing wing = new Wing(WingType.NONE, 0);

		public BodyBuilder(Arm arm, Ass ass, Breast breast, Face face, Eye eye, Ear ear, Hair hair, Leg leg, Skin skin, BodyMaterial bodyMaterial, GenitalArrangement genitalArrangement, int height, int femininity, int bodySize, int muscle) {
			this.arm = arm;
			this.ass = ass;
			this.breast = breast;
			this.face = face;
			this.eye = eye;
			this.ear = ear;
			this.hair = hair;
			this.leg = leg;
			this.skin = skin;
			this.bodyMaterial = bodyMaterial;
			this.genitalArrangement = genitalArrangement;
			this.height = height;
			this.femininity = femininity;
			this.bodySize = bodySize;
			this.muscle = muscle;
		}
		
		public BodyBuilder antenna(Antenna antenna) {
			this.antenna = antenna;
			return this;
		}
		
		public BodyBuilder breastCrotch(BreastCrotch breastCrotch) {
			this.breastCrotch = breastCrotch;
			return this;
		}
		
		public BodyBuilder breast(Antenna antenna) {
			this.antenna = antenna;
			return this;
		}

		public BodyBuilder horn(Horn horn) {
			this.horn = horn;
			return this;
		}

		public BodyBuilder penis(Penis penis) {
			this.penis = penis;
			return this;
		}
		
		public BodyBuilder secondPenis(Penis secondPenis) {
			this.secondPenis = secondPenis;
			return this;
		}

		public BodyBuilder tail(Tail tail) {
			this.tail = tail;
			return this;
		}

		public BodyBuilder tentacle(Tentacle tentacle) {
			this.tentacle = tentacle;
			return this;
		}

		public BodyBuilder vagina(Vagina vagina) {
			this.vagina = vagina;
			return this;
		}

		public BodyBuilder wing(Wing wing) {
			this.wing = wing;
			return this;
		}

		@Override
		public Body build() {
			return new Body(this);
		}
	}

	private Body(BodyBuilder builder) {
		antenna = builder.antenna;
		arm = builder.arm;
		ass = builder.ass;
		breast = builder.breast;
		breastCrotch = builder.breastCrotch;
		face = builder.face;
		eye = builder.eye;
		ear = builder.ear;
		hair = builder.hair;
		leg = builder.leg;
		skin = builder.skin;
		horn = builder.horn;
		penis = builder.penis;
		secondPenis = builder.secondPenis;
		tail = builder.tail;
		tentacle = builder.tentacle;
		vagina = builder.vagina;
		wing = builder.wing;
		
		bodyMaterial = builder.bodyMaterial;
		genitalArrangement = builder.genitalArrangement;
		
		height = builder.height;
		femininity = builder.femininity;
		bodySize =builder. bodySize;
		muscle= builder.muscle;
		
		this.pubicHair = BodyHair.ZERO_NONE;
		
		handleAllBodyPartsList();
		
		coverings = new EnumMap<>(BodyCoveringType.class);

		applyStartingCoveringValues();
		
		coveringsDiscovered = EnumSet.noneOf(BodyCoveringType.class);
		for(BodyPartInterface bp : allBodyParts) {
			if(bp.getBodyCoveringType(this)!=null) {
				coveringsDiscovered.add(bp.getBodyCoveringType(this));
			}
		}
		
		addDiscoveredBodyCoveringsFromMaterial(bodyMaterial);
		
		calculateRace(null);
		
		coveringsDiscovered.add(getBodyHairCoveringType(this.getRace()));
	}
	
	public void handleAllBodyPartsList() {
		allBodyParts = new ArrayList<>();
		allBodyParts.add(antenna);
		allBodyParts.add(arm);
		allBodyParts.add(ass);
		allBodyParts.add(breast);
		allBodyParts.add(breastCrotch);
		allBodyParts.add(face);
		allBodyParts.add(eye);
		allBodyParts.add(ear);
		allBodyParts.add(hair);
		allBodyParts.add(leg);
		allBodyParts.add(skin);
		allBodyParts.add(horn);
		allBodyParts.add(penis);
		allBodyParts.add(secondPenis);
		allBodyParts.add(tail);
		allBodyParts.add(tentacle);
		allBodyParts.add(vagina);
		allBodyParts.add(wing);
	}
	
	public void addDiscoveredBodyCoveringsFromMaterial(BodyMaterial bodyMaterial) {
		if(bodyMaterial==BodyMaterial.SLIME) {
			coveringsDiscovered.add(BodyCoveringType.SLIME_EYE);
			coveringsDiscovered.add(BodyCoveringType.SLIME_HAIR);
			coveringsDiscovered.add(BodyCoveringType.SLIME_PUPILS);
			coveringsDiscovered.add(BodyCoveringType.SLIME_SCLERA);
			coveringsDiscovered.add(BodyCoveringType.SLIME_NIPPLES);
			coveringsDiscovered.add(BodyCoveringType.SLIME_NIPPLES_CROTCH);
			coveringsDiscovered.add(BodyCoveringType.SLIME_MOUTH);
			coveringsDiscovered.add(BodyCoveringType.SLIME_ANUS);
			coveringsDiscovered.add(BodyCoveringType.SLIME_VAGINA);
			coveringsDiscovered.add(BodyCoveringType.SLIME_PENIS);
		} else {
			coveringsDiscovered.add(BodyCoveringType.EYE_SCLERA);
			coveringsDiscovered.add(BodyCoveringType.NIPPLES);
			coveringsDiscovered.add(BodyCoveringType.NIPPLES_CROTCH);
			coveringsDiscovered.add(BodyCoveringType.TONGUE);
			coveringsDiscovered.add(BodyCoveringType.MOUTH);
			coveringsDiscovered.add(BodyCoveringType.ANUS);
		}
	}
	
	public static BodyCoveringType getBodyHairCoveringType(Race race) {
		switch(race) {
			case NONE:
				break;
			case ANGEL:
				return BodyCoveringType.BODY_HAIR_ANGEL;
			case CAT_MORPH:
				return BodyCoveringType.BODY_HAIR_FELINE_FUR;
			case COW_MORPH:
				return BodyCoveringType.BODY_HAIR_BOVINE_FUR;
			case DEMON:
				return BodyCoveringType.BODY_HAIR_DEMON;
			case DOG_MORPH:
				return BodyCoveringType.BODY_HAIR_CANINE_FUR;
			case ALLIGATOR_MORPH:
				return BodyCoveringType.BODY_HAIR_SCALES_ALLIGATOR;
			case HARPY:
				return BodyCoveringType.BODY_HAIR_HARPY;
			case HORSE_MORPH:
				return BodyCoveringType.BODY_HAIR_HORSE_HAIR;
			case REINDEER_MORPH:
				return BodyCoveringType.BODY_HAIR_REINDEER_HAIR;
			case HUMAN:
				return BodyCoveringType.BODY_HAIR_HUMAN;
			case SQUIRREL_MORPH:
				return BodyCoveringType.BODY_HAIR_SQUIRREL_FUR;
			case WOLF_MORPH:
				return BodyCoveringType.BODY_HAIR_LYCAN_FUR;
			case SLIME:
				return BodyCoveringType.SLIME_BODY_HAIR;
			case BAT_MORPH:
				return BodyCoveringType.BODY_HAIR_BAT_FUR;
			case RAT_MORPH:
				return BodyCoveringType.BODY_HAIR_RAT_FUR;
			case RABBIT_MORPH:
				return BodyCoveringType.BODY_HAIR_RABBIT_FUR;
			case ELEMENTAL:
				break; // Doesn't matter what is passed in here, as getCovering will catch whatever BodyCoveringType the body is made up of.
			case FOX_MORPH:
				return BodyCoveringType.BODY_HAIR_FOX_FUR;
		}
		return BodyCoveringType.BODY_HAIR_HUMAN;
	}
	
	private void applyStartingCoveringValues() {
		
		// Everything is based on human skin value:
		StartingSkinTone tone = StartingSkinTone.values()[Util.random.nextInt(StartingSkinTone.values().length)];
		
		for (BodyCoveringType s : BodyCoveringType.values()) {
			
			// Specials:
			// orifice exterior/interior
			// makeup
			if(s == BodyCoveringType.MAKEUP_BLUSHER
					|| s == BodyCoveringType.MAKEUP_EYE_LINER
					|| s == BodyCoveringType.MAKEUP_EYE_SHADOW
					|| s == BodyCoveringType.MAKEUP_LIPSTICK
					|| s == BodyCoveringType.MAKEUP_NAIL_POLISH_FEET
					|| s == BodyCoveringType.MAKEUP_NAIL_POLISH_HANDS) {
				coverings.put(s, new Covering(s,
						CoveringPattern.NONE,
						Colour.COVERING_NONE, false,
						Colour.COVERING_NONE, false));
				continue;
			}
			List<Colour> colourApplicationList = BodyCoveringSkinToneColorHelper.getAcceptableColoursForPrimary(tone, s);
			Colour primary = colourApplicationList.get(Util.random.nextInt(colourApplicationList.size()));
			
			Colour secondary = primary;
			
			if(!s.getNaturalColoursSecondary().isEmpty()) {
				colourApplicationList = BodyCoveringSkinToneColorHelper.getAcceptableColoursForSecondary(tone, s);
				secondary = colourApplicationList.get(Util.random.nextInt(colourApplicationList.size()));
			}
			
			List<CoveringPattern> availablePatterns = new ArrayList<>(s.getNaturalPatterns());
			if(availablePatterns.size()>1) {
				availablePatterns.remove(CoveringPattern.FRECKLED); // Do not start with freckles.
			}
			
			CoveringPattern pattern = availablePatterns.get(Util.random.nextInt(availablePatterns.size()));
			
			if(pattern == CoveringPattern.EYE_IRISES_HETEROCHROMATIC) {
				if(Math.random()>0.02f) { // As it's already selected heterochromatic eyes (0.5 chance), this 0.02 chance corresponds to an overall heterochromatic chance of 0.01, or 1%
					pattern = CoveringPattern.EYE_IRISES;
				} else {
					if(primary==secondary) {
						List<Colour> secondaryIrisColours = new ArrayList<>();
						secondaryIrisColours.addAll(colourApplicationList);
						secondaryIrisColours.remove(primary);
						secondary = colourApplicationList.get(Util.random.nextInt(colourApplicationList.size()));
					}
				}
			}
			
			coverings.put(s, new Covering(s,
					pattern,
					primary, false,
					secondary, false));
		}

		updateCoverings(true, true, true, true);
	}
	
	
	
	
	@Override
	public Element saveAsXML(Element parentElement, Document doc) {
		// Core:
		Element bodyCore = doc.createElement("bodyCore");
		parentElement.appendChild(bodyCore);
		CharacterUtils.addAttribute(doc, bodyCore, "piercedStomach", String.valueOf(this.isPiercedStomach()));
		CharacterUtils.addAttribute(doc, bodyCore, "height", String.valueOf(this.getHeightValue()));
		CharacterUtils.addAttribute(doc, bodyCore, "femininity", String.valueOf(this.getFemininity()));
		CharacterUtils.addAttribute(doc, bodyCore, "bodySize", String.valueOf(this.getBodySize()));
		CharacterUtils.addAttribute(doc, bodyCore, "muscle", String.valueOf(this.getMuscle()));
		CharacterUtils.addAttribute(doc, bodyCore, "pubicHair", String.valueOf(this.getPubicHair()));
		CharacterUtils.addAttribute(doc, bodyCore, "bodyMaterial", String.valueOf(this.getBodyMaterial()));
		CharacterUtils.addAttribute(doc, bodyCore, "genitalArrangement", String.valueOf(this.getGenitalArrangement()));
		if(this.getSubspeciesOverride()!=null) {
			CharacterUtils.addAttribute(doc, bodyCore, "subspeciesOverride", String.valueOf(this.getSubspeciesOverride()));
		}
		if(this.getHalfDemonSubspecies()!=null) {
			CharacterUtils.addAttribute(doc, bodyCore, "halfDemonSubspecies", String.valueOf(this.getHalfDemonSubspecies()));
		}
		CharacterUtils.addAttribute(doc, bodyCore, "takesAfterMother", String.valueOf(this.isTakesAfterMother()));
		
		for(BodyCoveringType bct : BodyCoveringType.values()) {
			if(this.getBodyCoveringTypesDiscovered().contains(bct)
					|| ((bct == BodyCoveringType.MAKEUP_BLUSHER
							|| bct == BodyCoveringType.MAKEUP_EYE_LINER
							|| bct == BodyCoveringType.MAKEUP_EYE_SHADOW
							|| bct == BodyCoveringType.MAKEUP_LIPSTICK
							|| bct == BodyCoveringType.MAKEUP_NAIL_POLISH_FEET
							|| bct == BodyCoveringType.MAKEUP_NAIL_POLISH_HANDS)
							&& this.coverings.get(bct).getPrimaryColour()!=Colour.COVERING_NONE)
					|| bct == BodyCoveringType.EYE_PUPILS
					|| bct == getBodyHairCoveringType(this.getRace())) {
				Element element = doc.createElement("bodyCovering");
				bodyCore.appendChild(element);
				
				CharacterUtils.addAttribute(doc, element, "type", bct.toString());
				CharacterUtils.addAttribute(doc, element, "pattern", this.coverings.get(bct).getPattern().toString());
				CharacterUtils.addAttribute(doc, element, "modifier", this.coverings.get(bct).getModifier().toString());
				CharacterUtils.addAttribute(doc, element, "c1", this.coverings.get(bct).getPrimaryColour().toString());
				if(this.coverings.get(bct).isPrimaryGlowing()) {
					CharacterUtils.addAttribute(doc, element, "g1", String.valueOf(this.coverings.get(bct).isPrimaryGlowing()));
				}
				CharacterUtils.addAttribute(doc, element, "c2", this.coverings.get(bct).getSecondaryColour().toString());
				if(this.coverings.get(bct).isSecondaryGlowing()) {
					CharacterUtils.addAttribute(doc, element, "g2", String.valueOf(this.coverings.get(bct).isSecondaryGlowing()));
				}
				if(this.getBodyCoveringTypesDiscovered().contains(bct)) {
					CharacterUtils.addAttribute(doc, element, "discovered", String.valueOf(this.getBodyCoveringTypesDiscovered().contains(bct)));
				}
			}
		}
		

		// Antennae:
		Element bodyAntennae = doc.createElement("antennae");
		parentElement.appendChild(bodyAntennae);
			CharacterUtils.addAttribute(doc, bodyAntennae, "type", this.antenna.getType().toString());
			CharacterUtils.addAttribute(doc, bodyAntennae, "rows", String.valueOf(this.antenna.getAntennaRows()));
		
		// Arm:
		Element bodyArm = doc.createElement("arm");
		parentElement.appendChild(bodyArm);
			CharacterUtils.addAttribute(doc, bodyArm, "type", ArmType.getIdFromArmType(this.arm.getType()));
			CharacterUtils.addAttribute(doc, bodyArm, "rows", String.valueOf(this.arm.getArmRows()));
			CharacterUtils.addAttribute(doc, bodyArm, "underarmHair", this.arm.getUnderarmHair().toString());
		
		// Ass:
		Element bodyAss = doc.createElement("ass");
		parentElement.appendChild(bodyAss);
			CharacterUtils.addAttribute(doc, bodyAss, "type", AssType.getIdFromAssType(this.ass.getType()));
			CharacterUtils.addAttribute(doc, bodyAss, "assSize", String.valueOf(this.ass.getAssSize().getValue()));
			CharacterUtils.addAttribute(doc, bodyAss, "hipSize", String.valueOf(this.ass.getHipSize().getValue()));

		Element bodyAnus = doc.createElement("anus");
		parentElement.appendChild(bodyAnus);
			CharacterUtils.addAttribute(doc, bodyAnus, "wetness", String.valueOf(this.ass.anus.orificeAnus.wetness));
			CharacterUtils.addAttribute(doc, bodyAnus, "elasticity", String.valueOf(this.ass.anus.orificeAnus.elasticity));
			CharacterUtils.addAttribute(doc, bodyAnus, "plasticity", String.valueOf(this.ass.anus.orificeAnus.plasticity));
			CharacterUtils.addAttribute(doc, bodyAnus, "capacity", String.valueOf(this.ass.anus.orificeAnus.capacity));
			CharacterUtils.addAttribute(doc, bodyAnus, "stretchedCapacity", String.valueOf(this.ass.anus.orificeAnus.stretchedCapacity));
			CharacterUtils.addAttribute(doc, bodyAnus, "virgin", String.valueOf(this.ass.anus.orificeAnus.virgin));
			CharacterUtils.addAttribute(doc, bodyAnus, "bleached", String.valueOf(this.ass.anus.bleached));
			CharacterUtils.addAttribute(doc, bodyAnus, "assHair", this.ass.anus.assHair.toString());
			Element anusModifiers = doc.createElement("anusModifiers");
			bodyAnus.appendChild(anusModifiers);
			for(OrificeModifier om : this.ass.anus.orificeAnus.getOrificeModifiers()) {
				CharacterUtils.addAttribute(doc, anusModifiers, om.toString(), "true");
			}
		
		// Breasts:
		Element bodyBreast = doc.createElement("breasts");
		parentElement.appendChild(bodyBreast);
			CharacterUtils.addAttribute(doc, bodyBreast, "type", BreastType.getIdFromBreastType(this.breast.getType()));
			CharacterUtils.addAttribute(doc, bodyBreast, "shape", this.breast.shape.toString());
			CharacterUtils.addAttribute(doc, bodyBreast, "size", String.valueOf(this.breast.size));
			CharacterUtils.addAttribute(doc, bodyBreast, "rows", String.valueOf(this.breast.rows));
			CharacterUtils.addAttribute(doc, bodyBreast, "milkStorage", String.valueOf(this.breast.milkStorage));
			CharacterUtils.addAttribute(doc, bodyBreast, "storedMilk", String.valueOf(this.breast.milkStored));
			CharacterUtils.addAttribute(doc, bodyBreast, "milkRegeneration", String.valueOf(this.breast.milkRegeneration));
			CharacterUtils.addAttribute(doc, bodyBreast, "nippleCountPerBreast", String.valueOf(this.breast.nippleCountPerBreast));

		Element bodyNipple = doc.createElement("nipples");
		parentElement.appendChild(bodyNipple);
			CharacterUtils.addAttribute(doc, bodyNipple, "elasticity", String.valueOf(this.breast.nipples.orificeNipples.elasticity));
			CharacterUtils.addAttribute(doc, bodyNipple, "plasticity", String.valueOf(this.breast.nipples.orificeNipples.plasticity));
			CharacterUtils.addAttribute(doc, bodyNipple, "capacity", String.valueOf(this.breast.nipples.orificeNipples.capacity));
			CharacterUtils.addAttribute(doc, bodyNipple, "stretchedCapacity", String.valueOf(this.breast.nipples.orificeNipples.stretchedCapacity));
			CharacterUtils.addAttribute(doc, bodyNipple, "virgin", String.valueOf(this.breast.nipples.orificeNipples.virgin));
			CharacterUtils.addAttribute(doc, bodyNipple, "pierced", String.valueOf(this.breast.nipples.pierced));
			CharacterUtils.addAttribute(doc, bodyNipple, "nippleSize", String.valueOf(this.breast.nipples.nippleSize));
			CharacterUtils.addAttribute(doc, bodyNipple, "nippleShape", this.breast.nipples.nippleShape.toString());
			CharacterUtils.addAttribute(doc, bodyNipple, "areolaeSize", String.valueOf(this.breast.nipples.areolaeSize));
			CharacterUtils.addAttribute(doc, bodyNipple, "areolaeShape", this.breast.nipples.areolaeShape.toString());
			Element nippleModifiers = doc.createElement("nippleModifiers");
			bodyNipple.appendChild(nippleModifiers);
			for(OrificeModifier om : this.breast.nipples.orificeNipples.getOrificeModifiers()) {
				CharacterUtils.addAttribute(doc, nippleModifiers, om.toString(), "true");
			}
			
		this.breast.milk.saveAsXML(parentElement, doc);
		
		// Crotch Breasts:
		Element bodyCrotchBreast = doc.createElement("breastsCrotch");
		parentElement.appendChild(bodyCrotchBreast);
			CharacterUtils.addAttribute(doc, bodyCrotchBreast, "type", BreastType.getIdFromBreastType(this.breastCrotch.getType()));
			CharacterUtils.addAttribute(doc, bodyCrotchBreast, "shape", this.breastCrotch.shape.toString());
			CharacterUtils.addAttribute(doc, bodyCrotchBreast, "size", String.valueOf(this.breastCrotch.size));
			CharacterUtils.addAttribute(doc, bodyCrotchBreast, "rows", String.valueOf(this.breastCrotch.rows));
			CharacterUtils.addAttribute(doc, bodyCrotchBreast, "milkStorage", String.valueOf(this.breastCrotch.milkStorage));
			CharacterUtils.addAttribute(doc, bodyCrotchBreast, "storedMilk", String.valueOf(this.breastCrotch.milkStored));
			CharacterUtils.addAttribute(doc, bodyCrotchBreast, "milkRegeneration", String.valueOf(this.breastCrotch.milkRegeneration));
			CharacterUtils.addAttribute(doc, bodyCrotchBreast, "nippleCountPerBreast", String.valueOf(this.breastCrotch.nippleCountPerBreast));

		Element bodyCrotchNipple = doc.createElement("nipplesCrotch");
		parentElement.appendChild(bodyCrotchNipple);
			CharacterUtils.addAttribute(doc, bodyCrotchNipple, "elasticity", String.valueOf(this.breastCrotch.nipples.orificeNipples.elasticity));
			CharacterUtils.addAttribute(doc, bodyCrotchNipple, "plasticity", String.valueOf(this.breastCrotch.nipples.orificeNipples.plasticity));
			CharacterUtils.addAttribute(doc, bodyCrotchNipple, "capacity", String.valueOf(this.breastCrotch.nipples.orificeNipples.capacity));
			CharacterUtils.addAttribute(doc, bodyCrotchNipple, "stretchedCapacity", String.valueOf(this.breastCrotch.nipples.orificeNipples.stretchedCapacity));
			CharacterUtils.addAttribute(doc, bodyCrotchNipple, "virgin", String.valueOf(this.breastCrotch.nipples.orificeNipples.virgin));
			CharacterUtils.addAttribute(doc, bodyCrotchNipple, "pierced", String.valueOf(this.breastCrotch.nipples.pierced));
			CharacterUtils.addAttribute(doc, bodyCrotchNipple, "nippleSize", String.valueOf(this.breastCrotch.nipples.nippleSize));
			CharacterUtils.addAttribute(doc, bodyCrotchNipple, "nippleShape", this.breastCrotch.nipples.nippleShape.toString());
			CharacterUtils.addAttribute(doc, bodyCrotchNipple, "areolaeSize", String.valueOf(this.breastCrotch.nipples.areolaeSize));
			CharacterUtils.addAttribute(doc, bodyCrotchNipple, "areolaeShape", this.breastCrotch.nipples.areolaeShape.toString());
			Element crotchNippleModifiers = doc.createElement("nippleModifiers");
			bodyCrotchNipple.appendChild(crotchNippleModifiers);
			for(OrificeModifier om : this.breastCrotch.nipples.orificeNipples.getOrificeModifiers()) {
				CharacterUtils.addAttribute(doc, crotchNippleModifiers, om.toString(), "true");
			}
			
		this.breastCrotch.milk.saveAsXML(parentElement, doc);
		
		
		// Ear:
		Element bodyEar = doc.createElement("ear");
		parentElement.appendChild(bodyEar);
			CharacterUtils.addAttribute(doc, bodyEar, "type", EarType.getIdFromEarType(this.ear.type));
			CharacterUtils.addAttribute(doc, bodyEar, "pierced", String.valueOf(this.ear.pierced));

		// Eye:
		Element bodyEye = doc.createElement("eye");
		parentElement.appendChild(bodyEye);
			CharacterUtils.addAttribute(doc, bodyEye, "type", this.eye.type.toString());
			CharacterUtils.addAttribute(doc, bodyEye, "eyePairs", String.valueOf(this.eye.eyePairs));
			CharacterUtils.addAttribute(doc, bodyEye, "irisShape", this.eye.irisShape.toString());
			CharacterUtils.addAttribute(doc, bodyEye, "pupilShape", this.eye.pupilShape.toString());
		
		// Face:
		Element bodyFace = doc.createElement("face");
		parentElement.appendChild(bodyFace);
			CharacterUtils.addAttribute(doc, bodyFace, "type", this.face.type.toString());
			CharacterUtils.addAttribute(doc, bodyFace, "piercedNose", String.valueOf(this.face.piercedNose));
			CharacterUtils.addAttribute(doc, bodyFace, "facialHair", this.face.facialHair.toString());

		Element bodyMouth = doc.createElement("mouth");
		parentElement.appendChild(bodyMouth);
			CharacterUtils.addAttribute(doc, bodyMouth, "elasticity", String.valueOf(this.face.mouth.orificeMouth.elasticity));
			CharacterUtils.addAttribute(doc, bodyMouth, "plasticity", String.valueOf(this.face.mouth.orificeMouth.plasticity));
			CharacterUtils.addAttribute(doc, bodyMouth, "capacity", String.valueOf(this.face.mouth.orificeMouth.capacity));
			CharacterUtils.addAttribute(doc, bodyMouth, "wetness", String.valueOf(this.face.mouth.orificeMouth.wetness));
			CharacterUtils.addAttribute(doc, bodyMouth, "stretchedCapacity", String.valueOf(this.face.mouth.orificeMouth.stretchedCapacity));
			CharacterUtils.addAttribute(doc, bodyMouth, "virgin", String.valueOf(this.face.mouth.orificeMouth.virgin));
			CharacterUtils.addAttribute(doc, bodyMouth, "piercedLip", String.valueOf(this.face.mouth.piercedLip));
			CharacterUtils.addAttribute(doc, bodyMouth, "lipSize", String.valueOf(this.face.mouth.lipSize));
			Element mouthModifiers = doc.createElement("mouthModifiers");
			bodyMouth.appendChild(mouthModifiers);
			for(OrificeModifier om : this.face.mouth.orificeMouth.getOrificeModifiers()) {
				CharacterUtils.addAttribute(doc, mouthModifiers, om.toString(), "true");
			}
			
		Element bodyTongue = doc.createElement("tongue");
		parentElement.appendChild(bodyTongue);
			CharacterUtils.addAttribute(doc, bodyTongue, "piercedTongue", String.valueOf(this.face.tongue.pierced));
			CharacterUtils.addAttribute(doc, bodyTongue, "tongueLength", String.valueOf(this.face.tongue.tongueLength));
			Element tongueModifiers = doc.createElement("tongueModifiers");
			bodyTongue.appendChild(tongueModifiers);
			for(TongueModifier tm : this.face.tongue.tongueModifiers) {
				CharacterUtils.addAttribute(doc, tongueModifiers, tm.toString(), "true");
			}
			
		
		// Hair:
		Element bodyHair = doc.createElement("hair");
		parentElement.appendChild(bodyHair);
			CharacterUtils.addAttribute(doc, bodyHair, "type", this.hair.type.toString());
			CharacterUtils.addAttribute(doc, bodyHair, "length", String.valueOf(this.hair.length));
			CharacterUtils.addAttribute(doc, bodyHair, "hairStyle", this.hair.style.toString());
		
		// Horn:
		Element bodyHorn = doc.createElement("horn");
		parentElement.appendChild(bodyHorn);
			CharacterUtils.addAttribute(doc, bodyHorn, "type", HornType.getIdFromHornType(this.horn.type));
			CharacterUtils.addAttribute(doc, bodyHorn, "length", String.valueOf(this.horn.length));
			CharacterUtils.addAttribute(doc, bodyHorn, "rows", String.valueOf(this.horn.rows));
			CharacterUtils.addAttribute(doc, bodyHorn, "hornsPerRow", String.valueOf(this.horn.hornsPerRow));
		
		// Leg:
		Element bodyLeg = doc.createElement("leg");
		parentElement.appendChild(bodyLeg);
			CharacterUtils.addAttribute(doc, bodyLeg, "type", LegType.getIdFromLegType(this.leg.type));
			CharacterUtils.addAttribute(doc, bodyLeg, "footStructure", this.leg.footStructure.toString());
			CharacterUtils.addAttribute(doc, bodyLeg, "configuration", this.leg.legConfiguration.toString());
		
		// Penis:
		Element bodyPenis = doc.createElement("penis");
		parentElement.appendChild(bodyPenis);
			CharacterUtils.addAttribute(doc, bodyPenis, "type", this.penis.type.toString());
			CharacterUtils.addAttribute(doc, bodyPenis, "size", String.valueOf(this.penis.size));
			CharacterUtils.addAttribute(doc, bodyPenis, "girth", String.valueOf(this.penis.girth));
			CharacterUtils.addAttribute(doc, bodyPenis, "pierced", String.valueOf(this.penis.pierced));
			CharacterUtils.addAttribute(doc, bodyPenis, "virgin", String.valueOf(this.penis.virgin));
			Element penisModifiers = doc.createElement("penisModifiers");
			bodyPenis.appendChild(penisModifiers);
			for(PenetrationModifier pm : this.penis.getPenisModifiers()) {
				CharacterUtils.addAttribute(doc, penisModifiers, pm.toString(), "true");
			}
			CharacterUtils.addAttribute(doc, bodyPenis, "elasticity", String.valueOf(this.penis.orificeUrethra.elasticity));
			CharacterUtils.addAttribute(doc, bodyPenis, "plasticity", String.valueOf(this.penis.orificeUrethra.plasticity));
			CharacterUtils.addAttribute(doc, bodyPenis, "capacity", String.valueOf(this.penis.orificeUrethra.capacity));
			CharacterUtils.addAttribute(doc, bodyPenis, "stretchedCapacity", String.valueOf(this.penis.orificeUrethra.stretchedCapacity));
			CharacterUtils.addAttribute(doc, bodyPenis, "urethraVirgin", String.valueOf(this.penis.orificeUrethra.virgin));
			Element urethraModifiers = doc.createElement("urethraModifiers");
			bodyPenis.appendChild(urethraModifiers);
			for(OrificeModifier om : this.penis.orificeUrethra.getOrificeModifiers()) {
				CharacterUtils.addAttribute(doc, urethraModifiers, om.toString(), "true");
			}
			
		Element bodyTesticle = doc.createElement("testicles");
		parentElement.appendChild(bodyTesticle);
			CharacterUtils.addAttribute(doc, bodyTesticle, "testicleSize", String.valueOf(this.penis.testicle.testicleSize));
			CharacterUtils.addAttribute(doc, bodyTesticle, "cumStorage", String.valueOf(this.penis.testicle.cumStorage));
			CharacterUtils.addAttribute(doc, bodyTesticle, "storedCum", String.valueOf(this.penis.testicle.cumStored));
			CharacterUtils.addAttribute(doc, bodyTesticle, "cumRegeneration", String.valueOf(this.penis.testicle.cumRegeneration));
			CharacterUtils.addAttribute(doc, bodyTesticle, "cumExpulsion", String.valueOf(this.penis.testicle.cumExpulsion));
			CharacterUtils.addAttribute(doc, bodyTesticle, "numberOfTesticles", String.valueOf(this.penis.testicle.testicleCount));
			CharacterUtils.addAttribute(doc, bodyTesticle, "internal", String.valueOf(this.penis.testicle.internal));
		
		this.penis.testicle.cum.saveAsXML(parentElement, doc);
		
		
		// Skin:
		Element bodySkin = doc.createElement("skin");
		parentElement.appendChild(bodySkin);
			CharacterUtils.addAttribute(doc, bodySkin, "type", this.skin.type.toString());
		
		// Tail:
		Element bodyTail = doc.createElement("tail");
		parentElement.appendChild(bodyTail);
			CharacterUtils.addAttribute(doc, bodyTail, "type", this.tail.type.toString());
			CharacterUtils.addAttribute(doc, bodyTail, "count", String.valueOf(this.tail.tailCount));
		
		// Tail:
		Element bodyTentacle = doc.createElement("tentacle");
		parentElement.appendChild(bodyTentacle);
			CharacterUtils.addAttribute(doc, bodyTentacle, "type", this.tentacle.type.toString());
			CharacterUtils.addAttribute(doc, bodyTentacle, "count", String.valueOf(this.tentacle.tentacleCount));
			
		// Vagina
		Element bodyVagina = doc.createElement("vagina");
		parentElement.appendChild(bodyVagina);
			CharacterUtils.addAttribute(doc, bodyVagina, "type", this.vagina.type.toString());
			CharacterUtils.addAttribute(doc, bodyVagina, "labiaSize", String.valueOf(this.vagina.labiaSize));
			CharacterUtils.addAttribute(doc, bodyVagina, "clitSize", String.valueOf(this.vagina.clitoris.clitSize));
			CharacterUtils.addAttribute(doc, bodyVagina, "clitGirth", String.valueOf(this.vagina.clitoris.girth));
			Element clitModifiers = doc.createElement("clitModifiers");
			bodyVagina.appendChild(clitModifiers);
			for(PenetrationModifier pm : this.vagina.clitoris.getClitorisModifiers()) {
				CharacterUtils.addAttribute(doc, clitModifiers, pm.toString(), "true");
			}
			CharacterUtils.addAttribute(doc, bodyVagina, "pierced", String.valueOf(this.vagina.pierced));
			
			CharacterUtils.addAttribute(doc, bodyVagina, "wetness", String.valueOf(this.vagina.orificeVagina.wetness));
			CharacterUtils.addAttribute(doc, bodyVagina, "elasticity", String.valueOf(this.vagina.orificeVagina.elasticity));
			CharacterUtils.addAttribute(doc, bodyVagina, "plasticity", String.valueOf(this.vagina.orificeVagina.plasticity));
			CharacterUtils.addAttribute(doc, bodyVagina, "capacity", String.valueOf(this.vagina.orificeVagina.capacity));
			CharacterUtils.addAttribute(doc, bodyVagina, "stretchedCapacity", String.valueOf(this.vagina.orificeVagina.stretchedCapacity));
			CharacterUtils.addAttribute(doc, bodyVagina, "virgin", String.valueOf(this.vagina.orificeVagina.virgin));
			CharacterUtils.addAttribute(doc, bodyVagina, "squirter", String.valueOf(this.vagina.orificeVagina.squirter));
			Element vaginaModifiers = doc.createElement("vaginaModifiers");
			bodyVagina.appendChild(vaginaModifiers);
			for(OrificeModifier om : this.vagina.orificeVagina.getOrificeModifiers()) {
				CharacterUtils.addAttribute(doc, vaginaModifiers, om.toString(), "true");
			}

			CharacterUtils.addAttribute(doc, bodyVagina, "urethraElasticity", String.valueOf(this.vagina.orificeUrethra.elasticity));
			CharacterUtils.addAttribute(doc, bodyVagina, "urethraPlasticity", String.valueOf(this.vagina.orificeUrethra.plasticity));
			CharacterUtils.addAttribute(doc, bodyVagina, "urethraCapacity", String.valueOf(this.vagina.orificeUrethra.capacity));
			CharacterUtils.addAttribute(doc, bodyVagina, "urethraStretchedCapacity", String.valueOf(this.vagina.orificeUrethra.stretchedCapacity));
			CharacterUtils.addAttribute(doc, bodyVagina, "urethraVirgin", String.valueOf(this.vagina.orificeUrethra.virgin));
			urethraModifiers = doc.createElement("urethraModifiers");
			bodyVagina.appendChild(urethraModifiers);
			for(OrificeModifier om : this.vagina.orificeUrethra.getOrificeModifiers()) {
				CharacterUtils.addAttribute(doc, urethraModifiers, om.toString(), "true");
			}
			
		this.vagina.girlcum.saveAsXML(parentElement, doc);
			
		
		// Wing:
		Element bodyWing = doc.createElement("wing");
		parentElement.appendChild(bodyWing);
		CharacterUtils.addAttribute(doc, bodyWing, "type", this.wing.type.toString());
		CharacterUtils.addAttribute(doc, bodyWing, "size", String.valueOf(this.wing.size));

//		System.out.println("Difference1: "+(System.nanoTime()-timeStart)/1000000000f);
		
		return parentElement;
	}

	
	private void setBodyCoveringForXMLImport(BodyCoveringType bct, CoveringPattern pattern, CoveringModifier modifier, Colour primary, boolean primaryGlow, Colour secondary, boolean secondaryGlow) {
		this.getCoverings().put(bct, new Covering(bct, pattern, modifier, primary, primaryGlow, secondary, secondaryGlow));
	}
	private void setBodyCoveringForXMLImport(BodyCoveringType bct, CoveringPattern pattern, Colour primary, boolean primaryGlow, Colour secondary, boolean secondaryGlow) {
		this.getCoverings().put(bct, new Covering(bct, pattern, primary, primaryGlow, secondary, secondaryGlow));
	}
	
	public static Body loadFromXML(StringBuilder log, Element parentElement, Document doc) {
		
		// **************** Core **************** //
		
		Element element = (Element) parentElement.getElementsByTagName("bodyCore").item(0);
		int importedFemininity = (Integer.valueOf(element.getAttribute("femininity")));
		CharacterUtils.appendToImportLog(log, "<br/>Body: Set femininity: "+Integer.valueOf(element.getAttribute("femininity")));
		
		int importedHeight =(Integer.valueOf(element.getAttribute("height")));
		CharacterUtils.appendToImportLog(log, "<br/>Body: Set height: "+Integer.valueOf(element.getAttribute("height")));
		
		int importedBodySize = (Integer.valueOf(element.getAttribute("bodySize")));
		CharacterUtils.appendToImportLog(log, "<br/>Body: Set body size: "+Integer.valueOf(element.getAttribute("bodySize")));
	
		int importedMuscle = (Integer.valueOf(element.getAttribute("muscle")));
		CharacterUtils.appendToImportLog(log, "<br/>Body: Set muscle: "+Integer.valueOf(element.getAttribute("muscle")));
		
		GenitalArrangement importedGenitalArrangement = GenitalArrangement.NORMAL;
		if(element.getAttribute("genitalArrangement") != null && !element.getAttribute("genitalArrangement").isEmpty()) {
			importedGenitalArrangement = GenitalArrangement.valueOf(element.getAttribute("genitalArrangement"));
		}
		
		BodyMaterial importedBodyMaterial = BodyMaterial.FLESH;
		if(element.getAttribute("bodyMaterial") != null && !element.getAttribute("bodyMaterial").isEmpty()) {
			importedBodyMaterial = BodyMaterial.valueOf(element.getAttribute("bodyMaterial"));
		}

		Subspecies importedSubspeciesOverride = null;
		try {
			if(element.getAttribute("subspeciesOverride") != null && !element.getAttribute("subspeciesOverride").isEmpty()) {
				importedSubspeciesOverride = Subspecies.valueOf(element.getAttribute("subspeciesOverride"));
			}
		} catch(Exception ex) {	
		}
		
		
		
		// **************** Antenna **************** //
		
		Element antennae = (Element)parentElement.getElementsByTagName("antennae").item(0);
		
		Antenna importedAntenna = new Antenna(AntennaType.getTypeFromString(antennae.getAttribute("type")));
		CharacterUtils.appendToImportLog(log, "<br/><br/>Body: Antennae:"+ "<br/>type: "+importedAntenna.getType());

		importedAntenna.rows = Integer.valueOf(antennae.getAttribute("rows"));
		CharacterUtils.appendToImportLog(log, "<br/>rows: "+importedAntenna.getAntennaRows());
		
		
		// **************** Arm **************** //
		
		Element arm = (Element)parentElement.getElementsByTagName("arm").item(0);
		
		Arm importedArm = new Arm(ArmType.getArmTypeFromId(arm.getAttribute("type")), Integer.valueOf(arm.getAttribute("rows")));
		
		CharacterUtils.appendToImportLog(log, "<br/><br/>Body: Arm:"+ "<br/>type: "+importedArm.getType());

		CharacterUtils.appendToImportLog(log, "<br/>rows: "+importedArm.getArmRows());

		try {
			importedArm.underarmHair = BodyHair.valueOf(arm.getAttribute("underarmHair"));
			CharacterUtils.appendToImportLog(log, "<br/>underarm hair: "+importedArm.getUnderarmHair());
		} catch(IllegalArgumentException e) {
			importedArm.underarmHair = BodyHair.ZERO_NONE;
			CharacterUtils.appendToImportLog(log, "<br/>underarm hair: OLD_VALUE - Set to NONE");
		}
		
		// **************** Ass **************** //
		
		Element ass = (Element)parentElement.getElementsByTagName("ass").item(0);
		Element anus = (Element)parentElement.getElementsByTagName("anus").item(0);
		
		Ass importedAss = new Ass(AssType.getAssTypeFromId(ass.getAttribute("type")),
				Integer.valueOf(ass.getAttribute("assSize")),
				Integer.valueOf(anus.getAttribute("wetness")),
				Float.valueOf(anus.getAttribute("capacity")),
				Integer.valueOf(anus.getAttribute("elasticity")),
				Integer.valueOf(anus.getAttribute("plasticity")),
				Boolean.valueOf(anus.getAttribute("virgin")));
		
		importedAss.hipSize = Integer.valueOf(ass.getAttribute("hipSize"));
		
		importedAss.anus.orificeAnus.stretchedCapacity = (Float.valueOf(anus.getAttribute("stretchedCapacity")));
		importedAss.anus.bleached = (Boolean.valueOf(anus.getAttribute("bleached")));
		try {
			importedAss.anus.assHair = (BodyHair.valueOf(anus.getAttribute("assHair")));
		} catch(IllegalArgumentException e) {
			importedAss.anus.assHair = BodyHair.ZERO_NONE;
			CharacterUtils.appendToImportLog(log, "<br/>ass hair: OLD_VALUE - Set to NONE");
		}
		
		CharacterUtils.appendToImportLog(log, "<br/><br/>Body: Ass:"
				+ "<br/>type: "+importedAss.getType()
				+ "<br/>assSize: "+importedAss.getAssSize()
				+ "<br/>hipSize: "+importedAss.getHipSize());
		
		if (anus != null) {
			CharacterUtils.appendToImportLog(log, "<br/><br/>Anus:"
					+ "<br/>wetness: "+importedAss.anus.orificeAnus.wetness
					+ "<br/>elasticity: "+importedAss.anus.orificeAnus.elasticity
					+ "<br/>elasticity: "+importedAss.anus.orificeAnus.plasticity
					+ "<br/>capacity: "+importedAss.anus.orificeAnus.capacity
					+ "<br/>stretchedCapacity: "+importedAss.anus.orificeAnus.stretchedCapacity
					+ "<br/>virgin: "+importedAss.anus.orificeAnus.virgin
					+ "<br/>bleached: "+importedAss.anus.bleached
					+ "<br/>assHair: "+importedAss.anus.assHair
					+"<br/>Modifiers:");
			Element anusModifiersElement = (Element)anus.getElementsByTagName("anusModifiers").item(0);
			Collection<OrificeModifier> anusModifiers = importedAss.anus.orificeAnus.orificeModifiers;
			anusModifiers.clear();
			if(anusModifiersElement!=null) {
				handleLoadingOfModifiers(OrificeModifier.values(), log, anusModifiersElement, anusModifiers);
			}
		}
		

		// **************** Breasts **************** //
		
		Element breasts = (Element)parentElement.getElementsByTagName("breasts").item(0);
		Element nipples = (Element)parentElement.getElementsByTagName("nipples").item(0);
		
		BreastShape breastShape = BreastShape.ROUND;
		try {
			breastShape = BreastShape.valueOf(breasts.getAttribute("shape"));
		} catch(Exception e) {
		}
		
		int milkStorage = 0;
		try {
			if(!breasts.getAttribute("lactation").isEmpty()) {
				milkStorage = Integer.valueOf(breasts.getAttribute("lactation"));
			} else {
				milkStorage = Integer.valueOf(breasts.getAttribute("milkStorage"));
			}
		} catch(Exception ex) {
		}
		
		Breast importedBreast = new Breast(BreastType.getBreastTypeFromId(breasts.getAttribute("type")),
				breastShape,
				Integer.valueOf(breasts.getAttribute("size")),
				milkStorage,
				Integer.valueOf(breasts.getAttribute("rows")),
				Integer.valueOf(nipples.getAttribute("nippleSize")),
				NippleShape.valueOf(nipples.getAttribute("nippleShape")),
				Integer.valueOf(nipples.getAttribute("areolaeSize")),
				Integer.valueOf(breasts.getAttribute("nippleCountPerBreast")),
				Float.valueOf(nipples.getAttribute("capacity")),
				Integer.valueOf(nipples.getAttribute("elasticity")),
				Integer.valueOf(nipples.getAttribute("plasticity")),
				Boolean.valueOf(nipples.getAttribute("virgin")));

		try {
			importedBreast.milkStored = Float.valueOf(breasts.getAttribute("storedMilk"));
			importedBreast.milkRegeneration = Integer.valueOf(breasts.getAttribute("milkRegeneration"));
			if(Main.isVersionOlderThan(Game.loadingVersion, "0.3.1.9")) { // Change from percentage-based to set value:
				importedBreast.milkRegeneration = FluidRegeneration.ONE_AVERAGE.getMedianRegenerationValuePerDay();
			}
		} catch(Exception ex) {
		}
		
		importedBreast.nipples.crotchNipples = false;
		importedBreast.nipples.orificeNipples.stretchedCapacity = (Float.valueOf(nipples.getAttribute("stretchedCapacity")));
		importedBreast.nipples.pierced = (Boolean.valueOf(nipples.getAttribute("pierced")));
		importedBreast.nipples.areolaeShape = (AreolaeShape.valueOf(nipples.getAttribute("areolaeShape")));
		
		CharacterUtils.appendToImportLog(log, "<br/><br/>Body: Breasts:"
				+ "<br/>type: "+importedBreast.getType()
				+ "<br/>size: "+importedBreast.getSize()
				+ "<br/>rows: "+importedBreast.getRows()
				+ "<br/>lactation: "+importedBreast.getRawMilkStorageValue()
				+ "<br/>nippleCountPer: "+importedBreast.getNippleCountPerBreast()
				
				+ "<br/><br/>Nipples:"
				+ "<br/>elasticity: "+importedBreast.nipples.orificeNipples.getElasticity()
				+ "<br/>plasticity: "+importedBreast.nipples.orificeNipples.getPlasticity()
				+ "<br/>capacity: "+importedBreast.nipples.orificeNipples.getRawCapacityValue()
				+ "<br/>stretchedCapacity: "+importedBreast.nipples.orificeNipples.getStretchedCapacity()
				+ "<br/>virgin: "+importedBreast.nipples.orificeNipples.isVirgin()
				+ "<br/>pierced: "+importedBreast.nipples.isPierced()
				+ "<br/>nippleSize: "+importedBreast.nipples.getNippleSize()
				+ "<br/>nippleShape: "+importedBreast.nipples.getNippleShape()
				+ "<br/>areolaeSize: "+importedBreast.nipples.getAreolaeSize()
				+ "<br/>areolaeShape: "+importedBreast.nipples.getAreolaeShape()
				+"<br/>Modifiers:");
		
		Element nippleModifiersElement = (Element)nipples.getElementsByTagName("nippleModifiers").item(0);
		Collection<OrificeModifier> nippleOrificeModifiers = importedBreast.nipples.orificeNipples.orificeModifiers;
		nippleOrificeModifiers.clear();
		if(nippleModifiersElement!=null) {
			handleLoadingOfModifiers(OrificeModifier.values(), log, nippleModifiersElement, nippleOrificeModifiers);
		}
		
		CharacterUtils.appendToImportLog(log, "<br/><br/>Milk:");
		
		importedBreast.milk = FluidMilk.loadFromXML(parentElement, doc, importedBreast.getType().getFluidType(), false);
		if(Main.isVersionOlderThan(Main.VERSION_NUMBER, "0.2.5.1")) {
			importedBreast.milk.type = importedBreast.getType().getFluidType();
		}
		
		// **************** Ear **************** //
		
		Element ear = (Element)parentElement.getElementsByTagName("ear").item(0);

		Ear importedEar = new Ear(EarType.getEarTypeFromId(ear.getAttribute("type")));
		
		importedEar.pierced = (Boolean.valueOf(ear.getAttribute("pierced")));
		CharacterUtils.appendToImportLog(log, "<br/><br/>Body: Ear:"
				+ "<br/>type: "+importedEar.getType()
				+ "<br/>pierced: "+importedEar.isPierced());

		
		
		// **************** Eye **************** //
		
		Element eye = (Element)parentElement.getElementsByTagName("eye").item(0);
		
		String eyeTypeFromSave = eye.getAttribute("type");
		
		Map<String, String> eyeTypeConverterMap = new HashMap<>();
		eyeTypeConverterMap.put("EYE_HUMAN", "HUMAN");
		eyeTypeConverterMap.put("EYE_ANGEL", "ANGEL");
		eyeTypeConverterMap.put("EYE_DEMON_COMMON", "DEMON_COMMON");
		eyeTypeConverterMap.put("EYE_DOG_MORPH", "DOG_MORPH");
		eyeTypeConverterMap.put("EYE_LYCAN", "LYCAN");
		eyeTypeConverterMap.put("EYE_FELINE", "CAT_MORPH");
		eyeTypeConverterMap.put("EYE_SQUIRREL", "SQUIRREL_MORPH");
		eyeTypeConverterMap.put("EYE_HORSE_MORPH", "HORSE_MORPH");
		eyeTypeConverterMap.put("EYE_HARPY", "HARPY");
		eyeTypeConverterMap.put("EYE_SLIME", "SLIME");
		if(eyeTypeConverterMap.containsKey(eyeTypeFromSave)) {
			eyeTypeFromSave = eyeTypeConverterMap.get(eyeTypeFromSave);
		}

		Eye importedEye = new Eye(EyeType.getTypeFromString(eyeTypeFromSave));
		
		importedEye.eyePairs = (Integer.valueOf(eye.getAttribute("eyePairs")));
		importedEye.irisShape = (EyeShape.valueOf(eye.getAttribute("irisShape")));
		importedEye.pupilShape = (EyeShape.valueOf(eye.getAttribute("pupilShape")));
		
		CharacterUtils.appendToImportLog(log, "<br/><br/>Body: Eye:"
				+ "<br/>type: "+importedEye.getType()
				+ "<br/>pairs: "+importedEye.getEyePairs()
				+ "<br/>iris shape: "+importedEye.getIrisShape()
				+ "<br/>pupil shape: "+importedEye.getPupilShape());
		
		
		// **************** Face **************** //
	
		Element face = (Element)parentElement.getElementsByTagName("face").item(0);
		Element mouth = (Element)parentElement.getElementsByTagName("mouth").item(0);
		
		Face importedFace = new Face(FaceType.getTypeFromString(face.getAttribute("type")), Integer.valueOf(mouth.getAttribute("lipSize")));
		
		importedFace.piercedNose = (Boolean.valueOf(face.getAttribute("piercedNose")));
		try {
			importedFace.facialHair = (BodyHair.valueOf(face.getAttribute("facialHair")));
		} catch(IllegalArgumentException e) {
			importedFace.facialHair = BodyHair.ZERO_NONE;
			CharacterUtils.appendToImportLog(log, "<br/>facial hair: OLD_VALUE - Set to NONE");
		}
		
		CharacterUtils.appendToImportLog(log, "<br/><br/>Body: Face: "
				+ "<br/>type: "+importedFace.getType()
				+ "<br/>piercedNose: "+importedFace.isPiercedNose()
				+ "<br/>facial hair: "+importedFace.getFacialHair()
				
				+ "<br/><br/>Mouth: ");
		
		importedFace.mouth.orificeMouth.elasticity = (Integer.valueOf(mouth.getAttribute("elasticity")));
		importedFace.mouth.orificeMouth.plasticity = (Integer.valueOf(mouth.getAttribute("plasticity")));
		importedFace.mouth.orificeMouth.capacity = (Float.valueOf(mouth.getAttribute("capacity")));
		try {
			importedFace.mouth.orificeMouth.wetness = (Integer.valueOf(mouth.getAttribute("wetness")));
		} catch(Exception ex) {
		}
		importedFace.mouth.orificeMouth.stretchedCapacity = (Float.valueOf(mouth.getAttribute("stretchedCapacity")));
		importedFace.mouth.orificeMouth.virgin = (Boolean.valueOf(mouth.getAttribute("virgin")));
		importedFace.mouth.piercedLip = (Boolean.valueOf(mouth.getAttribute("piercedLip")));
		
		CharacterUtils.appendToImportLog(log, 
				"<br/>elasticity: "+importedFace.mouth.orificeMouth.getElasticity()
				+ "<br/>plasticity: "+importedFace.mouth.orificeMouth.getPlasticity()
				+ "<br/>capacity: "+importedFace.mouth.orificeMouth.getCapacity()
				+ "<br/>stretchedCapacity: "+importedFace.mouth.orificeMouth.getStretchedCapacity()
				+ "<br/>virgin: "+importedFace.mouth.orificeMouth.isVirgin()
				+ "<br/>piercedLip: "+importedFace.mouth.isPiercedLip()
				+ "<br/>lipSize: "+importedFace.mouth.getLipSize()
				+ "<br/>Modifiers: ");
			
		Element mouthModifiersElement = (Element)mouth.getElementsByTagName("mouthModifiers").item(0);
		Collection<OrificeModifier> mouthOrificeModifiers = importedFace.mouth.orificeMouth.orificeModifiers;
		mouthOrificeModifiers.clear();
		if(mouthModifiersElement!=null) {
			handleLoadingOfModifiers(OrificeModifier.values(), log, mouthModifiersElement, mouthOrificeModifiers);
		}

		Element tongue = (Element)parentElement.getElementsByTagName("tongue").item(0);
			importedFace.tongue.pierced = (Boolean.valueOf(tongue.getAttribute("piercedTongue")));
			importedFace.tongue.tongueLength = (Integer.valueOf(tongue.getAttribute("tongueLength")));
			
			CharacterUtils.appendToImportLog(log, 
					"<br/><br/>Tongue: "
					+ "<br/>piercedTongue: "+importedFace.tongue.isPierced()
					+ "<br/>tongueLength: "+importedFace.tongue.getTongueLength()
					+ "<br/>Modifiers: ");
			
			Element tongueModifiersElement = (Element)tongue.getElementsByTagName("tongueModifiers").item(0);
			Collection<TongueModifier> tongueModifiers = importedFace.tongue.tongueModifiers;
			tongueModifiers.clear();
			if(tongueModifiersElement!=null) {
				handleLoadingOfModifiers(TongueModifier.values(), log, tongueModifiersElement, tongueModifiers);
			}
			
			
		// **************** Hair **************** //
		
		Element hair = (Element)parentElement.getElementsByTagName("hair").item(0);
		String hairTypeFromSave = hair.getAttribute("type");
		
		Map<String, String> hairTypeConverterMap = new HashMap<>();
		hairTypeConverterMap.put("HAIR_HUMAN", "HUMAN");
		hairTypeConverterMap.put("HAIR_ANGEL", "ANGEL");
		hairTypeConverterMap.put("HAIR_DEMON", "DEMON_COMMON");
		hairTypeConverterMap.put("HAIR_CANINE_FUR", "DOG_MORPH");
		hairTypeConverterMap.put("HAIR_LYCAN_FUR", "LYCAN");
		hairTypeConverterMap.put("HAIR_FELINE_FUR", "CAT_MORPH");
		hairTypeConverterMap.put("HAIR_HORSE_HAIR", "HORSE_MORPH");
		hairTypeConverterMap.put("HAIR_SQUIRREL_FUR", "SQUIRREL_MORPH");
		hairTypeConverterMap.put("SLIME", "SLIME");
		hairTypeConverterMap.put("HAIR_HARPY", "HARPY");
		if(hairTypeConverterMap.containsKey(hairTypeFromSave)) {
			hairTypeFromSave = hairTypeConverterMap.get(hairTypeFromSave);
		}
		
		Hair importedHair = new Hair(HairType.getTypeFromString(hairTypeFromSave), Integer.valueOf(hair.getAttribute("length")), HairStyle.valueOf(hair.getAttribute("hairStyle")));
		
		CharacterUtils.appendToImportLog(log, "<br/><br/>Body: Hair: "
				+ "<br/>type: "+importedHair.getType()
				+ "<br/>length: "+importedHair.getLength()
				+ "<br/>hairStyle: "+importedHair.getStyle());

		
		// **************** Horn **************** //
		Element horn = (Element)parentElement.getElementsByTagName("horn").item(0);
		
		Horn importedHorn = new Horn(HornType.NONE, 0);
		int rows = (Integer.valueOf(horn.getAttribute("rows")));
		
		String hornType = horn.getAttribute("type");
		if(hornType.equals("DEMON")) {
			hornType = "";
		}
		if(hornType.equals("BOVINE")) {
			hornType = "";
		}
		int length = 0;
		if(!hornType.equals("NONE")) {
			length = HornLength.TWO_LONG.getMedianValue();
		}
		if(!horn.getAttribute("length").isEmpty()) {
			try {
				length = Integer.valueOf(horn.getAttribute("length"));
			} catch(IllegalArgumentException e) {
			}
		}
		int hornsPerRow = 2;
		if(!horn.getAttribute("hornsPerRow").isEmpty()) {
			try {
				hornsPerRow = Integer.valueOf(horn.getAttribute("hornsPerRow"));
			} catch(IllegalArgumentException e) {
			}
		}
		try {
			importedHorn = new Horn(HornType.getHornTypeFromId(hornType), length);
			importedHorn.rows = rows;
			importedHorn.hornsPerRow = hornsPerRow;
			CharacterUtils.appendToImportLog(log, "<br/><br/>Body: Horn: "
					+ "<br/>type: "+importedHorn.getType()
					+ "<br/>length: "+length
					+ "<br/>rows: "+importedHorn.getHornRows()
					+ "<br/>horns per row: "+importedHorn.getHornsPerRow());
			
		} catch(IllegalArgumentException e) {
			if(horn.getAttribute("type").startsWith("DEMON")) {
				importedHorn = new Horn(HornType.SWEPT_BACK, length);
				importedHorn.rows = rows;
				
			} else if(horn.getAttribute("type").startsWith("BOVINE")) {
				importedHorn = new Horn(HornType.CURVED, length);
				importedHorn.rows = rows;
			}
			
			CharacterUtils.appendToImportLog(log, "<br/><br/>Body: Horn: "
					+ "<br/>type NOT FOUND, defaulted to: "+importedHorn.getType()
					+ "<br/>rows: "+importedHorn.getHornRows());
		}
		
		
			
		// **************** Leg **************** //
		
		Element leg = (Element)parentElement.getElementsByTagName("leg").item(0);

		AbstractLegType legType = LegType.getLegTypeFromId(leg.getAttribute("type"));
		
		LegConfiguration configuration = LegConfiguration.BIPEDAL;
		try {
			configuration = LegConfiguration.valueOf(leg.getAttribute("configuration"));
		} catch(Exception ex) {}
		
		FootStructure footStructure = legType.getDefaultFootStructure();
		try {
			footStructure = FootStructure.valueOf(leg.getAttribute("footStructure"));
		} catch(Exception ex) {}
		
		Leg importedLeg = new Leg(legType, configuration);
		importedLeg.setFootStructure(null, footStructure);
		
		CharacterUtils.appendToImportLog(log, "<br/><br/>Body: Leg: "
				+ "<br/>type: "+importedLeg.getType());

		
		// **************** Penis **************** //
		
		Element penis = (Element)parentElement.getElementsByTagName("penis").item(0);
		Element testicles = (Element)parentElement.getElementsByTagName("testicles").item(0);
		
		int girth = 2;
		if(penis.getAttribute("girth") != null && !penis.getAttribute("girth").isEmpty()) {
			girth = Integer.valueOf(penis.getAttribute("girth"));
		}
		
		int cumStorage = 0;
		try {
			if(testicles.hasAttribute("cumProduction")) {
				cumStorage = Integer.valueOf(testicles.getAttribute("cumProduction"));
			} else {
				cumStorage = Integer.valueOf(testicles.getAttribute("cumStorage"));
			}
		} catch(Exception ex) {
		}
		
		Penis importedPenis = new Penis(PenisType.getTypeFromString(penis.getAttribute("type")),
				Integer.valueOf(penis.getAttribute("size")),
				false,
				girth,
				Integer.valueOf(testicles.getAttribute("testicleSize")),
				cumStorage,
				Integer.valueOf(testicles.getAttribute("numberOfTesticles")));
		
		importedPenis.pierced = (Boolean.valueOf(penis.getAttribute("pierced")));
		
		if(!penis.getAttribute("virgin").isEmpty()) {
			importedPenis.virgin = (Boolean.valueOf(penis.getAttribute("virgin")));
		}
		
		CharacterUtils.appendToImportLog(log, "<br/><br/>Body: Penis: "
				+ "<br/>type: "+importedPenis.getType()
				+ "<br/>size: "+importedPenis.getRawSizeValue()
				+ "<br/>pierced: "+importedPenis.isPierced()
				+ "<br/>Penis Modifiers: ");
		
		Collection<PenetrationModifier> penisModifiers = importedPenis.penisModifiers;
		penisModifiers.clear();
		Element penisModifiersElement = (Element)penis.getElementsByTagName("penisModifiers").item(0);
		if (penisModifiersElement != null) {
			handleLoadingOfModifiers(PenetrationModifier.values(), log, penisModifiersElement, penisModifiers);
		}
		
		importedPenis.orificeUrethra.elasticity = (Integer.valueOf(penis.getAttribute("elasticity")));
		importedPenis.orificeUrethra.plasticity = (Integer.valueOf(penis.getAttribute("plasticity")));
		importedPenis.orificeUrethra.capacity = (Float.valueOf(penis.getAttribute("capacity")));
		importedPenis.orificeUrethra.stretchedCapacity = (Float.valueOf(penis.getAttribute("stretchedCapacity")));
		if(!penis.getAttribute("urethraVirgin").isEmpty()) {
			importedPenis.orificeUrethra.virgin = (Boolean.valueOf(penis.getAttribute("urethraVirgin")));
		} else {
			importedPenis.orificeUrethra.virgin = true;
		}
		
		CharacterUtils.appendToImportLog(log, 
				"<br/>elasticity: "+importedPenis.orificeUrethra.getElasticity()
				+ "<br/>plasticity: "+importedPenis.orificeUrethra.getPlasticity()
				+ "<br/>capacity: "+importedPenis.orificeUrethra.getCapacity()
				+ "<br/>stretchedCapacity: "+importedPenis.orificeUrethra.getStretchedCapacity()
				+ "<br/>virgin: "+importedPenis.orificeUrethra.isVirgin()
				+ "<br/>Urethra Modifiers:");
		
		Element urethraModifiersElement = (Element)penis.getElementsByTagName("urethraModifiers").item(0);
		Collection<OrificeModifier> urethraOrificeModifiers = importedPenis.orificeUrethra.orificeModifiers;
		urethraOrificeModifiers.clear();
		if (urethraModifiersElement != null) {
			handleLoadingOfModifiers(OrificeModifier.values(), log, urethraModifiersElement, urethraOrificeModifiers);
		}
		
		importedPenis.testicle.internal = (Boolean.valueOf(testicles.getAttribute("internal")));
		
		try {
			importedPenis.testicle.cumStored = Float.valueOf(testicles.getAttribute("storedCum"));
			importedPenis.testicle.cumRegeneration = Integer.valueOf(testicles.getAttribute("cumRegeneration"));
			importedPenis.testicle.setCumExpulsion(null, Integer.valueOf(testicles.getAttribute("cumExpulsion")));
			if(Main.isVersionOlderThan(Game.loadingVersion, "0.3.1.9")) { // Change from percentage-based to set value:
				importedPenis.testicle.cumRegeneration = FluidRegeneration.CUM_REGEN_DEFAULT;
			}
		} catch(Exception ex) {
		}
		
		CharacterUtils.appendToImportLog(log, "<br/><br/>Testicles: "
				+ "<br/>cumProduction: "+importedPenis.testicle.getCumStorage()
				+ "<br/>numberOfTesticles: "+importedPenis.testicle.getTesticleCount()
				+ "<br/>testicleSize: "+importedPenis.testicle.getTesticleSize()
				+ "<br/>internal: "+importedPenis.testicle.internal);
		
		
		CharacterUtils.appendToImportLog(log, "<br/><br/>Cum:");
		
		importedPenis.testicle.cum = FluidCum.loadFromXML(parentElement, doc, importedPenis.getType().getTesticleType().getFluidType());

		
		// **************** Skin **************** //
		
		Element skin = (Element)parentElement.getElementsByTagName("skin").item(0);
		String skinTypeFromSave = skin.getAttribute("type");
		
		Map<String, String> skinTypeConverterMap = new HashMap<>();
		skinTypeConverterMap.put("HUMAN", "HUMAN");
		skinTypeConverterMap.put("ANGEL", "ANGEL");
		skinTypeConverterMap.put("DEMON_COMMON", "DEMON_COMMON");
		skinTypeConverterMap.put("CANINE_FUR", "DOG_MORPH");
		skinTypeConverterMap.put("LYCAN_FUR", "LYCAN");
		skinTypeConverterMap.put("FELINE_FUR", "CAT_MORPH");
		skinTypeConverterMap.put("SQUIRREL_FUR", "SQUIRREL_MORPH");
		skinTypeConverterMap.put("HORSE_HAIR", "HORSE_MORPH");
		skinTypeConverterMap.put("SLIME", "SLIME");
		skinTypeConverterMap.put("FEATHERS", "HARPY");
		if(skinTypeConverterMap.containsKey(skinTypeFromSave)) {
			skinTypeFromSave = skinTypeConverterMap.get(skinTypeFromSave);
		}
		
		Skin importedSkin = new Skin(SkinType.getTypeFromString(skinTypeFromSave));
		
		CharacterUtils.appendToImportLog(log, "<br/><br/>Body: Skin: "
				+ "<br/>type: "+importedSkin.getType());

		
		// **************** Tail **************** //
		
		Element tail = (Element)parentElement.getElementsByTagName("tail").item(0);
		
		Tail importedTail = new Tail(TailType.getTypeFromString(tail.getAttribute("type")));
		
		importedTail.tailCount = (Integer.valueOf(tail.getAttribute("count")));
		
		CharacterUtils.appendToImportLog(log, "<br/><br/>Body: Tail: "
				+ "<br/>type: "+importedTail.getType()
				+ "<br/>count: "+importedTail.getTailCount());

		
		// **************** Tentacle **************** //
		
		try {
			Element tentacle = (Element)parentElement.getElementsByTagName("tentacle").item(0);
			
			Tentacle importedTentacle = new Tentacle(TentacleType.getTypeFromString(tentacle.getAttribute("type")));
			
			importedTentacle.tentacleCount = (Integer.valueOf(tentacle.getAttribute("count")));
			
			CharacterUtils.appendToImportLog(log, "<br/><br/>Body: Tentacle: "
					+ "<br/>type: "+importedTentacle.getType()
					+ "<br/>count: "+importedTentacle.getTentacleCount());
		} catch(Exception ex) {
		}
		
		// **************** Vagina **************** //
		
		Element vagina = (Element)parentElement.getElementsByTagName("vagina").item(0);
		
		Vagina importedVagina = new Vagina(VaginaType.getTypeFromString(vagina.getAttribute("type")),
				(vagina.getAttribute("labiaSize").isEmpty()?1:Integer.valueOf(vagina.getAttribute("labiaSize"))),
				Integer.valueOf(vagina.getAttribute("clitSize")),
				Integer.valueOf(vagina.getAttribute("wetness")),
				Float.valueOf(vagina.getAttribute("capacity")),
				Integer.valueOf(vagina.getAttribute("elasticity")),
				Integer.valueOf(vagina.getAttribute("plasticity")),
				Boolean.valueOf(vagina.getAttribute("virgin")));
		
		try {
			importedVagina.clitoris.girth = Integer.valueOf(vagina.getAttribute("clitGirth"));
			
			Collection<PenetrationModifier> clitModifiers = importedVagina.clitoris.clitModifiers;
			clitModifiers.clear();
			Element clitModifiersElement = (Element)vagina.getElementsByTagName("clitModifiers").item(0);
			if (clitModifiersElement != null) {
				handleLoadingOfModifiers(PenetrationModifier.values(), log, clitModifiersElement, clitModifiers);
			}
			
		} catch(Exception ex) {
		}
		
		importedVagina.pierced = (Boolean.valueOf(vagina.getAttribute("pierced")));
		importedVagina.orificeVagina.stretchedCapacity = (Float.valueOf(vagina.getAttribute("stretchedCapacity")));
		try {
			importedVagina.orificeVagina.squirter = (Boolean.valueOf(vagina.getAttribute("squirter")));
		} catch(Exception ex) {
		}
		
		CharacterUtils.appendToImportLog(log, "<br/><br/>Body: Vagina: "
				+ "<br/>type: "+importedVagina.getType()
				+ "<br/>clitSize: "+importedVagina.clitoris.getClitorisSize()
				+ "<br/>pierced: "+importedVagina.isPierced()

				+ "<br/>wetness: "+importedVagina.orificeVagina.wetness
				+ "<br/>elasticity: "+importedVagina.orificeVagina.getElasticity()
				+ "<br/>plasticity: "+importedVagina.orificeVagina.getPlasticity()
				+ "<br/>capacity: "+importedVagina.orificeVagina.getCapacity()
				+ "<br/>stretchedCapacity: "+importedVagina.orificeVagina.getStretchedCapacity()
				+ "<br/>virgin: "+importedVagina.orificeVagina.isVirgin());
		
		Element vaginaModifiers = (Element)vagina.getElementsByTagName("vaginaModifiers").item(0);
		Collection<OrificeModifier> vaginaOrificeModifiers = importedVagina.orificeVagina.orificeModifiers;
		vaginaOrificeModifiers.clear();
		if(vaginaModifiers!=null) {
			handleLoadingOfModifiers(OrificeModifier.values(), log, vaginaModifiers, vaginaOrificeModifiers);
		}
		
		try {
			importedVagina.orificeUrethra.elasticity = (Integer.valueOf(vagina.getAttribute("urethraElasticity")));
			importedVagina.orificeUrethra.plasticity = (Integer.valueOf(vagina.getAttribute("urethraPlasticity")));
			importedVagina.orificeUrethra.capacity = (Float.valueOf(vagina.getAttribute("urethraCapacity")));
			importedVagina.orificeUrethra.stretchedCapacity = (Float.valueOf(vagina.getAttribute("urethraStretchedCapacity")));
			if(!vagina.getAttribute("urethraVirgin").isEmpty()) {
				importedVagina.orificeUrethra.virgin = (Boolean.valueOf(vagina.getAttribute("urethraVirgin")));
			} else {
				importedVagina.orificeUrethra.virgin = true;
			}
			
			urethraModifiersElement = (Element)vagina.getElementsByTagName("urethraModifiers").item(0);
			Collection<OrificeModifier> vaginaUrethraOrificeModifiers = importedVagina.orificeUrethra.orificeModifiers;
			vaginaUrethraOrificeModifiers.clear();
			if (urethraModifiersElement != null) {
				handleLoadingOfModifiers(OrificeModifier.values(), log, urethraModifiersElement, vaginaUrethraOrificeModifiers);
			}
		} catch(Exception ex) {
		}
		
		CharacterUtils.appendToImportLog(log, "<br/><br/>Girlcum:");
		
		importedVagina.girlcum = FluidGirlCum.loadFromXML(parentElement, doc, importedVagina.getType().getFluidType());
		
		// **************** Wing **************** //
		
		Element wing = (Element)parentElement.getElementsByTagName("wing").item(0);
		int wingSize = 0;
		if(!wing.getAttribute("size").isEmpty()) {
			wingSize = Integer.valueOf(wing.getAttribute("size"));
		}
		Wing importedWing = new Wing(WingType.getTypeFromString(wing.getAttribute("type")), wingSize);
		CharacterUtils.appendToImportLog(log, "<br/><br/>Body: Wing: "
				+ "<br/>type: "+importedWing.getType()+"<br/>"
				+ "<br/>size: "+importedWing.getSizeValue()+"<br/>");


		// ************** Version Overrides **************//

		if(Main.isVersionOlderThan(Game.loadingVersion, "0.2.5.1")) {
			importedVagina.girlcum.type = importedVagina.getType().getFluidType();
			importedPenis.testicle.cum.type = importedPenis.getType().getTesticleType().getFluidType();
			importedBreast.milk.type = importedBreast.getType().getFluidType();
		}


		if(Main.isVersionOlderThan(Game.loadingVersion, "0.3.0.8")) {
			// Convert all sizes from inch to cm
			importedHair.length *= 2.54;
			importedHorn.length *= 2.54;
			importedFace.tongue.tongueLength *= 2.54;
			importedPenis.size *= 2.54;
			importedVagina.clitoris.clitSize *= 2.54;

			// Convert all capacities from inch to cm
			importedFace.mouth.orificeMouth.capacity *= 2.54;
			importedFace.mouth.orificeMouth.stretchedCapacity *= 2.54;
			importedPenis.orificeUrethra.capacity *= 2.54;
			importedPenis.orificeUrethra.stretchedCapacity *= 2.54;
			importedVagina.orificeVagina.capacity *= 2.54;
			importedVagina.orificeVagina.stretchedCapacity *= 2.54;
			importedVagina.orificeUrethra.capacity *= 2.54;
			importedVagina.orificeUrethra.stretchedCapacity *= 2.54;
			importedAss.anus.orificeAnus.capacity *= 2.54;
			importedAss.anus.orificeAnus.stretchedCapacity *= 2.54;
			importedBreast.nipples.orificeNipples.capacity *= 2.54;
			importedBreast.nipples.orificeNipples.stretchedCapacity *= 2.54;
		}
		
		
		Body body = new Body.BodyBuilder(
				importedArm,
				importedAss,
				importedBreast,
				importedFace,
				importedEye,
				importedEar,
				importedHair,
				importedLeg,
				importedSkin,
				importedBodyMaterial,
				importedGenitalArrangement,
				importedHeight,
				importedFemininity,
				importedBodySize,
				importedMuscle)
						.vagina(importedVagina)
						.penis(importedPenis)
						.horn(importedHorn)
						.antenna(importedAntenna)
						.tail(importedTail)
						.wing(importedWing)
						.build();


		// **************** Crotch Breasts **************** //

		breasts = (Element)parentElement.getElementsByTagName("breastsCrotch").item(0);
		nipples = (Element)parentElement.getElementsByTagName("nipplesCrotch").item(0);
		BreastCrotch importedCrotchBreast = null;

		if(breasts!=null) {
			breastShape = BreastShape.ROUND;
			try {
				breastShape = BreastShape.valueOf(breasts.getAttribute("shape"));
			} catch(Exception e) {
			}

			milkStorage = 0;
			try {
				if(!breasts.getAttribute("lactation").isEmpty()) {
					milkStorage = Integer.valueOf(breasts.getAttribute("lactation"));
				} else {
					milkStorage = Integer.valueOf(breasts.getAttribute("milkStorage"));
				}
			} catch(Exception ex) {
			}

			AbstractBreastType crotchBoobType = BreastType.getBreastTypeFromId(breasts.getAttribute("type"));
			if(Main.isVersionOlderThan(Game.loadingVersion, "0.3.0.6") && importedLeg.getLegConfiguration().isBipedalPositionedCrotchBoobs()) { // Reset crotch-boob type as I accidentally applied crotch-boobs to demons
				if(body.isFeminine()) {
					crotchBoobType = RacialBody.valueOfRace(body.getRace()).getBreastCrotchType();
				} else {
					crotchBoobType = BreastType.NONE;
				}
			}
			importedCrotchBreast = new BreastCrotch(crotchBoobType,
					breastShape,
					Integer.valueOf(breasts.getAttribute("size")),
					milkStorage,
					Integer.valueOf(breasts.getAttribute("rows")),
					Integer.valueOf(nipples.getAttribute("nippleSize")),
					NippleShape.valueOf(nipples.getAttribute("nippleShape")),
					Integer.valueOf(nipples.getAttribute("areolaeSize")),
					Integer.valueOf(breasts.getAttribute("nippleCountPerBreast")),
					Float.valueOf(nipples.getAttribute("capacity")),
					Integer.valueOf(nipples.getAttribute("elasticity")),
					Integer.valueOf(nipples.getAttribute("plasticity")),
					Boolean.valueOf(nipples.getAttribute("virgin")));

			try {
				importedCrotchBreast.milkStored = Float.valueOf(breasts.getAttribute("storedMilk"));
				importedCrotchBreast.milkRegeneration = Integer.valueOf(breasts.getAttribute("milkRegeneration"));
				if(Main.isVersionOlderThan(Game.loadingVersion, "0.3.2")) { // Change from percentage-based to set value:
					importedCrotchBreast.milkRegeneration = FluidRegeneration.ONE_AVERAGE.getMedianRegenerationValuePerDay();
				}
			} catch(Exception ex) {
			}

			importedCrotchBreast.nipples.crotchNipples = true;
			importedCrotchBreast.nipples.orificeNipples.stretchedCapacity = (Float.valueOf(nipples.getAttribute("stretchedCapacity")));
			importedCrotchBreast.nipples.pierced = (Boolean.valueOf(nipples.getAttribute("pierced")));
			importedCrotchBreast.nipples.areolaeShape = (AreolaeShape.valueOf(nipples.getAttribute("areolaeShape")));

			CharacterUtils.appendToImportLog(log, "<br/><br/>Body: Crotch Breasts:"
					+ "<br/>type: "+importedCrotchBreast.getType()
					+ "<br/>size: "+importedCrotchBreast.getSize()
					+ "<br/>rows: "+importedCrotchBreast.getRows()
					+ "<br/>lactation: "+importedCrotchBreast.getRawMilkStorageValue()
					+ "<br/>nippleCountPer: "+importedCrotchBreast.getNippleCountPerBreast()

					+ "<br/><br/>Nipples:"
					+ "<br/>elasticity: "+importedCrotchBreast.nipples.orificeNipples.getElasticity()
					+ "<br/>plasticity: "+importedCrotchBreast.nipples.orificeNipples.getPlasticity()
					+ "<br/>capacity: "+importedCrotchBreast.nipples.orificeNipples.getRawCapacityValue()
					+ "<br/>stretchedCapacity: "+importedCrotchBreast.nipples.orificeNipples.getStretchedCapacity()
					+ "<br/>virgin: "+importedCrotchBreast.nipples.orificeNipples.isVirgin()
					+ "<br/>pierced: "+importedCrotchBreast.nipples.isPierced()
					+ "<br/>nippleSize: "+importedCrotchBreast.nipples.getNippleSize()
					+ "<br/>nippleShape: "+importedCrotchBreast.nipples.getNippleShape()
					+ "<br/>areolaeSize: "+importedCrotchBreast.nipples.getAreolaeSize()
					+ "<br/>areolaeShape: "+importedCrotchBreast.nipples.getAreolaeShape()
					+"<br/>Modifiers:");

			nippleModifiersElement = (Element)nipples.getElementsByTagName("nippleModifiers").item(0);
			nippleOrificeModifiers = importedCrotchBreast.nipples.orificeNipples.orificeModifiers;
			nippleOrificeModifiers.clear();
			if (nippleModifiersElement != null) {
				handleLoadingOfModifiers(OrificeModifier.values(), log, nippleModifiersElement, nippleOrificeModifiers);
			}

			CharacterUtils.appendToImportLog(log, "<br/><br/>Milk:");

			importedCrotchBreast.milk = FluidMilk.loadFromXML(parentElement, doc, importedCrotchBreast.getType().getFluidType(), true);
			if(Main.isVersionOlderThan(Main.VERSION_NUMBER, "0.2.5.1")) {
				importedCrotchBreast.milk.type = importedCrotchBreast.getType().getFluidType();
			}
		}


		if(importedCrotchBreast!=null) {
			if(Main.isVersionOlderThan(Game.loadingVersion, "0.3.0.8")) {
				importedCrotchBreast.nipples.orificeNipples.capacity *= 2.54;
				importedCrotchBreast.nipples.orificeNipples.stretchedCapacity *= 2.54;
			}

			body.setBreastCrotch(importedCrotchBreast);
		}
		
		body.setSubspeciesOverride(importedSubspeciesOverride);
		
		body.setPiercedStomach(Boolean.valueOf(element.getAttribute("piercedStomach")));
		CharacterUtils.appendToImportLog(log, "<br/>Body: Set piercedStomach: "+Boolean.valueOf(element.getAttribute("piercedStomach")));
		
		try {
			if(element.getAttribute("takesAfterMother") != null && !element.getAttribute("takesAfterMother").isEmpty()) {
				body.setTakesAfterMother(Boolean.valueOf(element.getAttribute("takesAfterMother")));
			}
		} catch(Exception ex) {	
		}
		
		if(element.getAttribute("pubicHair")!=null && !element.getAttribute("pubicHair").isEmpty()) {
			try {
				body.setPubicHair(BodyHair.valueOf(element.getAttribute("pubicHair")));
				CharacterUtils.appendToImportLog(log, "<br/>Body: Set pubicHair: "+body.getPubicHair());
			} catch(IllegalArgumentException e) {
				body.pubicHair = BodyHair.ZERO_NONE;
				CharacterUtils.appendToImportLog(log, "<br/>pubic hair: OLD_VALUE - Set to NONE");
			}
		}
		
		
		NodeList bodyCoverings = element.getElementsByTagName("bodyCovering");
		for(int i = 0; i < bodyCoverings.getLength(); i++){
			Element e = ((Element)bodyCoverings.item(i));

			String type = e.getAttribute("type");
			if(type.equals("HORN_COW") || type.equals("HORN_DEMON")) {
				type = "HORN";
			}
			try {
				
				String colourPrimary = e.getAttribute("colourPrimary");
				if(colourPrimary.isEmpty()) {
					colourPrimary = e.getAttribute("c1");
				}
				String colourSecondary = e.getAttribute("colourSecondary");
				if(colourSecondary.isEmpty()) {
					colourSecondary = e.getAttribute("c2");
				}
				
				if(type.startsWith("HAIR_")) {
					if(colourPrimary.equals("COVERING_TAN")) {
						colourPrimary = "COVERING_DIRTY_BLONDE";
					}
					if(colourSecondary.equals("COVERING_TAN")) {
						colourSecondary = "COVERING_DIRTY_BLONDE";
					}
				}
				
				if(e.getAttribute("modifier").isEmpty()) {
					body.setBodyCoveringForXMLImport(BodyCoveringType.getTypeFromString(type),
							CoveringPattern.valueOf(e.getAttribute("pattern")),
							Colour.valueOf(colourPrimary),
							!e.getAttribute("g1").isEmpty()?Boolean.valueOf(e.getAttribute("g1")):false,
							Colour.valueOf(colourSecondary),
							!e.getAttribute("g2").isEmpty()?Boolean.valueOf(e.getAttribute("g2")):false);
					
				} else { //TODO
					BodyCoveringType coveringType = BodyCoveringType.getTypeFromString(type);
					CoveringModifier modifier = CoveringModifier.valueOf(e.getAttribute("modifier"));
					
					body.setBodyCoveringForXMLImport(coveringType,
							CoveringPattern.valueOf(e.getAttribute("pattern")),
							coveringType.getNaturalModifiers().contains(modifier) || coveringType.getExtraModifiers().contains(modifier) ? modifier : coveringType.getNaturalModifiers().get(0),
							Colour.valueOf(colourPrimary),
							!e.getAttribute("g1").isEmpty()?Boolean.valueOf(e.getAttribute("g1")):false,
							Colour.valueOf(colourSecondary),
							!e.getAttribute("g2").isEmpty()?Boolean.valueOf(e.getAttribute("g2")):false);
				}
				
				if(!e.getAttribute("discovered").isEmpty() && Boolean.valueOf(e.getAttribute("discovered"))) {
					body.getBodyCoveringTypesDiscovered().add(BodyCoveringType.getTypeFromString(type));
				}
				
				CharacterUtils.appendToImportLog(log, "<br/>Body: Set bodyCovering: "+e.getAttribute("type") +" pattern:"+CoveringPattern.valueOf(e.getAttribute("pattern"))
					+" "+Colour.valueOf(e.getAttribute("colourPrimary")) +" glow:"+Boolean.valueOf(e.getAttribute("glowPrimary"))
					+" | "+Colour.valueOf(e.getAttribute("colourSecondary")) +" glow:"+Boolean.valueOf(e.getAttribute("glowSecondary"))
					+" (discovered: "+e.getAttribute("discovered")+")");
			} catch(Exception ex) {
			}
		}
		
		body.calculateRace(null);
		
		if(Main.isVersionOlderThan(Game.loadingVersion, "0.3.0.5")) {
			body.updateNippleCrotchColouring();
		}
		
		return body;
	}

	static <T extends Enum<T>> void handleLoadingOfModifiers(T[] enumValues, StringBuilder log, Element modifiersElement, Collection<T> modifiers) {
		for(T enumValue : enumValues) {
			String attributeValue = modifiersElement.getAttribute(enumValue.toString());
			if(!attributeValue.isEmpty()) {
				if(Boolean.valueOf(attributeValue)) {
					if (!modifiers.contains(enumValue)) {
						modifiers.add(enumValue);
					}
					if(log!=null) {
						CharacterUtils.appendToImportLog(log, "<br/>"+enumValue.toString()+":true");
					}
				} else if (!attributeValue.isEmpty()) {
					modifiers.remove(enumValue);
					if(log!=null) {
						CharacterUtils.appendToImportLog(log, "<br/>"+enumValue.toString()+":false");
					}
				} else {
					if(log!=null) {
						CharacterUtils.appendToImportLog(log, "<br/>"+enumValue.toString()+":not present, defaulted to "+modifiers.contains(enumValue));
					}
				}
			}
		}
	}
	
	public List<BodyPartInterface> getAllBodyParts() {
		return allBodyParts;
	}
	
	/**
	 * @param owner
	 * @param playerKnowledgeOfThroat
	 * @param playerKnowledgeOfBreasts
	 * @param playerKnowledgeOfGroin
	 * @return
	 */
	public String getDescription(GameCharacter owner) {
		StringBuilder sb = new StringBuilder();
		// Describe race:
		if (owner.isPlayer()) {
			sb.append("<p>"
						+ "You are [pc.name], "
							+(owner.getRace()==Race.HUMAN
								?"<span style='color:"+owner.getFemininity().getColour().toWebHexString()+";'>[pc.a_femininity]</span> [pc.gender(true)] [style.colourHuman(human)]. "
								:"[pc.a_fullRace(true)] [pc.gender(true)]. ")
						+ owner.getAppearsAsGenderDescription(true)
						+" Standing at full height, you measure [pc.heightValue].");
		} else {
			if(owner.isAreaKnownByCharacter(CoverableArea.PENIS, Main.game.getPlayer()) && owner.isAreaKnownByCharacter(CoverableArea.VAGINA, Main.game.getPlayer())) {
				sb.append("<p>"
						+ "[npc.Name] is "
							+(owner.getRace()==Race.HUMAN
								?"<span style='color:"+owner.getFemininity().getColour().toWebHexString()+";'>[npc.a_femininity]</span> [npc.gender(true)] [style.colourHuman(human)]. "
								:"[npc.a_fullRace(true)] [npc.gender(true)]. ")
						+ owner.getAppearsAsGenderDescription(true)
						+ " Standing at full height, [npc.she] measures [npc.heightValue]");
			} else {
				if(Main.game.getPlayer().hasTrait(Perk.OBSERVANT, true)) {
					sb.append("<p>"
							+ "Thanks to your observant perk, you can detect that [npc.name] is <span style='color:"+getGender().getColour().toWebHexString()+";'>[npc.a_gender]</span> [npc.raceStage] [npc.race]. "
							+ owner.getAppearsAsGenderDescription(true)
							+ " Standing at full height, [npc.she] measures [npc.heightValue]");
				} else {
					sb.append("<p>"
								+ "[npc.Name] is a [npc.a_fullRace(true)]. "
								+ owner.getAppearsAsGenderDescription(true)
								+ " Standing at full height, [npc.she] measures [npc.heightValue]");
				}
			}
			if(owner.isSizeDifferenceTallerThan(Main.game.getPlayer())) {
				sb.append(", making [npc.herHim] <span style='color:"+Colour.BODY_SIZE_FOUR.toWebHexString()+";'>significantly taller</span> than you.");
			} else if(owner.isSizeDifferenceShorterThan(Main.game.getPlayer())) {
				sb.append(", making [npc.herHim] <span style='color:"+Colour.BODY_SIZE_ZERO.toWebHexString()+";'>significantly shorter</span> than you.");
			} else {
				sb.append(".");
			}
		}
		
		switch(owner.getLegConfiguration()) {
			case ARACHNID:
			case CEPHALOPOD:
				sb.append(" [style.colourBestial([npc.Her] entire lower body, from the waist down, has transformed into that of a huge [npc.legRace]."
						+ " [npc.Her] legs and genitals are almost completely feral in nature, and are extremely similar to that of [npc.a_assRace]'s.)]");
				break;
			case BIPEDAL:
				break;
			case TAIL:
				sb.append(" [style.colourBestial([npc.Her] entire lower body, from the waist down, has transformed into the tail of [npc.a_legRace]."
						+ " [npc.Her] ass and genitals, while retaining anthropomorphic qualities, have shifted to be located within a front-facing cloaca.)]");
				break;
			case TAIL_LONG:
				sb.append(" [style.colourBestial([npc.Her] entire lower body, from the waist down, has transformed into the long tail of [npc.a_legRace]."
						+ " [npc.Her] ass and genitals are completely feral in nature, and, in a manner identical to that of [npc.a_assRace], have shifted to be located within a front-facing cloaca.)]");
				break;
			case TAUR:
				sb.append(" [style.colourBestial([npc.Her] entire lower body, from the waist down, has transformed into that of [npc.a_legRace]."
						+ " [npc.Her] legs, tail, ass, and genitals are completely feral in nature, and are identical to those of [npc.a_assRace]'s.)]");
				break;
		}
		
		if(Main.getProperties().hasValue(PropertyValue.ageContent)) {
			sb.append(" [npc.She] [npc.verb(appear)] to be in [npc.her] <span style='color:"+owner.getAppearsAsAge().getColour().toWebHexString()+";'>"+owner.getAppearsAsAge().getName()+"</span>.");
		}
		sb.append("</p>");
		
		switch(this.getBodyMaterial()) {
			case FLESH:
				break;
			case SLIME:
				if (owner.isPlayer()) {
					sb.append("<p>"
								+ "Your entire body, save for a small, glowing sphere in the place where your heart should be, is made out of [pc.skinFullDescription(true)]!"
								+ " You don't need to have any parts of your body pierced in order to equip jewellery, as you can freely morph your body at will!"
							+ "</p>");
				} else {
					sb.append("<p>"
								+ "[npc.NamePos] entire body, save for a small, glowing sphere in the place where [npc.her] heart should be, is made out of [npc.skinFullDescription(true)]!"
								+ " [npc.She] doesn't need to have any parts of [npc.her] body pierced in order to equip jewellery, as [npc.she] can freely morph [npc.her] body at will!"
							+ "</p>");
				}
				break;
			case AIR:
			case ARCANE:
			case STONE:
			case RUBBER:
			case ICE:
			case WATER:
			case FIRE:
				if (owner.isPlayer()) {
					sb.append("<p>"
								+ "Your entire body, save for a small obsidian sphere in the place where your heart should be, is made out of"
									+ " <b style='color:"+this.getBodyMaterial().getColour().toWebHexString()+";'>"+this.getBodyMaterial().getName()+"</b>!"
							+ "</p>");
				} else {
					sb.append("<p>"
								+ "[npc.NamePos] entire body, save for a small obsidian sphere in the place where [npc.her] heart should be, is made out of"
									+ " <b style='color:"+this.getBodyMaterial().getColour().toWebHexString()+";'>"+this.getBodyMaterial().getName()+"</b>!"
							+ "</p>");
				}
				break;
		}
		
		// Describe face (ears, eyes & horns):
		// Femininity:
		sb.append("<p>"
					+ "[npc.SheHasFull] ");
		
		if (Femininity.valueOf(femininity) == Femininity.MASCULINE_STRONG) {
			sb.append(
					UtilText.returnStringAtRandom(
							"a <span style='color:" + Colour.MASCULINE_PLUS.toWebHexString() + ";'>very masculine</span>",
							"an <span style='color:" + Colour.MASCULINE_PLUS.toWebHexString() + ";'>extremely handsome</span>"));
			
		} else if (Femininity.valueOf(femininity) == Femininity.MASCULINE) {
			sb.append(
					UtilText.returnStringAtRandom(
							"a <span style='color:" + Colour.MASCULINE.toWebHexString() + ";'>masculine</span>",
							"a <span style='color:" + Colour.MASCULINE.toWebHexString() + ";'>handsome</span>"));
			
		} else if (Femininity.valueOf(femininity) == Femininity.ANDROGYNOUS) {
			sb.append("an <span style='color:" + Colour.ANDROGYNOUS.toWebHexString() + ";'>androgynous</span>");
			
		} else if (Femininity.valueOf(femininity) == Femininity.FEMININE) {
			sb.append(
					UtilText.returnStringAtRandom(
							"a <span style='color:" + Colour.FEMININE.toWebHexString() + ";'>pretty</span>",
							"a <span style='color:" + Colour.FEMININE.toWebHexString() + ";'>feminine</span>",
							"a <span style='color:" + Colour.FEMININE.toWebHexString() + ";'>cute</span>"));
			
		} else {
			sb.append(
					UtilText.returnStringAtRandom(
							"a <span style='color:" + Colour.FEMININE_PLUS.toWebHexString() + ";'>very feminine</span>",
							"a <span style='color:" + Colour.FEMININE_PLUS.toWebHexString() + ";'>beautiful</span>",
							"a <span style='color:" + Colour.FEMININE_PLUS.toWebHexString() + ";'>gorgeous</span>"));
		}
		
		// Face and eyes:
		switch (face.getType()) {
			case HUMAN:
				sb.append(" face.");
				break;
			case ANGEL:
				sb.append(", perfectly proportioned face.");
				break;
			case DEMON_COMMON:
				if(!owner.isShortStature()) {
					sb.append(", perfectly proportioned face.");
				} else {
					sb.append(", impish face.");
				}
				break;
			case DOG_MORPH:
				sb.append(", anthropomorphic dog-like face, complete with a canine muzzle.");
				break;
			case FOX_MORPH:
				sb.append(", anthropomorphic fox-like face, complete with a vulpine muzzle.");
				break;
			case LYCAN:
				sb.append(", anthropomorphic wolf-like face, complete with a lupine muzzle.");
				break;
			case CAT_MORPH:
				sb.append(", anthropomorphic cat-like face, with a cute little feline muzzle.");
				break;
			case CAT_MORPH_PANTHER:
				sb.append(", anthropomorphic cat-like face, with a strong toothy feline muzzle, big nose and a strong jawline.");
				break;
			case ALLIGATOR_MORPH:
				sb.append(", anthropomorphic alligator-like face, with a long flat muzzle.");
				break;
			case COW_MORPH:
				sb.append(", anthropomorphic cow-like face, with a cute little bovine muzzle.");
				break;
			case SQUIRREL_MORPH:
				sb.append(", anthropomorphic squirrel-like face, with a cute little muzzle.");
				break;
			case RAT_MORPH:
				sb.append(", anthropomorphic rat-like face, with a rodent-like muzzle.");
				break;
			case RABBIT_MORPH:
				sb.append(", anthropomorphic rabbit-like face, with a cute little muzzle.");
				break;
			case BAT_MORPH:
				sb.append(", anthropomorphic bat-like face, with a cute little muzzle.");
				break;
			case HORSE_MORPH:
				sb.append(", anthropomorphic horse-like face, with a long, equine muzzle.");
				break;
			case REINDEER_MORPH:
				sb.append(", anthropomorphic reindeer-like face, with a long, reindeer-like muzzle.");
				break;
			case HARPY:
				sb.append(", anthropomorphic bird-like face, complete with beak.");
				break;
		}
		
		Covering faceCovering = owner.getCovering(face.getBodyCoveringType(owner));
		if(faceCovering.getPattern()==CoveringPattern.FRECKLED_FACE) {
			sb.append(" While mostly "
						+Covering.getFormattedColour(faceCovering.getPrimaryColour(), "", faceCovering.isPrimaryGlowing(), false)
						+" in colour, [npc.she] [npc.has] a scattering of "
						+Covering.getFormattedColour(faceCovering.getSecondaryColour(), "", faceCovering.isSecondaryGlowing(), false)
						+" freckles on [npc.her] cheeks.");
		}
		
		// Lynx side fluff
		if(hair.getType() == HairType.CAT_MORPH_SIDEFLUFF) {
			sb.append(" On the sides of [npc.her] face [npc.sheHasFull] some soft, fuzzy fur.");
		}

		if(owner.getBlusher().getPrimaryColour()!=Colour.COVERING_NONE) {
			sb.append(" [npc.SheIsFull] wearing "+owner.getBlusher().getColourDescriptor(owner, true, false)+" blusher.");
		}
		
		// Hair:
		
		if (hair.getRawLengthValue() == 0) {
			sb.append(" [npc.SheHasFull] no hair on [npc.her] head, revealing the [npc.faceSkin] that covers [npc.her] scalp.");
			
		} else {
			sb.append(" [npc.SheHasFull] [npc.hairLength], [npc.hairColour(true)]");
			
			switch (hair.getType()) {
				case HUMAN:
					sb.append(" hair");
					break;
				case DEMON_COMMON:
					sb.append(", silken hair");
					break;
				case ANGEL:
					sb.append(", silken hair");
					break;
				case DOG_MORPH:
					sb.append(", fur-like hair");
					break;
				case FOX_MORPH:
					sb.append(", fur-like hair");
					break;
				case LYCAN:
					sb.append(", fur-like hair");
					break;
				case CAT_MORPH:
					sb.append(", fur-like hair");
					break;
				case CAT_MORPH_SIDEFLUFF:
					sb.append(", fur-like hair");
					break;
				case COW_MORPH:
					sb.append(", fur-like hair");
					break;
				case ALLIGATOR_MORPH:
					sb.append(", coarse hair");
					break;
				case SQUIRREL_MORPH:
					sb.append(", fur-like hair");
					break;
				case RAT_MORPH:
					sb.append(", fur-like hair");
					break;
				case RABBIT_MORPH:
					sb.append(", fur-like hair");
					break;
				case BAT_MORPH:
					sb.append(", fur-like hair");
					break;
				case HORSE_MORPH:
					sb.append(", horse-like hair");
					break;
				case REINDEER_MORPH:
					sb.append(", reindeer-like hair");
					break;
				case HARPY:
					sb.append(" feathers in place of hair");
					break;
			
			}
			switch (hair.getStyle()) {
				case BRAIDED:
					sb.append(", which "+(hair.getType().isDefaultPlural()?"have":"has")+" been woven into a long braid.");
					break;
				case CURLY:
					sb.append(", which "+(hair.getType().isDefaultPlural()?"have":"has")+" been curled and left loose.");
					break;
				case LOOSE:
					sb.append(", which "+(hair.getType().isDefaultPlural()?"are":"is")+" left loose and unstyled.");
					break;
				case NONE:
					sb.append(", which "+(hair.getType().isDefaultPlural()?"are":"is")+" unstyled.");
					break;
				case PONYTAIL:
					sb.append(", which "+(hair.getType().isDefaultPlural()?"have":"has")+" been styled into a ponytail.");
					break;
				case STRAIGHT:
					sb.append(", which "+(hair.getType().isDefaultPlural()?"have":"has")+" been straightened and left loose.");
					break;
				case TWIN_TAILS:
					sb.append(", which "+(hair.getType().isDefaultPlural()?"have":"has")+" been styled into twin tails.");
					break;
				case WAVY:
					sb.append(", which "+(hair.getType().isDefaultPlural()?"have":"has")+" been styled into waves and left loose.");
					break;
				case MOHAWK:
					sb.append(", which "+(hair.getType().isDefaultPlural()?"have":"has")+" been styled into a mohawk.");
					break;
				case AFRO:
					sb.append(", which "+(hair.getType().isDefaultPlural()?"have":"has")+" been styled into an afro.");
					break;
				case SIDECUT:
					sb.append(", which "+(hair.getType().isDefaultPlural()?"have":"has")+" been styled into a sidecut.");
					break;
				case BOB_CUT:
					sb.append(", which "+(hair.getType().isDefaultPlural()?"have":"has")+" been styled into a bob cut.");
					break;
				case PIXIE:
					sb.append(", which "+(hair.getType().isDefaultPlural()?"have":"has")+" been styled into a pixie-cut.");
					break;
				case SLICKED_BACK:
					sb.append(", which "+(hair.getType().isDefaultPlural()?"have":"has")+" been slicked back.");
					break;
				case MESSY:
					sb.append(", which "+(hair.getType().isDefaultPlural()?"are":"is")+" unstyled and very messy.");
					break;
				case HIME_CUT:
					sb.append(", which "+(hair.getType().isDefaultPlural()?"have":"has")+" been straightened and styled into a hime cut.");
					break;
				case CHONMAGE:
					sb.append(", which "+(hair.getType().isDefaultPlural()?"have":"has")+" been straightened, oiled and styled into a chonmage topknot.");
					break;
				case TOPKNOT:
					sb.append(", which "+(hair.getType().isDefaultPlural()?"have":"has")+" been styled into a topknot.");
					break;
				case DREADLOCKS:
					sb.append(", which "+(hair.getType().isDefaultPlural()?"have":"has")+" been styled into dreadlocks.");
					break;
				case BIRD_CAGE:
					sb.append(", which "+(hair.getType().isDefaultPlural()?"have":"has")+" been styled into an elaborate bird cage"+UtilText.returnStringAtRandom(".",", birds not included."));
					break;
				case TWIN_BRAIDS:
					sb.append(", which "+(hair.getType().isDefaultPlural()?"have":"has")+" been woven into long twin braids.");
					break;
				case DRILLS:
					sb.append(", which "+(hair.getType().isDefaultPlural()?"have":"has")+" been styled into drills.");
					break;
				case LOW_PONYTAIL:
					sb.append(", which "+(hair.getType().isDefaultPlural()?"have":"has")+" been styled into a low ponytail.");
					break;
				case CROWN_BRAID:
					sb.append(", which "+(hair.getType().isDefaultPlural()?"have":"has")+" been woven into a "+UtilText.returnStringAtRandom("crown of braids.","braided crown."));
					break;
			}
		}
		
		// Horns:
		
		if(owner.hasHorns()) {
			sb.append(" "+owner.getHornType().getBodyDescription(owner));
		}
		
		// Antenna:
		
		switch (antenna.getType()) {
			case NONE:
				sb.append("");
				break;
//				if (owner.isPlayer())
//					sb.append(" [pc.A_antennae+] protrude from your upper forehead.");
//				else
//					sb.append(" [npc.A_antennae+] protrude from [npc.her] upper forehead.");
		}
		
		// Nose:
		
		if(face.isPiercedNose()) {
			if (owner.isPlayer()) {
				sb.append(" Your [pc.nose] has been pierced.");
			} else {
				sb.append(" [npc.Her] [npc.nose] has been pierced.");
			}
		}
		
		// Eyes:
		
		sb.append(" [npc.SheHasFull] [npc.eyePairs] ");
		
		switch(eye.getType()) {
			case ANGEL:
				sb.append(" angelic eyes");
				break;
			case CAT_MORPH:
				sb.append(" cat-like eyes");
				break;
			case COW_MORPH:
				sb.append(" cow-like eyes");
				break;
			case DEMON_COMMON:
				if(!owner.isShortStature()) {
					sb.append(" demonic eyes");
				} else {
					sb.append(" impish eyes");
				}
				break;
			case DOG_MORPH:
				sb.append(" dog-like eyes");
				break;
			case FOX_MORPH:
				sb.append(" fox-like eyes");
				break;
			case ALLIGATOR_MORPH:
				sb.append(" reptilian eyes");
				break;
			case HARPY:
				sb.append(" bird-like eyes");
				break;
			case HORSE_MORPH:
				sb.append(" horse-like eyes");
				break;
			case REINDEER_MORPH:
				sb.append(" reindeer-like eyes");
				break;
			case HUMAN:
				sb.append(" normal, human eyes");
				break;
			case LYCAN:
				sb.append(" wolf-like eyes");
				break;
			case SQUIRREL_MORPH:
				sb.append(" squirrel-like eyes");
				break;
			case RAT_MORPH:
				sb.append(" rat-like eyes");
				break;
			case RABBIT_MORPH:
				sb.append(" rabbit-like eyes");
				break;
			case BAT_MORPH:
				sb.append(" bat-like eyes");
				break;
		}
		
		if(owner.getCovering(owner.getEyeCovering()).getPattern() == CoveringPattern.EYE_IRISES_HETEROCHROMATIC) {
			sb.append(", with [npc.irisShape], heterochromatic [npc.irisPrimaryColour(true)]-and-[npc.irisSecondaryColour(true)] irises");
		} else {
			sb.append(", with [npc.irisShape], [npc.irisPrimaryColour(true)] irises");
		}
		
		if(owner.getCovering(BodyCoveringType.EYE_PUPILS).getPattern() == CoveringPattern.EYE_PUPILS_HETEROCHROMATIC) {
			sb.append(", [npc.pupilShape], heterochromatic [npc.pupilPrimaryColour(true)]-and-[npc.pupilSecondaryColour(true)] pupils");
		} else {
			sb.append(", [npc.pupilShape], [npc.pupilPrimaryColour(true)] pupils");
		}
		
		if(owner.getCovering(BodyCoveringType.EYE_SCLERA).getPattern() == CoveringPattern.EYE_SCLERA_HETEROCHROMATIC) {
			sb.append(", and heterochromatic [npc.scleraPrimaryColour(true)]-and-[npc.scleraSecondaryColour(true)] sclerae.");
		} else {
			sb.append(", and [npc.scleraPrimaryColour(true)] sclerae.");
		}
		
		
		// Eye makeup:
		if(owner.getEyeLiner().getPrimaryColour()!=Colour.COVERING_NONE) {
			if(owner.isPlayer()) {
				sb.append(" Around your [pc.eyes], you've got a layer of "+owner.getEyeLiner().getColourDescriptor(owner, true, false)+" eye liner.");
			} else {
				sb.append(" Around [npc.her] [npc.eyes], [npc.sheIs] got a layer of "+owner.getEyeLiner().getColourDescriptor(owner, true, false)+" eye liner.");
			}
		}
		if(owner.getEyeShadow().getPrimaryColour()!=Colour.COVERING_NONE) {
			if(owner.isPlayer()) {
				sb.append(" You're wearing a tasteful amount of "+owner.getEyeShadow().getFullDescription(owner, true)+".");
			} else {
				sb.append(" [npc.sheIs] wearing a tasteful amount of "+owner.getEyeShadow().getFullDescription(owner, true)+".");
			}
		}
		
		// Ear:
		sb.append(" "+ear.getType().getBodyDescription(owner));
		
		sb.append("</p>"
				+ "<p>");
		
		if(Main.game.isFacialHairEnabled()) {
			if(owner.getFacialHairType().getType()==BodyCoveringType.BODY_HAIR_SCALES_ALLIGATOR) {
				switch(owner.getFacialHair()) {
					case ZERO_NONE:
						if(!owner.isFeminine()) {
							sb.append(" [npc.She] [npc.do]n't have any trace of rough, stubbly "+owner.getFacialHairType().getName(owner)+".");
						}
						break;
					case ONE_STUBBLE:
						sb.append(" [npc.SheHasFull] a stubbly patch of "+owner.getFacialHairType().getFullDescription(owner, true)+" growing on [npc.her] [npc.face].");
						break;
					case TWO_MANICURED:
						sb.append(" [npc.SheHasFull] a small, rough patch of "+owner.getFacialHairType().getFullDescription(owner, true)+" growing on [npc.her] [npc.face], which resembles a beard.");
						break;
					case THREE_TRIMMED:
						sb.append(" [npc.SheHasFull] a rough patch of "+owner.getFacialHairType().getFullDescription(owner, true)+" growing on [npc.her] [npc.face], which resembles a beard.");
						break;
					case FOUR_NATURAL:
						sb.append(" [npc.SheHasFull] a natural-looking rough patch of "+owner.getFacialHairType().getFullDescription(owner, true)+" growing on [npc.her] [npc.face], which resembles a beard.");
						break;
					case FIVE_UNKEMPT:
						sb.append(" [npc.SheHasFull] a unkempt, rough patch of "+owner.getFacialHairType().getFullDescription(owner, true)+" growing on [npc.her] [npc.face], which resembles a beard.");
						break;
					case SIX_BUSHY:
						sb.append(" [npc.SheHasFull] a thick, rough patch of "+owner.getFacialHairType().getFullDescription(owner, true)+" growing on [npc.her] [npc.face], which resembles a beard.");
						break;
					case SEVEN_WILD:
						sb.append(" [npc.SheHasFull] a wild, rough patch of "+owner.getFacialHairType().getFullDescription(owner, true)+" growing on [npc.her] [npc.face], which resembles a beard.");
						break;
				}
			} else {
				switch(owner.getFacialHair()) {
					case ZERO_NONE:
						if(!owner.isFeminine()) {
							sb.append(" [npc.She] [npc.do]n't have any trace of facial "+owner.getFacialHairType().getName(owner)+".");
						}
						break;
					case ONE_STUBBLE:
						sb.append(" [npc.SheHasFull] a stubbly patch of "+owner.getFacialHairType().getFullDescription(owner, true)+" growing on [npc.her] [npc.face].");
						break;
					case TWO_MANICURED:
						sb.append(" [npc.SheHasFull] a small amount of "+owner.getFacialHairType().getFullDescription(owner, true)+" growing on [npc.her] [npc.face].");
						break;
					case THREE_TRIMMED:
						sb.append(" [npc.SheHasFull] a well-trimmed beard of "+owner.getFacialHairType().getFullDescription(owner, true)+" growing on [npc.her] [npc.face].");
						break;
					case FOUR_NATURAL:
						sb.append(" [npc.SheHasFull] a natural-looking beard of "+owner.getFacialHairType().getFullDescription(owner, true)+" growing on [npc.her] [npc.face].");
						break;
					case FIVE_UNKEMPT:
						sb.append(" [npc.SheHasFull] a unkempt, bushy beard of "+owner.getFacialHairType().getFullDescription(owner, true)+" growing on [npc.her] [npc.face].");
						break;
					case SIX_BUSHY:
						sb.append(" [npc.SheHasFull] a thick, bushy beard of "+owner.getFacialHairType().getFullDescription(owner, true)+" growing on [npc.her] [npc.face].");
						break;
					case SEVEN_WILD:
						sb.append(" [npc.SheHasFull] a wild, bushy beard of "+owner.getFacialHairType().getFullDescription(owner, true)+" growing on [npc.her] [npc.face].");
						break;
				}
			}
		}
		
		// Mouth & lips:
		sb.append(" [npc.SheHasFull] [npc.lipSize], [npc.mouthColourPrimary(true)] [npc.lips]");
		if(owner.getLipstick().getPrimaryColour()!=Colour.COVERING_NONE) {
			sb.append((owner.isPiercedLip()?", which have been pierced, and":", which")+" are currently [npc.materialCompositionDescriptor] "+owner.getLipstick().getFullDescription(owner, true)+".");
		} else {
			sb.append((owner.isPiercedLip()?", which have been pierced.":"."));
		}
		sb.append(" [npc.Her] throat is [npc.mouthColourSecondary(true)] in colour.");
		
		// Throat modifiers:
		for(OrificeModifier om : OrificeModifier.values()) {
			if(owner.hasFaceOrificeModifier(om)) {
				switch(om) {
					case PUFFY:
						sb.append(" [npc.Her] [npc.lips] have swollen up to be far puffier than what would be considered normal.");
						break;
					case MUSCLE_CONTROL:
						sb.append(" [npc.SheHasFull] a series of internal muscles lining the inside of [npc.her] throat, allowing [npc.herHim] to expertly squeeze and grip down on any intruding object.");
						break;
					case RIBBED:
						sb.append(" The inside of [npc.her] throat is lined with sensitive, fleshy ribs, which grant [npc.herHim] extra pleasure when stimulated.");
						break;
					case TENTACLED:
						sb.append(" [npc.Her] throat is filled with tiny little tentacles, which wriggle and squirm with a mind of their own.");
						break;
				}
			}
		}
		
		
		// Tongue & blowjob:
		if (owner.isPlayer()) {
			sb.append(" Your mouth holds [pc.a_tongueLength], [pc.tongueColour(true)] [pc.tongue]"+ (face.getTongue().isPierced() ? ", which has been pierced." : "."));
		} else {
			sb.append(" [npc.Her] mouth holds [npc.a_tongueLength], [npc.tongueColour(true)] [npc.tongue]"+ (face.getTongue().isPierced() ? ", which has been pierced." : "."));
		}
		
		for(TongueModifier tm : TongueModifier.values()) {
			if(owner.hasTongueModifier(tm)) {
				if(owner.isPlayer()) {
					switch(tm) {
						case RIBBED:
							sb.append(" It's lined with hard, fleshy ribs, which are sure to grant extra pleasure to any orifice that they penetrate.");
							break;
						case TENTACLED:
							sb.append(" A series of little tentacles coat its surface, which wriggle and squirm with a mind of their own.");
							break;
						case BIFURCATED:
							sb.append(" Near the tip, it's split in two, leaving your tongue bifurcated, like a snake.");
							break;
					}
				} else {
					switch(tm) {
						case RIBBED:
							sb.append(" It's lined with hard, fleshy ribs, which are sure to grant extra pleasure to any orifice that they penetrate.");
							break;
						case TENTACLED:
							sb.append(" A series of little tentacles coat its surface, which wriggle and squirm with a mind of their own.");
							break;
						case BIFURCATED:
							sb.append(" Near the tip, it's split in two, leaving [npc.her] tongue bifurcated, like a snake.");
							break;
					}
				}
			}
		}
		
		
		if (owner.isPlayer() || owner.isAreaKnownByCharacter(CoverableArea.MOUTH, Main.game.getPlayer())) {
			if (face.getMouth().getOrificeMouth().isVirgin()) {
				sb.append(" [npc.SheHas] [style.colourExcellent(never given head before)], so [npc.is] unsure of how much [npc.she] could fit down [npc.her] throat.</span>");
			} else {
				switch(face.getMouth().getOrificeMouth().getCapacity().getMaximumSizeComfortableWithLube()) {
					case NEGATIVE_UTILITY_VALUE:
					case ZERO_MICROSCOPIC:
						sb.append(" [style.colourSex([npc.SheIs] terrible at giving head)], and would struggle to fit the tip of even the smallest of cocks into [npc.her] mouth without gagging.");
						break;
					case ONE_TINY:
						sb.append(" [style.colourSex([npc.SheIs] really bad at giving head)], and would struggle to fit even tiny cocks into [npc.her] mouth without gagging.");
						break;
					case TWO_AVERAGE:
						sb.append(" [style.colourSex([npc.SheIs] not great at giving head)], and putting anything larger than an average-sized human cock into [npc.her] mouth would cause [npc.her] to gag.");
						break;
					case THREE_LARGE:
						sb.append(" [style.colourSex([npc.SheIs] somewhat competent at giving head)], and can suppress [npc.her] gag reflex enough to comfortably suck large cocks.");
						break;
					case FOUR_HUGE:
						sb.append(" [style.colourSex([npc.SheIs] pretty good at giving head)], and can comfortably suck huge cocks without gagging.");
						break;
					case FIVE_ENORMOUS:
						sb.append(" [style.colourSex([npc.SheIs] somewhat of an expert at giving head)], and can suck enormous cocks without too much difficulty.");
						break;
					case SIX_GIGANTIC:
						sb.append(" [style.colourSex([npc.SheIs] amazing at giving head)], and can comfortably suck all but the most absurdly-sized cocks with ease.");
						break;
					case SEVEN_STALLION:
						sb.append(" [style.colourSex([npc.SheIs])] [style.colourLegendary(legendary)] [style.colourSex(at giving head)]; it's almost as though [npc.her] throat was purposefully designed to fit phallic objects of any size or shape.");
						break;
				}
				
				// Throat wetness:
				switch (face.getMouth().getOrificeMouth().getWetness(owner)) {
					case ZERO_DRY:
						sb.append(" [npc.Her] mouth and throat are [style.colourWetness(very dry)], and [npc.she] struggles to produce any saliva at all.");
						break;
					case ONE_SLIGHTLY_MOIST:
						sb.append(" [npc.Her] mouth and throat are [style.colourWetness(drier than most)], and [npc.she] only produces a small amount of saliva.");
						break;
					case TWO_MOIST:
						sb.append(" [npc.Her] mouth and throat are [style.colourWetness(a little drier than most)], and [npc.she] produces less saliva than what could be considered average.");
						break;
					case THREE_WET:
						sb.append(" [npc.Her] mouth and throat are [style.colourWetness(of a typical wetness)], and [npc.she] produces an average amount of saliva.");
						break;
					case FOUR_SLIMY:
						sb.append(" [npc.Her] mouth and throat are [style.colourWetness(wetter than most)], and [npc.she] produces more saliva than an average person.");
						break;
					case FIVE_SLOPPY:
						sb.append(" [npc.Her] mouth and throat are [style.colourWetness(considerably wetter than most)], and [npc.she] produces a lot more more saliva than an average person.");
						break;
					case SIX_SOPPING_WET:
						sb.append(" [npc.Her] mouth and throat are [style.colourWetness(always wet and slimy)], and [npc.she] produces so much saliva that [npc.she] finds [npc.herself] having to swallow every minute or so.");
						break;
					case SEVEN_DROOLING:
						sb.append(" [npc.Her] mouth and throat are [style.colourWetness(overflowing with saliva)],"
								+ " and despite [npc.her] continuous efforts to swallow down the slimy mess, a constant stream of drool trickles from the corners of [npc.her] mouth.");
						break;
				}

				// Elasticity & plasticity:
				sb.append(" [npc.Her] throat");
				switch (face.getMouth().getOrificeMouth().getElasticity()) {
					case ZERO_UNYIELDING:
						sb.append(" is [style.colourElasticity(extremely unyielding)],");
						break;
					case ONE_RIGID:
						sb.append(" [style.colourElasticity(takes a huge amount of effort to be stretched out)],");
						break;
					case TWO_FIRM:
						sb.append(" [style.colourElasticity(does not stretch very easily)],");
						break;
					case THREE_FLEXIBLE:
						sb.append(" [style.colourElasticity(reluctantly stretches out)] when used as a sexual orifice,");
						break;
					case FOUR_LIMBER:
						sb.append(" is [style.colourElasticity(somewhat resistant to being stretched out)],");
						break;
					case FIVE_STRETCHY:
						sb.append(" [style.colourElasticity(stretches out fairly easily)],");
						break;
					case SIX_SUPPLE:
						sb.append(" [style.colourElasticity(stretches out very easily)],");
						break;
					case SEVEN_ELASTIC:
						sb.append(" is [style.colourElasticity(extremely elastic)],");
						break;
				}
				sb.append(" and after being used, it "+face.getMouth().getOrificeMouth().getPlasticity().getDescription()+".");
			}
		} else {
			sb.append(" [style.colourDisabled(You don't know [npc.herHim] well enough to know how competent [npc.she] is at performing oral sex.)]");
		}
		
		sb.append("</p>");

		
		// Describe body:
		
		sb.append("<p>");
		sb.append("[npc.Her] torso has");
		
		switch(Femininity.valueOf(femininity)) {
			case MASCULINE_STRONG:
				sb.append(UtilText.returnStringAtRandom(
						" an <span style='color:" + Colour.MASCULINE_PLUS.toWebHexString() + ";'>extremely masculine</span> appearance",
						" a <span style='color:" + Colour.MASCULINE_PLUS.toWebHexString() + ";'>very masculine</span> appearance"));
				break;
			case MASCULINE:
				sb.append(UtilText.returnStringAtRandom(
						" a <span style='color:" + Colour.MASCULINE.toWebHexString() + ";'>masculine</span> appearance",
						" a <span style='color:" + Colour.MASCULINE.toWebHexString() + ";'>boyish</span> appearance"));
				break;
			case ANDROGYNOUS:
				sb.append(UtilText.returnStringAtRandom(
						" a very <span style='color:" + Colour.ANDROGYNOUS.toWebHexString() + ";'>androgynous</span> appearance"));
				break;
			case FEMININE:
				sb.append(UtilText.returnStringAtRandom(
						" a <span style='color:" + Colour.FEMININE.toWebHexString() + ";'>feminine</span> appearance",
						" a <span style='color:" + Colour.FEMININE.toWebHexString() + ";'>pretty</span> appearance"));
				break;
			case FEMININE_STRONG:
				sb.append(UtilText.returnStringAtRandom(
						" an <span style='color:" + Colour.FEMININE_PLUS.toWebHexString() + ";'>extremely feminine</span> appearance",
						" a <span style='color:" + Colour.FEMININE_PLUS.toWebHexString() + ";'>gorgeous</span> appearance",
						" a <span style='color:" + Colour.FEMININE_PLUS.toWebHexString() + ";'>jaw-droppingly beautiful</span> appearance"));
				break;
		}
		
		if (owner.isPlayer()) {
			sb.append(", and is [npc.materialCompositionDescriptor] [pc.skinFullDescription(true)].");
		} else {
			sb.append(", and is [npc.materialCompositionDescriptor] [npc.skinFullDescription(true)].");
		}
		
		sb.append(" [npc.SheHasFull] <span style='color:"+ BodySize.valueOf(getBodySize()).getColour().toWebHexString() + ";'>" + BodySize.valueOf(getBodySize()).getName(true) + "</span>, "
						+ "<span style='color:"+ Muscle.valueOf(getMuscle()).getColour().toWebHexString() + ";'>" +Muscle.valueOf(getMuscle()).getName(false) + "</span>"
							+ " body, giving [npc.herHim] <span style='color:"+ owner.getBodyShape().toWebHexStringColour() + ";'>[npc.a_bodyShape]</span> body shape.");
		
		
		// Pregnancy:
		if(owner.hasStatusEffect(StatusEffect.PREGNANT_1)){
			sb.append(" [npc.Her] belly is slightly swollen, and it's clear to anyone who takes a closer look that <span style='color:"+Colour.GENERIC_ARCANE.toWebHexString()+";'>[npc.sheIs] pregnant</span>.");
			
		}else if(owner.hasStatusEffect(StatusEffect.PREGNANT_2)){
			sb.append(" [npc.Her] belly is heavily swollen, and it's clear to anyone who glances [npc.her] way that <span style='color:"+Colour.GENERIC_ARCANE.toWebHexString()+";'>[npc.sheIs] pregnant</span>.");
		
		}else if(owner.hasStatusEffect(StatusEffect.PREGNANT_3)){
			sb.append(" [npc.Her] belly is massively swollen, and it's blatantly obvious to anyone who glances [npc.her] way that"
					+ " <span style='color:"+Colour.GENERIC_ARCANE.toWebHexString()+";'>[npc.sheIs] expecting to give birth very soon</span>.");
		}
		sb.append("</p>");
		
		
		
		// Breasts:
		
		sb.append("<p>");
		Breast viewedBreast = breast;
		if(Main.game.getPlayer().hasIngestedPsychoactiveFluidType(FluidTypeBase.MILK)) {
			viewedBreast = new Breast(breast.getType(),
					breast.getShape(),
					(int)(breast.getRawSizeValue()*(1.75f)),
					(int)((breast.getRawMilkStorageValue()+100)*(2.25f)),
					breast.getRows(),
					breast.getNipples().getNippleSizeValue(),
					breast.getNipples().getNippleShape(),
					breast.getNipples().getAreolaeSizeValue(),
					breast.getNippleCountPerBreast(),
					breast.getNipples().getOrificeNipples().getRawCapacityValue(),
					breast.getNipples().getOrificeNipples().getElasticity().getValue(),
					breast.getNipples().getOrificeNipples().getPlasticity().getValue(),
					breast.getNipples().getOrificeNipples().isVirgin());
			sb.append(" <i style='color:"+Colour.PSYCHOACTIVE.toWebHexString()+";'>The psychoactive milk you recently ingested is causing your view of "+(owner.isPlayer()?"your":"[npc.namePos]")+" breasts to be distorted!</i>");
		}
		if(viewedBreast.getRawSizeValue()>0){
			sb.append(" [npc.SheHasFull] " + Util.intToString(owner.getBreastRows()) + " pair" + (owner.getBreastRows() == 1 ? "" : "s") + " of "+viewedBreast.getSize().getDescriptor()+" [npc.breasts]");
			
			if(owner.getBreastRows()==1) {
				if (viewedBreast.getSize().isTrainingBraSize()) {
					sb.append(", which fit comfortably into a training bra.");
				} else {
					sb.append(", which fit comfortably into "+UtilText.generateSingularDeterminer(viewedBreast.getSize().getCupSizeName())+" "+viewedBreast.getSize().getCupSizeName()+"-cup bra.");
				}
				
			} else if(owner.getBreastRows()==2) {
				if (viewedBreast.getSize().isTrainingBraSize()) {
					sb.append(", with [npc.her] top pair fitting comfortably into a training bra, and the pair below being slightly smaller.");
				} else {
					sb.append(", with [npc.her] top pair fitting comfortably into "
							+UtilText.generateSingularDeterminer(viewedBreast.getSize().getCupSizeName())+" "+viewedBreast.getSize().getCupSizeName()+"-cup bra, and the pair below being slightly smaller.");
				}
				
			} else if(owner.getBreastRows()>2) {
				if (viewedBreast.getSize().isTrainingBraSize()) {
					sb.append(", with [npc.her] top pair fitting comfortably into a training bra, and the pairs below each being slightly smaller than the ones above.");
				} else {
					sb.append(", with [npc.her] top pair fitting comfortably into "
								+UtilText.generateSingularDeterminer(viewedBreast.getSize().getCupSizeName())+" "+viewedBreast.getSize().getCupSizeName()+"-cup bra, and the pairs below each being slightly smaller than the ones above.");
				}
			}
			
		} else {
			sb.append(" [npc.SheHasFull] a completely flat chest");
			if(owner.getBreastRows()==1) {
				sb.append(", with a single pair of pecs.");
			} else {
				sb.append(", with "+Util.intToString(owner.getBreastRows())+" pairs of pecs.");
			}
		}
		
		sb.append(" " + getBreastDescription(owner, viewedBreast));
		sb.append("</p>");

		// BreastsCrotch:

		if(Main.getProperties().udders>0) {
			if(owner.isAreaKnownByCharacter(CoverableArea.BREASTS_CROTCH, Main.game.getPlayer())) {
				if(owner.hasBreastsCrotch()) {
						sb.append("<p>"
									+getBreastCrotchDescription(owner, breastCrotch)
								+"</p>");
				} else {
					if(this.leg.getLegConfiguration()!=LegConfiguration.BIPEDAL
							|| (this.getRaceStage()==RaceStage.GREATER && RacialBody.valueOfRace(this.getRace()).getBreastCrotchType()!=BreastType.NONE && Main.getProperties().udders==2)) {
						sb.append("<p>"
									+ "[style.colourDisabled([npc.She] [npc.do] not have any crotch-boobs or udders.)]"
								+ "</p>");
					}
				}
			} else {
				if(leg.getLegConfiguration()!=LegConfiguration.BIPEDAL
						|| (this.getRaceStage()==RaceStage.GREATER && Main.getProperties().udders==2)) {
					if(owner.hasBreastsCrotch() && owner.isBreastsCrotchVisibleThroughClothing() && leg.getLegConfiguration().isBipedalPositionedCrotchBoobs()) {
						sb.append("<p>"
									+ "Although you haven't seen [npc.her] exposed stomach before, [npc.her] [npc.crotchBoobsSize], [npc.crotchBoobsCups]-cup [npc.crotchBoobs] quite clearly bulge out from beneath [npc.her] [npc.topClothing(STOMACH)]."
								+ "</p>");
					} else {
						sb.append("<p>"
									+ "[style.colourDisabled(You haven't seen [npc.her] exposed stomach before, so you don't know if [npc.sheHasFull] any crotch-boobs or udders.)]"
								+ "</p>");
					}
				}
			}
		}
		
		// Arms and legs:

		sb.append("<p>");
		// Arms:
		sb.append(arm.getType().getBodyDescription(owner));
		
		if(owner.isPlayer()) {
			if(owner.getHandNailPolish().getPrimaryColour() != Colour.COVERING_NONE) {
				if(owner.getArmType().equals(ArmType.HARPY)) {
					sb.append(" The little claw on your thumb has been painted in "+owner.getCovering(BodyCoveringType.MAKEUP_NAIL_POLISH_HANDS).getFullDescription(owner, true)+".");
				} else {
					sb.append(" Your fingernails have been painted in "+owner.getCovering(BodyCoveringType.MAKEUP_NAIL_POLISH_HANDS).getFullDescription(owner, true)+".");
				}
			}
		} else {
			if(owner.getHandNailPolish().getPrimaryColour() != Colour.COVERING_NONE) {
				if(owner.getArmType().equals(ArmType.HARPY)) {
					sb.append(" The little claw on [npc.her] thumb has been painted in "+owner.getCovering(BodyCoveringType.MAKEUP_NAIL_POLISH_HANDS).getFullDescription(owner, true)+".");
				} else {
					sb.append(" [npc.Her] fingernails have been painted in "+owner.getCovering(BodyCoveringType.MAKEUP_NAIL_POLISH_HANDS).getFullDescription(owner, true)+".");
				}
			}
		}
		
		if(Main.game.isBodyHairEnabled()) {
			if(owner.getUnderarmHairType().getType()==BodyCoveringType.BODY_HAIR_SCALES_ALLIGATOR) {
				switch(owner.getUnderarmHair()) {
					case ZERO_NONE:
						sb.append(" There is no trace of any rough "+owner.getUnderarmHairType().getName(owner)+" in [npc.her] armpits.");
						break;
					case ONE_STUBBLE:
						sb.append(" [npc.SheHasFull] a rough patch of "+owner.getUnderarmHairType().getFullDescription(owner, true)+" in each of [npc.her] armpits.");
						break;
					case TWO_MANICURED:
						sb.append(" [npc.SheHasFull] a rough patch of "+owner.getUnderarmHairType().getFullDescription(owner, true)+" in each of [npc.her] armpits.");
						break;
					case THREE_TRIMMED:
						sb.append(" [npc.SheHasFull] a rough patch of "+owner.getUnderarmHairType().getFullDescription(owner, true)+" in each of [npc.her] armpits.");
						break;
					case FOUR_NATURAL:
						sb.append(" [npc.SheHasFull] a natural amount of rough "+owner.getUnderarmHairType().getFullDescription(owner, true)+" in each of [npc.her] armpits.");
						break;
					case FIVE_UNKEMPT:
						sb.append(" [npc.SheHasFull] a unkempt mass of rough "+owner.getUnderarmHairType().getFullDescription(owner, true)+" in each of [npc.her] armpits.");
						break;
					case SIX_BUSHY:
						sb.append(" [npc.SheHasFull] a thick, rough mass of "+owner.getUnderarmHairType().getFullDescription(owner, true)+" in each of [npc.her] armpits.");
						break;
					case SEVEN_WILD:
						sb.append(" [npc.SheHasFull] a wild, rough mass of "+owner.getUnderarmHairType().getFullDescription(owner, true)+" in each of [npc.her] armpits.");
						break;
				}
			} else {
				switch(owner.getUnderarmHair()) {
					case ZERO_NONE:
						sb.append(" There is no trace of any "+owner.getUnderarmHairType().getName(owner)+" in [npc.her] armpits.");
						break;
					case ONE_STUBBLE:
						sb.append(" [npc.SheHasFull] a stubbly patch of "+owner.getUnderarmHairType().getFullDescription(owner, true)+" in each of [npc.her] armpits.");
						break;
					case TWO_MANICURED:
						sb.append(" [npc.SheHasFull] a well-manicured patch of "+owner.getUnderarmHairType().getFullDescription(owner, true)+" in each of [npc.her] armpits.");
						break;
					case THREE_TRIMMED:
						sb.append(" [npc.SheHasFull] a trimmed patch of "+owner.getUnderarmHairType().getFullDescription(owner, true)+" in each of [npc.her] armpits.");
						break;
					case FOUR_NATURAL:
						sb.append(" [npc.SheHasFull] a natural amount of "+owner.getUnderarmHairType().getFullDescription(owner, true)+" in each of [npc.her] armpits.");
						break;
					case FIVE_UNKEMPT:
						sb.append(" [npc.SheHasFull] a unkempt mass of "+owner.getUnderarmHairType().getFullDescription(owner, true)+" in each of [npc.her] armpits.");
						break;
					case SIX_BUSHY:
						sb.append(" [npc.SheHasFull] a thick, bushy mass of "+owner.getUnderarmHairType().getFullDescription(owner, true)+" in each of [npc.her] armpits.");
						break;
					case SEVEN_WILD:
						sb.append(" [npc.SheHasFull] a wild, bushy mass of "+owner.getUnderarmHairType().getFullDescription(owner, true)+" in each of [npc.her] armpits.");
						break;
				}
			}
		}

		sb.append("</p>");

		
		// Legs:
		sb.append("<p>");
		switch(owner.getLegConfiguration()) {
			case ARACHNID:
			case CEPHALOPOD:
				sb.append("[npc.Her] [npc.legs], being part of [npc.her] [npc.legRace]'s body, are entirely [style.colourBestial(feral in nature)]. ");
				break;
			case BIPEDAL:
				break;
			case TAIL:
				sb.append("[npc.Her] [npc.legRace]'s tail, which [npc.sheHasFull] in place of legs, is entirely [style.colourBestial(feral in nature)]. ");
				break;
			case TAIL_LONG:
				sb.append("[npc.Her] long [npc.legRace]'s tail, which [npc.sheHasFull] in place of legs, is entirely [style.colourBestial(feral in nature)]. ");
				break;
			case TAUR:
				sb.append("[npc.Her] [npc.legs], being part of [npc.her] [npc.legRace]'s body, are entirely [style.colourBestial(feral in nature)]. ");
				break;
		}
		sb.append(leg.getType().getBodyDescription(owner));
		
		switch(owner.getFootStructure()) {
			case DIGITIGRADE:
				sb.append(" [npc.Her] [npc.legs] and [npc.feet] are [style.colourTFGeneric("+owner.getFootStructure().getName()+")], meaning that [npc.she] naturally [npc.verb(walk)] on [npc.her] toes.");
				break;
			case PLANTIGRADE:
				sb.append(" [npc.Her] [npc.legs] and [npc.feet] are [style.colourTFGeneric("+owner.getFootStructure().getName()+")], meaning that [npc.she] naturally [npc.verb(walk)] with [npc.her] feet flat on the ground.");
				break;
			case UNGULIGRADE:
				sb.append(" [npc.Her] [npc.legs] and [npc.feet] are [style.colourTFGeneric("+owner.getFootStructure().getName()+")], meaning that [npc.she] naturally [npc.verb(walk)] on [npc.her] hoofs.");
				break;
		}
		
		//TODO should be based on a FootType.
		if(owner.getFootNailPolish().getPrimaryColour() != Colour.COVERING_NONE) {
			if(owner.getLegType().equals(LegType.HARPY)) {
				sb.append(" The claws on [npc.her] talons have been painted in "+owner.getCovering(BodyCoveringType.MAKEUP_NAIL_POLISH_FEET).getFullDescription(owner, true)+".");
			} else if(owner.getLegType().equals(LegType.HORSE_MORPH)) {
				sb.append(" [npc.Her] hoofs have been painted in "+owner.getCovering(BodyCoveringType.MAKEUP_NAIL_POLISH_FEET).getFullDescription(owner, true)+".");

			} else if(owner.getLegType().equals(LegType.COW_MORPH)) {
				sb.append(" [npc.Her] hoofs have been painted in "+owner.getCovering(BodyCoveringType.MAKEUP_NAIL_POLISH_FEET).getFullDescription(owner, true)+".");


			} else {
				sb.append(" [npc.Her] toenails have been painted in "+owner.getCovering(BodyCoveringType.MAKEUP_NAIL_POLISH_FEET).getFullDescription(owner, true)+".");
			}
		}
		
		sb.append("<br/>");
		
		if (owner.isPlayer()) {
			sb.append(" Your limbs are "+(Util.randomItemFrom(owner.getBodyShape().getLimbDescriptors()))+", and ");
		} else {
			sb.append(" [npc.Her] limbs are "+(Util.randomItemFrom(owner.getBodyShape().getLimbDescriptors()))+", and ");
		}
		
		if (femininity <= Femininity.MASCULINE_STRONG.getMaximumFemininity()) {
			sb.append(
					UtilText.returnStringAtRandom(
							"<span style='color:" + Colour.MASCULINE_PLUS.toWebHexString() + ";'>have a very masculine shape to them</span>."));
			
		} else if (femininity <= Femininity.MASCULINE.getMaximumFemininity()) {
			sb.append(
					UtilText.returnStringAtRandom(
							"<span style='color:" + Colour.MASCULINE.toWebHexString() + ";'>have a masculine shape to them</span>."));
			
		} else if (femininity <= Femininity.ANDROGYNOUS.getMaximumFemininity()) {
			sb.append("<span style='color:" + Colour.ANDROGYNOUS.toWebHexString() + ";'>look quite androgynous</span>.");
			
		} else if (femininity <= Femininity.FEMININE.getMaximumFemininity()) {
			sb.append(
					UtilText.returnStringAtRandom(
							"<span style='color:" + Colour.FEMININE.toWebHexString() + ";'>have a feminine shape to them</span>."));
			
		} else {
			sb.append(
					UtilText.returnStringAtRandom(
							"<span style='color:" + Colour.FEMININE_PLUS.toWebHexString() + ";'>have an extremely feminine shape to them</span>."));
		}
		
		sb.append("</p>");
		
		
		// Wing:
		if(wing.getType()!=WingType.NONE) {
			sb.append("<p>");
			if(owner.getLegConfiguration().isWingsOnLegConfiguration()) {
				sb.append("Growing from either side of [npc.her] [npc.legConfiguration] body, ");
			} else {
				sb.append("Growing from [npc.her] shoulder-blades, ");
			}
			switch (wing.getType()) {
				case ANGEL:
					sb.append("[npc.sheHasFull] a pair of [npc.wingSize], angelic wings, which are covered in [npc.wingFullDescription(true)].");
					break;
				case DEMON_COMMON:
					sb.append("[npc.sheHasFull] a pair of [npc.wingSize], bat-like wings, which are covered in [npc.wingFullDescription(true)].");
					break;
				case DEMON_FEATHERED:
					sb.append("[npc.sheHasFull] a pair of [npc.wingSize], demonic wings, which are covered in [npc.wingFullDescription(true)].");
					break;
				case NONE:
					break;
				case PEGASUS:
					sb.append("[npc.sheHasFull] a pair of [npc.wingSize] wings, which are covered in [npc.wingFullDescription(true)].");
					break;
			}
			if (wing.getType().allowsFlight()) {
				if(this.getBodyMaterial() == BodyMaterial.SLIME) {
					sb.append(" [style.colourSlime(As they're made out of slime, flight is rendered impossible...)]");
				} else if(wing.getSizeValue()>=owner.getLegConfiguration().getMinimumWingSizeForFlight().getValue()) {
					sb.append(" [style.colourBlue(They are large and powerful enough to allow [npc.herHim] to fly!)]");
				} else {
					sb.append(" They aren't large enough to allow [npc.herHim] to fly.");
				}
			} else if (wing.getType()!=WingType.NONE) {
				sb.append(" They are entirely incapable of flight.");
			}
			sb.append("</p>");
		}
	
		// Tail:
		if(tail.getType()!=TailType.NONE) {
			sb.append("<p>"
					+ "Growing out from just above [npc.her] ass, [npc.sheHasFull] ");
			
			if(owner.getTailCount()==1) {
				switch(owner.getTailType()){
					case CAT_MORPH:
						if (tail.isBestial(owner)) {
							sb.append("[style.colourBestial(a feral)], [npc.tailColour(true)] cat tail, which [npc.she] can control well enough to grant [npc.herHim] significantly improved balance.");
						} else {
							sb.append("a furry, [npc.tailColour(true)] cat-like tail, which [npc.she] can control well enough to grant [npc.herHim] significantly improved balance.");
						}
						break;
					case CAT_MORPH_SHORT:
						if (tail.isBestial(owner)) {
							sb.append("[style.colourBestial(a feral)], short [npc.tailColour(true)] cat-like tail.");
						} else {
							sb.append("a furry, short [npc.tailColour(true)] cat-like tail.");
						}
						break;
					case CAT_MORPH_TUFTED:
						if (tail.isBestial(owner)) {
							sb.append("[style.colourBestial(a feral)], [npc.tailColour(true)] cat tail, which has a fuzzy tuft on the end. [npc.She] can control it well enough to grant [npc.herHim] significantly improved balance.");
						} else {
							sb.append("a furry, [npc.tailColour(true)] cat-like tail, which has a fuzzy tuft on the end. [npc.She] can control it well enough to grant [npc.herHim] significantly improved balance.");
						}
						break;
					case DEMON_COMMON:
						sb.append("a spaded, [npc.tailColour(true)] demonic tail, over which [npc.sheHasFull] complete control, and [npc.she] can easily use it to grip and hold objects.");
						break;
					case DEMON_HAIR_TIP:
						sb.append("a [npc.tailColour(true)] demonic tail, tipped with "+owner.getCovering(BodyCoveringType.HAIR_DEMON).getFullDescription(owner, true)
								+", over which [npc.sheHasFull] complete control, and [npc.she] can easily use it to grip and hold objects.");
						break;
					case DEMON_HORSE:
						if (tail.isBestial(owner)) {
							sb.append("[style.colourBestial(a feral)], [npc.tailColour(true)] horse tail, which [npc.she] can swipe from side to side, but other than that, [npc.she] [npc.does]n't have much control over it.");
						} else {
							sb.append("a long, [npc.tailColour(true)] horse-like tail, which [npc.she] can swipe from side to side, but other than that, [npc.she] [npc.does]n't have much control over it.");
						}
						break;
					case DOG_MORPH:
						if (tail.isBestial(owner)) {
							sb.append("[style.colourBestial(a feral)], [npc.tailColour(true)] dog tail, which wags uncontrollably when [npc.she] [npc.verb(get)] excited.");
						} else {
							sb.append("a furry, [npc.tailColour(true)] dog-like tail, which wags uncontrollably when [npc.she] [npc.verb(get)] excited.");
						}
						break;
					case DOG_MORPH_STUBBY:
						if (tail.isBestial(owner)) {
							sb.append("[style.colourBestial(a feral)], stubby, [npc.tailColour(true)] dog tail, which wags uncontrollably when [npc.she] [npc.verb(get)] excited.");
						} else {
							sb.append("a stubby, [npc.tailColour(true)] dog-like tail, which wags uncontrollably when [npc.she] [npc.verb(get)] excited.");
						}
						break;
					case FOX_MORPH:
						if (tail.isBestial(owner)) {
							sb.append("[style.colourBestial(a feral)], [npc.tailColour(true)] fox tail.");
						} else {
							sb.append("a furry, [npc.tailColour(true)] fox-like tail.");
						}
						break;
					case FOX_MORPH_MAGIC:
						if (tail.isBestial(owner)) {
							sb.append("[style.colourBestial(a feral)], [npc.tailColour(true)] fox tail, which grants [npc.herHim] a small fraction of the power of the youko.");
						} else {
							sb.append("a furry, [npc.tailColour(true)] fox-like tail, which grants [npc.herHim] a small fraction of the power of the youko.");
						}
						break;
					case ALLIGATOR_MORPH:
						if (tail.isBestial(owner)) {
							sb.append("[style.colourBestial(a feral)], [npc.tailColour(true)] alligator tail, which [npc.she] can swipe from side to side with considerable force.");
						} else {
							sb.append("a long, [npc.tailColour(true)] alligator-like tail, which [npc.she] can swipe from side to side with considerable force.");
						}
						break;
					case HARPY:
						if (tail.isBestial(owner)) {
							sb.append("[style.colourBestial(a feral)] plume of beautiful, [npc.tailColour(true)] tail-feathers,"
									+ " which [npc.she] can rapidly move up and down to help [npc.herHim] keep [npc.her] balance and to control [npc.her] path when in flight.");
						} else {
							sb.append("a plume of beautiful, [npc.tailColour(true)] tail-feathers,"
									+ " which [npc.she] can rapidly move up and down to help [npc.herHim] keep [npc.her] balance and to control [npc.her] path when in flight.");
						}
						break;
					case HORSE_MORPH:
						if (tail.isBestial(owner)) {
							sb.append("[style.colourBestial(a feral)], [npc.tailColour(true)] horse tail, which [npc.she] can swipe from side to side, but other than that, [npc.she] [npc.does]n't have much control over it.");
						} else {
							sb.append("a long, [npc.tailColour(true)] horse-like tail, which [npc.she] can swipe from side to side, but other than that, [npc.she] [npc.does]n't have much control over it.");
						}
						break;
					case HORSE_MORPH_ZEBRA:
						if (tail.isBestial(owner)) {
							sb.append("[style.colourBestial(a feral)], [npc.tailColour(true)] zebra tail, which [npc.she] can swipe from side to side, but other than that, [npc.she] [npc.does]n't have much control over it.");
						} else {
							sb.append("a [npc.tailColour(true)] zebra-like tail, which [npc.she] can swipe from side to side, but other than that, [npc.she] [npc.does]n't have much control over it.");
						}
						break;
					case REINDEER_MORPH:
						if (tail.isBestial(owner)) {
							sb.append("[style.colourBestial(a feral)], [npc.tailColour(true)] reindeer tail.");
						} else {
							sb.append("a short, [npc.tailColour(true)] reindeer-like tail.");
						}
						break;
					case COW_MORPH:
						if (tail.isBestial(owner)) {
							sb.append("[style.colourBestial(a feral)], [npc.tailColour(true)] cow tail, which [npc.she] can swipe from side to side, but other than that, [npc.she] [npc.does]n't have much control over it.");
						} else {
							sb.append("a long, [npc.tailColour(true)] cow-like tail, which [npc.she] can swipe from side to side, but other than that, [npc.she] [npc.does]n't have much control over it.");
						}
						break;
					case LYCAN:
						if (tail.isBestial(owner)) {
							sb.append("[style.colourBestial(a feral)], [npc.tailColour(true)] wolf tail.");
						} else {
							sb.append("a furry, [npc.tailColour(true)] wolf-like tail.");
						}
						break;
					case SQUIRREL_MORPH:
						if (tail.isBestial(owner)) {
							sb.append("[style.colourBestial(a feral)], [npc.tailColour(true)] squirrel tail, which [npc.she] can control well enough to grant [npc.herHim] significantly improved balance.");
						} else {
							sb.append("a fluffy, [npc.tailColour(true)] squirrel-like tail, which [npc.she] can control well enough to grant [npc.herHim] significantly improved balance.");
						}
						break;
					case RAT_MORPH:
						if (tail.isBestial(owner)) {
							sb.append("[style.colourBestial(a feral)], [npc.tailColour(true)] rat tail, over which [npc.sheHasFull] complete control, and [npc.she] can easily use it to grip and hold objects.");
						} else {
							sb.append("a long, [npc.tailColour(true)] rat-like tail, over which [npc.sheHasFull] has complete control, and [npc.she] can easily use it to grip and hold objects.");
						}
						break;
					case BAT_MORPH:
						if (tail.isBestial(owner)) {
							sb.append("[style.colourBestial(a feral)], [npc.tailColour(true)] bat tail.");
						} else {
							sb.append("a long, [npc.tailColour(true)] bat-like tail.");
						}
						break;
					case RABBIT_MORPH:
						if (tail.isBestial(owner)) {
							sb.append("[style.colourBestial(a feral)], [npc.tailColour(true)] rabbit tail, which is really no more than a large ball of downy fluff.");
						} else {
							sb.append("a round, [npc.tailColour(true)] rabbit-like tail, which is really no more than a large ball of downy fluff.");
						}
						break;
					case NONE:
						break;
				}
			} else {
				sb.append(Util.intToString(owner.getTailCount())+" ");
				switch(owner.getTailType()){
					case CAT_MORPH:
						if (tail.isBestial(owner)) {
							sb.append("[style.colourBestial(feral)], [npc.tailColour(true)] cat tails, which [npc.she] can control well enough to grant [npc.herHim] significantly improved balance.");
						} else {
							sb.append("furry, [npc.tailColour(true)] cat-like tails, which [npc.she] can control well enough to grant [npc.herHim] significantly improved balance.");
						}
						break;
					case CAT_MORPH_SHORT:
						if (tail.isBestial(owner)) {
							sb.append("[style.colourBestial(feral)], short, [npc.tailColour(true)] cat tails.");
						} else {
							sb.append("a furry, short, [npc.tailColour(true)] cat-like tails.");
						}
						break;
					case CAT_MORPH_TUFTED:
						if (tail.isBestial(owner)) {
							sb.append("[style.colourBestial(feral)], [npc.tailColour(true)] cat tails, with a fuzzy tuft on the end of each. [npc.She] can control them well enough to grant [npc.herHim] significantly improved balance.");
						} else {
							sb.append("furry, [npc.tailColour(true)] cat-like tails, with a fuzzy tuft on the end of each. [npc.She] can control them well enough to grant [npc.herHim] significantly improved balance.");
						}
						break;
					case DEMON_COMMON:
						sb.append("spaded, [npc.tailColour(true)] demonic tails, over which [npc.sheHasFull] complete control, and [npc.she] can easily use them to grip and hold objects.");
						break;
					case DEMON_HAIR_TIP:
						sb.append("[npc.tailColour(true)] demonic tails, tipped with "+owner.getCovering(BodyCoveringType.HAIR_DEMON).getFullDescription(owner, true)
								+", over which [npc.sheHasFull] complete control, and [npc.she] can easily use them to grip and hold objects.");
						break;
					case DEMON_HORSE:
						if (tail.isBestial(owner)) {
							sb.append("[style.colourBestial(feral)], [npc.tailColour(true)] horse tails, which [npc.she] can swipe from side to side, but other than that, [npc.she] doesn't have much control over them.");
						} else {
							sb.append("long, [npc.tailColour(true)] horse-like tails, which [npc.she] can swipe from side to side, but other than that, [npc.she] doesn't have much control over them.");
						}
						break;
					case DOG_MORPH:
						if (tail.isBestial(owner)) {
							sb.append("[style.colourBestial(feral)], [npc.tailColour(true)] dog tails, which wag uncontrollably when [npc.she] [npc.verb(get)] excited.");
						} else {
							sb.append("furry, [npc.tailColour(true)] dog-like tails, which wag uncontrollably when [npc.she] [npc.verb(get)] excited.");
						}
						break;
					case DOG_MORPH_STUBBY:
						if (tail.isBestial(owner)) {
							sb.append("[style.colourBestial(feral)], stubby, [npc.tailColour(true)] dog tails, which wag uncontrollably when [npc.she] [npc.verb(get)] excited.");
						} else {
							sb.append("stubby, [npc.tailColour(true)] dog-like tails, which wag uncontrollably when [npc.she] [npc.verb(get)] excited.");
						}
						break;
					case FOX_MORPH:
						if (tail.isBestial(owner)) {
							sb.append("[style.colourBestial(feral)], [npc.tailColour(true)] fox tails.");
						} else {
							sb.append("furry, [npc.tailColour(true)] fox-like tails.");
						}
						break;
					case FOX_MORPH_MAGIC:
						if (tail.isBestial(owner)) {
							sb.append("[style.colourBestial(feral)], [npc.tailColour(true)] fox tails, which grant [npc.herHim] powers of the youko.");
						} else {
							sb.append("furry, [npc.tailColour(true)] fox-like tails, which grant [npc.herHim] powers of the youko.");
						}
						break;
					case ALLIGATOR_MORPH:
						if (tail.isBestial(owner)) {
							sb.append("[style.colourBestial(feral)], [npc.tailColour(true)] alligator tails, which [npc.she] can swipe from side to side with considerable force.");
						} else {
							sb.append("long, [npc.tailColour(true)] alligator-like tails, which [npc.she] can swipe from side to side with considerable force.");
						}
						break;
					case HARPY:
						if (tail.isBestial(owner)) {
							sb.append("[style.colourBestial(feral)] plumes of beautiful, [npc.tailColour(true)] tail-feathers,"
									+ " which [npc.she] can rapidly move up and down to help [npc.herHim] keep [npc.her] balance and to control [npc.her] path when in flight.");
						} else {
							sb.append("plumes of beautiful, [npc.tailColour(true)] tail-feathers,"
									+ " which [npc.she] can rapidly move up and down to help [npc.herHim] keep [npc.her] balance and to control [npc.her] path when in flight.");
						}
						break;
					case HORSE_MORPH:
						if (tail.isBestial(owner)) {
							sb.append("[style.colourBestial(feral)], [npc.tailColour(true)] horse tails, which [npc.she] can swipe from side to side, but other than that, [npc.she] doesn't have much control over them.");
						} else {
							sb.append("long, [npc.tailColour(true)] horse-like tails, which [npc.she] can swipe from side to side, but other than that, [npc.she] doesn't have much control over them.");
						}
						break;
					case HORSE_MORPH_ZEBRA:
						if (tail.isBestial(owner)) {
							sb.append("[style.colourBestial(feral)], [npc.tailColour(true)] zebra tails, which [npc.she] can swipe from side to side, but other than that, [npc.she] [npc.does]n't have much control over them.");
						} else {
							sb.append("[npc.tailColour(true)] zebra-like tails, which [npc.she] can swipe from side to side, but other than that, [npc.she] [npc.does]n't have much control over them.");
						}
						break;
					case REINDEER_MORPH:
						if (tail.isBestial(owner)) {
							sb.append("[style.colourBestial(feral)], [npc.tailColour(true)] reindeer tails.");
						} else {
							sb.append("short, [npc.tailColour(true)] reindeer-like tails.");
						}
						break;
					case COW_MORPH:
						if (tail.isBestial(owner)) {
							sb.append("[style.colourBestial(feral)], [npc.tailColour(true)] cow tails, which [npc.she] can swipe from side to side, but other than that, [npc.she] doesn't have much control over them.");
						} else {
							sb.append("long, [npc.tailColour(true)] cow-like tails, which [npc.she] can swipe from side to side, but other than that, [npc.she] doesn't have much control over them.");
						}
						break;
					case LYCAN:
						if (tail.isBestial(owner)) {
							sb.append("[style.colourBestial(feral)], [npc.tailColour(true)] wolf tails.");
						} else {
							sb.append("furry, [npc.tailColour(true)] wolf-like tails.");
						}
						break;
					case SQUIRREL_MORPH:
						if (tail.isBestial(owner)) {
							sb.append("[style.colourBestial(feral)], [npc.tailColour(true)] squirrel tails, which [npc.she] can control well enough to grant [npc.herHim] significantly improved balance.");
						} else {
							sb.append("fluffy, [npc.tailColour(true)] squirrel-like tails, which [npc.she] can control well enough to grant [npc.herHim] significantly improved balance.");
						}
						break;
					case RAT_MORPH:
						if (tail.isBestial(owner)) {
							sb.append("[style.colourBestial(feral)], [npc.tailColour(true)] rat tails, over which [npc.she] has complete control, and [npc.she] can easily use them to grip and hold objects.");
						} else {
							sb.append("long, [npc.tailColour(true)] rat-like tails, over which [npc.she] has complete control, and [npc.she] can easily use them to grip and hold objects.");
						}
						break;
					case BAT_MORPH:
						if (tail.isBestial(owner)) {
							sb.append("[style.colourBestial(feral)], [npc.tailColour(true)] bat tails.");
						} else {
							sb.append("long, [npc.tailColour(true)] bat-like tails.");
						}
						break;
					case RABBIT_MORPH:
						if (tail.isBestial(owner)) {
							sb.append("[style.colourBestial(feral)], [npc.tailColour(true)] rabbit-like tails, which really are no more than large balls of downy fluff.");
						} else {
							sb.append("round, [npc.tailColour(true)] rabbit-like tails, which really are no more than large balls of downy fluff.");
						}
						break;
					case NONE:
						break;
				}
			}
			sb.append("</p>");
		}
		

		// Cloaca:
		if(owner.isPlayer()
				|| owner.isAreaKnownByCharacter(CoverableArea.ANUS, Main.game.getPlayer())
				|| owner.isAreaKnownByCharacter(CoverableArea.VAGINA, Main.game.getPlayer())
				|| owner.isAreaKnownByCharacter(CoverableArea.PENIS, Main.game.getPlayer())) {
			switch(owner.getGenitalArrangement()) {
				case NORMAL:// Don't need to add a description for normal arrangement I think.
					break;
				case CLOACA:
				case CLOACA_BEHIND:
					sb.append("<p>"
							+ "[style.colourBestial("+owner.getGenitalArrangement().getDescription()+")]"
						+ "</p>");
					break;
			}
		}
		
		
		// Ass & hips:
		sb.append("<p>"
				+ "[npc.Her] [npc.hips+] and [npc.assSize] [npc.ass] are"
				+ (ass.isBestial(owner)
						?" [style.colourBestial(part of [npc.her] feral lower body)], and are"
						:"")
				+ " [npc.materialCompositionDescriptor] [npc.assFullDescription(true)].");
		
		if(owner.isAreaKnownByCharacter(CoverableArea.ANUS, Main.game.getPlayer())) {
			sb.append(" " + getAssDescription(owner, false));
		} else {
			sb.append(" [style.colourDisabled(You haven't seen [npc.her] naked ass before, so you don't know what [npc.her] asshole looks like.)]");
		}
		sb.append("</p>");
		
		if(owner.isAreaKnownByCharacter(CoverableArea.VAGINA, Main.game.getPlayer()) && owner.isAreaKnownByCharacter(CoverableArea.PENIS, Main.game.getPlayer())) {
			// Vagina, virgin/capacity, wetness:
			if (vagina.getType() == VaginaType.NONE && penis.getType() == PenisType.NONE) {
				sb.append("<p>" + getMoundDescription(owner) + "</p>");
			}
		}
		
		if(owner.isAreaKnownByCharacter(CoverableArea.PENIS, Main.game.getPlayer())) {
			// Penises, cum production, testicle size, capacity:
			if (owner.hasPenis()) {
				sb.append("<p>" + getPenisDescription(owner) + "</p>");
			}
		} else {
			sb.append(" <p>"
						+ "[style.colourDisabled(You haven't seen [npc.her] naked groin before, so you don't know what [npc.her] cock looks like, or even if [npc.she] has one.)]"
					+ "</p>");
		}
		
		if(owner.isAreaKnownByCharacter(CoverableArea.VAGINA, Main.game.getPlayer())) {
			// Vagina, virgin/capacity, wetness:
			if (vagina.getType() != VaginaType.NONE) {
				sb.append("<p>" + getVaginaDescription(owner) + "</p>");
			}
		} else {
			sb.append(" <p>"
						+ "[style.colourDisabled(You haven't seen [npc.her] naked groin before, so you don't know what [npc.her] pussy looks like, or even if [npc.she] has one.)]"
					+ "</p>");
		}
		
		
		
		if(!owner.isPlayer()) {
			sb.append(getSexDetails(owner));
			sb.append(getPregnancyDetails(owner));
		}

		return UtilText.parse(owner, sb.toString());
	}

	private void addRaceWeight(Map<Race, Integer> raceWeightMap, Race race, int weight) {
		if(race!=null) {
			raceWeightMap.putIfAbsent(race, 0);
			raceWeightMap.put(race, raceWeightMap.get(race)+weight);
		}
	}

	
	/** To be called after every transformation. Returns the body's race. */
	public void calculateRace(GameCharacter target) {
		
		// Every time race is calculated, it's because parts have changed, so reset the body parts list:
		handleAllBodyPartsList();
		
		if(target!=null) {
			target.removeStatusEffect(StatusEffect.SUBSPECIES_BONUS);
		}
		
		Race race = Race.HUMAN;
		switch(this.getBodyMaterial()) {
			case AIR:
				race = Race.ELEMENTAL;
				this.raceStage = RaceStage.GREATER;
				break;
			case ARCANE:
				race = Race.ELEMENTAL;
				this.raceStage = RaceStage.GREATER;
				break;
			case FIRE:
				race = Race.ELEMENTAL;
				this.raceStage = RaceStage.GREATER;
				break;
			case FLESH:
				race = getRaceFromPartWeighting();
				
				if(raceWeightMap.size()==1) {
					if(raceWeightMap.containsKey(Race.HUMAN)) {
						this.raceStage = RaceStage.HUMAN;
					} else {
						this.raceStage = RaceStage.GREATER;
					}
				} else {
					this.raceStage = RaceStage.LESSER;
				}
				break;
			case ICE:
			case WATER:
				race = Race.ELEMENTAL;
				this.raceStage = RaceStage.GREATER;
				break;
			case RUBBER:
			case STONE:
				race = Race.ELEMENTAL;
				this.raceStage = RaceStage.GREATER;
				break;
			case SLIME:
				race = Race.SLIME;
				this.raceStage = RaceStage.GREATER;
				break;
		}
		
		subspecies = Subspecies.getSubspeciesFromBody(target, this, race);
		
		switch(subspecies) {
			case HALF_DEMON:
				this.setSubspeciesOverride(subspecies);
				break;
			case IMP:
			case IMP_ALPHA:
			case DEMON:
			case ELDER_LILIN:
			case LILIN:
				this.setSubspeciesOverride(subspecies);
				break;
			default:
				break;
		}
	}

	public Race getRaceFromPartWeighting() {
		return getRaceFromPartWeighting(false);
	}
	
	public Race getRaceFromPartWeighting(boolean ignoreOverride) {
		Race race = Race.HUMAN;
		
		raceWeightMap.clear();
		
		addRaceWeight(raceWeightMap, skin.getType().getRace(), 4);
		addRaceWeight(raceWeightMap, face.getType().getRace(), 4);
		
		addRaceWeight(raceWeightMap, arm.getType().getRace(), 3);
		addRaceWeight(raceWeightMap, leg.getType().getRace(), 3);

		addRaceWeight(raceWeightMap, antenna.getType().getRace(), 1);
		addRaceWeight(raceWeightMap, eye.getType().getRace(), 1);
		addRaceWeight(raceWeightMap, ear.getType().getRace(), 1);
		addRaceWeight(raceWeightMap, hair.getType().getRace(), 1);
		addRaceWeight(raceWeightMap, tail.getType().getRace(), 1);
		addRaceWeight(raceWeightMap, wing.getType().getRace(), 1);
		
		// Not using breast, ass, penis, or vagina
		
		int max = 0;
		boolean demonPartFound = false;
		
		for(Entry<Race, Integer> e : raceWeightMap.entrySet()) {
			if(e.getKey()!=null && e.getKey()==Race.DEMON) {
				demonPartFound = true;
				
			} else if(e.getKey()!=null && e.getKey()!=Race.HUMAN && e.getValue()>max) {
				race = e.getKey();
				max = e.getValue();
			}
		}
		if(!ignoreOverride && demonPartFound) { // Just one demon part is enough to make any character a demon:
			return Race.DEMON;
		}
		
		return race;
	}

	public Map<Race, Integer> getRaceWeightMap() {
		return raceWeightMap;
	}
	
	public Race getRace() {
		if(subspecies == null) {
			calculateRace(null);
		}
		return subspecies.getRace();
	}
	
	public Subspecies getSubspecies() {
		return subspecies;
	}

	public RaceStage getRaceStage() {
		return raceStage;
	}

	public Subspecies getSubspeciesOverride() {
		return subspeciesOverride;
	}

	public void setSubspeciesOverride(Subspecies subspeciesOverride) {
		this.subspeciesOverride = subspeciesOverride;
	}

	public Subspecies getHalfDemonSubspecies() {
		return Subspecies.getSubspeciesFromBody(null, this, getRaceFromPartWeighting(true), true);
	}

	public Antenna getAntenna() {
		return antenna;
	}

	public Arm getArm() {
		return arm;
	}

	public Ass getAss() {
		return ass;
	}

	public Breast getBreast() {
		return breast;
	}

	public BreastCrotch getBreastCrotch() {
		return breastCrotch;
	}

	public Face getFace() {
		return face;
	}

	public Eye getEye() {
		return eye;
	}

	public Ear getEar() {
		return ear;
	}

	public Hair getHair() {
		return hair;
	}

	public Horn getHorn() {
		return horn;
	}

	public Leg getLeg() {
		return leg;
	}

	public Penis getPenis() {
		return penis;
	}

	public Penis getSecondPenis() {
		return secondPenis;
	}

	public Skin getSkin() {
		return skin;
	}

	public Tail getTail() {
		return tail;
	}

	public Tentacle getTentacle() {
		return tentacle;
	}

	public Vagina getVagina() {
		return vagina;
	}

	public Wing getWing() {
		return wing;
	}

	public void setAntenna(Antenna antenna) {
		this.antenna = antenna;
	}

	public void setArm(Arm arm) {
		this.arm = arm;
	}

	public void setAss(Ass ass) {
		this.ass = ass;
	}

	public void setBreast(Breast breast) {
		this.breast = breast;
	}

	public void setBreastCrotch(BreastCrotch breastCrotch) {
		this.breastCrotch = breastCrotch;
	}

	public void setFace(Face face) {
		this.face = face;
	}

	public void setEye(Eye eye) {
		this.eye = eye;
	}

	public void setEar(Ear ear) {
		this.ear = ear;
	}

	public void setHair(Hair hair) {
		this.hair = hair;
	}

	public void setLeg(Leg leg) {
		this.leg = leg;
	}

	public void setSkin(Skin skin) {
		this.skin = skin;
	}
	
	public void setHorn(Horn horn) {
		this.horn = horn;
	}

	public void setPenis(Penis penis) {
		this.penis = penis;
	}

	public void setSecondPenis(Penis secondPenis) {
		this.secondPenis = secondPenis;
	}

	public void setTail(Tail tail) {
		this.tail = tail;
	}

	public void setTentacle(Tentacle tentacle) {
		this.tentacle = tentacle;
	}

	public void setVagina(Vagina vagina) {
		this.vagina = vagina;
	}

	public void setWing(Wing wing) {
		this.wing = wing;
	}

	// Descriptions:
	private StringBuilder descriptionSB;

	/**
	 * @param owner The person whose ass is to be described.
	 * @param locationSpecific Whether this description is specific to looking at the person's ass. If they have a cloaca, and you pass in true, it will say something along the lines of "there's no asshole here".
	 * @return A description of this character's asshole.
	 */
	public String getAssDescription(GameCharacter owner, boolean locationSpecific) {
		descriptionSB = new StringBuilder();
		
		switch(owner.getGenitalArrangement()) {
			case CLOACA:
				if(locationSpecific) {
					return UtilText.parse(owner, "[style.italicsBestial([npc.Her] asshole is not located between [npc.her] ass cheeks, and is instead positioned within [npc.her] front-facing cloaca!)]");
				} else {
					descriptionSB.append(UtilText.parse(owner, "[style.italicsBestial([npc.Her] asshole is located within [npc.her] slit-like, front-facing cloaca.)] "));
				}
				break;
			case CLOACA_BEHIND:
				descriptionSB.append(UtilText.parse(owner, "[style.italicsBestial([npc.Her] asshole is located within [npc.her] slit-like, rear-facing cloaca.)] "));
				break;
			case NORMAL:
			break;
		}
		
		descriptionSB.append(ass.getType().getBodyDescription(owner));
		
		// Colour:
		if(ass.getAnus().isBleached()) {
			descriptionSB.append(", which has been bleached so that the rim is no darker than the [npc.assSkin] around it.");
		} else {
			descriptionSB.append(", the rim being slightly darker than the [npc.assSkin] around it.");
		}
		
		if(ass.isBestial(owner)) {
			descriptionSB.append(" [style.colourBestial(Being a part of [npc.her] bestial lower body, it is entirely feral in form, and is no different to that of a regular [npc.assRace]'s.)]");
		}

		descriptionSB.append(" <span style='color:" + Colour.GENERIC_SEX.toWebHexString() + ";'>It is "+Capacity.getCapacityFromValue(ass.getAnus().getOrificeAnus().getStretchedCapacity()).getDescriptor()+", and can comfortably take "
				+ Capacity.getCapacityFromValue(ass.getAnus().getOrificeAnus().getStretchedCapacity()).getMaximumSizeComfortableWithLube().getDescriptor() + " cocks with enough lube.</span>");
		
		if (ass.getAnus().getOrificeAnus().isVirgin()){
			descriptionSB.append(" <span style='color:" + Colour.GENERIC_GOOD.toWebHexString() + ";'>[npc.Name] [npc.has] retained [npc.her] anal virginity.</span>");
			
		}else{
			boolean virginityLossFound = false;
			for(SexAreaPenetration pt : SexAreaPenetration.values()) {
				if(pt.isTakesVirginity()) {
					if(owner.getVirginityLoss(new SexType(SexParticipantType.NORMAL, SexAreaOrifice.ANUS, pt))!=null) {
						descriptionSB.append(" <span style='color:" + Colour.GENERIC_ARCANE.toWebHexString() + ";'>"+ owner.getVirginityLossDescription(new SexType(SexParticipantType.NORMAL, SexAreaOrifice.ANUS, pt)) + "</span>");
						virginityLossFound = true;
						break;
					}
				}
			}
			if(!virginityLossFound) {
				descriptionSB.append(" <span style='color:" + Colour.GENERIC_ARCANE.toWebHexString() + ";'>[npc.Name] [npc.has] lost [npc.her] anal virginity.</span>");
			}
		}
		
		// Ass wetness:
		switch (ass.getAnus().getOrificeAnus().getWetness(owner)) {
			case ZERO_DRY:
				descriptionSB.append(" It is [style.colourWetness(completely dry)], and would need lubricating before sex.");
				break;
			case ONE_SLIGHTLY_MOIST:
				descriptionSB.append(" It is [style.colourWetness(slightly moist)], but would still need lubricating before sex.");
				break;
			case TWO_MOIST:
				descriptionSB.append(" It is [style.colourWetness(moist)], but would still need lubricating before sex.");
				break;
			case THREE_WET:
				descriptionSB.append(" Its [style.colourWetness(surface constantly beads with wet droplets)], which provides enough natural lubrication for sex.");
				break;
			case FOUR_SLIMY:
				descriptionSB.append(" Its surface is always [style.colourWetness(wet)] and ready for penetration.");
				break;
			case FIVE_SLOPPY:
				descriptionSB.append(" Its surface is always [style.colourWetness(wet and slimy)], presenting a well-lubricated orifice for penetration.");
				break;
			case SIX_SOPPING_WET:
				descriptionSB.append(" Its surface is never less than [style.colourWetness(sopping wet)] from natural lubrication, presenting a well-lubricated orifice for penetration.");
				break;
			case SEVEN_DROOLING:
				descriptionSB.append(" It constantly  [style.colourWetness(drools)] with natural lubrication, and its [style.colourWetness(sopping wet)] entrance is always ready for penetration.");
				break;
		}
		// Ass elasticity & plasticity:
		switch (ass.getAnus().getOrificeAnus().getElasticity()) {
			case ZERO_UNYIELDING:
				descriptionSB.append(" It is [style.colourElasticity(extremely unyielding)],");
				break;
			case ONE_RIGID:
				descriptionSB.append(" It [style.colourElasticity(takes a huge amount of effort to stretch it out)],");
				break;
			case TWO_FIRM:
				descriptionSB.append(" It [style.colourElasticity(does not stretch very easily)],");
				break;
			case THREE_FLEXIBLE:
				descriptionSB.append(" It [style.colourElasticity(reluctantly stretches out)] when used as a sexual orifice,");
				break;
			case FOUR_LIMBER:
				descriptionSB.append(" It is [style.colourElasticity(somewhat resistant to being stretched out)],");
				break;
			case FIVE_STRETCHY:
				descriptionSB.append(" It [style.colourElasticity(stretches out fairly easily)],");
				break;
			case SIX_SUPPLE:
				descriptionSB.append(" It [style.colourElasticity(stretches out very easily)],");
				break;
			case SEVEN_ELASTIC:
				descriptionSB.append(" It is [style.colourElasticity(extremely elastic)],");
				break;
		}
		descriptionSB.append(" and after being used, it "+ass.getAnus().getOrificeAnus().getPlasticity().getDescription()+".");
		
		if(Main.game.isAssHairEnabled()) {
			if(owner.isPlayer()) {
				switch(ass.getAnus().getAssHair()) {
					case ZERO_NONE:
						descriptionSB.append(" There is no trace of any "+owner.getAssHairType().getName(owner)+" around your asshole.");
						break;
					case ONE_STUBBLE:
						descriptionSB.append(" You have a few strands of "+owner.getAssHairType().getFullDescription(owner, true)+" around your asshole.");
						break;
					case TWO_MANICURED:
						descriptionSB.append(" You have a very small amount of "+owner.getAssHairType().getFullDescription(owner, true)+" around your asshole.");
						break;
					case THREE_TRIMMED:
						descriptionSB.append(" You have a small amount of "+owner.getAssHairType().getFullDescription(owner, true)+" around your asshole.");
						break;
					case FOUR_NATURAL:
						descriptionSB.append(" You have a natural amount of "+owner.getAssHairType().getFullDescription(owner, true)+" around your asshole.");
						break;
					case FIVE_UNKEMPT:
						descriptionSB.append(" You have an unkempt mass of "+owner.getAssHairType().getFullDescription(owner, true)+" around your asshole.");
						break;
					case SIX_BUSHY:
						descriptionSB.append(" You have a thick, bushy mass of "+owner.getAssHairType().getFullDescription(owner, true)+" around your asshole.");
						break;
					case SEVEN_WILD:
						descriptionSB.append(" You have a wild, bushy mass of "+owner.getAssHairType().getFullDescription(owner, true)+" around your asshole.");
						break;
				}
				
			} else {
				switch(ass.getAnus().getAssHair()) {
					case ZERO_NONE:
						descriptionSB.append(" There is no trace of any "+owner.getAssHairType().getName(owner)+" around [npc.her] asshole.");
						break;
					case ONE_STUBBLE:
						descriptionSB.append(" [npc.She] has a few strands of "+owner.getAssHairType().getFullDescription(owner, true)+" around [npc.her] asshole.");
						break;
					case TWO_MANICURED:
						descriptionSB.append(" [npc.She] has a very small amount of "+owner.getAssHairType().getFullDescription(owner, true)+" around [npc.her] asshole.");
						break;
					case THREE_TRIMMED:
						descriptionSB.append(" [npc.She] has a small amount of "+owner.getAssHairType().getFullDescription(owner, true)+" around [npc.her] asshole.");
						break;
					case FOUR_NATURAL:
						descriptionSB.append(" [npc.She] has a natural amount of "+owner.getAssHairType().getFullDescription(owner, true)+" around [npc.her] asshole.");
						break;
					case FIVE_UNKEMPT:
						descriptionSB.append(" [npc.She] has an unkempt mass of "+owner.getAssHairType().getFullDescription(owner, true)+" around [npc.her] asshole.");
						break;
					case SIX_BUSHY:
						descriptionSB.append(" [npc.She] has a thick, bushy mass of "+owner.getAssHairType().getFullDescription(owner, true)+" around [npc.her] asshole.");
						break;
					case SEVEN_WILD:
						descriptionSB.append(" [npc.She] has a wild, bushy mass of "+owner.getAssHairType().getFullDescription(owner, true)+" around [npc.her] asshole.");
						break;
				}
			}
		}
		
		for(OrificeModifier om : OrificeModifier.values()) {
			if(owner.hasAssOrificeModifier(om)) {
				if(owner.isPlayer()) {
					switch(om) {
						case MUSCLE_CONTROL:
							descriptionSB.append(" You have a series of internal muscles lining the inside of your [pc.asshole], allowing you to expertly squeeze and grip down on any intruding object.");
							break;
						case PUFFY:
							descriptionSB.append(" The rim of your [pc.asshole] has swollen up into a puffy, doughnut-like ring.");
							break;
						case RIBBED:
							descriptionSB.append(" The inside of your [pc.asshole] is lined with sensitive, fleshy ribs, which grant you extra pleasure when stimulated.");
							break;
						case TENTACLED:
							descriptionSB.append(" Your [pc.asshole] is filled with tiny little tentacles, which wriggle and squirm with a mind of their own.");
							break;
					}
				} else {
					switch(om) {
						case MUSCLE_CONTROL:
							descriptionSB.append(" [npc.She] has a series of internal muscles lining the inside of [npc.her] [npc.asshole], allowing [npc.herHim] to expertly squeeze and grip down on any intruding object.");
							break;
						case PUFFY:
							descriptionSB.append(" The rim of [npc.her] [npc.asshole] has swollen up into a puffy, doughnut-like ring.");
							break;
						case RIBBED:
							descriptionSB.append(" The inside of [npc.her] [npc.asshole] is lined with sensitive, fleshy ribs, which grant [npc.herHim] extra pleasure when stimulated.");
							break;
						case TENTACLED:
							descriptionSB.append(" [npc.Her] [npc.asshole] is filled with tiny little tentacles, which wriggle and squirm with a mind of their own.");
							break;
					}
				}
			}
		}
		
		return UtilText.parse(owner, descriptionSB.toString());
	}

	public String getBreastDescription(GameCharacter owner) {
		return getBreastDescription(owner, breast);
	}
	
	public String getBreastDescription(GameCharacter owner, Breast viewedBreast) {
		descriptionSB = new StringBuilder();
		
		boolean playerKnowledgeOfBreasts = owner.isAreaKnownByCharacter(CoverableArea.NIPPLES, Main.game.getPlayer());
		
		if(!owner.isPlayer() && !playerKnowledgeOfBreasts) {
			descriptionSB.append("[style.colourDisabled(You've never seen [npc.her] naked chest, so you don't know what [npc.her] nipples look like.)]");
			
		} else {
			descriptionSB.append("On each of [npc.her] "+(owner.hasBreasts()?owner.getBreastShape().getDescriptor()+" breasts":"pecs")+", [npc.she] [npc.has] "+Util.intToString(owner.getNippleCountPerBreast())+" [npc.nippleSize], ");
			
			switch(owner.getNippleShape()) {
				case NORMAL:
					descriptionSB.append(" [npc.nipplePrimaryColour(true)]");
					break;
				case LIPS:
					descriptionSB.append(" [npc.nipplePrimaryColour(true)]-lipped");
					break;
				case VAGINA:
					descriptionSB.append(" [npc.nipplePrimaryColour(true)]-lipped");
					break;
			}
			if(owner.getNippleCountPerBreast()>1) {
				descriptionSB.append(" [npc.nipples],");
			} else {
				descriptionSB.append(" [npc.nipple],");
			}
			
			switch(owner.getAreolaeShape()) {
				case NORMAL:
					descriptionSB.append(" with [npc.areolaeSize], circular areolae.");
					break;
				case HEART:
					descriptionSB.append(" with [npc.areolaeSize], heart-shaped areolae.");
					break;
				case STAR:
					descriptionSB.append(" with [npc.areolaeSize], star-shaped areolae.");
					break;
			}

			if(breast.isBestial(owner)) {
				descriptionSB.append(" [style.colourBestial(Due to [npc.her] entire body being bestial in form, [npc.her] [npc.nipples] are identical to those of a feral [npc.breastRace]'s.)]");
			}
			
			if(owner.isPiercedNipple()) {
				descriptionSB.append(" They have been pierced.");
			}
			
			if(owner.getNippleCapacity() != Capacity.ZERO_IMPENETRABLE && Main.game.isNipplePenEnabled()) {
				if (viewedBreast.isFuckable()) {
					descriptionSB.append("<br/>[npc.Her] [npc.breasts] have internal, [npc.nippleSecondaryColour(true)] channels, allowing [npc.her] [npc.breastCapacity] [npc.nipples] to be comfortably penetrated by "
							+ Capacity.getCapacityFromValue(viewedBreast.getNipples().getOrificeNipples().getStretchedCapacity()).getMaximumSizeComfortableWithLube().getDescriptor() + " objects with sufficient lubrication.");
					
				} else {
					descriptionSB.append("<br/>[npc.Her] [npc.breasts] have internal, [npc.nippleSecondaryColour(true)] channels,"
							+ " but [npc.she] [npc.verb(need)] them to be at least D-cups before [npc.her] [npc.breastCapacity] [npc.nipples] could be penetrated.");
				}

				// Nipple elasticity & plasticity:
				switch (viewedBreast.getNipples().getOrificeNipples().getElasticity()) {
					case ZERO_UNYIELDING:
						descriptionSB.append(" They are [style.colourElasticity(extremely unyielding)],");
						break;
					case ONE_RIGID:
						descriptionSB.append(" It [style.colourElasticity(takes a huge amount of effort to stretch them out)],");
						break;
					case TWO_FIRM:
						descriptionSB.append(" They [style.colourElasticity(does not stretch very easily)],");
						break;
					case THREE_FLEXIBLE:
						descriptionSB.append(" They [style.colourElasticity(reluctantly stretch out)] when used as a sexual orifice,");
						break;
					case FOUR_LIMBER:
						descriptionSB.append(" They are [style.colourElasticity(somewhat resistant to being stretched out)],");
						break;
					case FIVE_STRETCHY:
						descriptionSB.append(" They [style.colourElasticity(stretch out fairly easily)],");
						break;
					case SIX_SUPPLE:
						descriptionSB.append(" They [style.colourElasticity(stretch out very easily)],");
						break;
					case SEVEN_ELASTIC:
						descriptionSB.append(" They are [style.colourElasticity(extremely elastic)],");
						break;
				}
				descriptionSB.append(" and after being used, they "+viewedBreast.getNipples().getOrificeNipples().getPlasticity().getDescriptionPlural()+".");
				
				for(OrificeModifier om : OrificeModifier.values()) {
					if(owner.hasNippleOrificeModifier(om)) {
						switch(om) {
							case MUSCLE_CONTROL:
								descriptionSB.append(" [npc.She] [npc.has] a series of muscles lining the insides of [npc.her] [npc.nipples], allowing [npc.herHim] to expertly squeeze and grip down on any intruding object.");
								break;
							case PUFFY:
								descriptionSB.append(" [npc.Her] [npc.nipples] have swollen up to be exceptionally plump and puffy.");
								break;
							case RIBBED:
								descriptionSB.append(" The insides of [npc.her] [npc.nipples] are lined with sensitive, fleshy ribs, which grant [npc.herHim] extra pleasure when stimulated.");
								break;
							case TENTACLED:
								descriptionSB.append(" [npc.Her] [npc.nipples] are filled with tiny little tentacles, which wriggle and squirm with a mind of their own.");
								break;
						}
					}
				}
				
				if (!viewedBreast.getNipples().getOrificeNipples().isVirgin()) {
					boolean virginityLossFound = false;
					for(SexAreaPenetration pt : SexAreaPenetration.values()) {
						if(pt.isTakesVirginity()) {
							if(owner.getVirginityLoss(new SexType(SexParticipantType.NORMAL, SexAreaOrifice.NIPPLE, pt))!=null) {
								descriptionSB.append(" <span style='color:" + Colour.GENERIC_ARCANE.toWebHexString() + ";'>"+ owner.getVirginityLossDescription(new SexType(SexParticipantType.NORMAL, SexAreaOrifice.NIPPLE, pt)) + "</span>");
								virginityLossFound = true;
								break;
							}
						}
					}
					if(!virginityLossFound) {
						descriptionSB.append(" <span style='color:" + Colour.GENERIC_ARCANE.toWebHexString() + ";'>[npc.Name] [npc.has] lost [npc.her] nipple virginity.</span>");
					}
					
				} else {
					descriptionSB.append(" [style.colourGood([npc.Name] [npc.has] retained [npc.her] nipple virginity.)]");
				}
				
			} else {
				if(owner.hasNippleOrificeModifier(OrificeModifier.PUFFY)) {
					descriptionSB.append(" [npc.Her] [npc.nipples] have swollen up to be exceptionally plump and puffy.");
				}	
			}
			
			if (viewedBreast.getRawMilkStorageValue() > 0) {
				descriptionSB.append("<br/>[npc.SheIsFull] currently producing "+ Units.fluid(viewedBreast.getRawMilkStorageValue(), Units.UnitType.LONG) + " of [npc.milkPrimaryColour(true)] [npc.milk]"
						+ " ("+ Units.fluid(viewedBreast.getRawStoredMilkValue()) + " currently stored) at [npc.a_milkRegen] rate.");
				
				switch(viewedBreast.getMilk().getFlavour()) {
					case CHOCOLATE:
						descriptionSB.append(" [npc.Her] [npc.milk] tastes of chocolate.");
						break;
					case CUM:
						descriptionSB.append(" [npc.Her] [npc.milk] tastes exactly like cum.");
						break;
					case GIRL_CUM:
						descriptionSB.append(" [npc.Her] [npc.milk] tastes of girl-cum.");
						break;
					case HONEY:
						descriptionSB.append(" [npc.Her] [npc.milk] tastes of honey.");
						break;
					case MILK:
						descriptionSB.append(" [npc.Her] [npc.milk] tastes like regular milk.");
						break;
					case MINT:
						descriptionSB.append(" [npc.Her] [npc.milk] tastes of mint.");
						break;
					case PINEAPPLE:
						descriptionSB.append(" [npc.Her] [npc.milk] tastes of pineapple.");
						break;
					case BUBBLEGUM:
						descriptionSB.append(" [npc.Her] [npc.milk] has the fruity taste of bubblegum.");
						break;
					case STRAWBERRY:
						descriptionSB.append(" [npc.Her] [npc.milk] tastes of strawberries.");
						break;
					case BEER:
						descriptionSB.append(" [npc.Her] [npc.milk] tastes like beer.");
						break;
					case VANILLA:
						descriptionSB.append(" [npc.Her] [npc.milk] tastes of vanilla.");
						break;
					case CHERRY:
						descriptionSB.append(" [npc.Her] [npc.milk] tastes of cherries.");
						break;
				}
				
				for(FluidModifier fm : FluidModifier.values()) {
					if(owner.hasMilkModifier(fm)) {
						switch(fm) {
							case ADDICTIVE:
								descriptionSB.append(" It is highly addictive, and anyone who drinks too much will quickly become dependent on it.");
								break;
							case BUBBLING:
								descriptionSB.append(" It fizzes and bubbles like a carbonated drink.");
								break;
							case HALLUCINOGENIC:
								descriptionSB.append(" Anyone who ingests it suffers psychoactive effects, which can manifest in lactation-related hallucinations or sensitivity to hypnotic suggestion.");
								break;
							case MUSKY:
								descriptionSB.append(" It has a strong, musky smell.");
								break;
							case SLIMY:
								descriptionSB.append(" It has a slimy, oily texture.");
								break;
							case STICKY:
								descriptionSB.append(" It's quite sticky, and is difficult to fully wash off without soap.");
								break;
							case VISCOUS:
								descriptionSB.append(" It's quite viscous, and slowly drips in large globules, much like thick treacle.");
								break;
							case ALCOHOLIC:
								descriptionSB.append(" It has a high alcohol content, and will get those who consume it very drunk.");
								break;
							case MINERAL_OIL:
								descriptionSB.append(" It is rich in minerals good for your skin but not for latex.");
						}
					}
				}
				
			} else {
				descriptionSB.append("<br/>[npc.SheIsFull] not producing any milk.");
			}
		}

		return UtilText.parse(owner, descriptionSB.toString());
	}

	public String getBreastCrotchDescription(GameCharacter owner) {
		return getBreastCrotchDescription(owner, breastCrotch);
	}

	public String getBreastCrotchDescription(GameCharacter owner, BreastCrotch viewedBreastCrotch) {
		descriptionSB = new StringBuilder();
		
//		boolean playerKnowledgeOfUdders = owner.isAreaKnownByCharacter(CoverableArea.NIPPLES_CROTCH, Main.game.getPlayer());
//		
//		if(owner.isPlayer() || playerKnowledgeOfUdders) {

			descriptionSB.append(leg.getLegConfiguration().getCrotchBoobLocationDescription());
			if(breastCrotch.getShape()==BreastShape.UDDERS) {
				if(breastCrotch.getRawSizeValue()>0){
					descriptionSB.append(" [npc.she] [npc.has] [npc.a_crotchBoobsSize] set of udders");
				} else {
					descriptionSB.append(" [npc.she] [npc.has] a completely flat set of udders");
				}
				if(leg.getLegConfiguration().isBipedalPositionedCrotchBoobs() && breastCrotch.getRows()>1) {
					descriptionSB.append(", which, due to the fact that it needs to be large enough to accommodate [npc.her] [npc.crotchBoobsRows] of [npc.crotchNipples], covers most of [npc.her] stomach.");
					
				} else if(breastCrotch.getRows()>1) {
					descriptionSB.append(", which, due to the fact that it needs to be large enough to accommodate [npc.her] [npc.crotchBoobsRows] of [npc.crotchNipples], covers a large portion of [npc.her] underbelly.");
					
				} else {
					descriptionSB.append(".");
				}
				
			} else {
				if(breastCrotch.getRawSizeValue()>0){
					descriptionSB.append(" [npc.she] [npc.has] [npc.crotchBoobsRows] [npc.crotchBoobsSize], [npc.crotchBoobsCups]-cup [npc.crotchBoobs]");
				} else {
					descriptionSB.append(" [npc.she] [npc.has] [npc.crotchBoobsRows] completely flat [npc.crotchBoobs]");
				}
				if(leg.getLegConfiguration().isBipedalPositionedCrotchBoobs() && breastCrotch.getRows()>1) {
					descriptionSB.append(", with the upper pair"+(breastCrotch.getRows()>2?"s":"")+" being positioned higher up on [npc.her] stomach.");
					
				} else if(breastCrotch.getRows()>1) {
					descriptionSB.append(", with the extra pair"+(breastCrotch.getRows()>2?"s":"")+" being positioned further towards [npc.her] underbelly.");
					
				} else {
					descriptionSB.append(".");
				}
			}
			
			if(viewedBreastCrotch.getShape()==BreastShape.UDDERS) {
				descriptionSB.append(" They are formed into [npc.totalCrotchBoobs] protrusions, upon each of which [npc.she] [npc.has] [npc.crotchBoobsNipplesPerBreast] [npc.crotchNippleSize], ");
			} else {
				descriptionSB.append(" On each of [npc.her] [npc.totalCrotchBoobs] [npc.crotchBoobs], [npc.she] [npc.has] [npc.crotchBoobsNipplesPerBreast] [npc.crotchNippleSize], ");
			}
			
			switch(owner.getNippleCrotchShape()) {
				case NORMAL:
					descriptionSB.append(" [npc.crotchNipplePrimaryColour(true)]");
					break;
				case LIPS:
					descriptionSB.append(" [npc.crotchNipplePrimaryColour(true)]-lipped");
					break;
				case VAGINA:
					descriptionSB.append(" [npc.crotchNipplePrimaryColour(true)]-lipped");
					break;
			}
			if(owner.getNippleCrotchCountPerBreast()>1) {
				descriptionSB.append(" [npc.crotchNipples],");
			} else {
				descriptionSB.append(" [npc.crotchNipple],");
			}
			
			switch(owner.getAreolaeCrotchShape()) {
				case NORMAL:
					descriptionSB.append(" with [npc.crotchBoobsAreolaSize], circular areolae.");
					break;
				case HEART:
					descriptionSB.append(" with [npc.crotchBoobsAreolaSize], heart-shaped areolae.");
					break;
				case STAR:
					descriptionSB.append(" with [npc.crotchBoobsAreolaSize], star-shaped areolae.");
					break;
			}

			if(breastCrotch.isBestial(owner)) {
				descriptionSB.append(" [style.colourBestial(As [npc.sheHasFull] the lower body of an animal, [npc.her] [npc.crotchBoobs] are identical in form to those found on a feral [npc.crotchBoobRace].)]");
			}
			
			if(owner.isPiercedNippleCrotch()) {
				descriptionSB.append(" They have been pierced.");
			}
			
			if(owner.getNippleCrotchCapacity() != Capacity.ZERO_IMPENETRABLE && Main.game.isNipplePenEnabled()) {
				if (viewedBreastCrotch.isFuckable()) {
					descriptionSB.append("<br/>[npc.Her] [npc.crotchBoobs] have internal, [npc.crotchNippleSecondaryColour(true)] channels, allowing [npc.her] [npc.crotchBoobsCapacity] [npc.crotchNipples] to be comfortably penetrated by "
							+ Capacity.getCapacityFromValue(viewedBreastCrotch.getNipples().getOrificeNipples().getStretchedCapacity()).getMaximumSizeComfortableWithLube().getDescriptor() + " objects with sufficient lubrication.");
					
				} else {
					descriptionSB.append("<br/>[npc.Her] [npc.crotchBoobs] have internal, [npc.crotchNippleSecondaryColour(true)] channels,"
							+ " but [npc.she] [npc.verb(need)] them to be bigger before [npc.her] [npc.crotchBoobsCapacity] [npc.crotchNipples] could be penetrated.");
				}

				// Nipple elasticity & plasticity:
				switch (viewedBreastCrotch.getNipples().getOrificeNipples().getElasticity()) {
					case ZERO_UNYIELDING:
						descriptionSB.append(" They are [style.colourElasticity(extremely unyielding)],");
						break;
					case ONE_RIGID:
						descriptionSB.append(" It [style.colourElasticity(takes a huge amount of effort to stretch them out)],");
						break;
					case TWO_FIRM:
						descriptionSB.append(" They [style.colourElasticity(does not stretch very easily)],");
						break;
					case THREE_FLEXIBLE:
						descriptionSB.append(" They [style.colourElasticity(reluctantly stretch out)] when used as a sexual orifice,");
						break;
					case FOUR_LIMBER:
						descriptionSB.append(" They are [style.colourElasticity(somewhat resistant to being stretched out)],");
						break;
					case FIVE_STRETCHY:
						descriptionSB.append(" They [style.colourElasticity(stretch out fairly easily)],");
						break;
					case SIX_SUPPLE:
						descriptionSB.append(" They [style.colourElasticity(stretch out very easily)],");
						break;
					case SEVEN_ELASTIC:
						descriptionSB.append(" They are [style.colourElasticity(extremely elastic)],");
						break;
				}
				descriptionSB.append(" and after being used, they "+viewedBreastCrotch.getNipples().getOrificeNipples().getPlasticity().getDescriptionPlural()+".");
				
				for(OrificeModifier om : OrificeModifier.values()) {
					if(owner.hasNippleCrotchOrificeModifier(om)) {
						switch(om) {
							case MUSCLE_CONTROL:
								descriptionSB.append(" [npc.She] [npc.has] a series of muscles lining the insides of [npc.her] [npc.crotchNipples], allowing [npc.herHim] to expertly squeeze and grip down on any intruding object.");
								break;
							case PUFFY:
								descriptionSB.append(" [npc.Her] [npc.crotchNipples] have swollen up to be exceptionally plump and puffy.");
								break;
							case RIBBED:
								descriptionSB.append(" The insides of [npc.her] [npc.crotchNipples] are lined with sensitive, fleshy ribs, which grant [npc.herHim] extra pleasure when stimulated.");
								break;
							case TENTACLED:
								descriptionSB.append(" [npc.Her] [npc.crotchNipples] are filled with tiny little tentacles, which wriggle and squirm with a mind of their own.");
								break;
						}
					}
				}
				
				if (!viewedBreastCrotch.getNipples().getOrificeNipples().isVirgin()) {
					boolean virginityLossFound = false;
					for(SexAreaPenetration pt : SexAreaPenetration.values()) {
						if(pt.isTakesVirginity()) {
							if(owner.getVirginityLoss(new SexType(SexParticipantType.NORMAL, SexAreaOrifice.NIPPLE_CROTCH, pt))!=null) {
								descriptionSB.append(" <span style='color:" + Colour.GENERIC_ARCANE.toWebHexString() + ";'>"+ owner.getVirginityLossDescription(new SexType(SexParticipantType.NORMAL, SexAreaOrifice.NIPPLE_CROTCH, pt)) + "</span>");
								virginityLossFound = true;
								break;
							}
						}
					}
					if(!virginityLossFound) {
						descriptionSB.append(" <span style='color:" + Colour.GENERIC_ARCANE.toWebHexString() + ";'>[npc.Name] [npc.has] lost [npc.her] [npc.crotchNipple] virginity.</span>");
					}
					
				} else {
					descriptionSB.append(" [style.colourGood([npc.Name] [npc.has] retained [npc.her] [npc.crotchNipple] virginity.)]");
				}
				
			} else {
				if(owner.hasNippleCrotchOrificeModifier(OrificeModifier.PUFFY)) {
					descriptionSB.append(" [npc.Her] [npc.crotchNipples] have swollen up to be exceptionally plump and puffy.");
				}	
			}
			
			if (viewedBreastCrotch.getRawMilkStorageValue() > 0) {
				descriptionSB.append("<br/>[npc.SheIsFull] currently producing "
						+ Units.fluid(viewedBreastCrotch.getRawMilkStorageValue(), Units.UnitType.LONG) + " of [npc.crotchMilkPrimaryColour(true)] [npc.crotchMilk] ("
						+ Units.fluid(viewedBreastCrotch.getRawStoredMilkValue(), Units.UnitType.LONG) + " currently stored) at [npc.a_crotchMilkRegen] rate.");
				
				switch(viewedBreastCrotch.getMilk().getFlavour()) {
					case CHOCOLATE:
						descriptionSB.append(" [npc.Her] [npc.crotchMilk] tastes of chocolate.");
						break;
					case CUM:
						descriptionSB.append(" [npc.Her] [npc.crotchMilk] tastes exactly like cum.");
						break;
					case GIRL_CUM:
						descriptionSB.append(" [npc.Her] [npc.crotchMilk] tastes of girl-cum.");
						break;
					case HONEY:
						descriptionSB.append(" [npc.Her] [npc.crotchMilk] tastes of honey.");
						break;
					case MILK:
						descriptionSB.append(" [npc.Her] [npc.crotchMilk] tastes like regular milk.");
						break;
					case MINT:
						descriptionSB.append(" [npc.Her] [npc.crotchMilk] tastes of mint.");
						break;
					case PINEAPPLE:
						descriptionSB.append(" [npc.Her] [npc.crotchMilk] tastes of pineapple.");
						break;
					case BUBBLEGUM:
						descriptionSB.append(" [npc.Her] [npc.crotchMilk] has the fruity taste of bubblegum.");
						break;
					case STRAWBERRY:
						descriptionSB.append(" [npc.Her] [npc.crotchMilk] tastes of strawberries.");
						break;
					case BEER:
						descriptionSB.append(" [npc.Her] [npc.crotchMilk] tastes like beer.");
						break;
					case VANILLA:
						descriptionSB.append(" [npc.Her] [npc.crotchMilk] tastes of vanilla.");
						break;
					case CHERRY:
						descriptionSB.append(" [npc.Her] [npc.crotchMilk] tastes of cherries.");
						break;
				}
				
				for(FluidModifier fm : FluidModifier.values()) {
					if(owner.hasMilkCrotchModifier(fm)) {
						switch(fm) {
							case ADDICTIVE:
								descriptionSB.append(" It is highly addictive, and anyone who drinks too much will quickly become dependent on it.");
								break;
							case BUBBLING:
								descriptionSB.append(" It fizzes and bubbles like a carbonated drink.");
								break;
							case HALLUCINOGENIC:
								descriptionSB.append(" Anyone who ingests it suffers psychoactive effects, which can manifest in lactation-related hallucinations or sensitivity to hypnotic suggestion.");
								break;
							case MUSKY:
								descriptionSB.append(" It has a strong, musky smell.");
								break;
							case SLIMY:
								descriptionSB.append(" It has a slimy, oily texture.");
								break;
							case STICKY:
								descriptionSB.append(" It's quite sticky, and is difficult to fully wash off without soap.");
								break;
							case VISCOUS:
								descriptionSB.append(" It's quite viscous, and slowly drips in large globules, much like thick treacle.");
								break;
							case ALCOHOLIC:
								descriptionSB.append(" It has a high alcohol content, and will get those who consume it very drunk.");
								break;
							case MINERAL_OIL:
								descriptionSB.append(" It is rich in minerals good for your skin but not for latex.");
						}
					}
				}
				
			} else {
				descriptionSB.append("<br/>[npc.SheIsFull] not producing any milk.");
			}
//		}

		return UtilText.parse(owner, descriptionSB.toString());
	}

	public String getPenisDescription(GameCharacter owner) {
		boolean isPlayer = owner.isPlayer();
		
		descriptionSB = new StringBuilder();
		
		Penis viewedPenis = owner.getCurrentPenis();
		
		if(Main.game.getPlayer().hasIngestedPsychoactiveFluidType(FluidTypeBase.CUM)) {
			viewedPenis = new Penis(penis.getType(),
					(int) (penis.getRawSizeValue() * 2.25f),
					false,
					PenisGirth.FOUR_FAT.getValue(),
					penis.getTesticle().getTesticleSize().getValue()*2,
					(int) ((penis.getTesticle().getRawCumStorageValue()+100) * 3.25f),
					penis.getTesticle().getTesticleCount());
			descriptionSB.append("<i style='color:"+Colour.PSYCHOACTIVE.toWebHexString()+";'>The psychoactive cum you recently ingested is causing your view of "+(owner.isPlayer()?"your":"[npc.namePos]")+" cock to be distorted!</i> ");
		}

		switch(owner.getGenitalArrangement()) {
			case CLOACA:
				descriptionSB.append(UtilText.parse(owner, "[style.italicsBestial([npc.Her] cock is located within [npc.her] slit-like, front-facing cloaca.)] "));
				break;
			case CLOACA_BEHIND:
				descriptionSB.append(UtilText.parse(owner, "[style.italicsBestial([npc.Her] cock is located within [npc.her] slit-like, rear-facing cloaca.)] "));
				break;
			case NORMAL:
			break;
		}
		
		if(viewedPenis.getType()!=PenisType.DILDO) {
			if (isPlayer) {
				descriptionSB.append("You have ");
			} else {
				descriptionSB.append("[npc.She] has ");
			}
		} else {
			if (isPlayer) {
				descriptionSB.append("You are currently wearing ");
			} else {
				descriptionSB.append("[npc.She] is currently wearing ");
			}
		}

		descriptionSB.append(UtilText.generateSingularDeterminer(viewedPenis.getSize().getDescriptor())+" "+viewedPenis.getSize().getDescriptor()
				+", "+(viewedPenis.getGirth()==PenisGirth.TWO_AVERAGE?"":viewedPenis.getGirth().getName()
				+", ")+Units.size(viewedPenis.getRawSizeValue(), Units.UnitType.LONG_SINGULAR));
		
		switch (viewedPenis.getType()) {
			case HUMAN:
				descriptionSB.append(" human cock");
				break;
			case DEMON_COMMON:
				if(!owner.isShortStature()) {
					descriptionSB.append(" demonic cock");
				} else {
					descriptionSB.append(" impish cock");
				}
				break;
			case BOVINE:
				descriptionSB.append(" bovine cock");
				break;
			case CANINE:
				descriptionSB.append(" canine cock");
				break;
			case VULPINE:
				descriptionSB.append(" vulpine cock");
				break;
			case LUPINE:
				descriptionSB.append(" lupine cock");
				break;
			case FELINE:
				descriptionSB.append(" feline cock");
				break;
			case ALLIGATOR_MORPH:
				descriptionSB.append(" reptilian cock");
				break;
			case SQUIRREL:
				descriptionSB.append(" [npc.penisRace]'s cock");
				break;
			case RAT_MORPH:
				descriptionSB.append(" [npc.penisRace]'s cock");
				break;
			case RABBIT_MORPH:
				descriptionSB.append(" [npc.penisRace]'s cock");
				break;
			case BAT_MORPH:
				descriptionSB.append(" [npc.penisRace]'s cock");
				break;
			case EQUINE:
				descriptionSB.append(" equine cock");
				break;
			case REINDEER_MORPH:
				descriptionSB.append(" [npc.penisRace]'s cock");
				break;
			case AVIAN:
				descriptionSB.append(" avian cock");
				break;
			case ANGEL:
				descriptionSB.append(" angelic cock");
				break;
			case DILDO:
				descriptionSB.append(" dildo");
				break;
			case NONE:
				break;
		}
		
		if(viewedPenis.getType()==PenisType.DILDO) {
			if (isPlayer) {
				descriptionSB.append(", which is made out of [pc.cockFullDescription(true)].");
			} else {
				descriptionSB.append(", which is made out of [npc.cockFullDescription(true)].");
			}
			
		} else {
			if (isPlayer) {
				descriptionSB.append(", which is [npc.materialCompositionDescriptor] [pc.cockFullDescription(true)].");
			} else {
				descriptionSB.append(", which is [npc.materialCompositionDescriptor] [npc.cockFullDescription(true)].");
			}
			
			if(penis.isBestial(owner)) {
				descriptionSB.append(" [style.colourBestial(As [npc.her] genitals are located on the animal portion of [npc.her] body, [npc.her] [npc.penis] is identical in functionality to that of a feral [npc.penisRace]'s.)]");
			}
		}
		
		for(PenetrationModifier pm : PenetrationModifier.values()) {
			if(owner.hasPenisModifier(pm)) {
				switch(pm) {
					case RIBBED:
						descriptionSB.append(" It's lined with hard, fleshy ribs, which are sure to grant extra pleasure to any orifice that they penetrate.");
						break;
					case TENTACLED:
						descriptionSB.append(" A series of little tentacles coat its surface, which wriggle and squirm with a mind of their own.");
						break;
					case BARBED:
						descriptionSB.append(" Fleshy, backwards-facing barbs line its length.");
						break;
					case BLUNT:
						descriptionSB.append(" The head curves around to a smooth surface.");
						break;
					case FLARED:
						descriptionSB.append(" The head is wide and flared.");
						break;
					case KNOTTED:
						descriptionSB.append(" A fat knot sits at the base.");
						break;
					case PREHENSILE:
						descriptionSB.append(" It is prehensile, and can be manipulated and moved much like a primate's tail.");
						break;
					case SHEATHED:
						descriptionSB.append(" When not in use, it retreats back into the sheath at its base.");
						break;
					case TAPERED:
						descriptionSB.append(" The shaft is tapered, and gets thinner nearer to the head.");
						break;
					case VEINY:
						descriptionSB.append(" Large veins press out from its surface.");
						break;
				}
			}
		}

		if(owner.getCurrentPenis().getType()!=PenisType.DILDO) {
			if (!owner.getCurrentPenis().isVirgin()) {
				boolean virginityLossFound = false;
				for(SexAreaOrifice ot : SexAreaOrifice.values()) {
					if(ot.isInternalOrifice()) {
						if(owner.getVirginityLoss(new SexType(SexParticipantType.NORMAL, SexAreaPenetration.PENIS, ot))!=null) {
							descriptionSB.append(" <span style='color:" + Colour.GENERIC_ARCANE.toWebHexString() + ";'>"+ owner.getVirginityLossDescription(new SexType(SexParticipantType.NORMAL, SexAreaPenetration.PENIS, ot)) + "</span>");
							virginityLossFound = true;
							break;
						}
					}
				}
				if(!virginityLossFound) {
					descriptionSB.append(" <span style='color:" + Colour.GENERIC_ARCANE.toWebHexString() + ";'>[npc.Name] [npc.has] lost [npc.her] penile virginity.</span>");
				}
				
			} else {
				descriptionSB.append(" [style.colourGood([npc.Name] [npc.has] retained [npc.her] penile virginity.)]");
			}
		}
		
		// Capacity:
		if (Capacity.getCapacityFromValue(viewedPenis.getOrificeUrethra().getStretchedCapacity()) != Capacity.ZERO_IMPENETRABLE) {
			if (isPlayer) {
				descriptionSB.append("<br/>Your cock's urethra has been loosened enough that it presents a ready orifice for penetration,"
						+ " [style.colourSex(and can be comfortably penetrated by "+ Capacity.getCapacityFromValue(viewedPenis.getOrificeUrethra().getStretchedCapacity()).getMaximumSizeComfortableWithLube().getDescriptor() + " cocks with sufficient lubrication.)]");
			} else {
				descriptionSB.append("<br/>[npc.Her] cock's urethra has been loosened enough that it presents a ready orifice for penetration,"
						+ " [style.colourSex(and can be comfortably penetrated by "+ Capacity.getCapacityFromValue(viewedPenis.getOrificeUrethra().getStretchedCapacity()).getMaximumSizeComfortableWithLube().getDescriptor() + " cocks with sufficient lubrication.)]");
			}

			switch (viewedPenis.getOrificeUrethra().getElasticity()) {
				case ZERO_UNYIELDING:
					descriptionSB.append(" It is [style.colourElasticity(extremely unyielding)],");
					break;
				case ONE_RIGID:
					descriptionSB.append(" It [style.colourElasticity(takes a huge amount of effort to stretch it out)],");
					break;
				case TWO_FIRM:
					descriptionSB.append(" It [style.colourElasticity(does not stretch very easily)],");
					break;
				case THREE_FLEXIBLE:
					descriptionSB.append(" It [style.colourElasticity(reluctantly stretches out)] when used as a sexual orifice,");
					break;
				case FOUR_LIMBER:
					descriptionSB.append(" It is [style.colourElasticity(somewhat resistant to being stretched out)],");
					break;
				case FIVE_STRETCHY:
					descriptionSB.append(" It [style.colourElasticity(stretches out fairly easily)],");
					break;
				case SIX_SUPPLE:
					descriptionSB.append(" It [style.colourElasticity(stretches out very easily)],");
					break;
				case SEVEN_ELASTIC:
					descriptionSB.append(" It is [style.colourElasticity(extremely elastic)],");
					break;
			}
			descriptionSB.append(" and after being used, it "+viewedPenis.getOrificeUrethra().getPlasticity().getDescription()+".");
			
			for(OrificeModifier om : OrificeModifier.values()) {
				if(owner.hasUrethraOrificeModifier(om)) {
					if(owner.isPlayer()) {
						switch(om) {
							case PUFFY:
								descriptionSB.append(" Your urethra has transformed into having a swollen, puffy rim.");
								break;
							case MUSCLE_CONTROL:
								descriptionSB.append(" A series of muscles lining the inside of your urethra, allowing you to expertly squeeze and grip down on any intruding object.");
								break;
							case RIBBED:
								descriptionSB.append(" The inside of your urethra is lined with sensitive, fleshy ribs, which grant you extra pleasure when stimulated.");
								break;
							case TENTACLED:
								descriptionSB.append(" Your urethra is filled with tiny little tentacles, which wriggle and squirm with a mind of their own.");
								break;
						}
					} else {
						switch(om) {
							case PUFFY:
								descriptionSB.append(" [npc.Her] urethra has transformed into having a swollen, puffy rim.");
								break;
							case MUSCLE_CONTROL:
								descriptionSB.append(" [npc.She] has a series of muscles lining the inside of [npc.her] urethra, allowing [npc.herHim] to expertly squeeze and grip down on any intruding object.");
								break;
							case RIBBED:
								descriptionSB.append(" The inside of [npc.her] urethra is lined with sensitive, fleshy ribs, which grant [npc.herHim] extra pleasure when stimulated.");
								break;
							case TENTACLED:
								descriptionSB.append(" [npc.Her] urethra is filled with tiny little tentacles, which wriggle and squirm with a mind of their own.");
								break;
						}
					}
				}
			}
		}

		if(viewedPenis.getType()!=PenisType.DILDO) {
			if (isPlayer && !owner.isUrethraVirgin()) {
				for(SexAreaPenetration pt : SexAreaPenetration.values()) {
					if(owner.getVirginityLoss(new SexType(SexParticipantType.NORMAL, SexAreaOrifice.URETHRA_PENIS, pt))!=null) {
						descriptionSB.append(" <span style='color:" + Colour.GENERIC_ARCANE.toWebHexString() + ";'>"+ owner.getVirginityLossDescription(new SexType(SexParticipantType.NORMAL, SexAreaOrifice.URETHRA_PENIS, pt)) + "</span>");
						break;
					}
				}
			}
			descriptionSB.append("<br/>");
		}
		

		if(viewedPenis.getType()!=PenisType.DILDO) {
			// Pubic Hair:
			if(Main.game.isPubicHairEnabled()) {
				if(owner.getPubicHairType().getType()==BodyCoveringType.BODY_HAIR_SCALES_ALLIGATOR) {
					switch(owner.getPubicHair()) {
						case ZERO_NONE:
							if (isPlayer) {
								descriptionSB.append(" There's no trace of any rough "+owner.getPubicHairType().getName(owner)+" around the base of your cock.");
							} else {
								descriptionSB.append(" There's no trace of any rough  "+owner.getPubicHairType().getName(owner)+" around the base of [npc.her] cock.");
							}
							break;
						case ONE_STUBBLE:
							if (isPlayer) {
								descriptionSB.append(" You have a small amount of rough "+owner.getPubicHairType().getFullDescription(owner, true)+" around the base of your cock.");
							} else {
								descriptionSB.append(" [npc.She] has a small amount of rough "+owner.getPubicHairType().getFullDescription(owner, true)+" around the base of [npc.her] cock.");
							}
							break;
						case TWO_MANICURED:
							if (isPlayer) {
								descriptionSB.append(" You have a rough patch of "+owner.getPubicHairType().getFullDescription(owner, true)+" around the base of your cock.");
							} else {
								descriptionSB.append(" [npc.She] has a rough patch of "+owner.getPubicHairType().getFullDescription(owner, true)+" around the base of [npc.her] cock.");
							}
							break;
						case THREE_TRIMMED:
							if (isPlayer) {
								descriptionSB.append(" You have a rough patch of "+owner.getPubicHairType().getFullDescription(owner, true)+" around the base of your cock.");
							} else {
								descriptionSB.append(" [npc.She] has a rough patch of "+owner.getPubicHairType().getFullDescription(owner, true)+" around the base of [npc.her] cock.");
							}
							break;
						case FOUR_NATURAL:
							if (isPlayer) {
								descriptionSB.append(" You have a natural amount of rough "+owner.getPubicHairType().getFullDescription(owner, true)+" around the base of your cock.");
							} else {
								descriptionSB.append(" [npc.She] has a natural amount of rough "+owner.getPubicHairType().getFullDescription(owner, true)+" around the base of [npc.her] cock.");
							}
							break;
						case FIVE_UNKEMPT:
							if (isPlayer) {
								descriptionSB.append(" You have an unkempt mass of rough "+owner.getPubicHairType().getFullDescription(owner, true)+" around the base of your cock.");
							} else {
								descriptionSB.append(" [npc.She] has an unkempt mass of rough "+owner.getPubicHairType().getFullDescription(owner, true)+" around the base of [npc.her] cock.");
							}
							break;
						case SIX_BUSHY:
							if (isPlayer) {
								descriptionSB.append(" You have a thick, rough mass of "+owner.getPubicHairType().getFullDescription(owner, true)+" around the base of your cock.");
							} else {
								descriptionSB.append(" [npc.She] has a thick, rough mass of "+owner.getPubicHairType().getFullDescription(owner, true)+" around the base of [npc.her] cock.");
							}
							break;
						case SEVEN_WILD:
							if (isPlayer) {
								descriptionSB.append(" You have a wild, rough mass of "+owner.getPubicHairType().getFullDescription(owner, true)+" around the base of your cock.");
							} else {
								descriptionSB.append(" [npc.She] has a wild, rough mass of "+owner.getPubicHairType().getFullDescription(owner, true)+" around the base of [npc.her] cock.");
							}
							break;
					}
				} else {
					switch(owner.getPubicHair()) {
						case ZERO_NONE:
							if (isPlayer) {
								descriptionSB.append(" There is no trace of any "+owner.getPubicHairType().getName(owner)+" around the base of your cock.");
							} else {
								descriptionSB.append(" There is no trace of any "+owner.getPubicHairType().getName(owner)+" around the base of [npc.her] cock.");
							}
							break;
						case ONE_STUBBLE:
							if (isPlayer) {
								descriptionSB.append(" You have a stubbly patch of "+owner.getPubicHairType().getFullDescription(owner, true)+" around the base of your cock.");
							} else {
								descriptionSB.append(" [npc.She] has a stubbly patch of "+owner.getPubicHairType().getFullDescription(owner, true)+" around the base of [npc.her] cock.");
							}
							break;
						case TWO_MANICURED:
							if (isPlayer) {
								descriptionSB.append(" You have a neat, manicured patch of "+owner.getPubicHairType().getFullDescription(owner, true)+" around the base of your cock.");
							} else {
								descriptionSB.append(" [npc.She] has a neat, manicured patch of "+owner.getPubicHairType().getFullDescription(owner, true)+" around the base of [npc.her] cock.");
							}
							break;
						case THREE_TRIMMED:
							if (isPlayer) {
								descriptionSB.append(" You have a trimmed patch of "+owner.getPubicHairType().getFullDescription(owner, true)+" around the base of your cock.");
							} else {
								descriptionSB.append(" [npc.She] has a trimmed patch of "+owner.getPubicHairType().getFullDescription(owner, true)+" around the base of [npc.her] cock.");
							}
							break;
						case FOUR_NATURAL:
							if (isPlayer) {
								descriptionSB.append(" You have a natural bush of "+owner.getPubicHairType().getFullDescription(owner, true)+" around the base of your cock.");
							} else {
								descriptionSB.append(" [npc.She] has a natural bush of "+owner.getPubicHairType().getFullDescription(owner, true)+" around the base of [npc.her] cock.");
							}
							break;
						case FIVE_UNKEMPT:
							if (isPlayer) {
								descriptionSB.append(" You have an unkempt bush of "+owner.getPubicHairType().getFullDescription(owner, true)+" around the base of your cock.");
							} else {
								descriptionSB.append(" [npc.She] has an unkempt bush of "+owner.getPubicHairType().getFullDescription(owner, true)+" around the base of [npc.her] cock.");
							}
							break;
						case SIX_BUSHY:
							if (isPlayer) {
								descriptionSB.append(" You have a thick, bushy mass of "+owner.getPubicHairType().getFullDescription(owner, true)+" around the base of your cock.");
							} else {
								descriptionSB.append(" [npc.She] has a thick, bushy mass of "+owner.getPubicHairType().getFullDescription(owner, true)+" around the base of [npc.her] cock.");
							}
							break;
						case SEVEN_WILD:
							if (isPlayer) {
								descriptionSB.append(" You have a wild, bushy mass of "+owner.getPubicHairType().getFullDescription(owner, true)+" around the base of your cock.");
							} else {
								descriptionSB.append(" [npc.She] has a wild, bushy mass of "+owner.getPubicHairType().getFullDescription(owner, true)+" around the base of [npc.her] cock.");
							}
							break;
					}
				}
			}
	
			descriptionSB.append("<br/>");
		}
		
		// Testicle size and cum production:

		if(viewedPenis.getType()!=PenisType.DILDO) {
			if(owner.isInternalTesticles()) {
				if (isPlayer) {
					descriptionSB.append(" Your [pc.ballsCount] [pc.balls+] have shifted to sit inside your body, leaving your [pc.cock] as the only visible part of your male reproductive organs.");
				} else {
					descriptionSB.append(" [npc.Her] [npc.ballsCount] [npc.balls+] have shifted to sit inside [npc.her] body, leaving [npc.her] [npc.cock] as the only visible part of [npc.her] male reproductive organs.");
				}
				
			} else {
				switch (viewedPenis.getTesticle().getTesticleSize()) {
					case ZERO_VESTIGIAL:
						if (isPlayer) {
							descriptionSB.append(" Your [pc.ballsCount] [pc.balls] are [npc.materialCompositionDescriptor] [pc.ballFullDescription(true)], and are so small that they're only just visible as tiny little mounds nestling beneath your [pc.cock].");
						} else {
							descriptionSB.append(" [npc.Her] [npc.ballsCount] [npc.balls] are [npc.materialCompositionDescriptor] [npc.ballFullDescription(true)], and are so small that they're only just visible as tiny little mounds nestling beneath [npc.her] [npc.cock].");
						}
						break;
					case ONE_TINY:
						if (isPlayer)
							descriptionSB.append(" Your [pc.ballsCount] [pc.balls] are [npc.materialCompositionDescriptor] [pc.ballFullDescription(true)], and are small enough to comfortably nestle underneath your [pc.cock].");
						else
							descriptionSB.append(" [npc.Her] [npc.ballsCount] [npc.balls] are [npc.materialCompositionDescriptor] [npc.ballFullDescription(true)], and are small enough to comfortably nestle underneath [npc.her] [npc.cock].");
						break;
					case TWO_AVERAGE:
						if (isPlayer)
							descriptionSB.append(" Your [pc.ballsCount] [pc.balls+] are [npc.materialCompositionDescriptor] [pc.ballFullDescription(true)], and dangle down beneath your [pc.cock].");
						else
							descriptionSB.append(" [npc.Her] [npc.ballsCount] [npc.balls+] are [npc.materialCompositionDescriptor] [npc.ballFullDescription(true)], and dangle down beneath [npc.her] [npc.cock].");
						break;
					case THREE_LARGE:
						if (isPlayer)
							descriptionSB.append(" Your [pc.ballsCount] [pc.balls+] are [npc.materialCompositionDescriptor] [pc.ballFullDescription(true)], and hang down beneath your [pc.cock].");
						else
							descriptionSB.append(" [npc.Her] [npc.ballsCount] [npc.balls+] are [npc.materialCompositionDescriptor] [npc.ballFullDescription(true)], and hang down beneath [npc.her] [npc.cock].");
						break;
					case FOUR_HUGE:
						if (isPlayer)
							descriptionSB.append(" Your [pc.ballsCount] [pc.balls+] are [npc.materialCompositionDescriptor] [pc.ballFullDescription(true)], and hang down beneath your [pc.cock].");
						else
							descriptionSB.append(" [npc.Her] [npc.ballsCount] [npc.balls+] are [npc.materialCompositionDescriptor] [npc.ballFullDescription(true)], and hang down beneath [npc.her] [npc.cock].");
						break;
					case FIVE_MASSIVE:
						if (isPlayer)
							descriptionSB.append(" Your [pc.ballsCount] [pc.balls+] are [npc.materialCompositionDescriptor] [pc.ballFullDescription(true)], and hang down beneath your [pc.cock].");
						else
							descriptionSB.append(" [npc.Her] [npc.ballsCount] [npc.balls+] are [npc.materialCompositionDescriptor] [npc.ballFullDescription(true)], and hang down beneath [npc.her] [npc.cock].");
						break;
					case SIX_GIGANTIC:
						if (isPlayer)
							descriptionSB.append(" Your [pc.ballsCount] [pc.balls+] are [npc.materialCompositionDescriptor] [pc.ballFullDescription(true)], and hang down beneath your [pc.cock].");
						else
							descriptionSB.append(" [npc.Her] [npc.ballsCount] [npc.balls+] are [npc.materialCompositionDescriptor] [npc.ballFullDescription(true)], and hang down beneath [npc.her] [npc.cock].");
						break;
					case SEVEN_ABSURD:
						if (isPlayer)
							descriptionSB.append(" Your [pc.ballsCount] [pc.balls+] are [npc.materialCompositionDescriptor] [pc.ballFullDescription(true)], and hang down beneath your [pc.cock].");
						else
							descriptionSB.append(" [npc.Her] [npc.ballsCount] [npc.balls+] are [npc.materialCompositionDescriptor] [npc.ballFullDescription(true)], and hang down beneath [npc.her] [npc.cock].");
						break;
				}
			}
			
			String cumName = "[npc.cum+]";
			if(owner.isPlayer()) {
				cumName = "[pc.cum+]";
			}
			switch (viewedPenis.getTesticle().getCumStorage()) {
				case ZERO_NONE:
					if (viewedPenis.getTesticle().getTesticleSize().getValue() > TesticleSize.TWO_AVERAGE.getValue()) {
						descriptionSB.append(" Despite their large size, they");
					} else {
						descriptionSB.append(" They");
					}
					descriptionSB.append(" don't produce any "+cumName+" at all.");
					break;
				case ONE_TRICKLE:
					if (viewedPenis.getTesticle().getTesticleSize().getValue() > TesticleSize.TWO_AVERAGE.getValue()) {
						descriptionSB.append(" Despite their large size, they");
					} else {
						descriptionSB.append(" They");
					}
					descriptionSB.append(" only produce a tiny trickle of "+cumName+".");
					break;
				case TWO_SMALL_AMOUNT:
					if (viewedPenis.getTesticle().getTesticleSize().getValue() > TesticleSize.THREE_LARGE.getValue()) {
						descriptionSB.append(" Despite their large size, they");
					} else {
						descriptionSB.append(" They");
					}
					descriptionSB.append(" only produce a small amount of "+cumName+".");
					break;
				case THREE_AVERAGE:
					if (viewedPenis.getTesticle().getTesticleSize().getValue() > TesticleSize.FOUR_HUGE.getValue()) {
						descriptionSB.append(" Despite their huge size, they only");
					} else {
						descriptionSB.append(" They");
					}
					descriptionSB.append(" produce an average amount of "+cumName+".");
					break;
				case FOUR_LARGE:
					if (viewedPenis.getTesticle().getTesticleSize().getValue() < TesticleSize.TWO_AVERAGE.getValue()) {
						descriptionSB.append(" Despite their small size, they");
					} else {
						descriptionSB.append(" They");
					}
					descriptionSB.append(" produce a large amount of "+cumName+".");
					break;
				case FIVE_HUGE:
					if (viewedPenis.getTesticle().getTesticleSize().getValue() < TesticleSize.TWO_AVERAGE.getValue()) {
						descriptionSB.append(" Despite their small size, they");
					} else {
						descriptionSB.append(" They");
					}
					descriptionSB.append(" produce a huge amount of "+cumName+".");
					break;
				case SIX_EXTREME:
					if (viewedPenis.getTesticle().getTesticleSize().getValue() < TesticleSize.TWO_AVERAGE.getValue()) {
						descriptionSB.append(" Despite their small size, they");
					} else {
						descriptionSB.append(" They");
					}
					descriptionSB.append(" produce an extreme amount of "+cumName+".");
					break;
				case SEVEN_MONSTROUS:
					if (viewedPenis.getTesticle().getTesticleSize().getValue() < TesticleSize.TWO_AVERAGE.getValue()) {
						descriptionSB.append(" Despite their small size, they");
					} else {
						descriptionSB.append(" They");
					}
					descriptionSB.append(" produce a monstrous amount of "+cumName+".");
					break;
			}
			
			if(owner.isPlayer()) {
				descriptionSB.append(" Your [pc.cum]");
			} else {
				descriptionSB.append(" [npc.Her] [npc.cum]");
			}
			
			switch(viewedPenis.getTesticle().getCum().getFlavour()) {
				case CHOCOLATE:
					descriptionSB.append(" tastes of chocolate.");
					break;
				case CUM:
					descriptionSB.append(", much to nobody's surprise, tastes like cum.");
					break;
				case GIRL_CUM:
					descriptionSB.append(" tastes of girl-cum.");
					break;
				case HONEY:
					descriptionSB.append(" tastes of honey.");
					break;
				case MILK:
					descriptionSB.append(" tastes like milk.");
					break;
				case MINT:
					descriptionSB.append(" tastes of mint.");
					break;
				case PINEAPPLE:
					descriptionSB.append(" tastes of pineapple.");
					break;
				case BUBBLEGUM:
					descriptionSB.append(" has the fruity taste of bubblegum.");
					break;
				case STRAWBERRY:
					descriptionSB.append(" tastes of strawberries.");
					break;
				case BEER:
					descriptionSB.append(" tastes like beer.");
					break;
				case VANILLA:
					descriptionSB.append(" tastes of vanilla.");
					break;
				case CHERRY:
					descriptionSB.append(" tastes of cherries.");
					break;
			}
			
			for(FluidModifier fm : FluidModifier.values()) {
				if(owner.hasCumModifier(fm)) {
					switch(fm) {
						case ADDICTIVE:
							descriptionSB.append(" It is highly addictive, and anyone who drinks too much will quickly become dependent on it.");
							break;
						case BUBBLING:
							descriptionSB.append(" It fizzes and bubbles like a carbonated drink.");
							break;
						case HALLUCINOGENIC:
							descriptionSB.append(" Anyone who ingests it suffers psychoactive effects, which can manifest in cum-related hallucinations or sensitivity to hypnotic suggestion.");
							break;
						case MUSKY:
							descriptionSB.append(" It has a strong, musky smell.");
							break;
						case SLIMY:
							descriptionSB.append(" It has a slimy, oily texture.");
							break;
						case STICKY:
							descriptionSB.append(" It's quite sticky, and is difficult to fully wash off without soap.");
							break;
						case VISCOUS:
							descriptionSB.append(" It's quite viscous, and slowly drips in large globules, much like thick treacle.");
							break;
						case ALCOHOLIC:
							descriptionSB.append(" It has a high alcohol content, and will get those who consume it very drunk.");
							break;
						case MINERAL_OIL:
							descriptionSB.append(" It contains mineral oils that deteriorate latex.");
							break;
					}
				}
			}
		}
		
		return UtilText.parse(owner, descriptionSB.toString());
	}

	public String getVaginaDescription(GameCharacter owner) {
		boolean isPlayer = owner.isPlayer();
		
		descriptionSB = new StringBuilder();

		Vagina viewedVagina = vagina;
		
		boolean hallucinating = false;
		if(Main.game.getPlayer().hasIngestedPsychoactiveFluidType(FluidTypeBase.GIRLCUM)) {
			viewedVagina = new Vagina(vagina.getType(),
					vagina.getRawLabiaSizeValue(),
					vagina.getClitoris().getRawClitorisSizeValue(),
					Wetness.SEVEN_DROOLING.getValue(),
					vagina.getOrificeVagina().getRawCapacityValue() *3,
					vagina.getOrificeVagina().getElasticity().getValue(),
					vagina.getOrificeVagina().getPlasticity().getValue(),
					vagina.getOrificeVagina().isVirgin());
			viewedVagina.setPierced(owner, vagina.isPierced());
			descriptionSB.append("<i style='color:"+Colour.PSYCHOACTIVE.toWebHexString()+";'>The psychoactive girlcum which you recently ingested is causing your view of "+(owner.isPlayer()?"your":"[npc.namePos]")+" pussy to be distorted!</i> ");
			hallucinating = true;
		}

		switch(owner.getGenitalArrangement()) {
			case CLOACA:
				descriptionSB.append(UtilText.parse(owner, "[style.italicsBestial([npc.Her] pussy is located within [npc.her] slit-like, front-facing cloaca.)] "));
				break;
			case CLOACA_BEHIND:
				descriptionSB.append(UtilText.parse(owner, "[style.italicsBestial([npc.Her] pussy is located within [npc.her] slit-like, rear-facing cloaca.)] "));
				break;
			case NORMAL:
			break;
		}
		
		if (isPlayer) {
			if (owner.hasPenis()) {
				descriptionSB.append("Beneath your [pc.penis], you have");
			} else {
				descriptionSB.append("Between your legs, you have");
			}
		} else {
			if (owner.hasPenis()) {
				descriptionSB.append("Beneath [npc.her] [npc.penis], [npc.she] has");
			} else {
				descriptionSB.append("Between [npc.her] legs, [npc.she] has");
			}
		}
		
		switch (viewedVagina.getType()) {
			case HUMAN:
					descriptionSB.append((viewedVagina.isPierced()?" a pierced,":" a")+" human pussy, with [npc.labiaSize], [npc.pussyPrimaryColour(true)] labia and [npc.pussySecondaryColour(true)] inner-walls.");
				break;
			case ANGEL:
					descriptionSB.append((viewedVagina.isPierced()?" a pierced,":" an")+" angelic pussy, with [npc.labiaSize], [npc.pussyPrimaryColour(true)] labia and [npc.pussySecondaryColour(true)] inner-walls.");
				break;
			case DEMON_COMMON:
				descriptionSB.append((viewedVagina.isPierced()?" a pierced,":" a")+" "+(!owner.isShortStature()?"demonic":"impish")+" pussy,"
							+ " with [npc.labiaSize], [npc.pussyPrimaryColour(true)] labia and [npc.pussySecondaryColour(true)] inner-walls.");
				break;
			case DOG_MORPH:
					descriptionSB.append((viewedVagina.isPierced()?" a pierced,":" a")+" canine pussy, with [npc.labiaSize], [npc.pussyPrimaryColour(true)] labia and [npc.pussySecondaryColour(true)] inner-walls.");
				break;
			case FOX_MORPH:
					descriptionSB.append((viewedVagina.isPierced()?" a pierced,":" a")+" vulpine pussy, with [npc.labiaSize], [npc.pussyPrimaryColour(true)] labia and [npc.pussySecondaryColour(true)] inner-walls.");
				break;
			case WOLF_MORPH:
					descriptionSB.append((viewedVagina.isPierced()?" a pierced,":" a")+" lupine pussy, with [npc.labiaSize], [npc.pussyPrimaryColour(true)] labia and [npc.pussySecondaryColour(true)] inner-walls.");
				break;
			case ALLIGATOR_MORPH:
					descriptionSB.append((viewedVagina.isPierced()?" a pierced,":" a")+" reptilian pussy, with [npc.labiaSize], [npc.pussyPrimaryColour(true)] labia and [npc.pussySecondaryColour(true)] inner-walls.");
				break;
			case CAT_MORPH:
					descriptionSB.append((viewedVagina.isPierced()?" a pierced,":" a")+" feline pussy, with [npc.labiaSize], [npc.pussyPrimaryColour(true)] labia and [npc.pussySecondaryColour(true)] inner-walls.");
				break;
			case COW_MORPH:
					descriptionSB.append((viewedVagina.isPierced()?" a pierced,":" a")+" bovine pussy, with [npc.labiaSize], [npc.pussyPrimaryColour(true)] labia and [npc.pussySecondaryColour(true)] inner-walls.");
				break;
			case SQUIRREL_MORPH:
					descriptionSB.append((viewedVagina.isPierced()?" a pierced,":" a")+" [npc.vaginaRace]'s pussy, with [npc.labiaSize], [npc.pussyPrimaryColour(true)] labia and [npc.pussySecondaryColour(true)] inner-walls.");
				break;
			case HORSE_MORPH:
					descriptionSB.append((viewedVagina.isPierced()?" a pierced,":" an")+" equine pussy, with [npc.labiaSize], [npc.pussyPrimaryColour(true)] labia and [npc.pussySecondaryColour(true)] inner-walls.");
				break;
			case REINDEER_MORPH:
					descriptionSB.append((viewedVagina.isPierced()?" a pierced,":" an")+" [npc.vaginaRace]'s pussy, with [npc.labiaSize], [npc.pussyPrimaryColour(true)] labia and [npc.pussySecondaryColour(true)] inner-walls.");
				break;
			case HARPY:
					descriptionSB.append((viewedVagina.isPierced()?" a pierced,":" an")+" avian pussy, with [npc.labiaSize], [npc.pussyPrimaryColour(true)] labia and [npc.pussySecondaryColour(true)] inner-walls.");
				break;
			case BAT_MORPH:
					descriptionSB.append((viewedVagina.isPierced()?" a pierced,":" a")+" [npc.vaginaRace]'s pussy, with [npc.labiaSize], [npc.pussyPrimaryColour(true)] labia and [npc.pussySecondaryColour(true)] inner-walls.");
				break;
			case RABBIT_MORPH:
					descriptionSB.append((viewedVagina.isPierced()?" a pierced,":" a")+" [npc.vaginaRace]'s pussy, with [npc.labiaSize], [npc.pussyPrimaryColour(true)] labia and [npc.pussySecondaryColour(true)] inner-walls.");
				break;
			case RAT_MORPH:
					descriptionSB.append((viewedVagina.isPierced()?" a pierced,":" a")+" [npc.vaginaRace]'s pussy, with [npc.labiaSize], [npc.pussyPrimaryColour(true)] labia and [npc.pussySecondaryColour(true)] inner-walls.");
				break;
			case NONE:
				break;
		}
		
		if(vagina.isBestial(owner)) {
			descriptionSB.append(" [style.colourBestial(As it is located on the lower, animalistic part of [npc.her] body, [npc.her] [npc.pussy] is no different to that of a feral [npc.vaginaRace]'s.)]");
		}
		
		if (isPlayer) {
			descriptionSB.append(" You have [pc.a_clitSize]"+(owner.getClitorisGirth()==PenisGirth.TWO_AVERAGE?"":", [pc.clitGirth]")
					+" clit, which measures [pc.clitSizeValue] in length.");
			
		} else {
			descriptionSB.append(" [npc.She] has [npc.a_clitSize]"+(owner.getClitorisGirth()==PenisGirth.TWO_AVERAGE?"":", [pc.clitGirth]")
					+" clit, which measures [npc.clitSizeValue] in length.");
		}
		
		for(PenetrationModifier pm : PenetrationModifier.values()) {
			if(owner.hasClitorisModifier(pm)) {
				switch(pm) {
					case RIBBED:
						descriptionSB.append(" It's lined with hard, fleshy ribs, which are sure to grant extra pleasure to any orifice that they penetrate.");
						break;
					case TENTACLED:
						descriptionSB.append(" A series of little tentacles coat its surface, which wriggle and squirm with a mind of their own.");
						break;
					case BARBED:
						descriptionSB.append(" Fleshy, backwards-facing barbs line its length.");
						break;
					case BLUNT:
						descriptionSB.append(" The tip curves around to a smooth surface.");
						break;
					case FLARED:
						descriptionSB.append(" The tip is wide and flared, like a horse's cock.");
						break;
					case KNOTTED:
						descriptionSB.append(" A fat knot sits at the base.");
						break;
					case PREHENSILE:
						descriptionSB.append(" It is prehensile, and can be manipulated and moved much like a primate's tail.");
						break;
					case SHEATHED:
						descriptionSB.append(" Its clitoral hood has transformed into a large sheath, into which [npc.her] [npc.clit] can retract, no matter how large it grows.");
						break;
					case TAPERED:
						descriptionSB.append(" The shaft is tapered, and gets thinner nearer to the head.");
						break;
					case VEINY:
						descriptionSB.append(" Large veins press out from its surface.");
						break;
				}
			}
		}
		
		// Virgin/capacity:
		if (viewedVagina.getOrificeVagina().isVirgin()) {
			if (isPlayer) {
				descriptionSB.append(" [style.colourSex(Within your " + Capacity.getCapacityFromValue(viewedVagina.getOrificeVagina().getStretchedCapacity()).getDescriptor()
						+ " [pc.pussy], your hymen is still intact, as it has never been penetrated before.)]"
						+ " [style.colourGood(You have retained your vaginal virginity.)]");
			} else if(!hallucinating) {
				descriptionSB.append(" [style.colourSex(Within [npc.her] " + Capacity.getCapacityFromValue(viewedVagina.getOrificeVagina().getStretchedCapacity()).getDescriptor()
						+ " [npc.pussy], [npc.her] hymen is still intact, as it has never been penetrated before.)]"
						+ " [style.colourGood([npc.She] has retained [npc.her] vaginal virginity.)]");
			}
			
		} else {
			if (isPlayer) {
				descriptionSB.append(" [style.colourSex(Your pussy is " + Capacity.getCapacityFromValue(viewedVagina.getOrificeVagina().getStretchedCapacity()).getDescriptor() + ", and can comfortably take "
						+ Capacity.getCapacityFromValue(viewedVagina.getOrificeVagina().getStretchedCapacity()).getMaximumSizeComfortableWithLube().getDescriptor() + " cocks with sufficient lubrication.)]");
				
			} else if(!hallucinating) {
				descriptionSB.append(" [style.colourSex([npc.Her] pussy is " + Capacity.getCapacityFromValue(viewedVagina.getOrificeVagina().getStretchedCapacity()).getDescriptor() + ", and can comfortably take "
						+ Capacity.getCapacityFromValue(viewedVagina.getOrificeVagina().getStretchedCapacity()).getMaximumSizeComfortableWithLube().getDescriptor() + " cocks with sufficient lubrication.)]");
			}

			boolean virginityLossFound = false;
			for(SexAreaPenetration pt : SexAreaPenetration.values()) {
				if(pt.isTakesVirginity()) {
					if(owner.getVirginityLoss(new SexType(SexParticipantType.NORMAL, SexAreaOrifice.VAGINA, pt))!=null) {
						descriptionSB.append(" <span style='color:" + Colour.GENERIC_ARCANE.toWebHexString() + ";'>"+ owner.getVirginityLossDescription(new SexType(SexParticipantType.NORMAL, SexAreaOrifice.VAGINA, pt)) + "</span>");
						virginityLossFound = true;
						break;
					}
				}
			}
			if(!virginityLossFound) {
				descriptionSB.append(" <span style='color:" + Colour.GENERIC_ARCANE.toWebHexString() + ";'>[npc.Name] [npc.has] lost [npc.her] virginity.</span>");
			}
		}
		
		// Wetness:
		switch (viewedVagina.getOrificeVagina().getWetness(owner)) {
			case ZERO_DRY:
				if (isPlayer) {
					descriptionSB.append(" It's [style.colourWetness(completely dry and never gets wet)], no matter how aroused you are.");
				} else {
					descriptionSB.append(" It's [style.colourWetness(completely dry and never gets wet)], no matter how aroused [npc.she] gets.");
				}
				break;
			case ONE_SLIGHTLY_MOIST:
				if (isPlayer) {
					descriptionSB.append(" It's [style.colourWetness(slightly moist)], and you need a huge amount of stimulation before you get wet.");
				} else {
					descriptionSB.append(" It's [style.colourWetness(slightly moist)], and [npc.she] needs a huge amount of stimulation before [npc.she] gets wet.");
				}
				break;
			case TWO_MOIST:
				if (isPlayer) {
					descriptionSB.append(" It's [style.colourWetness(moist)], but you still need a lot of stimulation before you get wet.");
				} else {
					descriptionSB.append(" It's [style.colourWetness(moist)], but [npc.she] still needs a lot of stimulation before [npc.she] gets wet.");
				}
				break;
			case THREE_WET:
				if (isPlayer) {
					descriptionSB.append(" It's of an [style.colourWetness(average wetness)], and you only need a small amount of foreplay before you're wet enough for a pleasurable penetration.");
				} else {
					descriptionSB.append(" It's of an [style.colourWetness(average wetness)], and [npc.she] only needs a small amount of foreplay before [npc.sheIs] wet enough for a pleasurable penetration.");
				}
				break;
			case FOUR_SLIMY:
				if (isPlayer) {
					descriptionSB.append(" It's always [style.colourWetness(slimy and wet)], and you're ready for penetration at a moment's notice.");
				} else {
					descriptionSB.append(" It's always [style.colourWetness(slimy and wet)], and [npc.sheIs] ready for penetration at a moment's notice.");
				}
				break;
			case FIVE_SLOPPY:
				if (isPlayer) {
					descriptionSB.append(" Its surface is always coated in [style.colourWetness(slimy moisture)], and within, your pussy is permanently [style.colourWetness(sloppy)] and practically begging to be fucked.");
				} else {
					descriptionSB.append(" Its surface is always coated in [style.colourWetness(slimy moisture)], and within, [npc.her] pussy is permanently [style.colourWetness(sloppy)] and practically begging to be fucked.");
				}
				break;
			case SIX_SOPPING_WET:
				if (isPlayer) {
					descriptionSB.append(" Your pussy is never anything less than [style.colourWetness(sopping wet)], and a trickle of your natural lubricant constantly dribbles from your slit.");
				} else {
					descriptionSB.append(" [npc.Her] pussy is never anything less than [style.colourWetness(sopping wet)], and a trickle of [npc.her] natural lubricant constantly dribbles from [npc.her] slit.");
				}
				break;
			case SEVEN_DROOLING:
				if (isPlayer) {
					descriptionSB.append(" Your pussy is [style.colourWetness(so wet that it audibly squelches with every step you take)], and a constant stream of juices flow from your inviting cunt.");
				} else {
					descriptionSB.append(" [npc.Her] pussy is [style.colourWetness(so wet that it audibly squelches with every step [npc.she] takes)], and a constant stream of juices flow from [npc.her] inviting cunt.");
				}
				break;
		}
		
		if(viewedVagina.getOrificeVagina().isSquirter()) {
			if (isPlayer) {
				descriptionSB.append(" You are a [style.colourArcane(squirter)], and [style.colourWetness(produce a considerable amount of female ejaculate)] each time you orgasm.");
			} else {
				descriptionSB.append(" [npc.She] is a [style.colourArcane(squirter)], and [style.colourWetness(produces a considerable amount of female ejaculate)] each time [npc.she] orgasms.");
			}
		}
		
		// Elasticity & plasticity:
		switch (viewedVagina.getOrificeVagina().getElasticity()) {
			case ZERO_UNYIELDING:
				descriptionSB.append(" It is [style.colourElasticity(extremely unyielding)],");
				break;
			case ONE_RIGID:
				descriptionSB.append(" It [style.colourElasticity(takes a huge amount of effort to stretch it out)],");
				break;
			case TWO_FIRM:
				descriptionSB.append(" It [style.colourElasticity(does not stretch very easily)],");
				break;
			case THREE_FLEXIBLE:
				descriptionSB.append(" It [style.colourElasticity(reluctantly stretches out)] when penetrated,");
				break;
			case FOUR_LIMBER:
				descriptionSB.append(" It is [style.colourElasticity(somewhat resistant to being stretched out)],");
				break;
			case FIVE_STRETCHY:
				descriptionSB.append(" It [style.colourElasticity(stretches out fairly easily)],");
				break;
			case SIX_SUPPLE:
				descriptionSB.append(" It [style.colourElasticity(stretches out very easily)],");
				break;
			case SEVEN_ELASTIC:
				descriptionSB.append(" It is [style.colourElasticity(extremely elastic)],");
				break;
		}
		descriptionSB.append(" and after being used, it "+viewedVagina.getOrificeVagina().getPlasticity().getDescription()+".");
		
		for(OrificeModifier om : OrificeModifier.values()) {
			if(owner.hasVaginaOrificeModifier(om)) {
				if(owner.isPlayer()) {
					switch(om) {
						case MUSCLE_CONTROL:
							descriptionSB.append(" You have a series of internal muscles lining the inside of your [pc.vagina], allowing you to expertly squeeze and grip down on any intruding object.");
							break;
						case PUFFY:
							descriptionSB.append(" Your labia have swollen up into big, extra-puffy pussy lips.");
							break;
						case RIBBED:
							descriptionSB.append(" The inside of your [pc.vagina] is lined with sensitive, fleshy ribs, which grant you extra pleasure when stimulated.");
							break;
						case TENTACLED:
							descriptionSB.append(" Your [pc.vagina] is filled with tiny little tentacles, which wriggle and squirm with a mind of their own.");
							break;
					}
				} else {
					switch(om) {
						case MUSCLE_CONTROL:
							descriptionSB.append(" [npc.She] has a series of internal muscles lining the inside of [npc.her] [npc.vagina], allowing [npc.herHim] to expertly squeeze and grip down on any intruding object.");
							break;
						case PUFFY:
							descriptionSB.append(" [npc.Her] labia have swollen up into big, extra-puffy pussy lips.");
							break;
						case RIBBED:
							descriptionSB.append(" The inside of [npc.her] [npc.vagina] is lined with sensitive, fleshy ribs, which grant [npc.herHim] extra pleasure when stimulated.");
							break;
						case TENTACLED:
							descriptionSB.append(" [npc.Her] [npc.vagina] is filled with tiny little tentacles, which wriggle and squirm with a mind of their own.");
							break;
					}
				}
			}
		}
		
		descriptionSB.append(" [npc.Her] [npc.girlcum]");
		
		switch(viewedVagina.getGirlcum().getFlavour()) {
			case CHOCOLATE:
				descriptionSB.append(" tastes of chocolate.");
				break;
			case CUM:
				descriptionSB.append(" tastes of salty cum.");
				break;
			case GIRL_CUM:
				descriptionSB.append(", much to nobody's surprise, tastes like ordinary girlcum.");
				break;
			case HONEY:
				descriptionSB.append(" tastes of honey.");
				break;
			case MILK:
				descriptionSB.append(" tastes like milk.");
				break;
			case MINT:
				descriptionSB.append(" tastes of mint.");
				break;
			case PINEAPPLE:
				descriptionSB.append(" tastes of pineapple.");
				break;
			case BUBBLEGUM:
				descriptionSB.append(" has the fruity taste of bubblegum.");
				break;
			case STRAWBERRY:
				descriptionSB.append(" tastes of strawberries.");
				break;
			case BEER:
				descriptionSB.append(" tastes like beer.");
				break;
			case VANILLA:
				descriptionSB.append(" tastes of vanilla.");
				break;
			case CHERRY:
				descriptionSB.append(" tastes of cherries.");
				break;
		}
		
		descriptionSB.append("<br/>");
		
		// Urethra:
		if (Capacity.getCapacityFromValue(viewedVagina.getOrificeUrethra().getStretchedCapacity()) != Capacity.ZERO_IMPENETRABLE) {
			if (isPlayer) {
				descriptionSB.append("Your vagina's urethra has been loosened enough that it presents a ready orifice for penetration,"
						+ " [style.colourSex(and can be comfortably penetrated by "
							+ Capacity.getCapacityFromValue(viewedVagina.getOrificeUrethra().getStretchedCapacity()).getMaximumSizeComfortableWithLube().getDescriptor() + " cocks with sufficient lubrication.)]");
			} else {
				descriptionSB.append("[npc.Her] vagina's urethra has been loosened enough that it presents a ready orifice for penetration,"
						+ " [style.colourSex(and can be comfortably penetrated by "
							+ Capacity.getCapacityFromValue(viewedVagina.getOrificeUrethra().getStretchedCapacity()).getMaximumSizeComfortableWithLube().getDescriptor() + " cocks with sufficient lubrication.)]");
			}
			
			// Elasticity & plasticity:
			switch (viewedVagina.getOrificeUrethra().getElasticity()) {
				case ZERO_UNYIELDING:
					descriptionSB.append(" It is [style.colourElasticity(extremely unyielding)],");
					break;
				case ONE_RIGID:
					descriptionSB.append(" It [style.colourElasticity(takes a huge amount of effort to stretch it out)],");
					break;
				case TWO_FIRM:
					descriptionSB.append(" It [style.colourElasticity(does not stretch very easily)],");
					break;
				case THREE_FLEXIBLE:
					descriptionSB.append(" It [style.colourElasticity(reluctantly stretches out)] when penetrated,");
					break;
				case FOUR_LIMBER:
					descriptionSB.append(" It is [style.colourElasticity(somewhat resistant to being stretched out)],");
					break;
				case FIVE_STRETCHY:
					descriptionSB.append(" It [style.colourElasticity(stretches out fairly easily)],");
					break;
				case SIX_SUPPLE:
					descriptionSB.append(" It [style.colourElasticity(stretches out very easily)],");
					break;
				case SEVEN_ELASTIC:
					descriptionSB.append(" It is [style.colourElasticity(extremely elastic)],");
					break;
			}
			descriptionSB.append(" and after being used, it "+viewedVagina.getOrificeUrethra().getPlasticity().getDescription()+".");
			
			for(OrificeModifier om : OrificeModifier.values()) {
				if(owner.hasVaginaUrethraOrificeModifier(om)) {
					if(owner.isPlayer()) {
						switch(om) {
							case PUFFY:
								descriptionSB.append(" Your urethra has transformed into having a swollen, puffy rim.");
								break;
							case MUSCLE_CONTROL:
								descriptionSB.append(" A series of muscles lining the inside of your urethra, allowing you to expertly squeeze and grip down on any intruding object.");
								break;
							case RIBBED:
								descriptionSB.append(" The inside of your urethra is lined with sensitive, fleshy ribs, which grant you extra pleasure when stimulated.");
								break;
							case TENTACLED:
								descriptionSB.append(" Your urethra is filled with tiny little tentacles, which wriggle and squirm with a mind of their own.");
								break;
						}
					} else {
						switch(om) {
							case PUFFY:
								descriptionSB.append(" [npc.Her] urethra has transformed into having a swollen, puffy rim.");
								break;
							case MUSCLE_CONTROL:
								descriptionSB.append(" [npc.She] has a series of muscles lining the inside of [npc.her] urethra, allowing [npc.herHim] to expertly squeeze and grip down on any intruding object.");
								break;
							case RIBBED:
								descriptionSB.append(" The inside of [npc.her] urethra is lined with sensitive, fleshy ribs, which grant [npc.herHim] extra pleasure when stimulated.");
								break;
							case TENTACLED:
								descriptionSB.append(" [npc.Her] urethra is filled with tiny little tentacles, which wriggle and squirm with a mind of their own.");
								break;
						}
					}
				}
			}
		}
		
		// Pubic Hair:
		if(Main.game.isPubicHairEnabled()) {
			if(owner.getPubicHairType().getType()==BodyCoveringType.BODY_HAIR_SCALES_ALLIGATOR) {
				switch(owner.getPubicHair()) {
					case ZERO_NONE:
						descriptionSB.append(" There's no trace of any rough  "+owner.getPubicHairType().getName(owner)+" around [npc.her] [npc.pussy].");
						break;
					case ONE_STUBBLE:
						descriptionSB.append(" [npc.SheHasFull] a small amount of rough "+owner.getPubicHairType().getFullDescription(owner, true)+" around [npc.her] [npc.pussy].");
						break;
					case TWO_MANICURED:
						descriptionSB.append(" [npc.SheHasFull] a rough patch of "+owner.getPubicHairType().getFullDescription(owner, true)+" around [npc.her] [npc.pussy].");
						break;
					case THREE_TRIMMED:
						descriptionSB.append(" [npc.SheHasFull] a rough patch of "+owner.getPubicHairType().getFullDescription(owner, true)+" around [npc.her] [npc.pussy].");
						break;
					case FOUR_NATURAL:
						descriptionSB.append(" [npc.SheHasFull] a natural amount of rough "+owner.getPubicHairType().getFullDescription(owner, true)+" around [npc.her] [npc.pussy].");
						break;
					case FIVE_UNKEMPT:
						descriptionSB.append(" [npc.SheHasFull] an unkempt mass of rough "+owner.getPubicHairType().getFullDescription(owner, true)+" around [npc.her] [npc.pussy].");
						break;
					case SIX_BUSHY:
						descriptionSB.append(" [npc.SheHasFull] a thick, rough mass of "+owner.getPubicHairType().getFullDescription(owner, true)+" around [npc.her] [npc.pussy].");
						break;
					case SEVEN_WILD:
						descriptionSB.append(" [npc.SheHasFull] a wild, rough mass of "+owner.getPubicHairType().getFullDescription(owner, true)+" around [npc.her] [npc.pussy].");
						break;
				}
			} else {
				switch(owner.getPubicHair()) {
					case ZERO_NONE:
						descriptionSB.append(" There is no trace of any "+owner.getPubicHairType().getName(owner)+" around [npc.her] [npc.pussy].");
						break;
					case ONE_STUBBLE:
						descriptionSB.append(" [npc.SheHasFull] a stubbly patch of "+owner.getPubicHairType().getFullDescription(owner, true)+" around [npc.her] [npc.pussy].");
						break;
					case TWO_MANICURED:
						descriptionSB.append(" [npc.SheHasFull] a neat, manicured patch of "+owner.getPubicHairType().getFullDescription(owner, true)+" around [npc.her] [npc.pussy].");
						break;
					case THREE_TRIMMED:
						descriptionSB.append(" [npc.SheHasFull] a trimmed patch of "+owner.getPubicHairType().getFullDescription(owner, true)+" around [npc.her] [npc.pussy].");
						break;
					case FOUR_NATURAL:
						descriptionSB.append(" [npc.SheHasFull] a natural bush of "+owner.getPubicHairType().getFullDescription(owner, true)+" around [npc.her] [npc.pussy].");
						break;
					case FIVE_UNKEMPT:
						descriptionSB.append(" [npc.SheHasFull] an unkempt bush of "+owner.getPubicHairType().getFullDescription(owner, true)+" around [npc.her] [npc.pussy].");
						break;
					case SIX_BUSHY:
						descriptionSB.append(" [npc.SheHasFull] a thick, bushy mass of "+owner.getPubicHairType().getFullDescription(owner, true)+" around [npc.her] [npc.pussy].");
						break;
					case SEVEN_WILD:
						descriptionSB.append(" [npc.SheHasFull] a wild, bushy mass of "+owner.getPubicHairType().getFullDescription(owner, true)+" around [npc.her] [npc.pussy].");
						break;
				}
			}
		}
		
		
		if (isPlayer && !owner.isVaginaUrethraVirgin()) {
			for(SexAreaPenetration pt : SexAreaPenetration.values()) {
				if(owner.getVirginityLoss(new SexType(SexParticipantType.NORMAL, SexAreaOrifice.URETHRA_VAGINA, pt))!=null) {
					descriptionSB.append(" <span style='color:" + Colour.GENERIC_ARCANE.toWebHexString() + ";'>"+ owner.getVirginityLossDescription(new SexType(SexParticipantType.NORMAL, SexAreaOrifice.URETHRA_VAGINA, pt))+ "</span>");
					break;
				}
			}
		}
		
		return UtilText.parse(owner, descriptionSB.toString());
	}

	public String getMoundDescription(GameCharacter owner) {
		descriptionSB = new StringBuilder();

		if (owner.isPlayer()) {
			descriptionSB.append("Between your legs, you have a genderless mound. Despite your lack of genitalia, it's still an incredibly sensitive area, and you can bring yourself to a quasi-orgasm by stroking it.");
		} else {
			descriptionSB.append("Between [npc.her] legs, [npc.she] has a genderless mound. Despite [npc.her] lack of genitalia, it's still an incredibly sensitive area, and [npc.she] can be brought to a quasi-orgasm by stimulating it.");
		}

		return UtilText.parse(owner, descriptionSB.toString());
	}
	
	public String getSexDetails(GameCharacter owner) {
		
		if(owner.getTotalTimesHadSex(Main.game.getPlayer()) >=1) {
			descriptionSB = new StringBuilder();
			
			// Amount of sex:
			
			descriptionSB.append(
					UtilText.parse(owner,
					"<p>"
						+ "<span style='color:" + Colour.GENERIC_SEX.toWebHexString() + ";'>"
							+ "You have had sex with [npc.name] "+Util.intToString(owner.getTotalTimesHadSex(Main.game.getPlayer()))+" "+(owner.getTotalTimesHadSex(Main.game.getPlayer())==1?"time.":"times.")
						+"</span>"));
			
			if(owner.getSexConsensualCount(Main.game.getPlayer())>=1) {
				if(owner.getSexConsensualCount(Main.game.getPlayer()) == owner.getTotalTimesHadSex(Main.game.getPlayer())) {
					if(owner.getTotalTimesHadSex(Main.game.getPlayer())==1) {
						descriptionSB.append(UtilText.parse(owner," The one time you had sex with [npc.herHim], it was consensual."));
					} else {
						descriptionSB.append(UtilText.parse(owner," All "+Util.intToString(owner.getTotalTimesHadSex(Main.game.getPlayer()))+" times were consensual."));
					}
					
				} else {
					if(owner.getTotalTimesHadSex(Main.game.getPlayer())==1) {
						descriptionSB.append(UtilText.parse(owner," The one time you had sex with [npc.herHim], it was consensual."));
					} else {
						descriptionSB.append(UtilText.parse(owner," "+Util.capitaliseSentence(Util.intToString(owner.getSexConsensualCount(Main.game.getPlayer())))+" of these times were consensual."));
					}
				}
			}
			if(owner.getSexAsSubCount(Main.game.getPlayer())>=1) {
				if(owner.getSexAsSubCount(Main.game.getPlayer()) == owner.getTotalTimesHadSex(Main.game.getPlayer())) {
					if(owner.getTotalTimesHadSex(Main.game.getPlayer())==1) {
						descriptionSB.append(UtilText.parse(owner," The one time you had sex with [npc.herHim], you were the dominant partner."));
					} else {
						descriptionSB.append(UtilText.parse(owner," All "+Util.intToString(owner.getTotalTimesHadSex(Main.game.getPlayer()))+" times you were the dominant partner."));
					}
					
				} else {
					if(owner.getTotalTimesHadSex(Main.game.getPlayer())==1) {
						descriptionSB.append(UtilText.parse(owner," The one time you had sex with [npc.herHim], you were the dominant partner."));
					} else {
						descriptionSB.append(UtilText.parse(owner," "+Util.capitaliseSentence(Util.intToString(owner.getSexAsSubCount(Main.game.getPlayer())))+" of these times you were the dominant partner."));
					}
				}
			}
			if(owner.getSexAsDomCount(Main.game.getPlayer())>=1) {
				if(owner.getSexAsDomCount(Main.game.getPlayer()) == owner.getTotalTimesHadSex(Main.game.getPlayer())) {
					if(owner.getTotalTimesHadSex(Main.game.getPlayer())==1) {
						descriptionSB.append(UtilText.parse(owner," The one time you had sex with [npc.herHim], you were the submissive partner."));
					} else {
						descriptionSB.append(UtilText.parse(owner," All "+Util.intToString(owner.getTotalTimesHadSex(Main.game.getPlayer()))+" times you were the submissive partner."));
					}
					
				} else {
					if(owner.getTotalTimesHadSex(Main.game.getPlayer())==1) {
						descriptionSB.append(UtilText.parse(owner," The one time you had sex with [npc.herHim], you were the submissive partner."));
					} else {
						descriptionSB.append(UtilText.parse(owner," "+Util.capitaliseSentence(Util.intToString(owner.getSexAsDomCount(Main.game.getPlayer())))+" of these times you were the submissive partner."));
					}
				}
			}
			descriptionSB.append("</p>");

			return UtilText.parse(owner, descriptionSB.toString());
		
		} else {
			return "";
		}
	}
	
	public String getPregnancyDetails(GameCharacter owner) {
		descriptionSB = new StringBuilder();
		
		// NPC is mother:
		
		if(owner.isVisiblyPregnant()) {
			GameCharacter father = owner.getPregnantLitter().getFather();
			if(father == null) {
				descriptionSB.append("<p><span style='color:" + Colour.GENERIC_ARCANE.toWebHexString() + ";'>From one of [npc.her] sexual encounters, [npc.name] has ended up getting impregnated.</span>");
			} else if(father.isPlayer()) {
				descriptionSB.append("<p><span style='color:" + Colour.GENERIC_ARCANE.toWebHexString() + ";'>From one of your sexual encounters, you've ended up impregnating [npc.name].</span>");
			} else {
				descriptionSB.append("<p><span style='color:" + Colour.GENERIC_ARCANE.toWebHexString() + ";'>From one of [npc.her] sexual encounters, [npc.name] has ended up getting impregnated by "+father.getName(true)+".</span>");
			}
			
			if(owner.hasStatusEffect(StatusEffect.PREGNANT_1)) {
				descriptionSB.append(" [npc.Her] belly is only a little swollen, as [npc.sheIs] only in the first stage of pregnancy.");
			} else if(owner.hasStatusEffect(StatusEffect.PREGNANT_2)) {
				descriptionSB.append(" [npc.Her] belly is noticeably swollen, as [npc.sheIs] well into [npc.her] pregnancy.");
			} else {
				descriptionSB.append(" [npc.Her] belly is massively swollen, and although [npc.sheIs] clearly ready for it, [npc.she] hasn't decided to give birth just yet.");
			}
			descriptionSB.append("</p>");
		}
		
		if(!owner.getLittersBirthed().isEmpty()) {
			descriptionSB.append("<p>"
				+ "<span style='color:" + Colour.GENERIC_ARCANE.toWebHexString() + ";'>"
						+ "[npc.Name] has given birth "+Util.intToString(owner.getLittersBirthed().size())+" "+(owner.getLittersBirthed().size()==1?"time":"times")+".</span>");
			
			for(Litter litter : owner.getLittersBirthed()) {
				if(litter.getFather() == null) {
					descriptionSB.append("<br/>On "+Units.date(litter.getConceptionDate(), Units.DateType.LONG)
							+", [npc.she] was impregnated, and then on "+Units.date(litter.getBirthDate(), Units.DateType.LONG)+", [npc.she] gave birth to ");
					
				} else if(litter.getFather().isPlayer()) {
					descriptionSB.append("<br/>On "+Units.date(litter.getConceptionDate(), Units.DateType.LONG)
							+", you impregnated [npc.herHim], and then on "+Units.date(litter.getBirthDate(), Units.DateType.LONG)+", [npc.she] gave birth to ");
					
				} else {
					descriptionSB.append("<br/>On "+Units.date(litter.getConceptionDate(), Units.DateType.LONG)+", "+litter.getFather().getName(true)
							+" impregnated [npc.herHim], and then on "+Units.date(litter.getBirthDate(), Units.DateType.LONG)+", [npc.she] gave birth to ");
				}
				
				descriptionSB.append(litter.getBirthedDescription());
				
				descriptionSB.append(".");
			}
			
			descriptionSB.append("</p>");
		}
		
		// NPC is father:
		
		if(Main.game.getPlayer().isVisiblyPregnant()) {
			for(PregnancyPossibility pp : Main.game.getPlayer().getPotentialPartnersAsMother()) {
				if(pp.getFather()!=null && pp.getFather().equals(owner)) {
					descriptionSB.append("<p>"
								+ "<span style='color:" + Colour.GENERIC_ARCANE.toWebHexString() + ";'>From one of your sexual encounters, you've been impregnated, and it's possible that [npc.name] is the father.</span>"
							+ "</p>");
					break;
				}
			}
		}
		
		if(!Main.game.getPlayer().getLittersBirthed().isEmpty()) {
			int fatheredLitters = 0;
			
			for(Litter litter : Main.game.getPlayer().getLittersBirthed()) {
				if(litter.getFather()!=null && litter.getFather().equals(owner)){
					fatheredLitters++;
				}
			}
			
			if(fatheredLitters!=0) {
				descriptionSB.append("<p>"
					+ "<span style='color:" + Colour.GENERIC_ARCANE.toWebHexString() + ";'>"
							+ "[npc.Name] is the father of some of your children, and has, in total, impregnated you "+Util.intToString(fatheredLitters)+" "+(fatheredLitters==1?"time":"times")+".</span>");
				
				for(Litter litter : Main.game.getPlayer().getLittersBirthed()) {
					if(litter.getFather()!=null && litter.getFather().equals(owner)){
						descriptionSB.append("<br/>On "+Units.date(litter.getConceptionDate(), Units.DateType.LONG)
								+", [npc.she] impregnated you, and then on "+Units.date(litter.getBirthDate(), Units.DateType.LONG)+", you gave birth to "+litter.getBirthedDescription()+".");
					}
				}
				
				descriptionSB.append("</p>");
			}
		}

		return UtilText.parse(owner, descriptionSB.toString());
	}

	/**
	 * Gender is calculated based on body femininity and sexual organs.
	 * 
	 * @return gender of this body
	 */
	public Gender getGender() {
		boolean hasPenis = penis.getType() != PenisType.NONE;
		boolean hasVagina = vagina.getType() != VaginaType.NONE;
		boolean hasBreasts = breast.hasBreasts();
		
		// Looks male:
		if (femininity <= Femininity.MASCULINE.getMaximumFemininity()) {
			if(hasPenis) {
				if(hasVagina) {
					if(hasBreasts) {
						return Gender.M_P_V_B_HERMAPHRODITE;
					} else {
						return Gender.M_P_V_HERMAPHRODITE;
					}
				} else {
					if(hasBreasts) {
						return Gender.M_P_B_BUSTYBOY;
					} else {
						return Gender.M_P_MALE;
					}
				}
			} else {
				if(hasVagina) {
					if(hasBreasts) {
						return Gender.M_V_B_BUTCH;
					} else {
						return Gender.M_V_CUNTBOY;
					}
				} else {
					if(hasBreasts) {
						return Gender.M_B_MANNEQUIN;
					} else {
						return Gender.M_MANNEQUIN;
					}
				}
			}

		// Looks androgynous:
		} else if (femininity <= Femininity.ANDROGYNOUS.getMaximumFemininity()){
			if(hasPenis) {
				if(hasVagina) {
					if(hasBreasts) {
						return Gender.N_P_V_B_HERMAPHRODITE;
					} else {
						return Gender.N_P_V_HERMAPHRODITE;
					}
				} else {
					if(hasBreasts) {
						return Gender.N_P_B_SHEMALE;
					} else {
						return Gender.N_P_TRAP;
					}
				}
			} else {
				if(hasVagina) {
					if(hasBreasts) {
						return Gender.N_V_B_TOMBOY;
					} else {
						return Gender.N_V_TOMBOY;
					}
				} else {
					if(hasBreasts) {
						return Gender.N_B_DOLL;
					} else {
						return Gender.N_NEUTER;
					}
				}
			}
		
		// Looks feminine:
		} else {
			if(hasPenis) {
				if(hasVagina) {
					if(hasBreasts) {
						return Gender.F_P_V_B_FUTANARI;
					} else {
						return Gender.F_P_V_FUTANARI;
					}
				} else {
					if(hasBreasts) {
						return Gender.F_P_B_SHEMALE;
					} else {
						return Gender.F_P_TRAP;
					}
				}
			} else {
				if(hasVagina) {
					if(hasBreasts) {
						return Gender.F_V_B_FEMALE;
					} else {
						return Gender.F_V_FEMALE;
					}
				} else {
					if(hasBreasts) {
						return Gender.F_B_DOLL;
					} else {
						return Gender.F_DOLL;
					}
				}
			}
			
		}
	}

	/**
	 * @return weight in kilograms
	 */
	public int getCalculatedWeight() { //TODO? Was this ever going to be used?

		// Weight = 0.4 * height
		int weight = (int) (height * 0.4f);

		// If harpy wings, your bones & muscles are really light, so *0.75
		if (arm.getType().equals(ArmType.HARPY)) {
			weight *= 0.75;
		}

		// If centaur, you have a horse body, so weight*0.6 + 250 (horses are 400kg at lightest and centaur bodies are smaller than horse's)
		if (leg.getLegConfiguration()==LegConfiguration.TAIL_LONG
				|| leg.getLegConfiguration()==LegConfiguration.ARACHNID
				|| leg.getLegConfiguration()==LegConfiguration.TAIL_LONG) {
			weight *= 0.6;
			weight += 250;
		}

		return weight;
	}

	/** Height is measured in cm. **/
	public int getHeightValue() {
		return height;
	}
	
	public Height getHeight() {
		return Height.getHeightFromInt(height);
	}

	/**
	 * Sets height attribute. Bound between 61 (2 feet) and 365 (12 feet).
	 * 
	 * @param height Value to set height to.
	 * @return True if height was changed.
	 */
	public boolean setHeight(int height) {
		if (this.height == height) {
			return false;
		}
		
		this.height = Math.max(Height.NEGATIVE_TWO_MIMIMUM.getMinimumValue(), Math.min(height, Height.SEVEN_COLOSSAL.getMaximumValue()));

		return true;
	}

	public boolean isFeminine() {
		return getFemininity() >= Femininity.ANDROGYNOUS.getMinimumFemininity();
	}
	
	public int getFemininity() {
		return femininity;
	}

	/**
	 * @param femininity
	 *            Value to set femininity to.
	 * @return True if femininity was changed.
	 */
	public boolean setFemininity(int femininity) {
		if (this.femininity == femininity) {
			return false;
		}
		
		if (femininity <= 0) {
			if (this.femininity == 0)
				return false;
			this.femininity = 0;
			return true;
		}
		if (femininity >= 100) {
			if (this.femininity == 100)
				return false;
			this.femininity = 100;
			return true;
		}
		
		this.femininity = femininity;
		return true;
	}
	
	public BodyHair getPubicHair() {
		return pubicHair;
	}
	
	public void setPubicHair(BodyHair pubicHair) {
		this.pubicHair = pubicHair;
	}
	
	public int getBodySize() {
		return bodySize;
	}

	/**
	 * @param bodySize Value to set femininity to.
	 * @return True if bodySize was changed.
	 */
	public boolean setBodySize(int bodySize) {
		if (this.bodySize == bodySize) {
			return false;
		}
		
		if (bodySize <= 0) {
			if (this.bodySize == 0)
				return false;
			this.bodySize = 0;
			return true;
		}
		if (bodySize >= 100) {
			if (this.bodySize == 100)
				return false;
			this.bodySize = 100;
			return true;
		}
		
		this.bodySize = bodySize;
		return true;
	}
	
	public int getMuscle() {
		return muscle;
	}

	/**
	 * @param muscle Value to set muscle to.
	 * @return True if muscle was changed.
	 */
	public boolean setMuscle(int muscle) {
		if (this.muscle == muscle) {
			return false;
		}
		
		if (muscle <= 0) {
			if (this.muscle == 0)
				return false;
			this.muscle = 0;
			return true;
		}
		if (muscle >= 100) {
			if (this.muscle == 100)
				return false;
			this.muscle = 100;
			return true;
		}
		
		this.muscle = muscle;
		return true;
	}
	
	public BodyShape getBodyShape() {
		return BodyShape.valueOf(Muscle.valueOf(getMuscle()), BodySize.valueOf(getBodySize()));
	}
	
	public BodyMaterial getBodyMaterial() {
		return bodyMaterial;
	}
	
	public boolean setBodyMaterial(BodyMaterial bodyMaterial) {
		if(this.bodyMaterial == bodyMaterial) {
			return false;
		}
		
		addDiscoveredBodyCoveringsFromMaterial(bodyMaterial);
		
		this.bodyMaterial = bodyMaterial;
		
		return true;
	}

	public GenitalArrangement getGenitalArrangement() {
		return genitalArrangement;
	}

	public void setGenitalArrangement(GenitalArrangement genitalArrangement) {
		this.genitalArrangement = genitalArrangement;
	}

	public boolean isPiercedStomach() {
		return piercedStomach;
	}

	public void setPiercedStomach(boolean piercedStomach) {
		this.piercedStomach = piercedStomach;
	}

	public Map<BodyCoveringType, Covering> getCoverings() {
		return coverings;
	}

	public Set<BodyCoveringType> getBodyCoveringTypesDiscovered() {
		return coveringsDiscovered;
	}
	
	/**
	 * Updates this body's covering to more realistic values.
	 * @param updateEyes If true, human eyes will be set to non-heterochromatic.
	 * @param updateHair If true, human hair and body hair will be set to unpatterned.
	 * @param updateBodyHairColours If true, the colour of all body hair values will be set to the matching colour of the body's head hair.
	 * @param updateSkin If true, all values for anus, lips, vagina, nipples, and penis will be set to the matching colour of the body's skin.
	 */
	public void updateCoverings(boolean updateEyes, boolean updateHair, boolean updateBodyHairColours, boolean updateSkin) {
		
//		if(this.getBodyMaterial()==BodyMaterial.SLIME) {
//			coverings.put(BodyCoveringType.SLIME, new Covering
//					(BodyCoveringType.SLIME, coverings.get(BodyCoveringType.SLIME).getPattern(), coverings.get(BodyCoveringType.SLIME).getPrimaryColour(), false, coverings.get(BodyCoveringType.SLIME).getPrimaryColour(), false));
//		}
		
		// Make eyes normal:
		if(updateEyes) {
			coverings.put(BodyCoveringType.EYE_HUMAN, new Covering
					(BodyCoveringType.EYE_HUMAN, CoveringPattern.EYE_IRISES, coverings.get(BodyCoveringType.EYE_HUMAN).getPrimaryColour(), false, coverings.get(BodyCoveringType.EYE_HUMAN).getPrimaryColour(), false));
		}
		
		// Make hair non-highlighted:
		if(updateHair) {
			coverings.put(BodyCoveringType.HAIR_HUMAN, new Covering
					(BodyCoveringType.HAIR_HUMAN, CoveringPattern.NONE, coverings.get(BodyCoveringType.HAIR_HUMAN).getPrimaryColour(), false, coverings.get(BodyCoveringType.HAIR_HUMAN).getPrimaryColour(), false));
	
			coverings.put(BodyCoveringType.BODY_HAIR_HUMAN, new Covering
					(BodyCoveringType.BODY_HAIR_HUMAN, CoveringPattern.NONE, coverings.get(BodyCoveringType.BODY_HAIR_HUMAN).getPrimaryColour(), false, coverings.get(BodyCoveringType.BODY_HAIR_HUMAN).getPrimaryColour(), false));
			
		}
		
		if(updateBodyHairColours) {
			for(Race r : Race.values()) {
				switch(r) {
					case NONE:
						break;
					case ANGEL:
						coverings.put(BodyCoveringType.BODY_HAIR_ANGEL, new Covering(BodyCoveringType.BODY_HAIR_ANGEL, coverings.get(BodyCoveringType.HAIR_ANGEL).getPrimaryColour()));
						break;
					case CAT_MORPH:
						coverings.put(BodyCoveringType.BODY_HAIR_FELINE_FUR, new Covering(BodyCoveringType.BODY_HAIR_FELINE_FUR, coverings.get(BodyCoveringType.HAIR_FELINE_FUR).getPrimaryColour()));
						break;
					case DEMON:
						coverings.put(BodyCoveringType.BODY_HAIR_DEMON, new Covering(BodyCoveringType.BODY_HAIR_DEMON, coverings.get(BodyCoveringType.HAIR_DEMON).getPrimaryColour()));
						break;
					case DOG_MORPH:
						coverings.put(BodyCoveringType.BODY_HAIR_CANINE_FUR, new Covering(BodyCoveringType.BODY_HAIR_CANINE_FUR, coverings.get(BodyCoveringType.HAIR_CANINE_FUR).getPrimaryColour()));
						break;
					case FOX_MORPH:
						coverings.put(BodyCoveringType.BODY_HAIR_FOX_FUR, new Covering(BodyCoveringType.BODY_HAIR_FOX_FUR, coverings.get(BodyCoveringType.HAIR_FOX_FUR).getPrimaryColour()));
						break;
					case ALLIGATOR_MORPH:
						coverings.put(BodyCoveringType.ALLIGATOR_SCALES, new Covering(BodyCoveringType.ALLIGATOR_SCALES, coverings.get(BodyCoveringType.ALLIGATOR_SCALES).getPrimaryColour()));
						break;
					case HARPY:
						coverings.put(BodyCoveringType.BODY_HAIR_HARPY, new Covering(BodyCoveringType.BODY_HAIR_HARPY, coverings.get(BodyCoveringType.HAIR_HARPY).getPrimaryColour()));
						break;
					case HORSE_MORPH:
						coverings.put(BodyCoveringType.BODY_HAIR_HORSE_HAIR, new Covering(BodyCoveringType.BODY_HAIR_HORSE_HAIR, coverings.get(BodyCoveringType.HAIR_HORSE_HAIR).getPrimaryColour()));
						break;
					case REINDEER_MORPH:
						coverings.put(BodyCoveringType.BODY_HAIR_REINDEER_HAIR, new Covering(BodyCoveringType.BODY_HAIR_REINDEER_HAIR, coverings.get(BodyCoveringType.HAIR_REINDEER_FUR).getPrimaryColour()));
						break;
					case COW_MORPH:
						coverings.put(BodyCoveringType.BODY_HAIR_BOVINE_FUR, new Covering(BodyCoveringType.BODY_HAIR_BOVINE_FUR, coverings.get(BodyCoveringType.HAIR_BOVINE_FUR).getPrimaryColour()));
						break;
					case HUMAN:
						coverings.put(BodyCoveringType.BODY_HAIR_HUMAN, new Covering(BodyCoveringType.BODY_HAIR_HUMAN, coverings.get(BodyCoveringType.HAIR_HUMAN).getPrimaryColour()));
						break;
					case SLIME:
						coverings.put(BodyCoveringType.SLIME_BODY_HAIR, new Covering(BodyCoveringType.SLIME_BODY_HAIR, coverings.get(BodyCoveringType.SLIME_HAIR).getPrimaryColour()));
						break;
					case SQUIRREL_MORPH:
						coverings.put(BodyCoveringType.BODY_HAIR_SQUIRREL_FUR, new Covering(BodyCoveringType.BODY_HAIR_SQUIRREL_FUR, coverings.get(BodyCoveringType.HAIR_SQUIRREL_FUR).getPrimaryColour()));
						break;
					case RAT_MORPH:
						coverings.put(BodyCoveringType.BODY_HAIR_RAT_FUR, new Covering(BodyCoveringType.BODY_HAIR_RAT_FUR, coverings.get(BodyCoveringType.HAIR_RAT_FUR).getPrimaryColour()));
						break;
					case RABBIT_MORPH:
						coverings.put(BodyCoveringType.BODY_HAIR_RABBIT_FUR, new Covering(BodyCoveringType.BODY_HAIR_RABBIT_FUR, coverings.get(BodyCoveringType.HAIR_RABBIT_FUR).getPrimaryColour()));
						break;
					case BAT_MORPH:
						coverings.put(BodyCoveringType.BODY_HAIR_BAT_FUR, new Covering(BodyCoveringType.BODY_HAIR_BAT_FUR, coverings.get(BodyCoveringType.HAIR_BAT_FUR).getPrimaryColour()));
						break;
					case WOLF_MORPH:
						coverings.put(BodyCoveringType.BODY_HAIR_LYCAN_FUR, new Covering(BodyCoveringType.BODY_HAIR_LYCAN_FUR, coverings.get(BodyCoveringType.HAIR_LYCAN_FUR).getPrimaryColour()));
						break;
					case ELEMENTAL:
						break;
				}
			}
		}
		
		// Make all orifice colours the same as their surroundings:
		if(updateSkin) {
			if(this.getBodyMaterial()==BodyMaterial.SLIME) {
				coverings.put(BodyCoveringType.SLIME_ANUS, new Covering(BodyCoveringType.SLIME_ANUS, CoveringPattern.ORIFICE_ANUS,
						coverings.get(BodyCoveringType.SLIME).getPrimaryColour(), false, coverings.get(BodyCoveringType.SLIME).getPrimaryColour(), false));
				
				coverings.put(BodyCoveringType.SLIME_NIPPLES, new Covering(BodyCoveringType.SLIME_NIPPLES, CoveringPattern.ORIFICE_NIPPLE,
						coverings.get(BodyCoveringType.SLIME).getPrimaryColour(), false, coverings.get(BodyCoveringType.SLIME).getPrimaryColour(), false));
				
				coverings.put(BodyCoveringType.SLIME_MOUTH, new Covering(BodyCoveringType.SLIME_MOUTH, CoveringPattern.ORIFICE_MOUTH,
						coverings.get(BodyCoveringType.SLIME).getPrimaryColour(), false, coverings.get(BodyCoveringType.SLIME).getPrimaryColour(), false));
				
				coverings.put(BodyCoveringType.SLIME_VAGINA, new Covering(BodyCoveringType.SLIME_VAGINA, CoveringPattern.ORIFICE_VAGINA,
						coverings.get(BodyCoveringType.SLIME).getPrimaryColour(), false, coverings.get(BodyCoveringType.SLIME).getPrimaryColour(), false));

				coverings.put(BodyCoveringType.SLIME_PENIS, new Covering(BodyCoveringType.SLIME_PENIS, CoveringPattern.NONE,
						coverings.get(BodyCoveringType.SLIME).getPrimaryColour(), false, coverings.get(BodyCoveringType.SLIME).getPrimaryColour(), false));
				
			} else {
				updateAnusColouring();

				updateNippleColouring();
				
				updateNippleCrotchColouring();

				updateMouthColouring();

				updateVaginaColouring();
				
				updatePenisColouring();
			}
		}
	}
	
	public void updateAnusColouring() {
		switch(ass.getType().getRace()) {
			case ANGEL:
				coverings.put(BodyCoveringType.ANUS, new Covering(BodyCoveringType.ANUS, CoveringPattern.ORIFICE_ANUS, coverings.get(BodyCoveringType.ANGEL).getPrimaryColour(), false, Colour.ORIFICE_INTERIOR, false));
				break;
			case DEMON:
				coverings.put(BodyCoveringType.ANUS, new Covering(BodyCoveringType.ANUS, CoveringPattern.ORIFICE_ANUS, coverings.get(BodyCoveringType.DEMON_COMMON).getPrimaryColour(), false, Colour.ORIFICE_INTERIOR, false));
				break;
			default:
				coverings.put(BodyCoveringType.ANUS, new Covering(BodyCoveringType.ANUS, CoveringPattern.ORIFICE_ANUS, coverings.get(BodyCoveringType.HUMAN).getPrimaryColour(), false, Colour.ORIFICE_INTERIOR, false));
				break;
		}
	}
	
	public void updateNippleColouring() {
		switch(breast.getType().getRace()) {
			case ANGEL:
				coverings.put(BodyCoveringType.NIPPLES, new Covering(BodyCoveringType.NIPPLES, CoveringPattern.ORIFICE_NIPPLE, coverings.get(BodyCoveringType.ANGEL).getPrimaryColour(), false, Colour.ORIFICE_INTERIOR, false));
				break;
			case DEMON:
				coverings.put(BodyCoveringType.NIPPLES, new Covering(BodyCoveringType.NIPPLES, CoveringPattern.ORIFICE_NIPPLE, coverings.get(BodyCoveringType.DEMON_COMMON).getPrimaryColour(), false, Colour.ORIFICE_INTERIOR, false));
				break;
			default:
				coverings.put(BodyCoveringType.NIPPLES, new Covering(BodyCoveringType.NIPPLES, CoveringPattern.ORIFICE_NIPPLE, coverings.get(BodyCoveringType.HUMAN).getPrimaryColour(), false, Colour.ORIFICE_INTERIOR, false));
				break;
		}
	}
	
	public void updateNippleCrotchColouring() {
		switch(breastCrotch.getType().getRace()) {
			case ANGEL:
				coverings.put(BodyCoveringType.NIPPLES_CROTCH, new Covering(BodyCoveringType.NIPPLES_CROTCH, CoveringPattern.ORIFICE_NIPPLE, coverings.get(BodyCoveringType.ANGEL).getPrimaryColour(), false, Colour.ORIFICE_INTERIOR, false));
				break;
			case DEMON:
				coverings.put(BodyCoveringType.NIPPLES_CROTCH, new Covering(BodyCoveringType.NIPPLES_CROTCH, CoveringPattern.ORIFICE_NIPPLE, coverings.get(BodyCoveringType.DEMON_COMMON).getPrimaryColour(), false, Colour.ORIFICE_INTERIOR, false));
				break;
			default:
				coverings.put(BodyCoveringType.NIPPLES_CROTCH, new Covering(BodyCoveringType.NIPPLES_CROTCH, CoveringPattern.ORIFICE_NIPPLE, coverings.get(BodyCoveringType.HUMAN).getPrimaryColour(), false, Colour.ORIFICE_INTERIOR, false));
				break;
		}
	}
	
	public void updateMouthColouring() {
		switch(face.getType().getRace()) {
			case ANGEL:
				coverings.put(BodyCoveringType.MOUTH, new Covering(BodyCoveringType.MOUTH, CoveringPattern.ORIFICE_MOUTH, coverings.get(BodyCoveringType.ANGEL).getPrimaryColour(), false, Colour.ORIFICE_INTERIOR, false));
				break;
			case DEMON:
				coverings.put(BodyCoveringType.MOUTH, new Covering(BodyCoveringType.MOUTH, CoveringPattern.ORIFICE_MOUTH, coverings.get(BodyCoveringType.DEMON_COMMON).getPrimaryColour(), false, Colour.ORIFICE_INTERIOR, false));
				break;
			default:
				coverings.put(BodyCoveringType.MOUTH, new Covering(BodyCoveringType.MOUTH, CoveringPattern.ORIFICE_MOUTH, coverings.get(BodyCoveringType.HUMAN).getPrimaryColour(), false, Colour.ORIFICE_INTERIOR, false));
				break;
		}
	}
	
	public void updateVaginaColouring() {
		if(vagina.getType().getRace()!=null) {
			switch(vagina.getType().getRace()) {
				case ANGEL:
					coverings.put(BodyCoveringType.VAGINA, new Covering(BodyCoveringType.VAGINA, CoveringPattern.ORIFICE_VAGINA, coverings.get(BodyCoveringType.ANGEL).getPrimaryColour(), false, Colour.ORIFICE_INTERIOR, false));
					break;
				case DEMON:
					coverings.put(BodyCoveringType.VAGINA, new Covering(BodyCoveringType.VAGINA, CoveringPattern.ORIFICE_VAGINA, coverings.get(BodyCoveringType.DEMON_COMMON).getPrimaryColour(), false, Colour.ORIFICE_INTERIOR, false));
					break;
				default:
					coverings.put(BodyCoveringType.VAGINA, new Covering(BodyCoveringType.VAGINA, CoveringPattern.ORIFICE_VAGINA, coverings.get(BodyCoveringType.HUMAN).getPrimaryColour(), false, Colour.ORIFICE_INTERIOR, false));
					break;
			}
		} else {
			switch(getRace()) {
				case ANGEL:
					coverings.put(BodyCoveringType.VAGINA, new Covering(BodyCoveringType.VAGINA, CoveringPattern.ORIFICE_VAGINA, coverings.get(BodyCoveringType.ANGEL).getPrimaryColour(), false, Colour.ORIFICE_INTERIOR, false));
					break;
				case DEMON:
					coverings.put(BodyCoveringType.VAGINA, new Covering(BodyCoveringType.VAGINA, CoveringPattern.ORIFICE_VAGINA, coverings.get(BodyCoveringType.DEMON_COMMON).getPrimaryColour(), false, Colour.ORIFICE_INTERIOR, false));
					break;
				default:
					coverings.put(BodyCoveringType.VAGINA, new Covering(BodyCoveringType.VAGINA, CoveringPattern.ORIFICE_VAGINA, coverings.get(BodyCoveringType.HUMAN).getPrimaryColour(), false, Colour.ORIFICE_INTERIOR, false));
					break;
			}
		}
	}
	
	public void updatePenisColouring() {
		if(penis.getType().getRace()!=null) {
			switch(penis.getType().getRace()) {
				case ANGEL:
					coverings.put(BodyCoveringType.PENIS, new Covering(BodyCoveringType.PENIS, CoveringPattern.NONE, coverings.get(BodyCoveringType.ANGEL).getPrimaryColour(), false, Colour.ORIFICE_INTERIOR, false));
					break;
				case DEMON:
					coverings.put(BodyCoveringType.PENIS, new Covering(BodyCoveringType.PENIS, CoveringPattern.NONE, coverings.get(BodyCoveringType.DEMON_COMMON).getPrimaryColour(), false, Colour.ORIFICE_INTERIOR, false));
					break;
				case DOG_MORPH: case WOLF_MORPH:
					coverings.put(BodyCoveringType.PENIS, new Covering(BodyCoveringType.PENIS, CoveringPattern.NONE, Colour.SKIN_RED, false, Colour.ORIFICE_INTERIOR, false));
					break;
				default:
					coverings.put(BodyCoveringType.PENIS, new Covering(BodyCoveringType.PENIS, CoveringPattern.NONE, coverings.get(BodyCoveringType.HUMAN).getPrimaryColour(), false, Colour.ORIFICE_INTERIOR, false));
					break;
			}
		} else {
			switch(getRace()) {
				case ANGEL:
					coverings.put(BodyCoveringType.PENIS, new Covering(BodyCoveringType.PENIS, CoveringPattern.NONE, coverings.get(BodyCoveringType.ANGEL).getPrimaryColour(), false, Colour.ORIFICE_INTERIOR, false));
					break;
				case DEMON:
					coverings.put(BodyCoveringType.PENIS, new Covering(BodyCoveringType.PENIS, CoveringPattern.NONE, coverings.get(BodyCoveringType.DEMON_COMMON).getPrimaryColour(), false, Colour.ORIFICE_INTERIOR, false));
					break;
				case DOG_MORPH: case WOLF_MORPH:
					coverings.put(BodyCoveringType.PENIS, new Covering(BodyCoveringType.PENIS, CoveringPattern.NONE, Colour.SKIN_RED, false, Colour.ORIFICE_INTERIOR, false));
					break;
				default:
					coverings.put(BodyCoveringType.PENIS, new Covering(BodyCoveringType.PENIS, CoveringPattern.NONE, coverings.get(BodyCoveringType.HUMAN).getPrimaryColour(), false, Colour.ORIFICE_INTERIOR, false));
					break;
			}
		}
	}
	
	public boolean isAbleToFlyFromArms() {
		if(this.getBodyMaterial()==BodyMaterial.SLIME || this.getLeg().getLegConfiguration().getMinimumWingSizeForFlight().getValue()>WingSize.THREE_LARGE.getValue()) {
			return false;
		}
		return arm.getType().allowsFlight();
	}
	public boolean isAbleToFlyFromWings() {
		if(this.getBodyMaterial()==BodyMaterial.SLIME) {
			return false;
		}
		return wing.getType().allowsFlight() && wing.getSize().getValue()>=this.getLeg().getLegConfiguration().getMinimumWingSizeForFlight().getValue();
	}

	public boolean isTakesAfterMother() {
		return takesAfterMother;
	}

	public void setTakesAfterMother(boolean takesAfterMother) {
		this.takesAfterMother = takesAfterMother;
	}

}
