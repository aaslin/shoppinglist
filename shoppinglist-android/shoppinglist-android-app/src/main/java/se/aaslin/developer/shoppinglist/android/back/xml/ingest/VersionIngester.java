package se.aaslin.developer.shoppinglist.android.back.xml.ingest;

import java.io.InputStream;
import java.util.List;

import se.aaslin.developer.shoppinglist.android.back.dto.VersionDTO;

import com.thoughtworks.xstream.XStream;

public class VersionIngester implements Ingester<VersionDTO>{

	@Override
	public List<VersionDTO> ingest(InputStream in) {
		XStream xstream = new XStream();
		xstream.alias("versions", List.class);
		xstream.processAnnotations(VersionDTO.class);

		@SuppressWarnings("unchecked")
		List<VersionDTO> versions = (List<VersionDTO>) xstream.fromXML(in);

		return versions;
	}

}
