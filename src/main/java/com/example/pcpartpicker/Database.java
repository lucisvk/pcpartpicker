package com.example.pcpartpicker;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {
    private static final String URL = "jdbc:mysql://localhost:3306/pc_part_picker";
    private static final String USER = "root";
    private static final String PASSWORD = "Lolplatgod12"; // Update this

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static boolean validateUser(String username, String password) {
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static List<Part> getParts(String tableName) {
        List<Part> parts = new ArrayList<>();
        String query = "SELECT * FROM " + tableName;
        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Part part = new Part();
                part.setName(rs.getString("name"));
                if (hasColumn(rs, "price")) {
                    part.setPrice(rs.getDouble("price"));
                }
                if (hasColumn(rs, "type")) {
                    part.setType(rs.getString("type"));
                }
                if (hasColumn(rs, "required_psu")) {
                    part.setRequiredPsu(rs.getInt("required_psu"));
                }
                if (hasColumn(rs, "wattage")) {
                    part.setWattage(rs.getInt("wattage"));
                }
                parts.add(part);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return parts;
    }

    private static boolean hasColumn(ResultSet rs, String columnName) throws SQLException {
        ResultSetMetaData md = rs.getMetaData();
        int columns = md.getColumnCount();
        for (int i = 1; i <= columns; i++) {
            if (md.getColumnName(i).equalsIgnoreCase(columnName)) {
                return true;
            }
        }
        return false;
    }

    public static void saveBuild(String username, String buildName, Part cpu, Part motherboard, Part gpu, Part psu, Part ram, Part aio, Part storage, Part pcCase, double totalPrice, String gamesInfo) {
        String query = "INSERT INTO builds (username, build_name, cpu, motherboard, gpu, psu, ram, aio, storage, pc_case, total_price, games_info) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, buildName);
            stmt.setString(3, cpu.getName());
            stmt.setString(4, motherboard.getName());
            stmt.setString(5, gpu.getName());
            stmt.setString(6, psu.getName());
            stmt.setString(7, ram.getName());
            stmt.setString(8, aio.getName());
            stmt.setString(9, storage.getName());
            stmt.setString(10, pcCase.getName());
            stmt.setDouble(11, totalPrice);
            stmt.setString(12, gamesInfo);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<String> getUserBuildNames(String username) {
        List<String> builds = new ArrayList<>();
        String query = "SELECT build_name FROM builds WHERE username = ?";
        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                builds.add(rs.getString("build_name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return builds;
    }

    public static BuildData loadBuild(String username, String buildName) {
        String query = "SELECT * FROM builds WHERE username = ? AND build_name = ?";
        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, buildName);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                BuildData build = new BuildData();
                build.setBuildName(rs.getString("build_name"));
                build.setCpu(rs.getString("cpu"));
                build.setMotherboard(rs.getString("motherboard"));
                build.setGpu(rs.getString("gpu"));
                build.setPsu(rs.getString("psu"));
                build.setRam(rs.getString("ram"));
                build.setAio(rs.getString("aio"));
                build.setStorage(rs.getString("storage"));
                build.setPcCase(rs.getString("pc_case"));
                build.setTotalPrice(rs.getDouble("total_price"));
                build.setGamesInfo(rs.getString("games_info"));
                return build;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}

class BuildData {
    private String buildName, cpu, motherboard, gpu, psu, ram, aio, storage, pcCase, gamesInfo;
    private double totalPrice;

    // Getters and setters...
    public String getBuildName() {return buildName;}
    public void setBuildName(String buildName) {this.buildName = buildName;}
    public String getCpu() {return cpu;}
    public void setCpu(String cpu) {this.cpu = cpu;}
    public String getMotherboard() {return motherboard;}
    public void setMotherboard(String motherboard) {this.motherboard = motherboard;}
    public String getGpu() {return gpu;}
    public void setGpu(String gpu) {this.gpu = gpu;}
    public String getPsu() {return psu;}
    public void setPsu(String psu) {this.psu = psu;}
    public String getRam() {return ram;}
    public void setRam(String ram) {this.ram = ram;}
    public String getAio() {return aio;}
    public void setAio(String aio) {this.aio = aio;}
    public String getStorage() {return storage;}
    public void setStorage(String storage) {this.storage = storage;}
    public String getPcCase() {return pcCase;}
    public void setPcCase(String pcCase) {this.pcCase = pcCase;}
    public String getGamesInfo() {return gamesInfo;}
    public void setGamesInfo(String gamesInfo) {this.gamesInfo = gamesInfo;}
    public double getTotalPrice() {return totalPrice;}
    public void setTotalPrice(double totalPrice) {this.totalPrice = totalPrice;}
}