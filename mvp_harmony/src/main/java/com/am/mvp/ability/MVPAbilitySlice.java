/*
 * Copyright (C) 2021 AlexMofer
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
package com.am.mvp.ability;

import com.am.mvp.core.MVPView;
import com.am.mvp.core.MVPViewHolder;
import ohos.aafwk.ability.AbilitySlice;
import ohos.aafwk.ability.Lifecycle;
import ohos.aafwk.ability.LifecycleStateObserver;
import ohos.aafwk.content.Intent;

/**
 * MVP AbilitySlice
 * Created by Alex on 2021/5/28.
 */
public class MVPAbilitySlice extends AbilitySlice implements MVPView {

    private final MVPViewHolder<MVPView> mViewHolder = new MVPViewHolder<>();
    private final LifecycleStateObserver mLifecycleEventObserver =
            this::onLifecycleOwnerStateChanged;
    private int mContentLayoutId;

    public MVPAbilitySlice() {
        getLifecycle().addObserver(mLifecycleEventObserver);
    }

    public MVPAbilitySlice(int contentLayoutId) {
        this();
        mContentLayoutId = contentLayoutId;
    }

    @Override
    protected void onStart(Intent intent) {
        super.onStart(intent);
        if (mContentLayoutId != 0) {
            setUIContent(mContentLayoutId);
        }
    }

    private void onLifecycleOwnerStateChanged(Lifecycle.Event event, Intent intent) {
        switch (event) {
            case ON_START:
                mViewHolder.setView(this);
                break;
            case ON_STOP:
                mViewHolder.setView(null);
                getLifecycle().removeObserver(mLifecycleEventObserver);
                break;
        }
    }

    /**
     * 获取ViewHolder
     *
     * @return ViewHolder
     */
    protected MVPViewHolder<? extends MVPView> getViewHolder() {
        return mViewHolder;
    }

    /**
     * 设置View
     * 便于在View已创建后手动调用
     *
     * @param view View
     */
    protected void setView(MVPView view) {
        mViewHolder.setView(view);
    }
}
