package com.exchange.util;

import org.jeasy.random.EasyRandomParameters;
import org.jeasy.random.randomizers.misc.BooleanRandomizer;
import org.jeasy.random.randomizers.text.StringRandomizer;

/**
 * @author msaritas
 */
public class TestUtil {

    private static final long STATIC_SEED = 5336L;

    public static EasyRandomParameters getEasyRandomParameters() {
        return new EasyRandomParameters().randomize(Boolean.class, new BooleanRandomizer(STATIC_SEED)).randomize(String.class, new StringRandomizer(4, 4, STATIC_SEED)).collectionSizeRange(2, 3);
    }
}
