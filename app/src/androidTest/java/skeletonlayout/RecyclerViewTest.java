package skeletonlayout;

import android.support.test.InstrumentationRegistry;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;

import com.faltenreich.skeletonlayout.R;
import com.faltenreich.skeletonlayout.SkeletonLayoutUtils;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class RecyclerViewTest {

    @Test
    public void testSkeleton() {
        RecyclerView recyclerView = new RecyclerView(InstrumentationRegistry.getTargetContext());
        SkeletonLayoutUtils.applySkeletonAdapter(recyclerView, R.layout.list_item);
    }
}
