
package com.webdoc.ibcc.ResponseModels.GetDetailsEquivalence;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EquivalenceGroup {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("equivalenceSubject")
    @Expose
    private List<EquivalenceSubject> equivalenceSubject = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<EquivalenceSubject> getEquivalenceSubject() {
        return equivalenceSubject;
    }

    public void setEquivalenceSubject(List<EquivalenceSubject> equivalenceSubject) {
        this.equivalenceSubject = equivalenceSubject;
    }

}
