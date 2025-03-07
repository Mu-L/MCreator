/*
 * MCreator (https://mcreator.net/)
 * Copyright (C) 2012-2020, Pylo
 * Copyright (C) 2020-2025, Pylo, opensource contributors
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

import net.mcreator.element.parts.GameEventEntry;
import net.mcreator.generator.mapping.NameMapper;
import net.mcreator.minecraft.ElementUtil;
import net.mcreator.minecraft.TagType;
import net.mcreator.ui.MCreator;
import net.mcreator.ui.component.JItemListField;
import net.mcreator.ui.dialogs.AddTagDialog;
import net.mcreator.ui.dialogs.DataListSelectorDialog;
import net.mcreator.ui.init.L10N;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class GameEventListField extends JItemListField<GameEventEntry> {

	public GameEventListField(MCreator mcreator) {
		super(mcreator);
	}

	public GameEventListField(MCreator mcreator, boolean allowTags) {
		super(mcreator);
		if (allowTags)
			allowTags();
	}

	@Override protected List<GameEventEntry> getElementsToAdd() {
		return DataListSelectorDialog.openMultiSelectorDialog(mcreator, w -> ElementUtil.loadAllGameEvents(),
						L10N.t("dialog.list_field.game_event_list_title"), L10N.t("dialog.list_field.game_event_list_message"))
				.stream().map(e -> new GameEventEntry(mcreator.getWorkspace(), e)).toList();
	}

	@Override protected List<GameEventEntry> getTagsToAdd() {
		List<GameEventEntry> tags = new ArrayList<>();

		String tag = AddTagDialog.openAddTagDialog(mcreator, mcreator, TagType.GAME_EVENTS, "vibrations",
				"warden_can_listen", "shrieker_can_listen", "allay_can_listen");
		if (tag != null)
			tags.add(new GameEventEntry(mcreator.getWorkspace(), "#" + tag));

		return tags;
	}

	@Nullable @Override protected GameEventEntry fromExternalToElement(String external) {
		return new GameEventEntry(mcreator.getWorkspace(), NameMapper.EXTERNAL_PREFIX + external);
	}

}
