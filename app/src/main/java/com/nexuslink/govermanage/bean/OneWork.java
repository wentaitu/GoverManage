package com.nexuslink.govermanage.bean;

import java.util.List;

public class OneWork {

    /**
     * state : 200
     * info : success
     * data : {"id":40,"area":222,"grid":14,"log_name":"2018-11-11","grid_admin":"test","log_type":2,"edit_time":"2018-12-20 16:05:45","content":"ddd","state":1,"pic":["http://web.cqupt.store/public/mission/20181220/375cfdc79d46e6c13ab9e8fb4c9010c5.png"]}
     */

    private int state;
    private String info;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 40
         * area : 222
         * grid : 14
         * log_name : 2018-11-11
         * grid_admin : test
         * log_type : 2
         * edit_time : 2018-12-20 16:05:45
         * content : ddd
         * state : 1
         * pic : ["http://web.cqupt.store/public/mission/20181220/375cfdc79d46e6c13ab9e8fb4c9010c5.png"]
         */

        private int id;
        private int area;
        private int grid;
        private String log_name;
        private String grid_admin;
        private int log_type;
        private String edit_time;
        private String content;
        private int state;
        private List<String> pic;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getArea() {
            return area;
        }

        public void setArea(int area) {
            this.area = area;
        }

        public int getGrid() {
            return grid;
        }

        public void setGrid(int grid) {
            this.grid = grid;
        }

        public String getLog_name() {
            return log_name;
        }

        public void setLog_name(String log_name) {
            this.log_name = log_name;
        }

        public String getGrid_admin() {
            return grid_admin;
        }

        public void setGrid_admin(String grid_admin) {
            this.grid_admin = grid_admin;
        }

        public int getLog_type() {
            return log_type;
        }

        public void setLog_type(int log_type) {
            this.log_type = log_type;
        }

        public String getEdit_time() {
            return edit_time;
        }

        public void setEdit_time(String edit_time) {
            this.edit_time = edit_time;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public List<String> getPic() {
            return pic;
        }

        public void setPic(List<String> pic) {
            this.pic = pic;
        }
    }
}
