package ua.org.solk.qc;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.StringJoiner;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import ua.org.solk.qc.NvSource.NvItem;

public class CNVParse {

	public static void main(String[] args) throws IOException, JAXBException {
		HashMap<Integer, String> nvItemPaths = new HashMap<>(); 
		
		Reader inR = new FileReader("nvitems_list.csv");
		Iterable<CSVRecord> records = CSVFormat.EXCEL.withDelimiter('\t').parse(inR);
		for (CSVRecord record : records) {
		    Integer nvId = Integer.parseInt(record.get(0));
		    String path = record.get(2);
		    if (nvId >= 65535)
		    	nvItemPaths.put(nvId, path);
		}

		
		File in = new File(args[0]);
		FileInputStream input = new FileInputStream(in);
		byte[] buf = new byte[(int) in.length()];
		input.read(buf);
		input.close();
		int i = 8;
		int addition = 0;
		NvSource xml = new NvSource();
		while (i < buf.length) {
			int id = addition + unsignedToBytes(buf[i]) + (256 * unsignedToBytes(buf[i + 1]));
			int len = unsignedToBytes(buf[i + 2]) + (256 * unsignedToBytes(buf[i + 3]));
			byte[] data = Arrays.copyOfRange(buf, i + 4, i + 4 + len);

			NvSource.NvItem item = new NvSource.NvItem();

			item.setId(id);
			item.setIndex(0);
			item.setMapping("direct");
			item.setName("UNKNOWN");
			item.setSubscriptionid(0);
			item.setEncoding("hex");
			item.setData(bytesToHex(data));
			item.setByteData(data);

			xml.getNvItems().add(item);
			
			if (id == 64686)
				addition = 65536;

			i = i + 4 + len;
			while (i % 4 != 0)
				i++;
		}
		
		for (NvItem item : xml.getNvItems()) {
			String path = nvItemPaths.get(item.getId());
			if (path != null)
				path = path.substring(1);
			else
				path = "nv/item_files/rfnv/" + String.format("%08d", item.getId());
			File outFile = new File(path);
			outFile.mkdirs();
			outFile.delete();
			outFile.createNewFile();
			FileOutputStream outFS = new FileOutputStream(outFile);
			outFS.write(item.getByteData());
			outFS.flush();
			outFS.close();			
		}

		JAXBContext ctx = JAXBContext.newInstance(NvSource.class);
		Marshaller marsh = ctx.createMarshaller();
		marsh.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		FileOutputStream out = new FileOutputStream("out.xml");
		marsh.marshal(xml, out);
		out.close();
		
	}

	final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();

	public static String bytesToHex(byte[] bytes) {
		StringJoiner str = new StringJoiner(", ");
		for (int j = 0; j < bytes.length; j++) {
			int v = bytes[j] & 0xFF;
			String val = "" + hexArray[v >>> 4] + hexArray[v & 0x0F];
			str.add(val);
		}
		return str.toString();
	}

	public static int unsignedToBytes(byte b) {
		return b & 0xFF;
	}

}
