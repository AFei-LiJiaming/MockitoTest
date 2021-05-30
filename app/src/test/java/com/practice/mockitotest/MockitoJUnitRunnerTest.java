package com.practice.mockitotest;



import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.after;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.atMost;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MockitoJUnitRunnerTest {

    @Mock
    Person mPerson;
    Person mPerson1;


    @Test
    public void testPersonReturn(){
        when(mPerson.getName()).thenReturn("小明");
        when(mPerson.getSex()).thenThrow(new NullPointerException("性别不明"));
        doReturn("小小").when(mPerson).getName();

        System.out.println(mPerson.getName());
        System.out.println(mPerson.getSex());
    }




    @Test
    public void testPersonAnswer(){
        when(mPerson.eat(anyString())).thenAnswer(new Answer<Object>() {
        @Override
            public String answer(InvocationOnMock invocation) throws Throwable {
            Object[] args = invocation.getArguments();
            return args[0].toString() + "真好吃";
        }
        });

        System.out.println(mPerson.eat("蛋饼"));
    }



    //上方都是状态测试，如果不关心返回结果，而是关心方法有否被正确的参数调用
    //则使用验证方法

    /**
     *  after(long millis)   在给定时间后进行验证
     *  timeout(long millis) 验证方法执行是否超时
     *  atLeast(int minNumberOfInvocations)   至少进行n次验证
     *  atMost(int maxNumberOfInvocations)   至多进行n次验证
     *  description(String description)   验证失败时输出的内容
     *  times(int wantedNumberOfInvocations)   验证调用方法的次数
     *  never()   验证交互没有发生,相当于times(0)
     *  only()   验证方法只被调用一次,相当于times(1)
     */


    @Test
    public void testPersonVerifyAfter(){
        when(mPerson.getAge()).thenReturn(18);
        //延时1s验证
        System.out.println(mPerson.getAge());
        System.out.println(System.currentTimeMillis());
        verify(mPerson, after(1000)).getAge();
        System.out.println(System.currentTimeMillis());

        verify(mPerson,atLeast(2)).getAge();
    }



    @Test
    public void testPersonVerifyAtLeast(){
        mPerson.getAge();
        mPerson.getAge();
        //至少验证两次
        verify(mPerson,atLeast(2)).getAge();
    }



    @Test
    public void testPersonVerifyAtMost(){
        mPerson.getAge();
        //至多验证2次
        verify(mPerson,atMost(2)).getAge();
    }



    @Test
    public void testPersonVerifyTimes(){
        mPerson.getAge();
        mPerson.getAge();
        verify(mPerson,times(2)).getAge();
    }



    @Test
    public void testPersonVerifyTimes1(){
        mPerson.getAge();
        mPerson.getAge();
        //验证方法在100ms超时前调用2次
        verify(mPerson,timeout(100).times(2)).getAge();
    }


    //参数匹配器 any+数据结构 ——匹配任何相对应的非空数据结构

    @Test
    public void testPersonAny(){
        when(mPerson.eat(any(String.class))).thenReturn("米饭");
        //或者
        //when(mPerson.eat(anyString())).thenReturn("米饭");

        //输出米饭
        System.out.println(mPerson.eat("小面"));
    }

    @Test
    public void testPersonArgThat(){
        //自定义输入字符长度为偶数时，输出小面
        when(mPerson.eat(argThat(new ArgumentMatcher<String>() {
            @Override
            public boolean matches(String argument) {
                return argument.length() % 2 == 0;
            }
        }))).thenReturn("小面");
        System.out.println(mPerson.eat("12"));
    }


    /**
     * 其他方法
     * reset(T...mocks)   重置Mock
     * spy(Class<T> classToSpy)   实现调用真实对象的实现
     * inOrder(Object...mocks)   验证执行顺序
     * @InjectMocks   自动将模拟对象注入到被测试对象中
     */

    @Test
    public void testPersonInOrder(){
        mPerson.setName("小明");
        mPerson.setSex(1);

        mPerson.setName("小红");
        mPerson.setSex(0);

        InOrder inOrder = inOrder(mPerson,mPerson1);
        //执行顺序正确
        inOrder.verify(mPerson).setName("小明");
        inOrder.verify(mPerson).setSex(1);

        //执行顺序错误
        inOrder.verify(mPerson1).setSex(0);
        inOrder.verify(mPerson1).setName("小红");

    }

}




