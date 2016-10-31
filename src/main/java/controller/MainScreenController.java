package controller;

import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.event.UIEventHandler;
import com.lynden.gmapsfx.javascript.event.UIEventType;
import com.lynden.gmapsfx.javascript.object.*;
import fxapp.MainFXApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.*;
import netscape.javascript.JSObject;

import java.io.IOException;
import java.net.URL;
import java.util.*;

/**
 * Controller for the main map screen of the app.
 */
public class MainScreenController implements Initializable, MapComponentInitializedListener, UIEventHandler {
    /**
     * reference back to mainApplication if needed
     */
    private MainFXApplication mainApplication;

    @FXML
    private Button edit;
    @FXML
    private Button logout;
    @FXML
    private Button sourceReport;
    @FXML
    private Button adminScreen;
    @FXML
    private Button mode_button;
    @FXML
    private GoogleMapView mapView;

    private GoogleMap map;

    public Person user;

    Set<Marker> markers;

    int mode;   // 0 - normal, 1 - source, 2 - quality

    int mode_mod;

    /**
     * allow for calling back to the main application code if necessary
     * @param main the reference to the FX Application instance
     */
    public void setMainApp(MainFXApplication main) {
        mainApplication = main;
    }

    /** Perform some basic setup functions to prepare main screen for use such as hiding admin button for non-admins*/
    public void setup() {
        // setup mode button
        mode_button.setStyle("-fx-background-color: yellow;");
        mode_button.setText("View Mode");

        // disable water quality mode for normal users
        if (!user.getType().hasPrivilege(Privilege.SUBMIT_WATER_PURITY)) {
            mode_mod = 2;
        } else {
            mode_mod = 3;
        }

        // disable admin screen if user is not an admin
        if (!user.getType().hasPrivilege(Privilege.VIEW_ADMIN_SCREEN)) {
            adminScreen.setVisible(false);
        }
    }

    /** Closes the map, restarts the application. */
    public void logout() throws Exception {
        System.out.println("Logging out of " + user.getName());
        ((Stage) mapView.getScene().getWindow()).close();
        mainApplication.start(new Stage());
    }

    /** Launches the profile edit view for the current user. */
    public void editProfile() throws IOException {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainFXApplication.class.getResource("../editProfile.fxml"));
        Parent loginRoot = loader.load();
        Scene scene = new Scene(loginRoot, 350, 350);
        EditProfileController controller = loader.getController();
        controller.setUser(user);
        stage.setTitle("Edit Profile");
        stage.setScene(scene);
        stage.showAndWait();
    }

    /** Launches the admin view. */
    public void adminScreen() throws Exception {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainFXApplication.class.getResource("../adminviewcontroller.fxml"));
        Parent loginRoot = loader.load();
        Scene scene = new Scene(loginRoot, 800, 600);
        AdminViewController controller = loader.getController();
        controller.loadData();
        stage.setTitle("Admin Tools");
        stage.setScene(scene);
        stage.showAndWait();
    }


    /** Called once the map is initialized. Sets up the map. */
    @Override
    public void mapInitialized() {
        MapOptions mapOptions = new MapOptions();
        mapOptions.center(new LatLong(33.762909, -84.422675))
                .mapType(MapTypeIdEnum.TERRAIN)
                .overviewMapControl(false)
                .panControl(false)
                .rotateControl(false)
                .scaleControl(false)
                .streetViewControl(false)
                .zoomControl(false)
                .zoom(12);

        map = mapView.createMap(mapOptions);

        this.markers = new HashSet<>();

        map.addUIEventHandler(UIEventType.click, this);
        this.createMapPins();
    }

    /** Called as the Controller is starting. */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mapView.addMapInializedListener(this);
    }

    public void refreshMapPins() {
        for (Marker marker: markers) {
            map.removeMarker(marker);
        }
        markers.clear();
        this.createMapPins();
    }

    /** Loads all of the reports from the database and puts pins for them on the map. */
    private void createMapPins() {
        Collection<Report> reports = SQLInterface.getAllReportsInSystem();
        for (Report report: reports) {
            Marker tMarker = new Marker(new MarkerOptions().position(new LatLong(
                    report.getLatitude(), report.getLongitude())).icon(report.getIconURL()), map, report);
            tMarker.setWindow(new InfoWindow(new InfoWindowOptions().content(report.toInfoWindow())));
            map.addMarker(tMarker);
            markers.add(tMarker);
            map.addUIEventHandler(tMarker, UIEventType.click, new UIEventHandler() {
                @Override
                public void handle(JSObject jsObject) {
                    tMarker.toggleWindowVisibility();
                }
            });
        }
    }

    /** Handles clicks on the map. */
    @Override
    public void handle(JSObject jsObject) {
        if (mode == 1) {
            LatLong ll = new LatLong((JSObject) jsObject.getMember("latLng"));
            double latitude = ll.getLatitude();
            double longitude = ll.getLongitude();
            try {
                sourceReport(latitude, longitude);
            } catch (Exception e) {
                // TODO: Error protection
            }
            mode = 0;
            mode_button.setStyle("-fx-background-color: yellow;");
            mode_button.setText("View Mode");
        } else if (mode == 2) {
            LatLong ll = new LatLong((JSObject) jsObject.getMember("latLng"));
            double latitude = ll.getLatitude();
            double longitude = ll.getLongitude();
            try {
                qualityReport(latitude, longitude);
            } catch (Exception e) {
                e.printStackTrace();
                // TODO: Error protection
            }
            mode = 0;
            mode_button.setStyle("-fx-background-color: yellow;");
            mode_button.setText("View Mode");
        }
    }

    /** Launches the window for creating a new Source Report */
    public void sourceReport(double lat, double lon) throws Exception {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainFXApplication.class.getResource("../sourcereport.fxml"));
        Parent loginRoot = loader.load();
        Scene scene = new Scene(loginRoot, 350, 350);
        SourceReportController controller = loader.getController();
        controller.linkMainController(this);
        controller.setUser(user);
        controller.setLatLon(lat, lon);
        stage.setTitle("Source Report");
        stage.setScene(scene);
        stage.showAndWait();
    }

    /** Launches the window for creating a new Source Report */
    public void qualityReport(double lat, double lon) throws Exception {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainFXApplication.class.getResource("../qualityreport.fxml"));
        Parent loginRoot = loader.load();
        Scene scene = new Scene(loginRoot, 350, 350);
        QualityReportController controller = loader.getController();
        controller.linkMainController(this);
        controller.setUser(user);
        controller.setLatLon(lat, lon);
        stage.setTitle("Source Report");
        stage.setScene(scene);
        stage.showAndWait();
    }

    @FXML
    public void mode_switch() {
        mode = (mode + 1) % mode_mod;
        // modeButton.setBackground(javafx.scene.paint.Color.RED);
        if (mode == 0) {
            mode_button.setStyle("-fx-background-color: yellow;");
            mode_button.setText("View Mode");
        } else if (mode == 1) {
            mode_button.setStyle("-fx-background-color: magenta;");
            mode_button.setText("Source Mode");
        } else {
            mode_button.setStyle("-fx-background-color: cyan;");
            mode_button.setText("Quality Mode");
        }
    }
}
