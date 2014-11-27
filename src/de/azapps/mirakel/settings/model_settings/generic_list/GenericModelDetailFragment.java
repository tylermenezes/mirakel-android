/*******************************************************************************
 * Mirakel is an Android App for managing your ToDo-Lists
 *
 *   Copyright (c) 2013-2014 Anatolij Zelenin, Georg Semmler.
 *
 *       This program is free software: you can redistribute it and/or modify
 *       it under the terms of the GNU General Public License as published by
 *       the Free Software Foundation, either version 3 of the License, or
 *       any later version.
 *
 *       This program is distributed in the hope that it will be useful,
 *       but WITHOUT ANY WARRANTY; without even the implied warranty of
 *       MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *       GNU General Public License for more details.
 *
 *       You should have received a copy of the GNU General Public License
 *       along with this program.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/

package de.azapps.mirakel.settings.model_settings.generic_list;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBarActivity;

import de.azapps.mirakel.model.IGenericElementInterface;

public abstract class GenericModelDetailFragment<T extends IGenericElementInterface> extends
    PreferenceFragment
    implements IDetailFragment<T> {

    protected static final int NO_PREFERENCES = -1;
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM = "item";


    /**
     * The dummy content this fragment is presenting.
     */
    protected T mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public GenericModelDetailFragment() {
    }

    @NonNull
    protected abstract T getDummyItem();

    protected abstract int getResourceId();

    protected abstract void setUp();

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM)) {
            mItem = getArguments().getParcelable(ARG_ITEM);
        } else {
            // Load the dummy content
            mItem = getDummyItem();
        }
        if ((getActivity() != null) && (((ActionBarActivity)getActivity()).getSupportActionBar() != null)) {
            ((ActionBarActivity)getActivity()).getSupportActionBar().setTitle(mItem.getName());
        }
        final int preferencesResource = getResourceId();
        if (preferencesResource != NO_PREFERENCES) {
            addPreferencesFromResource(getResourceId());
            setUp();
        }
    }


    @Override
    public void onPause() {
        super.onPause();
        mItem.save();
    }

    @NonNull
    @Override
    public T getItem() {
        return mItem;
    }

    protected void removePreference(final String which) {
        final Preference pref = findPreference(which);
        if (pref != null) {
            getPreferenceScreen().removePreference(pref);
        }
    }

}
