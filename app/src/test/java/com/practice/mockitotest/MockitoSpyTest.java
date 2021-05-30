package com.practice.mockitotest;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.inOrder;

//spy(Class<T> classToSpy)   实现调用真实对象的实现
public class MockitoSpyTest {

    @Spy
    Person mPerson;
    Person mPerson1;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Test
    public void testIsNotNull(){
        assertNotNull(mPerson);
    }

    @Test
    public void testPersonSpy(){
        //输出11
        System.out.println(mPerson.getAge());
    }



}
