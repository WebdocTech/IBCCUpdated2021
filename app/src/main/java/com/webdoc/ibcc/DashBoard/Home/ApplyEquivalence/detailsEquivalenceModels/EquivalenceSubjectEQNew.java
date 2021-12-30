package com.webdoc.ibcc.DashBoard.Home.ApplyEquivalence.detailsEquivalenceModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EquivalenceSubjectEQNew {

        @SerializedName("groupId")
        @Expose
        private Integer groupId;
        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("name")
        @Expose
        private String name;

        public Integer getGroupId() {
            return groupId;
        }

        public void setGroupId(Integer groupId) {
            this.groupId = groupId;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

}