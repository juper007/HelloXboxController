package com.example.juper.helloxboxcontroller;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.InputDevice;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static com.example.juper.helloxboxcontroller.Dpad.DOWN;
import static com.example.juper.helloxboxcontroller.Dpad.LEFT;
import static com.example.juper.helloxboxcontroller.Dpad.RIGHT;
import static com.example.juper.helloxboxcontroller.Dpad.UP;

public class MainActivity extends AppCompatActivity {

    private TextView mTextActionLog;
    private Dpad mDpad;
    private int mEventCount = 0;

    @Override
    protected void onStart() {
        super.onStart();
        ListView listView = (ListView) findViewById(R.id.listvew_controller_list);

        ArrayList controllerList = getGameControllerIds();
        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_expandable_list_item_1, controllerList);
        listView.setAdapter(arrayAdapter);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mTextActionLog = (TextView) findViewById(R.id.text_action_log);
        mDpad = new Dpad();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public ArrayList getGameControllerIds() {
        ArrayList gameControllerDeviceIds = new ArrayList();
        int[] deviceIds = InputDevice.getDeviceIds();
        for (int deviceId : deviceIds) {
            InputDevice dev = InputDevice.getDevice(deviceId);
            int sources = dev.getSources();

            // Verify that the device has gamepad buttons, control sticks, or both.
            if (((sources & InputDevice.SOURCE_GAMEPAD) == InputDevice.SOURCE_GAMEPAD)
                    || ((sources & InputDevice.SOURCE_JOYSTICK)
                    == InputDevice.SOURCE_JOYSTICK)) {
                // This device is a game controller. Store its device ID.
                if (!gameControllerDeviceIds.contains(deviceId)) {
                    gameControllerDeviceIds.add(deviceId);
                }
            }
        }
        return gameControllerDeviceIds;
    }

    @Override
    public boolean onGenericMotionEvent(MotionEvent event) {
        // Check if this event if from a D-pad and process accordingly.
        
        if (Dpad.isDpadDevice(event)) {

            int press = mDpad.getDirectionPressed(event);
            switch (press) {
                case LEFT:
                    addLog("D-Pad Pressed : LEFT");
                    return true;
                case RIGHT:
                    addLog("D-Pad Pressed : RIGHT");
                    return true;
                case UP:
                    addLog("D-Pad Pressed : UP");
                    return true;
                case DOWN:
                    addLog("D-Pad Pressed : DOWN");
                    return true;
                default:
                    addLog("D-Pad Pressed : Unknown(" + press + ")");
                    break;
            }
        }
        return super.onGenericMotionEvent(event);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((event.getSource() & InputDevice.SOURCE_GAMEPAD) == InputDevice.SOURCE_GAMEPAD) {
            if (event.getRepeatCount() == 0) {
                switch (keyCode) {
                    case (ButtonMap.BUTTON_A) :
                        addLog("Game Pad Key Down : A Button");
                        break;
                    case (ButtonMap.BUTTON_B) :
                        addLog("Game Pad Key Down : B Button");
                        break;
                    case (ButtonMap.BUTTON_X) :
                        addLog("Game Pad Key Down : X Button");
                        break;
                    case (ButtonMap.BUTTON_Y) :
                        addLog("Game Pad Key Down : Y Button");
                        break;
                    case (ButtonMap.BUTTON_VIEW) :
                        addLog("Game Pad Key Down : View Button");
                        break;
                    case (ButtonMap.BUTTON_MENU) :
                        addLog("Game Pad Key Down : Select Button");
                        break;
                    case (ButtonMap.BUTTON_XBOX) :
                        addLog("Game Pad Key Down : Xbox Button");
                        break;
                    case (ButtonMap.BUTTON_LEFT_BUMPER) :
                        addLog("Game Pad Key Down : Left bumper Button");
                        break;
                    case (ButtonMap.BUTTON_RIGHT_BUMPER) :
                        addLog("Game Pad Key Down : Right bumper Button");
                        break;
                    case (ButtonMap.BUTTON_LEFT_STICK) :
                        addLog("Game Pad Key Down : Left stick Button");
                        break;
                    case (ButtonMap.BUTTON_RIGHT_STICK) :
                        addLog("Game Pad Key Down : Right stick Button");
                        break;
                    default:
                        addLog("Game PAd Key Down : Unknown(" + keyCode + ")");
                }
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    public void addLog (String s) {
        mTextActionLog.setText("[" + mEventCount++ + "] " + s);
    }
}
