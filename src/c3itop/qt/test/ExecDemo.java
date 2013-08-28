/**
 * @author VerpHen
 * @date 2013年8月27日  上午9:24:59
 */

package c3itop.qt.test;

import java.io.IOException;

public class ExecDemo {
	public static void main(String[] args) {
		System.out.println("---------------");

		/* 临时Bat文件中的内容 */
		String qtBatContext = "f: \n cd "
				+ "F:\\runtime-EclipseApplication\\hello"
				+ "\n  call \"C:\\Program Files\\Microsoft Visual Studio 10.0\\VC\\vcvarsall.bat\" \n  nmake \n  goto :eof";

		try {

			Runtime rt = Runtime.getRuntime();
			rt.exec("cmd /c echo " + qtBatContext + "> temp.bat");

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
