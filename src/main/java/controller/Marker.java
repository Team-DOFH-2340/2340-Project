package main.java.controller;

import com.lynden.gmapsfx.javascript.object.GoogleMap;
import com.lynden.gmapsfx.javascript.object.InfoWindow;
import com.lynden.gmapsfx.javascript.object.MarkerOptions;
import main.java.model.Report;

/**
 * ADds functionality to keep track of if info window is open in map.
 */
class Marker extends com.lynden.gmapsfx.javascript.object.Marker {
    private InfoWindow window;
    private boolean windowVisible;
    private GoogleMap map;
    private final Report report;

    public Marker(MarkerOptions mo, GoogleMap map, Report report) {
        super(mo);
        windowVisible = false;
        this.report = report;
    }

    public InfoWindow getWindow() {
        return window;
    }

    public void setWindow(InfoWindow window) {
        this.window = window;
    }

    public void toggleWindowVisibility() {
        if (windowVisible) {
            window.close();
        } else {
            window.open(map, this);
        }
        this.windowVisible = !this.windowVisible;
    }

    public Report getReport() {
        return report;
    }
}
