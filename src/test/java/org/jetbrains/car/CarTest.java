package org.jetbrains.car;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CarTest {
    @Test
    void TestWithNegativeUsageRate(){
        assertThrows(IllegalArgumentException.class, () -> new PetrolCar(1,0),
                "energy usage rate should be higher than 0.");
        assertThrows(IllegalArgumentException.class, () -> new PetrolCar(1,-1),
                "energy usage rate should be higher than 0.");
    }

    @Disabled
    @Test
    void AllowedLocation(){
        assertThrows(IllegalArgumentException.class, () -> new PetrolCar(-1,1));
        assertThrows(IllegalArgumentException.class, () -> new PetrolCar(1000,1));
    }

    @Test
    void TestForNeedEnergy_CarLocationLessDestLocation(){
        Car car = new PetrolCar(0,5);
        Assertions.assertTrue(car.needsEnergy(25));
    }

    @Test
    void TestForNeedEnergy_CarLocationMoreDestLocation(){
        Car car = new PetrolCar(25,5);
        Assertions.assertTrue(car.needsEnergy(0));
    }

    @Test
    void DoNotNeedEnergy(){
        Car car = new PetrolCar(0,5);
        Assertions.assertFalse(car.needsEnergy(1));
    }

    @Test
    void TestDriveTo_CarLocationLessDestLocation(){
        int start = 0, dest = 10, UsageRate = 5;
        Car car = new PetrolCar(start,UsageRate);
        if(!car.needsEnergy(dest)){
            car.driveTo(dest);
        }
        Assertions.assertEquals(dest, car.getLocation(), "Car didn't arrive at the expected location.");
        Assertions.assertEquals(100 - UsageRate*(Math.abs(start-dest)), car.getEnergyValue());
    }

    @Test
    void TestDriveTo_CarLocationMoreDestLocation(){
        int start = 10, dest = 0, UsageRate = 5;
        Car car = new PetrolCar(start,UsageRate);
        if(!car.needsEnergy(dest)){
            car.driveTo(dest);
        }
        Assertions.assertEquals(dest, car.getLocation(), "Car didn't arrive at the expected location.");
        Assertions.assertEquals(100 - UsageRate*(Math.abs(start-dest)), car.getEnergyValue());
    }

}
