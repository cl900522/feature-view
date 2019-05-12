package acme.mock.mockito;

import acme.domain.Person;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@ExtendWith(MockitoExtension.class)
//@RunWith(MockitoJUnitRunner.StrictStubs.class)
public class
MockitoTest {
    @Test
    public void mockTest() {
        List mockList = Mockito.mock(ArrayList.class);
        Mockito.when(mockList.get(0)).thenReturn("1", "2", "3");
        Assertions.assertEquals(mockList.get(0), "1");
        Assertions.assertEquals(mockList.get(0), "2");
        Assertions.assertEquals(mockList.get(0), "3");

        Mockito.doReturn("4").when(mockList).get(1);
        Assertions.assertEquals(mockList.get(1), "4");

        Mockito.when(mockList.size()).thenReturn(100);
        Assertions.assertEquals(mockList.size(), 100);

        Mockito.doThrow(new IndexOutOfBoundsException()).when(mockList).get(100);
        try {
            mockList.get(100);
        } catch (Exception e) {
            Assertions.assertEquals(e.getClass(), IndexOutOfBoundsException.class);
        }
    }

    @Test
    public void spyTest() {
        List<String> arrayList = new ArrayList();
        arrayList.add("100");
        Assertions.assertEquals(arrayList.get(0), "100");

        List spyList = Mockito.spy(arrayList);
        Mockito.when(spyList.get(0)).thenReturn("1");

        Mockito.when(spyList.get(0)).thenReturn("1");
        Assertions.assertEquals(spyList.get(0), "1");

        Mockito.when(spyList.size()).thenReturn(100);
        Assertions.assertEquals(spyList.size(), 100);
        Assertions.assertEquals(arrayList.size(), 1);
    }

    @Test
    public void verifyNumTest() {
        List mockedList = Mockito.mock(List.class);
        List mockTwo = Mockito.mock(List.class);
        List mockThree = Mockito.mock(List.class);

        //using mock
        mockedList.add("once");

        mockedList.add("twice");
        mockedList.add("twice");

        mockedList.add("three times");
        mockedList.add("three times");
        mockedList.add("three times");

        //following two verifications work exactly the same - times(1) is used by default
        verify(mockedList).add("once");
        verify(mockedList, times(1)).add("once");

        //exact number of invocations verification
        verify(mockedList, times(2).description("add should be called twice")).add("twice");
        verify(mockedList, times(3)).add("three times");

        //verification using never(). never() is an alias to times(0)
        verify(mockedList, never()).add("never happened");

        //verification using atLeast()/atMost()
        verify(mockedList, atLeastOnce()).add("three times");
        verify(mockedList, atLeast(2)).add("three times");
        verify(mockedList, atMost(5)).add("three times");
        verify(mockedList, never()).add("four times");

        //verify that other mocks were not interacted
        verifyZeroInteractions(mockTwo, mockThree);
    }

    @Test
    public void verifyRedunantTest() {
        List mockedList = Mockito.mock(List.class);
        //using mocks
        mockedList.add("one");
        mockedList.add("two");

        verify(mockedList).add("one");

        reset(mockedList);
        //following verification will fail
        verifyNoMoreInteractions(mockedList);
    }

    @Test
    public void verifyOrderTest() {
        // A. Single mock whose methods must be invoked in a particular order
        List singleMock = Mockito.mock(List.class);

        //using a single mock
        singleMock.add("was added first");
        singleMock.add("was added second");

        //create an inOrder verifier for a single mock
        InOrder inOrder = Mockito.inOrder(singleMock);

        //following will make sure that add is first called with "was added first", then with "was added second"
        inOrder.verify(singleMock).add("was added first");
        inOrder.verify(singleMock).add("was added second");

        // B. Multiple mocks that must be used in a particular order
        List firstMock = Mockito.mock(List.class);
        List secondMock = Mockito.mock(List.class);

        //using mocks
        firstMock.add("was called first");
        secondMock.add("was called second");

        //create inOrder object passing any mocks that need to be verified in order
        inOrder = Mockito.inOrder(firstMock, secondMock);

        //following will make sure that firstMock was called before secondMock
        inOrder.verify(firstMock).add("was called first");
        inOrder.verify(secondMock).add("was called second");

        // Oh, and A + B can be mixed together at will
    }

    @Test
    public void verifyOuttime() {
        // pass test
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(100);
                } catch (Exception e) {
                }

                beTestedObj.outTime(1000L);
            }
        }).start();
        //此处会进行阻塞500ms，然后判断outTime方法是否执行，
        // 1. 上面的线程会异步等待100ms后执行outTime。所以成功！！！
        // 2. 上面的线程会异步等待600ms后执行outTime。所以失败！！！
        verify(beTestedObj, timeout(500)).outTime(1000L);
    }

    @Mock
    private List mockList;
    @Spy
    private ArrayList spyList;
    @InjectMocks
    @Spy
    private BeTestedObj beTestedObj;

    @BeforeEach
    public void initAnno() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void annoTest() {
        Mockito.mockingDetails(mockList).isMock();
        Mockito.mockingDetails(spyList).isSpy();

        Mockito.doCallRealMethod().when(spyList).add("Hello");
        spyList.add("Hello");
        Mockito.doCallRealMethod().when(spyList).get(0);
        Assertions.assertEquals(spyList.get(0), "Hello");

        when(mockList.size()).thenReturn(10);
        mockList.add(1);
        //Fail here
        //Mockito.verifyNoMoreInteractions(mockList);

        reset(mockList);
        //at this point the mock forgot any interactions & stubbing
        //Success here
        Mockito.verifyNoMoreInteractions(mockList);
    }

    @Test
    public void args() {
        Person person = new Person();
        person.setFirstName("Chen");
        beTestedObj.say(person);

        ArgumentCaptor<Person> argument = ArgumentCaptor.forClass(Person.class);
        verify(beTestedObj).say(argument.capture());
        //verify(mock).doSomething(argument.capture());
        Assertions.assertEquals("Chen", argument.getValue().getFirstName());
    }

    @Test
    public void bddTest() {
        List bddList = Mockito.mock(ArrayList.class);
        //given
        given(bddList.size()).willReturn(12);
        bddList.size();

        then(bddList).should(times(1)).size();
    }

    @Test
    public void lenientTest() {
        List mockList = mock(ArrayList.class);
        //即使没有调用mockList.get(0)也不会抛出异常，也可用下发方式设置
        //List mockList = mock(ArrayList.class, withSettings().lenient());
        lenient().when(mockList.get(0)).thenReturn("ok");

        //没有调用会抛出异常
        //Mockito.when(mockList.get(0)).thenReturn("1");
    }
}
