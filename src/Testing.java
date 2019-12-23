import static org.junit.jupiter.api.Assertions.*;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

class Testing {
	Calculate calc = new Calculate();
	Robot r;
	
	int[] inputTest1 = {KeyEvent.VK_1,KeyEvent.VK_R,'.',KeyEvent.VK_5};
	int[] inputTest2 = {'.',KeyEvent.VK_0,'.',KeyEvent.VK_5};
	int[] inputTest3 = {KeyEvent.VK_1,KeyEvent.VK_0,KeyEvent.VK_2,KeyEvent.VK_BACK_SPACE};
	
	@Test
	void test1() {
		System.out.println("Test1");
		try {
			r = new Robot();
			r.delay(100);
		}
		catch (AWTException e) {
			e.printStackTrace();
		}
		for (int key:inputTest1) {
			r.keyPress(key);
			r.keyRelease(key);
		}
		calc.result.doClick();
	}
	@Test
	void test2() {
		System.out.println("Test2");
		try {
			r = new Robot();
			r.delay(100);
		} catch (AWTException e) {
			e.printStackTrace();
		}
		for (int key:inputTest2) {
			r.keyPress(key);
			r.keyRelease(key);
		}
		
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if (calc.textbox_collection[0].getText().compareTo("0.05")!=0) {
			fail();
		}
		
	}
	@Test
	void test3() {
		System.out.println("Test3");
		try {
			r = new Robot();
			r.delay(100);
		} catch (AWTException e) {
			e.printStackTrace();
		}
		for (int key:inputTest3) {
			r.keyPress(key);
			r.keyRelease(key);
		}
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if (calc.textbox_collection[0].getText().compareTo("10")!=0) {
			fail();
		}
	}
}
