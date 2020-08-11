package com.baizhi.gr;

/**
 * @Author: 高瑞
 * @Date: 2020/8/3 13:28
 */
public class TestSon extends TestFather {
    public int t(){
        this.ageMan = 20;
        return super.ageMan;
    }

    public static void main(String[] args) {
        TestSon testSon = new TestSon();
        System.out.println(testSon.t());    //20
    }
}
