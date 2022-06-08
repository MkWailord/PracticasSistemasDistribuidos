package mx.ipn.app.run;

import java.io.IOException;

import static mx.ipn.app.run.ObjectMapperUtils.*;


public class Main {

	public static void main(String[] args) throws IOException {
		objectToJsonString();
		objectToJsonInFile();
		objectToBytes();
		jsonStrToObject();
		jsonInFileToObject();
		readJsonStringAsMap();
		readJsonArrayStringAsList();
		parseJsonStringAsJsonNode();
		createJsonNodeStr();
		demoUnknownField();
		demoNullPrimitiveValues();
		objectWithDateToJsonString();
		jsonStringWithDateToObject();
		demoCustomSerializer();
		demoCustomDeSerializer();
	}
}
