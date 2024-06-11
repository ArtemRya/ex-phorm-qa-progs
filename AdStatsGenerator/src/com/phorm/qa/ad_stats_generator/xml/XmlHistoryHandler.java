package com.phorm.qa.ad_stats_generator.xml;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class XmlHistoryHandler {

	/** Create XML file from history */
	public static void marshalHistory(XmlEvents history, String fileName) {
		try {
			JAXBContext context;
			BufferedWriter writer = null;
			File file = new File(fileName);
			writer = new BufferedWriter(new FileWriter(file));
			context = JAXBContext.newInstance(XmlEvents.class);
			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			m.marshal(history, writer);
			writer.close();
		} catch (IOException e) {
			throw new RuntimeException(
					"Can't create SML file for emulation history (IO)", e);
		} catch (JAXBException e) {
			throw new RuntimeException(
					"Can't create SML file for emulation history (JAXB)", e);
		}

	}

	public static XmlEvents unmarshal(String fileName) {
		try {
			File file = new File(fileName);
			JAXBContext jaxbContext = JAXBContext.newInstance(XmlEvents.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			XmlEvents xmlEvents = (XmlEvents) jaxbUnmarshaller.unmarshal(file);
			return xmlEvents;
		} catch (JAXBException e) {
			throw new RuntimeException("Unable to unmarshal history from "
					+ fileName, e);
		}
	}
}