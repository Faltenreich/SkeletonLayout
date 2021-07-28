# SkeletonLayout

[![MavenCentral](https://maven-badges.herokuapp.com/maven-central/com.faltenreich/skeletonlayout/badge.svg?style=flat)](https://maven-badges.herokuapp.com/maven-central/com.faltenreich/skeletonlayout)

*Make your app feel faster*

<img src="./images/preview.gif" width="250">

Users are time-sensitive and may skip an app due to long loading times and missing visual feedback. 
Instagram, Facebook, Google and other services tackled this problem with the so-called Skeleton View.
This view is being displayed during the process of fetching or requesting data asynchronously which leads to a perceivedly
more responsive app.

This library implements the Skeleton View pattern and provides an easy way for other developers to enable it in their apps. 

## Demo

<a href='https://play.google.com/store/apps/details?id=com.faltenreich.skeletonlayout&pcampaignid=MKT-Other-global-all-co-prtnr-py-PartBadge-Mar2515-1'><img alt='Get it on Google Play' src='https://play.google.com/intl/en_us/badges/images/generic/en_badge_web_generic.png' width="250"/></a>

## Preview

<img src="./images/preview.png" width="620">

The SkeletonLayout mimics the design of established implementations per default, but you are free to get creative. 
Create your own skeleton view with custom shapes, colors and shimmers.

<img src="./images/mask_square.png" width="200">   <img src="./images/mask_round.png" width="200">   <img src="./images/mask_color.png" width="200">

## Features

- **Make your app feel faster:** Immediate visual feedback long before your data has been fetched or requested
- **Support any View:** Apply to any type of View or ViewGroup
- **RecyclerView on speed:** Convenience adapter for RecyclerView as it is the main use case
- **ViewPager2 support:** Convenience adapter for ViewPager2 as it uses a RecyclerView under the hood
- **Customization:** Adjust shimmer, color and shape of the skeleton to set you apart from other apps
- **Minimum effort:** A fistful lines of code to use the SkeletonLayout
- **Minimum footprint:** org.jetbrains.kotlin:kotlin-stdlib-jdk7, androidx.recyclerview:recyclerview and androidx.viewpager2.widget.ViewPager2 are the only dependencies

### Getting Started

##### Gradle
```gradle
buildscript {
    repositories {
        mavenCentral()
    }
}
```
```gradle
dependencies {
    implementation 'com.faltenreich:skeletonlayout:<version>'
}
```

##### XML
```xml
<com.faltenreich.skeletonlayout.SkeletonLayout
    android:id="@+id/skeletonLayout"
    android:layout_width="match_parent"
    android:layout_height="wrap_content">
    
    <!-- Views to mask -->
    
</com.faltenreich.skeletonlayout.SkeletonLayout>
```

##### Kotlin
```kotlin
class MainActivity : AppCompatActivity() {

    private lateinit var skeleton: Skeleton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Either use an existing Skeletonlayout
        skeleton = findViewById(R.id.skeletonLayout)

        // or create a new SkeletonLayout from a given View
        skeleton = view.createSkeleton()

        // or apply a new SkeletonLayout to a RecyclerView
        skeleton = recyclerView.applySkeleton(R.layout.list_item_recyclerview)

        // or apply a new SkeletonLayout to a ViewPager2
        skeleton = viewPager2.applySkeleton(R.layout.list_item_viewpager2)

        skeleton.showSkeleton()
    }

    // Example callback that hides skeleton
    private fun onDataLoaded() {
        skeleton.showOriginal()
    }
}
```

##### Java
```java
public class MainActivity extends AppCompatActivity {
    
    private Skeleton skeleton;
    
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // Either use an existing Skeletonlayout
        skeleton = findViewById(R.id.skeletonLayout);
        
        // or create a new SkeletonLayout from a given View
        skeleton = SkeletonLayoutUtils.createSkeleton(view);
        
        // or apply a new SkeletonLayout to a RecyclerView
        skeleton = SkeletonLayoutUtils.applySkeleton(recyclerView, R.layout.list_item_recyclerview);

        // or apply a new SkeletonLayout to a ViewPager2
        skeleton = SkeletonLayoutUtils.applySkeleton(viewPager2, R.layout.list_item_viewpager2);

        skeleton.showSkeleton();
    }
    
    // Example callback that hides skeleton
    private void onDataLoaded() {
        skeleton.showOriginal();
    }
}
```

### Configuration

Property | Type | Description
--- | --- | ---
maskColor | color | Color of the mask that fills the original view bounds (defaults to #E0E0E0)
maskCornerRadius | dimension | The x- and y-radius of the oval used to round the mask corners (defaults to 25)
showShimmer | boolean | Animate shimmer if set to true (defaults to true)
shimmerColor | color | Color of the animated shimmer (defaults to #d5d5d5)
shimmerDurationInMillis | integer | Duration in milliseconds for one shimmer animation interval (defaults to 2000)
shimmerDirection | enum | Direction of animated shimmer (defaults to LEFT_TO_RIGHT)
shimmerAngle | integer | Angle in degrees for animated shimmer (defaults to 0)
itemCount | integer | Item count for Skeleton adapter (RecyclerView and ViewPager2 only, defaults to 3)

### FAQ

**When and how is the skeleton created?**
The skeleton gets created after the original view's onLayout(), since we rely on it to be fully rendered in order to mask its bounds.

**How does the masking work?**
The mask is drawn onto a single Bitmap by iterating once through the given Views and their bounds.

**May properties of the SkeletonLayout be changed at runtime?**
Yes. Any change to the skeleton leads to a redraw, since the whole content of the SkeletonLayout gets drawn onto a single bitmap.

**Will the shimmer eat my users' battery?**
The shimmer is a shader (LinearGradient) whose local matrix is updated according to the framerate of the target device, 
so no redrawing is required and processing time is kept to an absolute minimum. 
Additionally the shimmer gets inactive onWindowFocusChanged() and onDetachedFromWindow().

### License

Copyright 2021 Philipp Fahlteich

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

<img src="./images/android.png" width="100"> 