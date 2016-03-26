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
package com.feilong.core.util;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;

/**
 * <b>核心类</b>,判断对象是否为null或者Empty.
 * 
 * <ol>
 * <li>{@link #isNullOrEmpty(Object)} 判断对象是否是null或者空</li>
 * <li>{@link #isNotNullOrEmpty(Object)}判断对象是否不是null或者不是空</li>
 * </ol>
 * 
 * <h3>对于empty的判断,使用以下逻辑:</h3>
 * 
 * <blockquote>
 * <ol>
 * <li>{@link Collection},使用其 {@link Collection#isEmpty()};</li>
 * <li>{@link Map},使用其 {@link Map#isEmpty()};</li>
 * <li>{@link String},使用 {@link String#trim()}{@code .length()<=0}效率高;</li>
 * <li>{@link Enumeration},使用 {@link Enumeration#hasMoreElements()};</li>
 * <li>{@link Iterator},使用 {@link Iterator#hasNext()};</li>
 * <li><code>Object[]</code>,判断length==0;注:二维数组不管是primitive 还是包装类型,都instanceof Object[];</li>
 * <li><code>byte[]</code>,判断length==0;</li>
 * <li><code>boolean[]</code>,判断length==0;</li>
 * <li><code>char[]</code>,判断length==0;</li>
 * <li><code>int[]</code>,判断length==0;</li>
 * <li><code>short[]</code>,判断length==0;</li>
 * <li><code>float[]</code>,判断length==0;</li>
 * <li><code>double[]</code>,判断length==0;</li>
 * </ol>
 * </blockquote>
 * 
 * @author feilong
 * @version 1.0.0 Sep 2, 2010 8:35:28 PM
 * @version 1.0.1 2012-9-23 21:34 rename method,isNullOrEmpty替代isNull
 * @version 1.0.7 2014-5-22 15:57 add {@link #arrayIsNullOrEmpty(Object)}
 * @see String#trim()
 * @see Map#isEmpty()
 * @see Collection#isEmpty()
 * @see Enumeration#hasMoreElements()
 * @see Iterator#hasNext()
 * @see org.apache.commons.lang3.Validate
 * @see org.apache.commons.collections4.CollectionUtils#sizeIsEmpty(Object)
 * @since 1.0.0
 */
public final class Validator{

    /** Don't let anyone instantiate this class. */
    private Validator(){
        //AssertionError不是必须的. 但它可以避免不小心在类的内部调用构造器. 保证该类在任何情况下都不会被实例化.
        //see 《Effective Java》 2nd
        throw new AssertionError("No " + getClass().getName() + " instances for you!");
    }

    /**
     * 判断对象是否为Null或者Empty.
     * 
     * <h3>对于empty的判断,使用以下逻辑:</h3>
     * 
     * <blockquote>
     * <ol>
     * <li>{@link Collection},使用其 {@link Collection#isEmpty()};</li>
     * <li>{@link Map},使用其 {@link Map#isEmpty()};</li>
     * <li>{@link String},使用 {@link String#trim()}{@code .length()<=0}效率高;</li>
     * <li>{@link Enumeration},使用 {@link Enumeration#hasMoreElements()};</li>
     * <li>{@link Iterator},使用 {@link Iterator#hasNext()};</li>
     * <li><code>Object[]</code>,判断length==0;注:二维数组不管是primitive 还是包装类型,都instanceof Object[];</li>
     * <li><code>byte[]</code>,判断length==0;</li>
     * <li><code>boolean[]</code>,判断length==0;</li>
     * <li><code>char[]</code>,判断length==0;</li>
     * <li><code>int[]</code>,判断length==0;</li>
     * <li><code>short[]</code>,判断length==0;</li>
     * <li><code>float[]</code>,判断length==0;</li>
     * <li><code>double[]</code>,判断length==0;</li>
     * </ol>
     * </blockquote>
     * 
     * @param value
     *            可以是Collection,Map,String,Enumeration,Iterator,以及所有数组类型
     * @return 如果是null,返回true<br>
     *         如果是empty也返回true<br>
     *         其他情况返回false<br>
     *         如果不是上述类型,不判断empty,返回false
     * @see org.apache.commons.collections4.CollectionUtils#sizeIsEmpty(Object)
     * @see org.apache.commons.collections4.CollectionUtils#isEmpty(Collection)
     * @see org.apache.commons.collections4.CollectionUtils#isNotEmpty(Collection)
     * @see org.apache.commons.collections4.MapUtils#isEmpty(Map)
     * @see org.apache.commons.collections4.MapUtils#isNotEmpty(Map)
     * @see org.apache.commons.lang.ArrayUtils#isEmpty(byte[])
     * @see org.apache.commons.lang.ArrayUtils#isEmpty(boolean[])
     * @see org.apache.commons.lang.ArrayUtils#isEmpty(char[])
     * @see org.apache.commons.lang.ArrayUtils#isEmpty(int[])
     * @see org.apache.commons.lang.ArrayUtils#isEmpty(long[])
     * @see org.apache.commons.lang.ArrayUtils#isEmpty(short[])
     * @see org.apache.commons.lang.ArrayUtils#isEmpty(float[])
     * @see org.apache.commons.lang.ArrayUtils#isEmpty(double[])
     * @see org.apache.commons.lang.ArrayUtils#isEmpty(Object[])
     * @see org.apache.commons.lang.StringUtils#isBlank(String)
     * @see org.apache.commons.lang.StringUtils#isEmpty(String)
     */
    public static boolean isNullOrEmpty(Object value){
        if (null == value){
            return true;
        }

        // 字符串
        if (value instanceof String){// 比较字符串长度, 效率高
            return value.toString().trim().length() <= 0;
        }

        if (value instanceof Collection // 集合
                        || value instanceof Map// map
                        || value instanceof Enumeration // 枚举
                        || value instanceof Iterator// Iterator迭代器
        ){
            return CollectionUtils.sizeIsEmpty(value);
        }

        boolean arrayFlag = arrayIsNullOrEmpty(value);
        if (arrayFlag){
            return true;
        }
        // 这里可以扩展
        return false;
    }

