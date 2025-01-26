package com.faltenreich.skeletonlayout.demo.recyclerview

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.faltenreich.skeletonlayout.demo.R

data class RecyclerViewListItem(
    @StringRes val titleResId: Int,
    @StringRes val descriptionResId: Int,
    @DrawableRes val avatarResId: Int,
    @DrawableRes val wallpaperResId: Int,
) {

    companion object {
        val DEMO = listOf(
            RecyclerViewListItem(R.string.user_0_name, R.string.user_0_statement, R.mipmap.list_avatar_0, R.mipmap.list_image_0),
            RecyclerViewListItem(R.string.user_1_name, R.string.user_1_statement, R.mipmap.list_avatar_1, R.mipmap.list_image_1),
            RecyclerViewListItem(R.string.user_2_name, R.string.user_2_statement, R.mipmap.list_avatar_2, R.mipmap.list_image_2)
        )
    }
}