package com.example.moorthi.taskkttelematices.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class LoginModel extends RealmObject{

        private  String FirstName, LastName;
        @PrimaryKey
        private String Em;
        private String Password;


        public String getFirstName() {
            return FirstName;
        }

        public void setFirstName(String FirstName) {
            this.FirstName = FirstName;
        }

        public String getLastName() {
            return LastName;
        }

        public void setLastName(String LastName) {
            this.LastName = LastName;
        }

        public String getEm() {
            return Em;
        }

        public void setEm(String email) {
            this.Em = email;
        }

        public String getPassword() {
            return Password;
        }

        public void setPassword(String password) {
            this.Password = password;
        }

}
