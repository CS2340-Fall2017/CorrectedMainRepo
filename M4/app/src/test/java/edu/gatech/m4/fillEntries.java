package edu.gatech.m4;

import android.content.Context;
import android.test.ActivityTestCase;
import android.test.InstrumentationTestCase;


import org.junit.Test;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import android.view.View;

import com.github.mikephil.charting.data.BarEntry;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;


/**
 * Created by samuel_bretz on 11/20/17.
 */

public class fillEntries {
    /**
     * This test will check the functionality of the method fillEntries() in GraphActivity.java
     * It will make sure that when run, the entries list will be populated and formatted correctly.
     */
    @Test
    public void testFillEntries() {
        Map<String, Integer> graphHelper = new TreeMap<>();
        graphHelper.put("2010-11", 1);
        graphHelper.put("2014-08", 2);
        graphHelper.put("2017-04", 1);

        List<BarEntry> groundTruth = new ArrayList<>();
        groundTruth.add(new BarEntry(0,(float) graphHelper.get("2010-11")));
        groundTruth.add(new BarEntry(1,(float) graphHelper.get("2014-08")));
        groundTruth.add(new BarEntry(2,(float) graphHelper.get("2017-04")));

        Set<String> keys = graphHelper.keySet();
        List<BarEntry> test = GraphActivity.fillEntries(keys, graphHelper);


        assert(test == groundTruth);
    }
}
