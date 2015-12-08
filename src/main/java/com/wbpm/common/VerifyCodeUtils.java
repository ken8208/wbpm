package com.wbpm.common;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Random;

import javax.imageio.ImageIO;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;

/**
 * 随机验证码生成工具类
 * @author <a href="mailto:ken.wu@wolaidai.com">ken.wu</a>
 */
public abstract class VerifyCodeUtils
{
	// 使用到Algerian字体，系统里没有的话需要安装字体，字体只显示大写，去掉了1,0,i,o几个容易混淆的字符
	public static final String VERIFY_CODES = "23456789ABCDEFGHJKLMNPQRSTUVWXYZ";
	private static Random random = new Random();
	
	/**
	 * 使用系统默认字符源生成验证码
	 * 
	 * @param verifySize
	 *            验证码长度
	 * @return
	 */
	public static String generateVerifyCode(int verifySize)
	{
		return generateVerifyCode(verifySize, VERIFY_CODES);
	}

	/**
	 * 使用指定源生成验证码
	 * 
	 * @param verifySize
	 *            验证码长度
	 * @param sources
	 *            验证码字符源
	 * @return
	 */
	public static String generateVerifyCode(int verifySize, String sources)
	{
		if (sources == null || sources.length() == 0)
		{
			sources = VERIFY_CODES;
		}
		int codesLen = sources.length();
		Random rand = new Random(System.currentTimeMillis());
		StringBuilder verifyCode = new StringBuilder(verifySize);
		for (int i = 0; i < verifySize; i++)
		{
			verifyCode.append(sources.charAt(rand.nextInt(codesLen - 1)));
		}
		return verifyCode.toString();
	}

	/**
	 * 生成随机验证码文件,并返回验证码值
	 * 
	 * @param w
	 * @param h
	 * @param outputFile
	 * @param verifySize
	 * @return
	 * @throws IOException
	 */
	public static String outputVerifyImage(int w, int h, File outputFile, int verifySize) throws IOException
	{
		String verifyCode = generateVerifyCode(verifySize);
		outputImage(w, h, outputFile, verifyCode);
		return verifyCode;
	}

	/**
	 * 输出随机验证码图片流,并返回验证码值
	 * 
	 * @param w
	 * @param h
	 * @param os
	 * @param verifySize
	 * @return
	 * @throws IOException
	 */
	public static String outputVerifyImage(int w, int h, OutputStream os, int verifySize) throws IOException
	{
		String verifyCode = generateVerifyCode(verifySize);
		outputImage(w, h, os, verifyCode);
		return verifyCode;
	}

	/**
	 * 生成指定验证码图像文件
	 * 
	 * @param w
	 * @param h
	 * @param outputFile
	 * @param code
	 * @throws IOException
	 */
	public static void outputImage(int w, int h, File outputFile, String code) throws IOException
	{
		if (outputFile == null)
		{
			return;
		}
		File dir = outputFile.getParentFile();
		if (!dir.exists())
		{
			dir.mkdirs();
		}
		try
		{
			outputFile.createNewFile();
			FileOutputStream fos = new FileOutputStream(outputFile);
			outputImage(w, h, fos, code);
			fos.close();
		}
		catch (IOException e)
		{
			throw e;
		}
	}

