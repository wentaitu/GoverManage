package com.nexuslink.govermanage.bean;

import java.util.List;

public class GridManagerBean {

    /**
     * state : 200
     * info : success
     * data : [{"id":6,"name":"张主任","grid":"1","id_card":"5231980792847002","communication_phone":"13327890987","age":"40","sex":"1","icon":[],"server_area":"代家沟","state":1,"app_id":22,"app_info":{"id":22,"username":"13327890987","password":"e10adc3949ba59abbe56e057f20f883e","name":"张主任","identity":"4","area":"0","communicate_phone":"13327890987","last_login":"2019-01-04 12:57:09","last_login_ip":"123.147.250.194","user_state":"1","office_phone":"63879008","edit_person":5,"edit_time":"1970-01-01 08:00:00","icon":"","state":1,"grid":1,"lan":"29.53832","lon":"106.613922"}}]
     */

    private int state;
    private String info;
    private List<DataBean> data;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 6
         * name : 张主任
         * grid : 1
         * id_card : 5231980792847002
         * communication_phone : 13327890987
         * age : 40
         * sex : 1
         * icon : []
         * server_area : 代家沟
         * state : 1
         * app_id : 22
         * app_info : {"id":22,"username":"13327890987","password":"e10adc3949ba59abbe56e057f20f883e","name":"张主任","identity":"4","area":"0","communicate_phone":"13327890987","last_login":"2019-01-04 12:57:09","last_login_ip":"123.147.250.194","user_state":"1","office_phone":"63879008","edit_person":5,"edit_time":"1970-01-01 08:00:00","icon":"","state":1,"grid":1,"lan":"29.53832","lon":"106.613922"}
         */

        private int id;
        private String name;
        private String grid;
        private String id_card;
        private String communication_phone;
        private String age;
        private String sex;
        private String server_area;
        private int state;
        private int app_id;
        private AppInfoBean app_info;
        private List<?> icon;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getGrid() {
            return grid;
        }

        public void setGrid(String grid) {
            this.grid = grid;
        }

        public String getId_card() {
            return id_card;
        }

        public void setId_card(String id_card) {
            this.id_card = id_card;
        }

        public String getCommunication_phone() {
            return communication_phone;
        }

        public void setCommunication_phone(String communication_phone) {
            this.communication_phone = communication_phone;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getServer_area() {
            return server_area;
        }

        public void setServer_area(String server_area) {
            this.server_area = server_area;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public int getApp_id() {
            return app_id;
        }

        public void setApp_id(int app_id) {
            this.app_id = app_id;
        }

        public AppInfoBean getApp_info() {
            return app_info;
        }

        public void setApp_info(AppInfoBean app_info) {
            this.app_info = app_info;
        }

        public List<?> getIcon() {
            return icon;
        }

        public void setIcon(List<?> icon) {
            this.icon = icon;
        }

        public static class AppInfoBean {
            /**
             * id : 22
             * username : 13327890987
             * password : e10adc3949ba59abbe56e057f20f883e
             * name : 张主任
             * identity : 4
             * area : 0
             * communicate_phone : 13327890987
             * last_login : 2019-01-04 12:57:09
             * last_login_ip : 123.147.250.194
             * user_state : 1
             * office_phone : 63879008
             * edit_person : 5
             * edit_time : 1970-01-01 08:00:00
             * icon :
             * state : 1
             * grid : 1
             * lan : 29.53832
             * lon : 106.613922
             */

            private int id;
            private String username;
            private String password;
            private String name;
            private String identity;
            private String area;
            private String communicate_phone;
            private String last_login;
            private String last_login_ip;
            private String user_state;
            private String office_phone;
            private int edit_person;
            private String edit_time;
            private String icon;
            private int state;
            private int grid;
            private String lan;
            private String lon;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public String getPassword() {
                return password;
            }

            public void setPassword(String password) {
                this.password = password;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getIdentity() {
                return identity;
            }

            public void setIdentity(String identity) {
                this.identity = identity;
            }

            public String getArea() {
                return area;
            }

            public void setArea(String area) {
                this.area = area;
            }

            public String getCommunicate_phone() {
                return communicate_phone;
            }

            public void setCommunicate_phone(String communicate_phone) {
                this.communicate_phone = communicate_phone;
            }

            public String getLast_login() {
                return last_login;
            }

            public void setLast_login(String last_login) {
                this.last_login = last_login;
            }

            public String getLast_login_ip() {
                return last_login_ip;
            }

            public void setLast_login_ip(String last_login_ip) {
                this.last_login_ip = last_login_ip;
            }

            public String getUser_state() {
                return user_state;
            }

            public void setUser_state(String user_state) {
                this.user_state = user_state;
            }

            public String getOffice_phone() {
                return office_phone;
            }

            public void setOffice_phone(String office_phone) {
                this.office_phone = office_phone;
            }

            public int getEdit_person() {
                return edit_person;
            }

            public void setEdit_person(int edit_person) {
                this.edit_person = edit_person;
            }

            public String getEdit_time() {
                return edit_time;
            }

            public void setEdit_time(String edit_time) {
                this.edit_time = edit_time;
            }

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }

            public int getState() {
                return state;
            }

            public void setState(int state) {
                this.state = state;
            }

            public int getGrid() {
                return grid;
            }

            public void setGrid(int grid) {
                this.grid = grid;
            }

            public String getLan() {
                return lan;
            }

            public void setLan(String lan) {
                this.lan = lan;
            }

            public String getLon() {
                return lon;
            }

            public void setLon(String lon) {
                this.lon = lon;
            }
        }
    }
}
