/*
 * MCreator (https://mcreator.net/)
 * Copyright (C) 2012-2020, Pylo
 * Copyright (C) 2020-2024, Pylo, opensource contributors
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

package net.mcreator.ui.minecraft;

import net.mcreator.element.ModElementType;
import net.mcreator.ui.MCreator;
import net.mcreator.ui.component.JSingleEntrySelector;
import net.mcreator.ui.dialogs.StringSelectorDialog;
import net.mcreator.ui.init.L10N;
import net.mcreator.workspace.elements.ModElement;

import java.util.Locale;

public class SingleModElementSelector extends JSingleEntrySelector<String> {

	private final ModElementType<?> type;

	public SingleModElementSelector(MCreator mcreator, ModElementType<?> type) {
		super(mcreator);
		this.type = type;
	}

	@Override protected String openEntrySelector() {
		return StringSelectorDialog.openSelectorDialog(mcreator,
				w -> w.getModElements().stream().filter(e -> e.getType() == type).map(ModElement::getName).toList()
						.toArray(String[]::new),
				L10N.t("dialog.selector.mod_element_title", type.getReadableName().toLowerCase(Locale.ENGLISH)),
				L10N.t("dialog.selector.mod_element_message", type.getReadableName().toLowerCase(Locale.ENGLISH)));
	}
}
