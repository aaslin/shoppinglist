package se.aaslin.developer.shoppinglist.android.xml.ingest;

import java.io.InputStream;
import java.util.List;

public interface Ingester<E>{

	List<E> ingest(InputStream in);
}
