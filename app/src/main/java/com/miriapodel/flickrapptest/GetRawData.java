package com.miriapodel.flickrapptest;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

enum DownloadStatus{IDLE, PROCESSING, FAILED_OR_EMPTY, OK, NOT_INITIALIZED}

public class GetRawData extends AsyncTask<String, Void, String>
{
    private static final String TAG = "GetRawData";

    DownloadStatus downloadStatus;

    OnDownloadComplete callBack;

    interface OnDownloadComplete
    {
        void onDownloadComplete(String data, DownloadStatus status);
    }

    public GetRawData(OnDownloadComplete callBack)
    {
        downloadStatus = DownloadStatus.IDLE;

        this.callBack = callBack;
    }

    @Override
    protected void onPostExecute(String s) {
        Log.d(TAG, "onPostExecute: The parameter was: " + s);

        callBack.onDownloadComplete(s, downloadStatus);

        Log.d(TAG, "onPostExecute: ends");
    }

    @Override
    protected String doInBackground(String... strings) {

        BufferedReader reader = null;
        HttpURLConnection connection = null;

        if(strings[0] == null)
        {
            downloadStatus = DownloadStatus.NOT_INITIALIZED;

            return null;
        }

        try
        {
            downloadStatus = DownloadStatus.PROCESSING;

            URL url = new URL(strings[0]);

            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            int response = connection.getResponseCode();

            Log.d(TAG, "doInBackground: The response code was: " + response);

            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String lines =  new String();
            StringBuilder result = new StringBuilder();

            for(lines = reader.readLine() ; null != lines ; lines = reader.readLine())
            {
                result.append(lines).append("\n");
            }

            downloadStatus = DownloadStatus.OK;

            return result.toString();
        }
        catch (MalformedURLException e)
        {
            Log.e(TAG, "doInBackground: Invalid URL: " + e.getMessage());
        }
        catch (IOException e)
        {
            Log.e(TAG, "doInBackground: Unable to establish connection: " + e.getMessage());
        }
        catch (SecurityException e)
        {
            Log.e(TAG, "doInBackground: Security Exception. Needs permission?" + e.getMessage());
        }
        finally
        {
            if(connection != null)
            {
                connection.disconnect();
            }

            if(reader != null)
            {
                try
                {
                 reader.close();
                }
                catch (IOException e)
                {
                    Log.e(TAG, "doInBackground: Unable to close reader: " + e.getMessage());
                }
            }
        }

        downloadStatus = DownloadStatus.FAILED_OR_EMPTY;

        return null;
    }
}
