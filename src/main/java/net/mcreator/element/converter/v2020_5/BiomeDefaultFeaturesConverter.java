/*
 * MCreator (https://mcreator.net/)
 * Copyright (C) 2012-2020, Pylo
 * Copyright (C) 2020-2023, Pylo, opensource contributors
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

package net.mcreator.element.converter.v2020_5;

import com.google.gson.JsonElement;
import net.mcreator.element.GeneratableElement;
import net.mcreator.element.converter.IConverter;
import net.mcreator.element.types.Biome;
import net.mcreator.util.StringUtils;
import net.mcreator.workspace.Workspace;

public class BiomeDefaultFeaturesConverter implements IConverter {

	@Override
	public GeneratableElement convert(Workspace workspace, GeneratableElement input, JsonElement jsonElementInput) {
		Biome biome = (Biome) input;

		biome.defaultFeatures.add("Caves");
		biome.defaultFeatures.add("MonsterRooms");
		biome.defaultFeatures.add("Ores");

		biome.foliageColor = biome.grassColor;
		biome.waterFogColor = biome.waterColor;

		biome.name = StringUtils.machineToReadableName(input.getModElement().getName());

		return biome;
	}

	@Override public int getVersionConvertingTo() {
		return 12;
	}

}
