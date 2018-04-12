package com.tri.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.ListFolderErrorException;
import com.dropbox.core.v2.files.ListFolderResult;
import com.dropbox.core.v2.files.Metadata;

public class DropBox {

	public static DbxClientV2 client;
	public static final String FINAL_NAME = "info.txt";

	public DbxClientV2 getInit() {
		if (client == null)
			client = new DbxClientV2(new DbxRequestConfig("dropbox/java-tutorial", "en_US"),
					com.tri.common.SecurityConstants.ACCESS_TOKEN);
		return DropBox.client;
	}

	public static String getCurrentDateTime() {
		return (new SimpleDateFormat("yyyyMMddhhmmss")).format(new Date());
	}

	public String uploadFile(DbxClientV2 client) {
		try (InputStream in = new FileInputStream("info.txt")) {
			FileMetadata metadata = client.files().uploadBuilder("/" + getCurrentDateTime() + ".txt")
					.uploadAndFinish(in);
		} catch (FileNotFoundException e) {
			return e.getMessage();
		} catch (IOException e) {
			return e.getMessage();
		} catch (DbxException e) {
			return e.getMessage();
		}
		return "";
	}

	public String downloadFile(DbxClientV2 client, String path) {
		try {

			File file = new File("tmp/");
			if (!file.exists()) {
				file.mkdirs();
			}
			// in folder root of project, if not exist then create
			OutputStream downloadFile = new FileOutputStream("tmp" + path);
			FileMetadata metadata = client.files().downloadBuilder(path).download(downloadFile);
			downloadFile.close();

		} catch (FileNotFoundException e) {
			return e.getMessage();
		} catch (DbxException e) {
			return e.getMessage();
		} catch (IOException e) {
			return e.getMessage();
		}
		return "";
	}

	public List<String> getAllNameFile(DbxClientV2 client) throws ListFolderErrorException, DbxException {
		// StringBuilder str = new StringBuilder();
		List<String> res = new ArrayList();
		ListFolderResult result = client.files().listFolder("");
		for (Metadata metadata : result.getEntries()) {
			res.add(metadata.getPathLower());
			// res.add("https://www.dropbox.com/home/Apps/TestingDBApi030418" +
			// metadata.getPathLower());
			// str.append(metadata.getPathLower());
			// str.append(System.getProperty("line.separator"));
		}
		return res;
	}
}
