package testing;

import controller.SQLInterface;
import model.WaterSourceCondition;
import model.WaterSourceReport;
import model.WaterSourceType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import java.time.LocalDate;
import java.util.HashSet;

/**
 * Created by hflinner3 on 11/10/16.
 */
public class HaydenTest {
    private java.util.Set<WaterSourceReport> reports;

    @Before
    public void setUp() {
        reports = new HashSet<>();
        for (int i = 0; i < 10; i++) {
            reports.add(new WaterSourceReport(i * 1000 + 392, "Hayden", LocalDate.now(), i, i*4, i, i,
                    WaterSourceType.values()[i % WaterSourceType.values().length], WaterSourceCondition.values()[i % WaterSourceCondition.values().length]));
        }
    }
    @After
    public void tearDown() {
        for (WaterSourceReport report : reports) {
            SQLInterface.deleteReport("WaterSource", report.getReport_id());
        }
    }
    @Test
    public void putInDatabase() {
        reports.stream().forEach(e->{SQLInterface.createWaterSourceReport(e);});
    }

    @Test
    public void checkDatabase() {
        SQLInterface.getAllSourceReportsInSystem().forEach(e->reports.remove(e));
        assertTrue(reports.size() == 0);
    }

}