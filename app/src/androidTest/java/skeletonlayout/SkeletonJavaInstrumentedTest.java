package skeletonlayout;

import android.support.test.InstrumentationRegistry;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;

import com.faltenreich.skeletonlayout.R;
import com.faltenreich.skeletonlayout.Skeleton;
import com.faltenreich.skeletonlayout.SkeletonFactory;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class SkeletonJavaInstrumentedTest {

    @Test
    public void testSkeleton() {
        RecyclerView recyclerView = new RecyclerView(InstrumentationRegistry.getTargetContext());
        Skeleton skeleton = SkeletonFactory.skeletonForView(recyclerView, R.layout.list_item);
        Assert.assertEquals(skeleton.isSkeleton(), false);
        skeleton.showSkeleton();
        Assert.assertEquals(skeleton.isSkeleton(), true);
        skeleton.showOriginal();
        Assert.assertEquals(skeleton.isSkeleton(), false);
    }
}
