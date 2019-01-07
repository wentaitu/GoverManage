package com.nexuslink.govermanage.bean;

import java.util.List;

public class OneEvent {


    /**
     * state : 200
     * info : success
     * data : [{"id":5,"area":0,"grid":1,"created_at":"2018-12-15 21:39:29","title":"test","emergency_level":1,"emergency_type":0,"from_who":3,"status":2,"state":1,"is_important":0,"content":"test","emergency_source":2,"transfered_people":0,"is_transfered":0,"process_people":5,"from_who_name":"test","pic":["http://web.cqupt.store/public/mission/20181216/893faf2bb3bae7d607fb91f1427c1d31.jpeg"]}]
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
         * id : 5
         * area : 0
         * grid : 1
         * created_at : 2018-12-15 21:39:29
         * title : test
         * emergency_level : 1
         * emergency_type : 0
         * from_who : 3
         * status : 2
         * state : 1
         * is_important : 0
         * content : test
         * emergency_source : 2
         * transfered_people : 0
         * is_transfered : 0
         * process_people : 5
         * from_who_name : test
         * pic : ["http://web.cqupt.store/public/mission/20181216/893faf2bb3bae7d607fb91f1427c1d31.jpeg"]
         */

        private int id;
        private int area;
        private int grid;
        private String created_at;
        private String title;
        private int emergency_level;
        private int emergency_type;
        private int from_who;
        private int status;
        private int state;
        private int is_important;
        private String content;
        private int emergency_source;
        private int transfered_people;
        private int is_transfered;
        private int process_people;
        private String from_who_name;
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

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getEmergency_level() {
            return emergency_level;
        }

        public void setEmergency_level(int emergency_level) {
            this.emergency_level = emergency_level;
        }

        public int getEmergency_type() {
            return emergency_type;
        }

        public void setEmergency_type(int emergency_type) {
            this.emergency_type = emergency_type;
        }

        public int getFrom_who() {
            return from_who;
        }

        public void setFrom_who(int from_who) {
            this.from_who = from_who;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public int getIs_important() {
            return is_important;
        }

        public void setIs_important(int is_important) {
            this.is_important = is_important;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getEmergency_source() {
            return emergency_source;
        }

        public void setEmergency_source(int emergency_source) {
            this.emergency_source = emergency_source;
        }

        public int getTransfered_people() {
            return transfered_people;
        }

        public void setTransfered_people(int transfered_people) {
            this.transfered_people = transfered_people;
        }

        public int getIs_transfered() {
            return is_transfered;
        }

        public void setIs_transfered(int is_transfered) {
            this.is_transfered = is_transfered;
        }

        public int getProcess_people() {
            return process_people;
        }

        public void setProcess_people(int process_people) {
            this.process_people = process_people;
        }

        public String getFrom_who_name() {
            return from_who_name;
        }

        public void setFrom_who_name(String from_who_name) {
            this.from_who_name = from_who_name;
        }

        public List<String> getPic() {
            return pic;
        }

        public void setPic(List<String> pic) {
            this.pic = pic;
        }
    }
}
