package com.anaheim.unicorn.example.operation;

public class OperationTest {
    public static void main(String[] args) {
        //左移运算(a << n 相当于a * 2^n)
        int a = 10;
        System.out.println(a << 1);
        //右移运算(a >> n 相当于a / 2^n)
        System.out.println(a >> 1);
        //无符号右移运算(正数时与右移运算一致，负数时为补码右移)
        System.out.println(a >>> 1);
    }
}