	/**
	 * 输出指定验证码图片流
	 * 
	 * @param w
	 * @param h
	 * @param os
	 * @param code
	 * @throws IOException
	 */
	public static void outputImage(int w, int h, OutputStream os, String code) throws IOException
	{
		int verifySize = code.length();
		BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		Random rand = new Random();
		Graphics2D g2 = image.createGraphics();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		Color[] colors = new Color[5];
		Color[] colorSpaces =
				new Color[]{Color.WHITE, Color.CYAN, Color.GRAY, Color.LIGHT_GRAY, Color.MAGENTA, Color.ORANGE,
						Color.PINK, Color.YELLOW};
		float[] fractions = new float[colors.length];
		for (int i = 0; i < colors.length; i++)
		{
			colors[i] = colorSpaces[rand.nextInt(colorSpaces.length)];
			fractions[i] = rand.nextFloat();
		}
		Arrays.sort(fractions);

		g2.setColor(Color.GRAY);// 设置边框色
		g2.fillRect(0, 0, w, h);

		Color c = getRandColor(200, 250);
		g2.setColor(c);// 设置背景色
		g2.fillRect(0, 2, w, h - 4);

		// 绘制干扰线
		Random random = new Random();
		g2.setColor(getRandColor(160, 200));// 设置线条的颜色
		for (int i = 0; i < 20; i++)
		{
			int x = random.nextInt(w - 1);
			int y = random.nextInt(h - 1);
			int xl = random.nextInt(6) + 1;
			int yl = random.nextInt(12) + 1;
			g2.drawLine(x, y, x + xl + 40, y + yl + 20);
		}

		// 添加噪点
		float yawpRate = 0.05f;// 噪声率
		int area = (int) (yawpRate * w * h);
		for (int i = 0; i < area; i++)
		{
			int x = random.nextInt(w);
			int y = random.nextInt(h);
			int rgb = getRandomIntColor();
			image.setRGB(x, y, rgb);
		}

		shear(g2, w, h, c);// 使图片扭曲

		g2.setColor(getRandColor(100, 160));
		int fontSize = h - 4;
		Font font = new Font("Algerian", Font.ITALIC, fontSize);
		g2.setFont(font);
		char[] chars = code.toCharArray();
		for (int i = 0; i < verifySize; i++)
		{
			AffineTransform affine = new AffineTransform();
			affine.setToRotation(Math.PI / 4 * rand.nextDouble() * (rand.nextBoolean() ? 1 : -1), (w / verifySize) * i
					+ fontSize / 2, h / 2);
			g2.setTransform(affine);
			g2.drawChars(chars, i, 1, ((w - 10) / verifySize) * i + 4, h / 2 + fontSize / 2 - 6);
		}

		g2.dispose();
		ImageIO.write(image, "png", os);
	}

	public static String generateImageBase64(int w, int h, String code) throws IOException
	{
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		outputImage(w, h, outputStream, code);
		byte[] bytes = outputStream.toByteArray();
		return StringUtils.newStringUtf8(Base64.encodeBase64(bytes));
	}

	private static Color getRandColor(int fc, int bc)
	{
		if (fc > 255)
			fc = 255;
		if (bc > 255)
			bc = 255;
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}

	private static int getRandomIntColor()
	{
		int[] rgb = getRandomRgb();
		int color = 0;
		for (int c : rgb)
		{
			color = color << 8;
			color = color | c;
		}
		return color;
	}

	private static int[] getRandomRgb()
	{
		int[] rgb = new int[3];
		for (int i = 0; i < 3; i++)
		{
			rgb[i] = random.nextInt(255);
		}
		return rgb;
	}

	private static void shear(Graphics g, int w1, int h1, Color color)
	{
		shearX(g, w1, h1, color);
		shearY(g, w1, h1, color);
	}

	private static void shearX(Graphics g, int w1, int h1, Color color)
	{

		int period = random.nextInt(2);

		boolean borderGap = true;
		int frames = 1;
		int phase = random.nextInt(2);

		for (int i = 0; i < h1; i++)
		{
			double d =
					(double) (period >> 1)
							* Math.sin((double) i / (double) period + (6.2831853071795862D * (double) phase)
									/ (double) frames);
			g.copyArea(0, i, w1, 1, (int) d, 0);
			if (borderGap)
			{
				g.setColor(color);
				g.drawLine((int) d, i, 0, i);
				g.drawLine((int) d + w1, i, w1, i);
			}
		}

	}

	private static void shearY(Graphics g, int w1, int h1, Color color)
	{

		int period = random.nextInt(40) + 10; // 50;

		boolean borderGap = true;
		int frames = 20;
		int phase = 7;
		for (int i = 0; i < w1; i++)
		{
			double d =
					(double) (period >> 1)
							* Math.sin((double) i / (double) period + (6.2831853071795862D * (double) phase)
									/ (double) frames);
			g.copyArea(i, 0, 1, h1, 0, (int) d);
			if (borderGap)
			{
				g.setColor(color);
				g.drawLine(i, (int) d, i, 0);
				g.drawLine(i, (int) d + h1, i, h1);
			}

		}

	}

