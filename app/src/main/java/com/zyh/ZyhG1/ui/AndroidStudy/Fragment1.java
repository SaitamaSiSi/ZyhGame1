package com.zyh.ZyhG1.ui.AndroidStudy;

import android.app.Activity;
import android.content.Intent;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.gson.JsonObject;
import com.zyh.ZyhG1.R;
import com.zyh.ZyhG1.ulit.ContentHandler;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.Objects;

import javax.xml.parsers.SAXParserFactory;

public class Fragment1 extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment1, container, false);

        Button msgButton = view.findViewById(R.id.f1_btn_msg);
        msgButton.setOnClickListener((v) -> {
            Toast.makeText(this.getContext(), "You clicked Button 1", Toast.LENGTH_SHORT).show();
        });
        Button dialogButton = view.findViewById(R.id.f1_btn_dialog);
        dialogButton.setOnClickListener((v) -> {
            Activity activity = this.getActivity();
            if (activity != null) {
                Intent intent = new Intent(activity, DialogActivity.class);
                startActivity(intent);
            }
        });

        Button xmlBtn = view.findViewById(R.id.f1_btn_xml);
        xmlBtn.setOnClickListener((v) -> {
            parseXMLWithPull();
            // parseXmlWithSAX();
        });

        return view;
    }

    private void parseXMLWithPull() {
        try (XmlResourceParser parser = this.requireContext().getResources().getXml(R.xml.get_data)) {
            int eventType = parser.getEventType();
            String id = "";
            String name = "";
            String version = "";
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String nodeName = parser.getName();
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        switch (nodeName) {
                            case "id":
                                id = parser.nextText();
                                break;
                            case "name":
                                name = parser.nextText();
                                break;
                            case "version":
                                version = parser.nextText();
                                break;
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if (Objects.equals(nodeName, "app")) {
                            Log.d("MainActivity", "id is " + id);
                            Log.d("MainActivity", "name is " + name);
                            Log.d("MainActivity", "version is " + version);
                        }
                        break;
                    default:
                        break;
                }
                eventType = parser.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void parseXmlWithSAX() {
        try {
            String xmlData = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                    "<apps>\n" +
                    "    <app>\n" +
                    "        <id>1</id>\n" +
                    "        <name>Google Maps</name>\n" +
                    "        <version>1.0</version>\n" +
                    "    </app>\n" +
                    "    <app>\n" +
                    "        <id>2</id>\n" +
                    "        <name>Chrome</name>\n" +
                    "        <version>2.1</version>\n" +
                    "    </app>\n" +
                    "    <app>\n" +
                    "        <id>3</id>\n" +
                    "        <name>Google Play</name>\n" +
                    "        <version>2.3</version>\n" +
                    "    </app>\n" +
                    "</apps>";
            SAXParserFactory factory = SAXParserFactory.newInstance();
            XMLReader xmlReader = factory.newSAXParser().getXMLReader();
            ContentHandler handler = new ContentHandler();
            xmlReader.setContentHandler(handler);
            xmlReader.parse(new InputSource(new StringReader(xmlData)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
