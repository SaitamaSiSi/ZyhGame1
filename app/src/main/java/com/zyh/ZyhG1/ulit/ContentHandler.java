package com.zyh.ZyhG1.ulit;

import android.util.Log;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.Objects;

public class ContentHandler extends DefaultHandler {
    private String mNodeName = "";
    private StringBuilder mId;
    private StringBuilder mName;
    private StringBuilder mVersion;

    @Override
    public void startDocument() throws SAXException {
        // super.startDocument();
        mId = new StringBuilder();
        mName = new StringBuilder();
        mVersion = new StringBuilder();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        // super.startElement(uri, localName, qName, attributes);
        mNodeName = localName;
        Log.d("ContentHandler", "uri is " + uri);
        Log.d("ContentHandler", "localName is " + localName);
        Log.d("ContentHandler", "qName is " + qName);
        Log.d("ContentHandler", "attributes is " + attributes);
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        // super.characters(ch, start, length);
        switch (mNodeName) {
            case "id":
                mId.append(ch, start, length);
                break;
            case "name":
                mName.append(ch, start, length);
                break;
            case "version":
                mVersion.append(ch, start, length);
                break;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        // super.endElement(uri, localName, qName);
        if (Objects.equals(localName, "app")) {
            Log.d("ContentHandler", "id is " + mId.toString().trim());
            Log.d("ContentHandler", "name is " + mName.toString().trim());
            Log.d("ContentHandler", "version is " + mVersion.toString().trim());
            mId.setLength(0);
            mName.setLength(0);
            mVersion.setLength(0);
        }
    }

    @Override
    public void endDocument() throws SAXException {
        // super.endDocument();
    }
}
