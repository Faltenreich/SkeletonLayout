package skeletonlayout;

import android.view.View;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;

import com.faltenreich.skeletonlayout.Skeleton;
import com.faltenreich.skeletonlayout.SkeletonLayoutUtils;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class SkeletonJavaInstrumentedTest {

    @Test
    public void testSkeleton() {
        View view = new View(InstrumentationRegistry.getInstrumentation().getContext());
        Skeleton skeleton = SkeletonLayoutUtils.createSkeleton(view);
        Assert.assertFalse(skeleton.isSkeleton());
        skeleton.showSkeleton();
        Assert.assertTrue(skeleton.isSkeleton());
        skeleton.showOriginal();
        Assert.assertFalse(skeleton.isSkeleton());
    }
}
