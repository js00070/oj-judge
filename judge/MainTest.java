import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.*;

public class MainTest {

    // 其中每一个test方法代表一个测试用例，data为输入数据。assertEquals中的字符串代表输出数据

    Main calculation = new Main();


     @Test(timeout = 2000)
    public void test() {
		String data = "7 -7 -11 0 -2 -1 -4 1";
        InputStream stdin = System.in;
		final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
        try {
            System.setIn(new ByteArrayInputStream(data.getBytes()));
            calculation.main(null);
        } finally {
            System.setIn(stdin);
        }
        assertEquals("-11 -7 -4 -2 -1 0 1", outContent.toString().trim());
		//assertTrue("NO\r\n".equals(outContent.toString())||"NO".equals(outContent.toString())); 
		
    }
	@Test(timeout = 2000)
    public void test2() {
		String data = "5 -4 -1 0 3 10";
        InputStream stdin = System.in;
		final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
        try {
            System.setIn(new ByteArrayInputStream(data.getBytes()));
            calculation.main(null);
        } finally {
            System.setIn(stdin);
        }
        assertEquals("-4 -1 0 3 10", outContent.toString().trim());
		//assertTrue("NO\r\n".equals(outContent.toString())||"NO".equals(outContent.toString())); 
		
    }
	@Test(timeout = 2000)
    public void test3() {
		String data = "5 11 -7 -3 2 3";
        InputStream stdin = System.in;
		final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
        try {
            System.setIn(new ByteArrayInputStream(data.getBytes()));
            calculation.main(null);
        } finally {
            System.setIn(stdin);
        }
        assertEquals("-7 -3 2 3 11", outContent.toString().trim());
		//assertTrue("NO\r\n".equals(outContent.toString())||"NO".equals(outContent.toString())); 
		
    }
	@Test(timeout = 2000)
    public void test4() {
		String data = "6 0 1 3 4 5 6";
        InputStream stdin = System.in;
		final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
        try {
            System.setIn(new ByteArrayInputStream(data.getBytes()));
            calculation.main(null);
        } finally {
            System.setIn(stdin);
        }
        assertEquals("0 1 3 4 5 6", outContent.toString().trim());
		//assertTrue("NO\r\n".equals(outContent.toString())||"NO".equals(outContent.toString())); 
		
    }
	@Test(timeout = 2000)
    public void test5() {
		String data = "11 -5 -4 -3 -2 -1 0 1 2 3 4 5";
        InputStream stdin = System.in;
		final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
        try {
            System.setIn(new ByteArrayInputStream(data.getBytes()));
            calculation.main(null);
        } finally {
            System.setIn(stdin);
        }
        assertEquals("-5 -4 -3 -2 -1 0 1 2 3 4 5", outContent.toString().trim());
		//assertTrue("NO\r\n".equals(outContent.toString())||"NO".equals(outContent.toString())); 
		
    }

}