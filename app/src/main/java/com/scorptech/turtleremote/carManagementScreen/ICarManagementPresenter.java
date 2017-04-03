package com.scorptech.turtleremote.carManagementScreen;

import com.scorptech.turtleremote.views.MovementControlPanel;

/**
 * Created by talhahavadar on 13/01/2017.
 */

public interface ICarManagementPresenter {

    void setupSocketConnection();
    void setupMjpegConnection();
    void evaluateJoystick(MovementControlPanel.Joystick joystick);

}
