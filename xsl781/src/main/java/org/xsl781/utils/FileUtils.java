/*
 * 
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.xsl781.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.channels.FileChannel;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

/**
 * 文件与流处理工具类<br>
 * 
 * <b>创建时间</b> 2014-8-14
 * 
 * 
 * @version 1.1
 */
public final class FileUtils {
	/**
	 * 检测SD卡是否存在
	 */
	public static boolean checkSDcard() {
		return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
	}

	/**
	 * 将文件保存到本地
	 */
	public static void saveFileCache(byte[] fileData, String folderPath, String fileName) {
		File folder = new File(folderPath);
		folder.mkdirs();
		File file = new File(folderPath, fileName);
		ByteArrayInputStream is = new ByteArrayInputStream(fileData);
		OutputStream os = null;
		if (!file.exists()) {
			try {
				file.createNewFile();
				os = new FileOutputStream(file);
				byte[] buffer = new byte[1024];
				int len = 0;
				while (-1 != (len = is.read(buffer))) {
					os.write(buffer, 0, len);
				}
				os.flush();
			} catch (Exception e) {
				throw new RuntimeException(FileUtils.class.getClass().getName(), e);
			} finally {
				closeIO(is, os);
			}
		}
	}

	/**
	 * 从指定文件夹获取文件
	 * 
	 * @return 如果文件不存在则创建,如果如果无法创建文件或文件名为空则返回null
	 */
	public static File getSaveFile(String folderPath, String fileNmae) {
		File file = new File(folderPath + File.separator + fileNmae);
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		try {
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return file;
	}

	public static File getFileAndDeleteExist(String folderPath, String fileNmae) {
		File file = new File(folderPath, fileNmae);
		if (file.exists()) {
			file.delete();
		}
		try {
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return file;
	}

	/**
	 * 获取SD卡下指定文件夹的绝对路径
	 * 
	 * @return 返回SD卡下的指定文件夹的绝对路径
	 */
	public static String getSavePath(String folderName) {
		return getSaveFolder(folderName).getAbsolutePath();
	}

	/**
	 * 获取文件夹对象
	 * 
	 * @return 返回SD卡下的指定文件夹对象，若文件夹不存在则创建
	 */
	public static File getSaveFolder(String folderName) {
		File file = new File(getSDCardPath() + File.separator + folderName + File.separator);
		file.mkdirs();
		return file;
	}

	public static String getSDCardPath() {
		return Environment.getExternalStorageDirectory().getAbsolutePath();
	}

	/**
	 * 输入流转byte[]<br>
	 */
	public static final byte[] input2byte(InputStream inStream) {
		if (inStream == null) {
			return null;
		}
		byte[] in2b = null;
		BufferedInputStream in = new BufferedInputStream(inStream);
		ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
		int rc = 0;
		try {
			while ((rc = in.read()) != -1) {
				swapStream.write(rc);
			}
			in2b = swapStream.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			closeIO(inStream, in, swapStream);
		}
		return in2b;
	}

	/**
	 * 把uri转为File对象
	 */
	@SuppressLint("NewApi")
	public static File uri2File(Activity aty, Uri uri) {
		if (SystemTool.getSDKVersion() < 11) {
			// 在API11以下可以使用：managedQuery
			String[] proj = { MediaStore.Images.Media.DATA };
			@SuppressWarnings("deprecation")
			Cursor actualimagecursor = aty.managedQuery(uri, proj, null, null, null);
			int actual_image_column_index = actualimagecursor
					.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			actualimagecursor.moveToFirst();
			String img_path = actualimagecursor.getString(actual_image_column_index);
			return new File(img_path);
		} else {
			// 在API11以上：要转为使用CursorLoader,并使用loadInBackground来返回
			String[] projection = { MediaStore.Images.Media.DATA };
			CursorLoader loader = new CursorLoader(aty, uri, projection, null, null, null);
			Cursor cursor = loader.loadInBackground();
			int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			cursor.moveToFirst();
			return new File(cursor.getString(column_index));
		}
	}

	public static void inputToOutput(FileOutputStream outputStream, InputStream inputStream)
			throws IOException {
		byte[] buffer = new byte[1024];
		int len;
		while ((len = inputStream.read(buffer)) != -1) {
			outputStream.write(buffer, 0, len);
		}
		outputStream.close();
		inputStream.close();
	}

	public static void bytesToFile(final File file, byte[] bytes) throws
			IOException {
		FileOutputStream output = new FileOutputStream(file);
		output.write(bytes);
		output.close();
	}

	/**
	 * 复制文件
	 * 
	 * @param from
	 * @param to
	 */
	public static void copyFile(File from, File to) {
		if (null == from || !from.exists()) {
			return;
		}
		if (null == to) {
			return;
		}
		FileInputStream is = null;
		FileOutputStream os = null;
		try {
			is = new FileInputStream(from);
			if (!to.exists()) {
				to.createNewFile();
			}
			os = new FileOutputStream(to);
			copyFileFast(is, os);
		} catch (Exception e) {
			throw new RuntimeException(FileUtils.class.getClass().getName(), e);
		} finally {
			closeIO(is, os);
		}
	}

	/**
	 * 快速复制文件（采用nio操作）
	 * 
	 * @param is
	 *            数据来源
	 * @param os
	 *            数据目标
	 * @throws IOException
	 */
	public static void copyFileFast(FileInputStream is, FileOutputStream os) throws IOException {
		FileChannel in = is.getChannel();
		FileChannel out = os.getChannel();
		in.transferTo(0, in.size(), out);
	}

	/**
	 * 关闭流
	 * 
	 * @param closeables
	 */
	public static void closeIO(Closeable... closeables) {
		if (null == closeables || closeables.length <= 0) {
			return;
		}
		for (Closeable cb : closeables) {
			try {
				if (null == cb) {
					continue;
				}
				cb.close();
			} catch (IOException e) {
				throw new RuntimeException(FileUtils.class.getClass().getName(), e);
			}
		}
	}

	/**
	 * 图片写入文件
	 * 
	 * @param bitmap
	 *            图片
	 * @param filePath
	 *            文件路径
	 * @return 是否写入成功
	 */
	public static boolean bitmapToFile(Bitmap bitmap, String filePath) {
		boolean isSuccess = false;
		if (bitmap == null) {
			return isSuccess;
		}
		OutputStream out = null;
		try {
			out = new BufferedOutputStream(new FileOutputStream(filePath), 8 * 1024);
			isSuccess = bitmap.compress(CompressFormat.PNG, 70, out);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			closeIO(out);
		}
		return isSuccess;
	}

	/**
	 * 从文件中读取文本
	 * 
	 * @param filePath
	 * @return
	 */
	public static String readFile(String filePath) {
		InputStream is = null;
		try {
			is = new FileInputStream(filePath);
		} catch (Exception e) {
			throw new RuntimeException(FileUtils.class.getName() + "readFile---->" + filePath
					+ " not found");
		}
		return readStream2String(is);
	}

	/**
	 * 读取字符串
	 * 
	 * @param file
	 * @return
	 */

	public static String readFile(File file) {
		String str = null;
		if (!file.exists()) {
			return null;
		}
		ByteArrayOutputStream stream = null;
		FileInputStream inStream = null;
		try {
			inStream = new FileInputStream(file);
			stream = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int length = -1;
			while ((length = inStream.read(buffer)) != -1) {
				stream.write(buffer, 0, length);
			}
			str = stream.toString();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} finally {

			try {
				if (stream != null)
					stream.close();
				if (inStream != null)
					inStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		return str;
	}

	/**
	 * 指定起始行，读取字符串
	 * 
	 * @param fileName
	 * @return
	 */

	public static String readFile(String fileName, int startRow, int endRow) {
		String str = null;
		File readFile = new File(fileName);
		if (!readFile.exists()) {
			return null;
		}
		int lineNum = getTotalLines(fileName);
		if (endRow > lineNum)
			endRow = lineNum;
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(readFile));
			int rowNum = 1;
			String lineStr = br.readLine();
			StringBuffer sb = new StringBuffer();
			while (lineStr != null) {
				if (rowNum >= startRow && rowNum <= endRow) {
					sb.append(lineStr);
				}
				if (rowNum == endRow)
					return sb.toString();
				rowNum++;
				lineStr = br.readLine();
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} finally {

			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		return str;
	}

	public static InputStream readFile2Is(String filePath) {
		InputStream is = null;
		try {
			is = new FileInputStream(filePath);
		} catch (Exception e) {
			throw new RuntimeException(FileUtils.class.getName() + "readFile---->" + filePath
					+ " not found");
		}
		return is;
	}


	public static byte[] readStream2Bytes(InputStream inStream) throws Exception {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len = inStream.read(buffer)) != -1) {
			outStream.write(buffer, 0, len);
		}
		outStream.close();
		inStream.close();
		return outStream.toByteArray();
	}

	/**
	 * 从assets中读取文本
	 * 
	 * @param name
	 * @return
	 */
	public static String readFileFromAssets(Context context, String name) {
		InputStream is = null;
		try {
			is = context.getResources().getAssets().open(name);
		} catch (Exception e) {
			throw new RuntimeException(FileUtils.class.getName() + ".readFileFromAssets---->"
					+ name + " not found");
		}
		return readStream2String(is);
	}

	/**
	 * 输入流转字符串
	 * 
	 * @param is
	 * @return 一个流中的字符串
	 */
	public static String readStream2String(InputStream is) {
		if (null == is) {
			return null;
		}
		StringBuilder resultSb = null;
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			resultSb = new StringBuilder();
			String len;
			while (null != (len = br.readLine())) {
				resultSb.append(len);
			}
		} catch (Exception ex) {
		} finally {
			closeIO(is);
		}
		return null == resultSb ? null : resultSb.toString();
	}

	/**
	 * 字符串保存 保存成功后 返回true
	 * @param content
	 */
	public static boolean save(String fileName, String content) {
		FileOutputStream out = null;
		// String name = path.substring(path.lastIndexOf("/"));
		File file = new File(fileName);
		try {
			if (!file.exists()) {
				file.getParentFile().mkdirs();
				file.createNewFile();
			}

			out = new FileOutputStream(file);
			out.write(content.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return true;
	}

	/**
	 * 向文档中追加字符串
	 * @param content
	 */
	public static void saveAppendStr(String fileName, String content) {
		File file = new File(fileName);
		FileWriter writer = null;
		try {
			if (!file.exists()) {
				file.getParentFile().mkdirs();
				file.createNewFile();
			}
			writer = new FileWriter(file, true);
			writer.write(content);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Save Bitmap to a file.保存图片到SD卡。
	 * 
	 * @param bitmap
	 * @return error message if the saving is failed. null if the saving is
	 *         successful.
	 * @throws IOException
	 */
	public static boolean saveBitmapToFile(Bitmap bitmap, String _file) {
		BufferedOutputStream os = null;
		try {
			File file = new File(_file);
			if (file.exists())
				file.delete();
			int end = _file.lastIndexOf(File.separator);
			String _filePath = _file.substring(0, end);
			File filePath = new File(_filePath);
			if (!filePath.exists()) {
				filePath.mkdirs();
			}
			file.createNewFile();

			os = new BufferedOutputStream(new FileOutputStream(file));
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} finally {
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
					// Log.e(TAG_ERROR, e.getMessage(), e);
				}
			}
		}
		return true;
	}

	/**
	 * 从网络上获取图片，如果图片在本地存在的话就直接拿，如果不存在再去服务器上下载图片 这里的fileName是图片的名称 ,url为下载图片的地址
	 */
	public static Uri getImageURI(String fileUrl, String url) throws Exception {

		File file = new File(fileUrl);
		// 如果图片存在本地缓存目录，则不去服务器下载
		if (file.exists()) {
			return Uri.fromFile(file);// Uri.fromFile(path)这个方法能得到文件的URI
		} else {
			// 从网络上获取图片
			URL _url = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) _url.openConnection();
			conn.setConnectTimeout(5000);
			conn.setRequestMethod("GET");
			conn.setDoInput(true);
			if (conn.getResponseCode() == 200) {

				InputStream is = conn.getInputStream();
				FileOutputStream fos = new FileOutputStream(file);
				byte[] buffer = new byte[1024];
				int len = 0;
				while ((len = is.read(buffer)) != -1) {
					fos.write(buffer, 0, len);
				}
				is.close();
				fos.close();
				// 返回一个URI对象
				return Uri.fromFile(file);
			}
		}
		return null;
	}

	/**
	 * 文件内容的总行数。
	 * @return
	 * @throws IOException
	 */
	public static int getTotalLines(String fileName) {
		File readFile = new File(fileName);
		FileReader in = null;
		LineNumberReader reader = null;
		int lines = 0;
		try {
			in = new FileReader(readFile);
			reader = new LineNumberReader(in);
			String s = reader.readLine();
			while (s != null) {
				lines++;
				s = reader.readLine();
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return lines;
	}

	/**
	 * 删除指定文件
	 */
	public static void deleteFile(String file) {
		File readFile = new File(file);
		if (readFile.exists()) {
			readFile.delete();
		}
	}

	/**
	 * 从指定位置开始读取字符串，暂未实现
	 * 
	 * @param position
	 * @return
	 */
	public static String readStrAppointPosition(int position) {

		return null;
	}

	/**
	 * 判断文件是否存在
	 * @return
	 */

	public static boolean fileIsExist(String _file) {

		File file = new File(_file);
		return file.exists();
	}


	public static InputStream readFileReturnIs(String filePath) {
		InputStream is = null;
		try {
			is = new FileInputStream(filePath);
		} catch (Exception e) {
			throw new RuntimeException(FileUtils.class.getName() + "readFile---->" + filePath
					+ " not found");
		}
		return is;
	}

	/**
	 * 输入流转字符串
	 *
	 * @param is
	 * @return 一个流中的字符串
	 */
	public static String inputStream2String(InputStream is) {
		if (null == is) {
			return null;
		}
		StringBuilder resultSb = null;
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			resultSb = new StringBuilder();
			String len;
			while (null != (len = br.readLine())) {
				resultSb.append(len);
			}
		} catch (Exception ex) {
		} finally {
			closeIO(is);
		}
		return null == resultSb ? null : resultSb.toString();
	}

	/**
	 * 判断路径下是否存在文件
	 * 
	 * @param path
	 * @return
	 */
	public static boolean isFilesExist(String path) {
		File file = new File(path);
		File[] files = file.listFiles();
		return files != null && files.length > 0;
	}

	public static void clearDir(String dir) {
		File file = new File(dir);
		File[] fs = file.listFiles();
		for (File f : fs) {
			f.delete();
		}
	}

	/**
	 * 得到路径下所有文件
	 * 
	 * @param path
	 * @return
	 */
	public static File[] getFiles(String path) {
		File file = new File(path);
		File[] files = file.listFiles();
		return files;
	}

	public static boolean copyFile(String fromPath, String toPath) {
		try {
			File oldfile = new File(fromPath);
			if (oldfile.exists()) { // 文件存在时
				InputStream inStream = new FileInputStream(fromPath); // 读入原文件
				FileOutputStream fs = new FileOutputStream(toPath);
				byte[] b = new byte[1024];
				int byteRead = 0;
				int length;
				while ((byteRead = inStream.read(b)) != -1) {
					fs.write(b, 0, byteRead);
				}
				inStream.close();
				fs.close();
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}

	/*
	 * 创建文件
	 */
	public static File getFilePath(String filePath, String fileName) {
		File file = null;
		makeRootDirectory(filePath);
		try {
			file = new File(filePath + fileName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return file;
	}

	public static void makeRootDirectory(String filePath) {
		File file = null;
		try {
			file = new File(filePath);
			if (!file.exists()) {
				file.mkdir();
			}
		} catch (Exception e) {

		}
	}

}