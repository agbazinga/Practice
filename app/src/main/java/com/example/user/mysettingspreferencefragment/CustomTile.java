package com.example.user.mysettingspreferencefragment;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.service.quicksettings.TileService;
import android.util.Log;

public class CustomTile extends TileService {
    public CustomTile() {
        Log.d("flappy","CustomTile");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("flappy","onDestroy()");
    }

    @Override
    public void onTileAdded() {
        super.onTileAdded();
        Log.d("flappy","onTileAdded()");
    }

    @Override
    public void onTileRemoved() {
        super.onTileRemoved();
        Log.d("flappy","onTileRemoved()");
    }

    @Override
    public void onStartListening() {
        super.onStartListening();
        Log.d("flappy","onStartListening()");
    }

    @Override
    public void onStopListening() {
        super.onStopListening();
        Log.d("flappy","onStopListening()");
    }

    @Override
    public void onClick() {
        super.onClick();
        Log.d("flappy","onClick()");
    }
}
