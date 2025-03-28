/*
 * MCreator (https://mcreator.net/)
 * Copyright (C) 2020 Pylo and contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package net.mcreator.element.types;

import net.mcreator.element.BaseType;
import net.mcreator.element.GeneratableElement;
import net.mcreator.element.parts.*;
import net.mcreator.element.parts.Particle;
import net.mcreator.element.parts.procedure.Procedure;
import net.mcreator.element.parts.procedure.StringListProcedure;
import net.mcreator.element.types.interfaces.*;
import net.mcreator.generator.mapping.NameMapper;
import net.mcreator.minecraft.MCItem;
import net.mcreator.minecraft.MinecraftImageGenerator;
import net.mcreator.ui.workspace.resources.TextureType;
import net.mcreator.workspace.Workspace;
import net.mcreator.workspace.elements.ModElement;
import net.mcreator.workspace.references.ModElementReference;
import net.mcreator.workspace.references.TextureReference;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;

@SuppressWarnings("unused") public class Dimension extends GeneratableElement
		implements ICommonType, ITabContainedElement, ISpecialInfoHolder, IMCItemProvider, IPOIProvider {

	@ModElementReference public List<BiomeEntry> biomesInDimension;

	public String worldGenType;

	public MItemBlock mainFillerBlock;
	public MItemBlock fluidBlock;
	public int seaLevel;
	public boolean generateOreVeins;
	public boolean generateAquifers;
	public int horizontalNoiseSize;
	public int verticalNoiseSize;

	public String defaultEffects;
	public boolean useCustomEffects;
	public boolean hasClouds;
	public double cloudHeight;
	public String skyType;
	public Color airColor;
	public boolean sunHeightAffectsFog;
	public boolean canRespawnHere;
	public boolean hasFog;
	public double ambientLight;
	public boolean doesWaterVaporize;
	public boolean hasFixedTime;
	public int fixedTimeValue;
	public double coordinateScale;
	public String infiniburnTag;

	public boolean bedWorks;
	public boolean hasSkyLight;
	public boolean imitateOverworldBehaviour;
	public boolean piglinSafe;
	public boolean hasRaids;
	public int minMonsterSpawnLightLimit;
	public int maxMonsterSpawnLightLimit;
	public int monsterSpawnBlockLightLimit;

	public Procedure onPlayerEntersDimension;
	public Procedure onPlayerLeavesDimension;

	public MItemBlock portalFrame;
	public Particle portalParticles;
	public int portalLuminance;
	public Sound portalSound;
	public boolean enableIgniter;
	public String igniterName;
	public String igniterRarity;
	public StringListProcedure specialInformation;
	@ModElementReference public List<TabEntry> creativeTabs;
	@TextureReference(TextureType.ITEM) public TextureHolder texture;
	@TextureReference(TextureType.BLOCK) public TextureHolder portalTexture;
	public boolean enablePortal;
	public Procedure portalMakeCondition;
	public Procedure portalUseCondition;
	public Procedure whenPortaTriggerlUsed;
	public Procedure onPortalTickUpdate;

	private Dimension() {
		this(null);
	}

	public Dimension(ModElement element) {
		super(element);

		// DEFAULT VALUES
		this.seaLevel = 63;
		this.generateOreVeins = true;
		this.generateAquifers = true;
		this.horizontalNoiseSize = 1;
		this.verticalNoiseSize = 2;
		this.coordinateScale = 1;
		this.infiniburnTag = "minecraft:infiniburn_overworld";
		this.enablePortal = true;
		this.enableIgniter = true;
		this.creativeTabs = new ArrayList<>();
		this.defaultEffects = "overworld";
		this.cloudHeight = 192;
		this.skyType = "NONE";
		this.sunHeightAffectsFog = true;
		this.igniterRarity = "COMMON";
	}

	public boolean hasIgniter() {
		// igniter needs portal and igniter enabled
		return enablePortal && enableIgniter;
	}

	public boolean hasEffectsOrDimensionTriggers() {
		return useCustomEffects || onPlayerEntersDimension != null || onPlayerLeavesDimension != null;
	}

	public Set<String> getWorldgenBlocks() {
		Set<String> retval = new HashSet<>();
		retval.add(mainFillerBlock.getUnmappedValue());
		for (BiomeEntry biomeEntry : biomesInDimension) {
			if (biomeEntry.getUnmappedValue().startsWith(NameMapper.MCREATOR_PREFIX)) {
				ModElement biomeElement = getModElement().getWorkspace()
						.getModElementByName(biomeEntry.getUnmappedValue().replace(NameMapper.MCREATOR_PREFIX, ""));
				if (biomeElement != null) {
					GeneratableElement generatableElement = biomeElement.getGeneratableElement();
					if (generatableElement instanceof Biome biome) {
						retval.add(biome.groundBlock.getUnmappedValue());
						retval.add(biome.undergroundBlock.getUnmappedValue());
					}
				}
			}
		}
		return retval;
	}

	@Override public BufferedImage generateModElementPicture() {
		return this.enablePortal ?
				MinecraftImageGenerator.Preview.generateDimensionPreviewPicture(getModElement().getWorkspace(),
						portalTexture.getImage(TextureType.BLOCK), texture.getImage(TextureType.ITEM), portalFrame,
						this.hasIgniter()) :
				null;
	}

	@Override public List<TabEntry> getCreativeTabs() {
		return creativeTabs;
	}

	@Override public Collection<BaseType> getBaseTypesProvided() {
		List<BaseType> baseTypes = new ArrayList<>();
		if (enablePortal)
			baseTypes.add(BaseType.BLOCK);
		if (this.hasIgniter())
			baseTypes.add(BaseType.ITEM);
		return baseTypes;
	}

	@Override public List<MCItem> providedMCItems() {
		ArrayList<MCItem> retval = new ArrayList<>();
		if (this.enablePortal)
			retval.add(new MCItem.Custom(this.getModElement(), "portal", "block_without_item", "Portal block"));
		if (this.hasIgniter())
			retval.add(new MCItem.Custom(this.getModElement(), null, "item", "Portal igniter"));
		return retval;
	}

	@Override public List<MCItem> getCreativeTabItems() {
		if (this.hasIgniter())
			return List.of(new MCItem.Custom(this.getModElement(), null, "item", "Portal igniter"));
		return Collections.emptyList();
	}

	@Override public StringListProcedure getSpecialInfoProcedure() {
		return specialInformation;
	}

	@Override public ImageIcon getIconForMCItem(Workspace workspace, String suffix) {
		if ("portal".equals(suffix))
			return portalTexture.getImageIcon(TextureType.BLOCK);
		else
			return texture.getImageIcon(TextureType.ITEM);
	}

	@Override public List<MItemBlock> poiBlocks() {
		return List.of(new MItemBlock(this.getModElement().getWorkspace(),
				NameMapper.MCREATOR_PREFIX + this.getModElement().getName() + ".portal"));
	}

}
