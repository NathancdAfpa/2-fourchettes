module fr.afpa.fourchettes {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires transitive javafx.graphics;
    opens fr.afpa.fourchettes to javafx.fxml;
    exports fr.afpa.fourchettes;
}
