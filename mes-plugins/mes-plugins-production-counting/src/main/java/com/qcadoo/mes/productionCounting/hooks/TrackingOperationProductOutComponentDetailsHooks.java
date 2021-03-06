/**
 * ***************************************************************************
 * Copyright (c) 2010 Qcadoo Limited
 * Project: Qcadoo MES
 * Version: 1.4
 *
 * This file is part of Qcadoo.
 *
 * Qcadoo is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation; either version 3 of the License,
 * or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 * ***************************************************************************
 */
package com.qcadoo.mes.productionCounting.hooks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qcadoo.mes.productionCounting.SetTrackingOperationProductsComponentsService;
import com.qcadoo.mes.productionCounting.constants.TrackingOperationProductOutComponentFields;
import com.qcadoo.mes.productionCounting.listeners.TrackingOperationProductComponentDetailsListeners;
import com.qcadoo.model.api.Entity;
import com.qcadoo.view.api.ViewDefinitionState;
import com.qcadoo.view.api.components.FormComponent;

@Service
public class TrackingOperationProductOutComponentDetailsHooks {

    private static final String L_FORM = "form";

    public static final String L_SET_TAB = "setTab";

    @Autowired
    private TrackingOperationProductComponentDetailsListeners trackingOperationProductComponentDetailsListeners;

    @Autowired
    private SetTrackingOperationProductsComponentsService setTrackingOperationProductsComponents;

    public void onBeforeRender(final ViewDefinitionState view) {
        trackingOperationProductComponentDetailsListeners.onBeforeRender(view);

        FormComponent trackingOperationProductOutComponentForm = (FormComponent) view.getComponentByReference(L_FORM);

        Entity trackingOperationProductOutComponent = trackingOperationProductOutComponentForm
                .getPersistedEntityWithIncludedFormValues();

        hideOrShowSetTab(view, trackingOperationProductOutComponent);
    }

    private void hideOrShowSetTab(final ViewDefinitionState view, final Entity trackingOperationProductOutComponent) {
        view.getComponentByReference(L_SET_TAB)
                .setVisible(setTrackingOperationProductsComponents.isSet(
                        trackingOperationProductOutComponent
                                .getBelongsToField(TrackingOperationProductOutComponentFields.PRODUCTION_TRACKING),
                        trackingOperationProductOutComponent));
    }

}
