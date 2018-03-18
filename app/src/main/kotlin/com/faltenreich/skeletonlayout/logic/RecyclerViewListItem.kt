package com.faltenreich.skeletonlayout.logic

import android.support.annotation.DrawableRes
import android.support.annotation.StringRes

data class RecyclerViewListItem(
        @StringRes val titleResId: Int,
        @StringRes val descriptionResId: Int,
        @DrawableRes val avatarResId: Int,
        @DrawableRes val wallpaperResId: Int
)