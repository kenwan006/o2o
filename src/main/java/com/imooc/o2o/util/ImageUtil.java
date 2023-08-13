package com.imooc.o2o.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.imooc.o2o.dto.ImageHolder;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

public class ImageUtil {
	private static String basePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
	private static final SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
	private static final Random r = new Random();
	private static Logger logger = LoggerFactory.getLogger(ImageUtil.class);
	
	/**
	 * Transfer CommonsMultipartFile to File class
	 * 
	 * @param cFile
	 * @return
	 */
	public static File transferCommonsMultipartFileToFile(CommonsMultipartFile cFile) {
		File newFile = new File(cFile.getOriginalFilename());
		try {
			cFile.transferTo(newFile);
		} catch (IllegalStateException e) {
			logger.error(e.toString());
			e.printStackTrace();
		} catch (IOException e) {
			logger.error(e.toString());
			e.printStackTrace();
		}
		return newFile;
	}

	
	/**
	 * Process thumbnail image, and return the relative path of the generated image
	 * 
	 * @param thumbnail
	 * @param targetAddr
	 * @return
	 */
	public static String generateThumbnail(ImageHolder thumbnail, String targetAddr) {
		// get a random file name
		String realFileName = getRandomFileName();
		// get the file's extension name, such as png, jpg.
		String extension = getFileExtension(thumbnail.getImageName());
		// create if the target path does not exist
		makeDirPath(targetAddr);
		// get the relative path of the file
		String relativeAddr = targetAddr + realFileName + extension;
		logger.debug("current relativeAddr is :" + relativeAddr);
		// get the target path of the file to be stored
		File dest = new File(PathUtil.getImgBasePath() + relativeAddr);
		logger.debug("current complete addr is :" + PathUtil.getImgBasePath() + relativeAddr);
		logger.debug("basePath is :" + basePath);
		// call Thumbnails to generate watermark for the image
		try {
			Thumbnails.of(thumbnail.getImage()).size(200, 200)
					.watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath + "/watermark.jpg")), 0.25f)
					.outputQuality(0.8f).toFile(dest);
		} catch (IOException e) {
			logger.error(e.toString());
			throw new RuntimeException("Unsuccessful to create thumbnail image: " + e.toString());
		}
		// return the relative path of the image
		return relativeAddr;
	}
	
	/**
	 * 
	 * process image, and return the absolute path for the generated images
	 * 
	 * @param thumbnail
	 * @param targetAddr
	 * @return
	 */
	public static String generateNormalImg(ImageHolder thumbnail, String targetAddr) {
		// get a random file name
		String realFileName = getRandomFileName();
		// get the file's extension name, such as png, jpg.
		String extension = getFileExtension(thumbnail.getImageName());
		// create if the target path does not exist
		makeDirPath(targetAddr);
		// get the relative path of the file
		String relativeAddr = targetAddr + realFileName + extension;
		logger.debug("current relativeAddr is :" + relativeAddr);
		// get the target path of the file to be stored
		File dest = new File(PathUtil.getImgBasePath() + relativeAddr);
		logger.debug("current complete addr is :" + PathUtil.getImgBasePath() + relativeAddr);
		// call Thumbnails to generate watermark for the image
		try {
			Thumbnails.of(thumbnail.getImage()).size(337, 640)
					.watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath + "/watermark.jpg")), 0.25f)
					.outputQuality(0.9f).toFile(dest);
		} catch (IOException e) {
			logger.error(e.toString());
			throw new RuntimeException("Unsuccessful to create thumbnail image: " + e.toString());
		}
		// return the relative path of the image
		return relativeAddr;
	}
	
	/**
	 * create the all directory needed for the target path. 
	 * eg: /Users/chaowan/pictures/images/xxx.jpg, 
	 * then the directories including users, chaowan, pictures, images will be created
	 * 
	 * @param targetAddr
	 */
	private static void makeDirPath(String targetAddr) {
		String realFileParentPath = PathUtil.getImgBasePath() + targetAddr;
		File dirPath = new File(realFileParentPath);
		if (!dirPath.exists()) {
			dirPath.mkdirs();
		}
	}
	
	/**
	 * get the extension of the input file's name
	 * 
	 * @param thumbnail
	 * @return
	 */
	private static String getFileExtension(String fileName) {
		return fileName.substring(fileName.lastIndexOf("."));
	}
	
	/**
	 * generate the file name randomly, current time + a random 5 digit number
	 * 
	 * @return
	 */
	public static String getRandomFileName() {
		// get a random 5-digit number
		int rannum = r.nextInt(89999) + 10000;
		String nowTimeStr = sDateFormat.format(new Date());
		return nowTimeStr + rannum;
	}
	
	
	public static void main(String[] args) throws IOException {
		Thumbnails.of(new File("/Users/chaowan/Pictures/minions.png")).size(200, 200)
		.watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath + "/watermark.jpg")), 0.25f)
		.outputQuality(0.8f).toFile("/Users/chaowan/Pictures/new_minions.png");
	}
	
	/**
	 * check if storePath is the path for a file or for a directory
	 * If it's for a file, then delete the file; if it's for a directory then delete all files within this directory
	 * @param storePath
	 */
	public static void deleteFileOrPath(String storePath) {
		File fileOrPath = new File(PathUtil.getImgBasePath() + storePath);
		if (fileOrPath.exists()) {
			if (fileOrPath.isDirectory()) {
				File files[] = fileOrPath.listFiles();
				for (int i = 0; i < files.length; i++) {
					files[i].delete();
				}
			}
			fileOrPath.delete();
		}
	}
}
