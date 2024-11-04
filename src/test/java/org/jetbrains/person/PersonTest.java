package org.jetbrains.person;

import org.jetbrains.car.Car;
import org.jetbrains.car.ElectricCar;
import org.jetbrains.car.PetrolCar;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.assertThrows;

class PersonTest {

    private Person person;
    private PetrolCar spyPetrolCar;
    private ElectricCar spyElectricCar;
    private final int age = 25;
    private final double homeLocation = 10.0;
    private final double workLocation = 20.0;

    @BeforeEach
    void setUp() {
        spyElectricCar = Mockito.spy(new ElectricCar(0, 1));
        spyPetrolCar = Mockito.spy(new PetrolCar(0, 1));
        person = new Person(age, homeLocation, workLocation, spyPetrolCar);
    }

    @Disabled
    @Test
    void testPerson(){
        Car car = new PetrolCar(10,2);
        Person person = new Person(19,10.1,46.10, car);
        person.goToWork();
        person.goToHome();
        person.goToWork();
        person.goToHome();
        person.goToWork();

        assert (car.getEnergyValue() > 0 && car.getEnergyValue() <=100);
    }

    @Test
    void testConstructorInitializesFieldsCorrectly() {
        assertThrows(IllegalArgumentException.class, () -> new Person(age, homeLocation, workLocation, null),
                "Constructor should throw IllegalArgumentException if car is null");
    }

    @Test
    void testGoToWorkDrivesToWorkLocation() {
        person.goToWork();

        verify(spyPetrolCar).driveTo(workLocation);
        assertEquals(workLocation, spyPetrolCar.getLocation(), 0.1, "Car should reach work location");

    }

    @Test
    void testGoToHomeDrivesToHomeLocation() {
        person.goToHome();

        verify(spyPetrolCar).driveTo(homeLocation);
        assertEquals(homeLocation, spyPetrolCar.getLocation(), 0.1, "Car should reach home location");
    }

    @Test
    void testDriveTooYoungToDrive() {
        Person youngPerson = new Person(16, homeLocation, workLocation, spyPetrolCar);
        youngPerson.goToWork();

        verify(spyPetrolCar, never()).driveTo(anyDouble());
        verify(spyPetrolCar, never()).needsEnergy(anyDouble());
    }

    @Test
    void testDriveWhenNeedsEnergy() {
        when(spyPetrolCar.needsEnergy(workLocation)).thenReturn(true);

        person.goToWork();

        verify(spyPetrolCar).driveTo(workLocation);
        verify(spyPetrolCar).refuel();
        assertEquals(workLocation, spyPetrolCar.getLocation(), 0.1, "Car should reach work location after refueling");
    }

    @Test
    void testAddEnergyForElectricCar() {
        when(spyElectricCar.needsEnergy(workLocation)).thenReturn(true);

        person = new Person(age, homeLocation, workLocation, spyElectricCar);
        person.goToWork();

        verify(spyElectricCar).driveTo(15.0);
        verify(spyElectricCar).refuel();
        assertEquals(workLocation, spyElectricCar.getLocation(), 0.1, "Car should reach work location after recharging");
    }

    @Test
    void testAddEnergyForGasCar() {
        when(spyPetrolCar.needsEnergy(workLocation)).thenReturn(true);

        person.goToWork();

        verify(spyPetrolCar).driveTo(10.0);
        verify(spyPetrolCar).refuel();
        assertEquals(workLocation, spyPetrolCar.getLocation(), 0.1, "Car should reach work location after refueling");
    }

    @Test
    void testChangeCar() {
        Car newMockCar = mock(Car.class);
        person.changeCar(newMockCar);

        person.goToWork();

        verify(newMockCar).driveTo(workLocation);
        verify(spyPetrolCar, never()).driveTo(anyDouble());
    }

}