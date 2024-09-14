package SQLServer;

import java.io.InputStream;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import Model.Activity;
import Model.Apartment;
import Model.Fee;
import Model.Payment;
import Model.Resident;
import Model.User;
import Model.Vehicle;
import Resources.Constant.Constant;
import Resources.Constant.Tool;
public class DBQuery {
    public static boolean addActivity(Activity activity) {
        if (DBConnection.database != null) {
            try {
                PreparedStatement preparedStatement = DBConnection.database.prepareStatement("INSERT INTO Activity VALUES (?, ?, ?, ?, ?)");
                
                preparedStatement.setLong(1, activity.getResidentId());
                preparedStatement.setInt(2, activity.getStatus());
                preparedStatement.setDate(3, activity.getTimeIn());
                preparedStatement.setDate(4, activity.getTimeOut());
                preparedStatement.setString(5, activity.getNote());

                preparedStatement.executeUpdate();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return false;
    }
    public static boolean addNewApartment(int apartmentId, long ownerId) {
        if (DBConnection.database != null) {
            try {
                PreparedStatement preparedStatement = DBConnection.database.prepareStatement("UPDATE Apartment SET ownerId = ? WHERE apartmentId = ?");

                preparedStatement.setLong(1, ownerId);
                preparedStatement.setInt(2, apartmentId);

                preparedStatement.executeUpdate();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }
    public static boolean addNewFee(String name, int cost, boolean mandatory, int cycle, String expirationDate) {
        if (DBConnection.database != null) {
            try {
                PreparedStatement preparedStatement = DBConnection.database.prepareStatement("INSERT INTO Fee([name], cost, mandatory, cycle, expiration, [status]) VALUES (?, ?, ?, ?, ?, 1)");

                preparedStatement.setString(1, name);
                preparedStatement.setInt(2, cost);
                preparedStatement.setBoolean(3, mandatory);
                preparedStatement.setInt(4, cycle);
                preparedStatement.setString(5, expirationDate);

                preparedStatement.executeUpdate();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }
    public static boolean addPayment(Payment payment) {
        if (DBConnection.database != null) {
            try {
                PreparedStatement preparedStatement = DBConnection.database.prepareStatement(
                    "INSERT INTO Payment(apartmentId, feeId, payee, [number], timeValidate, [month], [year], paid) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");

                preparedStatement.setInt(1, payment.getFloor() * 100 + payment.getRoom());
                preparedStatement.setInt(2, payment.getFeeId());
                preparedStatement.setString(3, payment.getPayee());
                preparedStatement.setInt(4, payment.getQuantity());
                preparedStatement.setDate(5, payment.getTimeValidate());
                preparedStatement.setInt(6, payment.getMonth());
                preparedStatement.setInt(7,payment.getYear());
                preparedStatement.setLong(8, payment.getPaid());

                preparedStatement.executeUpdate();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        }
        return false;
    }
    public static boolean addResident(Resident resident) {
        if (DBConnection.database != null) {
            try {
                PreparedStatement preparedStatement;
                if (getResident(resident.getId()) != null) {
                    preparedStatement = DBConnection.database.prepareStatement("UPDATE Resident SET name = ?, birthday = ?, gender = ?, phoneNumber = ?, nationality = ?, ethnic = ?, apartmentId = ?, relationship = ?, [status] = ? WHERE id = ?");

                    preparedStatement.setString(1, resident.getName());
                    preparedStatement.setDate(2, resident.getBirthday());
                    preparedStatement.setBoolean(3, resident.getGender());
                    preparedStatement.setInt(4, resident.getPhoneNumber());
                    preparedStatement.setString(5, resident.getNationality());
                    preparedStatement.setString(6, resident.getEthnic());
                    preparedStatement.setInt(7, resident.getFloor() * 100 + resident.getRoom());
                    preparedStatement.setString(8, resident.getRelationship());
                    preparedStatement.setInt(9, resident.getStatus());
                    preparedStatement.setLong(10, resident.getId());
                    System.out.println(resident.getStatus());
                } else {
                    preparedStatement = DBConnection.database.prepareStatement("INSERT INTO Resident VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

                    preparedStatement.setLong(1, resident.getId());
                    preparedStatement.setString(2, resident.getName());
                    preparedStatement.setDate(3, resident.getBirthday());
                    preparedStatement.setBoolean(4, resident.getGender());
                    preparedStatement.setInt(5, resident.getPhoneNumber());
                    preparedStatement.setString(6, resident.getNationality());
                    preparedStatement.setString(7, resident.getEthnic());
                    preparedStatement.setInt(8, resident.getFloor() * 100 + resident.getRoom());
                    preparedStatement.setString(9, resident.getRelationship());
                    preparedStatement.setInt(10, resident.getStatus());
                }
                preparedStatement.executeUpdate();

                if (getOwner(resident.getFloor(), resident.getRoom()) == null) {
                    addNewApartment(resident.getFloor() * 100 + resident.getRoom(), resident.getId());
                }

                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }
    public static boolean addVehicle(Vehicle vehicle) {
        if (DBConnection.database != null) {
            try {
                PreparedStatement preparedStatement = DBConnection.database.prepareStatement("INSERT INTO Vehicle VALUES (?, ?, ?)");

                preparedStatement.setString(1, vehicle.getLicensePlates());
                preparedStatement.setInt(2, vehicle.getFloor() * 100 + vehicle.getRoom());
                preparedStatement.setInt(3, vehicle.getType());

                preparedStatement.executeUpdate();

                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    public static boolean changeAvatar(int userId, InputStream inputStream) {
        if (DBConnection.database != null) {
            try {
                PreparedStatement preparedStatement = DBConnection.database.prepareStatement("UPDATE [User] SET [image] = ? WHERE userId = ?");
                
                preparedStatement.setBlob(1, inputStream);
                preparedStatement.setInt(2, userId);

                preparedStatement.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        return true;
    }
    public static boolean changeAvatar(int userId, ImageIcon image) {
        if (DBConnection.database != null) {
            try {
                PreparedStatement preparedStatement = DBConnection.database.prepareStatement("UPDATE [User] SET [image] = ? WHERE userId = ?");

                preparedStatement.setBytes(1, Tool.imageToBytes(image));
                preparedStatement.setInt(2, userId);

                preparedStatement.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return true;
    }
    public static boolean changeInfo(int userId, String name, Date birthday, int phoneNumber, String address) {
        if (DBConnection.database != null) {
            try {
                PreparedStatement preparedStatement = DBConnection.database.prepareStatement("UPDATE [User] SET [name] = ?, birthday = ?, phoneNumber = ?, address = ? WHERE userId = ?");

                preparedStatement.setString(1, name);
                preparedStatement.setDate(2, birthday);
                preparedStatement.setInt(3, phoneNumber);
                preparedStatement.setString(4, address);
                preparedStatement.setInt(5, userId);

                preparedStatement.executeUpdate();

                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return false;
    }
    public static boolean changePassword(int userId, String newPassword) {
        if (DBConnection.database != null) {
            try {
                PreparedStatement preparedStatement = DBConnection.database.prepareStatement("UPDATE [Login] SET password = ? WHERE userId = ?");

                preparedStatement.setString(1, newPassword);
                preparedStatement.setInt(2, userId);

                preparedStatement.executeUpdate();

                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }
    public static boolean checkOldPassword(int userId, String oldPassword) {
        if (DBConnection.database != null) {
            try {
                PreparedStatement preparedStatement = DBConnection.database.prepareStatement("SELECT * FROM [Login] WHERE userId = ? AND password = ?");

                preparedStatement.setInt(1, userId);
                preparedStatement.setString(2, oldPassword);

                ResultSet resultSet = preparedStatement.executeQuery();
                
                if (resultSet.next()) {
                    return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static boolean deleteApartment(ArrayList<Integer> selections) {
        if (DBConnection.database != null) {
            try {
                PreparedStatement preparedStatement = DBConnection.database.prepareStatement("UPDATE Apartment SET ownerId = null WHERE apartmentId = ?");
                PreparedStatement preparedStatement2 = DBConnection.database.prepareStatement("DELETE FROM Vehicle WHERE apartmentId = ?");

                for (Integer i : selections) {
                    preparedStatement.setInt(1, i);
                    preparedStatement.addBatch();
                    preparedStatement2.setInt(1, i);
                    preparedStatement2.addBatch();
                }
                preparedStatement.executeBatch();
                preparedStatement2.executeBatch();
                
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }
    public static boolean deleteFee(ArrayList<Integer> selections) {
        if (DBConnection.database != null) {
            try {
                PreparedStatement preparedStatement = DBConnection.database.prepareStatement("UPDATE Fee SET [status] = 0 WHERE id = ?");

                for (Integer i : selections) {
                    preparedStatement.setInt(1, i);
                    preparedStatement.addBatch();
                }
                preparedStatement.executeBatch();

                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }
    public static boolean deleteResident(ArrayList<Resident> selections) {
        if (DBConnection.database != null) {
            try {
                PreparedStatement preparedStatement = DBConnection.database.prepareStatement("UPDATE Resident SET [status] = 3 WHERE id = ?");
                PreparedStatement preparedStatement2 = DBConnection.database.prepareStatement("UPDATE Apartment SET ownerId = (SELECT TOP 1 id FROM Resident WHERE apartmentId = ?) WHERE ownerId = ?");

                for (Resident resident : selections) {
                    preparedStatement.setLong(1, resident.getId());
                    preparedStatement.addBatch();
                    preparedStatement2.setInt(1, resident.getFloor() * 100 + resident.getRoom());
                    preparedStatement2.setLong(2, resident.getId());
                    preparedStatement2.addBatch();
                }
                preparedStatement.executeBatch();
                preparedStatement2.executeBatch();
                
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }
    public static boolean deleteVehicle(ArrayList<String> selections) {
        if (DBConnection.database != null) {
            try {
                PreparedStatement preparedStatement = DBConnection.database.prepareStatement("DELETE FROM Vehicle WHERE license_plates = ?");

                for (String selection : selections) {
                    preparedStatement.setString(1, selection);
                    preparedStatement.addBatch();
                }
                preparedStatement.executeBatch();

                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static boolean editFee(Fee fee) {
        if (DBConnection.database != null) {
            try {
                PreparedStatement preparedStatement = DBConnection.database.prepareStatement("UPDATE Fee SET [name] = ?, cost = ?, mandatory = ?, cycle = ?, expiration = ? WHERE id = ?");
                
                preparedStatement.setString(1, fee.getName());
                preparedStatement.setInt(2, fee.getCost());
                preparedStatement.setBoolean(3, fee.getMandatory());
                preparedStatement.setInt(4, fee.getCycle());
                preparedStatement.setString(5, fee.getExpirationDate().toString());
                preparedStatement.setInt(6, fee.getId());

                preparedStatement.executeUpdate();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }
    public static boolean editResident(Resident resident, Long oldId) {
        if (DBConnection.database != null) {
            try {
                PreparedStatement preparedStatement = DBConnection.database.prepareStatement("UPDATE Resident SET id = ?, [name] = ?, birthday = ?, gender = ?, phoneNumber = ?, nationality = ?, ethnic = ?, apartmentId = ?, relationship = ? WHERE id = ?");

                preparedStatement.setLong(1, resident.getId());
                preparedStatement.setString(2, resident.getName());
                preparedStatement.setDate(3, resident.getBirthday());
                preparedStatement.setBoolean(4, resident.getGender());
                preparedStatement.setInt(5, resident.getPhoneNumber());
                preparedStatement.setString(6, resident.getNationality());
                preparedStatement.setString(7, resident.getEthnic());
                preparedStatement.setInt(8, resident.getFloor() * 100 + resident.getRoom());
                preparedStatement.setString(9, resident.getRelationship());
                preparedStatement.setLong(10, oldId);

                preparedStatement.executeUpdate();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }
    public static boolean editVehicle(Vehicle vehicle, String oldLicensePlate) {
        if (DBConnection.database != null) {
            try {
                PreparedStatement preparedStatement = DBConnection.database.prepareStatement("UPDATE Vehicle SET license_plates = ?, apartmentId = ?, [type] = ? WHERE license_plates = ?");

                preparedStatement.setString(1, vehicle.getLicensePlates());
                preparedStatement.setInt(2, vehicle.getFloor() * 100 + vehicle.getRoom());
                preparedStatement.setInt(3, vehicle.getType());
                preparedStatement.setString(4, oldLicensePlate);

                preparedStatement.executeUpdate();

                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static boolean existsPhoneNumber(int phoneNumber) {
        if (DBConnection.database != null) {
            try {
                PreparedStatement preparedStatement = DBConnection.database.prepareStatement("SELECT * FROM Resident WHERE phoneNumber = ?");

                preparedStatement.setInt(1, phoneNumber);

                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }
    public static boolean existsResident(long residentId) {
        if (DBConnection.database != null) {
            try {
                PreparedStatement preparedStatement = DBConnection.database.prepareStatement("SELECT * FROM Resident WHERE id = ? AND [status] != 3");

                preparedStatement.setLong(1, residentId);

                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static boolean exchange(ArrayList<Long> residentIds, int floor, int room) {
        if (DBConnection.database != null) {
            try {
                PreparedStatement preparedStatement = DBConnection.database.prepareStatement("UPDATE Resident SET apartmentId = ? WHERE id = ?");

                preparedStatement.setInt(1, floor * 100 + room);
                for (Long long1 : residentIds) {
                    preparedStatement.setLong(2, long1);
                    preparedStatement.addBatch();
                }

                preparedStatement.executeBatch();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }
    
    public static User findUser(String username, String password) {
        Integer userId = null;

        if (DBConnection.database != null) {
            try {
                PreparedStatement preparedStatement = DBConnection.database.prepareStatement("SELECT * FROM [Login] WHERE username = ? AND password = ?");

                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);
                
                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    userId = resultSet.getInt(1);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (userId != null) {
            try {
                PreparedStatement preparedStatement = DBConnection.database.prepareStatement("SELECT * FROM [User] WHERE userId = ?");

                preparedStatement.setInt(1, userId);

                ResultSet resultSet = preparedStatement.executeQuery();
                if (resultSet.next()) {
                    return new User(
                        userId,
                        resultSet.getString(2),
                        resultSet.getDate(3).toString(),
                        (Integer.valueOf(resultSet.getInt(4))).toString(),
                        resultSet.getBytes(5) == null ? Tool.resize(new ImageIcon(Constant.image + "/avatar.png"), 512, 512) : Tool.BytesToImage(resultSet.getBytes(5)),
                        resultSet.getString(6)
                    );
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public static Apartment getApartment(Integer id) {
        if (DBConnection.database != null) {
            try {
                PreparedStatement preparedStatement = DBConnection.database.prepareStatement("SELECT Apartment.apartmentId, area, [name], phoneNumber FROM Apartment INNER JOIN Resident ON ownerId = id WHERE Apartment.apartmentId = ?");

                preparedStatement.setInt(1, id);

                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    return new Apartment(resultSet.getInt(1), resultSet.getInt(1) / 100, resultSet.getInt(1) % 100, resultSet.getFloat(2), resultSet.getString(3), resultSet.getString(4));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return null;
    }
    public static ArrayList<Apartment> getApartmentList() {
        ArrayList<Apartment> apartmentList = new ArrayList<Apartment>();

        if (DBConnection.database != null) {
            try {
                PreparedStatement preparedStatement = DBConnection.database.prepareStatement("SELECT Apartment.apartmentId, area, [name], phoneNumber FROM Apartment INNER JOIN Resident ON ownerId = id ORDER BY apartmentId ASC");
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    apartmentList.add(new Apartment(resultSet.getInt(1), resultSet.getInt(1) / 100, resultSet.getInt(1) % 100, resultSet.getInt(2), resultSet.getString(3), resultSet.getString(4)));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return apartmentList;
    }

    public static Fee getFee(int feeId) {
        if (DBConnection.database != null) {
            try {
                PreparedStatement preparedStatement = DBConnection.database.prepareStatement("SELECT * FROM Fee WHERE id = ?");

                preparedStatement.setInt(1, feeId);

                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    return new Fee(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getInt(3),
                        resultSet.getBoolean(4),
                        resultSet.getInt(5),
                        resultSet.getDate(6) == null ? "" : resultSet.getDate(6).toString()
                    );
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    public static ArrayList<Fee> getFeeList(int cycle) {
        ArrayList<Fee> feeList = new ArrayList<Fee>();

        if (DBConnection.database != null) {
            try {
                PreparedStatement preparedStatement = DBConnection.database.prepareStatement("SELECT * FROM Fee WHERE cycle = ? AND [status] = 1");
                preparedStatement.setInt(1, cycle);

                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    feeList.add(new Fee(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getInt(3),
                        resultSet.getBoolean(4),
                        resultSet.getInt(5),
                        resultSet.getString(6)));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return feeList;
    }
    public static ArrayList<Activity> getHistory(Long residentId) {
        ArrayList<Activity> activities = new ArrayList<Activity>();

        if (DBConnection.database != null) {
            try {
                PreparedStatement preparedStatement = DBConnection.database.prepareStatement("SELECT * FROM Activity WHERE residentId = ? ORDER BY id ASC");

                preparedStatement.setLong(1, residentId);

                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    activities.add(new Activity(
                        resultSet.getInt(1),
                        residentId,
                        resultSet.getInt(3),
                        resultSet.getDate(4),
                        resultSet.getDate(5),
                        resultSet.getString(6)));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return activities;
    }
    public static Resident getOwner(int floor, int room) {
        if (DBConnection.database != null) {
            try {
                PreparedStatement preparedStatement = DBConnection.database.prepareStatement("SELECT * FROM Resident WHERE id = (SELECT ownerId FROM Apartment WHERE apartmentId = ?)");
                
                preparedStatement.setInt(1, floor * 100 + room);
                
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    return new Resident(
                        resultSet.getLong(1),
                        resultSet.getString(2),
                        resultSet.getDate(3),
                        resultSet.getBoolean(4),
                        resultSet.getInt(5),
                        resultSet.getString(6),
                        resultSet.getString(7),
                        resultSet.getInt(8) / 100,
                        resultSet.getInt(8) % 100,
                        resultSet.getString(9),
                        resultSet.getInt(10));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static ArrayList<Payment> getPaymentList(int feeId) {
        ArrayList<Payment> payments = new ArrayList<Payment>();

        if (DBConnection.database != null) {
            try {
                PreparedStatement preparedStatement = DBConnection.database.prepareStatement(
                    "SELECT paymentId, apartmentId, payee, [number], timeValidate, [month], [year], paid FROM Payment WHERE feeId = ?");
                
                preparedStatement.setInt(1, feeId);
                
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    payments.add(new Payment(
                        resultSet.getInt(1),
                        resultSet.getInt(2) / 100,
                        resultSet.getInt(2) % 100,
                        feeId,
                        resultSet.getString(3),
                        resultSet.getInt(4),
                        resultSet.getDate(5),
                        resultSet.getInt(6),
                        resultSet.getInt(7),
                        resultSet.getLong(8)
                    ));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return payments;
    }

    public static Resident getResident(Long residentId) {
        if (DBConnection.database != null) {
            try {
                PreparedStatement preparedStatement = DBConnection.database.prepareStatement("SELECT * FROM Resident WHERE id = ?");

                preparedStatement.setLong(1, residentId);

                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    return new Resident(
                        resultSet.getLong(1),
                        resultSet.getString(2),
                        resultSet.getDate(3),
                        resultSet.getBoolean(4),
                        resultSet.getInt(5),
                        resultSet.getString(6),
                        resultSet.getString(7),
                        resultSet.getInt(8) / 100,
                        resultSet.getInt(8) % 100,
                        resultSet.getString(9),
                        resultSet.getInt(10));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    public static ArrayList<Resident> getResidentList() {
        ArrayList<Resident> residentList = new ArrayList<Resident>();

        if (DBConnection.database != null) {
            try {
                PreparedStatement preparedStatement = DBConnection.database.prepareStatement("SELECT * FROM Resident WHERE [status] != 3 ORDER BY apartmentId ASC, [name] ASC");
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    residentList.add(new Resident(
                        resultSet.getLong(1),
                        resultSet.getString(2),
                        resultSet.getDate(3),
                        resultSet.getBoolean(4),
                        resultSet.getInt(5),
                        resultSet.getString(6),
                        resultSet.getString(7),
                        resultSet.getInt(8) / 100,
                        resultSet.getInt(8) % 100,
                        resultSet.getString(9),
                        resultSet.getInt(10)));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return residentList;
    }
    public static ArrayList<Resident> getResidentList(int status) {
        ArrayList<Resident> residentList = new ArrayList<Resident>();

        if (DBConnection.database != null) {
            try {
                PreparedStatement preparedStatement = DBConnection.database.prepareStatement("SELECT * FROM Resident WHERE [status] = ? ORDER BY apartmentId ASC, [name] ASC");

                preparedStatement.setInt(1, status);

                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    residentList.add(new Resident(
                        resultSet.getLong(1),
                        resultSet.getString(2),
                        resultSet.getDate(3),
                        resultSet.getBoolean(4),
                        resultSet.getInt(5),
                        resultSet.getString(6),
                        resultSet.getString(7),
                        resultSet.getInt(8) / 100,
                        resultSet.getInt(8) % 100,
                        resultSet.getString(9),
                        resultSet.getInt(10)));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return residentList;
    }
    public static ArrayList<Resident> getResidentList(int floor, int room) {
        ArrayList<Resident> residentList = new ArrayList<Resident>();

        if (DBConnection.database != null) {
            try {
                PreparedStatement preparedStatement = DBConnection.database.prepareStatement("SELECT * FROM Resident WHERE apartmentId = ? AND status != 3");

                preparedStatement.setInt(1, floor * 100 + room);

                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    residentList.add(new Resident(
                        resultSet.getLong(1),
                        resultSet.getString(2),
                        resultSet.getDate(3),
                        resultSet.getBoolean(4),
                        resultSet.getInt(5),
                        resultSet.getString(6),
                        resultSet.getString(7),
                        resultSet.getInt(8) / 100,
                        resultSet.getInt(8) % 100,
                        resultSet.getString(9),
                        resultSet.getInt(10)));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return residentList;
    }

    public static ArrayList<Vehicle> getVehicle() {
        ArrayList<Vehicle> vehicles = new ArrayList<>();

        if (DBConnection.database != null) {
            try {
                PreparedStatement preparedStatement = DBConnection.database.prepareStatement("SELECT * FROM Vehicle ORDER BY apartmentId");

                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    vehicles.add(new Vehicle(
                        resultSet.getString(1),
                        resultSet.getInt(2) / 100, 
                        resultSet.getInt(2) % 100,
                        resultSet.getInt(3)));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return vehicles;
    }
    public static Vehicle getVehicle(String licensePlate) {
        if (DBConnection.database != null) {
            try {
                PreparedStatement preparedStatement = DBConnection.database.prepareStatement("SELECT * FROM Vehicle WHERE license_plates = ?");

                preparedStatement.setString(1, licensePlate);

                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    return new Vehicle(
                        resultSet.getString(1),
                        resultSet.getInt(2) / 100, 
                        resultSet.getInt(2) % 100,
                        resultSet.getInt(3));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return null;
    }
}
