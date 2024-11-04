package org.jetbrains.station;

import org.jetbrains.car.Car;
import org.jetbrains.car.PetrolCar;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestStationsPool {

    private Car car;

    @BeforeEach
    void setUp() {
        car = new PetrolCar(35,1);
    }

    @Test
    void testChargingStationsPool() {
        double station_location = StationsPool.getInstance().getClosestChargingStation(car).getLocation();
        Assertions.assertEquals(station_location, 35, 0.1, "car should go to nearest charging station");
    }

    @Test
    void testGasStationsPool() {
        double station_location = StationsPool.getInstance().getClosestGasStation(car).getLocation();
        Assertions.assertEquals(station_location, 25, 0.1, "car should go to nearest gas station");
    }
}
