package Controller;

import java.util.ArrayList;

import Model.Apartment;
import Model.Resident;
import Model.Vehicle;
import SQLServer.DBQuery;

public class ApartmentCtrl {
    public static boolean addNewApartment(int apartmentId, int ownerId) {
        return DBQuery.addNewApartment(apartmentId, ownerId);
    }
    public static boolean addVehicle(Vehicle vehicle) {
        return DBQuery.addVehicle(vehicle);
    }

    public static boolean deleteApartment(ArrayList<Integer> selections) {
        return DBQuery.deleteApartment(selections);
    }
    public static boolean deleteVehicle(ArrayList<String> selections) {
        return DBQuery.deleteVehicle(selections);
    }

    public static boolean editVehicle(Vehicle vehicle, String oldLicensePlate) {
        return DBQuery.editVehicle(vehicle, oldLicensePlate);
    }

    public static Apartment getApartment(Integer id) {
        return DBQuery.getApartment(id);
    }

    public static ArrayList<Apartment> getApartmentList() {
        return DBQuery.getApartmentList();
    }

    public static ArrayList<Resident> getMembers(int id) {
        return DBQuery.getResidentList(id / 100, id % 100);
    }

    public static Resident getOwner(int floor, int room) {
        return DBQuery.getOwner(floor, room);
    }

    public static ArrayList<Vehicle> getVehicle() {
        return DBQuery.getVehicle();
    }
    public static Vehicle getVehicle(String licensePlate) {
        return DBQuery.getVehicle(licensePlate);
    }
}
