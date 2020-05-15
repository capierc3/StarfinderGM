import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ships.*;

import java.util.ArrayList;

public class ShipDatabaseTest {

    ArrayList<Part> items;

    @Before
    public void setUp() {

    }

    @Test
    public void getParts() {
        items = ShipYard.getParts(new Drift());
        Assert.assertNotNull(items.get(0).getName());
        items = ShipYard.getParts(new ShipFrame());
        Assert.assertNotNull(items.get(0).getName());
        items = ShipYard.getParts(new ShipComputer());
        Assert.assertNotNull(items.get(0).getName());
        items = ShipYard.getParts(new PowerCore());
        Assert.assertNotNull(items.get(0).getName());
        items = ShipYard.getParts(new CounterMeasures());
        Assert.assertNotNull(items.get(0).getName());
        items = ShipYard.getParts(new Drift());
        Assert.assertNotNull(items.get(0).getName());
        items = ShipYard.getParts(new ExpansionBay());
        Assert.assertNotNull(items.get(0).getName());
        items = ShipYard.getParts(new CrewQuarter());
        Assert.assertNotNull(items.get(0).getName());
        items = ShipYard.getParts(new Security());
        Assert.assertNotNull(items.get(0).getName());
        items = ShipYard.getParts(new Sensor());
        Assert.assertNotNull(items.get(0).getName());
        items = ShipYard.getParts(new Shields());
        Assert.assertNotNull(items.get(0).getName());
        items = ShipYard.getParts(new Thruster());
        Assert.assertNotNull(items.get(0).getName());
        items = ShipYard.getParts(new ShipWeapon());
        Assert.assertNotNull(items.get(0).getName());
    }

}
