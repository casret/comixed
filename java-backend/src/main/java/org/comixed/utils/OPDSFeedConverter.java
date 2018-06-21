package org.comixed.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Scanner;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import org.comixed.library.model.OPDSEntry;
import org.comixed.library.model.OPDSFeed;
import org.comixed.library.model.OPDSLink;


public class OPDSFeedConverter
    extends AbstractHttpMessageConverter<OPDSFeed> {

    public OPDSFeedConverter() {
        super(MediaType.APPLICATION_ATOM_XML);
    }

    @Override
    protected boolean supports(Class<?> clazz) {
        return OPDSFeed.class.isAssignableFrom(clazz);
    }

    @Override
    protected OPDSFeed readInternal(Class<? extends OPDSFeed> clazz, HttpInputMessage inputMessage)
        throws IOException, HttpMessageNotReadableException {
        throw new HttpMessageNotReadableException("Serializing to a OPDSFeed not supported yet");
    }

    @Override
    protected void writeInternal(OPDSFeed feed, HttpOutputMessage outputMessage)
        throws IOException, HttpMessageNotWritableException {
        try {
            XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
            XMLStreamWriter writer = outputFactory.createXMLStreamWriter(outputMessage.getBody());
            writer.writeStartDocument("utf-8", "1.0");
            writer.writeStartElement("feed");
            writer.writeDefaultNamespace("http://www.w3.org/2005/Atom");
            writer.writeNamespace("opds", "http://opds-spec.org/2010/catalog");

            writer.writeStartElement("id");
            writer.writeCharacters(feed.getId());
            writer.writeEndElement();

            writer.writeStartElement("title");
            writer.writeCharacters(feed.getTitle());
            writer.writeEndElement();

            writer.writeStartElement("updated");
            writer.writeCharacters(feed.getUpdated().toString());
            writer.writeEndElement();

            _writeLinks(writer, feed.getLinks());

            for (OPDSEntry entry : feed.getEntries()) {
                writer.writeStartElement("entry");

                writer.writeStartElement("title");
                writer.writeCharacters(entry.getTitle());
                writer.writeEndElement();

                writer.writeStartElement("id");
                writer.writeCharacters(entry.getId());
                writer.writeEndElement();

                writer.writeStartElement("updated");
                writer.writeCharacters(entry.getUpdated().toString());
                writer.writeEndElement();

                writer.writeStartElement("content");
                writer.writeAttribute("type", "html");
                writer.writeCharacters(entry.getContent());
                writer.writeEndElement();

                writer.writeStartElement("author");
                for (String author : entry.getAuthors()) {
                    writer.writeStartElement("name");
                    writer.writeCharacters(author);
                    writer.writeEndElement();
                }
                writer.writeEndElement();


                _writeLinks(writer, entry.getLinks());

                writer.writeEndElement(); // entry
            }

            writer.writeEndDocument();
            writer.close();
        } catch (Exception e) {

        }
    }

    private void _writeLinks(XMLStreamWriter writer, List<OPDSLink> links)
            throws XMLStreamException {
        for (OPDSLink link : links) {
            writer.writeStartElement("link");
            writer.writeAttribute("type", link.getType());
            writer.writeAttribute("rel", link.getRel());
            writer.writeAttribute("href", link.getHRef());
            writer.writeEndElement();
        }
    }
}
