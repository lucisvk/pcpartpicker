package com.example.pcpartpicker;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.util.List;

public class PCBuildPage {
    private final String username;
    private TextField buildNameField;
    private ComboBox<Part> cpuBox, motherboardBox, gpuBox, psuBox, ramBox, aioBox, storageBox, caseBox;
    private Label errorLabel = new Label();
    private Button validateButton;
    private Button backButton;
    private BuildData editingBuild = null;

    public PCBuildPage(String username) {
        this.username = username;
    }

    public void showBuildPage(Stage stage, BuildData buildData) {
        this.editingBuild = buildData;

        Label titleLabel = new Label(editingBuild == null ? "Create New Build" : "Edit Build");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        titleLabel.setTextFill(Color.web("#2c3e50"));

        buildNameField = new TextField();
        buildNameField.setPromptText("Build Name");
        buildNameField.setFont(Font.font("Arial", 14));

        cpuBox = createPartComboBox("cpu");
        motherboardBox = createPartComboBox("motherboard");
        gpuBox = createPartComboBox("gpu");
        psuBox = createPartComboBox("psu");
        ramBox = createPartComboBox("ram");
        aioBox = createPartComboBox("aio");
        storageBox = createPartComboBox("storage");
        caseBox = createPartComboBox("pc_case");

        if (editingBuild != null) {
            buildNameField.setText(editingBuild.getBuildName());
            selectPart(cpuBox, editingBuild.getCpu());
            selectPart(motherboardBox, editingBuild.getMotherboard());
            selectPart(gpuBox, editingBuild.getGpu());
            selectPart(psuBox, editingBuild.getPsu());
            selectPart(ramBox, editingBuild.getRam());
            selectPart(aioBox, editingBuild.getAio());
            selectPart(storageBox, editingBuild.getStorage());
            selectPart(caseBox, editingBuild.getPcCase());
        }

        validateButton = createButton("Validate & Save Build", "#2ecc71");
        validateButton.setOnAction(e -> validateAndSave(stage));

        errorLabel.setTextFill(Color.RED);
        errorLabel.setFont(Font.font("Arial", 12));

        backButton = createButton("Back Home", "#e74c3c");
        backButton.setOnAction(e -> new HomePage(username).showHomePage(stage));

        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);
        grid.addRow(0, label("Build Name:"), buildNameField);
        grid.addRow(1, label("CPU:"), cpuBox);
        grid.addRow(2, label("Motherboard:"), motherboardBox);
        grid.addRow(3, label("GPU:"), gpuBox);
        grid.addRow(4, label("PSU:"), psuBox);
        grid.addRow(5, label("RAM:"), ramBox);
        grid.addRow(6, label("AIO:"), aioBox);
        grid.addRow(7, label("Storage:"), storageBox);
        grid.addRow(8, label("Case:"), caseBox);

