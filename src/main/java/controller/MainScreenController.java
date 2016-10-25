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
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Person;
import model.Privilege;
import model.WaterSourceReport;
import netscape.javascript.JSObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

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
    private ToggleButton pinCreateModeButton;
    @FXML
    private GoogleMapView mapView;

    private GoogleMap map;

    public Person user;

    ArrayList<Boolean> visibilityList;

    ArrayList<InfoWindow> windowList;

    ArrayList<Marker> markerList;

    boolean pinCreateMode;

    /**
     * allow for calling back to the main application code if necessary
     * @param main the reference to the FX Application instance
     */
    public void setMainApp(MainFXApplication main) {
        mainApplication = main;
    }

    /** Checks if user should have access to admin screen, hides button to launch it if not */
    public void disableAdminScreen() {
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

        visibilityList = new ArrayList<>();
        windowList = new ArrayList<>();
        markerList = new ArrayList<>();

        map.addUIEventHandler(UIEventType.click, this);
        this.createMapPins();
    }

    /** Called as the Controller is starting. */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mapView.addMapInializedListener(this);
    }

    public void refreshMapPins() {
        visibilityList.clear();
        windowList.clear();
        markerList.clear();

        this.createMapPins();
    }

    /** Loads all of the reports from the database and puts pins for them on the map. */
    private void createMapPins() {
        ArrayList<WaterSourceReport> reports = SQLInterface.getAllReportsInSystem();

        for (int a = 0; a < reports.size(); ++a) {
            final int b = a;
            LatLong tLL = new LatLong(reports.get(a).getLatitude(), reports.get(a).getLongitude());
            MarkerOptions tMO = new MarkerOptions();
            tMO.position(tLL);
            Marker tMarker = new Marker(tMO);
            InfoWindowOptions tIWO = new InfoWindowOptions();
            tIWO.content(reports.get(a).toInfoWindow());
            InfoWindow tIW= new InfoWindow(tIWO);
            visibilityList.add(Boolean.FALSE);
            windowList.add(tIW);
            markerList.add(tMarker);
            map.addMarker(tMarker);
            map.addUIEventHandler(tMarker, UIEventType.click, new UIEventHandler() {
                @Override
                public void handle(JSObject jsObject) {
                    if (visibilityList.get(b) == Boolean.FALSE) {
                        windowList.get(b).open(map, markerList.get(b));
                    } else {
                        windowList.get(b).close();
                    }
                    visibilityList.set(b, !visibilityList.get(b));
                }
            });
        }
    }

    /** Handles clicks on the map. */
    @Override
    public void handle(JSObject jsObject) {
        if (pinCreateMode) {
            LatLong ll = new LatLong((JSObject) jsObject.getMember("latLng"));
            double latitude = ll.getLatitude();
            double longitude = ll.getLongitude();
            try {
                sourceReport(latitude, longitude);
            } catch (Exception e) {
                // TODO: Error protection
            }
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

    @FXML
    public void pinCreateModeToggle() {
        pinCreateMode = !pinCreateMode;
    }
}