    /**
     * 判断对象是否不为Null或者Empty,调用 !{@link #isNullOrEmpty(Object)} 方法 .
     * 
     * <h3>对于empty的判断,使用以下逻辑:</h3>
     * 
     * <blockquote>
     * <ol>
     * <li>{@link Collection},使用其 {@link Collection#isEmpty()};</li>
     * <li>{@link Map},使用其 {@link Map#isEmpty()};</li>
     * <li>{@link String},使用 {@link String#trim()}{@code .length()<=0}效率高;</li>
     * <li>{@link Enumeration},使用 {@link Enumeration#hasMoreElements()};</li>
     * <li>{@link Iterator},使用 {@link Iterator#hasNext()};</li>
     * <li><code>Object[]</code>,判断length==0;注:二维数组不管是primitive 还是包装类型,都instanceof Object[];</li>
     * <li><code>byte[]</code>,判断length==0;</li>
     * <li><code>boolean[]</code>,判断length==0;</li>
     * <li><code>char[]</code>,判断length==0;</li>
     * <li><code>int[]</code>,判断length==0;</li>
     * <li><code>short[]</code>,判断length==0;</li>
     * <li><code>float[]</code>,判断length==0;</li>
     * <li><code>double[]</code>,判断length==0;</li>
     * </ol>
     * </blockquote>
     * 
     * @param value
     *            可以是Collection,Map,String,Enumeration,Iterator,以及所有数组类型
     * @return 如果是null,返回false<br>
     *         如果是空也返回false<br>
     *         其他情况返回true<br>
     *         如果不是上述类型,不判断empty,返回true
     * @see org.apache.commons.collections4.CollectionUtils#isEmpty(Collection)
     * @see org.apache.commons.collections4.CollectionUtils#isNotEmpty(Collection)
     * @see org.apache.commons.collections4.MapUtils#isEmpty(Map)
     * @see org.apache.commons.collections4.MapUtils#isNotEmpty(Map)
     * @see org.apache.commons.lang.ArrayUtils#isEmpty(byte[])
     * @see org.apache.commons.lang.ArrayUtils#isEmpty(boolean[])
     * @see org.apache.commons.lang.ArrayUtils#isEmpty(char[])
     * @see org.apache.commons.lang.ArrayUtils#isEmpty(int[])
     * @see org.apache.commons.lang.ArrayUtils#isEmpty(long[])
     * @see org.apache.commons.lang.ArrayUtils#isEmpty(short[])
     * @see org.apache.commons.lang.ArrayUtils#isEmpty(float[])
     * @see org.apache.commons.lang.ArrayUtils#isEmpty(double[])
     * @see org.apache.commons.lang.ArrayUtils#isEmpty(Object[])
     * @see org.apache.commons.lang.StringUtils#isBlank(String)
     * @see org.apache.commons.lang.StringUtils#isEmpty(String)
     */
    public static boolean isNotNullOrEmpty(Object value){
        return !isNullOrEmpty(value);
    }

    /**
     * 数组 类型的验证,区分 primitive 和包装类型.
     * 
     * @param value
     *            可以是
     *            <ul>
     *            <li>Object[] 二维数组属于这个类型</li>
     *            <li>byte[]</li>
     *            <li>boolean[]</li>
     *            <li>char[]</li>
     *            <li>int[]</li>
     *            <li>long[]</li>
     *            <li>short[]</li>
     *            <li>float[]</li>
     *            <li>double[]</li>
     *            </ul>
     * @return 如果是数组类型(区分 primitive和包装类型),判断其length==0;<br>
     *         如果不是 直接返回false
     * @see org.apache.commons.lang.ArrayUtils#isEmpty(byte[])
     * @see org.apache.commons.lang.ArrayUtils#isEmpty(boolean[])
     * @see org.apache.commons.lang.ArrayUtils#isEmpty(char[])
     * @see org.apache.commons.lang.ArrayUtils#isEmpty(int[])
     * @see org.apache.commons.lang.ArrayUtils#isEmpty(long[])
     * @see org.apache.commons.lang.ArrayUtils#isEmpty(short[])
     * @see org.apache.commons.lang.ArrayUtils#isEmpty(float[])
     * @see org.apache.commons.lang.ArrayUtils#isEmpty(double[])
     * @see org.apache.commons.lang.ArrayUtils#isEmpty(Object[])
     * @since 1.0.7
     */
    private static boolean arrayIsNullOrEmpty(Object value){
        if (value instanceof Object[] //Object[] 数组 Integer/String...自定义的对象User.等数组也 instanceof Object[]
                        || value instanceof int[] // primitive ints
                        || value instanceof long[] // primitive longs
                        || value instanceof float[]// primitive floats
                        || value instanceof double[] // primitive doubles
                        || value instanceof char[]// primitive chars
                        || value instanceof boolean[] // primitive booleans
                        || value instanceof byte[] // primitive bytes
                        || value instanceof short[] // primitive shorts       
        ){
            return Array.getLength(value) == 0;
        }
        return false;
    }
}