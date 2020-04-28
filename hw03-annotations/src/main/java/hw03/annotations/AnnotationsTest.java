package hw03.annotations;

public class AnnotationsTest {

    @Before
    public String doSomeWorkBefore() {
        return "do some work before";
    }

    @Before
    public String doSomeWorkBefore2() {
        return "do some work before 2";
    }

    @After
    public String doSomeWorkAfter() {
        return "do some work after";
    }

    @Test
    public String doSomeTest1() throws Exception {
        throw new Exception();
    }

    @Test
    public String doSomeTest2() {
        return "do some work in test 2";
    }

    @Test
    public String doSomeTest3() {
        return "do some work in test 3";
    }

    @Test
    public String doSomeTest4() {
        return "do some work in test 4";
    }

    @Test
    public String doSomeTest5() {
        return "do some work in test 5";
    }

    public String nop() {
        return "it must not run! ";
    }

    @Test
    public String doSomeTest6() {
        return "do some work in test 6";
    }
}
