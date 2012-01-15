package se.aaslin.developer.shoppinglist.test;

import android.test.ActivityInstrumentationTestCase2;
import se.aaslin.developer.shoppinglist.*;

public class HelloAndroidActivityTest extends ActivityInstrumentationTestCase2<HelloAndroidActivity> {

    public HelloAndroidActivityTest() {
        super("se.aaslin.developer.shoppinglist", HelloAndroidActivity.class);
    }

    public void testActivity() {
        HelloAndroidActivity activity = getActivity();
        assertNotNull(activity);
    }
}

