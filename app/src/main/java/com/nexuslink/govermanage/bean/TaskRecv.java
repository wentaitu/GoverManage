package com.nexuslink.govermanage.bean;

import java.util.List;

public class TaskRecv {

    /**
     * state : 200
     * info : success
     * data : [{"id":14,"area":"0","mission_title":"test","mission_level":"0","not_before":"2019-01-04 13:00:29","created_at":"2019-01-04 13:00:36","mission_type":"0","mission_people_json":"[\"22\"]","mission_people":1,"not_accepted_people":1,"processing_people":0,"finished_people":0,"content":"test","mission_from":5,"pic":["http://58.144.34.96:5000/web_manager/public/mission/20190104/4589b9a8f9c08a91841bcbd4cb5f86ed.PNG"]}]
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
         * id : 14
         * area : 0
         * mission_title : test
         * mission_level : 0
         * not_before : 2019-01-04 13:00:29
         * created_at : 2019-01-04 13:00:36
         * mission_type : 0
         * mission_people_json : ["22"]
         * mission_people : 1
         * not_accepted_people : 1
         * processing_people : 0
         * finished_people : 0
         * content : test
         * mission_from : 5
         * pic : ["http://58.144.34.96:5000/web_manager/public/mission/20190104/4589b9a8f9c08a91841bcbd4cb5f86ed.PNG"]
         */

        private int id;
        private String area;
        private String mission_title;
        private String mission_level;
        private String not_before;
        private String created_at;
        private String mission_type;
        private String mission_people_json;
        private int mission_people;
        private int not_accepted_people;
        private int processing_people;
        private int finished_people;
        private String content;
        private int mission_from;
        private List<String> pic;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getMission_title() {
            return mission_title;
        }

        public void setMission_title(String mission_title) {
            this.mission_title = mission_title;
        }

        public String getMission_level() {
            return mission_level;
        }

        public void setMission_level(String mission_level) {
            this.mission_level = mission_level;
        }

        public String getNot_before() {
            return not_before;
        }

        public void setNot_before(String not_before) {
            this.not_before = not_before;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getMission_type() {
            return mission_type;
        }

        public void setMission_type(String mission_type) {
            this.mission_type = mission_type;
        }

        public String getMission_people_json() {
            return mission_people_json;
        }

        public void setMission_people_json(String mission_people_json) {
            this.mission_people_json = mission_people_json;
        }

        public int getMission_people() {
            return mission_people;
        }

        public void setMission_people(int mission_people) {
            this.mission_people = mission_people;
        }

        public int getNot_accepted_people() {
            return not_accepted_people;
        }

        public void setNot_accepted_people(int not_accepted_people) {
            this.not_accepted_people = not_accepted_people;
        }

        public int getProcessing_people() {
            return processing_people;
        }

        public void setProcessing_people(int processing_people) {
            this.processing_people = processing_people;
        }

        public int getFinished_people() {
            return finished_people;
        }

        public void setFinished_people(int finished_people) {
            this.finished_people = finished_people;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getMission_from() {
            return mission_from;
        }

        public void setMission_from(int mission_from) {
            this.mission_from = mission_from;
        }

        public List<String> getPic() {
            return pic;
        }

        public void setPic(List<String> pic) {
            this.pic = pic;
        }
    }
}
