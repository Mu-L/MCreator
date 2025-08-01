<#--
 # MCreator (https://mcreator.net/)
 # Copyright (C) 2012-2020, Pylo
 # Copyright (C) 2020-2024, Pylo, opensource contributors
 #
 # This program is free software: you can redistribute it and/or modify
 # it under the terms of the GNU General Public License as published by
 # the Free Software Foundation, either version 3 of the License, or
 # (at your option) any later version.
 #
 # This program is distributed in the hope that it will be useful,
 # but WITHOUT ANY WARRANTY; without even the implied warranty of
 # MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 # GNU General Public License for more details.
 #
 # You should have received a copy of the GNU General Public License
 # along with this program.  If not, see <https://www.gnu.org/licenses/>.
 #
 # Additional permission for code generator templates (*.ftl files)
 #
 # As a special exception, you may create a larger work that contains part or
 # all of the MCreator code generator templates (*.ftl files) and distribute
 # that work under terms of your choice, so long as that work isn't itself a
 # template for code generation. Alternatively, if you modify or redistribute
 # the template itself, you may (at your option) remove this special exception,
 # which will cause the template and the resulting code generator output files
 # to be licensed under the GNU General Public License without this special
 # exception.
-->

<#-- @formatter:off -->

/*
 *	MCreator note: This file will be REGENERATED on each build.
 */

package ${package}.init;

@EventBusSubscriber
public class ${JavaModName}GameRules {

	<#list gamerules as gamerule>
		<#if gamerule.type == "Number">
		public static GameRules.Key<GameRules.IntegerValue> ${gamerule.getModElement().getRegistryNameUpper()};
		<#else>
		public static GameRules.Key<GameRules.BooleanValue> ${gamerule.getModElement().getRegistryNameUpper()};
		</#if>
	</#list>

	@SubscribeEvent
	public static void registerGameRules(FMLCommonSetupEvent event) {
	<#compress>
	<#list gamerules as gamerule>
		<#if gamerule.type == "Number">
		 ${gamerule.getModElement().getRegistryNameUpper()} =
				GameRules.register("${StringUtils.lowercaseFirstLetter(gamerule.getModElement().getName())}",
				GameRules.Category.${gamerule.category}, GameRules.IntegerValue.create(${gamerule.defaultValueNumber}));
		<#else>
		${gamerule.getModElement().getRegistryNameUpper()} =
				GameRules.register("${StringUtils.lowercaseFirstLetter(gamerule.getModElement().getName())}",
				GameRules.Category.${gamerule.category}, GameRules.BooleanValue.create(${gamerule.defaultValueLogic}));
		</#if>
	</#list>
	</#compress>
	}

}
<#-- @formatter:on -->