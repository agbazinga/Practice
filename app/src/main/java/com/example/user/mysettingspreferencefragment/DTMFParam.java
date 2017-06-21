package com.example.user.mysettingspreferencefragment;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;

public class DTMFParam {

    private int DTMF_boost = 0;
    private int DTMF_threshold = 55;
    private int DTMF_multiplying_factor = 35;

    private final String LOG_TAG = "DTMFParam";

    public int getThreshold() {
        return DTMF_threshold;
    }

    public int getMultiplyingFactor() {
        return DTMF_multiplying_factor;
    }

    public int getBoost() {
        return DTMF_boost;
    }

    public void parseXML(InputStream in, String operator) throws XmlPullParserException, IOException {
        try {
            XmlPullParserFactory Xml = XmlPullParserFactory.newInstance();
            Xml.setNamespaceAware(true);
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            int eventType = parser.getEventType();
            boolean parsed = false;
            String text = "0";

            while (eventType != XmlPullParser.END_DOCUMENT && !parsed) {
                if (eventType == XmlPullParser.START_TAG) {
                    String name = parser.getName();

                    if (name.equalsIgnoreCase("operator")) {
                        String operatorName = parser.getAttributeValue(null, "name");
                        if (operator.equalsIgnoreCase(operatorName)) {
                            Log.d(LOG_TAG, "setParseXML Operator Found");
                            while (!parsed) {

                                if (eventType == XmlPullParser.END_DOCUMENT) {
                                    parsed = true;
                                }

                                name = parser.getName();
                                switch (eventType) {
                                    case XmlPullParser.START_TAG:
                                        eventType = parser.next();
                                        continue;

                                    case XmlPullParser.TEXT:
                                        text = parser.getText();
                                        Log.d(LOG_TAG, "text : " + text);
                                        break;

                                    case XmlPullParser.END_TAG:
                                        if (name.equalsIgnoreCase("boost")) {
                                            DTMF_boost = Integer.parseInt(text);
                                        } else if (name.equalsIgnoreCase("threshold")) {
                                            DTMF_threshold = Integer.parseInt(text);
                                        } else if (name.equalsIgnoreCase("multiplying_factor")) {
                                            DTMF_multiplying_factor = Integer.parseInt(text);
                                        } else if (name.equalsIgnoreCase("operator")) {
                                            parsed = true;
                                        }
                                        break;

                                }
                                eventType = parser.next();
                            }
                        }
                    }
                }
                if (eventType != XmlPullParser.END_DOCUMENT && parsed == false) {
                    eventType = parser.next();
                }

            }
        } catch (IOException ex) {
            Log.d(LOG_TAG, "ParseXML exception " + ex.toString());
        } finally {
            in.close();
        }

    }

    public int setParamFromXML(Context context, String operator) {
        final int success = 1;
        final int failure = 0;
        try {
            AssetManager am = context.getAssets();
            String filename = "sample.xml";
            InputStream is = am.open(filename);

            if (is == null) {
                Log.d(LOG_TAG, "setParseXML XML file not Found");
                return success;
            }

            Log.d(LOG_TAG, "setParseXML Called");
            parseXML(is, operator);

            return success;
        } catch (IOException ex) {
            Log.d(LOG_TAG, "setParamFromXML exception " + ex.toString());
            return success;
        } catch (XmlPullParserException ex) {
            Log.d(LOG_TAG, "setParamFromXML exception " + ex.toString());
            return failure;
        }

    }

}