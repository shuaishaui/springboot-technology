package com.shuai.datastructure;

public class Break{

  Integer oo;
  int o;
  Character c;
  char cc;

  public static void main(String[] args) {
//    float f = 0.1;
    Break b = new Break();
    char a = 'a';
    int aa = a;
    System.out.println(aa);
    System.out.println("oo:" + b.oo + "  o:" + b.o  + " c:" + b.c + " cc:" + b.cc);
      out:
      for (int i = 0; i < 5; i++) {
        for (int j = 0; j < 5; j++) {
          if (j >= 2) {
            break ;
          }
          System.out.println(j);
        }
      }

  }
}