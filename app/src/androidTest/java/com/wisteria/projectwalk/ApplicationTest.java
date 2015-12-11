package com.wisteria.projectwalk;

import android.app.Activity;
import android.app.Application;
import android.test.ApplicationTestCase;

import com.wisteria.projectwalk.models.Entry;
import com.wisteria.projectwalk.models.Manager;

import org.junit.Test;

import java.util.ArrayList;

import static junit.framework.Assert.assertEquals;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest {
    private Activity mActivity;

    @Test
    public void testRetrievesDataFromAPI(){
        Manager manager = Manager.getInstance();
        ArrayList<Entry> entries = manager.getEntries();
        assertEquals("Gets at least one entry from the World Bank API", entries.size() == 0, false);
    }

    @Test
    public void test(){
        Manager manager = Manager.getInstance();
        ArrayList<Entry> entries = manager.getEntries();
        assertEquals("Gets at least one entry from the World Bank API", entries.size() == 0, false);
    }

}