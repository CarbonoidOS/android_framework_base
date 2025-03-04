/*
 * Copyright (C) 2006 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.systemui.statusbar.policy;

import static com.android.systemui.statusbar.phone.CollapsedStatusBarFragment.STATUS_BAR_CLOCK_RIGHT;

import android.app.StatusBarManager;
import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

import com.android.systemui.Dependency;

public class ClockRight extends Clock {

    private boolean mClockVisibleByPolicy = true;
    private boolean mClockVisibleByUser = true;

    public ClockRight(Context context) {
        this(context, null);
    }

    public ClockRight(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ClockRight(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setClockVisibleByUser(boolean visible) {
        mClockVisibleByUser = visible;
        updateClockVisibility();
    }

    public void setClockVisibilityByPolicy(boolean visible) {
        mClockVisibleByPolicy = visible;
        updateClockVisibility();
    }

    protected void updateClockVisibility() {
        boolean visible = mClockConfig == STATUS_BAR_CLOCK_RIGHT
                && mClockVisibleByPolicy && mClockVisibleByUser;
        Dependency.get(IconLogger.class).onIconVisibility("right_clock", visible);
        int visibility = visible ? View.VISIBLE : View.GONE;
        setVisibility(visibility);
    }

    @Override
    public void disable(int state1, int state2, boolean animate) {
        boolean clockVisibleByPolicy = (state1 & StatusBarManager.DISABLE_CLOCK) == 0;
        if (clockVisibleByPolicy != mClockVisibleByPolicy) {
            setClockVisibilityByPolicy(clockVisibleByPolicy);
        }
    }
}
