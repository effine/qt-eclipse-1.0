/**
 * @author VerpHen
 * @date 2013年8月28日  下午2:46:45
 */

package c3itop.qt.test;

import java.io.File;
import java.io.IOException;

public class OSDemo {
	public static void main(String[] args) throws IOException {

		File file = new File("d:/test/hello/tt.txt");
		// file.createNewFile();
		System.out.println(file.getAbsolutePath());

		System.out.println(file.getParentFile());

	}
}
