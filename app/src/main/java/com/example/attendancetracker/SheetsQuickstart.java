package com.example.attendancetracker;

import com.google.api.client.auth.oauth2.Credential;
import android.app.Service;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.ValueRange;
import java.io.*;
import java.util.*;

public class SheetsQuickstart {

    private static HttpTransport transport;
    private static JacksonFactory jsonFactory;
    private static FileDataStoreFactory dataStoreFactory;

    private static final java.io.File DATA_STORE_DIR = new java.io.File(System.getProperty("user.home"), ".credentials/sheets.googleapis.com.json");

    private static List<String> scopes = Arrays.asList(SheetsScopes.SPREADSHEETS);
    Sheets service;

    public SheetsQuickstart() {
        try {
            transport = GoogleNetHttpTransport.newTrustedTransport();
            dataStoreFactory = new FileDataStoreFactory(DATA_STORE_DIR);
            jsonFactory = JacksonFactory.getDefaultInstance();

            service = getSheetsService();
        } catch (Exception e) {
            // handle exception
        }
    }

    public static Credential authorize() throws IOException {
        // Load client secrets.
        File cfile = new File("service.json");
        cfile.createNewFile();
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(jsonFactory, new InputStreamReader(new FileInputStream(cfile)));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow =
                new GoogleAuthorizationCodeFlow.Builder(
                        transport, jsonFactory, clientSecrets, scopes)
                        .setDataStoreFactory(dataStoreFactory)
                        .setAccessType("offline")
                        .build();
        Credential credential = new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
        return credential;
    }

    public static Sheets getSheetsService() throws IOException {
        Credential credential = authorize();
        return new Sheets.Builder(transport, jsonFactory, credential)
                .setApplicationName("AttendanceTracker")
                .build();
    }

    public void writeSomething(List<String> myData) {

        try {
            String id = "1-JPOEjVf4MRxgWEKjdbLsNsm_vRVTRtT4ovLHPlHu6A";
            String writeRange = "INSERT_SHEET_NAME!A3:E";

            List<List<Object>> writeData = new ArrayList<>();
//            for (String someData : myData) {
//                List<Object> dataRow = new ArrayList<>();
//                dataRow.add(someData);
//                writeData.add(dataRow);
//            }

            ValueRange vr = new ValueRange().setValues(writeData).setMajorDimension("ROWS");
            service.spreadsheets().values()
                    .update(id, writeRange, vr)
                    .setValueInputOption("RAW")
                    .execute();
        } catch (Exception e) {
            // handle exception
        }
    }
}