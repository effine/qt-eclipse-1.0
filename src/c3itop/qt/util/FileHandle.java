/**
 * @author VerpHen
 * @date 2013年8月22日  上午9:33:59
 */

package c3itop.qt.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 针对本地文件的处理类
 */
public class FileHandle {

	/**
	 * 新建文件
	 * 
	 * @param pathNmae
	 *            文件所在路径及文件名
	 * @param context
	 *            往文件中写入的内容
	 */
	public File createBatFile(String pathNmae, String context) {
		File file = new File(pathNmae);
		try {
			FileWriter fw = new FileWriter(file, true);
			fw.write(context);
			fw.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return file;
	}

	/**
	 * 删除文件
	 * 
	 * @param pathName
	 *            删除文件的完整文件名（包含路径）
	 */
	public void delBatFile(File file) {
		if (file.isFile() && file.exists()) {
			if (file.delete())
				System.out.println("Bat文件删除成功");
			else
				System.out.println("Bat文件删除失败");
		}
	}

	/**
	 * 以行为单位读取文件，常用于读面向行的格式化文件
	 * 
	 * @param file
	 *            需要读取的文件名
	 */
	public void readFileByLines(String file) {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			int line = 1;
			// 一次读入一行，直到读入null为文件结束
			while ((tempString = reader.readLine()) != null) {
				// 显示行号
				System.out.println("line " + line + ": " + tempString);
				line++;
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
	}

}
