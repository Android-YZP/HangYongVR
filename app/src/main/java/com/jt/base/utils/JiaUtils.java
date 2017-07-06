package com.jt.base.utils;

import com.jt.base.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Smith on 2017/7/6.
 */

public class JiaUtils {

    public static List<Integer> getOne() {
        List<Integer> One = new ArrayList<>();
        One.add(R.drawable.a01);
        One.add(R.drawable.a02);
        One.add(R.drawable.a03);
        One.add(R.drawable.a04);
        One.add(R.drawable.a05);
        return One;
    }

    public static List<Integer> getTwo() {
        List<Integer> One = new ArrayList<>();
        One.add(R.drawable.a11);
        One.add(R.drawable.a12);
        One.add(R.drawable.a13);
        One.add(R.drawable.a14);
        One.add(R.drawable.a15);
        return One;
    }

    public static List<Integer> getThree() {
        List<Integer> One = new ArrayList<>();
        One.add(R.drawable.a21);
        One.add(R.drawable.a22);
        One.add(R.drawable.a23);
        One.add(R.drawable.a24);
        One.add(R.drawable.a25);
        return One;
    }

    public static List<Integer> getFour() {
        List<Integer> One = new ArrayList<>();
        One.add(R.drawable.a31);
        One.add(R.drawable.a32);
        One.add(R.drawable.a33);
        One.add(R.drawable.a34);
        One.add(R.drawable.a35);
        return One;
    }

    public static List<Integer> getFive() {
        List<Integer> One = new ArrayList<>();
        One.add(R.drawable.a41);
        One.add(R.drawable.a42);
        One.add(R.drawable.a43);
        One.add(R.drawable.a44);
        One.add(R.drawable.a45);
        return One;
    }

    public static HashMap<Integer, List<Integer>> getJSJ() {
        HashMap<Integer, List<Integer>> Jiashuju = new HashMap<>();
        Jiashuju.put(0,getOne());
        Jiashuju.put(1,getTwo());
        Jiashuju.put(2,getThree());
        Jiashuju.put(3,getFour());
        Jiashuju.put(4,getFive());
        return Jiashuju;
    }


}
