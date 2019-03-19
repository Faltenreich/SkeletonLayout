package com.faltenreich.skeletonlayout.logic

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class RecyclerViewListItem(
        @StringRes val titleResId: Int,
        @StringRes val descriptionResId: Int,
        @DrawableRes val avatarResId: Int,
        @DrawableRes val wallpaperResId: Int
)