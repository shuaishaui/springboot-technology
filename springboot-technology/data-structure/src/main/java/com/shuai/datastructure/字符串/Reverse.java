package com.shuai.datastructure.字符串;

import com.sun.org.apache.xerces.internal.xs.datatypes.ObjectList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

public class Reverse {

  // 使用 stringbuilder来反转字符串
  // 一开始本来使用的是stringbuffer，但是后来发现想到stringbuilder效率要高一点
  // 因为stringbuffer是线程安全的，安全虽好，但是降低了它的效率
  private static String reverse(String str) {
    int i, len = str.length();
    StringBuilder buffer = new StringBuilder(len);
    for (i = len - 1; i >= 0; i--) {
      buffer.append(str.charAt(i));
    }
    return buffer.toString();
  }


  // 使用StringTokenizer来反转句子，指定单词之间的分隔符
  private static String reversSentence(String line) {
    // 指定空格为分隔符
    StringTokenizer tokenizer = new StringTokenizer(line, " ");
    StringBuilder str = new StringBuilder();
    while (tokenizer.hasMoreTokens()) {
      // 因为要反转，所以直接在0位置进行插入即可
      // 先插入一个空格，相当于还原它本来的空格
      str.insert(0, " ");
      str.insert(0, tokenizer.nextToken());
    }

//    String  str = " ";
//    while (tokenizer.hasMoreTokens()){
//      str = tokenizer.nextToken() + " " + str;
//    }

    return str.toString();
  }


  private static void palindrome(List<String> list, String prefix, int length) {
    if (prefix.length() == length) {
      System.out.println(prefix);
    }
    System.out.println("length:" + length + "size:" + list.size());
    for (int i = 0; i < list.size(); i++) {
      List<String> tmp = new LinkedList<String>(list);
      palindrome(tmp, prefix + tmp.remove(i), length);
    }
  }


  public static void main(String[] args) {
    String str = "abcd";
    System.out.println(reverse(str));

    String s = "aa bb cc dd ee";
    System.out.println(reversSentence(s));

    Arrays.asList(s.toCharArray());

    String[] array = new String[]{"a", "b", "c","d"};
    palindrome(Arrays.asList(array), "", array.length);


  }

}
