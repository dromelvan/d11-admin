package org.d11.admin.model;

import java.util.ArrayList;
import java.util.List;

public class UpdateMatchStatsResult extends D11Model {

    private List<String> validation_errors = new ArrayList<String>();
    private List<String> data_errors = new ArrayList<String>();
    private List<MissingPlayer> missing_players = new ArrayList<MissingPlayer>();
    private List<String> data_updates = new ArrayList<String>();

    public List<String> getValidationErrors() {
        return validation_errors;
    }

    public void setValidationErrors(List<String> validationErrors) {
        this.validation_errors = validationErrors;
    }

    public List<String> getDataErrors() {
        return data_errors;
    }

    public void setDataErrors(List<String> dataErrors) {
        this.data_errors = dataErrors;
    }

    public List<MissingPlayer> getMissingPlayers() {
        return missing_players;
    }

    public void setMissingPlayers(List<MissingPlayer> missingPlayers) {
        this.missing_players = missingPlayers;
    }

    public List<String> getDataUpdates() {
        return data_updates;
    }

    public void setDataUpdates(List<String> dataUpdates) {
        this.data_updates = dataUpdates;
    }

    public boolean isValid() {
        return getValidationErrors().isEmpty() && getDataErrors().isEmpty() && getMissingPlayers().isEmpty();
    }
}