        VBox layout = new VBox(20, titleLabel, grid, validateButton, errorLabel, backButton);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);
        layout.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));

        Scene scene = new Scene(layout, 600, 650);
        stage.setScene(scene);
        stage.setTitle("Build Your PC");
    }

    private ComboBox<Part> createPartComboBox(String tableName) {
        ComboBox<Part> comboBox = new ComboBox<>();
        List<Part> parts = Database.getParts(tableName);
        comboBox.getItems().addAll(parts);
        comboBox.setPromptText("Select " + tableName.toUpperCase());
        comboBox.setPrefWidth(200);
        // Font and color can be influenced by cell factories:
        comboBox.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Part item, boolean empty) {
                super.updateItem(item, empty);
                setFont(Font.font("Arial", 14));
                if (empty || item == null) setText("Select Part");
                else setText(item.getName() + " ($" + item.getPrice() + ")");
            }
        });
        comboBox.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Part item, boolean empty) {
                super.updateItem(item, empty);
                setFont(Font.font("Arial", 14));
                if (empty || item == null) setText(null);
                else setText(item.getName() + " ($" + item.getPrice() + ")");
            }
        });
        return comboBox;
    }

    private Label label(String text) {
        Label lbl = new Label(text);
        lbl.setFont(Font.font("Arial", 14));
        lbl.setTextFill(Color.web("#2c3e50"));
        return lbl;
    }

    private Button createButton(String text, String colorHex) {
        Button button = new Button(text);
        button.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 14));
        button.setTextFill(Color.WHITE);
        button.setBackground(new Background(new BackgroundFill(Color.web(colorHex), CornerRadii.EMPTY, Insets.EMPTY)));
        return button;
    }

    private void selectPart(ComboBox<Part> box, String partName) {
        for (Part p : box.getItems()) {
            if (p.getName().equals(partName)) {
                box.setValue(p);
                break;
            }
        }
    }

    private void validateAndSave(Stage stage) {
        errorLabel.setText("");

        String buildName = buildNameField.getText();
        Part cpu = cpuBox.getValue();
        Part motherboard = motherboardBox.getValue();
        Part gpu = gpuBox.getValue();
        Part psu = psuBox.getValue();
        Part ram = ramBox.getValue();
        Part aio = aioBox.getValue();
        Part storage = storageBox.getValue();
        Part pcCase = caseBox.getValue();

        if (buildName == null || buildName.isEmpty()) {
            errorLabel.setText("Please enter a build name.");
            return;
        }

        if (cpu == null || motherboard == null || gpu == null || psu == null || ram == null || aio == null || storage == null || pcCase == null) {
            errorLabel.setText("Please select all components before validating.");
            return;
        }

        // Compatibility checks
        if (cpu.getType() != null && motherboard.getType() != null) {
            if ((cpu.getType().equalsIgnoreCase("Intel") && !motherboard.getType().equalsIgnoreCase("Intel")) ||
                    (cpu.getType().equalsIgnoreCase("Ryzen") && !motherboard.getType().equalsIgnoreCase("Ryzen"))) {
                errorLabel.setText("Incompatible CPU and Motherboard!");
                return;
            }
        }

        if (gpu.getRequiredPsu() > 0 && psu.getWattage() > 0 && psu.getWattage() < gpu.getRequiredPsu()) {
            errorLabel.setText(gpu.getName() + " requires at least " + gpu.getRequiredPsu() + "W PSU!");
            return;
        }

        double totalPrice = cpu.getPrice() + motherboard.getPrice() + gpu.getPrice() + psu.getPrice() + ram.getPrice() + aio.getPrice() + storage.getPrice() + pcCase.getPrice();

        String gamesInfo = determineGamingInfo(cpu, gpu);

        Database.saveBuild(username, buildName, cpu, motherboard, gpu, psu, ram, aio, storage, pcCase, totalPrice, gamesInfo);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Build Saved");
        alert.setHeaderText("Build Validated & Saved Successfully!");
        alert.setContentText("Total Price: $" + totalPrice + "\nGames Info: " + gamesInfo);
        alert.showAndWait();

        new HomePage(username).showHomePage(stage);
    }

    private String determineGamingInfo(Part cpu, Part gpu) {
        boolean highEndCPU = (cpu.getName().contains("i9") || cpu.getName().contains("Ryzen 9"));
        boolean highEndGPU = gpu.getName().contains("4080") || gpu.getName().contains("7900");
        if (highEndCPU && highEndGPU) {
            return "Can play most AAA games at 4K @ 120Hz";
        }

        boolean midCPU = (cpu.getName().contains("i7") || cpu.getName().contains("Ryzen 7"));
        boolean midGPU = gpu.getName().contains("3080") || gpu.getName().contains("6700");
        if (midCPU && midGPU) {
            return "Can play most AAA games at 1440p @ 60Hz";
        }

        return "Can play most games at 1080p @ 30Hz";
    }
}