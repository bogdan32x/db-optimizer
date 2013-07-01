package ro.utcluj.utils.googleapi;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import org.apache.log4j.Logger;

public class UrlDownload {

	final static int			size	= 1024;

	private static final Logger	logger	= Logger.getLogger(UrlDownload.class);

	public static void fileUrl(final String fAddress, final String localFileName, final String destinationDir) {
		OutputStream outStream = null;
		URLConnection uCon = null;

		InputStream is = null;
		try {
			URL Url;
			byte[] buf;
			int ByteRead, ByteWritten = 0;
			Url = new URL(fAddress);
			outStream = new BufferedOutputStream(new FileOutputStream(destinationDir + "\\" + localFileName));

			uCon = Url.openConnection();
			is = uCon.getInputStream();
			buf = new byte[UrlDownload.size];
			while ((ByteRead = is.read(buf)) != -1) {
				outStream.write(buf, 0, ByteRead);
				ByteWritten += ByteRead;
			}
			UrlDownload.logger.info("Downloaded Successfully.");
			UrlDownload.logger.info("File name:\"" + localFileName + "\"\nNo ofbytes :" + ByteWritten);
		} catch (final Exception e) {
			UrlDownload.logger.error(e.getMessage());
		}
		finally {
			try {
				if(is!= null) {
					is.close();
				}
				if (outStream != null) {
					outStream.close();
				}
			} catch (final IOException e) {
				UrlDownload.logger.error(e.getMessage());
			}
		}
	}

	public static void fileDownload(final String fAddress, final String destinationDir) {
		final int slashIndex = fAddress.lastIndexOf('/');
		final int periodIndex = fAddress.lastIndexOf('.');

		final String fileName = fAddress.substring(slashIndex + 1);

		if (periodIndex >= 1 && slashIndex >= 0 && slashIndex < fAddress.length() - 1) {
			UrlDownload.fileUrl(fAddress, fileName, destinationDir);
		} else {
			UrlDownload.logger.debug("Wrong path or file name.");
		}
	}
}
