<#include "mcitems.ftl">
${mappedMCItemToItemStackCode(input$item, 1)}.enchant(world.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(${generator.map(field$enhancement, "enchantments")}), ${opt.toInt(input$level)});