	public static void main(String[] args) throws IOException
	{
//		int w = 200, h = 80;
//		String verifyCode = generateVerifyCode(4);
//		File dir = new File("/home/ken/");
//		File file = new File(dir, verifyCode + ".jpg");
//		outputImage(w, h, file, verifyCode);
//		String data = generateImageBase64(w, h, verifyCode);
		String data = "/9j/4AAQSkZJRgABAgAAAQABAAD/2wBDAAgGBgcGBQgHBwcJCQgKDBQNDAsLDBkSEw8UHRofHh0aHBwgJC4nICIsIxwcKDcpLDAxNDQ0Hyc5PTgyPC4zNDL/2wBDAQkJCQwLDBgNDRgyIRwhMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjIyMjL/wAARCAAcAEIDASIAAhEBAxEB/8QAHwAAAQUBAQEBAQEAAAAAAAAAAAECAwQFBgcICQoL/8QAtRAAAgEDAwIEAwUFBAQAAAF9AQIDAAQRBRIhMUEGE1FhByJxFDKBkaEII0KxwRVS0fAkM2JyggkKFhcYGRolJicoKSo0NTY3ODk6Q0RFRkdISUpTVFVWV1hZWmNkZWZnaGlqc3R1dnd4eXqDhIWGh4iJipKTlJWWl5iZmqKjpKWmp6ipqrKztLW2t7i5usLDxMXGx8jJytLT1NXW19jZ2uHi4+Tl5ufo6erx8vP09fb3+Pn6/8QAHwEAAwEBAQEBAQEBAQAAAAAAAAECAwQFBgcICQoL/8QAtREAAgECBAQDBAcFBAQAAQJ3AAECAxEEBSExBhJBUQdhcRMiMoEIFEKRobHBCSMzUvAVYnLRChYkNOEl8RcYGRomJygpKjU2Nzg5OkNERUZHSElKU1RVVldYWVpjZGVmZ2hpanN0dXZ3eHl6goOEhYaHiImKkpOUlZaXmJmaoqOkpaanqKmqsrO0tba3uLm6wsPExcbHyMnK0tPU1dbX2Nna4uPk5ebn6Onq8vP09fb3+Pn6/9oADAMBAAIRAxEAPwD1Oe6gttvnSohbO1SeW9gOpPsKyE1LVdS3zaXBbLaKSEkuS2ZcZyQB0GR3/wD1aFxb2Co8ciQQm4cLuICl5Oq4PdgeR3yK5lbi+0fTXgb7P5dup2Tea3zc8AKOpyf84q6aud0pWNvSZ1urFdQeS6MibhLGWLfMOPuqOoHQAc55BOKi1HWXN6LHTtktwp+ZznZEec7sH5j7eo9ayLSx1aGzFvCkkCPEJLm4U7iQSW2oP73JBxz+hK3Wm3GmRytbwEafsUzwmd18zB55GNvG3vhskEY4N8kb6kuXTZmnd6/bx3Rt4HF3dSbVjWCQoAfQknaOfr1wRxk37caiLQi7SN22crC5DZOeATjoMc56k9MZPHahexXENtPY2TWdnbMCrqMSMTw2CMjt1P8A9aurt5ra4t45EumiXcMK064V348s7T1G5RjpkrjJpVI2Vu4XV7EVxq1xLezW+nWM08kOBLukEajIyMZ5z+VTK92YrdZrr7Pcb8mFSpL4ydoJB3DAycYPHUVntDq9teXkq/YoBJKZBNMzYK4AA44xx3q9peoSahDIl5EsVzBM0JZOV3j09P60WtsD8ty59uxwVjz/ANdMf0oqbNyP4Ij77iP6UVGnYfvd/wABt3HczRmO3mEBIz52NxUgjA2kYIIz344654yxoG+RJb68e48tvMUJEqKDnJyoByTzz15OOeRedy16bdwHTBYZ6inXEIWF13MUKkMjYZSMHIOfWhJrYHLS41IICzm2Y28jHLhRjcemSO/QD8MVh6xHPJfxRajdRvb5Hl20X3pCe7Acnngf49Wi6udU1G0tZrhkimySI1UEFV3AgkEjkV0drYW1mMxR5kxhpW5dunVupzgU+ZpkJue2n9djNnhvrvS2sre0hhjMflBrgk4XGMY6g98n0qDR9FuPsEf2+aVJFxtjjwpTB45HXoP1zmuioqeZpWRp7JddTlbqw1V7iWKGPUNmSqyNqACEf3iMZ/Crtj4dNpa4N/ci4Yl5GR8IWP8AsnqP88Vu0Uc3Yfs4mcLPUQABqa4Hrb5P/oVFaNFK7DkX9Nn/2Q==";
		String verifyCode = "verify_code";
		OutputStream out = new FileOutputStream("/home/ken/"+ verifyCode + ".jpg");
		byte[] bytes = Base64.decodeBase64(StringUtils.getBytesUtf8(data));
		out.write(bytes);  
		out.flush();  
		out.close();
	}
}
