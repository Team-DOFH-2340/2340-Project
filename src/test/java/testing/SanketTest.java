package testing;

import controller.SQLInterface;
import model.WaterQualityReport;
import model.Condition;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import java.time.LocalDate;
import java.util.HashSet;

/**
 * Created by sshah402 on 11/10/16.
 */
public class SanketTest {
    private java.util.Set<WaterQualityReport> reports;

    @Before
    public void setUp() {
        reports = new HashSet<>();
        reports.add(new WaterQualityReport(Condition.SAFE, 2.0, 19.0));
        reports.add(new WaterQualityReport(Condition.UNSAFE, 2.0, 19.0));
        reports.add(new WaterQualityReport(Condition.TREATABLE, 2.0, 19.0));

    }
    @After
    public void tearDown() {
        for (WaterQualityReport report : reports) {
            SQLInterface.deleteReport("WaterQuality", report.getReport_id());
        }
    }
    @Test
    public void putInDatabase() {
        reports.stream().forEach(e->{SQLInterface.createWaterQualityReport(e);});
    }

    @Test
    public void checkDatabase() {
        SQLInterface.getAllQualityReportsInSysten().forEach(e->reports.remove(e));
        assertTrue(reports.size() == 0);
    }

}