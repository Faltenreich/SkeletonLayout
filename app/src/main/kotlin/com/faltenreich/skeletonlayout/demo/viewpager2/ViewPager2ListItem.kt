package com.faltenreich.skeletonlayout.demo.viewpager2

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.faltenreich.skeletonlayout.demo.R

data class ViewPager2ListItem(
    @StringRes val titleResId: Int,
    @StringRes val descriptionResId: Int,
    @DrawableRes val avatarResId: Int,
    @DrawableRes val wallpaperResId: Int
) {
    companion object {
        val DEMO = listOf(
            ViewPager2ListItem(R.string.user_0_name, R.string.user_0_statement, R.mipmap.list_avatar_0, R.mipmap.list_image_0),
            ViewPager2ListItem(R.string.user_1_name, R.string.user_1_statement, R.mipmap.list_avatar_1, R.mipmap.list_image_1),
            ViewPager2ListItem(R.string.user_2_name, R.string.user_2_statement, R.mipmap.list_avatar_2, R.mipmap.list_image_2)
        )
    }
}