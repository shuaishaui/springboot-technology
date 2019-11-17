package com.shuai.datastructure.栈;

import java.util.HashMap;
import java.util.Stack;

public class Matching {


  static boolean isValid(String s) {

    HashMap<Character, Character> map = new HashMap<Character, Character>();
    map.put('{', '}');
    map.put('(', ')');
    map.put('<', '>');
    map.put('[', ']');

    Stack<Character> stack = new Stack<>();
    if (s == null || "".equals(s)) {
      return true;
    } else {

      // 分解字符串
      char[] chars = s.toCharArray();
      // 输入第一个即为闭符号，直接返回false
//      if (map.containsKey(chars[0])) {
//        return false;
//      }

      for (char c : chars) {

        // 就是当栈为空时，进来的是闭符号，返回false，有了这里，上面的第一个符号就可以注释掉了
        if (stack.isEmpty()) {
          if (map.containsValue(c)) {
            return false;
          }
        }

        // 如果输入的字符不是符号，直接返回true
        if (!map.containsKey(c) && !map.containsValue(c)) {
          return true;
        }

        // 开符号入栈
        if (map.containsKey(c)) {
          stack.push(c);
        }
        // 闭符号出栈
        if (map.containsValue(c)) {

          if (map.get(stack.peek()).equals(c)) {
            stack.pop();
          } else {
            return false;
          }
        }
      }

      // 最后栈为空，则可以判断为true
      return stack.isEmpty();
    }
  }

  public static void main(String[] args) {
    String s = "(]";

    char[] chars = s.toCharArray();
    for (char c : chars) {
      System.out.println(c);
    }
    Character a = '(';
    char b = ']';
    System.out.println(isValid("(]"));
    System.out.println(isValid("([])"));
    System.out.println(isValid("((0))"));


  }



}
