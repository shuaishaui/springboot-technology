package com.atguigu.decorator;

//�����Decorator�� ������ǵ�ζƷ
public class Chocolate extends Decorator {

	public Chocolate(Drink obj) {
		super(obj);
		setDes(" �ɿ��� ");
		setPrice(3.0f); // ��ζƷ �ļ۸�
	}

}
