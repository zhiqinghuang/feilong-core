/*
 * Copyright (C) 2008 feilong
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.feilong.core.text;

import java.math.RoundingMode;
import java.text.DecimalFormat;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class NumberFormatUtilTest.
 *
 * @author <a href="http://feitianbenyue.iteye.com/">feilong</a>
 */
public class NumberFormatUtilTest{

    /** The Constant log. */
    private static final Logger LOGGER = LoggerFactory.getLogger(NumberFormatUtilTest.class);

    /**
     * Test format.
     */
    //***************NumberFormatUtil.format(Number, String)************************************
    /**
     * Test format null value.
     */
    @Test(expected = NullPointerException.class)
    public void testFormatNullValue(){
        NumberFormatUtil.format(null, "#####");
    }

    /**
     * Test format null number pattern.
     */
    @Test(expected = NullPointerException.class)
    public void testFormatNullNumberPattern(){
        NumberFormatUtil.format(25, null);
    }

    /**
     * Test format blank number pattern.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testFormatBlankNumberPattern(){
        NumberFormatUtil.format(25, "");
    }

    /**
     * Test format blank number pattern 1.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testFormatBlankNumberPattern1(){
        NumberFormatUtil.format(25, " ");
    }

    //*************NumberFormatUtil.format(Number, String, RoundingMode)***************************************

    /**
     * Test format null value 1.
     */
    @Test(expected = NullPointerException.class)
    public void testFormatNullValue1(){
        NumberFormatUtil.format(null, "#####", RoundingMode.HALF_UP);
    }

    /**
     * Test format null number pattern 1.
     */
    @Test(expected = NullPointerException.class)
    public void testFormatNullNumberPattern1(){
        NumberFormatUtil.format(25, null, RoundingMode.HALF_UP);
    }

    /**
     * Test format blank number pattern 2.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testFormatBlankNumberPattern2(){
        NumberFormatUtil.format(25, "", RoundingMode.HALF_UP);
    }

    /**
     * Test format blank number pattern 21.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testFormatBlankNumberPattern21(){
        NumberFormatUtil.format(25, " ", RoundingMode.HALF_UP);
    }

    /**
     * Test convert number to string.
     */
    @Test
    public void testToString1(){
        // //######0
        DecimalFormat df = new DecimalFormat("######0");
        LOGGER.debug(df.format(((double) 5 / Integer.valueOf(8)) * 100));
    }
}
