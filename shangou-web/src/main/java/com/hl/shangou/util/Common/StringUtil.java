package com.hl.shangou.util.Common;

import com.mysql.cj.x.protobuf.MysqlxDatatypes;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class StringUtil{


    /**
     * SortAndRepeatStr 排序并且去重复
     * @param Strings
     * @return
     */
    public static List<String> SortAndRepeatStr(String[] Strings){

        StringBuffer stringBuffer = new StringBuffer();

        ArrayList<String> stringArrayList = new ArrayList<>();
        for (String s : Strings) {

            stringArrayList.add(s);

        }


        System.out.println("去重前："+stringArrayList);
        List<String> distinctList = stringArrayList.stream().distinct().collect(Collectors.toList());


        distinctList.sort(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return Integer.valueOf(o1)-Integer.valueOf(o2);
            }
        });


        System.out.println("去重后："+distinctList);

        return distinctList;

    }

   /*public static void main(String[] args) {
       String s1 = new String("6879411787114,");

         String[] split = s1.split("");
       List<String> strings = SortAndRepeatStr(split);
       String substring = s1.substring(1, s1.length() - 1);
       System.err.println(substring);
    }*/


}
